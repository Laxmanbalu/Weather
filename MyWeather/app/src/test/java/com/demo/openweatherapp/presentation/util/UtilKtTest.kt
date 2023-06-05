package com.demo.openweatherapp.presentation.util

import android.content.Context
import android.content.SharedPreferences
import com.demo.openweatherapp.presentation.util.Constants.EMPTY_STRING
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito

class UtilKtTest {

    @Mock
    private val mockContext: Context = Mockito.mock(Context::class.java)

    @Mock
    private var mockPrefs: SharedPreferences = Mockito.mock(SharedPreferences::class.java)

    @Mock
    private var mockEditor: SharedPreferences.Editor =
        Mockito.mock(SharedPreferences.Editor::class.java)

    @Before
    fun setup() {
        Mockito.`when`(
            mockContext.getSharedPreferences(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(mockPrefs)
        Mockito.`when`(mockPrefs.edit()).thenReturn(mockEditor)
        Mockito.`when`(
            mockContext.getSharedPreferences(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            ).edit()
        ).thenReturn(mockEditor)
        Mockito.`when`(
            mockEditor.putString(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(mockEditor)
    }

    @Test
    fun testStoringIntoPref_Success() {
        storeCityInfo(mockContext, CITY)
        Mockito.verify(mockEditor).putString(
            KEY_CITY,
            CITY
        )
    }

    @Test
    fun testStoringIntoPref_withEmpty() {
        storeCityInfo(mockContext, Constants.EMPTY_STRING)
        Mockito.verify(mockEditor, Mockito.never()).putString(
            KEY_CITY,
            CITY
        )
    }

    @Test
    fun testGetLastUpdatedCity_Empty() {
        Mockito.`when`(mockPrefs.getString(KEY_CITY, EMPTY_STRING)).thenReturn(EMPTY_STRING)
        val result = getLastCityName(mockContext)
        assertEquals(EMPTY_STRING, result)
    }


    @Test
    fun testGetLastUpdatedCity_ValidValue() {
        Mockito.`when`(mockPrefs.getString(KEY_CITY, EMPTY_STRING)).thenReturn(CITY)
        val result = getLastCityName(mockContext)
        assertEquals(CITY, result)
    }

    private companion object {
        const val CITY = "Edison"
    }
}