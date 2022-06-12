package com.epam.grocerystoreapp.presentation.utils

import android.content.Context
import android.content.res.Resources
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getDateStringByTimestamp
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getDayIntCurrentTimezoneByTimestamp
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getMonthIntCurrentTimezoneByTimestamp
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getTimestampGmtByInt
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getYearIntCurrentTimezoneByTimestamp
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito

class CalendarUtilsTest {

    private val mockContext = Mockito.mock(Context::class.java)
    private val mockResources = Mockito.mock(Resources::class.java)

    @Test
    fun `get timestamp GMT by date 01-01-1970`() {
        /** Given */
        val testYear = 1970
        val testMonth = 1
        val testDay = 1

        /** When */
        val testTimestamp = getTimestampGmtByInt(
            year = testYear,
            month = testMonth,
            day = testDay,
        )

        /** Then */
        val expected = 0L
        val actual = testTimestamp
        assertEquals(expected, actual)
    }

    @Test
    fun `get timestamp GMT by date 01-01-2022`() {
        /** Given */
        val testYear = TEST_YEAR_INT
        val testMonth = TEST_MONTH_INT
        val testDay = TEST_DAY_INT
        val testHour = TEST_HOUR_INT
        val testMinute = TEST_MINUTE_INT
        val testSecond = TEST_SECOND_INT

        /** When */
        val testTimestamp = getTimestampGmtByInt(
            year = testYear,
            month = testMonth,
            day = testDay,
            hour = testHour,
            minute = testMinute,
            second = testSecond,
        )

        /** Then */
        val expected = TEST_TIMESTAMP
        val actual = testTimestamp
        assertEquals(expected, actual)
    }

    @Test
    fun `get year in current timezone by timestamp date 01-01-2022`() {
        /** Given */
        val testTimestamp = TEST_TIMESTAMP

        /** When */
        val testYear = getYearIntCurrentTimezoneByTimestamp(testTimestamp)

        /** Then */
        val expected = TEST_YEAR_INT
        val actual = testYear
        assertEquals(expected, actual)
    }

    @Test
    fun `get month in current timezone by timestamp date 01-01-2022`() {
        /** Given */
        val testTimestamp = TEST_TIMESTAMP

        /** When */
        val testMonth = getMonthIntCurrentTimezoneByTimestamp(testTimestamp)

        /** Then */
        val expected = TEST_MONTH_INT
        val actual = testMonth
        assertEquals(expected, actual)
    }

    @Test
    fun `get day in current timezone by timestamp date 01-01-2022`() {
        /** Given */
        val testTimestamp = TEST_TIMESTAMP

        /** When */
        val testDay = getDayIntCurrentTimezoneByTimestamp(testTimestamp)

        /** Then */
        val expected = TEST_DAY_INT
        val actual = testDay
        assertEquals(expected, actual)
    }

    @Test
    fun `get date String by Timestamp`() {
        /** Given */
        val testTimestamp = TEST_TIMESTAMP
        val testMonths = arrayOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
            "августа", "сентября", "октября", "ноября", "декабря")

        Mockito.`when`(mockContext.resources).thenReturn(mockResources)
        Mockito.`when`(mockResources.getStringArray(anyInt())).thenReturn(testMonths)

        /** When */
        val testDateString = getDateStringByTimestamp(
            resources = mockResources,
            date = testTimestamp
        )

        /** Then */
        val expected = TEST_DATE_STRING
        val actual = testDateString
        assertEquals(expected, actual)
    }

    companion object {
        private const val TEST_TIMESTAMP = 1595115915000L
        private const val TEST_YEAR_INT = 2020
        private const val TEST_MONTH_INT = 7
        private const val TEST_DAY_INT = 18
        private const val TEST_HOUR_INT = 23
        private const val TEST_MINUTE_INT = 45
        private const val TEST_SECOND_INT = 15
        private const val TEST_DATE_STRING = "19 июля 2020" // Для GMT -> 18 июля; GMT+1 -> 19 июля
    }

}
