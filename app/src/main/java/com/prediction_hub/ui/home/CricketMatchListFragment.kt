package com.project.prediction_hub.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.project.prediction_hub.R
import com.project.prediction_hub.common_helper.Application
import com.project.prediction_hub.common_helper.ConstantHelper
import com.project.prediction_hub.common_helper.DefaultHelper
import com.project.prediction_hub.common_helper.DefaultHelper.decrypt
import com.project.prediction_hub.common_helper.DefaultHelper.forceLogout
import com.project.prediction_hub.common_helper.DefaultHelper.showToast
import com.project.prediction_hub.databinding.FragmentMatchListBinding
import com.prediction_hub.retrofit.APIService
import com.prediction_hub.ui.home.model.MatchListModel
import com.prediction_hub.ui.home.adapter.MatchListAdapter
import com.project.prediction_hub.ui.home.view_model.MatchListViewModel
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

    @SuppressLint("SimpleDateFormat")
    private fun init() {
        this.matchListClickListener = this

        getFcmToken()


        //epoch time
        /* val dt = Instant.ofEpochSecond(1510500494)
             .atZone(ZoneId.systemDefault())
             .toLocalDateTime()*/

        /*val serverSideFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val receivedDate = "2021-04-25 14:05:00"
        val reformattedStr: String = formatter.format(serverSideFormat.parse(receivedDate))
        println("formattedDate : $reformattedStr")*/

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

    private fun getMatchList(fcmToken: String) {
        viewModel.getMatchList(context as FragmentActivity, apiService, 0, 10,fcmToken)
            ?.observe(viewLifecycleOwner, { matchListModel ->
                if (matchListModel != null) {

                    if (matchListModel.force_logout != ConstantHelper.forceLogout) {
                        forceLogout(context as FragmentActivity, "")
                    }

                    when (matchListModel.status) {
                        ConstantHelper.success -> {
                            matchListModel.data?.match_list?.let { setData(it) }
                        }
                        ConstantHelper.failed -> {
                            setNoDataLayout(matchListModel.message.toString())
                        }
                        ConstantHelper.apiFailed -> {
                            showToast(context, matchListModel.message.toString())
                        }
                        ConstantHelper.noInternet -> {
                            setNoDataLayout(matchListModel.message.toString())
                        }
                        else -> {
                            setNoDataLayout(matchListModel.message.toString())
                        }
                    }

                }
            })

    }

    private fun setData(matchList: List<MatchListModel.Data.Match>) {
        if (matchList.isNotEmpty()) {
            layoutManager = LinearLayoutManager(context)
            mBinding?.rvHome?.layoutManager = layoutManager
            adapter = MatchListAdapter(
                context as FragmentActivity, matchList,
                matchListClickListener as CricketMatchListFragment
            )
            mBinding?.rvHome?.adapter = adapter
            adapter?.notifyDataSetChanged()

            mBinding?.clNoData?.clNoDataParent?.visibility = View.GONE
            mBinding?.rvHome?.visibility = View.VISIBLE
        } else {
            setNoDataLayout(getString(R.string.no_data_available))
        }
    }

    private fun setNoDataLayout(msg: String) {
        if (msg.isNotEmpty()) {
            mBinding?.rvHome?.visibility = View.GONE
            mBinding?.clNoData?.tvNoDataLayout?.text = decrypt(msg)
            mBinding?.clNoData?.clNoDataParent?.visibility = View.VISIBLE
        }
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
            println("FcmToken: $token")
            getMatchList(token)

        }).toString()
    }
}