package com.demo.openweatherapp.presentation.util

import android.content.Context
import com.demo.openweatherapp.presentation.util.Constants.EMPTY_STRING

private const val PREF_NAME = "weather"
const val KEY_CITY = "city"

fun storeCityInfo(context: Context, city: String) {
    if (city.isEmpty()) return
    val sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putString(KEY_CITY, city)
    editor.apply()
}

fun getLastCityName(context: Context): String {
    val sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    return sharedPreference.getString(KEY_CITY, EMPTY_STRING).orEmpty()
}