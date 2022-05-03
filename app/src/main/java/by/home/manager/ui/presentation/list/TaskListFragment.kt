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
import androidx.compose.material.Checkbox
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
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
import kotlin.random.Random

class TaskListFragment : Fragment() {

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
                viewModel = viewModel
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
    viewModel: TaskListViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tasks",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                backgroundColor = AppColors.pastelBlue
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = AppColors.imperialPurple,
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
                    TaskItemView(
                        item = it,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItemView(
    item: TaskItem,
    viewModel: TaskListViewModel
) {
    val alpha =
        if (item.isFinished) {
            0.3F
        } else {
            1F
        }
    val colorToChoose = Random.nextInt(0, AppColors.colorsList.size)
    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .alpha(alpha),
        elevation = 4.dp,
        backgroundColor = AppColors.colorsList[colorToChoose]
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(12.dp)
        ) {
            val (taskTitle, taskDescription, removeIcon, checkBox) = createRefs()
            CheckBoxCustom(
                modifier = Modifier.constrainAs(checkBox) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
                viewModel = viewModel,
                item
            )
            Text(
                text = item.title,
                fontSize = 17.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                modifier = Modifier.constrainAs(taskTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(checkBox.end)
                    end.linkTo(removeIcon.start)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = item.description,
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Gray,
                modifier = Modifier
                    .constrainAs(taskDescription) {
                        top.linkTo(taskTitle.bottom)
                        end.linkTo(removeIcon.start)
                        start.linkTo(checkBox.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 5.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icon_recycler_grey),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(removeIcon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .clickable { viewModel.removeItem(item) }
                    .padding(start = 15.dp)
            )
        }
    }
}

@Composable
private fun CheckBoxCustom(
    modifier: Modifier,
    viewModel: TaskListViewModel,
    item: TaskItem
) {
    Checkbox(
        modifier = modifier,
        checked = item.isFinished,
        onCheckedChange = {
            viewModel.updateTask(
                TaskItem(
                    item.title,
                    item.description,
                    item.priority,
                    it
                )
            )
        }
    )
}

object AppColors {
    val crystal = Color(0xFFACDDDE)
    val aeroBlue = Color(0xFFCAF1DE)
    val nyanZa = Color(0xFFE1F8DC)
    val cornSilk = Color(0xFFFEF8DD)
    val bisque = Color(0xFFFFE7C7)
    val sandyTan = Color(0xFFF7D8BA)
    val blueGreen = Color(0xFF25BABF)
    val pastelBlue = Color(0xFFA6C9D4)
    val imperialPurple = Color(0xFF610139)

    val colorsList = listOf(crystal, aeroBlue, nyanZa, cornSilk, bisque, sandyTan)
}
