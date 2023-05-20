package com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAppointmentScreen(
    updateApptViewModel:UpdateAppointmentViewModel = hiltViewModel(),
    apptId:Long,
    navigateBack:()->Unit
) {
    LaunchedEffect(Unit) {
        updateApptViewModel.getAppointment(apptId)
        updateApptViewModel.getCities()
    }

    var expanded by remember { mutableStateOf(false)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Update Appointment")},
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier
                .padding(it)) {
                OutlinedTextField(
                    value = updateApptViewModel.currentApptViewData.description,
                    onValueChange = {
                        Log.d("VALUE CHANGE:", it)
                        updateApptViewModel.updateDescription(it) },
                    label = { Text(text = "Description")}
                )


                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {expanded = !expanded}) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        readOnly = true,
                        value = updateApptViewModel.currentApptViewData.cityName,
                        onValueChange = {},
                        label = { Text(text = "City")},
                        trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)})

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false}) {
                        updateApptViewModel.cityList.forEach {
                            it ->
                            DropdownMenuItem(
                                text = { Text(text = it.name)},
                                onClick = {
                                    updateApptViewModel.updateCityName(it.name)
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }

                Button(onClick = {
                    updateApptViewModel.updateAppointment()
                    navigateBack()
                }) {
                    Text(text = "Update")
                }
            }
        }
    )
}