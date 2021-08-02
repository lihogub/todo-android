package ru.lihogub.todo.screens.todolist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.lihogub.todo.R
import ru.lihogub.todo.models.Todo

class TodoListAdapter() : RecyclerView.Adapter<TodoListItemHolder>() {
    private var todoList: List<Todo> = listOf()
    private var onClickCallback: (Todo) -> Unit = {}
    private var onLongClickCallback: (Todo) -> Unit = {}

    @SuppressLint("NotifyDataSetChanged")
    fun setOnLongClickCallback(onLongClickCallback: (Todo) -> Unit) {
        this.onLongClickCallback = onLongClickCallback
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOnClickCallback(callback: (Todo) -> Unit) {
        onClickCallback = callback
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTodoList(todoList: List<Todo>) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListItemHolder {
        val todoListItem = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_todo_list, parent, false)
        return TodoListItemHolder(todoListItem)
    }

    override fun onBindViewHolder(holder: TodoListItemHolder, position: Int) {
        holder.bind(todoList[position], onClickCallback, onLongClickCallback)
    }

    override fun getItemCount(): Int = todoList.size
}