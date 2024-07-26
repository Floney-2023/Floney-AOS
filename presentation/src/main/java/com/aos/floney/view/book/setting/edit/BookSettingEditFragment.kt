package com.aos.floney.view.book.setting.edit

import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.aos.data.util.CommonUtil
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentBookSettingEditBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.setting.BookSettingActivity
import com.aos.floney.view.common.WarningPopupDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookSettingEditFragment :
    BaseFragment<FragmentBookSettingEditBinding, BookSettingEditViewModel> (R.layout.fragment_book_setting_edit){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToggleListener()
        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }
        repeatOnStarted {
            viewModel.profileChangePage.collect() {
                if(it) {
                    val profileChangeAction = BookSettingEditFragmentDirections.actionBookSettingEditFragmentToBookSettingProfileChangeFragment(
                        CommonUtil.bookProfile,
                        viewModel.profileCheck.value!!,
                        viewModel.roleCheck.value!!
                    )
                    findNavController().navigate(profileChangeAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.deletePopup.collect() {
                val bookName = if(viewModel.bookName.value=="") viewModel.bookHintName.value!! else viewModel.bookName.value
                if(it) {
                    val exitDialogFragment = WarningPopupDialog(
                        getString(R.string.book_setting_exit_dialog_title),
                        getString(R.string.book_setting_exit_dialog_info, bookName),
                        getString(R.string.book_setting_exit_dialog_delete_button),
                        getString(R.string.book_setting_exit_dialog_cancel_button),
                        false
                    ) {  checked ->
                        if (checked)
                            viewModel.deleteBook()
                    }
                    exitDialogFragment.show(parentFragmentManager, "exitDialog")
                }
            }
        }
        repeatOnStarted {
            viewModel.deletePage.collect() {
                if(it) {
                    // 가계부 2개 이상인데 삭제한 경우 -> 홈 화면으로 이동
                    val activity = requireActivity() as BookSettingActivity
                    activity.startHomeActivity()
                }
                else {
                    // 가계부 1개 있는데 삭제한 경우 -> 가계부 추가 화면으로 이동
                    val activity = requireActivity() as BookSettingActivity
                    activity.startBookAddActivity()
                }
            }
        }
    }
    private fun setUpToggleListener(){
        binding.switchButton.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                viewModel.onClickMarketingTerms()
                true
            } else false
        }
    }
}