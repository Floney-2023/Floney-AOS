package com.aos.floney.view.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentHomeDayTypeBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeDayTypeFragment : BaseFragment<FragmentHomeDayTypeBinding, HomeDayTypeViewModel>(R.layout.fragment_home_day_type) {

    private val activityViewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
        activityViewModel.getBookDays(activityViewModel.getFormatDateDay())
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            activityViewModel.clickedPreviousMonth.collect {
                activityViewModel.getBookDays(it)
            }
        }
        repeatOnStarted {
            activityViewModel.clickedNextMonth.collect {
                activityViewModel.getBookDays(it)
            }
        }
        repeatOnStarted {
            activityViewModel.getMoneyDayData.collect {
                viewModel.updateMoneyDay(it)
            }
        }
    }

}