package com.prediction_hub.ui.home.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.prediction_hub.retrofit.APIService
import com.prediction_hub.ui.home.model.MatchListModel
import com.prediction_hub.ui.home.repository.MatchListRepository

class MatchListViewModel : ViewModel() {

    private val matchListRepository: MatchListRepository = MatchListRepository()
    private var matchListModel: LiveData<MatchListModel>? = null


    fun getMatchList(
        context: Context,
        apiService: APIService,
        offset: Int,
        nextLimit: Int,
        fcmToken: String
    ): LiveData<MatchListModel>? {
        matchListModel =
            matchListRepository.getMatchList(context, apiService, offset, nextLimit, fcmToken)
        return matchListModel
    }


    fun getFootballMatchList(
        context: Context,
        apiService: APIService,
        offset: Int,
        nextLimit: Int,
        fcmToken: String
    ): LiveData<MatchListModel>? {
        matchListModel = matchListRepository.getFootballMatchList(
            context,
            apiService,
            offset,
            nextLimit,
            fcmToken
        )
        return matchListModel
    }

    fun getBasketballMatchList(
        context: Context,
        apiService: APIService,
        offset: Int,
        nextLimit: Int,
        fcmToken: String
    ): LiveData<MatchListModel>? {
        matchListModel = matchListRepository.getBasketballMatchList(
            context,
            apiService,
            offset,
            nextLimit,
            fcmToken
        )
        return matchListModel
    }

}