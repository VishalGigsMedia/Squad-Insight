package com.project.prediction_hub.ui.home.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.prediction_hub.common_helper.InputParams
import com.prediction_hub.ui.home.model.MatchListModel
import com.project.prediction_hub.R
import com.project.prediction_hub.common_helper.ConstantHelper.apiFailed
import com.project.prediction_hub.common_helper.ConstantHelper.failed
import com.project.prediction_hub.common_helper.ConstantHelper.noInternet
import com.project.prediction_hub.common_helper.DefaultHelper.encrypt
import com.project.prediction_hub.common_helper.DefaultHelper.getDeviceId
import com.project.prediction_hub.common_helper.DefaultHelper.isOnline
import com.prediction_hub.retrofit.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchListRepository {
    var modelOfferList: MatchListModel? = null

    fun getMatchList(
        context: Context,
        apiService: APIService,
        offset: Int,
        nextLimit: Int,
        fcmToken: String
    ): MutableLiveData<MatchListModel> {
        val mutableLiveData: MutableLiveData<MatchListModel> = MutableLiveData()

        if (isOnline()) {
            val inputParams = InputParams()
            inputParams.offset = encrypt(offset.toString())
            inputParams.limit = encrypt(nextLimit.toString())
            inputParams.device_Id = encrypt(getDeviceId(context))
            inputParams.fcm_key = encrypt(fcmToken)
            apiService.getMatchList(inputParams).enqueue(object : Callback<MatchListModel> {
                override fun onResponse(
                    call: Call<MatchListModel>,
                    response: Response<MatchListModel>
                ) {

                    if (response.body() != null && response.isSuccessful && response.code() == 200) {
                        modelOfferList = response.body()
                        mutableLiveData.value = modelOfferList
                    } else if (response.code() == 500) {
                        modelOfferList =
                            MatchListModel(null, 0, response.body()?.message.toString(), failed)
                    } else {
                        modelOfferList =
                            MatchListModel(null, 0, response.body()?.message.toString(), failed)
                    }
                }

                override fun onFailure(call: Call<MatchListModel>, t: Throwable) {
                    modelOfferList = MatchListModel(null, 0, t.toString(), apiFailed)
                }
            })
        } else {
            modelOfferList =
                MatchListModel(null, 0, context.getString(R.string.no_internet), noInternet)
        }
        return mutableLiveData
    }


}