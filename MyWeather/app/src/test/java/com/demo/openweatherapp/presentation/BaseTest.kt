/*
*Lowe's Companies Inc., Android Application
* Copyright (C)  Lowe's Companies Inc.
*
*  The Lowe's Application is the private property of
*  Lowe's Companies Inc. Any distribution of this software
*  is unlawful and prohibited.
*/
package com.demo.openweatherapp.presentation

import com.google.gson.Gson
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations.openMocks

open class BaseTest {
    protected val gson = Gson()

    @Before
    open fun setup() {
        openMocks(this)
    }

    fun <T> any(): T = Mockito.any<T>()
}