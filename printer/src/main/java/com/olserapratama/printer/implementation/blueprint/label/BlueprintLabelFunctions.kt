package com.olserapratama.printer.implementation.blueprint.label

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.olserapratama.printer.R
import com.olserapratama.printer.repository.Setting
import com.olserapratama.printer.util.*
import com.olserapratama.printer.util.PrinterUtil.formatAlignCenter
import com.olserapratama.printer.util.PrinterUtil.formatAlignRight
import com.olserapratama.printer.util.PrinterUtil.stringToBarcode
import com.olserapratama.printer.util.PrinterUtil.stringToQrCode
import tspl.HPRTPrinterHelper

object BlueprintLabelFunctions {
    var statusPrinter = false

    fun connect(context: Context, printerSetting: Setting): String {
        var connectMessage = ""
        try {
            val result = HPRTPrinterHelper.PortOpen("Bluetooth," + printerSetting.address)
            statusPrinter = if (result > 0) {
                context.getString(R.string.printer_connected)
                true
            } else {
                context.getString(R.string.printer_not_connected)
                false
            }

        } catch (e: Exception) {
            statusPrinter = false
            connectMessage = e.message.toString()
        }

        return connectMessage
    }

    fun disconnect() {
        try {
            HPRTPrinterHelper.PortClose()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, printerSetting: Setting): Boolean {
        if (!HPRTPrinterHelper.IsOpened()) {
            return false
        }

        if (HPRTPrinterHelper.getPrinterStatus() != HPRTPrinterHelper.STATUS_OK) {
            return false
        }

        var totalHeight = 0
        HPRTPrinterHelper.CLS()
        lines.forEach {
            when (it) {
                is EmptyLine -> {
                    HPRTPrinterHelper.printText(
                        "120",
                        "" + totalHeight,
                        "3",
                        "" + "0",
                        1,
                        "\n".repeat(it.lineCount)
                    )
                    totalHeight += (30 * it.lineCount)
                    HPRTPrinterHelper.Print("1", "1")
                }
                is TextCenter -> {
                    HPRTPrinterHelper.printText(
                        "120",
                        "" + totalHeight,
                        "3",
                        "" + "0",
                        1,
                        formatAlignCenter(it.text, printerSetting.charCount)
                    )
                    totalHeight += 30
                    HPRTPrinterHelper.Print("1", "1")
                }
                is TextLeft -> {
                    HPRTPrinterHelper.printText("120", "" + totalHeight, "3", "" + "0", 1, it.text)
                    totalHeight += 30
                    HPRTPrinterHelper.Print("1", "1")
                }

                is TextCustomSize -> {

                }

                is TextRight -> {
                    HPRTPrinterHelper.printText(
                        "120",
                        "" + totalHeight,
                        "3",
                        "" + "0",
                        1,
                        formatAlignRight(it.text, printerSetting.charCount)
                    )
                    totalHeight += 30
                    HPRTPrinterHelper.Print("1", "1")
                }

                is TextLeftRight -> {
                    HPRTPrinterHelper.printText(
                        "120",
                        "" + totalHeight,
                        "3",
                        "" + "0",
                        1,
                        PrinterUtil.formatLeftRight(it.left, it.right, printerSetting.charCount)
                    )
                    totalHeight += 30
                    HPRTPrinterHelper.Print("1", "1")
                }

                is Image -> {
                    val bitmap = Glide.with(context)
                        .asBitmap()
                        .load(it.url)
                        .apply(
                            RequestOptions().override(it.width, it.height)
                                .downsample(DownsampleStrategy.CENTER_INSIDE)
                        )
                        .submit(it.width, it.height)
                        .get()

                    if (bitmap != null) {
                        try {
                            HPRTPrinterHelper.printAreaSize(
                                "" + bitmap.width / 8,
                                "" + bitmap.height / 8
                            )
                            HPRTPrinterHelper.printImage("0", "0", bitmap, true, false, 0)
                            totalHeight += bitmap.height
                            HPRTPrinterHelper.Print("1", "1")
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                is Line -> {
                    var line = ""
                    for (i in 0 until printerSetting.charCount) {
                        line += "-"
                    }
                    HPRTPrinterHelper.printText("120", "" + totalHeight, "3", "" + "0", 1, line)
                    totalHeight += 30
                    HPRTPrinterHelper.Print("1", "1")
                }

                is Barcode -> {
                    val bitmap = stringToBarcode(it.text, it.width)
                    HPRTPrinterHelper.printAreaSize("" + bitmap.width / 8, "" + bitmap.height / 8)
                    HPRTPrinterHelper.printImage("0", "0", bitmap, true, false, 0)
                    totalHeight += bitmap.height
                    HPRTPrinterHelper.Print("1", "1")
                }

                is QRCode -> {
                    val bitmap = stringToQrCode(it.text, it.width, it.height)
                    HPRTPrinterHelper.printAreaSize("" + bitmap.width / 8, "" + bitmap.height / 8)
                    HPRTPrinterHelper.printImage("0", "0", bitmap, true, false, 0)
                    totalHeight += bitmap.height
                    HPRTPrinterHelper.Print("1", "1")
                }
                is BitmapImage -> {
                    try {
                        HPRTPrinterHelper.printAreaSize(
                            "" + it.bitmap.width / 8,
                            "" + it.bitmap.height / 8
                        )
                        HPRTPrinterHelper.printImage("0", "0", it.bitmap, true, false, 0)
                        totalHeight += it.bitmap.height
                        HPRTPrinterHelper.Print("1", "1")
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
//        HPRTPrinterHelper.Print("1", "1")
        HPRTPrinterHelper.Cut()

        return true
    }

    fun printReceiptWithGap(
        context: Context,
        lines: List<PrintLine>,
        printerSetting: Setting
    ): Boolean {
        if (!HPRTPrinterHelper.IsOpened()) {
            return false
        }

        if (HPRTPrinterHelper.getPrinterStatus() != HPRTPrinterHelper.STATUS_OK) {
            return false
        }

        HPRTPrinterHelper.CLS()
        lines.forEach {
            when (it) {
                is EmptyLine -> {

                }

                is TextCenter -> {

                }

                is TextLeft -> {

                }

                is TextRight -> {

                }

                is TextLeftRight -> {

                }

                is Image -> {

                }

                is Line -> {

                }

                is Barcode -> {

                }

                is QRCode -> {

                }

                else -> {}
            }
        }
        HPRTPrinterHelper.Cut()
        return true

    }

    fun openDrawer() {
        try {
            HPRTPrinterHelper.WriteData(byteArrayOf(0x1b, 0x70, 0x00))
            HPRTPrinterHelper.WriteData(byteArrayOf(0x1d, 0x72, 0x02))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}