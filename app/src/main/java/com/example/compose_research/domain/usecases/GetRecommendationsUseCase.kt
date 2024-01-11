package com.example.compose_research.domain.usecases

import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.domain.respository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetRecommendationsUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<List<FeedPost>> {
        return repository.getRecommendations()
    }
}