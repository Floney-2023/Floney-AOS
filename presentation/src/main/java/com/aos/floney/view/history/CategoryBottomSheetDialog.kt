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
import com.aos.model.book.UiBookCategory
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.internal.notifyAll
import timber.log.Timber


class CategoryBottomSheetDialog(
    context: Context,
    private val category: String,
    private val viewModel: HistoryViewModel,
    private val activityLifecycleOwner: LifecycleOwner,
    private val clickedChoiceBtn: () -> Unit,
) : BottomSheetDialog(context), UiBookCategory.OnItemClickListener {
    lateinit var binding: BottomSheetCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        setUpGridLayoutManger()
    }

    private fun setUpUi() {
        with(binding) {
            binding.dialog = this@CategoryBottomSheetDialog
            setVariable(BR.vm, viewModel)
            setVariable(BR.eventHolder, this@CategoryBottomSheetDialog)
            lifecycleOwner = activityLifecycleOwner

            binding.tvCategory.text = category
        }
    }

    private fun setUpGridLayoutManger() {
        val layoutManager = GridLayoutManager(context, 4)
        binding.rvCategory.layoutManager = layoutManager
        binding.rvCategory.setHasFixedSize(true)
        binding.rvCategory.itemAnimator = null
    }

    fun onClickChoiceBtn() {
        if(viewModel.isClickedCategoryItem()) {
            // 선택 버튼 클릭 리스너
            clickedChoiceBtn()
            this.dismiss()
        }
    }

    override fun onItemClick(item: UiBookCategory) {
        viewModel.onClickCategoryItem(item)
    }
}