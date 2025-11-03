package com.olserapratama.printer.implementation.kassen.xa_923

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.olserapratama.printer.R
import com.olserapratama.printer.util.*
import com.olserapratama.printer.util.PrinterUtil.stringToBarcode
import com.olserapratama.printer.util.PrinterUtil.stringToQrCode
import com.szsicod.print.escpos.PrinterAPI
import com.szsicod.print.io.USBAPI


object Kassen923PrintFunctions {
    lateinit var printer: PrinterAPI
    var printerStatus = false

    fun connectPrinter(context: Context): String {
        return try {
            printer = PrinterAPI.getInstance()
            printer.connect(USBAPI(context))
            if (printer.isConnect){
                printerStatus = true
                context.getString(R.string.printer_connected)
            } else {
                printerStatus = false
                context.getString(R.string.printer_not_connected)
            }
        } catch (e: Exception) {
            printerStatus = false
            e.printStackTrace().toString()
        }
    }

    fun disconnect() {
        printer.disconnect()
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, charCount: Int): Boolean {
        if (!printer.isConnect) {
            return false
        }

        try {
            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        printer.printString("\n".repeat(it.lineCount))
                    }
                    is TextCenter -> {
                        printer.setAlignMode(1)
                        printer.printString(it.text+ "\n")
                    }
                    is TextLeft -> {
                        printer.setAlignMode(0)
                        printer.printString(it.text+ "\n")
                    }
                    is TextRight -> {
                        printer.setAlignMode(2)
                        printer.printString(it.text+ "\n")
                    }
                    is TextCustomSize -> {
                        printer.setAlignMode(1)
                        printer.printString(it.text+ "\n")
                    }
                    is TextLeftRight -> {
                        printer.setAlignMode(1)
                        printer.printString(PrinterUtil.formatLeftRight(it.left, it.right, charCount) + "\n")
                    }
                    is Image -> {
                        val bitmap = Glide.with(context)
                            .asBitmap()
                            .load(it.url)
                            .apply(RequestOptions().override(it.width, it.height).downsample(DownsampleStrategy.CENTER_INSIDE))
                            .submit(it.width, it.height)
                            .get()
                        printer.setAlignMode(1)
                        printer.printRasterBitmap(bitmap)
                    }
                    is Line -> {
                        var line = ""
                        for (i in 0 until charCount) {
                            line += "-"
                        }
                        printer.printString(line + "\n")
                    }
                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        printer.setAlignMode(1)
                        printer.printRasterBitmap(bitmap)
                    }
                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        printer.setAlignMode(1)
                        printer.printRasterBitmap(bitmap)
                    }
                    else -> {}
                }
            }

            printer.printAndFeedLine(5)
//            printer.fullCut()
            printer.cutMark()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun openDrawer(): Boolean{
        if (!printer.isConnect) {
            return false
        }

        return try {
            printer.openCashDrawer(0, 100, 200)
            true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }
}