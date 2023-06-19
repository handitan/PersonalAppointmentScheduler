package com.handitan.personalappointmentscheduler.presentation.ui.appointmentList

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.handitan.personalappointmentscheduler.MainActivity
import com.handitan.personalappointmentscheduler.R
import com.handitan.personalappointmentscheduler.data.di.AppModule
import com.handitan.personalappointmentscheduler.navigation.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AppointmentListScreenTest {

    private val instrumentationContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule(order=0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order=1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.AppointmentListScreen.route
            ) {
                composable(route = Screen.AppointmentListScreen.route) {
                    AppointmentListScreen(
                        navigateToUpdateApptScreen = { apptId ->
                            navController.navigate(route = Screen.UpdateAppointmentScreen.route + "/$apptId")
                        },
                        navigateToAddApptScreen = {
                            navController.navigate(route = Screen.AddAppointmentScreen.route)
                        }
                    )
                }
            }
        }
    }

    @Test
    fun mainScreen_NoAppointment_isVisible() {
        composeTestRule.onNodeWithContentDescription(instrumentationContext.resources.getString(R.string.no_appt_msg)).assertIsDisplayed()
    }

}