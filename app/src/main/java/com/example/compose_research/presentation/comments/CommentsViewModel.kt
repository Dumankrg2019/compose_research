package com.example.compose_research.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_research.data.repository.NewsFeedRepository
import com.example.compose_research.domain.FeedPost
import com.example.compose_research.domain.PostComment
import kotlinx.coroutines.launch

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : ViewModel() {

    private val repository = NewsFeedRepository(application)

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    private fun loadComments(
        feedPost: FeedPost
    ) {
        viewModelScope.launch {
            val comments = repository.getComments(feedPost)
            _screenState.value = CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = comments
            )
        }
    }
}