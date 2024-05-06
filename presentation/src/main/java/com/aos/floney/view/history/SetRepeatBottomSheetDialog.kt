package com.aos.floney.view.history

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.aos.floney.BR
import com.aos.floney.base.BaseViewModel
import com.aos.floney.databinding.BottomSheetCategoryBinding
import com.aos.floney.databinding.BottomSheetSetRepeatBinding
import com.aos.model.book.UiBookCategory
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.internal.notifyAll
import timber.log.Timber


class SetRepeatBottomSheetDialog(
    context: Context,
    private val category: String,
    private val viewModel: HistoryViewModel,
    private val activityLifecycleOwner: LifecycleOwner,
    private val clickedChoiceBtn: () -> Unit
) : BottomSheetDialog(context), UiBookCategory.OnItemClickListener {
    lateinit var binding: BottomSheetSetRepeatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetSetRepeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        setUpGridLayoutManger()
    }

    private fun setUpUi() {
        with(binding) {
            binding.dialog = this@SetRepeatBottomSheetDialog
            setVariable(BR.vm, viewModel)
            setVariable(BR.eventHolder, this@SetRepeatBottomSheetDialog)
            lifecycleOwner = activityLifecycleOwner

            binding.tvCategory.text = category
        }
    }

    private fun setUpGridLayoutManger() {
        val layoutManager = GridLayoutManager(context, 5)
        binding.rvCategory.layoutManager = layoutManager
        binding.rvCategory.setHasFixedSize(true)
        binding.rvCategory.itemAnimator = null
    }

    fun onClickChoiceBtn() {
        if(viewModel.isClickedRepeatItem()) {
            // 선택 버튼 클릭 리스너
            clickedChoiceBtn()
            this.dismiss()
        }
    }
    override fun onItemClick(item: UiBookCategory) {
        viewModel.onClickRepeatItem(item)
    }
}