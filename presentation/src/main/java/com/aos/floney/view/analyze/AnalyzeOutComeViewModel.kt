package com.aos.floney.view.analyze

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.analyze.AnalyzeResult
import com.aos.model.analyze.UiAnalyzeCategoryModel
import com.aos.usecase.analyze.PostAnalyzeCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AnalyzeOutComeViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val postAnalyzeCategoryUseCase: PostAnalyzeCategoryUseCase,
) : BaseViewModel() {

    private val colorUsedArr = arrayListOf<Int>()
    private val colorArr = listOf<Int>(
        Color.parseColor("#AD1F25"),
        Color.parseColor("#EFA9AB"),
        Color.parseColor("#FF5C00"),
        Color.parseColor("#FFBE99"),
        Color.parseColor("#E4BF00"),
        Color.parseColor("#FFEE97"),
        Color.parseColor("#0060E5"),
        Color.parseColor("#4C97FF"),
        Color.parseColor("#99C4FF"),
        Color.parseColor("#35347F"),
        Color.parseColor("#4A48B0"),
        Color.parseColor("#706EC4"),
        Color.parseColor("#654CFF"),
        Color.parseColor("#9B8BFF"),
        Color.parseColor("#D3CCFF")
    )
    private val randomColorArr = arrayListOf<Int>()

    // 지출 - 분석 결과값
    private var _postAnalyzeCategoryResult = MutableLiveData<UiAnalyzeCategoryModel>()
    val postAnalyzeCategoryResult: LiveData<UiAnalyzeCategoryModel> get() = _postAnalyzeCategoryResult

    // 지출 - 분석 ui 업데이트
    private var _uiAnalyzeCategory = MutableLiveData<List<AnalyzeResult>>()
    val uiAnalyzeCategory: LiveData<List<AnalyzeResult>> get() = _uiAnalyzeCategory

    init {
        postAnalyzeCategory()
    }

    // 지출 분석 가져오기
    private fun postAnalyzeCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            postAnalyzeCategoryUseCase(
                prefs.getString("bookKey", ""), "지출", "2024-04-01"
            ).onSuccess {
                Timber.e("it $it")
                _postAnalyzeCategoryResult.postValue(it)
                _uiAnalyzeCategory.postValue(it.analyzeResult)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 랜덤 색상 가져오기 그래프는 3개까지는 색상 고정 그 이후는 랜덤 색상임
    fun getRandomColor(repeat: Int): List<Int> {
        if(repeat > 0) {
            var random = 0
            for (i in 0..repeat) {
                random = Random.nextInt(0, 14)
                if (!colorUsedArr.contains(random)) {
                    colorUsedArr.add(random)
                    randomColorArr.add(colorArr[random])
                }
                Timber.e("random $random")
            }
            Timber.e("randomColorArr $randomColorArr")
        }
        return randomColorArr
    }
}