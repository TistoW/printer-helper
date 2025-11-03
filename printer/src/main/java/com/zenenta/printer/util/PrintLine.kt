package com.zenenta.printer.util

import android.graphics.Bitmap

sealed class PrintLine

data class EmptyLine(val lineCount: Int = 1) : PrintLine()
data class TextCenter(val text: String) : PrintLine()
data class TextLeft(val text: String) : PrintLine()
data class TextRight(val text: String) : PrintLine()
data class TextCustomSize(val text: String, val size: Int = 1): PrintLine()
data class TextLeftRight(val left: String, val right: String) : PrintLine()
data class Image(val url: String, val width: Int = 200, val height: Int = 200) : PrintLine()
data class BitmapImage(val bitmap: Bitmap) : PrintLine()
data class Barcode(val text: String, val width: Int = 200) : PrintLine()
data class QRCode(val text: String, val width: Int = 300, val height: Int = 300) : PrintLine()
object Line : PrintLine()