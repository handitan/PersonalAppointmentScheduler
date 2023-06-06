package com.handitan.personalappointmentscheduler.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.handitan.personalappointmentscheduler.core.Constants

@Composable
fun FakeCompose() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.testTag(Constants.NOAPPOINTMENT_TESTTAG),text="TEST 123")
    }
}