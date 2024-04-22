package com.aos.floney.view.book.setting.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.formatNumber
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.UiBookCategory
import com.aos.model.home.DayMoneyModifyItem
import com.aos.usecase.booksetting.BooksCategoryDeleteUseCase
import com.aos.usecase.history.GetBookCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSettingCategoryViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val getBookCategoryUseCase: GetBookCategoryUseCase,
    private val booksCategoryDeleteUseCase: BooksCategoryDeleteUseCase
) : BaseViewModel() {

    // 내역 삭제
    private var _deleteBooksLines = MutableEventFlow<Boolean>()
    val deleteBooksLines: EventFlow<Boolean> get() = _deleteBooksLines

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


    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 내역 모드 추가 / 수정
    var edit = MutableLiveData<Boolean>(false)

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

    init {
        getBookCategory()
    }

    // 내역 추가 시에는 날짜만 세팅함
    fun setIntentAddData(clickDate: String, nickname: String) {
        date.value = clickDate
        _nickname.value = nickname
    }

    fun onClickPreviousPage() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    // 자산/분류 카테고리 항목 가져오기
    private fun getBookCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            getBookCategoryUseCase(prefs.getString("bookKey", ""), flow.value!!).onSuccess { it ->
                _categoryList.postValue(it)

            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 저장버튼 클릭
    fun onClickSaveBtn() {

    }
    // 편집버튼 클릭
    fun onClickEdit(){
        edit.value = !edit.value!!

        val item = _categoryList.value?.map {
            UiBookCategory(
                it.idx, edit.value!!, it.name, it.default
            )
        } ?: listOf()
        _categoryList.postValue(item)
    }

    // 내역 삭제
    fun deleteCategory(item : UiBookCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            booksCategoryDeleteUseCase(
                bookKey = prefs.getString("bookKey", ""),
                flow.value!!,
                item.name
            ).onSuccess {
                val updatedList = _categoryList.value!!.filter { it.name != item.name }
                _categoryList.postValue(updatedList)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 자산, 지출, 수입, 이체 클릭
    fun onClickFlow(type: String) {
        flow.postValue(type)
        parent = type
        getBookCategory()
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