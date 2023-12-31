package com.example.instragramclone.presentation.Main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.instragramclone.presentation.BottomNavigationItem
import com.example.instragramclone.presentation.BottomNavigationMenu

@Composable
fun SearchScreen(navController: NavController) {
    Column(modifier= Modifier.fillMaxSize()) {
        Column(modifier= Modifier.weight(1f)) {
            Text(text = "Search Screen")
        }
        BottomNavigationMenu(selectedItem = BottomNavigationItem.SEARCH, navController = navController)
    }
}