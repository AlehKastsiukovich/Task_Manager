package by.home.manager.ui.domain.usecase

import by.home.manager.ui.data.repository.TaskRepository
import by.home.manager.ui.domain.entity.TaskItem
import kotlinx.coroutines.flow.Flow

class GeneralTaskUseCase(private val repository: TaskRepository) {

    fun getAllTask(): Flow<List<TaskItem>> = repository.getAllTasks()

    suspend fun addTask(taskItem: TaskItem) = repository.addTask(taskItem)

    suspend fun removeTask(taskItem: TaskItem) = repository.removeTask(taskItem)

    suspend fun updateTask(taskItem: TaskItem) = repository.updateTask(taskItem)
}
