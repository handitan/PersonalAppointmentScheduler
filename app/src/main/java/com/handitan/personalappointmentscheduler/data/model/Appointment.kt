package com.handitan.personalappointmentscheduler.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
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
    @ColumnInfo(index = true)
    val cityId:Long,
    val date:Long,
    val hour:Int,
    val minute:Int
)