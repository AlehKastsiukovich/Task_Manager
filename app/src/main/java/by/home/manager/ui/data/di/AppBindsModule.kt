package by.home.manager.ui.data.di

import androidx.lifecycle.ViewModel
import by.home.manager.ui.presentation.add.SingleTaskViewModel
import by.home.manager.ui.presentation.list.TaskListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AppBindsModule {

    @Binds
    @[IntoMap ViewModelKey(TaskListViewModel::class)]
    fun provideTaskListViewModel(taskListViewModel: TaskListViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(SingleTaskViewModel::class)]
    fun provideSingleTaskViewModel(singleTaskViewModel: SingleTaskViewModel): ViewModel
}
