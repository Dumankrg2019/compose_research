package com.example.compose_research.data.model

import com.example.compose_research.data.model.groups.GroupDto
import com.example.compose_research.data.model.items.PostDto
import com.google.gson.annotations.SerializedName

data class NewsFeedContentDto(
    @SerializedName("items") val posts: List<PostDto>,
    @SerializedName("groups") val groups: List<GroupDto>

)
