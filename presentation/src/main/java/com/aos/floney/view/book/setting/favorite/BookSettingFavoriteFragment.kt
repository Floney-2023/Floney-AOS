package com.aos.floney.view.book.setting.favorite

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentBookSettingCategoryBinding
import com.aos.floney.databinding.FragmentBookSettingFavoriteBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.common.BaseAlertDialog
import com.aos.floney.view.common.WarningPopupDialog
import com.aos.model.book.UiBookCategory
import com.aos.model.book.UiBookFavorite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSettingFavoriteFragment : BaseFragment<FragmentBookSettingFavoriteBinding, BookSettingFavoriteViewModel>(R.layout.fragment_book_setting_favorite) , UiBookFavorite.OnItemClickListener {
    override fun onResume() {
        super.onResume()
        viewModel.getBookCategory()
    }
    override fun onItemClick(item: UiBookFavorite) {
        if (viewModel.edit.value!!)
        {

            val exitDialogFragment = BaseAlertDialog(
                "삭제하기",
                "삭제하시겠습니까?",
                false
            ) { checked ->
                if (checked)
                    viewModel.deleteFavorite(item)
            }
            exitDialogFragment.show(parentFragmentManager, "favoriteLoadDialog")
        }
        else{

            val exitDialogFragment = BaseAlertDialog(
                "즐겨찾기",
                "해당 내역을 불러오겠습니까?",
                false
            ) { checked ->

            }
            exitDialogFragment.show(parentFragmentManager, "favoriteLoadDialog")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@BookSettingFavoriteFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 이전 페이지로 (가계부 설정)
            viewModel.back.collect {
                if(it) {
                    val activity = requireActivity() as BookFavoriteActivity
                    activity.startBookSettingActivity()
                }
            }
        }
        repeatOnStarted {
            // 즐겨찾기 추가 페이지로
            viewModel.addPage.collect {
                if(it) {
                    val addAction = BookSettingFavoriteFragmentDirections.actionBookSettingFavoriteFragmentToBookSettingFavoriteAddFragment()
                    findNavController().navigate(addAction)
                }
            }
        }
    }

}