package com.dod.travel.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

internal object ImageUtil {

    private lateinit var out: FileOutputStream

    fun resizeBitMap(uri: Uri, resize: Int, context: Context): Bitmap? {
        var resizeBitmap: Bitmap? = null
        val ratioTemp = 2

        val options = BitmapFactory.Options()
        try {
            BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(uri),
                null,
                options
            )
            var width = options.outWidth
            var height = options.outHeight
            var sampleSize = 1

            while (true) {
                if (width / ratioTemp < resize || height / ratioTemp < resize) break

                width /= ratioTemp
                height /= ratioTemp
                sampleSize *= ratioTemp
            }

            options.inSampleSize = sampleSize
            val bitmap = BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(uri),
                null,
                options
            )
            resizeBitmap = bitmap

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return rotateBitmap(resizeBitmap!!)
    }

    private fun rotateBitmap(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(90f)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)
        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)
    }
}