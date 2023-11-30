package com.example.compose_research.presentation.news

import com.example.compose_research.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial: NewsFeedScreenState()

    object Loading: NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ): NewsFeedScreenState()
}
