package com.bhagyashreereddy.gokulahealth.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "milk_entries",
    foreignKeys = [ForeignKey(
        entity = Cattle::class,
        parentColumns = ["id"],
        childColumns = ["cattleId"],
        onDelete = ForeignKey.CASCADE   // Delete entries when cattle is deleted
    )]
)
data class MilkEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cattleId: Int,
    val date: String,           // "2024-01-15"
    val morningYield: Float,    // litres
    val eveningYield: Float,    // litres
    val totalYield: Float       // total = morning + evening
)
