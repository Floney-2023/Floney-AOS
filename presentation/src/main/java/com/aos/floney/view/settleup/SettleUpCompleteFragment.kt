package com.aos.floney.view.settleup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.BuildConfig
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSettleUpCompleteBinding
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
class SettleUpCompleteFragment : BaseFragment<FragmentSettleUpCompleteBinding, SettleUpCompleteViewModel>(R.layout.fragment_settle_up_complete) , UiSettlementAddModel.OnItemClickListener {

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
        binding.setVariable(BR.eventHolder, this@SettleUpCompleteFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 다음 페이지 이동
            viewModel.back.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }
        repeatOnStarted {
            // 처음 정산 페이지로
            viewModel.settlementPage.collect {
                if(it) {
                    val activity = requireActivity() as SettleUpActivity
                    activity.startSettleUpActivity()
                }
            }
        }
        repeatOnStarted {
            // 작성하러 가기 -> 홈 화면 가기
            viewModel.getInform.collect {
                Timber.e("nextPage $it")
                if(it) {
                    viewModel.saveInviteAlarm()
                }
            }
        }
        repeatOnStarted {
            // 정산 공유 링크
            viewModel.settlementSharePage.collect {
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
}