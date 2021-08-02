package ru.lihogub.todo.screens.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.lihogub.todo.database.AppDatabase
import ru.lihogub.todo.models.Todo

class TodoListViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao by lazy { AppDatabase.getInstance(application).getTodoDao() }

    private val _todoListLiveData: MutableLiveData<List<Todo>> = MutableLiveData()
    val todoListLiveData: LiveData<List<Todo>> = _todoListLiveData

    fun fetchTodoList(): Disposable = todoDao.getTodoList()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                _todoListLiveData.postValue(it)
            }, {
                it.printStackTrace()
            })

    fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                fetchTodoList()
            }, {
                it.printStackTrace()
            })
    }
}