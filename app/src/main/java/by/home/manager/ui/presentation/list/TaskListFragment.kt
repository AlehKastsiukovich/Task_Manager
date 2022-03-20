package by.home.manager.ui.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.home.manager.R
import by.home.manager.ui.domain.entity.TaskItem
import by.home.manager.ui.presentation.add.TaskListStatus
import by.home.manager.ui.presentation.app.appComponent

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val taskListViewModel: TaskListViewModel by viewModels {
        requireContext().appComponent.viewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            TaskListScreen(
                viewModel = taskListViewModel,
                onButtonClick = { findNavController().navigate(R.id.fragmentSingleTask) }
            )
        }
    }
}

@Composable
fun TaskListScreen(viewModel: TaskListViewModel, onButtonClick: () -> Unit) {
    val viewState by remember(viewModel) { viewModel.state }
        .collectAsState(initial = TaskListStatus.Loading)

    when (viewState) {
        is TaskListStatus.Success -> {
            val data = (viewState as TaskListStatus.Success).data
            TaskList(
                tasks = data,
                onButtonClick = onButtonClick,
                onRemoveButton = { viewModel.removeItem(it) }
            )
        }
        else -> {
        }
    }
}

@Composable
fun TaskList(
    tasks: List<TaskItem>,
    onButtonClick: () -> Unit,
    onRemoveButton: (item: TaskItem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tasks",
                        color = Color.White
                    )
                },
                backgroundColor = Color.Black
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color.Red,
                onClick = { onButtonClick.invoke() },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            )
        }
    ) {
        LazyColumn {
            tasks.forEach {
                item {
                    TaskItemView(item = it, onRemoveButton)
                }
            }
        }
    }
}

@Composable
fun TaskItemView(item: TaskItem, onRemoveButton: (item: TaskItem) -> Unit) {
    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(8.dp)
        ) {
            val (taskTitle, taskDescription, removeIcon) = createRefs()
            Text(
                text = item.title,
                fontSize = 15.sp,
                modifier = Modifier.constrainAs(taskTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(removeIcon.start)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = item.description,
                fontSize = 15.sp,
                modifier = Modifier
                    .constrainAs(taskDescription) {
                        top.linkTo(taskTitle.bottom)
                        end.linkTo(removeIcon.start)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 5.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icon_remove),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(removeIcon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .clickable { onRemoveButton.invoke(item) }
                    .padding(start = 15.dp)
            )
        }
    }
}
