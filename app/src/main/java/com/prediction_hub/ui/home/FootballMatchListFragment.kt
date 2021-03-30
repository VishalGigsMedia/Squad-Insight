package com.project.prediction_hub.ui.home

import androidx.fragment.app.Fragment
import com.prediction_hub.retrofit.APIService
import javax.inject.Inject

class FootballMatchListFragment : Fragment() {
    @Inject
    lateinit var apiService: APIService
}