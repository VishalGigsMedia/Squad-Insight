package com.prediction_hub.retrofit


import com.prediction_hub.common_helper.InputParams
import com.prediction_hub.ui.home.model.MatchDetailsModel
import com.prediction_hub.ui.home.model.MatchListModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface APIService {

    @POST("get_cricket_match_lists")
    fun getCricketMatchList(@Body inputParams: InputParams): Call<MatchListModel>

    @POST("get_football_match_lists")
    fun getFootballMatchList(@Body inputParams: InputParams): Call<MatchListModel>

    @POST("get_basketball_match_lists")
    fun getBasketballMatchList(@Body inputParams: InputParams): Call<MatchListModel>


    @POST("get_match_details")
    fun getMatchDetails(@Body inputParams: InputParams): Call<MatchDetailsModel>


}