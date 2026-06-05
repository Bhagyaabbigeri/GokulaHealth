package com.bhagyashreereddy.gokulahealth.ui.milk

import androidx.lifecycle.*
import com.bhagyashreereddy.gokulahealth.data.db.entity.MilkEntry
import com.bhagyashreereddy.gokulahealth.data.repository.MilkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MilkViewModel @Inject constructor(
    private val repository: MilkRepository
) : ViewModel() {

    private val _monthlyAverage = MutableLiveData<Float>()
    val monthlyAverage: LiveData<Float> = _monthlyAverage

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
