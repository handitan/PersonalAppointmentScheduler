package com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment.components

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material.icons.sharp.Face
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.handitan.personalappointmentscheduler.R
import com.handitan.personalappointmentscheduler.core.Utilities
import com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment.AddAppointmentViewModel
import com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.components.ApptInputConfirmationDialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentContent(paddingValues: PaddingValues,
                          addApptViewModel: AddAppointmentViewModel,
                          navigateBack:()->Unit

) {

    var showConfirmDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val activity = LocalContext.current as AppCompatActivity
    val outlineTextFieldColor = OutlinedTextFieldDefaults.colors(
        disabledTextColor = MaterialTheme.colorScheme.onSurface,
        disabledBorderColor = MaterialTheme.colorScheme.outline,
        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        //For Icons
        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(280.dp)
                .fillMaxHeight()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.semantics {
                  contentDescription = activity.getString(R.string.appt_description)
                },
                value = addApptViewModel.currentApptViewData.description,
                onValueChange = {
                    addApptViewModel.updateDescription(it)
                },
                maxLines = 5,
                label = {
                    Text(text = activity.getString(R.string.appt_description))
                }
            )


            ExposedDropdownMenuBox(
                modifier = Modifier
                    .semantics {
                        contentDescription = activity.getString(R.string.appt_city)
                    },
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor(),
                    readOnly = true,
                    value = addApptViewModel.currentApptViewData.cityName,
                    onValueChange = {},
                    label = {
                        Text(text = activity.getString(R.string.appt_city))
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) })

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    addApptViewModel.cityList.forEach {
                        DropdownMenuItem(
                            modifier = Modifier.semantics {
                               contentDescription = activity.getString(R.string.appt_city_name)
                            },
                            text = { Text(text = it.name) },
                            onClick = {
                                addApptViewModel.updateCityName(it.name)
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }


            OutlinedTextField(
                value = addApptViewModel.savedDateStr,
                modifier = Modifier
                    .semantics {
                        contentDescription = activity.getString(R.string.appt_date)
                    }
                    .clickable(onClick = {
                        val datePicker = Utilities.createDatePicker(Calendar.getInstance().timeInMillis,activity.getString(R.string.appt_date))

                        datePicker.show(activity.supportFragmentManager, "DATE_PICKER")
                        datePicker.addOnPositiveButtonClickListener {
                            addApptViewModel.updateAppointmentDate(it)
                        }
                    }),
                readOnly = true,
                enabled = false,
                onValueChange = {},
                label = { Text(text = activity.getString(R.string.appt_date)) },
                colors = outlineTextFieldColor,
                trailingIcon = {
                    Icon(imageVector = Icons.Sharp.DateRange,
                        contentDescription = "Date Picker")
                }
            )

            OutlinedTextField(
                value = Utilities.convertTimeToString(addApptViewModel.currentApptViewData.timeHour,addApptViewModel.currentApptViewData.timeMinute),
                modifier = Modifier
                    .semantics {
                        contentDescription = activity.getString(R.string.appt_time)
                    }
                    .clickable(onClick = {
                        val timePicker = Utilities.createTimePicker(
                                            12,
                                            0,
                                            activity.getString(R.string.appt_time)
                                        )

                        timePicker.show(activity.supportFragmentManager, "TIME_PICKER")
                        timePicker.addOnPositiveButtonClickListener {
                            addApptViewModel.updateAppointmentTime(
                                timePicker.hour,
                                timePicker.minute
                            )
                        }
                    }),
                readOnly = true,
                enabled = false,
                onValueChange = {},
                label = { Text(text = activity.getString(R.string.appt_time)) },
                colors = outlineTextFieldColor,
                trailingIcon = {
                    Icon(imageVector = Icons.Sharp.Face,
                        contentDescription = "Time Picker")
                }
            )

            Button(modifier = Modifier
                .padding(top = 10.dp)
                .semantics {
                    contentDescription = activity.getString(R.string.add_new_appt)
                },
                onClick = {
                    if (Utilities.verifyApptFieldsFilledOut(addApptViewModel.currentApptViewData.description,
                            addApptViewModel.currentApptViewData.cityName,
                            addApptViewModel.savedDateStr,
                            addApptViewModel.currentApptViewData.timeHour)) {
                        addApptViewModel.addAppointment()
                        navigateBack()
                    } else {
                        showConfirmDialog = true
                    }
                }) {
                Text(text = activity.getString(R.string.appt_add_confirmation))
            }

            if (showConfirmDialog) {
                ApptInputConfirmationDialog {
                    showConfirmDialog = false
                }
            }
        }
    }
}