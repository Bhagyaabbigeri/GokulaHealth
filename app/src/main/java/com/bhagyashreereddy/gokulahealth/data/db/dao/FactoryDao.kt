package com.bhagyashreereddy.gokulahealth.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhagyashreereddy.gokulahealth.data.db.entity.Factory

@Dao
interface FactoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFactory(factory: Factory): Long

    @Query("SELECT * FROM factories")
    fun getAllFactories(): LiveData<List<Factory>>

    @Query("""
        SELECT f.name as factoryName, SUM(m.totalYield) as totalYield 
        FROM factories f
        JOIN cattle c ON f.id = c.factoryId
        JOIN milk_entries m ON c.id = m.cattleId
        GROUP BY f.id
    """)
    fun getFactoryComparison(): LiveData<List<FactoryYield>>
}

data class FactoryYield(
    val factoryName: String,
    val totalYield: Float
)
