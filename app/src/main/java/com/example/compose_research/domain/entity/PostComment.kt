package com.example.compose_research.domain.entity

import com.example.compose_research.R

data class PostComment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val publicationDate: String
)
