package com.handitan.personalappointmentscheduler.core

import org.hamcrest.MatcherAssert.assertThat
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UtilitiesTest {
    @Test
    fun test1_verifyChangeToDateString() {
        val currDateTime = 1688128079157
        val dateToMatched = "06/30/2023"
        val curDateStr = Utilities.changeToDateString(currDateTime)
        assertThat("Date doesn't matched: $curDateStr",curDateStr == dateToMatched)
    }

    @Test
    fun test2_verifyApptFieldsFilledOut() {
        val allFilledOut = Utilities.verifyApptFieldsFilledOut("TestDesc", "TestCityName", "TestAppDateStr", 12)
        assertThat("All fields must be filled out: $allFilledOut", allFilledOut)

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
    fun test3_convertTimeToString() {
        val timeStr1 = Utilities.convertTimeToString(12,15)
        assertThat("Time 1 value doesn't matched: $timeStr1", timeStr1 == "12:15 PM")

        val timeStr2 = Utilities.convertTimeToString(8,45)
        assertThat("Time 2 value doesn't matched: $timeStr2", timeStr2 == "8:45 AM")

        val timeStr3 = Utilities.convertTimeToString(21,25)
        assertThat("Time 3 value doesn't matched: $timeStr3", timeStr3 == "9:25 PM")

        val timeStr4 = Utilities.convertTimeToString(1,10)
        assertThat("Time 4 value doesn't matched: $timeStr4", timeStr4 == "1:10 AM")

        val timeStr5 = Utilities.convertTimeToString(0,25)
        assertThat("Time 5 value doesn't matched: $timeStr5", timeStr5 == "")
    }
}