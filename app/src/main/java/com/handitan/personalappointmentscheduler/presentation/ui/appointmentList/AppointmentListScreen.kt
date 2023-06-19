package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.handitan.personalappointmentscheduler.R
import com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.components.AppointmentCard
import com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.components.SwipeToDeleteBackground

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppointmentListScreen(
    appointmentListViewModel: AppointmentListViewModel = hiltViewModel(),
    navigateToUpdateApptScreen:(Long)->Unit,
    navigateToAddApptScreen:()->Unit
) {

    val activity = LocalContext.current as AppCompatActivity

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = activity.getString(R.string.appt_main_title))},
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.semantics {
                      contentDescription = activity.getString(R.string.create_new_appt)
                },
                onClick = {}) {
                IconButton(
                    onClick = {
                        navigateToAddApptScreen()
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        },
        content = { paddingValue ->
            val apptListSateFlow = appointmentListViewModel.apptListStateFlow.collectAsStateWithLifecycle()

            if (apptListSateFlow.value.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(paddingValue)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(modifier = Modifier.semantics {
                        contentDescription = activity.getString(R.string.zero_appt)
                    },
                        text = activity.getString(R.string.no_appt_msg)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValue)
                ) {
                    items(apptListSateFlow.value,
                        key = { it.id }) { it ->
                        val currentAppt by rememberUpdatedState(newValue = it)
                        val dismissState = rememberDismissState(
                            confirmValueChange = {
                                if (it.equals(DismissValue.DismissedToStart)) {
                                    appointmentListViewModel.deleteAppointment(currentAppt)
                                    true
                                } else false
                            }
                        )

                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier
                                .padding(vertical = 1.dp)
                                .animateItemPlacement(),
                            background = {
                                SwipeToDeleteBackground(dismissState)
                            },
                            dismissContent = {
                                AppointmentCard(
                                    currentAppt,
                                    navigateToUpdateApptScreen
                                )
                            },
                            directions = setOf(
                                DismissDirection.EndToStart
                            )
                        )
                    }
                }
            }
        }
    )
}