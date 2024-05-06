package com.aos.floney.view.settleup

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.BuildConfig
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSettleUpOutcomesSelectBinding
import com.aos.floney.databinding.FragmentSettleUpPeriodSelectBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.setting.BookSettingActivity
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.Outcomes
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SettleUpOutcomesSelectFragment : BaseFragment<FragmentSettleUpOutcomesSelectBinding, SettleUpOutcomesSelectViewModel>(R.layout.fragment_settle_up_outcomes_select) , UiOutcomesSelectModel.OnItemClickListener {
    private var mRewardAd: RewardedAd? = null

    override fun onItemClick(item: Outcomes) {
        viewModel.settingSettlementOutcomes(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
        setUpAdMob()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@SettleUpOutcomesSelectFragment)
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
            // 다음 페이지 이동
            viewModel.nextPage.collect {
                if(it) {
                    if (mRewardAd != null) {
                        mRewardAd?.show(requireActivity(), OnUserEarnedRewardListener {
                            fun onUserEarnedReward(rewardItem: RewardItem) {
                                val rewardAmount = rewardItem.amount
                                var rewardType = rewardItem.type

                            }
                        })
                        mRewardAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                goToSettlementCompleteFragment()
                                mRewardAd = null
                                setUpAdMob()
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                mRewardAd = null
                            }
                        }
                    } else {

                    }

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

    }
    fun goToSettlementCompleteFragment()
    {
        val action = SettleUpOutcomesSelectFragmentDirections
            .actionSettleUpOutcomesSelectFragmentToSettleUpCompleteFragment(
                viewModel.memberArray.value!!,
                viewModel.startDate.value!!,
                viewModel.endDate.value!!,
                viewModel.outcome.value!!,
                viewModel.userEmail.value!!)
        findNavController().navigate(action)
    }
    private fun setUpAdMob(){
        MobileAds.initialize(requireContext())

        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(requireContext(),
            BuildConfig.google_app_reward_key, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mRewardAd = null
            }

            override fun onAdLoaded(ad: RewardedAd) {
                mRewardAd = ad
            }
        })
    }

}