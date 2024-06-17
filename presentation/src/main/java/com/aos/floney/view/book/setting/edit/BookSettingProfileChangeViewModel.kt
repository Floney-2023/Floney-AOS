package com.aos.floney.view.book.setting.edit

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.data.util.CommonUtil
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.bookadd.ChangeBookImgUseCase
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
class BookSettingProfileChangeViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
    private val prefs: SharedPreferenceUtil,
    private val changeBookImgUseCase: ChangeBookImgUseCase
): BaseViewModel() {

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    var profileImg: LiveData<String> = stateHandle.getLiveData("profileImg")

    // 이미지 선택 버튼 클릭
    private var _onClickChoiceImage = MutableEventFlow<Boolean>()
    val onClickChoiceImage: EventFlow<Boolean> get() = _onClickChoiceImage

    // 변경하기 버튼 클릭
    private var _onClickChange = MutableEventFlow<Boolean>()
    val onClickChange: EventFlow<Boolean> get() = _onClickChange

    // 기본 프로필로 변경 버튼 클릭
    private var _onClickDefaultProfile = MutableEventFlow<Boolean>()
    val onClickDefaultProfile: EventFlow<Boolean> get() = _onClickDefaultProfile

    // 사진 촬영 uri
    private var takeCaptureUri: Uri? = null
    private var imageBitmap: Bitmap? = null

    init {
        Timber.e("profileImg ${profileImg.value}")
    }

    private fun getChangeProfile(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            changeBookImgUseCase(prefs.getString("bookKey", ""), path).onSuccess {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowSuccessToast("변경이 완료되었습니다."))
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
                CommonUtil.bookProfile = uri.toString()
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

    // 랜덤 이미지 uri 가져오기
    fun getRandomProfileDrawable(): Int {
        val random = kotlin.random.Random.nextInt(0, 5)
        val drawable: Int = when(random) {
            0 -> R.drawable.random_profile_icon_1
            1 -> R.drawable.random_profile_icon_2
            2 -> R.drawable.random_profile_icon_3
            3 -> R.drawable.random_profile_icon_4
            4 -> R.drawable.random_profile_icon_5
            5 -> R.drawable.random_profile_icon_6
            else -> R.drawable.random_profile_icon_1
        }

        return drawable
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
        viewModelScope.launch {
            _onClickDefaultProfile.emit(true)
        }
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
    fun setImageBitmap(bitmap: Bitmap?) {
        imageBitmap = bitmap?.let { cropBitmapToSquare(it) }
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
    fun cropBitmapToSquare(bitmap: Bitmap): Bitmap {
        val size = Math.min(bitmap.width, bitmap.height)
        val x = (bitmap.width - size) / 2
        val y = (bitmap.height - size) / 2
        return Bitmap.createBitmap(bitmap, x, y, size, size)
    }

}