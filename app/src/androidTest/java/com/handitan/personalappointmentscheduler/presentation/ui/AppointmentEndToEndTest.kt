package com.handitan.personalappointmentscheduler.presentation.ui

import com.handitan.personalappointmentscheduler.R
import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.handitan.personalappointmentscheduler.MainActivity
import com.handitan.personalappointmentscheduler.data.di.AppModule
import com.handitan.personalappointmentscheduler.navigation.Screen
import com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment.AddAppointmentScreen
import com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.AppointmentListScreen
import com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.UpdateAppointmentScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


@HiltAndroidTest
@UninstallModules(AppModule::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppointmentEndToEndTest {

    private val testDescription = "Test Content 123456"
    // Context of the app under test.
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

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
                composable(
                    route = Screen.AddAppointmentScreen.route
                ) {
                    AddAppointmentScreen(
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable(
                    route = Screen.UpdateAppointmentScreen.route + "/{apptId}",
                    arguments = listOf(
                        navArgument("apptId") {
                            type = NavType.LongType
                        }
                    )
                ) {
                    val appointmentId = it.arguments?.getLong("apptId") ?: 1
                    UpdateAppointmentScreen(
                        apptId = appointmentId,
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }

    @Test
    fun test1_NoAppointmentIsVisible() {
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.zero_appt)).assertIsDisplayed()
    }

    @Test
    fun test2_addApptWithoutAllFieldsFilledOut_checkErrorDisplayOnConfirm() {
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.create_new_appt)).performClick()
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.add_new_appt)).performClick()
        composeTestRule.onNodeWithText(appContext.resources.getString(R.string.err_msg_error_dlg)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.confirm_error_dlg)).performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test3_AddNewAppt_checkNewAppt() {
        val testCityName = "Dallas"
        val currentDateTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("E, LLL dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()//TimeZone.getTimeZone("UTC")
        }

        val testCurrDateStr = sdf.format(currentDateTime)
        val sdf2 = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()//TimeZone.getTimeZone("UTC")
        }
        val testCurrentDateApptFormatted = sdf2.format(currentDateTime)
        val testCurrTimeApptFormatted = "6:30 PM"

        //composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.zero_appt)).assertIsDisplayed()

        // Add btn to lead add appointment screen
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.create_new_appt)).performClick()

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_description)).performTextInput(testDescription)
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_city)).performClick()
        composeTestRule.onNodeWithText(testCityName).performClick()

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_date)).performClick()

        onView(withId(com.google.android.material.R.id.date_picker_actions))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )


        //You can see The View Hierarchy to see what it really looks like
        //If it's today's date, it will have a prefix word "Today ."
        onView(withContentDescription("Today $testCurrDateStr"))
            .perform(click())

        onView(withId(com.google.android.material.R.id.confirm_button)).perform(click())
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_time)).performClick()

        onView(withId(com.google.android.material.R.id.material_timepicker_view))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )

        onView(withId(com.google.android.material.R.id.material_clock_period_pm_button)).perform(click())

        onView(withContentDescription("6 o'clock")).perform(click())
        onView(withContentDescription("30 minutes")).perform(click())
        onView(withId(com.google.android.material.R.id.material_timepicker_ok_button)).perform(click())

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.add_new_appt)).performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasText(testDescription))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Location: $testCityName"))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Date: $testCurrentDateApptFormatted")) //06/17/2023"),10000)
        composeTestRule.waitUntilAtLeastOneExists(hasText("Time: $testCurrTimeApptFormatted"))

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test4_addNewAppt_editThatAppt() {
        val testDescription = "Test Content 789"
        val testCityName = "Austin"
        //val currentDateTime = Calendar.getInstance().time
        val currentDateTime = Calendar.getInstance(TimeZone.getTimeZone("CDT")).time
        val sdf = SimpleDateFormat("E, LLL dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }

        val testCurrDateStr = sdf.format(currentDateTime)
        val sdf2 = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }
        val testCurrentDateApptFormatted = sdf2.format(currentDateTime)
        val testCurrTimeApptFormatted = "6:30 PM"

        // Add btn to lead add appointment screen
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.create_new_appt)).performClick()

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_description)).performTextInput(testDescription)
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_city)).performClick()
        composeTestRule.onNodeWithText(testCityName).performClick()

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_date)).performClick()

        onView(withId(com.google.android.material.R.id.date_picker_actions))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )


        //You can see The View Hierarchy to see what it really looks like
        //If it's today's date, it will have a prefix word "Today "
        onView(withContentDescription("Today $testCurrDateStr")).perform(click())

        onView(withId(com.google.android.material.R.id.confirm_button)).perform(click())
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_time)).performClick()

        onView(withId(com.google.android.material.R.id.material_timepicker_view))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )

        onView(withId(com.google.android.material.R.id.material_clock_period_pm_button)).perform(click())

        onView(withContentDescription("6 o'clock")).perform(click())
        onView(withContentDescription("30 minutes")).perform(click())
        onView(withId(com.google.android.material.R.id.material_timepicker_ok_button)).perform(click())

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.add_new_appt)).performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasText(testDescription))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Location: $testCityName"))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Date: $testCurrentDateApptFormatted")) //06/17/2023"),10000)
        composeTestRule.waitUntilAtLeastOneExists(hasText("Time: $testCurrTimeApptFormatted"))

        //=======================================
        // Do edit with the new created appt
        //=======================================
        composeTestRule.onNodeWithText(testDescription).performClick()

        val testDescription2 = "Test Content 101112"
        val testCityName2 = "Dallas"

        val prevDate = Calendar.getInstance()
        prevDate.add(Calendar.DATE,-1)
        val previousDateTime = prevDate.time

        val testPrevDateStr = sdf.format(previousDateTime)

        val testPrevDateApptFormatted = sdf2.format(previousDateTime)
        val testCurrTimeApptFormatted2 = "8:45 PM"

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_description)).performTextClearance()
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_description)).performTextInput(testDescription2)

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_city)).performClick()
        composeTestRule.onNodeWithText(testCityName2).performClick()

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_date)).performClick()

        onView(withId(com.google.android.material.R.id.date_picker_actions))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )


        //You can see The View Hierarchy to see what it really looks like
        //If it's today's date, it will have a prefix word "Today ."
        onView(withContentDescription(testPrevDateStr)).perform(click())

        onView(withId(com.google.android.material.R.id.confirm_button)).perform(click())
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_time)).performClick()

        onView(withId(com.google.android.material.R.id.material_timepicker_view))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )

        onView(withId(com.google.android.material.R.id.material_clock_period_pm_button)).perform(click())

        onView(withContentDescription("8 o'clock")).perform(click())
        onView(withContentDescription("45 minutes")).perform(click())
        onView(withId(com.google.android.material.R.id.material_timepicker_ok_button)).perform(click())

        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.update_appt)).performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasText(testDescription2))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Location: $testCityName2"))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Date: $testPrevDateApptFormatted"))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Time: $testCurrTimeApptFormatted2"))
    }

    @Test
    fun test5_editExistingApptClearAField_checkErrorDisplayOnConfirm() {
        composeTestRule.onNodeWithText(testDescription).performClick()
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.appt_description)).performTextClearance()
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.update_appt)).performClick()
        composeTestRule.onNodeWithText(appContext.resources.getString(R.string.err_msg_error_dlg)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(appContext.resources.getString(R.string.confirm_error_dlg)).performClick()
    }

    @Test
    fun test6_removeExistingAppt_checkItDoesNotExist() {
        composeTestRule.onNodeWithText(testDescription).performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText(testDescription).assertIsNotDisplayed()
    }
}