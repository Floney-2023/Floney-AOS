package com.letspl.oceankeeper.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ImgFileMaker {
    // Uri 로부터 실제 파일 경로를 얻어옴
    fun getFullPathFromUri(ctx: Context, fileUri: Uri?): String? {
        var fullPath: String? = null
        val column = "_data"
        var cursor = ctx.contentResolver.query(fileUri!!, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            var document_id = cursor.getString(0)
            if (document_id == null) {
                for (i in 0 until cursor.columnCount) {
                    if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
                cursor.close()
                val projection = arrayOf(column)
                try {
                    cursor = ctx.contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media._ID + " = ? ",
                        arrayOf(document_id),
                        null
                    )
                    if (cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                    }
                } finally {
                    if (cursor != null) cursor.close()
                }
            }
        }
        return fullPath
    }

    // 비트맵을 파일로 변환함
    fun saveBitmapToFile(bitmap: Bitmap, fileName: String?): File {
        val file: File = File(fileName)
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}