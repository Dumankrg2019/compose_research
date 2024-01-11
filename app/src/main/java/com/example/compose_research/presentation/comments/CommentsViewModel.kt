package com.example.compose_research.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.compose_research.data.repository.NewsFeedRepositoryImpl
import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : ViewModel() {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository)

    val screenState = getCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}