package com.aos.floney.view.book.setting

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentBookSettingEditProfilechangeBinding
import com.aos.floney.databinding.FragmentMyPageInformProfilechangeBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSettingProfileChangeFragment :
    BaseFragment<FragmentBookSettingEditProfilechangeBinding, BookSettingProfileChangeViewModel>(R.layout.fragment_book_setting_edit_profilechange) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }
}