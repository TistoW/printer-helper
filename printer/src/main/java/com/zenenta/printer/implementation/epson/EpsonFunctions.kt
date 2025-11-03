package com.zenenta.printer.implementation.epson

import android.content.Context
import android.hardware.usb.UsbManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.epson.epos2.printer.Printer
import com.zenenta.printer.R
import com.zenenta.printer.repository.DeviceInterface
import com.zenenta.printer.repository.Setting
import com.zenenta.printer.util.*
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode
import timber.log.Timber

object EpsonFunctions {
    private var printer: Printer? = null
    var statusPrinter = false

    fun connect(context: Context, printerSetting: Setting): String{
        try {
            printer = printerSetting.modelId?.toInt()?.let { Printer(it, 0, context) }
            printer?.setConnectionEventListener { any, i -> Timber.d("AOEU connection $any $i") }
            printer?.setReceiveEventListener { printer, i, printerStatusInfo, s -> Timber.d("AOEU event $printer $i $printerStatusInfo $s") }
            printer?.setStatusChangeEventListener { printer, i -> Timber.d("AOEU status $printer $i") }

            var address = printerSetting.deviceInterface + printerSetting.address
            if (printerSetting.deviceInterface == DeviceInterface.USB.code){
                val mUsbManger = context.getSystemService(Context.USB_SERVICE) as UsbManager
                val deviceList = mUsbManger.deviceList
                if (deviceList.size > 0) {
                    val mDevice = deviceList.values.elementAt(0)
                    address = printerSetting.deviceInterface + mDevice.deviceName
                }
            }
            printer?.connect(address, Printer.PARAM_DEFAULT)
            statusPrinter = true
            return context.getString(R.string.printer_connected)
        } catch (e: Exception) {
            printer = null
            statusPrinter = false
            return e.message.toString()
        }
    }

    fun disconnect() {
        printer?.disconnect()
        printer = null
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, printerSetting: Setting): Boolean {
        if (printer == null){
            connect(context, printerSetting)
        } else {
            printer?.beginTransaction()
        }

        lines.forEach {
            when (it) {
                is EmptyLine -> {
                    printer?.addFeedLine(it.lineCount)
                }
                is TextCenter -> {
                    printer?.addTextAlign(Printer.ALIGN_CENTER)
                    printer?.addText(it.text+"\n")
                }
                is TextLeft -> {
                    printer?.addTextAlign(Printer.ALIGN_LEFT)
                    printer?.addText(it.text+"\n")
                }
                is TextRight -> {
                    printer?.addTextAlign(Printer.ALIGN_RIGHT)
                    printer?.addText(it.text+"\n")
                }
                is TextLeftRight -> {
                    printer?.addTextAlign(Printer.ALIGN_LEFT)
                    printer?.addText(PrinterUtil.formatLeftRight(it.left, it.right, printerSetting.charCount)+"\n")
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

                    printer?.addTextAlign(Printer.ALIGN_CENTER)
                    printer?.addImage(
                        bitmap,
                        0,
                        0,
                        bitmap.width,
                        bitmap.height,
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT.toDouble(),
                        Printer.COMPRESS_AUTO
                    )
                }
                is BitmapImage -> {
                    try {
                        printer?.addTextAlign(Printer.ALIGN_CENTER)
                        printer?.addImage(
                            it.bitmap,
                            0,
                            0,
                            it.bitmap.width,
                            it.bitmap.height,
                            Printer.COLOR_1,
                            Printer.MODE_MONO,
                            Printer.HALFTONE_DITHER,
                            Printer.PARAM_DEFAULT.toDouble(),
                            Printer.COMPRESS_AUTO
                        )
                    } catch (e: Exception) {
                        //
                    }
                }
                is Line -> {
                    printer?.addTextAlign(Printer.ALIGN_CENTER)
                    printer?.addText("-".repeat(printerSetting.charCount)+"\n")
                }
                is Barcode -> {
                    val bitmap = stringToBarcode(it.text, it.width)
                    printer?.addTextAlign(Printer.ALIGN_CENTER)
                    printer?.addImage(
                        bitmap,
                        0,
                        0,
                        bitmap.width,
                        bitmap.height,
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT.toDouble(),
                        Printer.COMPRESS_AUTO
                    )
                }
                is QRCode -> {
                    val bitmap = stringToQrCode(it.text, it.width, it.height)
                    printer?.addTextAlign(Printer.ALIGN_CENTER)
                    printer?.addImage(
                        bitmap,
                        0,
                        0,
                        bitmap.width,
                        bitmap.height,
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT.toDouble(),
                        Printer.COMPRESS_AUTO
                    )
                }
            }
        }
        printer?.addText("\n\n")
        printer?.addCut(Printer.CUT_FEED)
        printer?.sendData(Printer.PARAM_DEFAULT)
        printer?.clearCommandBuffer()
        printer?.endTransaction()
        return true

    }

    fun openDrawer(): Boolean {
        try {
//            printer?.addPulse(0, 0)

            printer?.addCommand(byteArrayOf(0x1b, 0x70, 0x00))
            printer?.addCommand(byteArrayOf(0x1d, 0x72, 0x02))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
}