package com.zenenta.printer.implementation.sunmi

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.zenenta.printer.repository.Setting
import com.zenenta.printer.util.*
import com.zqprintersdk.PrinterConst
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.zenenta.printer.R
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode
import woyou.aidlservice.jiuiv5.IWoyouService


object SunmiFunctions {
    private const val SERVICE_PACKAGE = "woyou.aidlservice.jiuiv5"
    private const val SERVICE_ACTION = "woyou.aidlservice.jiuiv5.IWoyouService"
    private var woyouService: IWoyouService? = null
    var statusPrinter = false

    fun initPrinter(){
        if (woyouService == null) {
            return
        }

        try {
            woyouService!!.printerInit(null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }

    fun connect(context: Context): String {
        val intent = Intent()
        intent.setPackage(SERVICE_PACKAGE)
        intent.action = SERVICE_ACTION
        context.applicationContext.startService(intent)
        val result = context.applicationContext.bindService(intent, connService, Context.BIND_AUTO_CREATE)

        return if (result){
            statusPrinter = true
            context.resources.getString(R.string.printer_connected)
        } else {
            statusPrinter = false
            context.resources.getString(R.string.printer_not_connected)
        }
    }

    fun disconnect(context: Context) {
        if (woyouService != null) {
            context.applicationContext.unbindService(connService)
            woyouService = null
        }
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, printerSetting: Setting): Boolean {
        try {
            if (printerSetting.charCount > 32){
                woyouService?.setFontSize(24F, null)
            } else {
                woyouService?.setFontSize(20F, null)
            }
            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        woyouService?.setAlignment(PrinterConst.Alignment.CENTER, null)
                        woyouService?.printText("\n".repeat(it.lineCount), null)
                    }
                    is TextCenter -> {
                        woyouService?.setAlignment(PrinterConst.Alignment.CENTER, null)
                        woyouService?.printText(it.text + "\n", null)
                    }
                    is TextLeft -> {
                        woyouService?.setAlignment(PrinterConst.Alignment.LEFT, null)
                        woyouService?.printText(it.text + "\n", null)
                    }
                    is TextRight -> {
                        woyouService?.setAlignment(PrinterConst.Alignment.RIGHT, null)
                        woyouService?.printText(it.text + "\n", null)
                    }
                    is TextLeftRight -> {
                        woyouService?.setAlignment(PrinterConst.Alignment.LEFT, null)
                        woyouService?.printText(PrinterUtil.formatLeftRight(it.left, it.right, printerSetting.charCount) + "\n", null)
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

                        woyouService?.setAlignment(PrinterConst.Alignment.CENTER, null)
                        woyouService?.printBitmap(bitmap, null)
                        woyouService?.printText("\n", null)
                    }

                    is BitmapImage -> {
                        try {
                            woyouService?.setAlignment(PrinterConst.Alignment.CENTER, null)
                            woyouService?.printBitmap(it.bitmap, null)
                            woyouService?.printText("\n", null)
                        } catch (e: Exception) {
                            //
                        }
                    }
                    is Line -> {
                        var line = ""
                        for (i in 0 until printerSetting.charCount) {
                            line += "-"
                        }
                        woyouService?.setAlignment(PrinterConst.Alignment.CENTER, null)
                        woyouService?.printText(line + "\n", null)
                    }
                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        woyouService?.setAlignment(PrinterConst.Alignment.CENTER, null)
                        woyouService?.printBitmap(bitmap, null)
                        woyouService?.printText("\n", null)
                    }
                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        woyouService?.setAlignment(PrinterConst.Alignment.CENTER, null)
                        woyouService?.printBitmap(bitmap, null)
                        woyouService?.printText("\n", null)
                    }
                }
            }

            woyouService?.lineWrap(3, null)
            woyouService?.cutPaper(null)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun openDrawer(){
        if (woyouService == null) {
            return
        }

        try {
            woyouService!!.openDrawer(null)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private val connService: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            woyouService = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            woyouService = IWoyouService.Stub.asInterface(service)
        }
    }
}