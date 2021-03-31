package com.project.prediction_hub.dagger


import com.prediction_hub.common_helper.Application
import com.prediction_hub.retrofit.APIService
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