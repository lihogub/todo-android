package ru.lihogub.todo.screens.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lihogub.todo.R
import ru.lihogub.todo.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    private lateinit var binding: FragmentTodoListBinding
    private val recyclerView: RecyclerView
        get() = binding.todolistRv
    private val viewModel by viewModels<TodoListViewModel>()
    private val todoListLiveData
        get() = viewModel.todoListLiveData


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchTodoList()

        binding.todoEditCreateButton.setOnClickListener {
            editNote(null)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
            .apply {
                orientation = LinearLayoutManager.VERTICAL
            }

        val todoListAdapter = TodoListAdapter().apply {
            setOnClickCallback { todo -> editNote(todo.id)}
            setOnLongClickCallback { todo ->
                AlertDialog.Builder(requireContext())
                    .setMessage(R.string.delete_todo_question)
                    .setPositiveButton(R.string.delete_todo) { _, _ ->
                        viewModel.deleteTodo(todo)
                    }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }
        }

        todoListLiveData.observe(this) {
            todoListAdapter.setTodoList(it)
        }

        recyclerView.adapter = todoListAdapter

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun editNote(todoId: String?) {
        val action = TodoListFragmentDirections
            .actionTodoListFragmentToTodoEditFragment(todoId)
        view?.findNavController()?.navigate(action)
    }

    companion object {
        fun newInstance() = TodoListFragment()
    }
}