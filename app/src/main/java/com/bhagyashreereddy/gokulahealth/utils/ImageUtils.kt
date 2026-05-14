package com.bhagyashreereddy.gokulahealth.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object ImageUtils {

    /**
     * Saves image from gallery URI to app's internal storage.
     * Internal storage is private — doesn't need WRITE_EXTERNAL_STORAGE.
     */
    fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val fileName = "cattle_${UUID.randomUUID()}.jpg"
            val file = File(context.filesDir, fileName)

            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            inputStream.close()
            file.absolutePath    // Return the saved path
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Delete a stored cattle image when cattle is deleted.
     */
    fun deleteImage(imagePath: String?) {
        if (imagePath != null) {
            val file = File(imagePath)
            if (file.exists()) file.delete()
        }
    }
}
