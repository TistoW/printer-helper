package com.zenenta.printer.implementation.zonerich

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.zenenta.printer.R
import com.zenenta.printer.repository.Setting
import com.zenenta.printer.util.*
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode
import com.zqprintersdk.PrinterConst
import com.zqprintersdk.ZQPrinterSDK

object ZonerichFunctions {
    private var prnZQ: ZQPrinterSDK? = null
    var statusPrinter = false

    fun connect(context: Context, printerSetting: Setting): String {
        prnZQ = ZQPrinterSDK()

        val nRet = prnZQ!!.Prn_Connect(printerSetting.address, context)
        if (nRet != PrinterConst.ErrorCode.SUCCESS) {
            statusPrinter = false
            return context.getString(R.string.printer_not_connected)
        }
        statusPrinter = true
        return context.getString(R.string.printer_connected)
    }

    fun disconnect() {
        statusPrinter = false
        prnZQ?.Prn_Disconnect()
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, printerSetting: Setting): Boolean {
        try {
            if (prnZQ?.Prn_Status() != PrinterConst.PrinterStatus.ONLINE) {
                statusPrinter = false
                return false
            }

            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        prnZQ?.Prn_PrintString("\n".repeat(it.lineCount))
                    }

                    is TextCenter -> {
                        prnZQ?.Prn_SetAlignment(PrinterConst.Alignment.CENTER)
                        prnZQ?.Prn_PrintString(it.text + "\n")
                    }

                    is TextLeft -> {
                        prnZQ?.Prn_SetAlignment(PrinterConst.Alignment.LEFT)
                        prnZQ?.Prn_PrintString(it.text + "\n")
                    }

                    is TextRight -> {
                        prnZQ?.Prn_SetAlignment(PrinterConst.Alignment.RIGHT)
                        prnZQ?.Prn_PrintString(it.text + "\n")
                    }

                    is TextLeftRight -> {
                        prnZQ?.Prn_SetAlignment(PrinterConst.Alignment.LEFT)
                        prnZQ?.Prn_PrintString(
                            PrinterUtil.formatLeftRight(
                                it.left,
                                it.right,
                                printerSetting.charCount
                            ) + "\n"
                        )
                    }

                    is TextCustomSize -> {

                    }
                    is Image -> {
                        prnZQ?.Prn_SetAlignment(PrinterConst.Alignment.CENTER)
                        val bitmap = Glide.with(context)
                            .asBitmap()
                            .load(it.url)
                            .apply(
                                RequestOptions().override(it.width, it.height)
                                    .downsample(DownsampleStrategy.CENTER_INSIDE)
                            )
                            .submit(it.width, it.height)
                            .get()

                        if (bitmap != null)
                            prnZQ?.Prn_PrintBitmap(bitmap, PrinterConst.BitmapSize.ZQSIZE0)
                    }

                    is BitmapImage -> {
                        try {
                            prnZQ?.Prn_PrintBitmap(it.bitmap, PrinterConst.BitmapSize.ZQSIZE0)
                        } catch (e: Exception) {
                            //
                        }
                    }

                    is Line -> {
                        prnZQ?.Prn_SetAlignment(PrinterConst.Alignment.CENTER)
                        prnZQ?.Prn_PrintString("-".repeat(printerSetting.charCount) + "\n")
                    }

                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        prnZQ?.Prn_PrintBitmap(bitmap, PrinterConst.BitmapSize.ZQSIZE0)
                    }

                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        prnZQ?.Prn_PrintBitmap(bitmap, PrinterConst.BitmapSize.ZQSIZE0)
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            statusPrinter = false
            return false
        }
        return true
    }

    fun openDrawer() {
        try {
            if (prnZQ?.Prn_Status() != PrinterConst.PrinterStatus.ONLINE) {
                statusPrinter = false
                return
            }

            prnZQ?.Prn_OpenCashbox()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}