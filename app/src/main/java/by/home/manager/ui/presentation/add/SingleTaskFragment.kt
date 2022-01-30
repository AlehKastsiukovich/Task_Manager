package by.home.manager.ui.presentation.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.home.manager.R
import by.home.manager.databinding.FragmentSingleTaskBinding
import by.home.manager.ui.data.util.FragmentBinding
import by.home.manager.ui.presentation.app.appComponent

class SingleTaskFragment : Fragment(R.layout.fragment_single_task) {

    private val binding: FragmentSingleTaskBinding by FragmentBinding(FragmentSingleTaskBinding::bind)

    private val singleTaskViewModel: SingleTaskViewModel by viewModels {
        requireContext().appComponent.viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveTaskButton.setOnClickListener {
            val title = binding.taskNameEdit.text.toString()
            val description = binding.taskDescriptionEdit.text.toString()

            singleTaskViewModel.saveTask(title, description, TaskImportance.HIGH_PRIORITY)
            findNavController().navigate(R.id.fragmentTaskList)
        }
    }
}
