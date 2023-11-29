package com.example.compose_research.data.model.likes

import com.google.gson.annotations.SerializedName

data class LikesCountDto(
    @SerializedName("likes") val count: Int,
    @SerializedName("user_likes") val userLikes: Int,
)
