package com.bhagyashreereddy.gokulahealth.data.repository

import androidx.lifecycle.LiveData
import com.bhagyashreereddy.gokulahealth.data.db.dao.MilkEntryDao
import com.bhagyashreereddy.gokulahealth.data.db.entity.MilkEntry

class MilkRepository(private val milkEntryDao: MilkEntryDao) {

    fun getLast30DaysEntries(cattleId: Int): LiveData<List<MilkEntry>> =
        milkEntryDao.getLast30DaysEntries(cattleId)

    suspend fun insertMilkEntry(milkEntry: MilkEntry) {
        milkEntryDao.insertMilkEntry(milkEntry)
    }

    suspend fun getMonthlyAverage(cattleId: Int, month: String): Float? {
        // month pattern e.g. "2024-01%"
        return milkEntryDao.getMonthlyAverage(cattleId, "$month%")
    }

    suspend fun deleteMilkEntry(milkEntry: MilkEntry) {
        milkEntryDao.deleteMilkEntry(milkEntry)
    }
}
