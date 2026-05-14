package com.bhagyashreereddy.gokulahealth.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "factories")
data class Factory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val location: String
)
