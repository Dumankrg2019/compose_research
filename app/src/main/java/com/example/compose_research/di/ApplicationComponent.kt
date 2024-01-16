package com.example.compose_research.di

import android.content.Context
import com.example.compose_research.domain.entity.FeedPost
import com.example.compose_research.presentation.ViewModelFactory
import com.example.compose_research.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory
    fun getCommentsScreenComponentFactory(): CommentScreenComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}