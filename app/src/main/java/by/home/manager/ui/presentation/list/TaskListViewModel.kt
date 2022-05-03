package by.home.manager.ui.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.home.manager.ui.domain.entity.TaskItem
import by.home.manager.ui.domain.usecase.GeneralTaskUseCase
import by.home.manager.ui.presentation.add.TaskListStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskListViewModel @Inject constructor(
    private val useCase: GeneralTaskUseCase
) : ViewModel() {

    val state: Flow<TaskListStatus> = flow {
        try {
            useCase.getAllTask().collect {
                emit(TaskListStatus.Success(it))
            }
        } catch (e: Exception) {
            emit(TaskListStatus.Error(e.message ?: "error"))
        }
    }

    fun removeItem(taskItem: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.removeTask(taskItem)
        }
    }

    fun addTask(item: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addTask(item)
        }
    }

    fun updateTask(item: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateTask(item)
        }
    }
}
