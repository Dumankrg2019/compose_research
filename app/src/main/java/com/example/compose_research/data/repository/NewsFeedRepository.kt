package com.example.compose_research.data.repository

import android.app.Application
import com.example.compose_research.data.mapper.NewsFeedMapper
import com.example.compose_research.data.network.ApiFactory
import com.example.compose_research.domain.FeedPost
import com.example.compose_research.domain.PostComment
import com.example.compose_research.domain.StatisticItem
import com.example.compose_research.domain.StatisticType
import com.example.compose_research.extensions.mergeWith
import com.example.compose_research.domain.AuthState
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepository(application: Application) {
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token
        get() = VKAccessToken.restore(storage)


    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private var nextFrom: String? = null

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect{
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            val authState = if(loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }

    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )


    suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.loadRecommendations(getAccessToken())
            } else {
                apiService.loadRecommendations(getAccessToken(), startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }


    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    val recommendations: StateFlow<List<FeedPost>> =
        loadedListFlow.mergeWith(refreshedListFlow).stateIn(
            scope = coroutineScope, started = SharingStarted.Lazily, initialValue = feedPosts
        )

    suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getAccessToken(), ownerId = feedPost.communityId, postId = feedPost.id
        )

        //удаляем пост с сервера и локально
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(), ownerId = feedPost.communityId, postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(), ownerId = feedPost.communityId, postId = feedPost.id
            )
        }
        val newLikeCount = response.likes.count

        /*создается копия старой коллекции, которую можно менять
        //новая коллекция с элементами статистики - для этого брали старую коллекцию - делаем ее изменяемой
        посредством toMutableList() - т.е создается ее копия, чтобы в старой не делать никаких изменений
        */
        val newStatistics = feedPost.statistics.toMutableList().apply {
            //удаляем элемент, который хранит кол-во лайков
            removeIf { it.type == StatisticType.LIKES }
            //и вставляем новый
            add(StatisticItem(type = StatisticType.LIKES, newLikeCount))
        }

        /*далее создаем новый объект feedPost, который явл копией старого но сдвумя отличиями
        1) актальное кол-во лайков и 2) поменян статус isFavorite
         */
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)

        //получаем номер позиции по которому лежал старый объект, далее по этому  индексу положим новый объект
        val postIndex = _feedPosts.indexOf(feedPost)

        _feedPosts[postIndex] = newPost
        refreshedListFlow.emit(feedPosts)
    }

    fun getComments(feedPost: FeedPost): Flow<List<PostComment>> = flow {
        val comments = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        emit(mapper.mapResponseToComments(comments))
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }

    companion object {
        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}