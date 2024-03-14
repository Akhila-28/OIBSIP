package com.example.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import com.example.todoapplication.ui.theme.ToDoApplicationTheme

data class Task(val id: Int, val title: String, val description: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoApplicationTheme {
                val tasks = remember { mutableStateListOf<Task>() }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        TaskList(tasks)
                        AddTask(onAddTask = { task ->
                            tasks.add(task)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun TaskList(tasks: List<Task>) {
    LazyColumn {
        items(tasks.size) { index ->
            val task = tasks[index]
            Text(text = task.title)
        }
    }
}

@Composable
fun AddTask(onAddTask: (Task) -> Unit) {
    val titleState = remember { mutableStateOf(TextFieldValue()) }
    val descriptionState = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current

    Column {
        TextField(
            value = titleState.value,
            onValueChange = { titleState.value = it },
            label = { Text("Title") }
        )
        TextField(
            value = descriptionState.value,
            onValueChange = { descriptionState.value = it },
            label = { Text("Description") }
        )
        Button(onClick = {
            val newTask = Task(
                id = System.currentTimeMillis().toInt(), // Generate unique ID
                title = titleState.value.text,
                description = descriptionState.value.text
            )
            onAddTask(newTask)
            // Clear text fields
            titleState.value = TextFieldValue()
            descriptionState.value = TextFieldValue()
        }) {
            Text("Add Task")
        }
    }
}
