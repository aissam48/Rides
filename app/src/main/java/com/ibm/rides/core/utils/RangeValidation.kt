package com.ibm.rides.core.utils

object RangeValidation {

    fun validate(value:Int):Boolean{
        return when(value){
            in 1..100 ->{
                 true
            }
            else ->{
                false
            }
        }
    }
}