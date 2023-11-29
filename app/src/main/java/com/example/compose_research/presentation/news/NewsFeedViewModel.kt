package com.example.compose_research.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.compose_research.data.repository.NewsFeedRepository
import com.example.compose_research.domain.FeedPost
import com.example.compose_research.domain.InstagramModel
import com.example.compose_research.domain.StatisticItem
import kotlinx.coroutines.launch
import kotlin.random.Random

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {



    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository(application)
    init {
        Log.e("newsFeedVM", "start")
        loadRecommendations()
    }
    private fun loadRecommendations() {
        viewModelScope.launch {

            val feedPosts = repository.loadRecommendation()
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
            Log.e("loadRecommendations", "end ${_screenState.value}")
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)

            //после добавления лайка - получаем акутальную коллекцию
            _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
        }
    }

    private val initialList = mutableListOf<InstagramModel>().apply {
        repeat(100) {
            add(
                InstagramModel(
                    id = it,
                    title = "Title $it",
                    isFollowed = Random.nextBoolean()
                )
            )
        }
    }

    private val _models = MutableLiveData<List<InstagramModel>>(initialList)
    val models: LiveData<List<InstagramModel>> = _models

    fun changeFollowingStatus(model: InstagramModel) {
        val modifiedList = _models.value?.toMutableList() ?: mutableListOf() //создает arrayList
        modifiedList.replaceAll {
            if (it == model) {
                it.copy(isFollowed = !it.isFollowed)
            } else {
                it
            }
        }
        _models.value = modifiedList
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value

        if(currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun removeVKBloc(feedPost: FeedPost) {

        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = oldPosts)
    }
    fun delete(model: InstagramModel) {
        val modifiedList = _models.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(model)
        _models.value = modifiedList
    }
}