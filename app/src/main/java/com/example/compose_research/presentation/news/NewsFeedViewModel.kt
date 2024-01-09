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
import com.example.compose_research.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.random.Random

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {



    private val repository = NewsFeedRepository(application)
    private val recommendationFlow = repository.recommendations

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()

    private val exceptionHandler = CoroutineExceptionHandler{_, _ ->
        Log.e("NewsFeedViewModel", "Exception caught by exceptionHandler")
    }


    val screenState =   recommendationFlow
        .filter { it.isNotEmpty() }
        .map {
            NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState
        }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)




    fun loadNextRecommendations() {
        Log.e("nexRecommendation", "oooopppssss")
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    posts = recommendationFlow.value,
                    nextDataIsLoading = true
                )
            )
            repository.loadNextData()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            repository.changeLikeStatus(feedPost)

            //после добавления лайка - получаем акутальную коллекцию

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


    fun removeVKBloc(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            repository.deletePost(feedPost)

        }
    }
    fun delete(model: InstagramModel) {
        val modifiedList = _models.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(model)
        _models.value = modifiedList
    }
}