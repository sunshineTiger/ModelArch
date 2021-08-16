package com.example.basemodule.http

import android.text.TextUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @ClassName HttpManger
 * @Author zhangHong
 * @Date 2021/8/2 10:59
 */
class HttpManger() {
    private lateinit var baseUrl: String

    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            HttpManger()
        }
    }

    init {
        initHttp()
    }

    private fun initHttp() {
        //初始化Retrofit
//        val retrofit = Retrofit.Builder()
//            .baseUrl(if (TextUtils.isEmpty(baseUrl)) "" else baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .build()
    }

    public fun setBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }
}