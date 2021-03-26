package com.project.prediction_application.ui.about_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.prediction_application.MainActivity
import com.project.prediction_application.common_helper.OnCurrentFragmentVisibleListener
import com.project.prediction_application.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {

    private var callback: OnCurrentFragmentVisibleListener? = null

    private var mBinding: FragmentAboutUsBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callback?.onSetToolbarTitle(true, AboutUsFragment::class.java.simpleName)

    }

    fun setOnCurrentFragmentVisibleListener(activity: MainActivity) {
        callback = activity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}