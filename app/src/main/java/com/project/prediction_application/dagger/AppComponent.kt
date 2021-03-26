package com.project.prediction_application.dagger

import com.project.prediction_application.common_helper.Application
import com.project.prediction_application.ui.home.BasketballMatchListFragment
import com.project.prediction_application.ui.home.HomeFragment
import com.project.prediction_application.ui.home.CricketMatchListFragment
import com.project.prediction_application.ui.home.FootballMatchListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent {

    fun inject(application: Application)

    fun inject(homeFragment: HomeFragment)

    fun inject(matchListFragment: CricketMatchListFragment)

    fun inject(footballMatchListFragment: FootballMatchListFragment)

    fun inject(basketballMatchListFragment: BasketballMatchListFragment)


}