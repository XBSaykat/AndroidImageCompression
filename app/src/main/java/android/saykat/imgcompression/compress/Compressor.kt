package android.saykat.imgcompression.compress

import android.content.Context
import android.saykat.imgcompression.compress.process.Compression
import android.saykat.imgcompression.compress.process.default
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object Compressor {
    suspend fun compress(
        context: Context,
        imageFile: File,
        coroutineContext: CoroutineDispatcher = Dispatchers.IO,
        compressionPatch: Compression.() -> Unit = { default() }
    ) = withContext(coroutineContext) {
        val compression = Compression().apply(compressionPatch)
        var result = copyToCache(context, imageFile)
        compression.constraints.forEach { constraint ->
            while (constraint.isSatisfied(result).not()) {
                result = constraint.satisfy(result)
            }
        }
        return@withContext result
    }
}