package com.bhagyashreereddy.gokulahealth.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bhagyashreereddy.gokulahealth.data.db.dao.*
import com.bhagyashreereddy.gokulahealth.data.db.entity.*

import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.bhagyashreereddy.gokulahealth.utils.DateUtils

@Database(
    entities = [Cattle::class, MilkEntry::class, Vaccination::class, Factory::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cattleDao(): CattleDao
    abstract fun milkEntryDao(): MilkEntryDao
    abstract fun vaccinationDao(): VaccinationDao
    abstract fun factoryDao(): FactoryDao

    companion object {
        // Singleton: only one instance of database
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gokula_health_db"
                )
                .fallbackToDestructiveMigration(true)
                .addCallback(DatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateInitialData()
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Ensure factories exist even if app was updated from v1
                populateInitialData()
            }

            private fun populateInitialData() {
                CoroutineScope(Dispatchers.IO).launch {
                    val dbInstance = getDatabase(context)
                    val factoryDao = dbInstance.factoryDao()
                    
                    // Check if factories already exist to avoid duplicates
                    val existing = dbInstance.cattleDao().getAllCattle() // Just a trigger to open DB
                    
                    val cattleDao = dbInstance.cattleDao()
                    val milkDao = dbInstance.milkEntryDao()
                    val vaccineDao = dbInstance.vaccinationDao()

                    // 1. Add Factories
                    val factoryAId = factoryDao.insertFactory(Factory(name = "Green Pastures Dairy", location = "North Wing")).toInt()
                    val factoryBId = factoryDao.insertFactory(Factory(name = "Sunny Side Farm", location = "South Wing")).toInt()

                    val packageName = context.packageName

                    // 2. Add Cattle with specific Photos linked
                    val lakshmiId = cattleDao.insertCattle(Cattle(
                        factoryId = factoryAId,
                        name = "Lakshmi",
                        earTagId = "TAG-001",
                        breed = "HF Cross",
                        dateOfBirth = "2021-05-20",
                        photoPath = "android.resource://$packageName/drawable/cow_lakshmi"
                    )).toInt()

                    val gauriId = cattleDao.insertCattle(Cattle(
                        factoryId = factoryAId,
                        name = "Gauri",
                        earTagId = "TAG-002",
                        breed = "Jersey",
                        dateOfBirth = "2022-01-10",
                        photoPath = "android.resource://$packageName/drawable/cow_gauri"
                    )).toInt()

                    val kamadhenuId = cattleDao.insertCattle(Cattle(
                        factoryId = factoryBId,
                        name = "Kamadhenu",
                        earTagId = "TAG-003",
                        breed = "Gir",
                        dateOfBirth = "2020-08-15",
                        photoPath = "android.resource://$packageName/drawable/cow_kamadhenu"
                    )).toInt()

                    val nandiniId = cattleDao.insertCattle(Cattle(
                        factoryId = factoryBId,
                        name = "Nandini",
                        earTagId = "TAG-004",
                        breed = "Sahiwal",
                        dateOfBirth = "2021-11-05",
                        photoPath = "android.resource://$packageName/drawable/cow_nandini"
                    )).toInt()

                    val kapilaId = cattleDao.insertCattle(Cattle(
                        factoryId = factoryBId,
                        name = "Kapil",
                        earTagId = "TAG-005",
                        breed = "Malnad Gidda",
                        dateOfBirth = "2022-03-25",
                        photoPath = "android.resource://$packageName/drawable/cow_kapil"
                    )).toInt()

                    // 3. Add Milk Entries
                    val cattleIds = listOf(lakshmiId, gauriId, kamadhenuId, nandiniId, kapilaId)
                    val baseYields = listOf(10f, 8f, 12f, 9f, 5f)

                    for (idx in cattleIds.indices) {
                        val id = cattleIds[idx]
                        val base = baseYields[idx]
                        for (i in 0 until 7) {
                            val morning = base + (0..20).random() / 10f
                            val evening = (base * 0.8f) + (0..15).random() / 10f
                            milkDao.insertMilkEntry(MilkEntry(
                                cattleId = id,
                                date = DateUtils.getDateAfterDays(-i),
                                morningYield = morning,
                                eveningYield = evening,
                                totalYield = morning + evening
                            ))
                        }
                    }

                    // 4. Add Vaccinations
                    vaccineDao.insertVaccination(Vaccination(cattleId = lakshmiId, vaccineName = "FMD Vaccine", scheduledDate = DateUtils.getDateAfterDays(-10), isCompleted = true))
                    vaccineDao.insertVaccination(Vaccination(cattleId = lakshmiId, vaccineName = "Anthrax Booster", scheduledDate = DateUtils.getDateAfterDays(5), isCompleted = false))
                    vaccineDao.insertVaccination(Vaccination(cattleId = gauriId, vaccineName = "Brucellosis", scheduledDate = DateUtils.getDateAfterDays(12), isCompleted = false))
                    vaccineDao.insertVaccination(Vaccination(cattleId = kamadhenuId, vaccineName = "Lumpy Skin Disease", scheduledDate = DateUtils.getDateAfterDays(2), isCompleted = false))
                    vaccineDao.insertVaccination(Vaccination(cattleId = nandiniId, vaccineName = "HS+BQ Vaccine", scheduledDate = DateUtils.getDateAfterDays(-20), isCompleted = true))
                    vaccineDao.insertVaccination(Vaccination(cattleId = kapilaId, vaccineName = "Theileriosis", scheduledDate = DateUtils.getDateAfterDays(20), isCompleted = false))
                }
            }
        }
    }
}
