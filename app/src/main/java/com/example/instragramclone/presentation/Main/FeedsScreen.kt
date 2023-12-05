package com.example.instragramclone.presentation.Main


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.instragramclone.presentation.BottomNavigationItem
import com.example.instragramclone.presentation.BottomNavigationMenu
import com.example.instragramclone.presentation.Main.Posts.PostViewModel
import com.example.instragramclone.presentation.Toast
import com.example.instragramclone.util.Response
import com.example.instragramclone.R
import com.example.instragramclone.domain.model.Post


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedsScreen(navController:NavController){
    val postViewModel : PostViewModel = hiltViewModel()
    postViewModel.getAllPosts()
    when(val response =  postViewModel.postData.value){
        is Response.Loading->{
            CircularProgressIndicator()
        }
        is Response.Success-> {
            val obj = response.data
            Scaffold(topBar = {
                TopAppBar(title = { Text(text = "Instagram",) },
                    backgroundColor = MaterialTheme.colors.surface,
                    elevation = 10.dp,
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(painter = painterResource(id = R.drawable.ic_instagram), contentDescription = "Logo")
                        }
                    },
                    actions ={
                        IconButton(onClick = { }) {
                            Icon(painter = painterResource(id = R.drawable.ic_message), contentDescription = "Message" ,Modifier.size(30.dp), tint= Color.Black)
                        }
                    }
                )
            },content = {
                InstagramHomeContent()
            },
                bottomBar = {
                    BottomNavigationMenu(selectedItem = BottomNavigationItem.FEED, navController= navController)
                }
            )
        }

        is Response.Error->{
            Toast(message = response.message)
        }
    }
}

@Composable
fun InstagramHomeContent(postsList:List<Post>) {
    LazyColumn{
        items(postsList, itemContent = {
            InstagramListContent(it)
        })
    }
}

@Composable
fun InstagramListContent(it: Post) {
    Column {
        ProfileInfoSection(it)
        PostImage(it)
        PostDescription(it = it)
    }
}

@Composable
fun PostDescription(it: Post) {
    Text(text = it.postDescription, style = MaterialTheme.typography.body1, modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .height(30.dp)
    )
}

@Composable
fun PostImage(it: Post) {
    Icon(rememberImagePainter(data=it.postImage),contentDescription = null, modifier = Modifier
        .fillMaxWidth()
        .height(450.dp))

}

@Composable
fun ProfileInfoSection(it: Post) {
    Row (modifier= Modifier
        .padding(8.dp)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        Icon(rememberImagePainter(data=it.userImage),contentDescription = "ProfileImage", tint = Color.Unspecified)
        Text(text = it.userName, style = MaterialTheme.typography.body1, modifier = Modifier
            .padding(8.dp)
            .weight(1f), textAlign = TextAlign.Left)
        Icon(imageVector = Icons.Default.MoreVert, contentDescription ="null")
    }
}