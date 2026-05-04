package com.example.composedapp.data

data class TaskEntity(
    var id: String,
    var title: String,
    var  isDone: Boolean = false
)