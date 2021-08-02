package ru.lihogub.todo.screens.todoedit

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import ru.lihogub.todo.R
import ru.lihogub.todo.databinding.FragmentTodoEditBinding

class TodoEditFragment : Fragment(R.layout.fragment_todo_edit) {
    private val viewModel by viewModels<TodoEditViewModel>()
    private lateinit var binding: FragmentTodoEditBinding
    private val args: TodoEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        println(args.todoId)

        args.todoId
            ?.let { viewModel.fetchData(it) }
            ?: viewModel.initTodo()

        binding.todoEditTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.todoLiveData.value?.title = text.toString()
        }

        binding.todoEditDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.todoLiveData.value?.description = text.toString()
        }

        viewModel.todoLiveData.observe(this) {
            binding.todoEditTitle.text = Editable.Factory.getInstance().newEditable(it.title)
            binding.todoEditDescription.text = Editable.Factory.getInstance().newEditable(it.description)

        }

        binding.todoEditCancelButton.setOnClickListener {
            goBack()
        }

        binding.todoEditSaveButton.setOnClickListener {
            viewModel.saveTodo()
            goBack()
        }
    }

    private fun goBack() {
        val action = TodoEditFragmentDirections
            .actionTodoEditFragmentToTodoListFragment()
        view?.findNavController()?.navigate(action)
    }

    companion object {
        fun newInstance() = TodoEditFragment()
    }
}