package ru.lihogub.todo.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.lihogub.todo.models.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo ORDER BY updated DESC")
    fun getTodoList(): Single<List<Todo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodoById(id: String): Single<Todo>

    @Insert(onConflict = REPLACE)
    fun insertTodo(todo: Todo): Completable

    @Delete
    fun deleteTodo(todo: Todo): Completable
}