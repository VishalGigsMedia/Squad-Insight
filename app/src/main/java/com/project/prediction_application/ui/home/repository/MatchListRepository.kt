package com.project.prediction_application.ui.home.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.project.prediction_application.common_helper.DefaultHelper.encrypt
import com.project.prediction_application.common_helper.DefaultHelper.isOnline
import com.project.prediction_application.common_helper.InputParams
import com.project.prediction_application.retrofit.APIService
import com.project.prediction_application.ui.home.model.MatchListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchListRepository {
    var modelOfferList: MatchListModel? = null

    fun getMatchList(
        context: Context,
        apiService: APIService,
        offset: Int,
        nextLimit: Int
    ): MutableLiveData<MatchListModel> {
        val mutableLiveData: MutableLiveData<MatchListModel> = MutableLiveData()

        if (isOnline()) {
            val inputParams = InputParams()
            inputParams.offset = encrypt(offset.toString())
            inputParams.limit = encrypt(nextLimit.toString())
            apiService.getMatchList(inputParams).enqueue(object : Callback<MatchListModel> {
                override fun onResponse(
                    call: Call<MatchListModel>,
                    response: Response<MatchListModel>
                ) {

                    if (response.body() != null && response.isSuccessful && response.code() == 200) {
                        modelOfferList = response.body()
                        mutableLiveData.value = modelOfferList
                    } else {

                    }
                }

                override fun onFailure(call: Call<MatchListModel>, t: Throwable) {

                }
            })
        } else {

        }
        return mutableLiveData
    }

}