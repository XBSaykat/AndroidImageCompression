package android.saykat.imgcompression.compress.process

import android.graphics.BitmapFactory
import android.saykat.imgcompression.compress.calculateInSampleSize
import android.saykat.imgcompression.compress.decodeSampledBitmapFromFile
import android.saykat.imgcompression.compress.determineImageRotation
import android.saykat.imgcompression.compress.overWrite
import java.io.File

class ResolutionConstraint (private val width: Int, private val height: Int) : Constraint {

    override fun isSatisfied(imageFile: File): Boolean {
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(imageFile.absolutePath, this)
            calculateInSampleSize(this, width, height) <= 1
        }
    }

    override fun satisfy(imageFile: File): File {
        return decodeSampledBitmapFromFile(imageFile, width, height).run {
            determineImageRotation(imageFile, this).run {
                overWrite(imageFile, this)
            }
        }
    }
}

fun Compression.resolution(width: Int, height: Int) {
    constraint(ResolutionConstraint(width, height))
}