package com.example.tasktrek.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskName: String?,
    val taskStartTime: String?,
    val taskEndTime: String?,
    val timeDiff:Long?,
    val taskDate: Date?,
    val taskDescription: String?,
    val colorId: Int?
)
