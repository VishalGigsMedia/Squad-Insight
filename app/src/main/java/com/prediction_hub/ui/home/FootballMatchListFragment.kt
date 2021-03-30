package com.prediction_hub.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.prediction_hub.retrofit.APIService
import com.prediction_hub.ui.home.adapter.FootballMatchListAdapter
import com.prediction_hub.ui.home.model.MatchListModel
import com.prediction_hub.ui.home.view_model.MatchListViewModel
import com.project.prediction_hub.R
import com.project.prediction_hub.common_helper.Application
import com.project.prediction_hub.common_helper.ConstantHelper
import com.project.prediction_hub.common_helper.DefaultHelper
import com.project.prediction_hub.databinding.FragmentMatchListBinding
import com.project.prediction_hub.ui.home.MatchDetailFragment
import javax.inject.Inject

class FootballMatchListFragment : Fragment(), FootballMatchListAdapter.MatchListClickListener {
    @Inject
    lateinit var apiService: APIService

    private lateinit var layoutManager: LinearLayoutManager
    private var list: ArrayList<MatchListModel.Data.Match> = ArrayList()
    private var adapter: FootballMatchListAdapter? = null

    private var matchListClickListener: FootballMatchListAdapter.MatchListClickListener? = null

    private var offset = 0
    private var nextLimit = 20
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    private var fcmToken: String = ""

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

    @SuppressLint("SimpleDateFormat")
    private fun init() {
        this.matchListClickListener = this

    }

    override fun onResume() {
        super.onResume()
        swipeToRefresh()
        initRefreshData()
    }

    private fun swipeToRefresh() {
        mBinding?.srl?.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                context as FragmentActivity,
                R.color.colorPrimary
            )
        )
        mBinding?.srl?.setColorSchemeColors(Color.WHITE)

        mBinding?.srl?.setOnRefreshListener {
            initRefreshData()
            mBinding?.srl?.isRefreshing = false
        }
    }

    private fun initRefreshData() {
        this.list.clear()
        this.offset = 0
        this.nextLimit = 20
        setAdapter()
        getFcmToken()
        addScrollListener()
    }

    private fun setAdapter() {
        layoutManager = LinearLayoutManager(context)
        mBinding?.rvHome?.layoutManager = layoutManager
        adapter = FootballMatchListAdapter(
            context as FragmentActivity, list,
            matchListClickListener as FootballMatchListFragment
        )
        mBinding?.rvHome?.adapter = adapter
        adapter?.notifyDataSetChanged()
    }


    override fun onMatchClick(id: String, browser: String) {
        DefaultHelper.openFragment(context as FragmentActivity, MatchDetailFragment(), true)
    }

    private fun getFcmToken(): String {
        return Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                // Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result.toString()
            this.fcmToken = token
            getMatchList(fcmToken, offset, nextLimit)

        }).toString()
    }

    private fun getMatchList(fcmToken: String, offset: Int, nextLimit: Int) {
        showLoader()
        viewModel.getFootballMatchList(
            context as FragmentActivity,
            apiService,
            offset,
            nextLimit,
            fcmToken
        )
            ?.observe(viewLifecycleOwner, { matchListModel ->
                hideLoader()
                if (matchListModel != null) {

                    if (matchListModel.force_logout != ConstantHelper.forceLogout) {
                        DefaultHelper.forceLogout(context as FragmentActivity, "")
                    }

                    when (matchListModel.status) {
                        ConstantHelper.success -> {
                            this.offset = 20
                            this.list =
                                matchListModel.data?.match_list as ArrayList<MatchListModel.Data.Match>
                            adapter?.addData(this.list)

                            mBinding?.clNoData?.clNoDataParent?.visibility = View.GONE
                            mBinding?.rvHome?.visibility = View.VISIBLE

                        }
                        ConstantHelper.failed -> {
                            setNoDataLayout(DefaultHelper.decrypt(matchListModel.message.toString()))
                        }
                        ConstantHelper.apiFailed -> {
                            DefaultHelper.showToast(
                                context,
                                DefaultHelper.decrypt(matchListModel.message.toString())
                            )
                        }
                        ConstantHelper.noInternet -> {
                            setNoDataLayout(matchListModel.message.toString())
                        }
                        else -> {
                            setNoDataLayout(DefaultHelper.decrypt(matchListModel.message.toString()))
                        }
                    }

                }
            })
    }


    private fun addScrollListener() {
        mBinding?.rvHome?.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                //you have to call load more items to get more data
                getMoreMatchList(fcmToken, offset, nextLimit)
            }
        })
    }

    private fun getMoreMatchList(fcmToken: String, offset: Int, nextLimit: Int) {
        print("loadMore : Loading More Data !!!.....")
        mBinding?.clProgressBar?.clProgressBarParent?.visibility = View.VISIBLE
        viewModel.getFootballMatchList(
            context as FragmentActivity,
            apiService,
            offset,
            nextLimit,
            fcmToken
        )
            ?.observe(viewLifecycleOwner, { matchListModel ->
                mBinding?.clProgressBar?.clProgressBarParent?.visibility = View.GONE
                if (matchListModel != null) {
                    if (matchListModel.force_logout != ConstantHelper.forceLogout) {
                        DefaultHelper.forceLogout(context as FragmentActivity, "")
                    }

                    when (matchListModel.status) {
                        ConstantHelper.success -> {
                            isLoading = false
                            this.offset += 20
                            adapter?.addData(matchListModel.data?.match_list as ArrayList<MatchListModel.Data.Match>)
                        }
                        ConstantHelper.failed -> {
                            DefaultHelper.showToast(
                                context,
                                DefaultHelper.decrypt(matchListModel.message.toString())
                            )
                        }
                        ConstantHelper.apiFailed -> {
                            DefaultHelper.showToast(
                                context,
                                DefaultHelper.decrypt(matchListModel.message.toString())
                            )
                        }
                        ConstantHelper.noInternet -> {
                            DefaultHelper.showToast(context, matchListModel.message.toString())
                        }
                        else -> {
                            DefaultHelper.showToast(
                                context,
                                DefaultHelper.decrypt(matchListModel.message.toString())
                            )
                        }
                    }

                }
            })

    }

    private fun setNoDataLayout(msg: String) {
        if (msg.isNotEmpty()) {
            mBinding?.rvHome?.visibility = View.GONE
            mBinding?.clNoData?.tvNoDataLayout?.text = DefaultHelper.decrypt(msg)
            mBinding?.clNoData?.clNoDataParent?.visibility = View.VISIBLE
        }
    }

    private fun showLoader() {
        mBinding?.clProgressBar?.clProgressBarParent?.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        mBinding?.clProgressBar?.clProgressBarParent?.visibility = View.GONE
    }
}