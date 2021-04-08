package com.squad_insight.common_helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.squad_insight.dagger.AppComponent
import com.squad_insight.dagger.AppModule
import com.squad_insight.dagger.DaggerAppComponent
import com.squad_insight.dagger.RetrofitModule
import com.squad_insight.R


@SuppressLint("StaticFieldLeak")
class Application : Application() {

    companion object {
        public var instance: com.squad_insight.common_helper.Application? = null
        lateinit var mComponent: AppComponent
        var appContext: Context? = null

        fun get(): com.squad_insight.common_helper.Application? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext

        //initialize dagger
        mComponent = DaggerAppComponent.builder().appModule(AppModule(this)).retrofitModule(RetrofitModule(this, getString(R.string.server_url))).build()
        mComponent.inject(this)
    }


    public fun getComponent(): AppComponent {
        return mComponent
    }
}