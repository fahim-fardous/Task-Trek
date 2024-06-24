package com.example.tasktrek.screens.task.add

import android.icu.text.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktrek.models.Task
import com.example.tasktrek.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskAddViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {
    private val eventShowLoading = MutableStateFlow(false)

    private val eventMessage = MutableStateFlow<String?>(null)

    private val addSuccess = MutableStateFlow<Boolean?>(null)

    // --------------------------------------------------------------------------

    private val _state = MutableStateFlow(TaskAddViewState())

    val state: StateFlow<TaskAddViewState>
        get() = _state

    // --------------------------------------------------------------------------

    init {
        viewModelScope.launch {
            combine(
                eventShowLoading,
                eventMessage,
                addSuccess
            ) { showLoading, showMessage, addSuccess ->
                TaskAddViewState(
                    loading = showLoading,
                    message = showMessage,
                    addSuccess = addSuccess
                )
            }.collect {
                _state.value = it
            }
        }
    }

    // --------------------------------------------------------------------------

    private fun isValid(
        taskName: String?,
        startTime: String?,
        endTime: String?,
        taskDate: Date?,
        taskDescription: String?,
        color: Int
    ): Boolean {
        if (taskName != null) {
            if (taskName.isBlank()) {
                return false
            }
        }
        if (startTime != null) {
            if (startTime.isBlank()) {
                return false
            }
        }
        if (startTime != null) {
            if (startTime.isBlank()) {
                return false
            }
        }
        if (endTime != null) {
            if (endTime.isBlank()) {
                return false
            }
        }
        if (taskDate.toString().isBlank()) {
            return false
        }
        if (taskDescription != null) {
            if (taskDescription.isBlank()) {
                return false
            }
        }
        if (color.toString().isBlank()) {
            return false
        }
        return true
    }

    fun saveTask(
        taskName: String,
        startTime: String,
        endTime: String,
        taskDate: Date?,
        taskDescription: String,
        color: Int
    ) = viewModelScope.launch {
        if (!isValid(taskName, startTime, endTime, taskDate, taskDescription, color)) {
            return@launch
        }
        addSuccess.value = false
        val task = repository.saveTask(
            Task(
                taskName = taskName,
                taskStartTime = startTime,
                taskEndTime = endTime,
                timeDiff = calculateTimeDifference(startTime, endTime),
                taskDate = taskDate,
                taskDescription = taskDescription,
                colorId = color
            )
        )
        if (task != null) {
            eventMessage.value = "Task added successfully"
            addSuccess.value = true
        } else {
            eventMessage.value = "Unknown error"
        }
    }
}

fun convertTo24HourFormat(time: String): String {
    val inputFormat = SimpleDateFormat("hh:mm a")
    val outputFormat = SimpleDateFormat("HH:mm")
    val date: Date? = inputFormat.parse(time)
    return outputFormat.format(date)
}

fun calculateTimeDifference(startTime: String, endTime: String): Long {
    val timeFormat = SimpleDateFormat("HH:mm")

    val start24Hour = convertTo24HourFormat(startTime)
    val end24Hour = convertTo24HourFormat(endTime)

    val startDate: Date = timeFormat.parse(start24Hour)
    val endDate: Date = timeFormat.parse(end24Hour)

    var differenceInMillis = endDate.time - startDate.time
    if (differenceInMillis < 0) {
        // Add 24 hours in milliseconds if end time is on the next day
        differenceInMillis += 24 * 60 * 60 * 1000
    }

    return differenceInMillis
}

data class TaskAddViewState(
    val loading: Boolean = false,
    val message: String? = null,
    val addSuccess: Boolean? = null
)