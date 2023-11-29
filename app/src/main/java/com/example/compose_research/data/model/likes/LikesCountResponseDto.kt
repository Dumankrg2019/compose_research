package com.example.compose_research.data.model.likes

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likes: LikesCountDto
)
