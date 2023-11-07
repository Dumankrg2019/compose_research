package com.example.compose_research.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.compose_research.MainViewModel
import com.example.compose_research.R

@Composable
fun LazyColumnSample(viewModel: MainViewModel) {
    LazyColumn {
        item {
            Text(text = "Title", color = Color.Green)
        }
        items(10) {
            InstagramProfileCard(viewModel)
        }
        item {
            Image(
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = "post"
            )
        }
        items(200) {
            InstagramProfileCard(viewModel)
        }
    }
}