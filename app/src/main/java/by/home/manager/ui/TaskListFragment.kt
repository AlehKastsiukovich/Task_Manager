package by.home.manager.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.home.manager.R
import by.home.manager.databinding.FragmentTaskListBinding
import by.home.manager.ui.binding.FragmentBinding

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val binding by FragmentBinding(FragmentTaskListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
