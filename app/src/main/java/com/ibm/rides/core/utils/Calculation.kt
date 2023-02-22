package com.ibm.rides.core.utils

object Calculation {

    fun calculateCarbonEmitted(km: Int): Double {
        return when (km) {
            in 0..5000 -> {
                 km * 1.0
            }
            else -> {
                val rest = km - 5000
                (rest * 1.5 + 5000)
            }
        }
    }

}