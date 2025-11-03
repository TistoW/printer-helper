package com.zenenta.printer.implementation.imin

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.imin.library.IminSDKManager
import com.imin.printerlib.IminPrintUtils
import com.zenenta.printer.R
import com.zenenta.printer.libs.gprinterlibs.GPUtils.toast
import com.zenenta.printer.repository.DeviceInterface
import com.zenenta.printer.repository.Setting
import com.zenenta.printer.util.*
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode
import com.pavolibrary.commands.CashAPI
import com.pavolibrary.commands.CashAPI.CASH_OPEN_PIN2


object IminFunctions {
    var statusPrinter = false
    @SuppressLint("StaticFieldLeak")
    private var mIminPrintUtils: IminPrintUtils? = null
    private var mBluetoothStateReceiver: BluetoothStateReceiver? = null

    private fun initPrinter(context: Context) {
        try {
            mBluetoothStateReceiver = BluetoothStateReceiver()
            val filter = IntentFilter()
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            context.registerReceiver(mBluetoothStateReceiver, filter)
            mIminPrintUtils = IminPrintUtils.getInstance(context)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun connect(context: Context, printerSetting: Setting): String{
        try {
            return when (printerSetting.deviceInterface){
                DeviceInterface.BLUETOOTH.code ->{
                    initPrinter(context)
                    val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
                    val mBluetoothDevice: BluetoothDevice = bluetoothManager.adapter.getRemoteDevice(printerSetting.address)
                    mIminPrintUtils?.resetDevice()
                    mIminPrintUtils?.initPrinter(IminPrintUtils.PrintConnectType.BLUETOOTH, mBluetoothDevice)
                    val status = mIminPrintUtils?.getPrinterStatus(IminPrintUtils.PrintConnectType.BLUETOOTH)
                    if (mIminPrintUtils == null){
                        statusPrinter = false
                        context.getString(R.string.printer_not_connected)
                    } else {
                        statusPrinter = true
                        context.getString(R.string.printer_connected)
                    }
                }
                else -> {
                    //initPrinter(context)
                    mIminPrintUtils = IminPrintUtils.getInstance(context)
                    mIminPrintUtils?.resetDevice()
                    mIminPrintUtils?.initPrinter(IminPrintUtils.PrintConnectType.USB)

                    if (mIminPrintUtils == null){
                        statusPrinter = false
                        context.getString(R.string.printer_not_connected)
                    } else {
                        statusPrinter = true
                        context.getString(R.string.printer_connected)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            statusPrinter = false
            return e.message.toString()
        }

    }

    fun disconnect(context: Context) {
        try {
            mIminPrintUtils?.release()
            context.unregisterReceiver(mBluetoothStateReceiver)
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, printerSetting: Setting): Boolean {
        if (mIminPrintUtils == null) {
            return false
        }

        if (printerSetting.charCount <= 32){
            mIminPrintUtils?.textSize = 22
            mIminPrintUtils?.setPageFormat(1)
        } else {
            mIminPrintUtils?.setPageFormat(0)
        }

        try {
            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        mIminPrintUtils?.setAlignment(1)
                        mIminPrintUtils?.printText("\n".repeat(it.lineCount))
                    }
                    is TextCenter -> {
                        mIminPrintUtils?.setAlignment(1)
                        mIminPrintUtils?.printText(it.text + "\n")
                    }
                    is TextLeft -> {
                        mIminPrintUtils?.setAlignment(0)
                        mIminPrintUtils?.printText(it.text + "\n")
                    }
                    is TextRight -> {
                        mIminPrintUtils?.setAlignment(2)
                        mIminPrintUtils?.printText(it.text + "\n")
                    }
                    is TextLeftRight -> {
                        mIminPrintUtils?.setAlignment(0)
                        mIminPrintUtils?.printText(PrinterUtil.formatLeftRight(it.left, it.right, printerSetting.charCount) + "\n")
                    }
                    is TextCustomSize -> {
                        mIminPrintUtils?.setAlignment(1)
                        mIminPrintUtils?.printText(it.text + "\n")
                    }
                    is Image -> {
                        val bitmap = Glide.with(context)
                            .asBitmap()
                            .load(it.url)
                            .apply(RequestOptions().override(it.width, it.height).downsample(DownsampleStrategy.CENTER_INSIDE))
                            .submit(it.width, it.height)
                            .get()

                        mIminPrintUtils?.printSingleBitmap(bitmap, 1)
                    }

                    is BitmapImage -> {
                        try {
                            mIminPrintUtils?.printSingleBitmap(it.bitmap, 1)
                        } catch (e: Exception) {
                            //
                        }
                    }
                    is Line -> {
                        var line = ""
                        for (i in 0 until printerSetting.charCount) {
                            line += "-"
                        }
                        mIminPrintUtils?.setAlignment(1)
                        mIminPrintUtils?.printText(line + "\n")
                    }
                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        mIminPrintUtils?.printSingleBitmap(bitmap, 1)
                    }
                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        mIminPrintUtils?.printSingleBitmap(bitmap, 1)
                    }
                }
            }

            mIminPrintUtils?.printAndFeedPaper(100)
            mIminPrintUtils?.partialCut()

            return true
        } catch (e: Exception){
            return false
        }
    }

    fun openDrawer(context: Context, printerSetting: Setting){
        try {
            if (printerSetting.modelId == 3L) {
                val mDrawer = CashAPI(context)
                mDrawer.connect("USB", 9600, 0)
                if (mDrawer.isConnect) {
                    mDrawer.setup(CASH_OPEN_PIN2, 100, 100)
                    mDrawer.openDrawer()

                    //disconnect drawer
                    mDrawer.disconnect()
                }
            } else {
                IminSDKManager.opencashBox()
            }
        } catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }


    internal class BluetoothStateReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
            when (state) {
                BluetoothAdapter.STATE_TURNING_ON -> toast(context, "Bluetooth ON")
                BluetoothAdapter.STATE_TURNING_OFF -> toast(context, "Bluetooth OFF")
            }
        }
    }
}