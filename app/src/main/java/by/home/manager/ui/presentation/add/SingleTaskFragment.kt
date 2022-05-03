package by.home.manager.ui.presentation.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.home.manager.R
import by.home.manager.ui.presentation.app.appComponent
import by.home.manager.ui.presentation.list.AppColors

class SingleTaskFragment : Fragment() {

    private val singleTaskViewModel: SingleTaskViewModel by viewModels {
        requireContext().appComponent.viewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AddTaskScreen(
                    singleTaskViewModel
                ) { findNavController().navigate(R.id.fragmentTaskList) }
            }
        }
    }
}

@Composable
fun AddTaskScreen(
    singleTaskViewModel: SingleTaskViewModel,
    onTaskSave: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        topBar = {
            TopAppBar(
                backgroundColor = AppColors.pastelBlue,
                title = {
                    Text(
                        text = "Edit task",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
            )
        }
    ) {
        TaskTitle(singleTaskViewModel, onTaskSave)
    }
}

@Composable
fun TaskTitle(
    singleTaskViewModel: SingleTaskViewModel,
    onTaskSave: () -> Unit
) {
    val titleTextState = remember { mutableStateOf("") }
    val descriptionTextState = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text(
                text = "Task name",
                fontSize = 17.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.DarkGray
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = titleTextState.value,
                onValueChange = {
                    titleTextState.value = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.DarkGray,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Text(
                text = "Task description",
                fontSize = 17.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.DarkGray
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = descriptionTextState.value,
                onValueChange = {
                    descriptionTextState.value = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.DarkGray,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
            backgroundColor = AppColors.imperialPurple,
            text = { Text(text = "Save", color = Color.White) },
            onClick = {
                singleTaskViewModel.saveTask(
                    titleTextState.value,
                    descriptionTextState.value,
                    TaskImportance.HIGH_PRIORITY
                )
                onTaskSave()
            }
        )
    }
}
