package com.example.instragramclone.presentation.Main.Profile.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostsSection(
    posts: List<Painter>,
    modifier: Modifier = Modifier
){
    //LazyVerticalGrid(columns = GridCells.Fixed(3),modifier = modifier.scale(1.01f))
    LazyVerticalGrid(columns = GridCells.Fixed(3),modifier = modifier.scale(1.01f)){
        items(posts.size){
            Image(
                painter=posts[it],
                contentDescription = "Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1f)
                    .border(width = 2.dp, color = Color.White)
            )
        }
    }
}