package com.aos.floney.view.book.add

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.base.BaseViewModel
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.FragmentBookAddSettingProfileBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.common.ChoiceImageDialog
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class BookAddSettingProfileFragment : BaseFragment<FragmentBookAddSettingProfileBinding, BookAddSettingProfileViewModel>(R.layout.fragment_book_add_setting_profile) {

    // 사진 찍기 결과
    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) {
    }

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = result.data?.data
            imageUri?.let {
                lifecycleScope.launch {
//                    try {
//                        val path = ImgFileMaker.getFullPathFromUri(requireContext(), it)!!
//                        val angle = RotateTransform.getRotationAngle(path)
//                        val rotateBitmap = RotateTransform.rotateImage(
//                            BitmapFactory.decodeFile(path),
//                            angle.toFloat(),
//                            it
//                        )
//
//                        activityRecruit2ViewModel.setThumbnailImageFile(
//                            ImgFileMaker.saveBitmapToFile(rotateBitmap!!, path)
//                        )
//
//                        binding.thumbnailIv.visibility = View.VISIBLE
//                        Glide.with(requireActivity()).load(imageUri).into(binding.thumbnailIv)
//
//                        binding.thumbnailPhotoCl.setBackgroundResource(R.drawable.custom_radius_8_stroke_g300_solid_fff)
//                        binding.thumbnailPhotoTv.visibility = View.GONE
//                        binding.thumbnailInfoTv.visibility = View.GONE
//                    } catch (e: Exception) {
//                        activity.showErrorMsg("해당 이미지는 사용할 수 없습니다.")
//                    }
//                }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
        checkGalleryPermission()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 다음 페이지 이동
            viewModel.nextPage.collect {
                if(it) {
                    val code = viewModel.inviteCode.value ?: ""
                    val nextAction =
                        BookAddSettingProfileFragmentDirections.actionBookAddSettingProfileFragmentToBookAddCreateSuccessFragment(code)
                    findNavController().navigate(nextAction)
                }
            }
        }
        repeatOnStarted {
            // 이전 페이지 이동
            viewModel.back.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }
        repeatOnStarted {
            viewModel.onClickChoiceImage.collect {
                if(it) {
                    onClickChoiceImage()
                }
            }
        }
    }

    private fun onClickChoiceImage() {
        if(checkGalleryPermission()) {
            ChoiceImageDialog(requireContext(), {
                // 사진 촬영하기
                viewModel.setTakeCaptureUri(createTempImageFile())
                takePhoto.launch(viewModel.getTakeCaptureUri())
            }, {
                // 앨범에서 사진 선택
                selectGallery()
            }, {
                // 랜덤 이미지
            }).show()
        } else {
            viewModel.baseEvent(BaseViewModel.Event.ShowToast("이미지 접근 권한이 허용되지 않았습니다. "))
        }
    }

    // 사진 촬영을 위해 임시 파일 생성
    private fun createTempImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            content
        )
    }

    private fun selectGallery() {
        if (checkGalleryPermission()) {
            // 권한이 있는 경우 갤러리 실행
            val intent = Intent(Intent.ACTION_PICK)
            // intent와 data와 type을 동시에 설정하는 메서드
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
            )

            imageResult.launch(intent)
        }
    }

    private fun checkGalleryPermission(): Boolean {
        val readPermission = ContextCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val imagePermission = ContextCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.READ_MEDIA_IMAGES
        )

        return if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
            Timber.e("true")
            if(imagePermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        android.Manifest.permission.READ_MEDIA_IMAGES
                    ), 1
                )

                false
            } else {
                true
            }
        } else{
            Timber.e("else")
            if(readPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ), 1
                )
                false
            } else {
                true
            }
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_PICK = 2
    }
}