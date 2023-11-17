package com.example.compose_research.ui

import com.example.compose_research.domain.FeedPost
import com.example.compose_research.domain.PostComment

sealed class HomeScreenState {

    object Initial: HomeScreenState()
    data class Posts(
        val posts: List<FeedPost>
    ): HomeScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ): HomeScreenState()
}
