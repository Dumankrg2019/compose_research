package com.example.compose_research

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose_research.domain.FeedPost

class CommentsViewModelFactory(
    private val feedPost: FeedPost
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}