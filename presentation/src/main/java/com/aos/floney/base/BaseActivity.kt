package com.aos.floney.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelLazy
import com.aos.floney.BR
import com.aos.floney.R
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.common.ErrorToastDialog
import com.aos.floney.view.common.SuccessToastDialog
import com.aos.floney.view.login.LoginActivity
import timber.log.Timber
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutResId: Int,
) : AppCompatActivity() {

    protected lateinit var binding: B

    private val viewModelClass = ((javaClass.genericSuperclass as ParameterizedType?)
        ?.actualTypeArguments
        ?.get(1) as Class<VM>).kotlin

    protected open val viewModel by ViewModelLazy(
        viewModelClass,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras },
    )

    private val loadingDialog by lazy {
        AppCompatDialog(this).apply {
            setContentView(R.layout.item_progress_loading)
            setCancelable(false)
            window?.setDimAmount(0.2f)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        setupObserve()
    }

    private fun setupUi() {
        binding = DataBindingUtil.setContentView(this, layoutResId)
        with(binding) {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@BaseActivity
        }
    }

    private fun setupObserve() {
        repeatOnStarted {
            viewModel.baseEventFlow.collect {
                handleEvent(it)
            }
        }
    }

    private fun handleEvent(event: BaseViewModel.Event) {
        try {
            when (event) {
                is BaseViewModel.Event.ShowToast -> {
                    if (event.message != "") {
                        val errorToastDialog = ErrorToastDialog(this, event.message)
                        errorToastDialog.show()

                        Handler(Looper.myLooper()!!).postDelayed({
                            errorToastDialog.dismiss()
                        }, 2000)
                    }
                }

                is BaseViewModel.Event.ShowToastRes -> {
                    val errorToastDialog = ErrorToastDialog(this, getString(event.message))
                    errorToastDialog.show()

                    Handler(Looper.myLooper()!!).postDelayed({
                        errorToastDialog.dismiss()
                    }, 2000)
                }

                is BaseViewModel.Event.ShowSuccessToast -> {
                    val successToastDialog = SuccessToastDialog(this, event.message)
                    successToastDialog.show()

                    Handler(Looper.myLooper()!!).postDelayed({
                        successToastDialog.dismiss()
                    }, 2000)
                }

                is BaseViewModel.Event.ShowSuccessToastRes -> {
                    val successToastDialog = SuccessToastDialog(this, getString(event.message))
                    successToastDialog.show()

                    Handler(Looper.myLooper()!!).postDelayed({
                        successToastDialog.dismiss()
                    }, 2000)
                }

                is BaseViewModel.Event.ShowLoading -> showLoadingDialog()
                is BaseViewModel.Event.HideLoading -> dismissLoadingDialog()
                is BaseViewModel.Event.ExpiredToken -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finishAffinity()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showLoadingDialog() {
        val circle1 = loadingDialog.findViewById<View>(R.id.circle1)
        val circle2 = loadingDialog.findViewById<View>(R.id.circle2)
        val circle3 = loadingDialog.findViewById<View>(R.id.circle3)

        val animationDelay = 200L // 애니메이션 시작 지연 시간 (0.2초)
        val animationDuration = 600L // 애니메이션 지속 시간 (0.6초)

        val animator1 = ObjectAnimator.ofFloat(circle1, "translationY", 0f, -30f, 0f).apply {
            duration = animationDuration
            interpolator = AccelerateDecelerateInterpolator()
            repeatMode = ValueAnimator.RESTART
            repeatCount = 100 // 한 번만 실행
            startDelay = animationDelay * 0
        }

        val animator2 = ObjectAnimator.ofFloat(circle2, "translationY", 0f, -30f, 0f).apply {
            duration = animationDuration
            interpolator = AccelerateDecelerateInterpolator()
            repeatMode = ValueAnimator.RESTART
            repeatCount = 100 // 한 번만 실행
            startDelay = animationDelay * 1
        }

        val animator3 = ObjectAnimator.ofFloat(circle3, "translationY", 0f, -30f, 0f).apply {
            duration = animationDuration
            interpolator = AccelerateDecelerateInterpolator()
            repeatMode = ValueAnimator.RESTART
            repeatCount = 100 // 한 번만 실행
            startDelay = animationDelay * 2
        }

        // 마지막 애니메이션인 animator3에 대한 리스너 설정
        animator3.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                animator1.cancel()
                animator2.cancel()
                animator3.cancel()
            }
        })

        // 애니메이션들을 함께 실행
        animator1.start()
        animator2.start()
        animator3.start()

        loadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        try {
            loadingDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}