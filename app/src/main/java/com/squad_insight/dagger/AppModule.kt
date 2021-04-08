package com.squad_insight.dagger


import com.squad_insight.common_helper.Application
import com.squad_insight.retrofit.APIService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Singleton
    @Provides
    fun provideApplication() = app

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

}