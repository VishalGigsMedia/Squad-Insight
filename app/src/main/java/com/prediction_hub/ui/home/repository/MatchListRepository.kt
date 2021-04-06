package com.prediction_hub.ui.home.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.prediction_hub.common_helper.ConstantHelper.apiFailed
import com.prediction_hub.common_helper.ConstantHelper.failed
import com.prediction_hub.common_helper.ConstantHelper.noInternet
import com.prediction_hub.common_helper.DefaultHelper.encrypt
import com.prediction_hub.common_helper.DefaultHelper.getDeviceId
import com.prediction_hub.common_helper.DefaultHelper.isOnline
import com.prediction_hub.common_helper.InputParams
import com.prediction_hub.retrofit.APIService
import com.prediction_hub.ui.home.model.MatchDetailsModel
import com.prediction_hub.ui.home.model.MatchListModel
import com.project.prediction_hub.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchListRepository {
    var matchListModel: MatchListModel? = null
    var matchDetailsModel: MatchDetailsModel? = null

    fun getCricketMatchList(
        context: Context, apiService: APIService, offset: Int, nextLimit: Int, fcmToken: String
    ): MutableLiveData<MatchListModel> {
        val mutableLiveData: MutableLiveData<MatchListModel> = MutableLiveData()

        if (isOnline()) {
            val inputParams = InputParams()
            //println("offset : $offset nextLimit : $nextLimit")
            inputParams.offset = encrypt(offset.toString())
            inputParams.limit = encrypt(nextLimit.toString())
            inputParams.device_id = encrypt(getDeviceId(context))
            inputParams.fcm_key = encrypt(fcmToken)
            apiService.getCricketMatchList(inputParams).enqueue(object : Callback<MatchListModel> {
                override fun onResponse(
                    call: Call<MatchListModel>, response: Response<MatchListModel>
                ) {
                    try {
                        matchListModel = response.body()
                        mutableLiveData.value = matchListModel
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<MatchListModel>, t: Throwable) {
                    matchListModel = MatchListModel(null, 0, t.toString(), apiFailed)
                }
            })
        } else {
            matchListModel = MatchListModel(null, 0, context.getString(R.string.no_internet), noInternet)
        }
        return mutableLiveData
    }

    fun getFootballMatchList(
        context: Context, apiService: APIService, offset: Int, nextLimit: Int, fcmToken: String
    ): MutableLiveData<MatchListModel> {
        val mutableLiveData: MutableLiveData<MatchListModel> = MutableLiveData()

        if (isOnline()) {
            val inputParams = InputParams()
            //println("offset : $offset nextLimit : $nextLimit")
            inputParams.offset = encrypt(offset.toString())
            inputParams.limit = encrypt(nextLimit.toString())
            inputParams.device_id = encrypt(getDeviceId(context))
            inputParams.fcm_key = encrypt(fcmToken)
            apiService.getFootballMatchList(inputParams).enqueue(object : Callback<MatchListModel> {
                override fun onResponse(
                    call: Call<MatchListModel>, response: Response<MatchListModel>
                ) {
                    try {
                        matchListModel = response.body()
                        mutableLiveData.value = matchListModel
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<MatchListModel>, t: Throwable) {
                    matchListModel = MatchListModel(null, 0, t.toString(), apiFailed)
                }
            })
        } else {
            matchListModel = MatchListModel(null, 0, context.getString(R.string.no_internet), noInternet)
        }
        return mutableLiveData
    }


    fun getBasketballMatchList(
        context: Context, apiService: APIService, offset: Int, nextLimit: Int, fcmToken: String
    ): MutableLiveData<MatchListModel> {
        val mutableLiveData: MutableLiveData<MatchListModel> = MutableLiveData()

        if (isOnline()) {
            val inputParams = InputParams()
            //println("offset : $offset nextLimit : $nextLimit")
            inputParams.offset = encrypt(offset.toString())
            inputParams.limit = encrypt(nextLimit.toString())
            inputParams.device_id = encrypt(getDeviceId(context))
            inputParams.fcm_key = encrypt(fcmToken)
            apiService.getBasketballMatchList(inputParams).enqueue(object : Callback<MatchListModel> {
                override fun onResponse(
                    call: Call<MatchListModel>, response: Response<MatchListModel>
                ) {
                    try {
                        matchListModel = response.body()
                        mutableLiveData.value = matchListModel
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<MatchListModel>, t: Throwable) {
                    matchListModel = MatchListModel(null, 0, t.toString(), apiFailed)
                }
            })
        } else {
            matchListModel = MatchListModel(null, 0, context.getString(R.string.no_internet), noInternet)
        }
        return mutableLiveData
    }


    fun getMatchDetails(
        context: Context, apiService: APIService, matchId: String, matchType: String
    ): MutableLiveData<MatchDetailsModel> {
        val mutableLiveData: MutableLiveData<MatchDetailsModel> = MutableLiveData()
        if (isOnline()) {
            val inputParams = InputParams()
            inputParams.match_id = matchId//encrypt(matchId)
            inputParams.match_type = encrypt(matchType)
            apiService.getMatchDetails(inputParams).enqueue(object : Callback<MatchDetailsModel> {
                override fun onResponse(
                    call: Call<MatchDetailsModel>, response: Response<MatchDetailsModel>
                ) {
                    try {

                        if (response.body()?.data != null) {
                            matchDetailsModel = response.body()
                            mutableLiveData.value = matchDetailsModel
                        } else {
                            matchDetailsModel = MatchDetailsModel(
                                null, 0, response.body()?.message.toString(), failed
                            )
                            mutableLiveData.value = matchDetailsModel
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<MatchDetailsModel>, t: Throwable) {
                    matchDetailsModel = MatchDetailsModel(null, 0, "API failed", apiFailed)
                    mutableLiveData.value = matchDetailsModel
                }
            })
        } else {
            matchDetailsModel = MatchDetailsModel(null, 0, context.getString(R.string.no_internet), noInternet)
            mutableLiveData.value = matchDetailsModel
        }
        return mutableLiveData
    }


}