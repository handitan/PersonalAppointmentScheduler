package com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment.components

import android.util.Log
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.handitan.personalappointmentscheduler.core.Utilities
import com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment.AddAppointmentViewModel
import com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.components.ApptInputConfirmationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentContent(paddingValues: PaddingValues,
                          addApptViewModel: AddAppointmentViewModel,
                          navigateBack:()->Unit

) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val activity = LocalContext.current as AppCompatActivity

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
                value = addApptViewModel.currentApptViewData.description,
                onValueChange = {
                    Log.d("VALUE CHANGE:", it)
                    addApptViewModel.updateDescription(it)
                },
                maxLines = 5,
                label = { Text(text = "Description") }
            )


            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = addApptViewModel.currentApptViewData.cityName,
                    onValueChange = {},
                    label = { Text(text = "City") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) })

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    addApptViewModel.cityList.forEach { it ->
                        DropdownMenuItem(
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
                readOnly = true,
                onValueChange = {
                    Log.d("VALUE CHANGE:", it)
                },
                label = { Text(text = "Appointment Date") },
                trailingIcon = {
                    Icon(imageVector = Icons.Sharp.DateRange,
                        contentDescription = "Date Picker",
                        modifier = Modifier.clickable {
                            val datePicker = MaterialDatePicker
                                .Builder
                                .datePicker()
                                .setTitleText("Appointment Date")
                                .build()

                            datePicker.show(activity.supportFragmentManager, "DATE_PICKER")
                            datePicker.addOnPositiveButtonClickListener {
                                addApptViewModel.updateAppointmentDate(it)
                                //Log.i("VALUE CHANGE", date)
                            }
                        })
                }
            )

            OutlinedTextField(
                value = if (addApptViewModel.currentApptViewData.timeHour == 0) "" else addApptViewModel.currentApptViewData.timeHour.toString() + ":" + addApptViewModel.currentApptViewData.timeMinute.toString(),
                readOnly = true,
                onValueChange = {
                    Log.d("VALUE CHANGE:", it)
                },
                label = { Text(text = "Appointment Time") },
                trailingIcon = {
                    Icon(imageVector = Icons.Sharp.Face,
                        contentDescription = "Time Picker",
                        modifier = Modifier.clickable {
                            val timePicker = MaterialTimePicker
                                .Builder()
                                .setTitleText("Appointment Time")
                                .build()

                            timePicker.show(activity.supportFragmentManager, "TIME_PICKER")
                            timePicker.addOnPositiveButtonClickListener {
                                addApptViewModel.updateAppointmentTime(
                                    timePicker.hour,
                                    timePicker.minute
                                )
                            }
                        })
                }
            )

            Button(modifier = Modifier
                .padding(top = 10.dp),
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
                Text(text = "Add")
            }

            if (showConfirmDialog) {
                //TODO issue when opening the dialog 2nd time
                ApptInputConfirmationDialog() {
                    showConfirmDialog = false
                }
            }
        }
    }
}