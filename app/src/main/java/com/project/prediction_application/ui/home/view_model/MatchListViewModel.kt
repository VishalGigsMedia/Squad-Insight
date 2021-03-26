package com.project.prediction_application.ui.home.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.prediction_application.retrofit.APIService
import com.project.prediction_application.ui.home.model.MatchListModel
import com.project.prediction_application.ui.home.repository.MatchListRepository

class MatchListViewModel : ViewModel() {

    private val matchListRepository: MatchListRepository = MatchListRepository()
    private var matchListModel: LiveData<MatchListModel>? = null


    fun getMatchList(context: Context,
                     apiService: APIService,
                     offset: Int,
                     nextLimit: Int): LiveData<MatchListModel>? {
        matchListModel = matchListRepository.getMatchList(context,apiService,offset,nextLimit)
        return matchListModel
    }

}