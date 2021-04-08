package com.squad_insight

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.squad_insight.retrofit.APIService

class UpdateVersionViewModel : ViewModel() {

    private val updateVersionRepository: UpdateVersionRepository = UpdateVersionRepository()
    private var updateApplicationModel: LiveData<UpdateApplicationModel>? = null


    fun checkVersion(context: Context, apiService: APIService, versionCode: String): LiveData<UpdateApplicationModel> {
        updateApplicationModel = updateVersionRepository.checkVersion(context, apiService, versionCode)
        return updateApplicationModel!!
    }

}