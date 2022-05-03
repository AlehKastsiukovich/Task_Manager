package by.home.manager.ui.data.repository

import by.home.manager.ui.domain.entity.TaskItem
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks(): Flow<List<TaskItem>>

    suspend fun addTask(task: TaskItem)

    suspend fun removeTask(task: TaskItem)

    suspend fun updateTask(task: TaskItem)
}
