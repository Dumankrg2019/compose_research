package com.example.compose_research.di

import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.presentation.ViewModelFactory
import dagger.Binds
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CommentsViewModelModule::class
    ]
)
interface CommentScreenComponent {

    fun getViewModelFactory(): ViewModelFactory
    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentScreenComponent
    }
}