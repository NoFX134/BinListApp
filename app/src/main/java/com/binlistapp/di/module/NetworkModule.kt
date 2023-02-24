package com.binlistapp.di.module

import com.binlistapp.data.remote.BinApi
import com.binlistapp.utils.functions.ifDebug
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.myproject.currencyconverter.di.scope.ApplicationScope

@Module
class NetworkModule {

    companion object {

        private const val BASE_URL = "https://lookup.binlist.net/"
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        ifDebug { okHttpBuilder.addInterceptor(loggingInterceptor) }
           return okHttpBuilder.build()
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @ApplicationScope
    fun provideApi(retrofit: Retrofit): BinApi = retrofit.create(BinApi::class.java)
}