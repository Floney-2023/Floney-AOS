package com.aos.floney.view.history

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.aos.floney.BR
import com.aos.floney.databinding.BottomSheetCategoryBinding
import com.aos.model.book.UiBookCategory
import com.google.android.material.bottomsheet.BottomSheetDialog
import timber.log.Timber


class CategoryBottomSheetDialog(
    context: Context,
    private val viewModel: HistoryViewModel,
    private val clickedChoiceBtn: () -> Unit,
) : BottomSheetDialog(context), UiBookCategory.OnItemClickListener {
    lateinit var binding: BottomSheetCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BottomSheetCategoryBinding.inflate(layoutInflater)

        with(binding) {
            setVariable(BR.vm, viewModel)
            setVariable(BR.eventHolder, this@CategoryBottomSheetDialog)
            lifecycleOwner = this@CategoryBottomSheetDialog
        }

        viewModel.categoryList.observe(this@CategoryBottomSheetDialog) {
            Timber.e("categoryList $it")
        }

        setContentView(binding.root)

        // 선택 버튼 클릭 리스너
        binding.btnChoice.setOnClickListener {
            clickedChoiceBtn()
            this.dismiss()
        }


    }

    override fun onItemClick(item: UiBookCategory) {
        Timber.e("item $item")
    }
}