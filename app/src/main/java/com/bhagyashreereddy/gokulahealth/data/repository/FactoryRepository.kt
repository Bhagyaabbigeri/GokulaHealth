package com.bhagyashreereddy.gokulahealth.data.repository

import androidx.lifecycle.LiveData
import com.bhagyashreereddy.gokulahealth.data.db.dao.FactoryDao
import com.bhagyashreereddy.gokulahealth.data.db.dao.FactoryYield
import com.bhagyashreereddy.gokulahealth.data.db.entity.Factory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactoryRepository @Inject constructor(private val factoryDao: FactoryDao) {

    fun getAllFactories(): LiveData<List<Factory>> = factoryDao.getAllFactories()

    fun getFactoryComparison(): LiveData<List<FactoryYield>> = factoryDao.getFactoryComparison()

    suspend fun insertFactory(factory: Factory) {
        factoryDao.insertFactory(factory)
    }
}
