package com.example.hava_durumu.api

import okhttp3.ResponseBody

sealed class GenericResult<T> {
    data class Loading<T>(var loading: Boolean = true) : GenericResult<T>()
    data class Success<T>(val data: T) : GenericResult<T>()
    data class Failure<T>(val exception: ResponseBody?) : GenericResult<T>()
}
