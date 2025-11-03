package com.zenenta.printer.implementation.ktouch

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.os.IBinder
import android.os.RemoteException
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.zenenta.printer.R
import com.zenenta.printer.util.*
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode
import com.prints.printerservice.IPrinterCallback
import com.prints.printerservice.IPrinterService


object KTouchFunctions {

    private lateinit var mCallback: IPrinterCallback
    private var mPrinterService: IPrinterService? = null
    var statusPrinter = false
    var sCurrentLength: Long = 0
    var sTotalLength: Long = 0

    private val mListener: PrinterManagerListener? = null

    private var mMapTextAlignCenter: Map<String, Int>? = null
    private var mMapTextAlignRight: Map<String, Int>? = null
    private var mMapTextAlignLeft: Map<String, Int>? = null


    private var mMapLogo: Map<String, Int>? = null
    private const val KEY_ALIGN = "key_attributes_align"
    private const val KEY_TEXTSIZE = "key_attributes_textsize"
    private const val KEY_TYPEFACE = "key_attributes_typeface"
    private const val KEY_MARGINLEFT = "key_attributes_marginleft"
    private const val KEY_MARGINRIGHT = "key_attributes_marginright"
    private const val KEY_MARGINTOP = "key_attributes_margintop"
    private const val KEY_MARGINBOTTOM = "key_attributes_marginbottom"
    private const val KEY_LINESPACE = "key_attributes_linespace"
    private const val KEY_WEIGHT = "key_attributes_weight"
    private const val ALIGN_CENTER = 0
    private const val ALIGN_LEFT = 1
    private const val ALIGN_RIGHT = 2

    interface PrinterManagerListener {
        fun onO2ServiceConnected()
    }

    fun printerInit() {
        try {
            mPrinterService?.printerInit(mCallback)
        } catch (e: java.lang.Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            //Log.i(TAG, "printerInit error=$e")
        }
    }


    fun onPrinterStart(context: Context) : String {
        mCallback = object : IPrinterCallback.Stub() {
            @Throws(RemoteException::class)
            override fun onException(code: Int, msg: String) {
                //Log.i(TAG, "onException code=$code msg=$msg")
            }

            @Throws(RemoteException::class)
            override fun onLength(current: Long, total: Long) {
                sCurrentLength = current
                sTotalLength = total
            }

            @Throws(RemoteException::class)
            override fun onComplete() {
                //Log.i(TAG, "onComplete")
            }
        }
        val intent = Intent()
        intent.setPackage("com.prints.printerservice")
        intent.action = "com.prints.printerservice.IPrinterService"
        context.startService(intent)
        context.bindService(intent, mConnectionService, Context.BIND_AUTO_CREATE)
        statusPrinter = true
        return context.getString(R.string.printer_connected)
    }

    fun onPrinterStop(context: Context){
        ///*
        try {
            context.unbindService(mConnectionService)
            statusPrinter = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val mConnectionService: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            //Log.i(TAG, "onServiceDisconnected")
            mPrinterService = null
            statusPrinter = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            //Log.i(TAG, "onServiceConnected")
            mPrinterService = IPrinterService.Stub.asInterface(service)
            mListener?.onO2ServiceConnected()
            statusPrinter = true
        }
    }


    private fun printTextWithAttributes(text: String?, attributes: Map<*, *>?) {
        try {
            mPrinterService?.printTextWithAttributes(text, attributes, mCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, charCount: Int) {
        mMapTextAlignCenter = HashMap()
        (mMapTextAlignCenter as HashMap<String, Int>)[KEY_LINESPACE] = 0
        (mMapTextAlignCenter as HashMap<String, Int>)[KEY_TEXTSIZE] = 24
        (mMapTextAlignCenter as HashMap<String, Int>)[KEY_TYPEFACE] = 8
        (mMapTextAlignCenter as HashMap<String, Int>)[KEY_ALIGN] = ALIGN_CENTER

        mMapTextAlignLeft = HashMap()
        (mMapTextAlignLeft as HashMap<String, Int>)[KEY_LINESPACE] = 0
        (mMapTextAlignLeft as HashMap<String, Int>)[KEY_TEXTSIZE] = 24
        (mMapTextAlignCenter as HashMap<String, Int>)[KEY_TYPEFACE] = 8
        (mMapTextAlignLeft as HashMap<String, Int>)[KEY_ALIGN] = ALIGN_LEFT


        mMapTextAlignRight = HashMap()
        (mMapTextAlignRight as HashMap<String, Int>)[KEY_LINESPACE] = 0
        (mMapTextAlignCenter as HashMap<String, Int>)[KEY_TYPEFACE] = 8
        (mMapTextAlignRight as HashMap<String, Int>)[KEY_ALIGN] = ALIGN_RIGHT


        mMapLogo = HashMap()
        (mMapLogo as HashMap<String, Int>)[KEY_ALIGN] = 0
        (mMapLogo as HashMap<String, Int>)[KEY_MARGINLEFT] = 5
        (mMapLogo as HashMap<String, Int>)[KEY_MARGINRIGHT] = 5

        if (mPrinterService == null) {
            //Toast.makeText(mContext, "Printer is not connected!", Toast.LENGTH_LONG).show()
            println("Printer is not connected!")

            return
        }

        try {
            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        printTextWithAttributes("\n".repeat(it.lineCount), mMapTextAlignLeft)
                    }
                    is TextCenter -> {
                        printTextWithAttributes( it.text, mMapTextAlignCenter)
                    }
                    is TextLeft -> {
                        printTextWithAttributes( it.text, mMapTextAlignLeft)
                    }
                    is TextRight -> {
                        printTextWithAttributes( it.text, mMapTextAlignRight)
                    }
                    is TextLeftRight -> {
                        printTextWithAttributes( PrinterUtil.formatLeftRight(it.left, it.right, charCount), mMapTextAlignLeft)
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

                        if (bitmap != null) {
                            val width: Int = bitmap.width
                            val height: Int = bitmap.height
                            val pageWidth = 384
                            var dstw = width
                            var dsth = height
                            if (dstw > pageWidth) {
                                dstw = pageWidth
                                dsth = (dstw * (height.toDouble() / width)).toInt()
                            }

                            printBitmap(bitmap, mMapLogo)
                        }
                    }
                    is BitmapImage -> {
                        try {
                            val width: Int = it.bitmap.width
                            val height: Int = it.bitmap.height
                            val pageWidth = 384
                            var dstw = width
                            var dsth = height
                            if (dstw > pageWidth) {
                                dstw = pageWidth
                                dsth = (dstw * (height.toDouble() / width)).toInt()
                            }

                            printBitmap(it.bitmap, mMapLogo)
                        } catch (e: Exception) {
                            //
                        }
                    }
                    is Line -> {
                        var line = ""
                        for (i in 0 until charCount) {
                            line += "-"
                        }
                        printTextWithAttributes( line, mMapTextAlignLeft)
                    }
                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        printBitmap(bitmap, mMapLogo)
                    }
                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        printBitmap(bitmap, mMapLogo)
                    }
                }
            }

            cutPaper()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun cutPaper() {
        sendRAWData(byteArrayOf(0x1d, 0x56, 0x42, 0x00))
    }


    private fun sendRAWData(data: ByteArray?) {
        try {
            mPrinterService!!.sendRAWData(data, mCallback)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    private fun printBitmap(bitmap: Bitmap, attributes: Map<String, Int>?) {
        try {
            mPrinterService?.printBitmapWithAttributes(bitmap, attributes, mCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openDrawer(): Boolean{
        if (mPrinterService == null) {
            return false
        }

        return try {
            sendRAWData(byteArrayOf(0x1b, 0x70, 0x00))
            sendRAWData(byteArrayOf(0x1d, 0x72, 0x02))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }
}