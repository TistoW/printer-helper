package com.olserapratama.printer.implementation.xcheng

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.bumptech.glide.Glide
import com.olserapratama.printer.R
import com.olserapratama.printer.util.*
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.util.PrinterUtil.stringToBarcode
import com.olserapratama.printer.util.PrinterUtil.stringToQrCode
import com.xcheng.printerservice.IPrinterCallback
import com.xcheng.printerservice.IPrinterService
import timber.log.Timber

object XchengFunctions {
    private const val ALIGN_CENTER = 0
    private const val ALIGN_LEFT = 1
    private const val ALIGN_RIGHT = 2
    private const val TAG = "PrinterXcheng"
    private const val KEY_ALIGN = "key_attributes_align"

    private val style = hashMapOf(KEY_ALIGN to ALIGN_LEFT)
    private var commandCallback: IPrinterCallback? = null
    private var printerService: IPrinterService? = null
    private var connectionService: ServiceConnection? = null
    var statusPrinter = false

    fun connect(context: Context): String{
        commandCallback = object : IPrinterCallback.Stub() {
            @Throws(RemoteException::class)
            override fun onException(code: Int, msg: String) {
                Timber.d("$TAG onException code=$code msg=$msg")
            }

            @Throws(RemoteException::class)
            override fun onLength(current: Long, total: Long) {
            }

            @Throws(RemoteException::class)
            override fun onComplete() {
                Timber.d("$TAG onComplete")
            }
        }

        connectionService = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                printerService = IPrinterService.Stub.asInterface(service)
                printerService!!.printerInit(commandCallback)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                printerService = null
            }

        }
        val intent = Intent()
        intent.setPackage("com.xcheng.printerservice")
        intent.action = "com.xcheng.printerservice.IPrinterService"
        context.startService(intent)
        statusPrinter = context.bindService(intent, connectionService!!, Context.BIND_AUTO_CREATE)

        return if (statusPrinter){
            context.getString(R.string.printer_connected)
        } else {
            context.getString(R.string.printer_not_connected)
        }
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, charCount: Int) {
        lines.forEach {
            when (it) {
                is EmptyLine -> {
                    val text = "\n".repeat(it.lineCount)
                    printerService!!.printText(text, commandCallback)
                }
                is TextCenter -> {
                    style[KEY_ALIGN] = ALIGN_CENTER
                    printerService!!.printTextWithAttributes(it.text, style, commandCallback)

                }
                is TextLeft -> {
                    style[KEY_ALIGN] = ALIGN_LEFT
                    printerService!!.printTextWithAttributes(it.text, style, commandCallback)
                }
                is TextRight -> {
                    style[KEY_ALIGN] = ALIGN_RIGHT
                    printerService!!.printTextWithAttributes(it.text, style, commandCallback)
                }
                is TextLeftRight -> {
                    style[KEY_ALIGN] = ALIGN_LEFT
                    printerService!!.printTextWithAttributes(
                        PrinterUtil.formatLeftRight(
                            it.left,
                            it.right,
                            charCount
                        ), style, commandCallback
                    )
                }
                is Image -> {
                    val bitmap = Glide.with(context)
                        .asBitmap()
                        .load(it.url)
                        .submit(it.width, it.height)
                        .get()

                    style[KEY_ALIGN] = ALIGN_CENTER
                    printerService!!.printBitmapWithAttributes(
                        bitmap,
                        style,
                        commandCallback
                    )
                }
                is Line -> {
                    style[KEY_ALIGN] = ALIGN_CENTER
                    printerService!!.printTextWithAttributes(
                        "-".repeat(charCount),
                        style,
                        commandCallback
                    )
                }
                is Barcode -> {
                    val bitmap = stringToBarcode(it.text, it.width)
                    style[KEY_ALIGN] = ALIGN_CENTER
                    printerService!!.printBitmapWithAttributes(bitmap, style, commandCallback)
                }
                is QRCode -> {
                    val bitmap = stringToQrCode(it.text, it.width, it.height)
                    style[KEY_ALIGN] = ALIGN_CENTER
                    printerService!!.printBitmapWithAttributes(bitmap, style, commandCallback)
                }
                else -> {}
            }
        }
        printerService!!.printText("\n\n", commandCallback)
    }

    fun disconnect(context: Context) {
        try {
            context.unbindService(connectionService!!)
        } catch (e: Exception) {
            //e.debugPrintStackTrace()
        }
    }
}