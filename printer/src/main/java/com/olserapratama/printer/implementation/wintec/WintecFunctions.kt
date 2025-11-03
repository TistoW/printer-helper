package com.olserapratama.printer.implementation.wintec

import android.content.Context
import cn.wintec.wtandroidjar.ComIO
import com.bumptech.glide.Glide
import com.olserapratama.printer.util.*
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import cn.wintec.wtandroidjar.Printer
import com.olserapratama.printer.R
import com.olserapratama.printer.implementation.zjiang.ZjiangFunctions
import com.olserapratama.printer.libs.zjianglibs.Command
import com.olserapratama.printer.util.PrinterUtil.stringToBarcode
import com.olserapratama.printer.util.PrinterUtil.stringToQrCode

object WintecFunctions {
    var statusPrinter = false
    private var printer: Printer? = null
    private const val ALIGN_LEFT = 0
    private const val ALIGN_CENTER = 1
    private const val ALIGN_RIGHT = 2

    fun connect(context: Context): String {
        printer = Printer("/dev/ttySAC3", ComIO.Baudrate.BAUD_38400)

        return if (printer == null){
            statusPrinter = false
            context.getString(R.string.printer_not_connected)
        } else {
            statusPrinter = true
            context.getString(R.string.printer_connected)
        }
    }

    fun disconnect() {
        printer?.PRN_Close()
        printer = null
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, charCount: Int): Boolean {
        if (printer == null){
            return false
        }

        lines.forEach {
            when (it) {
                is EmptyLine -> {
                    printer?.PRN_PrintAndFeedLine(it.lineCount)
                }
                is TextCenter -> {
                    printer?.PRN_Alignment(ALIGN_CENTER)
                    printer?.PRN_Print(it.text, "gbk")
                }
                is TextLeft -> {
                    printer?.PRN_Alignment(ALIGN_LEFT)
                    printer?.PRN_Print(it.text, "gbk")
                }
                is TextRight -> {
                    printer?.PRN_Alignment(ALIGN_RIGHT)
                    printer?.PRN_Print(it.text, "gbk")
                }
                is TextLeftRight -> {
                    printer?.PRN_Alignment(ALIGN_LEFT)
                    printer?.PRN_Print(PrinterUtil.formatLeftRight(it.left, it.right, charCount), "gbk")
                }
                is TextCustomSize -> {

                }
                is Image -> {
                    val bitmap = Glide.with(context)
                        .asBitmap()
                        .load(it.url)
                        .apply(RequestOptions().override(it.width, it.height).downsample(DownsampleStrategy.CENTER_INSIDE))
                        .submit(it.width, it.height)
                        .get()

                    printer?.PRN_PrintDotBitmap(bitmap, ALIGN_CENTER)
                }
                is BitmapImage -> {
                    try {
                        printer?.PRN_PrintDotBitmap(it.bitmap, ALIGN_CENTER)
                    } catch (e: Exception) {
                        //
                    }
                }
                is Line -> {
                    var line = ""
                    for (i in 0 until charCount) {
                        line += "-"
                    }
                    printer?.PRN_Alignment(ALIGN_LEFT)
                    printer?.PRN_Print(line, "gbk")

                }
                is Barcode -> {
                    val bitmap = stringToBarcode(it.text, it.width)
                    printer?.PRN_PrintDotBitmap(bitmap, ALIGN_CENTER)
                }
                is QRCode -> {
                    val bitmap = stringToQrCode(it.text, it.width, it.height)
                    printer?.PRN_PrintDotBitmap(bitmap, ALIGN_CENTER)
                }
            }
        }
        printer?.PRN_PrintAndFeedLine(3)
        printer?.PRN_HalfCutPaper()

        return true
    }

    fun openDrawer(){
        try {
            printer?.PRN_SetHT(byteArrayOf(0x1b, 0x70, 0x00))
            printer?.PRN_SetHT(byteArrayOf(0x1d, 0x72, 0x02))
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}