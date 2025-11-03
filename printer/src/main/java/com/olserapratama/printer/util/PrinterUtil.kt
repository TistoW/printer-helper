package com.olserapratama.printer.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.util.Base64
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import java.nio.ByteBuffer
import kotlin.experimental.or
import kotlin.math.max
import kotlin.math.roundToInt

object PrinterUtil {

    fun formatAlignCenter(text: String, charCount: Int): String {
        val lines = text.lines()

        var finalText = ""
        lines.forEach {
            val str1 = it.substring(0, (it.length/2))
            val str2 = it.substring((it.length/2))

            var firstText = ""
            var secondText = str2
            for (i in 0 until (charCount / 2) - str1.length){
                firstText += " "
            }
            firstText += str1
            for (i in (charCount/2) + str2.length until charCount){
                secondText += " "
            }
            finalText = finalText + firstText + secondText + "\n"
        }
        return finalText
    }

    fun formatAlignRight(text: String, charCount: Int): String {
        var editedText = ""
        for (i in 0 until charCount - text.length){
            editedText += " "
        }
        editedText += text
        return editedText
    }

    fun formatLeftRight(left: String, right: String, charCount: Int): String {
        val half = charCount / 2
        val leftEdited = left.replace("\n", " ")
        val leftList = leftEdited.chunked(half - 1)
        val rightList = right.chunked(half - 1)
        var text = ""

        val maxLines = max(leftList.size, rightList.size)
        for (i in 0 until maxLines) {
            val _left = if (i < leftList.size) leftList[i] else ""
            val _right = if (i < rightList.size) rightList[i] else ""

            val space = " ".repeat(charCount - _left.length - _right.length)
            text += _left + space + _right + "\n"
        }

        return text.substring(0, text.length-1)
        //return text
    }

    fun stringToQrCode(string: String, width: Int, height: Int): Bitmap {
        var bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )

        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4
        val qrEncoder = QRGEncoder(string, null, QRGContents.Type.TEXT, dimen)
        qrEncoder.colorBlack = Color.WHITE
        qrEncoder.colorWhite = Color.BLACK
        try {
            bitmap = qrEncoder.bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (bitmap == null){
            val imageBytes = Base64.decode(string, 0)
            bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        return bitmap
    }

    fun stringToBarcode(string: String, width: Int): Bitmap {
        val height = width * 0.35
        val bitMatrix = Code128Writer().encode(
            string,
            BarcodeFormat.CODE_128,
            width,
            height.roundToInt()
        )

        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] =
                    if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
            }
        }

        val bitmap = Bitmap.createBitmap(
            bitMatrix.width,
            bitMatrix.height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(
            pixels,
            0,
            bitMatrix.width,
            0,
            0,
            bitMatrix.width,
            bitMatrix.height
        )
        return bitmap
    }

    fun bitmapToByteArray(bm: Bitmap): ByteArray {
        var w: Int = bm.width
        val h: Int = bm.height
        if (w > 384) w = 384
        val bitw = (w + 7) / 8 * 8
        val pitch = bitw / 8
        val cmd = byteArrayOf(
            0x1D, 0x76, 0x30, 0x00, (pitch and 0xff).toByte(), (pitch shr 8 and 0xff).toByte(),
            (h and 0xff).toByte(),
            (h shr 8 and 0xff).toByte()
        )
        val bits = ByteArray(h * pitch)

        for (y in 0 until h) {
            for (x in 0 until w) {
                val color = bm.getPixel(x, y)
                if (color and 0xFF < 128) {
                    bits[y * pitch + x / 8] = bits[y * pitch + x / 8] or ((0x80 shr x % 8).toByte())
                }
            }
        }
        val bb: ByteBuffer = ByteBuffer.allocate(cmd.size + bits.size)
        bb.put(cmd)
        bb.put(bits)
        return bb.array()
    }

    fun getLogoBitmapForReceipt(context: Context, imageUrl: String, bmWidth: Int, bmHeight: Int, receiptWidth: Double): Bitmap {
        //val bm: Bitmap = getLogoBitmapUrl(context, imageUrl)
        val bitmap = Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .apply(RequestOptions().override(bmWidth, bmHeight).downsample(DownsampleStrategy.CENTER_INSIDE))
            .submit(bmWidth, bmHeight)
            .get()

        var dheight = bitmap.height.toDouble()
        var dwidth = bitmap.width.toDouble()
        if (dwidth < receiptWidth) {
            val newBitmap = Bitmap.createBitmap(receiptWidth.toInt(), dheight.toInt(), bitmap.config)

            val canvas = Canvas(newBitmap)
            canvas.drawColor(Color.WHITE)
            canvas.drawBitmap(bitmap, (receiptWidth / 2 - dwidth / 2).toFloat(), (0).toFloat(), null)
            return newBitmap
        }
        val scale = receiptWidth / dwidth
        dwidth *= scale
        dheight *= scale
        return Bitmap.createScaledBitmap(bitmap, dwidth.toInt(), dheight.toInt(), true)
    }

    fun getBlackWhiteBitmap(bitmap: Bitmap): Bitmap? {
        val w = bitmap.width
        val h = bitmap.height
        val resultBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
        var color = 0
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        var r1: Int
        var g1: Int
        var b1: Int
        val oldPx = IntArray(w * h)
        val newPx = IntArray(w * h)
        bitmap.getPixels(oldPx, 0, w, 0, 0, w, h)
        for (i in 0 until w * h) {
            color = oldPx[i]
            r = Color.red(color)
            g = Color.green(color)
            b = Color.blue(color)
            a = Color.alpha(color)
            //黑白矩阵
            r1 = (0.33 * r + 0.59 * g + 0.11 * b).toInt()
            g1 = (0.33 * r + 0.59 * g + 0.11 * b).toInt()
            b1 = (0.33 * r + 0.59 * g + 0.11 * b).toInt()
            //检查各像素值是否超出范围
            if (r1 > 255) {
                r1 = 255
            }
            if (g1 > 255) {
                g1 = 255
            }
            if (b1 > 255) {
                b1 = 255
            }
            newPx[i] = Color.argb(a, r1, g1, b1)
        }
        resultBitmap.setPixels(newPx, 0, w, 0, 0, w, h)
        return getGreyBitmap(resultBitmap)
    }

    private fun getGreyBitmap(bitmap: Bitmap): Bitmap? {
        return run {
            val width = bitmap.width
            val height = bitmap.height
            val pixels = IntArray(width * height)
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            val gray = IntArray(height * width)
            var e: Int
            var i: Int
            var j: Int
            var g: Int
            e = 0
            while (e < height) {
                i = 0
                while (i < width) {
                    j = pixels[width * e + i]
                    g = j and 16711680 shr 16
                    gray[width * e + i] = g
                    ++i
                }
                ++e
            }
            i = 0
            while (i < height) {
                j = 0
                while (j < width) {
                    g = gray[width * i + j]
                    if (g >= 128) {
                        pixels[width * i + j] = -1
                        e = g - 255
                    } else {
                        pixels[width * i + j] = -16777216
                        e = g - 0
                    }
                    if (j < width - 1 && i < height - 1) {
                        gray[width * i + j + 1] += 3 * e / 8
                        gray[width * (i + 1) + j] += 3 * e / 8
                        gray[width * (i + 1) + j + 1] += e / 4
                    } else if (j == width - 1 && i < height - 1) {
                        gray[width * (i + 1) + j] += 3 * e / 8
                    } else if (j < width - 1 && i == height - 1) {
                        gray[width * i + j + 1] += e / 4
                    }
                    ++j
                }
                ++i
            }
            val mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            mBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            mBitmap
        }
    }

}