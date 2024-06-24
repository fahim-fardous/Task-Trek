package com.example.tasktrek.screens.task.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasktrek.models.Task
import com.example.tasktrek.screens.task.list.elements.TaskListItem
import com.example.tasktrek.ui.theme.colorScheme3

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    gotoTaskEdit: (Int) -> Unit,
    goBack: () -> Unit,
    gotoTaskAdd: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.showTaskList()
    }
    TaskListScreenSkeleton(
        gotoTaskEdit = gotoTaskEdit,
        gotoTaskAdd = gotoTaskAdd,
        taskList = tasks
    )
}

@Preview
@Composable
private fun TaskListScreenSkeletonPreview() {
    TaskListScreenSkeleton()
}

@Composable
fun TaskListScreenSkeleton(
    gotoTaskEdit: (Int) -> Unit = {},
    gotoTaskAdd: () -> Unit = {},
    taskList: List<Task> = emptyList()
) {
    println(taskList)
    Scaffold(modifier = Modifier
        .imePadding()
        .statusBarsPadding(),
        containerColor = Color.Black,
        topBar = {
            Text(
                text = "MY TASKS",
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineLarge
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            Box(modifier = Modifier
                .size(50.dp)
                .clickable { gotoTaskAdd() }
                .background(Color.White, shape = RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.Add, contentDescription = "Add task")
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier, contentPadding = PaddingValues(
                    top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp
                )
            ) {
                items(taskList) { task ->
                    TaskListItem(
                        taskName = task.taskName!!,
                        startTime = task.taskStartTime!!,
                        endTime = task.taskEndTime!!,
                        timeDiff = task.timeDiff!!,
                        date = task.taskDate.toString(),
                        description = task.taskDescription!!,
                        colorId = task.colorId!!
                    )
                }

            }

        }

    }
}