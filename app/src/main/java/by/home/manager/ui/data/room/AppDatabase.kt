package by.home.manager.ui.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import by.home.manager.ui.data.entity.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
