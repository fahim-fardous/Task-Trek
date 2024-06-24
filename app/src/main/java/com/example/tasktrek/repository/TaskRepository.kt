package com.example.tasktrek.repository

import com.example.tasktrek.db.AppDatabase
import com.example.tasktrek.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun saveTask(task: Task) = withContext(Dispatchers.IO) {
        db.taskDao().saveTask(task)
    }

    suspend fun getTask(id:Int) = withContext(Dispatchers.IO){
        db.taskDao().getTask(id)
    }

    suspend fun getAllTask() = withContext(Dispatchers.IO){
        db.taskDao().getAllTask()
    }
}