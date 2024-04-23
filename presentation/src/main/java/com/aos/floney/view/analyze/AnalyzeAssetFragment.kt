package com.aos.floney.view.analyze

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentAnalyzeAssetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyzeAssetFragment : BaseFragment<FragmentAnalyzeAssetBinding, AnalyzeAssetViewModel>(R.layout.fragment_analyze_asset) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.view1.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // 부모 뷰의 높이를 얻습니다.
                val parentHeight = binding.view1.height

                // 자식 뷰의 높이를 부모 뷰의 50%로 설정합니다.
                val layoutParams = binding.view1.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.height = (parentHeight / 100) * 50
                layoutParams.topMargin = dpToPx(28)
                binding.view1.layoutParams = layoutParams

                // 이제 리스너를 제거합니다.
                binding.view1.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            getResources().getDisplayMetrics()
        ).toInt()
    }

}