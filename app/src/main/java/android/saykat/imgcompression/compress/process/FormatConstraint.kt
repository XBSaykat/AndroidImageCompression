package android.saykat.imgcompression.compress.process

import android.graphics.Bitmap
import android.saykat.imgcompression.compress.compressFormat
import android.saykat.imgcompression.compress.loadBitmap
import android.saykat.imgcompression.compress.overWrite
import java.io.File

class FormatConstraint (private val format: Bitmap.CompressFormat) : Constraint {

    override fun isSatisfied(imageFile: File): Boolean {
        return format == imageFile.compressFormat()
    }

    override fun satisfy(imageFile: File): File {
        return overWrite(imageFile, loadBitmap(imageFile), format)
    }
}

fun Compression.format(format: Bitmap.CompressFormat) {
    constraint(FormatConstraint(format))
}