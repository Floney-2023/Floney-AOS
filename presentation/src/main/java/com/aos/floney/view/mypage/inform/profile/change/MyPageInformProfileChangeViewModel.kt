package com.aos.floney.view.mypage.inform.profile.change

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.aos.data.util.CommonUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.mypage.ChangeProfileUseCase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.letspl.oceankeeper.util.ImgFileMaker
import com.letspl.oceankeeper.util.RotateTransform
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MyPageInformProfileChangeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val changeProfileUseCase: ChangeProfileUseCase,
) :
    BaseViewModel() {

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 이미지 선택 버튼 클릭
    private var _onClickChoiceImage = MutableEventFlow<Boolean>()
    val onClickChoiceImage: EventFlow<Boolean> get() = _onClickChoiceImage

    // 변경하기 버튼 클릭
    private var _onClickChange = MutableEventFlow<Boolean>()
    val onClickChange: EventFlow<Boolean> get() = _onClickChange

    // 사진 촬영 uri
    private var takeCaptureUri: Uri? = null
    private var imageBitmap: Bitmap? = null

    private fun getChangeProfile(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            changeProfileUseCase(path).onSuccess {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowSuccessToast("프로필 변경이 성공하였습니다."))
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast("프로필 변경이 실패하였습니다."))
            }
        }
    }

    // 파이어베이스 이미지 파일 업로드
    fun uploadImageFile(bitmap: Bitmap) {
        baseEvent(Event.ShowLoading)
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageRef = storageRef.child("dev/users/${getUserEmail()}/profile.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            baseEvent(Event.HideLoading)
            baseEvent(Event.ShowToast("프로필 변경이 실패하였습니다."))
        }.addOnSuccessListener {
            // 다운로드 링크 가져오기
            it.storage.downloadUrl.addOnSuccessListener {uri ->
                // 성공
                CommonUtil.userProfileImg = uri.toString()
                getChangeProfile(uri.toString())
            }.addOnFailureListener {
                // 실패
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast("프로필 변경이 실패하였습니다."))
            }
        }
    }

    // 사진 촬영을 위해 임시 파일 생성
    fun createTempImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            content
        )
    }

    // 회전 각도를 맞춘 이미지 파일 생성
    fun createBitmapFile(uri: Uri?): Bitmap? {
        return if (uri != null) {
            val path =
                ImgFileMaker.getFullPathFromUri(context, uri)!!
            val angle = RotateTransform.getRotationAngle(path)
            val bitmap = RotateTransform.rotateImage(
                context,
                BitmapFactory.decodeFile(path),
                angle.toFloat(),
                uri
            )
            if (bitmap != null) {
                setImageBitmap(bitmap)
                bitmap
            } else {
                baseEvent(Event.ShowToast("이미지 파일 생성에 실패하였습니다."))
                null
            }
        } else {
            baseEvent(Event.ShowToast("이미지 파일 설정에 실패하였습니다."))
            null
        }
    }

    // 이전 페이지로 이동
    fun onClickPreviousPage() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 프로필 이미지 설정
    fun onClickSettingImage() {
        viewModelScope.launch {
            _onClickChoiceImage.emit(true)
        }
    }

    // 기본 이미지로 설정
    fun onClickBasicSettingImage() {

    }

    // 변경하기 버튼 클릭
    fun onClickProfileChange() {
        Timber.e("asdassdad")
        viewModelScope.launch {
            _onClickChange.emit(true)
        }
    }

    // 임시 촬영 uri 저장
    fun setTakeCaptureUri(uri: Uri?) {
        takeCaptureUri = uri
    }

    // 임시 촬영 uri 불러오기
    fun getTakeCaptureUri(): Uri? {
        return takeCaptureUri
    }

    // 임시 촬영 파일 저장
    private fun setImageBitmap(bitmap: Bitmap?) {
        imageBitmap = bitmap
    }

    // 임시 촬영 파일 불러오기
    fun getImageBitmap(): Bitmap? {
        return imageBitmap
    }

    // 유저 이메일 불러오기
    private fun getUserEmail(): String {
        return CommonUtil.userEmail
    }

    // 유저 프로필 이미지 불러오기
    fun getUserProfile(): String {
        return CommonUtil.userProfileImg
    }

}