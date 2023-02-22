package com.ibm.rides.core.utils

import com.ibm.rides.remote.dto.ErrorHandler


sealed class Resource<T>(val data: T? = null, val message: String? = null ,val errorBody: ErrorHandler? = null) {
    class Loading<T>(): Resource<T>( )
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String?,   errorBody: ErrorHandler?): Resource<T>(  message =  message, errorBody = errorBody )

}
