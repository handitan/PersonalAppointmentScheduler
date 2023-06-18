package com.handitan.personalappointmentscheduler.presentation.ui

import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textview.MaterialTextView
import com.handitan.personalappointmentscheduler.MainActivity
import com.handitan.personalappointmentscheduler.core.TestTags
import com.handitan.personalappointmentscheduler.data.di.AppModule
import com.handitan.personalappointmentscheduler.navigation.Screen
import com.handitan.personalappointmentscheduler.presentation.ui.add_Appointment.AddAppointmentScreen
import com.handitan.personalappointmentscheduler.presentation.ui.appointmentList.AppointmentListScreen
import com.handitan.personalappointmentscheduler.presentation.ui.update_Appointment.UpdateAppointmentScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.OrderWith
import org.junit.runners.MethodSorters
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


@HiltAndroidTest
@UninstallModules(AppModule::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppointmentEndToEndTest {

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

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test1_AddNewAppt_checkNewAppt() {

        val testDescription = "Test Content 123456"
        val testCityName = "Dallas"
        val currentDateTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("E, LLL dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        val testCurrDateStr = sdf.format(currentDateTime)
        val sdf2 = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val testCurrentDateApptFormatted = sdf2.format(currentDateTime)
        val testCurrTimeApptFormatted = "6:30 PM"

        composeTestRule.onNodeWithTag(TestTags.NOAPPOINTMENT).assertIsDisplayed()

        //composeTestRule.mainClock.
        // Add btn to lead add appointment screen
        composeTestRule.onNodeWithTag(TestTags.ADDAPPOINTMENTBTN).performClick()

        composeTestRule.onNodeWithContentDescription("Description").performTextInput(testDescription)
        composeTestRule.onNodeWithContentDescription("City").performClick()
        composeTestRule.onNodeWithText(testCityName).performClick()

        //composeTestRule.mainClock.advanceTimeBy(50000)
        composeTestRule.onNodeWithContentDescription("Appointment Date").performClick()

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
        composeTestRule.onNodeWithContentDescription("Appointment Time").performClick()

        onView(withId(com.google.android.material.R.id.material_timepicker_view))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )

        onView(withId(com.google.android.material.R.id.material_clock_period_pm_button)).perform(click())

        onView(withContentDescription("6 o'clock")).perform(click())
        onView(withContentDescription("30 minutes")).perform(click())
        onView(withId(com.google.android.material.R.id.material_timepicker_ok_button)).perform(click())

        composeTestRule.onNodeWithContentDescription("Add New Appointment").performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasText(testDescription))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Location: $testCityName"))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Date: $testCurrentDateApptFormatted")) //06/17/2023"),10000)
        composeTestRule.waitUntilAtLeastOneExists(hasText("Time: $testCurrTimeApptFormatted"))

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test2_addNewAppt_editThatAppt() {
        val testDescription = "Test Content 789"
        val testCityName = "Austin"
        val currentDateTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("E, LLL dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        val testCurrDateStr = sdf.format(currentDateTime)
        val sdf2 = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val testCurrentDateApptFormatted = sdf2.format(currentDateTime)
        val testCurrTimeApptFormatted = "6:30 PM"

        //composeTestRule.mainClock.
        // Add btn to lead add appointment screen
        composeTestRule.onNodeWithTag(TestTags.ADDAPPOINTMENTBTN).performClick()

        composeTestRule.onNodeWithContentDescription("Description").performTextInput(testDescription)
        composeTestRule.onNodeWithContentDescription("City").performClick()
        composeTestRule.onNodeWithText(testCityName).performClick()

        //composeTestRule.mainClock.advanceTimeBy(50000)
        composeTestRule.onNodeWithContentDescription("Appointment Date").performClick()

        onView(withId(com.google.android.material.R.id.date_picker_actions))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )


        //You can see The View Hierarchy to see what it really looks like
        //If it's today's date, it will have a prefix word "Today."
        onView(withContentDescription("Today $testCurrDateStr")).perform(click())

        onView(withId(com.google.android.material.R.id.confirm_button)).perform(click())
        composeTestRule.onNodeWithContentDescription("Appointment Time").performClick()

        onView(withId(com.google.android.material.R.id.material_timepicker_view))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )

        onView(withId(com.google.android.material.R.id.material_clock_period_pm_button)).perform(click())

        onView(withContentDescription("6 o'clock")).perform(click())
        onView(withContentDescription("30 minutes")).perform(click())
        onView(withId(com.google.android.material.R.id.material_timepicker_ok_button)).perform(click())

        composeTestRule.onNodeWithContentDescription("Add New Appointment").performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasText(testDescription))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Location: $testCityName"))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Date: $testCurrentDateApptFormatted")) //06/17/2023"),10000)
        composeTestRule.waitUntilAtLeastOneExists(hasText("Time: $testCurrTimeApptFormatted"))

        //=======================================
        // Do edit with the new created appt
        //composeTestRule.onNodeWithText(testDescription).assertIsDisplayed()
        composeTestRule.onNodeWithText(testDescription).performClick()

        val testDescription2 = "Test Content 101112"
        val testCityName2 = "Dallas"

        val prevDate = Calendar.getInstance()
        prevDate.add(Calendar.DATE,-1)
        val previousDateTime = prevDate.time

        val testPrevDateStr = sdf.format(previousDateTime)

        val testPrevDateApptFormatted = sdf2.format(previousDateTime)
        val testCurrTimeApptFormatted2 = "8:45 PM"


        composeTestRule.onNodeWithContentDescription("Description").performTextClearance()
        composeTestRule.onNodeWithContentDescription("Description").performTextInput(testDescription2)

        composeTestRule.onNodeWithContentDescription("City").performClick()
        composeTestRule.onNodeWithText(testCityName2).performClick()

        composeTestRule.onNodeWithContentDescription("Appointment Date").performClick()

        onView(withId(com.google.android.material.R.id.date_picker_actions))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )


        //You can see The View Hierarchy to see what it really looks like
        //If it's today's date, it will have a prefix word "Today."
        onView(withContentDescription("$testPrevDateStr")).perform(click())

        onView(withId(com.google.android.material.R.id.confirm_button)).perform(click())
        composeTestRule.onNodeWithContentDescription("Appointment Time").performClick()

        onView(withId(com.google.android.material.R.id.material_timepicker_view))
            .inRoot(RootMatchers.isDialog())
            .check(
                matches(isDisplayed())
            )

        onView(withId(com.google.android.material.R.id.material_clock_period_pm_button)).perform(click())

        onView(withContentDescription("8 o'clock")).perform(click())
        onView(withContentDescription("45 minutes")).perform(click())
        onView(withId(com.google.android.material.R.id.material_timepicker_ok_button)).perform(click())

        composeTestRule.onNodeWithContentDescription("Update Appointment").performClick()

        composeTestRule.waitUntilAtLeastOneExists(hasText(testDescription2))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Location: $testCityName2"))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Date: $testPrevDateApptFormatted"))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Time: $testCurrTimeApptFormatted2"))

        //composeTestRule.waitUntilAtLeastOneExists(hasText("Time: 22"),10000)


    }
}