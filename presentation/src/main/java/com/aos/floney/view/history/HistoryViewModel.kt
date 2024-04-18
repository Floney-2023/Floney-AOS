package com.aos.floney.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.formatNumber
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.UiBookCategory
import com.aos.model.home.DayMoney
import com.aos.model.home.DayMoneyModifyItem
import com.aos.usecase.history.GetBookCategoryUseCase
import com.aos.usecase.history.PostBooksLinesChangeUseCase
import com.aos.usecase.history.PostBooksLinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val getBookCategoryUseCase: GetBookCategoryUseCase,
    private val postBooksLinesUseCase: PostBooksLinesUseCase,
    private val postBooksLinesChangeUseCase: PostBooksLinesChangeUseCase
) : BaseViewModel() {

    // 내역 추가 결과
    private var _postBooksLines = MutableEventFlow<Boolean>()
    val postBooksLines: EventFlow<Boolean> get() = _postBooksLines

    // 내역 수정 결과
    private var _postModifyBooksLines = MutableEventFlow<Boolean>()
    val postModifyBooksLines: EventFlow<Boolean> get() = _postModifyBooksLines

    // 날짜 클릭 여부
    private var _showCalendar = MutableEventFlow<Boolean>()
    val showCalendar: EventFlow<Boolean> get() = _showCalendar

    // 닫기 클릭
    private var _onClickCloseBtn = MutableEventFlow<Boolean>()
    val onClickCloseBtn: EventFlow<Boolean> get() = _onClickCloseBtn

    // 카테고리 클릭
    private var _onClickCategory = MutableEventFlow<Boolean>()
    val onClickCategory: EventFlow<Boolean> get() = _onClickCategory

    // 날짜
    private var tempDate = ""
    var date = MutableLiveData<String>("날짜를 선택하세요")

    // 닉네임
    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    // 내역 모드 추가 / 수정
    var mode = MutableLiveData<String>("add")

    // 금액
    var cost = MutableLiveData<String>("")

    // 지출, 수입, 이체
    var flow = MutableLiveData<String>("지출")

    // 자산
    var asset = MutableLiveData<String>("자산을 선택하세요")

    // 분류
    var line = MutableLiveData<String>("분류를 선택하세요")

    // 내용
    var content = MutableLiveData<String>("")

    // 내용
    var _categoryList = MutableLiveData<List<UiBookCategory>>()
    val categoryList: LiveData<List<UiBookCategory>> get() = _categoryList

    // 카테고리 선택 아이템 저장
    private var categoryClickItem: UiBookCategory? = null

    // 자산, 지출, 수입, 이체 카테고리 조회에 사용
    private var parent = ""

    // 예산/자산 제외 설정 여부
    private var deleteChecked = false

    // 내역 수정 시 해당 아이템 Id
    private var modifyId = 0

    // 내역 추가 시에는 날짜만 세팅함
    fun setIntentAddData(clickDate: String, nickname: String) {
        date.value = clickDate
        _nickname.value = nickname
    }

    fun setIntentModifyData(item: DayMoneyModifyItem) {
        mode.value = "modify"
        modifyId = item.id
        cost.value = item.money.substring(2, item.money.length).trim() + "원"
        date.value = item.lineDate
        flow.value = getCategory(item.lineCategory)
        asset.value = item.assetSubCategory
        line.value = item.lineSubCategory
        content.value = item.description
        _nickname.value = item.writerNickName
        deleteChecked = item.exceptStatus
    }

    // 자산/분류 카테고리 항목 가져오기
    private fun getBookCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            getBookCategoryUseCase(prefs.getString("bookKey", ""), parent).onSuccess { it ->
                // 카테고리 선택 값 초기화
                categoryClickItem = null

                val tempValue = if (parent == "자산") {
                    asset.value
                } else {
                    line.value
                }

                val item = it.map { innerItem ->
                    if (innerItem.name == tempValue) {
                        categoryClickItem = innerItem

                        UiBookCategory(
                            innerItem.idx, true, innerItem.name, innerItem.default
                        )
                    } else {
                        UiBookCategory(
                            innerItem.idx, innerItem.checked, innerItem.name, innerItem.default
                        )
                    }
                }

                _categoryList.postValue(item.toMutableList())
                _onClickCategory.emit(true)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 저장버튼 클릭
    fun onClickSaveBtn() {
        if (isAllInputData()) {
            when (mode.value) {
                "add" -> postAddHistory()
                "modify" -> postModifyHistory()
            }
        } else {
            baseEvent(Event.ShowToast("모든 값을 입력해 주세요"))
        }
    }

    // 내역 추가
    private fun postAddHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            postBooksLinesUseCase(
                bookKey = prefs.getString("bookKey", ""),
                money = cost.value!!.replace(",", "").substring(0, cost.value!!.length - 2)
                    .toInt(),
                flow = flow.value!!,
                asset = asset.value!!,
                line = line.value!!,
                lineDate = date.value!!.replace(".", "-"),
                description = content.value!!,
                except = deleteChecked,
                nickname = nickname.value!!,
                repeatDuration = "NONE"
            ).onSuccess {
                _postBooksLines.emit(true)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 내역 수정
    private fun postModifyHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            postBooksLinesChangeUseCase(
                lineId = modifyId,
                bookKey = prefs.getString("bookKey", ""),
                money = cost.value!!.replace(",", "").substring(0, cost.value!!.length - 2)
                    .toInt(),
                flow = flow.value!!,
                asset = asset.value!!,
                line = line.value!!,
                lineDate = date.value!!.replace(".", "-"),
                description = content.value!!,
                except = deleteChecked,
                nickname = nickname.value!!,
            ).onSuccess {
                _postModifyBooksLines.emit(true)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 모든 데이터 입력 되었는지 체크
    private fun isAllInputData(): Boolean {
        return cost.value != "" && asset.value != "자산을 선택하세요" && line.value != "분류를 선택하세요" && content.value != ""
    }

    // 닫기 버튼 클릭
    fun onClickCloseBtn() {
        viewModelScope.launch {
            _onClickCloseBtn.emit(true)
        }
    }

    // 날짜 표시 클릭
    fun onClickShowCalendar() {
        viewModelScope.launch {
            _showCalendar.emit(true)
        }
    }

    // 카테고리 자산 표시 클릭
    fun onClickCategory() {
        parent = "자산"
        getBookCategory()
    }

    // 카테고리 분류 표시 클릭
    fun onClickCategoryDiv() {
        parent = flow.value ?: "지출"
        getBookCategory()
    }

    // 지출, 수입, 이체 클릭
    fun onClickFlow(type: String) {
        if(mode.value == "add") {
            line.postValue("분류를 선택하세요")
            flow.postValue(type)
        }
    }

    // 비용 입력 시 저장
    fun onCostTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (count == 0) {
            cost.postValue("${s.toString().formatNumber()}")
        } else {
            cost.postValue("${s.toString().formatNumber()}원")
        }
    }

    // 예산/자산 제외 스위치 값 저장
    fun onDeleteCheckedChange(checked: Boolean) {
        deleteChecked = checked
    }

    // 캘린더 날짜 선택 시 값 저장
    fun setCalendarDate(_date: String) {
        tempDate = _date.replace("{", "")
        val dateArr = tempDate.replace("}", "").split("-")

        tempDate = "${dateArr[0]}.${
            if (dateArr[1].toInt() < 10) {
                "0${dateArr[1]}"
            } else {
                dateArr[1]
            }
        }.${
            if (dateArr[2].toInt() < 10) {
                "0${dateArr[2]}"
            } else {
                dateArr[2]
            }
        }"
    }

    // 캘린더 선택 버튼 클릭
    fun onClickCalendarChoice() {
        if (tempDate != "") {
            date.postValue(tempDate)
        }
    }

    // 선택 버튼 클릭
    fun onClickCategoryChoiceDate() {
        if (parent == "자산") {
            // 자산 선택
            asset.value = categoryClickItem?.name
        } else {
            // 분류 선택
            line.value = categoryClickItem?.name
        }
    }

    fun onClickCategoryItem(_item: UiBookCategory) {
        categoryClickItem = _item

        val item = _categoryList.value?.map {
            UiBookCategory(
                it.idx, false, it.name, it.default
            )
        } ?: listOf()
        item[_item.idx].checked = !item[_item.idx].checked
        _categoryList.postValue(item)
    }

    // 카테고리 선택 여부 확인
    fun isClickedCategoryItem(): Boolean {
        return if (categoryClickItem != null) {
            true
        } else {
            baseEvent(Event.ShowToast("카테고리 항목을 선택해주세요"))
            false
        }
    }

    // 카테고리 가져오기 영문 -> 한글
    private fun getCategory(category: String): String {
        return when (category) {
            "INCOME" -> {
                "수입"
            }

            "OUTCOME" -> {
                "지출"
            }

            "TRANSFER" -> {
                "이체"
            }

            else -> ""
        }
    }
}