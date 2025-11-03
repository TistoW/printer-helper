package com.zenenta.printer.implementation.enibit

import android.content.Context
import android.graphics.Bitmap
import com.RT_Printer.BluetoothPrinter.BLUETOOTH.BluetoothPrintDriver
import com.zenenta.printer.R
import com.zenenta.printer.repository.Setting
import com.zenenta.printer.util.*
import com.zenenta.printer.util.PrinterUtil.bitmapToByteArray
import com.zenenta.printer.util.PrinterUtil.getLogoBitmapForReceipt
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode

object EnibitFunctions {
    var statusPrinter = false

    fun connect(context: Context, printerSetting: Setting): String {
        if (BluetoothPrintDriver.IsNoConnection()) {
            return if (BluetoothPrintDriver.OpenPrinter(printerSetting.address)) {
                statusPrinter = true
                context.getString(R.string.printer_connected)
            } else {
                BluetoothPrintDriver.close()
                statusPrinter = false
                context.getString(R.string.printer_not_connected)
            }
        }
        statusPrinter = false
        return "Printer not found!"
    }

    fun disconnect() {
        if (!BluetoothPrintDriver.IsNoConnection()) {
            BluetoothPrintDriver.close()
        }
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, printerSetting: Setting): Boolean {
        try {
            if (BluetoothPrintDriver.IsNoConnection()) {
                statusPrinter = false
                return false
            }

            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        BluetoothPrintDriver.ImportData("\n".repeat(it.lineCount))
                    }
                    is TextCenter -> {
                        BluetoothPrintDriver.AddAlignMode(1)
                        BluetoothPrintDriver.ImportData(it.text + "\n")
                    }
                    is TextLeft -> {
                        BluetoothPrintDriver.AddAlignMode(0)
                        BluetoothPrintDriver.ImportData(it.text + "\n")
                    }
                    is TextRight -> {
                        BluetoothPrintDriver.AddAlignMode(2)
                        BluetoothPrintDriver.ImportData(it.text + "\n")
                    }
                    is TextLeftRight -> {
                        BluetoothPrintDriver.AddAlignMode(0)
                        BluetoothPrintDriver.ImportData(PrinterUtil.formatLeftRight(it.left, it.right, printerSetting.charCount) + "\n")
                    }
                    is Image -> {
                        BluetoothPrintDriver.AddAlignMode(1)
                        val bitmap: Bitmap = getLogoBitmapForReceipt(context, it.url, it.width, it.height, 380.0)
                        val command: ByteArray = bitmapToByteArray(bitmap)
                        BluetoothPrintDriver.printByteData(command)
                    }
                    is TextCustomSize -> {
                        BluetoothPrintDriver.AddAlignMode(1)
                        BluetoothPrintDriver.ImportData(it.text + "\n")
                    }
                    is Line -> {
                        var line = ""
                        for (i in 0 until printerSetting.charCount) {
                            line += "-"
                        }
                        BluetoothPrintDriver.AddAlignMode(0)
                        BluetoothPrintDriver.ImportData(line + "\n")
                    }
                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        val command: ByteArray = bitmapToByteArray(bitmap)
                        BluetoothPrintDriver.printByteData(command)
                    }
                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        val command: ByteArray = bitmapToByteArray(bitmap)
                        BluetoothPrintDriver.printByteData(command)
                    }
                    else -> {}
                }
            }

            BluetoothPrintDriver.ImportData("\n".repeat(2))
            BluetoothPrintDriver.excute()
            BluetoothPrintDriver.ClearData()

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }


}