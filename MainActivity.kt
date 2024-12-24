package com.example.todoapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme


import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Task(val title: String, val description: String, val datetime: String)

@Composable
fun TodoApp(modifier: Modifier = Modifier) {

    var taskList by remember { mutableStateOf(listOf<Task>()) }
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        OutlinedTextField(
            value = taskTitle,
            onValueChange = {taskTitle = it},
            label = {Text(" Enter Task Title")},
            modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)
        )

        OutlinedTextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            maxLines = 10,
            label = { Text("Enter Task") },
            modifier = modifier.fillMaxWidth(),shape = RoundedCornerShape(20.dp)
        )

        Button(onClick = {
            if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()) {
                val currentDateTime = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                taskList = taskList + Task(taskTitle,taskDescription,currentDateTime)

                taskTitle = ""
                taskDescription = ""
            }
        }, modifier = modifier.align(Alignment.End))
        { Text("Add") }

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            items(taskList) { task ->
                Card(
                    modifier = modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                )
                {
                    Row(
                        modifier = modifier.padding(16.dp).fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Column (modifier = modifier.weight(1f)){

                            Text(
                                text = "Title: ${task.title}",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = task.description,
                                modifier = modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Created on: ${task.datetime}",
                                modifier = modifier.padding(2.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        IconButton(onClick = { taskList = taskList.filter { it != task } })
                        { Icon(Icons.Default.Delete, contentDescription = "Delete Task") }
                    }

                }

            }

        }


    }
}