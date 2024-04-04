package com.aos.floney.view.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentHomeCalendarTypeBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.home.ListData
import com.aos.model.home.UiBookMonthModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeCalendarTypeFragment : BaseFragment<FragmentHomeCalendarTypeBinding, HomeCalendarTypeViewModel>(R.layout.fragment_home_calendar_type), UiBookMonthModel.OnItemClickListener{

    private val activityViewModel: HomeViewModel by activityViewModels()

    override fun onItemClick(item: ListData) {
        if(item.date != "") {
            Timber.e("item $item")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setUpViewModelObserver()
        setUpCalendarRecyclerView()
    }

    private fun setupUi() {
        binding.setVariable(BR.eventHolder, this@HomeCalendarTypeFragment)
    }

    private fun setUpCalendarRecyclerView() {
        binding.rvCalendar.itemAnimator = null
      }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            activityViewModel.clickedPreviousMonth.collect {
                viewModel.getBookMonth(it)
            }
        }
        repeatOnStarted {
            activityViewModel.clickedNextMonth.collect {
                Timber.e("activityViewModel.clickedNextMonth")
                viewModel.getBookMonth(it)
            }
        }
        repeatOnStarted {
            activityViewModel.showCalendarFragment.collect {
                if(it) {
                    viewModel.getBookMonth()
                }
            }
        }
    }

}