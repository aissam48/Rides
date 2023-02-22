package com.ibm.rides.core.utils

import org.junit.Assert.*
import org.junit.Test

class RangeValidationTest{

    @Test
    fun numberIsValid(){
        val number = 5
        assert(RangeValidation.validate(number) == true)
    }

    @Test
    fun numberIsNotValid(){
        val number = 112
        assert(RangeValidation.validate(number) == false)
    }
}