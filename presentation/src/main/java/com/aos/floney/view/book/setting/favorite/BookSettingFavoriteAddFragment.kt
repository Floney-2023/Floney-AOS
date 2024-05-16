package com.aos.floney.view.book.setting.favorite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.ActivityHistoryBinding
import com.aos.floney.databinding.FragmentBookSettingFavoriteAddBinding
import com.aos.floney.ext.intentSerializable
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.setting.category.BookCategoryActivity
import com.aos.floney.view.common.BaseAlertDialog
import com.aos.floney.view.common.BaseChoiceAlertDialog
import com.aos.floney.view.history.CalendarBottomSheetDialog
import com.aos.floney.view.history.CategoryBottomSheetDialog
import com.aos.floney.view.history.HistoryViewModel
import com.aos.floney.view.history.SetRepeatBottomSheetDialog
import com.aos.floney.view.home.HomeActivity
import com.aos.model.book.UiBookCategory
import com.aos.model.book.UiBookFavorite
import com.aos.model.home.DayMoneyModifyItem
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class BookSettingFavoriteAddFragment :
    BaseFragment<FragmentBookSettingFavoriteAddBinding, HistoryViewModel>(R.layout.fragment_book_setting_favorite_add) {

    private lateinit var categoryBottomSheetDialog: CategoryBottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }



    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.onClickCategory.collect {
                categoryBottomSheetDialog = CategoryBottomSheetDialog(requireContext(), it, viewModel, this@BookSettingFavoriteAddFragment, {
                    // 완료 버튼 클릭
                    viewModel.onClickCategoryChoiceDate()
                }, {
                    // 편집 버튼 클릭 (카테고리 설정 화면)
                    val activity = requireActivity() as BookFavoriteActivity
                    activity.startBookSettingActivity()
                })
                categoryBottomSheetDialog.show()
            }
        }

        repeatOnStarted {
            viewModel.postBooksFavorites.collect {
                if(it) {

                }
            }
        }

        repeatOnStarted {
            viewModel.onClickCloseBtn.collect {
                if(it) {
                    // 수정 내역 있음
                    BaseAlertDialog(title = "잠깐", info = "수정한 내용이 저장되지 않았습니다.\n그대로 나가시겠습니까?", false) {
                        if(it) {
                            findNavController().popBackStack()
                        }
                    }.show(parentFragmentManager, "baseAlertDialog")
                } else {
                    // 수정 내역 없음
                    findNavController().popBackStack()
                }
            }
        }
    }
}