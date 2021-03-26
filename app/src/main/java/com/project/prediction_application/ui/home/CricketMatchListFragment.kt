package com.project.prediction_application.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.prediction_application.common_helper.Application
import com.project.prediction_application.common_helper.DefaultHelper
import com.project.prediction_application.databinding.FragmentMatchListBinding
import com.project.prediction_application.retrofit.APIService
import com.project.prediction_application.ui.home.adapter.MatchListAdapter
import com.project.prediction_application.ui.home.model.MatchListModel
import com.project.prediction_application.ui.home.view_model.MatchListViewModel
import javax.inject.Inject

class CricketMatchListFragment : Fragment(), MatchListAdapter.MatchListClickListener {
    @Inject
    lateinit var apiService: APIService

    private lateinit var layoutManager: LinearLayoutManager
    private var list: ArrayList<MatchListModel> = ArrayList()
    private var adapter: MatchListAdapter? = null

    private var matchListClickListener: MatchListAdapter.MatchListClickListener? = null

    private lateinit var viewModel: MatchListViewModel
    private var mBinding: FragmentMatchListBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.instance?.getComponent()?.inject(this)
        viewModel = ViewModelProvider(this).get(MatchListViewModel::class.java)
        mBinding = FragmentMatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        this.matchListClickListener = this
        getMatchList()
        /*list.clear()
        list.add("16-03-2021 14:10:00")
        list.add("25-03-2021 14:11:00")
        list.add("25-03-2021 14:12:00")
        list.add("26-03-2021 14:13:00")
        list.add("25-03-2021 14:11:00")
        layoutManager = LinearLayoutManager(context)
        mBinding?.rvHome?.layoutManager = layoutManager
        adapter = MatchListAdapter(
            context as FragmentActivity, list,
            matchListClickListener as MatchListFragment
        )
        mBinding?.rvHome?.adapter = adapter
        adapter?.notifyDataSetChanged()*/
    }

    private fun getMatchList() {
        viewModel.getMatchList(context as FragmentActivity, apiService, 0, 10)
            ?.observe(viewLifecycleOwner, { matchListModel ->
                if (matchListModel != null) {

                    if(matchListModel.data.match_list.isNotEmpty()){
                        layoutManager = LinearLayoutManager(context)
                        mBinding?.rvHome?.layoutManager = layoutManager
                        adapter = MatchListAdapter(
                            context as FragmentActivity, matchListModel.data.match_list,
                            matchListClickListener as CricketMatchListFragment
                        )
                        mBinding?.rvHome?.adapter = adapter
                        adapter?.notifyDataSetChanged()
                    }


                }
            })

    }

    override fun onMatchClick(id: String, browser: String) {
        DefaultHelper.openFragment(context as FragmentActivity, MatchDetailFragment(), true)
    }

}