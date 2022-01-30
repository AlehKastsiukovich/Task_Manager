package by.home.manager.ui.data.room

import androidx.room.TypeConverter
import by.home.manager.ui.presentation.add.TaskImportance

class RoomConverter {

    @TypeConverter
    fun toStatusInt(value: TaskImportance) = value.ordinal

    @TypeConverter
    fun fromStatus(value: Int) = enumValues<TaskImportance>()[value]
}
