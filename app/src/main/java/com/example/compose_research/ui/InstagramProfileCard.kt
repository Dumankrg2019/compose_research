package com.example.compose_research.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_research.R
import com.example.compose_research.domain.entity.InstagramModel

@Composable
fun InstagramProfileCard(
    model: InstagramModel,
    onFollowedButtonClickListener: (InstagramModel) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colors.background),
        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp
        ),
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground)
    ) {
        Log.e("RECOMPOSITION", "Card")
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.insta),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(8.dp)
                    ,
                )
                UserStatistics(title = "Posts", value = "6,950")
                UserStatistics(title = "Followers", value = "436M")
                UserStatistics(title = "Following", value = "76")

            }
            BottomTextAndBtn(
                isFollow = model.isFollowed,
                model = model,
                onFollowedButtonClickListener = onFollowedButtonClickListener
            )
        }
    }
}

@Composable
private fun UserStatistics(
    title: String,
    value: String
) {
    Column(
        modifier = Modifier.height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontFamily = FontFamily.Cursive,
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Preview
//@Composable
//fun PreviewCardLight() {
//    Compose_researchTheme(
//        darkTheme = false
//    ) {
//        InstagramProfileCard(viewModel = viewModel)
//    }
//}
//
//@Preview
//@Composable
//fun PreviewCardDark() {
//    Compose_researchTheme(
//        darkTheme = true
//    ) {
//        InstagramProfileCard()
//    }
//}

@Composable
fun BottomTextAndBtn(
    isFollow: Boolean,
    model: InstagramModel,
    onFollowedButtonClickListener: (InstagramModel) -> Unit
    ) {
//    val isFallowed = remember {
//        mutableStateOf(false)
//    }
    Log.e("RECOMPOSITION", "BottomTextAndBtn")
    Text(
        text = "Instagram ${model.id}",
        fontSize = 32.sp,
        fontFamily = FontFamily.Cursive
    )
    Text(
        text = "${model.title}",
        fontSize = 12.sp
    )
    Text(
        text = "www.facebook.com/emotional_health",
        fontSize = 12.sp
    )
    FollowBtn(model = model, onFollowedButtonClickListener = onFollowedButtonClickListener) {
        onFollowedButtonClickListener(model)
    }
}
@Composable
private fun FollowBtn(
    model: InstagramModel,
    onFollowedButtonClickListener: (InstagramModel) -> Unit,
    clickListener: () -> Unit
) {
    Log.e("RECOMPOSITION", "FollowBtn")
    Button(
        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomEnd = 4.dp,
            bottomStart = 4.dp,
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if(model.isFollowed) {
                MaterialTheme.colors.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colors.primary
            }
        ),
        onClick = {
         clickListener()
        },
        modifier = Modifier.padding(bottom = 10.dp)

    )

    {
        val text = if(model.isFollowed) {
            "Unfollow"
        } else {
            "Fallow"
        }
        Text(text = text, color = Color.White)
    }
}