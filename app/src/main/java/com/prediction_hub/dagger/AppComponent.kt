package com.prediction_hub.dagger

import com.prediction_hub.ui.home.*
import com.prediction_hub.common_helper.Application
import com.project.prediction_hub.dagger.AppModule
import com.project.prediction_hub.dagger.RetrofitModule
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

    fun inject(matchDetailFragment: MatchDetailFragment)

}