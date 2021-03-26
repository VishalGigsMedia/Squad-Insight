package com.project.prediction_application.dagger


import com.project.prediction_application.common_helper.Application
import com.project.prediction_application.retrofit.APIService
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