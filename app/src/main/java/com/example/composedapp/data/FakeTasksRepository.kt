package com.example.composedapp.data

import kotlinx.coroutines.delay

class FakeTasksRepository {

    private val tasks = mutableListOf(
        TaskEntity("1", "Review code"),
        TaskEntity("2", "Learn Compose"),
        TaskEntity("3", "Make some coffee"),
        TaskEntity("4", "Go to bed"),
    )

    suspend fun getTasks(): List<TaskEntity> {
        delay(500)
        return tasks
    }

    suspend fun getTask(id: String): TaskEntity? {
        delay(500)
        return tasks.firstOrNull { it.id == id }
    }

    suspend fun toggleDone(id: String, isDone: Boolean): TaskEntity? {
        delay(500)
        val index = tasks.indexOfFirst { it.id == id }
        if (index < 0) return null

        val updated = tasks[index].copy(isDone = isDone)
        tasks[index] = updated
        return updated
    }
}