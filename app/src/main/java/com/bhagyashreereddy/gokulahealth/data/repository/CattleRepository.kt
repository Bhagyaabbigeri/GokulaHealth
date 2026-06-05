package com.bhagyashreereddy.gokulahealth.data.repository

import androidx.lifecycle.LiveData
import com.bhagyashreereddy.gokulahealth.data.db.dao.CattleDao
import com.bhagyashreereddy.gokulahealth.data.db.entity.Cattle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CattleRepository @Inject constructor(private val cattleDao: CattleDao) {

    val allCattle: LiveData<List<Cattle>> = cattleDao.getAllCattle()

    suspend fun insertCattle(cattle: Cattle): Long {
        return cattleDao.insertCattle(cattle)
    }

    suspend fun updateCattle(cattle: Cattle) {
        cattleDao.updateCattle(cattle)
    }

    suspend fun deleteCattle(cattle: Cattle) {
        cattleDao.deleteCattle(cattle)
    }

    fun getCattleById(id: Int): LiveData<Cattle> {
        return cattleDao.getCattleById(id)
    }
}
