package com.example.compose_research.ui

import com.example.compose_research.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial: NewsFeedScreenState()
    data class Posts(
        val posts: List<FeedPost>
    ): NewsFeedScreenState()
}
