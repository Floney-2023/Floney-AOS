package com.aos.floney.view.analyze

import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.usecase.analyze.PostAnalyzeCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnalyzeOutComeViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val postAnalyzeCategoryUseCase: PostAnalyzeCategoryUseCase
) : BaseViewModel() {

    init {
        postAnalyzeCategory()
    }

    // 지출 분석 가져오기
    private fun postAnalyzeCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            postAnalyzeCategoryUseCase(prefs.getString("bookKey", ""), "지출", "2024-04-01").onSuccess {
                Timber.e("it $it")
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }
}