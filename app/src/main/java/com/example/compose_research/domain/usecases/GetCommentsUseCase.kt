package com.example.compose_research.domain.usecases

import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.domain.entity.PostComment
import com.example.compose_research.domain.respository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> {
        return repository.getComments(feedPost)
    }
}