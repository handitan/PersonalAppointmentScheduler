package com.handitan.personalappointmentscheduler.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.handitan.personalappointmentscheduler.core.Constants

@Entity(
    tableName = Constants.APPOINTMENT_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["cityId"]
        )
    ]
)
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val description:String,
    val cityId:Long,
    val date:Long,
    val time:Long
)