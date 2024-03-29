package com.aos.floney.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
        when (event) {
            is BaseViewModel.Event.ShowToast -> Toast.makeText(
                this,
                event.message,
                Toast.LENGTH_LONG
            )

            is BaseViewModel.Event.ShowToastRes -> Toast.makeText(
                this,
                getString(event.message),
                Toast.LENGTH_LONG
            )

            is BaseViewModel.Event.ShowLoading -> loadingDialog.show()
            is BaseViewModel.Event.HideLoading -> loadingDialog.dismiss()
        }
    }
}