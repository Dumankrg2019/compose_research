package com.example.compose_research.domain

data class FeedPost(
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val publicationDate: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageResId: String?,
    val statistics: List<StatisticItem>,
    val isLiked: Boolean
)
