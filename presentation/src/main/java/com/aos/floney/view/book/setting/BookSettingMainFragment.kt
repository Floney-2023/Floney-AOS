package com.aos.floney.view.book.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentBookSettingMainBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.add.BookAddInviteShareBottomSheetFragment
import com.aos.floney.view.book.setting.asset.BookSettingAssetBottomSheetFragment
import com.aos.floney.view.book.setting.carryinfo.BookSettingCarryInfoSheetFragment
import com.aos.floney.view.book.setting.excel.BookSettingExcelBottomSheetFragment
import com.aos.model.book.MyBookUsers
import com.aos.model.book.UiBookSettingModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSettingMainFragment :
    BaseFragment<FragmentBookSettingMainBinding, BookSettingMainViewModel>(R.layout.fragment_book_setting_main), UiBookSettingModel.OnItemClickListener {
    override fun onItemClick(item: MyBookUsers) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    override fun onResume() {
        super.onResume()
        viewModel.searchBookSettingItems()
    }

    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@BookSettingMainFragment)
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    val activity = requireActivity() as BookSettingActivity
                    activity.startHomeActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.settingPage.collect() {
                if(it) {
                    val settingAction = BookSettingMainFragmentDirections.actionBookSettingMainFragmentToBookSettingEditFragment(
                        viewModel.bookSettingInfo.value!!.bookImg,
                        viewModel.bookSettingInfo.value!!.seeProfileStatus,
                        if (viewModel.bookSettingInfo.value!!.ourBookUsers[0].role.equals("방장·나")) true else false,
                        viewModel.bookSettingInfo.value!!.ourBookUsers.size,
                        viewModel.bookSettingInfo.value!!.bookName
                    )
                    findNavController().navigate(settingAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.currencyPage.collect(){
                if(it){
                    val currencyAction = BookSettingMainFragmentDirections.actionBookSettingMainFragmentToBookSettingCurrencyFragment()
                    findNavController().navigate(currencyAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.assetPage.collect() {
                if(it) {
                    val inviteCode = ""
                    val bottomSheetFragment = BookSettingAssetBottomSheetFragment()
                    bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
                }
            }
        }
        repeatOnStarted {
            viewModel.carryInfoPage.collect() {
                if(it) {
                    val bottomSheetFragment = BookSettingCarryInfoSheetFragment(viewModel.bookSettingInfo.value!!.carryOver) { carryInfo ->
                        viewModel.changeCarryOver(carryInfo)
                    }
                    bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
                }
            }
        }
        repeatOnStarted {
            viewModel.categoryPage.collect() {
                if(it) {
                    val activity = requireActivity() as BookSettingActivity
                    activity.startBookCategoryActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.invitePage.collect() {
                if(it) {
                    val bottomSheetFragment = BookAddInviteShareBottomSheetFragment()
                    bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
                }
            }
        }
        repeatOnStarted {
            viewModel.excelPage.collect() {
                if(it) {
                    val excelFragment = BookSettingExcelBottomSheetFragment()
                    excelFragment.show(parentFragmentManager, excelFragment.tag)
                }
            }
        }
        repeatOnStarted {
            viewModel.budgetPage.collect() {
                if(it) {
                    val repeatAction = BookSettingMainFragmentDirections.actionBookSettingMainFragmentToBookSettingBudgetFragment()
                    findNavController().navigate(repeatAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.repeatPage.collect() {
                if(it) {
                    val repeatAction = BookSettingMainFragmentDirections.actionBookSettingMainFragmentToBookSettingRepeatFragment()
                    findNavController().navigate(repeatAction)
                }
            }
        }
    }
}