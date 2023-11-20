package com.example.compose_research

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_research.ui.ActivityResultTest
import com.example.compose_research.ui.VkNewsMS
import com.example.compose_research.ui.theme.Compose_researchTheme

class MainActivity : ComponentActivity() {

    //private val viewModel by viewModels<NewsFeedViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            Compose_researchTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        //.padding(8.dp),
                ) {
                    //PostCard()
                    //CustomOutlineButton()
                    //CustomDialog()
                    VkNewsMS()
                    //ActivityResultTest()
                    //ListComments(viewModel)
                    //MainScreen()
                   // InstagramProfileCard(viewModel)
                    //LazyColumnSample(viewModel)
                    //TestImage()
                }
            }
        }
    }
}


//@Composable
//fun ListComments(
//    viewModel: MainViewModel
//) {
//    val feedPosts = viewModel.screenState.observeAsState(listOf())
//
//    if(feedPosts.value.isNotEmpty()) {
//        val comments = mutableListOf<PostComment>().apply {
//            repeat(20) {
//                add(
//                    PostComment(id = it)
//                )
//            }
//        }
//        CommentsScreen(feedPost = feedPosts.value.get(0), comments = comments)
//    }
//}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_researchTheme {
       //InstagramProfileCard()
    }
}

@Preview
@Composable
fun showText() {
    Text(
        text = "Hello",
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Serif,
        color = Color.Green
    )
}

@Preview
@Composable
fun TestImage() {
    Image(
        modifier = Modifier
            .size(48.dp),
        painter = painterResource(id = R.drawable.insta),
        contentDescription = "Instagram",
        contentScale = ContentScale.Fit)
}

@Preview
@Composable
fun CustomOutlineButton() {
    OutlinedButton(onClick = {}) {
        Text(text = "Hello World!")
    }
}
@Preview
@Composable
private fun CustomDialog() {
    AlertDialog(
        onDismissRequest = {  },
        title = { Text(text = "Duman hello")},
        text = { Text(text = "Opan gangam style hop hop olya  gensgta rap is ready to fight with rock gyis")},
        dismissButton = {
            TextButton(onClick = { }) {
                Text(text = "close")
            }
        },
        confirmButton = {
            TextButton(onClick = { }) {
                Text(text = "Confirm")
            }
        }

    )
}