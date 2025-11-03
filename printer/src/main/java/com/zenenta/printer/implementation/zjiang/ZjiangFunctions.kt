@file:Suppress("DEPRECATION")

package com.zenenta.printer.implementation.zjiang

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.graphics.Bitmap
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Handler
import android.os.Message
import com.zenenta.printer.R
import com.zenenta.printer.libs.gprinterlibs.GPUtils
import com.zenenta.printer.libs.zjianglibs.BluetoothService
import com.zenenta.printer.libs.zjianglibs.Command
import com.zenenta.printer.libs.zjianglibs.PrintPicture
import com.zenenta.printer.libs.zjianglibs.PrinterCommand.POS_Print_Text
import com.zenenta.printer.libs.zjianglibs.PrinterCommand.POS_Set_Cashbox
import com.zenenta.printer.libs.zjianglibs.PrinterCommand.POS_Set_Cut
import com.zenenta.printer.libs.zjianglibs.PrinterCommand.POS_Set_PrtInit
import com.zenenta.printer.repository.DeviceInterface
import com.zenenta.printer.repository.Setting
import com.zenenta.printer.util.*
import com.zenenta.printer.util.PrinterUtil.bitmapToByteArray
import com.zenenta.printer.util.PrinterUtil.getLogoBitmapForReceipt
import com.zenenta.printer.util.PrinterUtil.stringToBarcode
import com.zenenta.printer.util.PrinterUtil.stringToQrCode
import com.zj.usbsdk.UsbController


object ZjiangFunctions {
    private var zjUsbService: UsbController? = null
    var statusPrinter = false

    @SuppressLint("StaticFieldLeak")
    private var zjBluetoothService: BluetoothService? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null

    fun connect(context: Context, printerSetting: Setting): String {
        var message = ""
        when (printerSetting.deviceInterface) {
            DeviceInterface.BLUETOOTH.code -> {
                zjBluetoothService = BluetoothService(context)
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                zjBluetoothService?.stop()
                zjBluetoothService?.start()
                if (BluetoothAdapter.checkBluetoothAddress(printerSetting.address)) {
                    val device: BluetoothDevice =
                        mBluetoothAdapter!!.getRemoteDevice(printerSetting.address)
                    zjBluetoothService?.connect(device)
                }
                if (zjBluetoothService != null) {
                    if (zjBluetoothService!!.state > 1) {
                        statusPrinter = true
                        message = context.resources.getString(R.string.printer_connected)
                    } else {
                        statusPrinter = false
                        message = context.resources.getString(R.string.printer_not_connected)
                    }
                }
            }

            DeviceInterface.WIFI.code -> {


            }

            else -> {
                try {
                    val activity = context as Activity
                    zjUsbService = UsbController(activity, mZJiangUSBHandler)

                    zjUsbService?.close()
                    val mUsbManger = context.getSystemService(Context.USB_SERVICE) as UsbManager
                    val deviceList = mUsbManger.deviceList

                    val usbDevice: UsbDevice =
                        GPUtils.getUsbDeviceFromName(context, deviceList.keys.elementAt(0))
                    if (zjUsbService?.isHasPermission(usbDevice) == true) {
                        zjUsbService?.getPermission(usbDevice)
                        statusPrinter = true
                        message = context.resources.getString(R.string.printer_connected)
                    } else {
                        statusPrinter = false
                        message = context.resources.getString(R.string.printer_not_connected)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return message
    }

    fun disconnect(printerSetting: Setting) {
        when (printerSetting.deviceInterface) {
            DeviceInterface.BLUETOOTH.code -> {
                statusPrinter = false
                zjBluetoothService?.stop()
            }

            DeviceInterface.WIFI.code -> {

            }

            else -> {
                statusPrinter = false
                zjUsbService?.close()
            }
        }
    }

    fun printReceiptByBluetooth(
        context: Context,
        lines: List<PrintLine>,
        printerSetting: Setting
    ): Boolean {
        try {
            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        Command.ESC_Align[2] = 0x00
                        zjBluetoothService!!.write(Command.ESC_Align)
                        val text = "\r\n".repeat(it.lineCount)
                        zjBluetoothService!!.write(POS_Print_Text(text, "GBK", 0, 0, 0, 0))
                    }

                    is TextCenter -> {
                        Command.ESC_Align[2] = 0x01
                        zjBluetoothService!!.write(Command.ESC_Align)
                        val text = it.text + "\r\n"
                        zjBluetoothService!!.write(POS_Print_Text(text, "GBK", 0, 0, 0, 0))
                    }

                    is TextLeft -> {
                        Command.ESC_Align[2] = 0x00
                        zjBluetoothService!!.write(Command.ESC_Align)
                        val text = it.text + "\r\n"
                        zjBluetoothService!!.write(POS_Print_Text(text, "GBK", 0, 0, 0, 0))
                    }

                    is TextRight -> {
                        Command.ESC_Align[2] = 0x02
                        zjBluetoothService!!.write(Command.ESC_Align)
                        val text = it.text + "\r\n"
                        zjBluetoothService!!.write(POS_Print_Text(text, "GBK", 0, 0, 0, 0))
                    }

                    is TextLeftRight -> {
                        Command.ESC_Align[2] = 0x00
                        zjBluetoothService!!.write(Command.ESC_Align)
                        val text = PrinterUtil.formatLeftRight(
                            it.left,
                            it.right,
                            printerSetting.charCount
                        ) + "\r\n"
                        zjBluetoothService!!.write(POS_Print_Text(text, "GBK", 0, 0, 0, 0))
                    }

                    is TextCustomSize -> {

                    }
                    is Image -> {
                        val bitmap: Bitmap =
                            getLogoBitmapForReceipt(context, it.url, it.width, it.height, 380.0)

                        val data: ByteArray? = PrintPicture.POS_PrintBMP(bitmap, it.width, 0)
                        Command.ESC_Align[2] = 0x01
                        zjBluetoothService!!.write(Command.ESC_Align)
                        zjBluetoothService!!.write(data)
                    }

                    is BitmapImage -> {
                        try {
                            val data: ByteArray = bitmapToByteArray(it.bitmap)
                            Command.ESC_Align[2] = 0x01
                            zjBluetoothService!!.write(Command.ESC_Align)
                            zjBluetoothService!!.write(data)
                        } catch (e: Exception) {
                            //
                        }
                    }

                    is Line -> {
                        var line = ""
                        for (i in 0 until printerSetting.charCount) {
                            line += "-"
                        }
                        Command.ESC_Align[2] = 0x00
                        zjBluetoothService!!.write(Command.ESC_Align)
                        zjBluetoothService!!.write(POS_Print_Text(line, "GBK", 0, 0, 0, 0))
                    }

                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        val data: ByteArray? = PrintPicture.POS_PrintBMP(bitmap, it.width, 0)
                        Command.ESC_Align[2] = 0x01
                        zjBluetoothService!!.write(Command.ESC_Align)
                        zjBluetoothService!!.write(data)
                    }

                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        val data: ByteArray? = PrintPicture.POS_PrintBMP(bitmap, it.width, 0)
                        Command.ESC_Align[2] = 0x01
                        zjBluetoothService!!.write(Command.ESC_Align)
                        zjBluetoothService!!.write(data)
                    }
                }
            }
            zjBluetoothService!!.write(POS_Set_Cut(1))
            zjBluetoothService!!.write(POS_Set_PrtInit())
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun printReceiptByUsb(
        context: Context,
        lines: List<PrintLine>,
        printerSetting: Setting
    ): Boolean {
        try {
            val mUsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
            val deviceList = mUsbManager.deviceList
            val dev: UsbDevice = GPUtils.getUsbDeviceFromName(context, deviceList.keys.elementAt(0))
            //val dev: UsbDevice? = getDevice(zjUsbService!!)
            lines.forEach {
                when (it) {
                    is EmptyLine -> {
                        Command.ESC_Align[2] = 0x00
                        zjUsbService!!.sendByte(Command.ESC_Align, dev)
                        val text = "\r\n".repeat(it.lineCount)
                        zjUsbService!!.sendByte(POS_Print_Text(text, "GBK", 0, 0, 0, 0), dev)
                    }

                    is TextCenter -> {
                        Command.ESC_Align[2] = 0x01
                        zjUsbService!!.sendByte(Command.ESC_Align, dev)
                        val text = it.text + "\r\n"
                        zjUsbService!!.sendByte(POS_Print_Text(text, "GBK", 0, 0, 0, 0), dev)
                    }

                    is TextLeft -> {
                        Command.ESC_Align[2] = 0x00
                        zjUsbService!!.sendByte(Command.ESC_Align, dev)
                        val text = it.text + "\r\n"
                        zjUsbService!!.sendByte(POS_Print_Text(text, "GBK", 0, 0, 0, 0), dev)
                    }

                    is TextRight -> {
                        Command.ESC_Align[2] = 0x02
                        zjUsbService!!.sendByte(Command.ESC_Align, dev)
                        val text = it.text + "\r\n"
                        zjUsbService!!.sendByte(POS_Print_Text(text, "GBK", 0, 0, 0, 0), dev)
                    }

                    is TextLeftRight -> {
                        Command.ESC_Align[2] = 0x00
                        zjUsbService!!.sendByte(Command.ESC_Align, dev)
                        val text = PrinterUtil.formatLeftRight(
                            it.left,
                            it.right,
                            printerSetting.charCount
                        ) + "\r\n"
                        zjUsbService!!.sendByte(POS_Print_Text(text, "GBK", 0, 0, 0, 0), dev)
                    }

                    is TextCustomSize -> {

                    }
                    is Image -> {
                        val bitmap: Bitmap =
                            getLogoBitmapForReceipt(context, it.url, it.width, it.height, 380.0)

                        val command: ByteArray = bitmapToByteArray(bitmap)
                        Command.ESC_Align[2] = 0x01
                        zjUsbService!!.sendByte(Command.ESC_Align, dev)
                        zjUsbService!!.sendByte(command, dev)
                    }

                    is BitmapImage -> {
                        try {
                            val data: ByteArray = bitmapToByteArray(it.bitmap)
                            Command.ESC_Align[2] = 0x01
                            zjBluetoothService?.write(Command.ESC_Align)
                            zjBluetoothService?.write(data)
                        } catch (e: Exception) {
                            //
                        }
                    }

                    is Line -> {
                        var line = ""
                        for (i in 0 until printerSetting.charCount) {
                            line += "-"
                        }
                        line += "\r\n"
                        Command.ESC_Align[2] = 0x01
                        zjUsbService!!.sendByte(Command.ESC_Align, dev)
                        zjUsbService!!.sendByte(POS_Print_Text(line, "GBK", 0, 0, 0, 0), dev)
                    }

                    is Barcode -> {
                        val bitmap = stringToBarcode(it.text, it.width)
                        val command: ByteArray = bitmapToByteArray(bitmap)
                        Command.ESC_Align[2] = 0x01
                        zjUsbService!!.sendByte(Command.ESC_Align, dev)
                        zjUsbService!!.sendByte(command, dev)

                    }

                    is QRCode -> {
                        val bitmap = stringToQrCode(it.text, it.width, it.height)
                        val command: ByteArray = bitmapToByteArray(bitmap)
                        Command.ESC_Align[2] = 0x01
                        zjUsbService!!.sendByte(Command.ESC_Align, dev)
                        zjUsbService!!.sendByte(command, dev)
                    }
                }
            }
            zjUsbService!!.sendByte(POS_Print_Text("\r\n\r\n", "GBK", 0, 0, 0, 0), dev)
            zjUsbService!!.sendByte(POS_Set_Cut(1), dev)
            zjUsbService!!.sendByte(POS_Set_PrtInit(), dev)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun openDrawerByBluetooth(): Boolean {
        return try {
            if (zjBluetoothService!!.state != BluetoothService.STATE_CONNECTED) {
                return false
            }
            zjBluetoothService!!.write(POS_Set_Cashbox(1, 200, 200))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun openDrawerByUsb(context: Context): Boolean {
        return try {
            val mUsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
            val deviceList = mUsbManager.deviceList
            val dev: UsbDevice = GPUtils.getUsbDeviceFromName(context, deviceList.keys.elementAt(0))
            if (!zjUsbService!!.isHasPermission(dev)) {
                zjUsbService!!.getPermission(dev)
            }
            zjUsbService!!.openCashBox(dev)
            true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }

    private val mZJiangUSBHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                UsbController.USB_CONNECTED -> println("Printer connected: $msg.")
            }
        }
    }

}