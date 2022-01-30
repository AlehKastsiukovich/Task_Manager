package by.home.manager.ui.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.home.manager.ui.domain.entity.TaskItem
import by.home.manager.ui.domain.usecase.GeneralTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SingleTaskViewModel @Inject constructor(
    private val useCase: GeneralTaskUseCase
) : ViewModel() {

    fun saveTask(title: String, description: String, priority: TaskImportance) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addTask(TaskItem(title, description, priority, false))
        }
    }
}

sealed class TaskListStatus {
    object Loading : TaskListStatus()
    data class Error(val text: String) : TaskListStatus()
    data class Success(val data: List<TaskItem>) : TaskListStatus()
}

enum class TaskImportance {
    HIGH_PRIORITY,
    MID_PRIORITY,
    LOW_PRIORITY
}
