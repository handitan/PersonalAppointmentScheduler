package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.handitan.personalappointmentscheduler.core.Utilities
import com.handitan.personalappointmentscheduler.data.model.AppointmentData

@Composable
fun AppointmentCard(
    currentAppt:AppointmentData,
    deleteAppt:(AppointmentData)->Unit,
    navigateToUpdateApptScreen:(Long)->Unit
) {
    var showDeleteApptDialog by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp,start = 8.dp, end = 8.dp)
        .clickable {
            navigateToUpdateApptScreen(currentAppt.id)
        },
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = currentAppt.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)
            Text(text = "Location: ${currentAppt.cityName}",
                modifier = Modifier.padding(top = 5.dp))
            Text(text = "Date: ${Utilities.changeToDateString(currentAppt.date)}")
            Text(text = "Time: ${currentAppt.hour.toString() + ":" + currentAppt.minute.toString()}")
//            IconButton(
//                onClick = {
//                    showDeleteApptDialog = true
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Delete,
//                    contentDescription = "Delete Appointment"
//                )
//            }
        }
    }

//    if (showDeleteApptDialog) {
//        DeleteConfirmationAlertDialog(
//            currentAppt,
//            deleteAppt
//        ) {
//            showDeleteApptDialog = false
//        }
//    }
}

//@Composable
//fun DeleteConfirmationAlertDialog(
//    currentAppt:AppointmentData,
//    deleteAppt:(AppointmentData)->Unit,
//    resetShowDeleteApptDialogState:()->Unit
//) {
//    val openDialog = remember {mutableStateOf(true)}
//
//    val handleDialogClosed = {
//        openDialog.value = false
//        resetShowDeleteApptDialogState()
//    }
//
//    if (openDialog.value) {
//        AlertDialog(
//            onDismissRequest = {
//                handleDialogClosed()
//            },
//            title = {
//                Text(text = "Delete")
//            },
//            text = {
//                Text(text = "Continue to delete this appointment?")
//            },
//            confirmButton = {
//                TextButton(onClick = {
//                    openDialog.value = false
//                    deleteAppt(currentAppt)
//                }) {
//                    Text(text = "Confirm")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = {
//                    handleDialogClosed()
//                }) {
//                    Text(text = "Cancel")
//                }
//            })
//    }
//}