package com.bhagyashreereddy.gokulahealth.ui.cattle

import androidx.lifecycle.*
import com.bhagyashreereddy.gokulahealth.data.db.entity.Cattle
import com.bhagyashreereddy.gokulahealth.data.repository.CattleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CattleViewModel @Inject constructor(
    private val repository: CattleRepository
) : ViewModel() {

    val allCattle: LiveData<List<Cattle>> = repository.allCattle

    fun insertCattle(cattle: Cattle) = viewModelScope.launch {
        repository.insertCattle(cattle)
    }

    fun updateCattle(cattle: Cattle) = viewModelScope.launch {
        repository.updateCattle(cattle)
    }

    fun deleteCattle(cattle: Cattle) = viewModelScope.launch {
        repository.deleteCattle(cattle)
    }

    fun getCattleById(id: Int): LiveData<Cattle> = repository.getCattleById(id)
}
