package com.example.tasktrek.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.tasktrek.TaskNavHost

@Composable
fun TaskMainScreen() {
    TaskMainScreenSkeleton()
}

@Preview
@Composable
private fun TaskMainScreenSkeletonPreview() {
    TaskMainScreenSkeleton()
}

@Composable
fun TaskMainScreenSkeleton() {
    val navController = rememberNavController()
    TaskNavHost(
        navController = navController
    )

}