package com.example.tasktrek.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tasktrek.models.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: Task):Long

    @Query("SELECT * FROM tasks")
    suspend fun getAllTask():List<Task>

    @Query("SELECT * FROM tasks WHERE id=:id")
    suspend fun getTask(id:Int):Task
}