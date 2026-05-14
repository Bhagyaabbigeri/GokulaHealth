package com.bhagyashreereddy.gokulahealth.data.repository

import androidx.lifecycle.LiveData
import com.bhagyashreereddy.gokulahealth.data.db.dao.VaccinationDao
import com.bhagyashreereddy.gokulahealth.data.db.entity.Vaccination

class VaccinationRepository(private val vaccinationDao: VaccinationDao) {

    fun getVaccinationsForCattle(cattleId: Int): LiveData<List<Vaccination>> =
        vaccinationDao.getVaccinationsForCattle(cattleId)

    suspend fun insertVaccination(vaccination: Vaccination) {
        vaccinationDao.insertVaccination(vaccination)
    }

    suspend fun updateVaccination(vaccination: Vaccination) {
        vaccinationDao.updateVaccination(vaccination)
    }

    suspend fun deleteVaccination(vaccination: Vaccination) {
        vaccinationDao.deleteVaccination(vaccination)
    }
}
