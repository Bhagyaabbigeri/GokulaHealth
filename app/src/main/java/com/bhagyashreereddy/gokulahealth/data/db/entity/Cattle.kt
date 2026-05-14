package com.bhagyashreereddy.gokulahealth.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.ForeignKey

@Entity(
    tableName = "cattle",
    foreignKeys = [ForeignKey(
        entity = Factory::class,
        parentColumns = ["id"],
        childColumns = ["factoryId"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Cattle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val factoryId: Int? = null, // Link to Dairy Factory/Unit
    val name: String,           // e.g., "Lakshmi"
    val earTagId: String,       // e.g., "KA-2024-001"
    val breed: String,          // e.g., "HF Cross"
    val dateOfBirth: String,    // e.g., "2021-06-15"
    val photoPath: String? = null,   // Local file path for image
    val addedDate: Long = System.currentTimeMillis()
)
