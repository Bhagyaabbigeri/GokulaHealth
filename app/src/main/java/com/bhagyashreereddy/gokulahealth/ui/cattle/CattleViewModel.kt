package com.bhagyashreereddy.gokulahealth.ui.cattle

import android.app.Application
import androidx.lifecycle.*
import com.bhagyashreereddy.gokulahealth.data.db.AppDatabase
import com.bhagyashreereddy.gokulahealth.data.db.entity.Cattle
import com.bhagyashreereddy.gokulahealth.data.repository.CattleRepository
import kotlinx.coroutines.launch

class CattleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CattleRepository
    val allCattle: LiveData<List<Cattle>>

    init {
        val cattleDao = AppDatabase.getDatabase(application).cattleDao()
        repository = CattleRepository(cattleDao)
        allCattle = repository.allCattle
    }

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
