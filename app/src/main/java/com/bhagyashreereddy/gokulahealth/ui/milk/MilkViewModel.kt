package com.bhagyashreereddy.gokulahealth.ui.milk

import android.app.Application
import androidx.lifecycle.*
import com.bhagyashreereddy.gokulahealth.data.db.AppDatabase
import com.bhagyashreereddy.gokulahealth.data.db.entity.MilkEntry
import com.bhagyashreereddy.gokulahealth.data.repository.MilkRepository
import kotlinx.coroutines.launch

class MilkViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MilkRepository
    private val _monthlyAverage = MutableLiveData<Float>()
    val monthlyAverage: LiveData<Float> = _monthlyAverage

    init {
        val dao = AppDatabase.getDatabase(application).milkEntryDao()
        repository = MilkRepository(dao)
    }

    fun getMilkEntries(cattleId: Int): LiveData<List<MilkEntry>> =
        repository.getLast30DaysEntries(cattleId)

    fun insertMilkEntry(milkEntry: MilkEntry) = viewModelScope.launch {
        repository.insertMilkEntry(milkEntry)
    }

    fun fetchMonthlyAverage(cattleId: Int, month: String) = viewModelScope.launch {
        val avg = repository.getMonthlyAverage(cattleId, month) ?: 0f
        _monthlyAverage.postValue(avg)
    }

    fun deleteMilkEntry(milkEntry: MilkEntry) = viewModelScope.launch {
        repository.deleteMilkEntry(milkEntry)
    }
}
