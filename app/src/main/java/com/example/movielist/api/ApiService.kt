package com.example.movielist.api


import com.example.movielist.data.DataModel
import com.example.movielist.data.Results
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.themoviedb.org/4/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

interface ApiService{

    @GET("list/{list_id}")
    fun getAllData(@Path("list_id") listId:Int, @Query("page") page:Int, @Query("api_key") apiKey:String): Call<DataModel>

}

object ApiCall {

    val retrofitService: ApiService by lazy{
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.readTimeout(30, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)

        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .build()
        retrofit.create(ApiService::class.java)

    }
}
