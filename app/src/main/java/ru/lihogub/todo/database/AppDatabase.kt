package ru.lihogub.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.lihogub.todo.models.Todo

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTodoDao(): TodoDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "todo").build()
            }
            return instance as AppDatabase
        }
    }
}