package com.aos.floney.view.analyze

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.analyze.UiAnalyzePlanModel
import com.aos.usecase.analyze.PostAnalyzeIPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnalyzePlanViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    val postAnalyzeIPlanUseCase: PostAnalyzeIPlanUseCase
): BaseViewModel() {
    // 예산 조회하기
    private var _postAnalyzePlan = MutableLiveData<UiAnalyzePlanModel>(UiAnalyzePlanModel("", "0원", "0", ""))
    val postAnalyzePlan: LiveData<UiAnalyzePlanModel> get() = _postAnalyzePlan

    private var _onClickSetBudget = MutableEventFlow<Boolean>()
    val onClickSetBudget : EventFlow<Boolean> get() = _onClickSetBudget

    init {
        postAnalyzePlan()
    }

    // 예산 조회하기
    fun postAnalyzePlan() {
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            postAnalyzeIPlanUseCase(prefs.getString("bookKey", ""), "2024-04-01").onSuccess {
                Timber.e("it $it")
                _postAnalyzePlan.postValue(it)
                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                baseEvent(Event.HideLoading)
            }
        }
    }

    fun onClickSetBudget() {
        viewModelScope.launch {
            _onClickSetBudget.emit(true)
        }
    }

}