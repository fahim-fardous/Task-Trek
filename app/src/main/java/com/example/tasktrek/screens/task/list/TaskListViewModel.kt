package com.example.tasktrek.screens.task.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktrek.models.Task
import com.example.tasktrek.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    val tasks: StateFlow<List<Task>>
        get() = _tasks

    fun showTaskList() = viewModelScope.launch {
        val tasks = repository.getAllTask()
        if(tasks.isNotEmpty()){
            _tasks.value = tasks
        }
    }
}