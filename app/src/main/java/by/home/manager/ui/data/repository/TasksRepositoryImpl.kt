package by.home.manager.ui.data.repository

import by.home.manager.ui.data.entity.Task
import by.home.manager.ui.data.room.AppDatabase
import by.home.manager.ui.domain.entity.TaskItem
import by.home.manager.ui.presentation.add.TaskImportance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor (
    private val appDatabase: AppDatabase
) : TaskRepository {

    override fun getAllTasks(): Flow<List<TaskItem>> {
        return appDatabase.taskDao().getAllTasks().map {
            it.map { task ->
                taskToTaskItem(task)
            }
        }
    }

    override suspend fun addTask(task: TaskItem) {
        appDatabase.taskDao().addTask(taskItemToTask(task))
    }

    override suspend fun removeTask(task: TaskItem) {
        appDatabase.taskDao().removeTask(taskItemToTask(task))
    }

    override suspend fun updateTask(task: TaskItem) {
        appDatabase.taskDao().updateTask(taskItemToTask(task))
    }
}

fun taskItemToTask(taskItem: TaskItem): Task = Task(
    title = taskItem.title,
    description = taskItem.description,
    priority = taskItem.priority.ordinal,
    isFinished = taskItem.isFinished
)

fun taskToTaskItem(task: Task): TaskItem = TaskItem(
    title = task.title,
    description = task.description,
    priority = TaskImportance.values()[task.priority],
    isFinished = task.isFinished
)
