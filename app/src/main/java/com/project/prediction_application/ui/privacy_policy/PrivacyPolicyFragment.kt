package com.project.prediction_application.ui.privacy_policy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.prediction_application.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : Fragment() {


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      /*  privacyPolicyViewModel.text.observe(viewLifecycleOwner, {
            mBinding?.textGallery?.text = it
        })*/

    }
}