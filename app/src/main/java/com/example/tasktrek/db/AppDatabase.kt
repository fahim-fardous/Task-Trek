package com.example.tasktrek.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.tasktrek.db.dao.TaskDao
import com.example.tasktrek.models.Task
import java.util.Date

@Database(
    entities = [Task::class], version = 1, exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        operator fun invoke(context: Context) = buildDatabase(context)
        private fun buildDatabase(context: Context): AppDatabase {
            val databaseBuilder =
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase.db"
                )
            return databaseBuilder.build()
        }
    }
}

class DateConverter {
    @TypeConverter
    fun toDatabaseValue(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromDatabaseValue(value: Long?): Date? {
        return value?.let { Date(it) }
    }
}