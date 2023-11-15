package com.example.compose_research.domain

import com.example.compose_research.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.ic_like,
    val commentText: String = "Long comment text",
    val publicationDate: String = "14: 00"
)
