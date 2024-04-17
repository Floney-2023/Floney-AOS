package com.aos.floney.view.analyze

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentAnalyzePlanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyzePlanFragment : BaseFragment<FragmentAnalyzePlanBinding, AnalyzePlanViewModel>(R.layout.fragment_analyze_plan) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}