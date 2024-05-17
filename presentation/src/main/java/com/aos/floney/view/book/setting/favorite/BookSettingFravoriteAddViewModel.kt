package com.aos.floney.view.book.setting.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.CurrencyUtil
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.formatNumber
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.floney.view.history.OnClickedDelete
import com.aos.model.book.UiBookCategory
import com.aos.model.home.DayMoneyModifyItem
import com.aos.usecase.history.DeleteBookLineUseCase
import com.aos.usecase.history.DeleteBooksLinesAllUseCase
import com.aos.usecase.history.GetBookCategoryUseCase
import com.aos.usecase.history.PostBooksLinesChangeUseCase
import com.aos.usecase.history.PostBooksLinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookSettingFravoriteAddViewModel @Inject constructor(
) : BaseViewModel() {
}