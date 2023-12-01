package com.example.compose_research.data.model.comments

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val authorId: String,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: String
)
