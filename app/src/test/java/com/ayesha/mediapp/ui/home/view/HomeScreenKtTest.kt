package com.ayesha.mediapp.ui.home.view

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar
import  com.ayesha.mediapp.R
import org.mockito.Mockito

class HomeScreenKtTest {
    @Test
    fun `returns good morning for hours between 0 and 11`() {
        val mockedCalendar = Mockito.mock(Calendar::class.java)
        Mockito.`when`(mockedCalendar.get(Calendar.HOUR_OF_DAY)).thenReturn(9)

        val greeting = getGreeting(mockedCalendar)

        assertEquals(R.string.good_morning, greeting)
    }

    @Test
    fun `returns good afternoon for hours between 12 and 17`() {
        val mockedCalendar = Mockito.mock(Calendar::class.java)
        Mockito.`when`(mockedCalendar.get(Calendar.HOUR_OF_DAY)).thenReturn(15)// Mock 3 PM

        val greeting = getGreeting(mockedCalendar)

        assertEquals( R.string.good_afternoon,greeting)
    }

    @Test
    fun `returns good evening for hours between 18 and 23`() {
        val mockedCalendar = Mockito.mock(Calendar::class.java)
        Mockito.`when`(mockedCalendar.get(Calendar.HOUR_OF_DAY)).thenReturn(20) // Mock 8 PM

        val greeting = getGreeting(mockedCalendar)
        assertEquals(R.string.good_evening, greeting)
    }

    @Test
    fun `returns hello for invalid hours`() {
        val mockedCalendar = Mockito.mock(Calendar::class.java)
        Mockito.`when`(mockedCalendar.get(Calendar.HOUR_OF_DAY)).thenReturn(25) // Mock invalid hour

        val greeting = getGreeting(mockedCalendar)
        assertEquals(R.string.hello, greeting)
    }


}