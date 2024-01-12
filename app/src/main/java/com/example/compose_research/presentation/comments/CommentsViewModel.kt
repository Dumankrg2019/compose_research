package com.example.compose_research.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.compose_research.data.repository.NewsFeedRepositoryImpl
import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private var feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    val screenState = getCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}