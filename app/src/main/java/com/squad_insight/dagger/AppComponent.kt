package com.squad_insight.dagger

import com.squad_insight.MainActivity
import com.squad_insight.common_helper.Application
import com.squad_insight.ui.home.BasketballMatchListFragment
import com.squad_insight.ui.home.CricketMatchListFragment
import com.squad_insight.ui.home.FootballMatchListFragment
import com.squad_insight.ui.home.HomeFragment
import com.squad_insight.ui.home.MatchDetailFragment
import com.squad_insight.ui.home.TeamDetailFragment
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

    fun inject(teamDetailFragment: TeamDetailFragment)


    fun inject(mainActivity: MainActivity)

}