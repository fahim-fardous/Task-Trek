package com.example.tasktrek.screens.task.add

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasktrek.components.ColoredBox
import com.example.tasktrek.components.TimePickerDialog
import com.example.tasktrek.ui.theme.border1
import com.example.tasktrek.ui.theme.border2
import com.example.tasktrek.ui.theme.border3
import com.example.tasktrek.ui.theme.colorScheme1
import com.example.tasktrek.ui.theme.colorScheme2
import com.example.tasktrek.ui.theme.colorScheme3
import com.example.tasktrek.utils.extensions.getHumanReadableDate
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun TaskAddScreen(
    goBack: () -> Unit,
    viewModel: TaskAddViewModel,
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    TaskAddScreenSkeleton(
        goBack = goBack,
        addTask = { name, startTime, endTime, date, description, color ->
            viewModel.saveTask(
                name,
                startTime,
                endTime,
                date,
                description,
                color,
            )
        },
    )
}

@Preview
@Composable
private fun TaskAddScreenSkeletonPreview() {
    TaskAddScreenSkeleton()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddScreenSkeleton(
    goBack: () -> Unit = {},
    addTask: (
        name: String,
        startTime: String,
        endTime: String,
        date: Date?,
        description: String,
        color: Int,
    ) -> Unit = { _, _, _, _, _, _ -> },
) {
    val context = LocalContext.current

    var selectedBoxIndex by remember { mutableStateOf(-1) }

    var taskName by remember { mutableStateOf("") }

    var startTime by remember { mutableStateOf("") }

    var endTime by remember { mutableStateOf("") }

    var taskDate by rememberSaveable { mutableStateOf<Date?>(null) }

    var taskDescription by remember { mutableStateOf("") }

    var openDatePickerDialog by remember {
        mutableStateOf(false)
    }

    var openStartTimePickerDialog by remember {
        mutableStateOf(false)
    }

    var openEndTimePickerDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier =
            Modifier
                .imePadding()
                .statusBarsPadding(),
        containerColor = Color.Black,
        topBar = {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Cancel",
                    tint = Color.White,
                    modifier =
                        Modifier.clickable {
                            goBack()
                        },
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Create New Task",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            OutlinedTextField(
                value = taskName,
                onValueChange = { taskName = it },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
                label = {
                    Text(
                        text = "TASK NAME",
                        color = colorScheme1,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                singleLine = true,
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = colorScheme1,
                        unfocusedIndicatorColor = colorScheme1,
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                    ),
                textStyle =
                    TextStyle(
                        color = Color.White,
                    ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .clickable {},
                label = {
                    Text(
                        text = "START TIME",
                        color = colorScheme1,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Alarm,
                        contentDescription = null,
                        modifier = Modifier.clickable { openStartTimePickerDialog = true },
                    )
                },
                singleLine = true,
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = colorScheme1,
                        unfocusedIndicatorColor = colorScheme1,
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                    ),
                readOnly = true,
                textStyle =
                    TextStyle(
                        color = Color.White,
                    ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                label = {
                    Text(
                        text = "END TIME",
                        color = colorScheme1,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Alarm,
                        contentDescription = null,
                        modifier =
                            Modifier.clickable {
                                openEndTimePickerDialog = true
                            },
                    )
                },
                singleLine = true,
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = colorScheme1,
                        unfocusedIndicatorColor = colorScheme1,
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                    ),
                readOnly = true,
                textStyle =
                    TextStyle(
                        color = Color.White,
                    ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = taskDate.getHumanReadableDate(),
                onValueChange = {},
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .clickable {
                            openDatePickerDialog = true
                        },
                label = {
                    Text(
                        text = "DATE",
                        color = colorScheme1,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Outlined.DateRange,
                        contentDescription = null,
                        modifier =
                            Modifier.clickable {
                                openDatePickerDialog = true
                            },
                    )
                },
                singleLine = true,
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = colorScheme1,
                        unfocusedIndicatorColor = colorScheme1,
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                    ),
                readOnly = true,
                textStyle =
                    TextStyle(
                        color = Color.White,
                    ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = taskDescription,
                onValueChange = {
                    if (taskDescription.length < 1000) {
                        taskDescription = it
                    }
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                label = {
                    Text(
                        text = "DESCRIPTION",
                        color = colorScheme1,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = colorScheme1,
                        unfocusedIndicatorColor = colorScheme1,
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                    ),
                textStyle =
                    TextStyle(
                        color = Color.White,
                    ),
                maxLines = 10,
                supportingText = {
                    Text(
                        text = "${taskDescription.length}/1000",
                        color = if (taskDescription.length == 1000) Color(0xFFFF44336) else Color.White,
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )

            Text(
                text = "SELECT A COLOR",
                color = colorScheme1,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp),
            )
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ColoredBox(
                    isSelected = selectedBoxIndex == 0,
                    onClick = { selectedBoxIndex = 0 },
                    color = colorScheme1,
                    borderColor = border1,
                )
                Spacer(modifier = Modifier.width(16.dp))
                ColoredBox(
                    isSelected = selectedBoxIndex == 1,
                    onClick = { selectedBoxIndex = 1 },
                    color = colorScheme2,
                    borderColor = border2,
                )
                Spacer(modifier = Modifier.width(16.dp))
                ColoredBox(
                    isSelected = selectedBoxIndex == 2,
                    onClick = { selectedBoxIndex = 2 },
                    color = colorScheme3,
                    borderColor = border3,
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            addTask(
                                taskName,
                                startTime,
                                endTime,
                                taskDate,
                                taskDescription,
                                selectedBoxIndex,
                            )
                            goBack()
                        }.background(color = colorScheme1)
                        .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "CREATE", style = MaterialTheme.typography.labelLarge)
            }
        }
    }

    // -----------------------------------------------------------------------------------
    // Dialog
    // -----------------------------------------------------------------------------------
    if (openStartTimePickerDialog) {
        val calendar = Calendar.getInstance()
        val timePickerState =
            rememberTimePickerState(
                initialHour = calendar.get(Calendar.HOUR_OF_DAY),
                initialMinute = calendar.get(Calendar.MINUTE),
                is24Hour = false,
            )
        TimePickerDialog(
            onDismissRequest = {
                openStartTimePickerDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    val hour = timePickerState.hour
                    val minute = timePickerState.minute
                    startTime = convert24HourTo12Hour("$hour:$minute")
                    openStartTimePickerDialog = false
                }) {
                    Text(text = "Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openStartTimePickerDialog = false
                }) {
                    Text(text = "Cancel")
                }
            },
            containerColor = colorScheme1,
        ) {
            TimePicker(state = timePickerState)
        }
    }
    if (openEndTimePickerDialog) {
        val calendar = Calendar.getInstance()
        val timePickerState =
            rememberTimePickerState(
                initialHour = calendar.get(Calendar.HOUR_OF_DAY),
                initialMinute = calendar.get(Calendar.MINUTE),
                is24Hour = false,
            )
        TimePickerDialog(
            onDismissRequest = {
                openEndTimePickerDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    val hour = timePickerState.hour
                    val minute = timePickerState.minute
                    val time = convert24HourTo12Hour("$hour:$minute")
                    if (!compareTimes(time, startTime)) {
                        println(compareTimes(time, startTime))
                        Toast
                            .makeText(
                                context,
                                "End time should be after start time",
                                Toast.LENGTH_SHORT,
                            ).show()
                    } else {
                        endTime = convert24HourTo12Hour("$hour:$minute")
                    }
                    openEndTimePickerDialog = false
                }) {
                    Text(text = "Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openEndTimePickerDialog = false
                }) {
                    Text(text = "Cancel")
                }
            },
            containerColor = colorScheme1,
        ) {
            TimePicker(state = timePickerState)
        }
    }
    if (openDatePickerDialog) {
        val initialSelectedDate =
            remember {
                val localCalender = Calendar.getInstance()
                val utcCalender = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                utcCalender.clear()
                utcCalender.set(
                    localCalender.get(Calendar.YEAR),
                    localCalender.get(Calendar.MONTH),
                    localCalender.get(Calendar.DATE),
                )
                utcCalender.timeInMillis
            }

        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis = initialSelectedDate,
                selectableDates =
                    object : SelectableDates {
                        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

                        override fun isSelectableDate(utcTimeMillis: Long): Boolean = utcTimeMillis >= calendar.timeInMillis

                        override fun isSelectableYear(year: Int): Boolean = year >= calendar.get(Calendar.YEAR)
                    },
            )
        val datePickerConfirmButtonEnabled =
            remember {
                derivedStateOf { datePickerState.selectedDateMillis != null }
            }

        DatePickerDialog(onDismissRequest = { openDatePickerDialog = false }, confirmButton = {
            TextButton(
                onClick = {
                    openDatePickerDialog = false

                    datePickerState.selectedDateMillis?.let {
                        taskDate = Date(it)
                    }
                },
                enabled = datePickerConfirmButtonEnabled.value,
            ) {
                Text(text = "OK")
            }
        }, dismissButton = {
            TextButton(onClick = { openDatePickerDialog = false }) {
                Text(text = "Cancel")
            }
        }) {
            DatePicker(state = datePickerState, title = {
                Text(
                    text = "Task Date",
                    Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp),
                )
            })
        }
    }
}

fun convert24HourTo12Hour(time24: String): String {
    // Define the input and output date formats
    val inputFormat = SimpleDateFormat("HH:mm")
    val outputFormat = SimpleDateFormat("hh:mm a")

    // Parse the input time string to a Date object
    val date: Date? = inputFormat.parse(time24)

    // Format the Date object to the desired output format
    return outputFormat.format(date)
}

fun compareTimes(
    time1: String,
    time2: String,
): Boolean {
    val endHour = "${time1[0]}${time1[1]}"
    val startHour = "${time2[0]}${time2[1]}"
    val endMinute = "${time1[3]}${time1[4]}"
    val startMinute = "${time2[3]}${time2[4]}"
    if (endHour > startHour) {
        return true
    } else if (endHour == startHour && endMinute > startMinute) {
        return true
    }
    return false
}
