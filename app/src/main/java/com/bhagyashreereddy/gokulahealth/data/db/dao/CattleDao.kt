package com.bhagyashreereddy.gokulahealth.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhagyashreereddy.gokulahealth.data.db.entity.Cattle

@Dao
interface CattleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCattle(cattle: Cattle): Long

    @Update
    suspend fun updateCattle(cattle: Cattle)

    @Delete
    suspend fun deleteCattle(cattle: Cattle)

    @Query("SELECT * FROM cattle ORDER BY addedDate DESC")
    fun getAllCattle(): LiveData<List<Cattle>>

    @Query("SELECT * FROM cattle WHERE id = :cattleId")
    fun getCattleById(cattleId: Int): LiveData<Cattle>
}
