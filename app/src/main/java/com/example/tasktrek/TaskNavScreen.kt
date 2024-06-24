package com.example.tasktrek

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.tasktrek.screens.splash.SplashScreen
import com.example.tasktrek.screens.task.add.TaskAddScreen
import com.example.tasktrek.screens.task.add.TaskAddViewModel
import com.example.tasktrek.screens.task.edit.TaskEditScreen
import com.example.tasktrek.screens.task.list.TaskListScreen
import com.example.tasktrek.screens.task.list.TaskListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Task : Screen("task")
}

sealed class SplashScreen(val route: String) {
    data object Splash : SplashScreen("splash/index")
}

sealed class TaskScreen(val route: String) {
    data object TaskList : TaskScreen("tasks")
    data object TaskAdd : TaskScreen("tasks/add")
    data object TaskEdit : TaskScreen("tasks/{taskId}") {
        const val TASK_ID = "taskId"
    }
}

@Composable
fun TaskNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        addSplashScreen(navController = navController)
        addTasksScreen(navController = navController)
    }
}

private fun NavGraphBuilder.addSplashScreen(
    navController: NavHostController
) {
    navigation(
        route = Screen.Splash.route, startDestination = SplashScreen.Splash.route
    ) {
        composable(SplashScreen.Splash.route) {
            SplashScreen(gotoHomeIndex = {
                navController.navigate(Screen.Task.route) {
                    popUpTo(SplashScreen.Splash.route) {
                        inclusive = true
                    }
                }
            })
        }
    }
}

private fun NavGraphBuilder.addTasksScreen(
    navController: NavHostController
) {
    navigation(route = Screen.Task.route, startDestination = TaskScreen.TaskList.route) {
        composable(TaskScreen.TaskList.route) {
            val viewModel:TaskListViewModel = hiltViewModel()
            TaskListScreen(
                viewModel = viewModel,
                gotoTaskEdit = { taskId ->
                    navController.navigate(
                        TaskScreen.TaskEdit.route.replaceFirst(
                            "{${TaskScreen.TaskEdit.TASK_ID}}", "$taskId"
                        )
                    )
                }, goBack = {
                    navController.popBackStack()
                },
                gotoTaskAdd = {
                    navController.navigate(TaskScreen.TaskAdd.route)
                })
        }
        composable(TaskScreen.TaskAdd.route) {
            val viewModel: TaskAddViewModel = hiltViewModel()
            TaskAddScreen(goBack = {
                navController.popBackStack()
            }, viewModel = viewModel)
        }

        composable(TaskScreen.TaskEdit.route,
            arguments = listOf(navArgument(TaskScreen.TaskEdit.TASK_ID) {
                type = NavType.IntType
            })
        ) {
            TaskEditScreen(taskId = it.arguments?.getInt(TaskScreen.TaskEdit.TASK_ID) ?: 0,
                goBack = {
                    navController.popBackStack()
                })

        }

    }
}