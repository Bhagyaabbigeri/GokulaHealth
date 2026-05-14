package com.bhagyashreereddy.gokulahealth.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhagyashreereddy.gokulahealth.data.db.entity.Vaccination

@Dao
interface VaccinationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccination(vaccination: Vaccination): Long

    @Update
    suspend fun updateVaccination(vaccination: Vaccination)

    @Delete
    suspend fun deleteVaccination(vaccination: Vaccination)

    @Query("SELECT * FROM vaccinations WHERE cattleId = :cattleId ORDER BY scheduledDate ASC")
    fun getVaccinationsForCattle(cattleId: Int): LiveData<List<Vaccination>>

    // For the notification worker — get upcoming vaccinations
    @Query("""
        SELECT * FROM vaccinations 
        WHERE isCompleted = 0 
        AND scheduledDate BETWEEN :today AND :future
    """)
    suspend fun getUpcomingVaccinations(today: String, future: String): List<Vaccination>
}
