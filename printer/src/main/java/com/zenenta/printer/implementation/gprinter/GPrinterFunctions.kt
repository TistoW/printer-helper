package com.zenenta.printer.implementation.gprinter

import android.content.Context
import android.hardware.usb.UsbManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.gprinter.command.EscCommand
import com.gprinter.command.LabelCommand
import com.zenenta.printer.R
import com.zenenta.printer.libs.gprinterlibs.GPDeviceConnFactoryManager
import com.zenenta.printer.repository.DeviceInterface
import com.zenenta.printer.repository.Setting
import com.zenenta.printer.util.*
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode
import java.util.*
import kotlin.concurrent.thread


object GPrinterFunctions {
    var statusPrinter = false
    private var gPrinter: GPDeviceConnFactoryManager? = null
    var idPrinter = 0

    fun connect(context: Context, printerSetting: Setting): String{
        var connectMessage = ""
        when (printerSetting.deviceInterface) {
            DeviceInterface.BLUETOOTH.code -> {
                GPDeviceConnFactoryManager.Build()
                    .setId(0)
                    .setContext(context)
                    .setName(printerSetting.name)
                    .setConnMethod(GPDeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
                    .setMacAddress(printerSetting.address)
                    .build()
            }
            DeviceInterface.WIFI.code -> {
                GPDeviceConnFactoryManager.Build()
                    .setId(0)
                    .setContext(context)
                    .setName(printerSetting.name)
                    .setConnMethod(GPDeviceConnFactoryManager.CONN_METHOD.WIFI)
                    .setIp(printerSetting.address)
                    .setPort(9600)
                    .build()

            }
            else -> {
                val mUsbManger = context.getSystemService(Context.USB_SERVICE) as UsbManager
                val deviceList = mUsbManger.deviceList
                if (deviceList.isNotEmpty()){
                    val usbDevice = deviceList.values.elementAt(0)

                    GPDeviceConnFactoryManager.Build()
                        .setId(0)
                        .setContext(context)
                        .setName(printerSetting.name)
                        .setConnMethod(GPDeviceConnFactoryManager.CONN_METHOD.USB)
                        .setUsbDevice(usbDevice)
                        .setPort(0)
                        .build()
                }
            }
        }

        try {
            statusPrinter = if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers().isNotEmpty() &&  GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] != null) {
                thread {
                    GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].openPort()
                    //gPrinter?.openPort()
                }
//                gPrinter?.openPort()
                connectMessage = context.getString(R.string.printer_connected)
                true
            } else {
                connectMessage = context.getString(R.string.printer_not_connected)
                false
            }
        } catch (e: Exception) {
            connectMessage = e.message!!
            statusPrinter = false
        }
        return  connectMessage
    }

    fun disconnect() {
        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers().isNotEmpty()) {
            GPDeviceConnFactoryManager.closeAllPort()
            statusPrinter = false
        }
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, printerSetting: Setting): Boolean {
        for (i in GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()){
            println("LIST GPDEVICE: $i")
        }
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null ||
//            gPrinter?.connState == false
//        ) { return false }

        try {
            val esc = EscCommand()
            esc.addInitializePrinter()
            /*esc.addSelectPrintModes(
                EscCommand.FONT.FONTB,
                EscCommand.ENABLE.OFF,
                EscCommand.ENABLE.OFF,
                EscCommand.ENABLE.OFF,
                EscCommand.ENABLE.OFF
            )*/
            esc.addSelectCharacterFont(EscCommand.FONT.FONTA)
            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        esc.addText("\n".repeat(it.lineCount))
                    }
                    is TextCenter -> {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER)
                        esc.addSetCharcterSize(EscCommand.WIDTH_ZOOM.MUL_1, EscCommand.HEIGHT_ZOOM.MUL_1)
                        esc.addText(it.text + "\n")
                    }
                    is TextLeft -> {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT)
                        esc.addSetCharcterSize(EscCommand.WIDTH_ZOOM.MUL_1, EscCommand.HEIGHT_ZOOM.MUL_1)
                        esc.addText(it.text + "\n")
                    }
                    is TextRight -> {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT)
                        esc.addSetCharcterSize(EscCommand.WIDTH_ZOOM.MUL_1, EscCommand.HEIGHT_ZOOM.MUL_1)
                        esc.addText(it.text + "\n")
                    }
                    is TextLeftRight -> {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT)
                        esc.addSetCharcterSize(EscCommand.WIDTH_ZOOM.MUL_1, EscCommand.HEIGHT_ZOOM.MUL_1)
                        esc.addText(PrinterUtil.formatLeftRight(it.left, it.right, printerSetting.charCount) + "\n")
                    }
                    is TextCustomSize -> {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER)
                        esc.addSetCharcterSize(EscCommand.WIDTH_ZOOM.MUL_2, EscCommand.HEIGHT_ZOOM.MUL_2)
                        esc.addText(it.text + "\n")

                    }
                    is Image -> {
                        val bitmap = Glide.with(context)
                            .asBitmap()
                            .load(it.url)
                            .apply(RequestOptions().override(it.width, it.height).downsample(DownsampleStrategy.CENTER_INSIDE))
                            .submit(it.width, it.height)
                            .get()

                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER)
                        esc.addRastBitImage(bitmap, it.width, 0)
                        esc.addPrintAndFeedLines(1.toByte())
                    }
                    is BitmapImage -> {
                        try {
                            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER)
                            esc.addRastBitImage(it.bitmap, it.bitmap.width, 0)
                            esc.addPrintAndFeedLines(1.toByte())
                        } catch (e: Exception) {
                            //
                        }
                    }
                    is Line -> {
                        var line = ""
                        for (i in 0 until printerSetting.charCount) {
                            line += "-"
                        }
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT)
                        esc.addSetCharcterSize(EscCommand.WIDTH_ZOOM.MUL_1, EscCommand.HEIGHT_ZOOM.MUL_1)
                        esc.addText(line + "\n")
                    }
                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER)
                        esc.addRastBitImage(bitmap, it.width, 0)
                        esc.addPrintAndFeedLines(1.toByte())
                    }
                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER)
                        esc.addRastBitImage(bitmap, it.width, 0)
                        esc.addPrintAndFeedLines(1.toByte())
                    }
                }
            }
            esc.addText("\n".repeat(2))
            val datas: Vector<Byte> = esc.command

            return GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas)
//            return gPrinter?.sendDataImmediately(datas)!!

        } catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

    fun openDrawer(): Boolean {
        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null ||
            !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].connState
        ) { return false }

        val esc = EscCommand()
        esc.addInitializePrinter()

        esc.addGeneratePlus(LabelCommand.FOOT.F5, 255.toByte(), 255.toByte())
        esc.addGeneratePlus(LabelCommand.FOOT.F2, 255.toByte(), 255.toByte())

        val datas = esc.command
        return GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas)
    }

}