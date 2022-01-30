package by.home.manager.ui.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.home.manager.ui.domain.entity.TaskItem
import by.home.manager.ui.domain.usecase.GeneralTaskUseCase
import by.home.manager.ui.presentation.add.TaskListStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskListViewModel @Inject constructor(
    private val useCase: GeneralTaskUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<TaskListStatus> = MutableStateFlow(TaskListStatus.Loading)
    val state = _state.asStateFlow()

    init {
        _state.value = TaskListStatus.Loading
        viewModelScope.launch {
            try {
                useCase.getAllTask().collect {
                    _state.value = TaskListStatus.Success(it)
                }
            } catch (exception: Exception) {
                exception.message?.let {
                    _state.value = TaskListStatus.Error(it)
                }
            }
        }
    }

    fun removeItem(taskItem: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.removeTask(taskItem)
        }
    }
}
