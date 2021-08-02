package ru.lihogub.todo.screens.todolist

import android.view.View
import android.widget.TextView
import androidx.core.text.trimmedLength
import androidx.recyclerview.widget.RecyclerView
import ru.lihogub.todo.R
import ru.lihogub.todo.models.Todo
import java.text.SimpleDateFormat

class TodoListItemHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(todo: Todo, onClickAction: (Todo) -> Unit, onLongClickCallback: (Todo) -> Unit) {
        view.findViewById<TextView>(R.id.todo_title).text =
            if (todo.title.length < 50)
                todo.title
            else
                "${todo.title.take(50)}..."

        view.findViewById<TextView>(R.id.todo_description).text =
            if (todo.description.length < 100)
                todo.description
            else
                "${todo.description.take(100)}..."

        view.findViewById<TextView>(R.id.todo_updated).text = SimpleDateFormat
            .getDateTimeInstance()
            .format(todo.updated)

        view.setOnClickListener {
            onClickAction(todo)
        }

        view.setOnLongClickListener {
            onLongClickCallback(todo)
            true
        }
    }
}