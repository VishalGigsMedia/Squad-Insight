package com.project.prediction_hub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.prediction_hub.MainActivity
import com.project.prediction_hub.common_helper.OnCurrentFragmentVisibleListener
import com.project.prediction_hub.databinding.FragmentMatchDetailsBinding
import com.project.prediction_hub.ui.home.adapter.MatchDetailsAdapter

class MatchDetailFragment : Fragment() {

    private var callback: OnCurrentFragmentVisibleListener? = null

    private lateinit var layoutManager: LinearLayoutManager
    private var list: ArrayList<String> = ArrayList()
    private var adapter: MatchDetailsAdapter? = null

    private var mBinding: FragmentMatchDetailsBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        callback?.onSetToolbarTitle(true, MatchDetailFragment::class.java.simpleName)

        layoutManager = LinearLayoutManager(context)
        mBinding?.rvMatchDetails?.layoutManager = layoutManager
        adapter = MatchDetailsAdapter(
            context as FragmentActivity, list
        )
        mBinding?.rvMatchDetails?.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    fun setOnCurrentFragmentVisibleListener(activity: MainActivity) {
        callback = activity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}