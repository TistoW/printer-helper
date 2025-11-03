package com.olserapratama.printer.implementation.kassen.xa_921

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.minipos.device.CashDrawer
import com.minipos.device.Printer
import com.minipos.device.SDK
import com.olserapratama.printer.R
import com.olserapratama.printer.util.*
import com.olserapratama.printer.util.PrinterUtil.bitmapToByteArray
import com.olserapratama.printer.util.PrinterUtil.getLogoBitmapForReceipt
import com.olserapratama.printer.util.PrinterUtil.stringToBarcode
import com.olserapratama.printer.util.PrinterUtil.stringToQrCode
import timber.log.Timber


object Kassen921PrintFunctions {
    private lateinit var prt: Printer
    val PRINT_ALIGN_LEFT = byteArrayOf(0x1b, 'a'.code.toByte(), 0x00)
    val PRINT_ALIGN_CENTER = byteArrayOf(0x1b, 'a'.code.toByte(), 0x01)
    val PRINT_ALIGN_RIGHT = byteArrayOf(0x1b, 'a'.code.toByte(), 0x02)
    var printerStatus = false

    fun initPrinter(mContext: Context?) {
        try {
            SDK.init(mContext)
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    fun connectPrinter(context: Context): String{
        try {
            prt = Printer.newInstance()
            return if (!prt.ready()) {
                printerStatus = false
                context.getString(R.string.check_paper_tray)
            } else {
                printerStatus = true
                context.getString(R.string.printer_ready)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            printerStatus = false
            return e.message.toString()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            printerStatus = false
            return throwable.printStackTrace().toString()
        }
    }

    fun disconnectPrinter(){
        if (prt != null){
            prt.close()
        }
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, charCount: Int) {
        val bytes: List<ByteArray> = createTextReceipt(context, lines, charCount)
        if (printerStatus){
            for (i in bytes.indices) {
                prt.getOutputStream().write(bytes[i])
            }
        }
    }

    private fun createTextReceipt(context: Context, lines: List<PrintLine>, charCount: Int): List<ByteArray> {
        var list: List<ByteArray> = ArrayList()
        list = ArrayList()

        lines.forEach {
            when (it) {
                is EmptyLine -> {
                    list.add("\n".repeat(it.lineCount).toByteArray())
                }
                is TextCenter -> {
                    list.add(PRINT_ALIGN_CENTER)
                    list.add((it.text + "\n").toByteArray())
                }
                is TextLeft -> {
                    list.add(PRINT_ALIGN_LEFT)
                    list.add((it.text + "\n").toByteArray())
                }
                is TextRight -> {
                    list.add(PRINT_ALIGN_RIGHT)
                    list.add((it.text + "\n").toByteArray())

                }
                is TextLeftRight -> {
                    list.add(PRINT_ALIGN_LEFT)
                    list.add((PrinterUtil.formatLeftRight(it.left, it.right, charCount) + "\n").toByteArray())
                }
                is TextCustomSize -> {
                    list.add(PRINT_ALIGN_CENTER)
                    list.add((it.text + "\n").toByteArray())
                }
                is Image -> {
                    list.add(PRINT_ALIGN_CENTER)
                    val bitmap: Bitmap = getLogoBitmapForReceipt(context, it.url, it.width, it.height, 380.0)

                    val command: ByteArray = bitmapToByteArray(bitmap)
                    list.add(command)
                    list.add("\r\n".toByteArray())
                }
                is Line -> {
                    var line = ""
                    for (i in 0 until charCount) {
                        line += "-"
                    }
                    list.add((line + "\n").toByteArray())
                }
                is Barcode -> {
                    val bitmap = stringToBarcode(it.text, it.width)
                    val command: ByteArray = bitmapToByteArray(bitmap)
                    list.add(command)
                    list.add("\r\n".toByteArray())
                }
                is QRCode -> {
                    val bitmap = stringToQrCode(it.text, it.width, it.height)
                    val command: ByteArray = bitmapToByteArray(bitmap)
                    list.add(command)
                    list.add("\r\n".toByteArray())
                }
                else -> {}
            }
        }

        list.add("\n".repeat(3).toByteArray())

        return list
    }

    fun openDrawer(): Boolean{
        return try {
            var mCashDrawer: CashDrawer? = null
            mCashDrawer = CashDrawer.newInstance()
            mCashDrawer.kickOutPin2(100)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Timber.tag("april4").d(throwable.message!!)
            false
        }
    }

}