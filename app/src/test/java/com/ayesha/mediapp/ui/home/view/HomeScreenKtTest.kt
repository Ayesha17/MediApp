package com.ayesha.mediapp.ui.home.view

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar
import  com.ayesha.mediapp.R
import org.mockito.Mockito

class HomeScreenKtTest {
    @Test
    fun `returns good morning for hours between 0 and 11`() {
        mockCurrentHour(9) // Mock 9 AM
        assertEquals(R.string.good_morning, getGreeting())
    }

    @Test
    fun `returns good afternoon for hours between 12 and 17`() {
        mockCurrentHour(15) // Mock 3 PM
        assertEquals( R.string.good_afternoon, getGreeting())
    }

    @Test
    fun `returns good evening for hours between 18 and 23`() {
        mockCurrentHour(20) // Mock 8 PM
        assertEquals(R.string.good_evening, getGreeting())
    }

    @Test
    fun `returns hello for invalid hours`() {
        mockCurrentHour(25) // Mock invalid hour
        assertEquals(R.string.hello, getGreeting())
    }

    private fun mockCurrentHour(hour: Int) {
        val mockedCalendar = Mockito.mock(Calendar::class.java)
        Mockito.`when`(mockedCalendar.get(Calendar.HOUR_OF_DAY)).thenReturn(hour)
        Mockito.mockStatic(Calendar::class.java).use { mockedStatic ->
            mockedStatic.`when`<Calendar> { Calendar.getInstance() }.thenReturn(mockedCalendar)
        }
    }
}