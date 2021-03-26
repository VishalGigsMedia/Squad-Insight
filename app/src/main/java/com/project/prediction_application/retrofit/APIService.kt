package com.project.prediction_application.retrofit


import com.project.prediction_application.common_helper.InputParams
import com.project.prediction_application.ui.home.model.MatchListModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface APIService {

    @POST("get_cricket_match_lists")
    fun getMatchList(@Body inputParams: InputParams): Call<MatchListModel> //LoginModel


}