package com.project.prediction_application.ui.privacy_policy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.prediction_application.MainActivity
import com.project.prediction_application.common_helper.OnCurrentFragmentVisibleListener
import com.project.prediction_application.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : Fragment() {

    private var callback: OnCurrentFragmentVisibleListener? = null

    private lateinit var privacyPolicyViewModel: PrivacyPolicyViewModel

    private var mBinding: FragmentPrivacyPolicyBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        privacyPolicyViewModel = ViewModelProvider(this).get(PrivacyPolicyViewModel::class.java)
        mBinding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    fun setOnCurrentFragmentVisibleListener(activity: MainActivity) {
        callback = activity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callback?.onSetToolbarTitle(true, PrivacyPolicyFragment::class.java.simpleName)

      /*  privacyPolicyViewModel.text.observe(viewLifecycleOwner, {
            mBinding?.textGallery?.text = it
        })*/

    }
}