package com.bemos.familyohesion.presentation.features.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.bemos.familyohesion.R
import com.bemos.familyohesion.core.ui.theme.Gray
import com.bemos.familyohesion.core.ui.theme.Green
import com.bemos.familyohesion.core.ui.theme.GreenAlpha03
import com.bemos.familyohesion.core.ui.theme.Red
import com.bemos.familyohesion.core.ui.theme.RedAlpha
import com.bemos.familyohesion.core.ui.theme.RedAlpha03
import com.bemos.familyohesion.domain.models.Task
import com.bemos.familyohesion.presentation.features.tasks.utils.ui.TaskCardItem
import com.bemos.familyohesion.shared.utils.ui.button.CustomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onFinishTask: (Task) -> Unit,
    onDismissTask: (Task) -> Unit,
    isRefresh: () -> Unit
) {
    var bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var currentTask by remember {
        mutableStateOf<Task>(Task())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray)
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Задания на проверку",
                fontSize = 26.sp,
            )

            Spacer(Modifier.height(10.dp))

            LazyColumn {
                items(items = tasks) {
                    TaskCardItem(
                        task = it,
                        onCardClick = { curTask ->
                            showBottomSheet = true
                            currentTask = curTask
                        }
                    )
                }
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {
                showBottomSheet = false
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentTask.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Баллы за задание: " + currentTask.pointsToAdd.toString() + " XP",
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(20.dp))
                AsyncImage(
                    modifier = Modifier.size(350.dp),
                    model = currentTask.imageUrl,
                    contentDescription = null
                )
                Spacer(Modifier.height(30.dp))
                if (!currentTask.isApproved) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(1f),
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(width = 1.dp, color = Color.Green),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GreenAlpha03
                            ),
                            onClick = {
                                onFinishTask(currentTask)
                                showBottomSheet = false
                            },
                        ) {
                            Icon(
                                modifier = Modifier.size(54.dp),
                                painter = painterResource(
                                    id = R.drawable.round_add_task_24
                                ),
                                contentDescription = null,
                                tint = Green
                            )
                        }
                        Spacer(Modifier.width(5.dp))
                        Button(
                            modifier = Modifier
                                .weight(1f),
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(width = 1.dp, color = Color.Red),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RedAlpha03
                            ),
                            onClick = {
                                onDismissTask(currentTask)
                                showBottomSheet = false
                            },
                        ) {
                            Icon(
                                modifier = Modifier.size(54.dp),
                                painter = painterResource(
                                    id = R.drawable.baseline_block_flipped_24
                                ),
                                contentDescription = null,
                                tint = Red
                            )
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                end = 20.dp,
                bottom = 20.dp
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        IconButton(
            modifier = Modifier.size(64.dp),
            onClick = {
                isRefresh()
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Red
            )
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                painter = painterResource(R.drawable.round_autorenew_24),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun TasksContentPreview() {
    TasksContent(
        tasks = listOf(
            Task(
                id = "",
                imageUrl = "",
                familyId = "",
                userId = "",
                pointsToAdd = 0,
                name = "Приготовление супа",
                isApproved = false
            )
        ),
        onFinishTask = {},
        onDismissTask = {},
        isRefresh = {}
    )
}