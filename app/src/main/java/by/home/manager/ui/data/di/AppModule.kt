package by.home.manager.ui.data.di

import android.content.Context
import androidx.room.Room
import by.home.manager.ui.data.repository.TaskRepository
import by.home.manager.ui.data.repository.TasksRepositoryImpl
import by.home.manager.ui.data.room.AppDatabase
import by.home.manager.ui.domain.usecase.GeneralTaskUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideTaskRepository(appDatabase: AppDatabase): TaskRepository {
        return TasksRepositoryImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "tasks_database")
            .build()
    }

    @Provides
    fun provideGeneralTaskUseCase(taskRepository: TaskRepository): GeneralTaskUseCase {
        return GeneralTaskUseCase(taskRepository)
    }
}
