package com.bhagyashreereddy.gokulahealth.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val monthFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())

    fun getTodayDate(): String = dateFormat.format(Date())

    fun getCurrentMonth(): String = monthFormat.format(Date())

    fun getDateAfterDays(days: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, days)
        return dateFormat.format(cal.time)
    }

    fun formatDisplayDate(dateString: String): String {
        return try {
            val date = dateFormat.parse(dateString) ?: return dateString
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
        } catch (e: Exception) {
            dateString
        }
    }
}
