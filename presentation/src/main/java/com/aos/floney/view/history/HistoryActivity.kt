package com.aos.floney.view.history

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityHistoryBinding
import com.aos.floney.ext.intentSerializable
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.setting.category.BookCategoryActivity
import com.aos.floney.view.common.BaseAlertDialog
import com.aos.floney.view.common.BaseChoiceAlertDialog
import com.aos.floney.view.home.HomeActivity
import com.aos.model.book.UiBookCategory
import com.aos.model.home.DayMoney
import com.aos.model.home.DayMoneyModifyItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HistoryActivity :
    BaseActivity<ActivityHistoryBinding, HistoryViewModel>(R.layout.activity_history), UiBookCategory.OnItemClickListener {
    private lateinit var calendarBottomSheetDialog: CalendarBottomSheetDialog
    private lateinit var categoryBottomSheetDialog: CategoryBottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
        setUpCalendarBottomSheet()
    }

    // 내역 추가 데이터 가져오기
    private fun getIntentAddData(): String {
        val date = intent.getStringExtra("date") ?: ""
        val nickname = intent.getStringExtra("nickname") ?: ""

        viewModel.setIntentAddData(date, nickname)

        return date
    }

    // 내역 수정 데이터 가져오기
    private fun getModifyData(): String {
        var date = ""
        intent.intentSerializable("dayItem", DayMoneyModifyItem::class.java)
            ?.let {
                viewModel.setIntentModifyData(it)
                date = it.lineDate
            }

        Timber.e("date $date")
        return date
    }

    // 캘린더 bottomSheet 구현
    private fun setUpCalendarBottomSheet() {
        val date = if(getIntentAddData() == "") {
            getModifyData()
        } else {
            getIntentAddData()
        }

        calendarBottomSheetDialog = CalendarBottomSheetDialog(this@HistoryActivity, date, DayDisableDecorator(this@HistoryActivity), {date ->
            viewModel.setCalendarDate(date)
        }, {
            viewModel.onClickCalendarChoice()
        })
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.deleteBookLines.collect {
                startActivity(Intent(this@HistoryActivity, HomeActivity::class.java))
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                } else {
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
                finish()
            }
        }
        repeatOnStarted {
            viewModel.onClickDelete.collect {
                Timber.e("it.isExistRepeat ${it.isExistRepeat}")
                if(it.isExistRepeat) {
                    // 반복내역 있음
                    BaseChoiceAlertDialog(title = "이 내역을 삭제하시겠습니까?\n반복되는 내역입니다.", btnTextStr1 = "이 내역만 삭제", btnTextStr2 = "이후 모든 내역 삭제") {
                        if(it) {
                            // 이 내역만 삭제 선택
                            viewModel.deleteHistory()
                        } else {
                            // 이후 모든 내역 삭제 선택
                            // 반복내역삭제
                        }
                    }.show(supportFragmentManager, "baseChoiceDialog")
                } else {
                    // 반복내역 없음
                    BaseAlertDialog(title = "삭제하기", info = "삭제하시겠습니까?", false) {
                        if(it) {
                            viewModel.deleteHistory()
                        }
                    }.show(supportFragmentManager, "baseAlertDialog")
                }
            }
        }
        repeatOnStarted {
            viewModel.showCalendar.collect {
                if(it) {
                    if(!calendarBottomSheetDialog.isShowing) {
                        calendarBottomSheetDialog.show()
                    }
                }
            }
        }
        repeatOnStarted {
            viewModel.onClickCategory.collect {
                categoryBottomSheetDialog = CategoryBottomSheetDialog(this@HistoryActivity, it, viewModel, this@HistoryActivity, {
                    // 완료 버튼 클릭
                    viewModel.onClickCategoryChoiceDate()
                }, {
                    // 편집 버튼 클릭
                    startActivity(Intent(this@HistoryActivity, BookCategoryActivity::class.java).putExtra("entryPoint", "history"))
                })
                categoryBottomSheetDialog.show()
            }
        }
        repeatOnStarted {
            viewModel.onClickRepeat.collect {
                SetRepeatBottomSheetDialog(this@HistoryActivity, it, viewModel, this@HistoryActivity) {
                    // 완료 버튼 클릭
                    viewModel.onClickRepeatChoice()
                }.show()
            }
        }

        repeatOnStarted {
            viewModel.postBooksLines.collect {
                if(it) {
                    Handler(Looper.myLooper()!!).postDelayed({
                        startActivity(Intent(this@HistoryActivity, HomeActivity::class.java))
                        if (Build.VERSION.SDK_INT >= 34) {
                            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                        } else {
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                        finish()
                    }, 2000)
                }
            }
        }

        repeatOnStarted {
            viewModel.onClickCloseBtn.collect {
                if(it) {
                    // 수정 내역 있음
                    BaseAlertDialog(title = "잠깐", info = "수정한 내용이 저장되지 않았습니다.\n그대로 나가시겠습니까?", false) {
                        if(it) {
                            finish()
                        }
                    }.show(supportFragmentManager, "baseAlertDialog")
                } else {
                    // 수정 내역 없음
                    finish()
                }
            }
        }

        repeatOnStarted {
            viewModel.postModifyBooksLines.collect {
                if(it) {
                    Handler(Looper.myLooper()!!).postDelayed({
                        startActivity(Intent(this@HistoryActivity, HomeActivity::class.java))
                        if (Build.VERSION.SDK_INT >= 34) {
                            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                        } else {
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                        finish()
                    }, 2000)
                }
            }
        }
    }

    // 캘린더 일자 배경 커스텀 클래스
    inner class DayDisableDecorator(context: Context) : DayViewDecorator {
        private val drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector)
        override fun shouldDecorate(day: CalendarDay): Boolean {
            // 휴무일 || 이전 날짜
            return true
        }

        override fun decorate(view: DayViewFacade) {
            view.let {
                if (drawable != null) {
                    it.setSelectionDrawable(drawable)
                }
            }
        }
    }

    override fun onItemClick(item: UiBookCategory) {
        Timber.e("item $item")
    }
}