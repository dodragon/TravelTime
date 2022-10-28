package com.dod.data

import com.dod.data.api.GroupApi
import com.dod.data.api.TravelApi
import com.dod.data.api.UserApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitInstance {

    const val BASE_URL = "http://192.168.122.237:8083/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .run {
            addInterceptor(HttpLoggingInterceptor().run {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            addInterceptor(AuthInterceptor())
            build()
        }

    val userApi: UserApi by lazy { retrofit.create(UserApi::class.java) }
    val groupApi: GroupApi by lazy { retrofit.create(GroupApi::class.java) }
    val travelApi: TravelApi by lazy { retrofit.create(TravelApi::class.java) }

    class AuthInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain){
            val newRequest = request().newBuilder()
                .addHeader("App_auth", "NjQQqjtz1Wdk3LMF2aARcdL6XgprszD3jxLwwL9srI4=")
                .build()

            proceed(newRequest)
        }
    }
}