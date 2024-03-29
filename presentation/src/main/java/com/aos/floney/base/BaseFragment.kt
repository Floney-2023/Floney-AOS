package com.aos.floney.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelLazy
import com.aos.floney.ext.repeatOnStarted
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutResId: Int,
) : Fragment(layoutResId) {

    private var _binding: B? = null
    protected val binding: B
        get() = _binding!!

    private val viewModelClass =
        ((javaClass.genericSuperclass as ParameterizedType?)?.actualTypeArguments?.get(1) as Class<VM>).kotlin

    protected open val viewModel by ViewModelLazy(
        viewModelClass,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras },
    )

    private val loadingDialog by lazy {
        AppCompatDialog(requireContext()).apply {
            setContentView(R.layout.dialog_progress)
            setCancelable(false)
            window?.setDimAmount(0.2f)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        _binding = DataBindingUtil.bind(view!!)!!
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupObserve()
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
                requireContext(), event.message, Toast.LENGTH_LONG
            ).show()

            is BaseViewModel.Event.ShowToastRes -> Toast.makeText(
                requireContext(), getString(event.message), Toast.LENGTH_LONG
            ).show()

            is BaseViewModel.Event.ShowLoading -> loadingDialog.show()
            is BaseViewModel.Event.HideLoading -> loadingDialog.dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupUi() {
        with(binding) {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
    }

    abstract fun setupObserver()

}