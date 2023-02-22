package com.ibm.rides.core.utils

import org.junit.Assert.*
import org.junit.Test

class CalculationTest{

    @Test
    fun calculateCarbonEmittedTestCase1(){
        val km = 4345
        assert(Calculation.calculateCarbonEmitted(km) in 0.0..5000.0)
    }

    @Test
    fun calculateCarbonEmittedTestCase2(){
        val km = 6374
        assert(Calculation.calculateCarbonEmitted(km) > 5000.0)
    }
}