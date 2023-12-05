package com.example.instragramclone.presentation.Main.Profile.components

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.instragramclone.domain.model.TabModel

@Composable
fun TabView(
    modifier: Modifier = Modifier,
    tabModels: List<TabModel>,
    onTabSelected: (selectedIndex:Int) -> Unit
){
    var selectedTabIndex by remember { mutableStateOf(0) }
    TabRow(
        selectedTabIndex = selectedTabIndex,
        //backgroundColor Color.Transparent,
        contentColor= Color.Black,
    ){
        tabModels.forEachIndexed{index,item ->
            Tab(
                selected=selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color(0xFF777777)
            )
        }
    }
}