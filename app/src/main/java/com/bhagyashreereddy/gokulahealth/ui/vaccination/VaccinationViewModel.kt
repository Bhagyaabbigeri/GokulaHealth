package com.bhagyashreereddy.gokulahealth.ui.vaccination

import androidx.lifecycle.*
import com.bhagyashreereddy.gokulahealth.data.db.entity.Vaccination
import com.bhagyashreereddy.gokulahealth.data.repository.VaccinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaccinationViewModel @Inject constructor(
    private val repository: VaccinationRepository
) : ViewModel() {

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
