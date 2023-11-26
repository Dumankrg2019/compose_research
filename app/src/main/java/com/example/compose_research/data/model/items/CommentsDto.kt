package com.example.compose_research.data.model.items

import com.google.gson.annotations.SerializedName

data class CommentsDto(
    @SerializedName("count")
    val count: Int
)
