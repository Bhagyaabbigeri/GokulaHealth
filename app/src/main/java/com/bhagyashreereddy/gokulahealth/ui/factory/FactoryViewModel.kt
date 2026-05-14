package com.bhagyashreereddy.gokulahealth.ui.factory

import android.app.Application
import androidx.lifecycle.*
import com.bhagyashreereddy.gokulahealth.data.db.AppDatabase
import com.bhagyashreereddy.gokulahealth.data.db.dao.FactoryYield
import com.bhagyashreereddy.gokulahealth.data.db.entity.Factory
import com.bhagyashreereddy.gokulahealth.data.repository.FactoryRepository
import kotlinx.coroutines.launch

class FactoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FactoryRepository
    val factoryComparison: LiveData<List<FactoryYield>>
    val allFactories: LiveData<List<Factory>>

    init {
        val dao = AppDatabase.getDatabase(application).factoryDao()
        repository = FactoryRepository(dao)
        factoryComparison = repository.getFactoryComparison()
        allFactories = repository.getAllFactories()
    }
}
