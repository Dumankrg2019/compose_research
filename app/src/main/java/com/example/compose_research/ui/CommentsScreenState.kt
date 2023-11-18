package com.example.compose_research.ui

import com.example.compose_research.domain.FeedPost
import com.example.compose_research.domain.PostComment

sealed class CommentsScreenState {

    object Initial: CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ): CommentsScreenState()
}
