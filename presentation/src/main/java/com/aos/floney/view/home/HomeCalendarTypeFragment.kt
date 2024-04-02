package com.aos.floney.view.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentHomeCalendarTypeBinding
import com.aos.model.home.ListData
import com.aos.model.home.UiBookMonthModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeCalendarTypeFragment : BaseFragment<FragmentHomeCalendarTypeBinding, HomeCalendarTypeViewModel>(R.layout.fragment_home_calendar_type), UiBookMonthModel.OnItemClickListener {
    override fun onItemClick(item: ListData) {
        Timber.e("onItemClick")
        Timber.e("item $item")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        viewModel.getBookMonth()
    }

    private fun setupUi() {
        binding.setVariable(BR.eventHolder, this@HomeCalendarTypeFragment)
    }

}