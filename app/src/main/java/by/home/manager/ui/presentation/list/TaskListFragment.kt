package by.home.manager.ui.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.home.manager.R
import by.home.manager.databinding.FragmentTaskListBinding
import by.home.manager.ui.data.util.BindingInterface
import by.home.manager.ui.data.util.FragmentBinding
import by.home.manager.ui.data.util.GenericRecyclerViewAdapter
import by.home.manager.ui.domain.entity.TaskItem
import by.home.manager.ui.presentation.add.TaskListStatus
import by.home.manager.ui.presentation.app.appComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val binding by FragmentBinding(FragmentTaskListBinding::bind)

    private val taskListViewModel: TaskListViewModel by viewModels {
        requireContext().appComponent.viewModelFactory()
    }

    private val taskItemBinder = object : BindingInterface<TaskItem> {
        override fun bind(item: TaskItem, view: View) {
            view.findViewById<TextView>(R.id.task_title_text).text = item.title
            view.findViewById<TextView>(R.id.task_description_text).text = item.description
            view.findViewById<Button>(R.id.remove_button).setOnClickListener {
                taskListViewModel.removeItem(item)
            }
        }
    }

    private val genericAdapter = GenericRecyclerViewAdapter(
        R.layout.task_item,
        taskItemBinder
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAddTaskButton()
        setUpAdapter()
        observeTasks()
    }

    private fun observeTasks() {
        taskListViewModel.state.onEach {
            when (it) {
                is TaskListStatus.Success -> {
                    genericAdapter.itemList = it.data
                }
                else -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setUpAddTaskButton() {
        binding.createNewTaskButton.setOnClickListener {
            findNavController().navigate(R.id.fragmentSingleTask)
        }
    }

    private fun setUpAdapter() {
        binding.taskListRecyclerView.apply {
            adapter = genericAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
