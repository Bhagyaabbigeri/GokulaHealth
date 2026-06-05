package com.bhagyashreereddy.gokulahealth.di

import android.content.Context
import com.bhagyashreereddy.gokulahealth.data.db.AppDatabase
import com.bhagyashreereddy.gokulahealth.data.db.dao.CattleDao
import com.bhagyashreereddy.gokulahealth.data.db.dao.MilkEntryDao
import com.bhagyashreereddy.gokulahealth.data.db.dao.VaccinationDao
import com.bhagyashreereddy.gokulahealth.data.db.dao.FactoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideCattleDao(database: AppDatabase): CattleDao {
        return database.cattleDao()
    }

    @Provides
    fun provideMilkEntryDao(database: AppDatabase): MilkEntryDao {
        return database.milkEntryDao()
    }

    @Provides
    fun provideVaccinationDao(database: AppDatabase): VaccinationDao {
        return database.vaccinationDao()
    }
    
    @Provides
    fun provideFactoryDao(database: AppDatabase): FactoryDao {
        return database.factoryDao()
    }
}
