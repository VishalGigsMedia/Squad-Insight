package com.squad_insight.ui.home.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.squad_gyan.ui.home.model.TeamDetailsModel
import com.squad_insight.retrofit.APIService
import com.squad_insight.ui.home.model.MatchDetailsModel
import com.squad_insight.ui.home.model.MatchListModel
import com.squad_insight.ui.home.repository.MatchListRepository

class MatchListViewModel : ViewModel() {

    private val matchListRepository: MatchListRepository = MatchListRepository()
    private var matchListModel: LiveData<MatchListModel>? = null
    private var matchDetailsModel: LiveData<MatchDetailsModel>? = null
    private var teamDetailsModel: LiveData<TeamDetailsModel>? = null


    fun getCricketMatchList(
        context: Context, apiService: APIService, offset: Int, nextLimit: Int, fcmToken: String
    ): LiveData<MatchListModel>? {
        matchListModel = matchListRepository.getCricketMatchList(
            context, apiService, offset, nextLimit, fcmToken
        )
        return matchListModel
    }

    fun getFootballMatchList(
        context: Context, apiService: APIService, offset: Int, nextLimit: Int, fcmToken: String
    ): LiveData<MatchListModel>? {
        matchListModel = matchListRepository.getFootballMatchList(
            context, apiService, offset, nextLimit, fcmToken
        )
        return matchListModel
    }

    fun getBasketballMatchList(
        context: Context, apiService: APIService, offset: Int, nextLimit: Int, fcmToken: String
    ): LiveData<MatchListModel>? {
        matchListModel = matchListRepository.getBasketballMatchList(
            context, apiService, offset, nextLimit, fcmToken
        )
        return matchListModel
    }

    fun getMatchDetails(
        context: Context, apiService: APIService, matchId: String, matchType: String
    ): LiveData<MatchDetailsModel>? {
        matchDetailsModel = matchListRepository.getMatchDetails(context, apiService, matchId, matchType)
        return matchDetailsModel
    }


    fun getTeamDetails(context: Context, apiService: APIService, matchId: String, matchType: String): LiveData<TeamDetailsModel>? {
        teamDetailsModel = matchListRepository.getTeamDetails(context, apiService, matchId, matchType)
        return teamDetailsModel
    }

}