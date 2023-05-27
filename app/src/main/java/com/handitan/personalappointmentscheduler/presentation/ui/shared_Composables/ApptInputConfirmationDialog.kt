package com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


@Composable
fun ApptInputConfirmationDialog(resetShowDeleteApptDialogState:()->Unit) {
    val openDialog = remember {mutableStateOf(true)}

    val handleDialogClosed = {
        openDialog.value = false
        resetShowDeleteApptDialogState()
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                handleDialogClosed()
            },
            title = {
                Text(text = "Error")
            },
            text = {
                Text(text = "Please fill out all the fields")
            },
            confirmButton = {
                TextButton(onClick = {
                    handleDialogClosed()
                }) {
                    Text(text = "OK")
                }
            }
        )
    }
}