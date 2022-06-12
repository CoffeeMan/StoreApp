package com.epam.grocerystoreapp.presentation.utils

import android.content.res.Resources
import com.epam.grocerystoreapp.R
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtils {

    private val calendar by lazy { GregorianCalendar() }

    fun getTimestampGmtByInt(
        year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0,
    ): Long {
        return GregorianCalendar(year, month - 1, day, hour, minute, second).timeInMillis + getGmt()
    }

    fun getYearIntCurrentTimezoneByTimestamp(date: Long): Int {
        return SimpleDateFormat("yyyy", Locale.getDefault())
            .format(Date(date)).toInt()
    }

    fun getMonthIntCurrentTimezoneByTimestamp(date: Long): Int {
        return SimpleDateFormat("MM", Locale.getDefault())
            .format(Date(date)).toInt()
    }

    fun getDayIntCurrentTimezoneByTimestamp(date: Long): Int {
        return SimpleDateFormat("dd", Locale.getDefault())
            .format(Date(date)).toInt()
    }

    fun getDateStringByTimestamp(resources: Resources, date: Long): String {
        val day = getDayIntCurrentTimezoneByTimestamp(date)
        val month = getMonthIntCurrentTimezoneByTimestamp(date)
        val year = getYearIntCurrentTimezoneByTimestamp(date)
        val months = resources.getStringArray(R.array.months)

        return when (month) {
            1 -> "$day ${months[0]} $year"
            2 -> "$day ${months[1]} $year"
            3 -> "$day ${months[2]} $year"
            4 -> "$day ${months[3]} $year"
            5 -> "$day ${months[4]} $year"
            6 -> "$day ${months[5]} $year"
            7 -> "$day ${months[6]} $year"
            8 -> "$day ${months[7]} $year"
            9 -> "$day ${months[8]} $year"
            10 -> "$day ${months[9]} $year"
            11 -> "$day ${months[10]} $year"
            12 -> "$day ${months[11]} $year"
            else -> ""
        }
    }

    private fun getGmt(): Long {
        return calendar.timeZone.rawOffset.toLong()
    }

}