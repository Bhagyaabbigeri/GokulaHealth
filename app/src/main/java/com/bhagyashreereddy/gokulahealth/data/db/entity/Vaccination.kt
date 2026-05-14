package com.bhagyashreereddy.gokulahealth.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "vaccinations",
    foreignKeys = [ForeignKey(
        entity = Cattle::class,
        parentColumns = ["id"],
        childColumns = ["cattleId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Vaccination(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cattleId: Int,
    val vaccineName: String,    // e.g., "FMD Vaccine"
    val scheduledDate: String,  // "2024-03-01"
    val isCompleted: Boolean = false,
    val notes: String? = null
)
