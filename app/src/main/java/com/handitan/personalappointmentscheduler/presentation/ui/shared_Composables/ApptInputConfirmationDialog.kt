package com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.components

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.handitan.personalappointmentscheduler.R


@Composable
fun ApptInputConfirmationDialog(resetShowDeleteApptDialogState:()->Unit) {
    val openDialog = remember {mutableStateOf(true)}
    val activity = LocalContext.current as AppCompatActivity

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
                Text(text = activity.getString(R.string.title_error_dlg))
            },
            text = {
                Text(text = activity.getString(R.string.err_msg_error_dlg))
            },
            confirmButton = {
                TextButton(onClick = {
                    handleDialogClosed()
                }) {
                    Text(
                        modifier = Modifier.semantics {
                            contentDescription = activity.getString(R.string.confirm_error_dlg)
                        },
                        text = activity.getString(R.string.confirm_action_error_dlg)
                    )
                }
            }
        )
    }
}