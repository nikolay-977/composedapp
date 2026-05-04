package com.example.compose_advanced.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composedapp.data.TaskEntity
import com.example.composedapp.presentation.SearchBar
import com.example.composedapp.presentation.TaskState
import com.example.composedapp.presentation.TasksViewModel

@Composable
fun TasksRoute(
    viewModel: TasksViewModel,
    onTaskClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
){
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.load()
    }
    TasksScreen(state.value, onQueryChanged =  viewModel::onQueryChanged, onTaskClick = onTaskClick, onSettingsClick = onSettingsClick)
}

@Composable
fun TasksScreen(
    state: TaskState,
    onQueryChanged: (String) -> Unit,
    onTaskClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredTasks = state.tasks.filter { it.title.contains( other = state.query, true)}
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding( all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp
            )
        ) {
            TextButton(onClick = onSettingsClick) {
                Text("Settings")
            }
            SearchBar(
                value = state.query,
                onValueChanged = onQueryChanged,
            )
            // isLoading
            // data -> tasks.notEmpty
            // no data -> !isLoading && tasks.isEmpty
            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                filteredTasks.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No data")
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(space = 12.dp),
                    ) {
                        items(items = filteredTasks) {
                            TaskItem(task = it, onClick = { onTaskClick(it.id)})
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: TaskEntity,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp)
    ) {
        Text(text = task.title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = if(task.isDone) "Done!" else "Not Done :C",
            style = MaterialTheme.typography.bodyMedium)
    }
}