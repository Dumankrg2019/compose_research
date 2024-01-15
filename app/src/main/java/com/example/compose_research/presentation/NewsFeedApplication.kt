package com.example.compose_research.presentation

import android.app.Application
import com.example.compose_research.di.ApplicationComponent
import com.example.compose_research.di.DaggerApplicationComponent
import com.example.compose_research.domain.entity.FeedPost

class NewsFeedApplication: Application() {

    val component: ApplicationComponent by lazy {
       DaggerApplicationComponent.factory().create(this)
    }

}