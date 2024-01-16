package com.example.compose_research.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.compose_research.di.ApplicationComponent
import com.example.compose_research.di.DaggerApplicationComponent
import com.example.compose_research.domain.entity.FeedPost

class NewsFeedApplication: Application() {

    val component: ApplicationComponent by lazy {
       DaggerApplicationComponent.factory().create(this)
    }

}

@Composable
fun getApplicationComponent(): ApplicationComponent{
    Log.e("RECOMPOSITION", "getApplicationComponent")
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}