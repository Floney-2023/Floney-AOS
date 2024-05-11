package com.aos.floney.view.settleup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.BuildConfig
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSettleUpCompleteBinding
import com.aos.floney.databinding.FragmentSettleUpDetailSeeBinding
import com.aos.floney.databinding.FragmentSettleUpOutcomesSelectBinding
import com.aos.floney.databinding.FragmentSettleUpPeriodSelectBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.Details
import com.aos.model.settlement.Outcomes
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import com.aos.model.settlement.UiSettlementAddModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SettleUpDetailSeeFragment : BaseFragment<FragmentSettleUpDetailSeeBinding, SettleUpDetailSeeViewModel>(R.layout.fragment_settle_up_detail_see) , UiSettlementAddModel.OnItemClickListener {

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    override fun onItemClick(item: Details) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@SettleUpDetailSeeFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }

        repeatOnStarted {
            // 처음 정산 페이지로 (공유 페이지 - 임시)
            viewModel.sharedPage.collect {
                if(it) {
                    onSharedBtnClicked()
                }
            }
        }

    }
    private fun onSharedBtnClicked() {
        val url = "https://floney.onelink.me${BuildConfig.appsflyer_settlement_url}?settlementId=${viewModel.settlementModel.value!!.id ?: ""}&bookKey=${sharedPreferenceUtil.getString("bookKey", "")}"
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/html"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(sharingIntent, "Share using text"))
    }
    fun printBackStack() {
        val fragmentManager = parentFragmentManager
        val count = fragmentManager.backStackEntryCount
        Log.d("BackStack", "Total Entries: $count")
        for (i in 0 until count) {
            // 백스택에 있는 각 프래그먼트의 이름을 가져옵니다.
            val backStackEntry = fragmentManager.getBackStackEntryAt(i)
            Log.d("BackStack", "Entry $i: ${backStackEntry.name}")
        }
        if (count == 0) {
            Log.d("BackStack", "BackStack is empty")
        }
    }
}