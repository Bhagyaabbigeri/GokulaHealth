package com.bhagyashreereddy.gokulahealth.ui.factory

import androidx.lifecycle.*
import com.bhagyashreereddy.gokulahealth.data.db.dao.FactoryYield
import com.bhagyashreereddy.gokulahealth.data.db.entity.Factory
import com.bhagyashreereddy.gokulahealth.data.repository.FactoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FactoryViewModel @Inject constructor(
    private val repository: FactoryRepository
) : ViewModel() {

    val factoryComparison: LiveData<List<FactoryYield>> = repository.getFactoryComparison()
    val allFactories: LiveData<List<Factory>> = repository.getAllFactories()
}
