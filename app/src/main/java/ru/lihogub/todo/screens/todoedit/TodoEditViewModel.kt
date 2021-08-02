package ru.lihogub.todo.screens.todoedit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.lihogub.todo.database.AppDatabase
import ru.lihogub.todo.models.Todo
import java.util.*

class TodoEditViewModel(application: Application) : AndroidViewModel(application) {
    val todoLiveData = MutableLiveData<Todo>()
    private val todoDao by lazy { AppDatabase.getInstance(application).getTodoDao() }

    fun fetchData(todoId: String) {
        todoDao.getTodoById(todoId)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                println("fetched $it")
                todoLiveData.postValue(it)
            },{
                it.printStackTrace()
            })

    }

    fun saveTodo() {
        todoLiveData.value?.let {
            it.updated = Date().time
            todoDao.insertTodo(it)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({
                           println("saved")
                }, { result ->
                    result.printStackTrace()
                })
        }
    }

    fun initTodo() {
        todoLiveData.postValue(Todo(UUID.randomUUID().toString(), "", "", Date().time, false))
    }
}