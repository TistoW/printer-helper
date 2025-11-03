package com.zenenta.printer.implementation.kassen.xa_02

import android.content.Context
import android.os.Message
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.zenenta.printer.R
import com.zenenta.printer.implementation.kassen.KassenApiHelper
import com.zenenta.printer.util.*
import com.zenenta.printer.util.PrinterUtil.getBlackWhiteBitmap
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode
import timber.log.Timber


object Kassen02PrintFunctions {
    var tag = "KassenPrintThread"

    private var m_bThreadFinished = true
    //private var isSuccess = true

    var printerStatus = false

    var kassenApiHelper = KassenApiHelper.getInstance()

    private const val ENABLE_RG = 10
    private const val DISABLE_RG = 11
    var type = 0
    var ret = -1
    private var RESULT_CODE = 0
    var IsWorking = 0
    private val BatteryV = 0


    fun connectPrinter(context: Context): String {
        val msg: Message = Message.obtain()
        val msg1 = Message()

        printerStatus = true
        m_bThreadFinished = false
        try {
            ret = kassenApiHelper.PrintInit()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        kassenApiHelper.PrintSetGray(4)
        ret = kassenApiHelper.PrintCheckStatus()
        when (ret) {
            -1 -> {
                RESULT_CODE = -1
                Timber.tag(tag).e("Lib_PrnCheckStatus fail, ret = $ret")
                m_bThreadFinished = true
                printerStatus = false
                return "Error, No Paper "
            }
            -2 -> {
                RESULT_CODE = -1
                Timber.tag(tag).e("Lib_PrnCheckStatus fail, ret = $ret")
                m_bThreadFinished = true
                printerStatus = false
                return "Error, Printer Too Hot "
            }
            -3 -> {
                RESULT_CODE = -1
                Timber.tag(tag).e("voltage = ${BatteryV * 2}" )
                m_bThreadFinished = true
                printerStatus = false
                return "Battery less :" + (BatteryV * 2)
            }
            else -> {
                RESULT_CODE = 0
            }
        }


        msg.what = DISABLE_RG
        println("MESSAGE: $msg")

        kassenApiHelper.PrintSetFont(24.toByte(), 24.toByte(), 0x00.toByte())


        return context.getString(R.string.printer_ready)
    }


    fun printReceipt(context: Context, lines: List<PrintLine>, charCount: Int): Boolean {
        val msg1 = Message()
        try {
            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        kassenApiHelper.PrintStr("\n".repeat(it.lineCount))
                    }
                    is TextCenter -> {
                        kassenApiHelper.PrintSetAlign(1)
                        val listString: List<String> = it.text.split("\n")
                        if (listString.isNotEmpty()) {
                            for (a in listString.indices) {
                                kassenApiHelper.PrintStr(listString[a])
                            }
                        }
                    }
                    is TextLeft -> {
                        kassenApiHelper.PrintSetAlign(0)
                        kassenApiHelper.PrintStr(it.text+ "\n")
                    }
                    is TextRight -> {
                        kassenApiHelper.PrintSetAlign(2)
                        val listString: List<String> = it.text.split("\n")
                        if (listString.isNotEmpty()) {
                            for (a in listString.indices) {
                                kassenApiHelper.PrintStr(listString[a])
                            }
                        }
                    }
                    is TextLeftRight -> {
                        kassenApiHelper.PrintSetAlign(0)
                        kassenApiHelper.PrintStr(PrinterUtil.formatLeftRight(it.left, it.right, charCount) + "\n")
                    }
                    is TextCustomSize -> {
                        kassenApiHelper.PrintSetAlign(1)
                        val listString: List<String> = it.text.split("\n")
                        if (listString.isNotEmpty()) {
                            for (a in listString.indices) {
                                kassenApiHelper.PrintStr(listString[a])
                            }
                        }
                    }
                    is Image -> {
                        val bitmap = Glide.with(context)
                            .asBitmap()
                            .load(it.url)
                            .apply(RequestOptions().override(it.width, it.height).downsample(DownsampleStrategy.CENTER_INSIDE))
                            .submit(it.width, it.height)
                            .get()
                        kassenApiHelper.PrintSetAlign(1)
                        val greyScaleBitmap = getBlackWhiteBitmap(bitmap)
                        kassenApiHelper.PrintBmp(greyScaleBitmap)
                    }

                    is BitmapImage -> {
                        try {
                            kassenApiHelper.PrintSetAlign(1)
                            val greyScaleBitmap = getBlackWhiteBitmap(it.bitmap)
                            kassenApiHelper.PrintBmp(greyScaleBitmap)
                        } catch (e: Exception) {
                            //
                        }
                    }
                    is Line -> {
                        var line = ""
                        for (i in 0 until charCount) {
                            line += "-"
                        }
                        kassenApiHelper.PrintStr(line + "\n")
                    }
                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        kassenApiHelper.PrintSetAlign(1)
                        val greyScaleBitmap = getBlackWhiteBitmap(bitmap)
                        kassenApiHelper.PrintBmp(greyScaleBitmap)
                    }
                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        kassenApiHelper.PrintSetAlign(1)
                        val greyScaleBitmap = getBlackWhiteBitmap(bitmap)
                        kassenApiHelper.PrintBmp(greyScaleBitmap)
                    }
                }
            }
            kassenApiHelper.PrintStr("\n".repeat(4))
            ret = kassenApiHelper.PrintStart()
            msg1.what = ENABLE_RG

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

}