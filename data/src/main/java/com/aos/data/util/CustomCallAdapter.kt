package com.aos.data.util

import com.aos.util.NetworkState
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class CustomCallAdapter<R : Any>(private val responseType: Type) :
    CallAdapter<R, Call<NetworkState<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Call<NetworkState<R>> = CustomCall(call)
}