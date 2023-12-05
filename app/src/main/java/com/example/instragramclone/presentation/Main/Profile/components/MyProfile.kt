package com.example.instragramclone.presentation.Main.Profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyProfile(
    displayName:String,
    bio:String,
    url:String
) {
    Column(
        modifier= Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(text=displayName, fontWeight = FontWeight.Bold, lineHeight = 20.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text=bio, lineHeight = 20.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text=url, color= Color(0xFF3D3D91), lineHeight = 20.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)

    }
}