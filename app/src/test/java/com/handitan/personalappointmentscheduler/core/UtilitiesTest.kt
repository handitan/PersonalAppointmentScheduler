package com.handitan.personalappointmentscheduler.core

import org.hamcrest.MatcherAssert.assertThat
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

class UtilitiesTest {
    @Test
    fun `Pass timeInMillis to changeToDateString, output date in String format needs to match`() {
        val currDateTime = 1688128079157
        val dateToMatched = "06/30/2023"
        val curDateStr = Utilities.changeToDateString(currDateTime)
        assertThat("Date doesn't match: $curDateStr",curDateStr == dateToMatched)
    }

    @Test
    fun `Non-empty strings and non-zero value are passed to verifyApptFieldsFilledOut, returns true`() {
        val allFilledOut = Utilities.verifyApptFieldsFilledOut("TestDesc", "TestCityName", "TestAppDateStr", 12)
        assertThat("All fields must be filled out: $allFilledOut", allFilledOut)
    }

    @Test
    fun `An empty string or zero value are passed to verifyApptFieldsFilledOut, returns false`() {
        val descNotFilledOut = Utilities.verifyApptFieldsFilledOut("", "TestCityName", "TestAppDateStr", 12)
        assertThat("Description is supposed to be empty", !descNotFilledOut)

        val cityNotFilledOut = Utilities.verifyApptFieldsFilledOut("TestDesc", "", "TestAppDateStr", 12)
        assertThat("City is supposed to be empty", !cityNotFilledOut)

        val appDateStrFilledOut = Utilities.verifyApptFieldsFilledOut("TestDesc", "TestCityName", "", 12)
        assertThat("AppDateStr is supposed to be empty", !appDateStrFilledOut)

        val zeroAppHour = Utilities.verifyApptFieldsFilledOut("TestDesc", "TestCityName", "TestAppDateStr", 0)
        assertThat("Hour is supposed to be zero", !zeroAppHour)
    }

    @Test
    fun `Invalid hour to convertTimeToString, output should match to empty string`() {
        val timeStr5 = Utilities.convertTimeToString(0, 25)
        assertThat("Time 5 value doesn't match: $timeStr5", timeStr5 == "")
    }

    @Test
    fun `Valid hour and minute to convertTimeToString, output Time in String format needs to match`() {
        val timeStr1 = Utilities.convertTimeToString(12,15)
        assertThat("Time 1 value doesn't match: $timeStr1", timeStr1 == "12:15 PM")

        val timeStr2 = Utilities.convertTimeToString(8,45)
        assertThat("Time 2 value doesn't match: $timeStr2", timeStr2 == "8:45 AM")

        val timeStr3 = Utilities.convertTimeToString(21,25)
        assertThat("Time 3 value doesn't match: $timeStr3", timeStr3 == "9:25 PM")

        val timeStr4 = Utilities.convertTimeToString(1,10)
        assertThat("Time 4 value doesn't match: $timeStr4", timeStr4 == "1:10 AM")
    }
}