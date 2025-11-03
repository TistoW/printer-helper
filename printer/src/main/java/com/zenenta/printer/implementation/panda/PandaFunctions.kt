package com.zenenta.printer.implementation.panda

import android.content.Context
import com.bumptech.glide.Glide
import com.zenenta.printer.util.*
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.zenenta.printer.libs.printerlibs.printerlibs_caysnpos.printerlibs_caysnpos
import com.zenenta.printer.repository.DeviceInterface
import com.sun.jna.Pointer
import com.zenenta.printer.repository.Setting
import com.epson.epos2.printer.Printer
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode

object PandaFunctions {
    private var printer: Printer? = null
    var statusPrinter = false
    private var h = Pointer.NULL

    fun connect(context: Context, printerSetting: Setting): String {
        when (printerSetting.deviceInterface) {
            DeviceInterface.BLUETOOTH.code -> {
                return try {
                    h = printerlibs_caysnpos.INSTANCE.CaysnPos_OpenBT2ByConnectA(printerSetting.address)
                    statusPrinter = true
                    "Printer is connected!"
                } catch (e: Exception) {
                    statusPrinter = false
                    e.message
                    e.message.toString()
                }
            }
            DeviceInterface.WIFI.code -> {
                /*return try {
                    printer = printerSetting.modelId?.toInt()?.let { Printer(it, 0, context) }
                    printer!!.setConnectionEventListener { any, i -> Timber.d("AOEU connection $any $i") }
                    printer!!.setReceiveEventListener { printer, i, printerStatusInfo, s -> Timber.d("AOEU event $printer $i $printerStatusInfo $s") }
                    printer!!.setStatusChangeEventListener { printer, i -> Timber.d("AOEU status $printer $i") }

                    var address = printerSetting.deviceInterface + printerSetting.address
                    printer!!.connect(address, Printer.PARAM_DEFAULT)
                    statusPrinter = true
                    "Printer is connected!"
                } catch (e: Exception) {
                    printer = null
                    statusPrinter = false
                    e.message.toString()
                }*/
                return "Printer is not connected!"
            }
            else -> {
                return try {
                    val devicePaths = printerlibs_caysnpos.CaysnPos_EnumUsbVidPid_Helper.CaysnPos_EnumUsbVidPidA()
                    if (devicePaths.isNotEmpty()){
                        printerSetting.address = devicePaths[0]
                    }
                    h = printerlibs_caysnpos.INSTANCE.CaysnPos_OpenUsbVidPidStringA(printerSetting.address)
                    statusPrinter = true
                    "Printer is connected!"
                } catch (ex: Exception) {
                    statusPrinter = false
                    ex.message.toString()
                }
            }
        }
    }

    fun disconnect(printerSetting: Setting) {
        if (printerSetting.deviceInterface == DeviceInterface.BLUETOOTH.code || printerSetting.deviceInterface == DeviceInterface.USB.code){
            if (h !== Pointer.NULL) {
                printerlibs_caysnpos.INSTANCE.CaysnPos_Close(h)
                h = Pointer.NULL
            }
            statusPrinter = false
        }
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, charCount: Int): Boolean {
        try {
            val result: Int = printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
            if (result == 0) {
                statusPrinter = false
                return false
            }

            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        printerlibs_caysnpos.INSTANCE.CaysnPos_PrintTextA(h, "\n".repeat(it.lineCount))
                        printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                    }
                    is TextCenter -> {
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, printerlibs_caysnpos.PosAlignment_HCenter)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetTextScale(h, 0, 0)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_PrintTextA(h, it.text + "\n")
                        printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                    }
                    is TextLeft -> {
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, printerlibs_caysnpos.PosAlignment_Left)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetTextScale(h, 0, 0)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_PrintTextA(h, it.text + "\n")
                        printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                    }
                    is TextRight -> {
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, printerlibs_caysnpos.PosAlignment_Right)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetTextScale(h, 0, 0)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_PrintTextA(h, it.text + "\n")
                        printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                    }
                    is TextLeftRight -> {
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, 0)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetTextScale(h, 0, 0)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_PrintTextA(h, PrinterUtil.formatLeftRight(it.left, it.right, charCount) + "\n")
                        printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                    }
                    is TextCustomSize -> {
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, printerlibs_caysnpos.PosAlignment_HCenter)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetTextScale(h, 1, 1)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_PrintTextA(h, it.text + "\n")
                        printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                    }
                    is Image -> {
                        val bitmap = Glide.with(context)
                            .asBitmap()
                            .load(it.url)
                            .apply(RequestOptions().override(it.width, it.height).downsample(DownsampleStrategy.CENTER_INSIDE))
                            .submit(it.width, it.height)
                            .get()

                        if (bitmap != null) {
                            val width: Int = bitmap.width
                            val height: Int = bitmap.height
                            val page_width = 384
                            var dstw = width
                            var dsth = height
                            if (dstw > page_width) {
                                dstw = page_width
                                dsth = (dstw * (height.toDouble() / width)).toInt()
                            }
                            printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, printerlibs_caysnpos.PosAlignment_HCenter)
                            printerlibs_caysnpos.CaysnPos_PrintRasterImage_Helper.CaysnPos_PrintRasterImageFromBitmap(h, dstw, dsth, bitmap, 0)
                            printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                        }
                    }
                    is BitmapImage -> {
                        try {
                            val width: Int = it.bitmap.width
                            val height: Int = it.bitmap.height
                            val page_width = 384
                            var dstw = width
                            var dsth = height
                            if (dstw > page_width) {
                                dstw = page_width
                                dsth = (dstw * (height.toDouble() / width)).toInt()
                            }
                            printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, printerlibs_caysnpos.PosAlignment_HCenter)
                            printerlibs_caysnpos.CaysnPos_PrintRasterImage_Helper.CaysnPos_PrintRasterImageFromBitmap(h, dstw, dsth, it.bitmap, 0)
                            printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                        } catch (e: Exception) {
                            //
                        }
                    }
                    is Line -> {
                        var line = ""
                        for (i in 0 until charCount) {
                            line += "-"
                        }
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, printerlibs_caysnpos.PosAlignment_Left)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetTextScale(h, 0, 0)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_PrintTextA(h, line + "\n")
                        printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                    }
                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, printerlibs_caysnpos.PosAlignment_HCenter)
                        printerlibs_caysnpos.CaysnPos_PrintRasterImage_Helper.CaysnPos_PrintRasterImageFromBitmap(h, bitmap.width, bitmap.height, bitmap, 0)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                    }
                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_SetAlignment(h, printerlibs_caysnpos.PosAlignment_HCenter)
                        printerlibs_caysnpos.CaysnPos_PrintRasterImage_Helper.CaysnPos_PrintRasterImageFromBitmap(h, bitmap.width, it.height, bitmap, 0)
                        printerlibs_caysnpos.INSTANCE.CaysnPos_ResetPrinter(h)
                    }
                }
            }
            printerlibs_caysnpos.INSTANCE.CaysnPos_FeedLine(h, 1)
            printerlibs_caysnpos.INSTANCE.CaysnPos_FullCutPaper(h)

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun openDrawer(): Boolean{
        try {
            val result = printerlibs_caysnpos.INSTANCE.CaysnPos_KickOutDrawer(h, 0, 100, 100)
            if (result == 0) {
                return false
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}