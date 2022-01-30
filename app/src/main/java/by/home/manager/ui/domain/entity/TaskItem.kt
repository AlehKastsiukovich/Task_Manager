package by.home.manager.ui.domain.entity

import by.home.manager.ui.presentation.add.TaskImportance

data class TaskItem(
    val title: String,
    val description: String,
    val priority: TaskImportance,
    val isFinished: Boolean
)
