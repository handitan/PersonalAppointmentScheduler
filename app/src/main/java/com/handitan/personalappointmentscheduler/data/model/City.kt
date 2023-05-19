package com.handitan.personalappointmentscheduler.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.handitan.personalappointmentscheduler.core.Constants

@Entity(
    tableName = Constants.CITY_TABLE
)
data class City(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val name:String
)