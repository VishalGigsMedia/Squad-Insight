package com.project.prediction_application.ui.home

import androidx.fragment.app.Fragment
import com.project.prediction_application.retrofit.APIService
import javax.inject.Inject

class BasketballMatchListFragment : Fragment() {
    @Inject
    lateinit var apiService: APIService
}