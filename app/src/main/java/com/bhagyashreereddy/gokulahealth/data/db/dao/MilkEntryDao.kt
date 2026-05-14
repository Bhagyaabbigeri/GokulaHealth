package com.bhagyashreereddy.gokulahealth.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhagyashreereddy.gokulahealth.data.db.entity.MilkEntry

@Dao
interface MilkEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilkEntry(milkEntry: MilkEntry)

    @Delete
    suspend fun deleteMilkEntry(milkEntry: MilkEntry)

    // Get last 30 days entries for a specific cattle
    @Query("""
        SELECT * FROM milk_entries 
        WHERE cattleId = :cattleId 
        ORDER BY date DESC 
        LIMIT 30
    """)
    fun getLast30DaysEntries(cattleId: Int): LiveData<List<MilkEntry>>

    // Monthly average for a specific month
    @Query("""
        SELECT AVG(totalYield) FROM milk_entries 
        WHERE cattleId = :cattleId 
        AND date LIKE :monthPattern
    """)
    suspend fun getMonthlyAverage(cattleId: Int, monthPattern: String): Float?

    // Check if entry exists for a date
    @Query("""
        SELECT * FROM milk_entries 
        WHERE cattleId = :cattleId AND date = :date 
        LIMIT 1
    """)
    suspend fun getEntryForDate(cattleId: Int, date: String): MilkEntry?
}
