package com.olserapratama.printer.libs.zjianglibs

import android.graphics.Bitmap;
import zj.com.customize.sdk.Other;
import kotlin.experimental.or

object PrintPicture {

    fun POS_PrintBMP(mBitmap: Bitmap, nWidth: Int, nMode: Int): ByteArray? {
        val width = (nWidth + 7) / 8 * 8
        var height = mBitmap.height * width / mBitmap.width
        height = (height + 7) / 8 * 8
        var rszBitmap: Bitmap? = mBitmap
        if (mBitmap.width != width) {
            rszBitmap = Other.resizeImage(mBitmap, width, height)
        }
        val grayBitmap = Other.toGrayscale(rszBitmap)
        val dithered = Other.thresholdToBWPic(grayBitmap)
        return Other.eachLinePixToCmd(dithered, width, nMode)
    }

    fun Print_1D2A(bmp: Bitmap): ByteArray {
        val width = bmp.width
        val height = bmp.height
        val data = ByteArray(1024 * 10)
        data[0] = 0x1D
        data[1] = 0x2A
        data[2] = ((width - 1) / 8 + 1).toByte()
        data[3] = ((height - 1) / 8 + 1).toByte()
        var k: Byte = 0
        var position = 4
        var i: Int
        var j: Int
        var temp: Byte = 0
        i = 0
        while (i < width) {
            j = 0
            while (j < height) {
                if (bmp.getPixel(i, j) != -1) {
                    temp = temp or ((0x80 shr k.toInt()).toByte())
                } // end if
                k++
                if (k.toInt() == 8) {
                    data[position++] = temp
                    temp = 0
                    k = 0
                } // end if k
                j++
            }
            if (k % 8 != 0) {
                data[position++] = temp
                temp = 0
                k = 0
            }
            i++
        }
        println("data$data")
        if (width % 8 != 0) {
            i = height / 8
            if (height % 8 != 0) i++
            j = 8 - width % 8
            k = 0
            while (k < i * j) {
                data[position++] = 0
                k++
            }
        }
        return data
    }

}