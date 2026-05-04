package com.example.composedapp.presentation

import com.example.composedapp.data.TaskEntity

data class TaskState(
    val query: String = "",
    val tasks: List<TaskEntity> = emptyList(),
    val isLoading: Boolean = false
)