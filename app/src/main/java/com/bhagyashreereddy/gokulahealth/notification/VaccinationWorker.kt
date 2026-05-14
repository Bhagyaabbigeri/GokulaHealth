package com.bhagyashreereddy.gokulahealth.notification

import android.content.Context
import androidx.work.*
import com.bhagyashreereddy.gokulahealth.data.db.AppDatabase
import com.bhagyashreereddy.gokulahealth.utils.DateUtils
import java.util.concurrent.TimeUnit

class VaccinationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val db = AppDatabase.getDatabase(applicationContext)
            val today = DateUtils.getTodayDate()
            val future = DateUtils.getDateAfterDays(7)

            val upcoming = db.vaccinationDao().getUpcomingVaccinations(today, future)

            upcoming.forEach { vaccination ->
                NotificationHelper.showVaccinationNotification(
                    applicationContext,
                    "Your Cattle",
                    vaccination.vaccineName,
                    vaccination.scheduledDate,
                    vaccination.id
                )
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "vaccination_check_work"

        fun scheduleDaily(context: Context) {
            val request = PeriodicWorkRequestBuilder<VaccinationWorker>(1, TimeUnit.DAYS)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
