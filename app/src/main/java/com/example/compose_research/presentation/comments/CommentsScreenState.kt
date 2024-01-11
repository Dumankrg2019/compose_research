package com.example.compose_research.presentation.comments

import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.domain.entity.PostComment

sealed class CommentsScreenState {

    object Initial: CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ): CommentsScreenState()
}
