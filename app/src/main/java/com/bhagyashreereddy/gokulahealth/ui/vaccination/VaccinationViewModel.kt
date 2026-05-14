package com.bhagyashreereddy.gokulahealth.ui.vaccination

import android.app.Application
import androidx.lifecycle.*
import com.bhagyashreereddy.gokulahealth.data.db.AppDatabase
import com.bhagyashreereddy.gokulahealth.data.db.entity.Vaccination
import com.bhagyashreereddy.gokulahealth.data.repository.VaccinationRepository
import kotlinx.coroutines.launch

class VaccinationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VaccinationRepository

    init {
        val dao = AppDatabase.getDatabase(application).vaccinationDao()
        repository = VaccinationRepository(dao)
    }

    fun getVaccinations(cattleId: Int): LiveData<List<Vaccination>> =
        repository.getVaccinationsForCattle(cattleId)

    fun insertVaccination(vaccination: Vaccination) = viewModelScope.launch {
        repository.insertVaccination(vaccination)
    }

    fun updateVaccination(vaccination: Vaccination) = viewModelScope.launch {
        repository.updateVaccination(vaccination)
    }

    fun deleteVaccination(vaccination: Vaccination) = viewModelScope.launch {
        repository.deleteVaccination(vaccination)
    }
}
