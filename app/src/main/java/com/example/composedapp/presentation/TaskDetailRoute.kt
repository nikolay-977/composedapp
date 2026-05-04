package com.example.composedapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composedapp.data.TaskEntity


@Composable
fun TaskDetailRoute(id: String, viewModel: TasksViewModel, onBack: () -> Unit, modifier: Modifier = Modifier) {
    var task by remember { mutableStateOf<TaskEntity?>(value = null) }

    LaunchedEffect(key1 = id) {
        task = viewModel.getTask(id)
    }

    TaskDetailScreen(
        task,
        onBack = onBack,
        onSave = { id, isDone -> viewModel.saveTask(id, isDone); onBack()})
}

@Composable
fun TaskDetailScreen(
    task: TaskEntity?,
    onBack: () -> Unit,
    onSave: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(all = 16.dp)
        ) {
            if (task == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(space = 16.dp)) {
                    Text(text = task.id, style = MaterialTheme.typography.titleLarge)
                    Text(text = task.title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (task.isDone) "Done!" else "Not Done :C",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(space = 12.dp)) {
                        Button(onClick = { onSave(task.id, !task.isDone)}) {
                            Text(text = if (task.isDone) "make undone:C" else "make done:C")
                        }
                        Button(onClick = onBack) {
                            Text(text = "go back")
                        }
                    }

                }
            }
        }
    }
}