package com.example.compose_research.data.model.items

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("count")
    val count: Int,
    @SerializedName("user_likes")
    val userLikes: Int
)
