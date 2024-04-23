package com.aos.floney.view.book.setting.excel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import com.aos.floney.R
import com.aos.floney.base.BaseBottomSheetFragment
import com.aos.floney.databinding.BottomSheetBookSettingAssetBinding
import com.aos.floney.databinding.BottomSheetBookSettingExcelBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.RequestBody
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@AndroidEntryPoint
class BookSettingExcelBottomSheetFragment :
    BaseBottomSheetFragment<BottomSheetBookSettingExcelBinding, BookSettingExcelViewModel>
        (R.layout.bottom_sheet_book_setting_excel) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModelObserver()

    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 나중에 하기 -> Bottomsheet 닫기
            viewModel.completePage.collect {
                Timber.e("nextPage $it")
                val fileUri = saveFileFromResponseBody(requireContext(), it, "[Floney]+가계부+엑셀+파일.xlsx")

                fileUri?.let {
                    shareFile(requireContext(), it, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                }
            }
        }

    }
    private fun saveFileFromResponseBody(context: Context, body: ResponseBody, fileName: String): Uri? {
        try {
            // 파일을 저장할 경로 설정
            val futureFile = File(context.getExternalFilesDir(null), fileName)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureFile)

                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                }

                outputStream.flush()
                // FileProvider를 통해 Uri를 얻어 반환
                return FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", futureFile)
            } catch (e: IOException) {
                return null
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            return null
        }
    }
    fun shareFile(context: Context, fileUri: Uri, fileType: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = fileType
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, null))
    }
}