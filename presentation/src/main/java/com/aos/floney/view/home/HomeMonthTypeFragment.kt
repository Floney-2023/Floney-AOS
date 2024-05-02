package com.aos.floney.view.home

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentHomeMonthTypeBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.home.MonthMoney
import com.aos.model.home.UiBookMonthModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeMonthTypeFragment : BaseFragment<FragmentHomeMonthTypeBinding, HomeMonthTypeViewModel>(R.layout.fragment_home_month_type), UiBookMonthModel.OnItemClickListener{

    private val activityViewModel: HomeViewModel by activityViewModels()

    override fun onItemClick(item: MonthMoney) {
        Timber.e("item $item")
        if(item.day != "") {
            val activity = requireActivity() as HomeActivity
            activity.onClickCalendarItem(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setUpViewModelObserver()
        setUpCalendarRecyclerView()

        Timber.e("onViewCreated")
    }

    private fun setupUi() {
        binding.setVariable(BR.eventHolder, this@HomeMonthTypeFragment)
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
                viewModel.getBookMonth(it)
            }
        }
        activityViewModel.bookInfo.observe(viewLifecycleOwner) {
            viewModel.getBookMonth(activityViewModel.getFormatDateMonth())
        }
    }

}