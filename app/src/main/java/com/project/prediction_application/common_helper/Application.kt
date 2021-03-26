package com.project.prediction_application.common_helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.project.prediction_application.R
import com.project.prediction_application.dagger.AppComponent
import com.project.prediction_application.dagger.AppModule
import com.project.prediction_application.dagger.DaggerAppComponent
import com.project.prediction_application.dagger.RetrofitModule


@SuppressLint("StaticFieldLeak")
class Application : Application() {

    companion object {
        public var instance: com.project.prediction_application.common_helper.Application? = null
        lateinit var mComponent: AppComponent
        var appContext: Context? = null

        fun get(): com.project.prediction_application.common_helper.Application? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext

        //initialize dagger
        mComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .retrofitModule(RetrofitModule(this, getString(R.string.server_url)))
            .build()
        mComponent.inject(this)
    }


    public fun getComponent(): AppComponent {
        return mComponent
    }
}