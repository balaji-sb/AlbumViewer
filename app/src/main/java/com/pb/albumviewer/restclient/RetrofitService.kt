package com.pb.albumviewer.restclient

import com.pb.albumviewer.utils.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by balaji on 12/9/20 9:24 AM
 */


object RetrofitService {

    private val retrofit = Retrofit.Builder().baseUrl(Const.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient())
        .build()

    fun <S> createService(serviceClass: Class<S>?): S {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        return retrofit.create(serviceClass)
    }

    private fun okHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor = loggingInterceptor)
        return builder.build()
    }

}