package com.pronesh.dictionarydemo.utils


/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */

sealed class ResponseResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(val isLoading: Boolean = true) : ResponseResult<T>(null)
    class Success<T>(data: T?) : ResponseResult<T>(data)
    class Error<T>(message: String?) : ResponseResult<T>(null, message)
}