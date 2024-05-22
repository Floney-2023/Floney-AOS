package com.aos.floney.view.book.setting.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.UiBookFavoriteModel
import com.aos.usecase.booksetting.BooksFavoriteDeleteUseCase
import com.aos.usecase.history.GetBookFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSettingFavoriteViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val getBookFavoriteUseCase: GetBookFavoriteUseCase,
    private val booksFavoriteDeleteUseCase: BooksFavoriteDeleteUseCase
) : BaseViewModel() {

    // 닫기 클릭
    private var _onClickCloseBtn = MutableEventFlow<Boolean>()
    val onClickCloseBtn: EventFlow<Boolean> get() = _onClickCloseBtn

    // 카테고리 클릭
    private var _onClickCategory = MutableEventFlow<Boolean>()
    val onClickCategory: EventFlow<Boolean> get() = _onClickCategory



    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 추가 페이지
    private var _addPage = MutableEventFlow<Boolean>()
    val addPage: EventFlow<Boolean> get() = _addPage

    // 분류 항목 관리 모드 편집 / 완료
    var edit = MutableLiveData<Boolean>(false)

    // 자산, 지출, 수입, 이체
    var flow = MutableLiveData<String>("지출")

    // 내용
    var _favoriteList = MutableLiveData<List<UiBookFavoriteModel>>()
    val favoriteList: LiveData<List<UiBookFavoriteModel>> get() = _favoriteList

    init {
        getBookCategory()
    }

    fun onClickPreviousPage() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    // 자산/분류 카테고리 항목 가져오기
    fun getBookCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            getBookFavoriteUseCase(prefs.getString("bookKey", ""), getCategory(flow.value!!)).onSuccess { it ->
                val item = it.map {
                    UiBookFavoriteModel(
                        it.idx, edit.value!!, it.description,it.lineCategoryName,it.lineSubcategoryName,it.assetSubcategoryName, it.money
                    )
                }
                _favoriteList.postValue(item)

            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookSettingFavoriteViewModel)))
            }
        }
    }

    // 추가하기 버튼 클릭
    fun onClickAddBtn() {
        viewModelScope.launch {
            _addPage.emit(true)
        }
    }
    // 편집버튼 클릭
    fun onClickEdit(){
        edit.value = !edit.value!!
        getBookCategory()
    }

    // 내역 삭제
    fun deleteFavorite(item : UiBookFavoriteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            booksFavoriteDeleteUseCase(
                bookKey = prefs.getString("bookKey", ""),
                item.idx
            ).onSuccess {
                val updatedList = _favoriteList.value!!.filter { it.idx != item.idx }
                _favoriteList.postValue(updatedList)
                baseEvent(Event.ShowSuccessToast("삭제가 완료되었습니다."))
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookSettingFavoriteViewModel)))
            }
        }
    }
    private fun getCategory(category: String): String {
        return when (category) {
            "수입" -> {
                "INCOME"
            }

            "지출" -> {
                "OUTCOME"
            }

            "이체" -> {
                "TRANSFER"
            }

            else -> ""
        }
    }

    // 자산, 지출, 수입, 이체 클릭
    fun onClickFlow(type: String) {
        flow.postValue(type)
        getBookCategory()
    }
}