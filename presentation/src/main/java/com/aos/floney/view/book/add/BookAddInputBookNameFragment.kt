package com.aos.floney.view.book.add

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.FragmentBookAddInputBookNameBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.signup.SignUpActivity
import com.aos.floney.view.signup.SignUpEmailCodeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookAddInputBookNameFragment : BaseFragment<FragmentBookAddInputBookNameBinding, BookAddInputBookNameViewModel>(R.layout.fragment_book_add_input_book_name) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 다음 페이지 이동
            viewModel.nextPage.collect {
                Timber.e("nextPage $it")
                if(it) {
                    val bookName = viewModel.bookName.value ?: ""
                    val settingProfileAction =
                        BookAddInputBookNameFragmentDirections.actionBookAddInputBookNameFragmentToBookAddSettingProfileFragment(
                            bookName
                        )
                    findNavController().navigate(settingProfileAction)
                }
            }
        }
        repeatOnStarted {
            // 이전 페이지 이동
            viewModel.back.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }
    }

}