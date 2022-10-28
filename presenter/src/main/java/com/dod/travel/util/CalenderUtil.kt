package com.dod.travel.util

import java.util.*

object CalenderUtil {

    private val cal = Calendar.getInstance()

    fun year(): Int = cal.get(Calendar.YEAR)
    fun month(): Int = cal.get(Calendar.MONTH)
    fun day(): Int = cal.get(Calendar.DAY_OF_MONTH)
    fun hour(): Int = cal.get(Calendar.HOUR_OF_DAY)
    fun min(): Int = cal.get(Calendar.MINUTE)
    fun sec(): Int = cal.get(Calendar.SECOND)
}