package com.zenenta.printer.implementation.kassen.label

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.zenenta.printer.R
import com.zenenta.printer.libs.gprinterlibs.GPUtils.toast
import com.zenenta.printer.repository.Setting
import com.zenenta.printer.util.*
import com.zenenta.printer.util.PrinterUtil.formatAlignRight
import com.zenenta.printer.util.PrinterUtil.formatLeftRight
import com.printer.psdk.device.adapter.ConnectedDevice
import com.printer.psdk.device.adapter.ReadOptions
import com.printer.psdk.device.bluetooth.classic.ClassicBluetooth
import com.printer.psdk.device.bluetooth.classic.ConnectListener
import com.printer.psdk.device.bluetooth.classic.Connection
import com.printer.psdk.frame.father.PSDK
import com.printer.psdk.tspl.GenericTSPL
import com.printer.psdk.tspl.TSPL
import com.printer.psdk.tspl.args.TBarCode
import com.printer.psdk.tspl.args.TDirection
import com.printer.psdk.tspl.args.TLine
import com.printer.psdk.tspl.args.TPage
import com.printer.psdk.tspl.args.TText
import com.printer.psdk.tspl.mark.CodeType
import com.printer.psdk.tspl.mark.Font
import com.printer.psdk.tspl.mark.ShowType
import java.io.IOException


object KassenLabelFunctions {
    private var printUtils: GenericTSPL? = null
    private var mBluetoothStateReceiver: BluetoothStateReceiver? = null
    private var connection: Connection? = null
    var statusPrinter = false

    fun connect(context: Context, printerSetting: Setting): String{
        try {
            mBluetoothStateReceiver = BluetoothStateReceiver()
            val filter = IntentFilter()
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            context.registerReceiver(mBluetoothStateReceiver, filter)

            val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            val mBluetoothDevice: BluetoothDevice = bluetoothManager.adapter.getRemoteDevice(printerSetting.address)

            connection = ClassicBluetooth.getInstance().createConnection(mBluetoothDevice, object : ConnectListener {
                override fun onConnectSuccess(connectedDevice: ConnectedDevice?) {
                    printUtils = TSPL.generic(connectedDevice)
                    statusPrinter = true
                }

                override fun onConnectFail(errMsg: String?, e: Throwable?) {
                    statusPrinter = false
                }
                override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
                    val msg: String
                    msg = when (state) {
                        Connection.STATE_CONNECTING -> "connecting"
                        Connection.STATE_PAIRING -> "Pairing..."
                        Connection.STATE_PAIRED -> "paired successfully"
                        Connection.STATE_CONNECTED -> "connection succeeded"
                        Connection.STATE_DISCONNECTED -> "Disconnect"
                        Connection.STATE_RELEASED -> "connection destroyed"
                        else -> ""
                    }
                }
            })
            connection?.connect(null)
        } catch (e: Exception){
            e.printStackTrace()
        }

        return if (statusPrinter) context.resources.getString(R.string.printer_connected) else context.resources.getString(R.string.printer_not_connected)
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, printerSetting: Setting){
        try {
            var is80mm = true
            var isGap = false
            var pageHeight = 36
            val isPrinter30mm = printerSetting.modelId == 5L
            val xPosition80mm = 30
            val xPosition30mm = 273

            var yPosition30mm = 30
            var yPosition80mm = 273

            if (isPrinter30mm) {
                is80mm = false
                pageHeight = 30
                isGap = true
            }

            val print = printUtils!!.page(TPage.builder().width(100).height(pageHeight).build())
                .direction(TDirection.builder().direction(TDirection.Direction.UP_OUT).mirror(TDirection.Mirror.NO_MIRROR).build())
                .gap(isGap)
                .cut(true)
                .speed(6)
                .density(6)

            var pagePosition: Int = if (is80mm) 30 else 23

            lines.forEach {
                val increment: Int = if (is80mm) 30 else 23
                val xPosition: Int = if (is80mm) 30 else 273
                when (it) {
                    is EmptyLine -> {
                        val text = "\n".repeat(it.lineCount)
                        print.text(TText.builder().x(xPosition).y(pagePosition).font(Font.TSS20).xmulti(1).ymulti(1).content(text).build())
                        pagePosition += increment
                    }
                    is TextCenter -> {
                        print.text(TText.builder().x(xPosition).y(pagePosition).font(Font.TSS20).xmulti(1).ymulti(1).content(it.text).build())
                        pagePosition += increment
                    }
                    is TextLeft -> {
                        print.text(TText.builder().x(xPosition).y(pagePosition).font(Font.TSS20).xmulti(1).ymulti(1).content(it.text).build())
                        pagePosition += increment
                    }
                    is TextRight -> {
                        val space = printerSetting.charCount + 0
                        val text = formatAlignRight(it.text, space)
                        print.text(TText.builder().x(xPosition).y(pagePosition).font(Font.TSS20).xmulti(1).ymulti(1).content(text).build())
                        pagePosition += increment
                    }
                    is TextLeftRight -> {
                        val space = printerSetting.charCount + 0
                        val text = formatLeftRight(it.left, it.right, space)
                        print.text(TText.builder().x(xPosition).y(pagePosition).font(Font.TSS20).xmulti(1).ymulti(1).content(text).build())
                        pagePosition += increment
                    }
                    is TextCustomSize -> {
                        print.text(TText.builder().x(xPosition).y(pagePosition).font(Font.TSS20).xmulti(1).ymulti(1).content(it.text).build())
                        pagePosition += increment
                    }
                    is Image -> {

                    }
                    is BitmapImage -> {

                    }
                    is Line -> {
                        print.line(TLine.builder().start_x(xPosition).end_x(900).width(1).build())
                        pagePosition += increment
                    }
                    is Barcode -> {
                        print.barcode(TBarCode.builder().x(xPosition).y(it.width).codeType(CodeType.CODE_128).showType(ShowType.SHOW_CENTER).content(it.text).build())
                        pagePosition += increment
                    }
                    is QRCode -> {

                    }
                }
            }
        } catch (e: Exception){

        }
    }

    fun disconnect(context: Context){
        context.unregisterReceiver(mBluetoothStateReceiver)
        connection?.disconnect()
    }


    private fun safeWriteAndRead(psdk: PSDK<*, *, *, *>): String? {
        return try {
            val reporter = psdk.write()
            if (!reporter.isOk) {
                throw IOException("Failed to write data", reporter.exception)
            }
            Thread.sleep(200)
            val bytes = psdk.read(ReadOptions.builder().timeout(2000).build())
            String(bytes)
        } catch (e: java.lang.Exception) {
            null
        }
    }

    internal class BluetoothStateReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
            when (state) {
                BluetoothAdapter.STATE_TURNING_ON -> toast(context, "Bluetooth ON")
                BluetoothAdapter.STATE_TURNING_OFF -> toast(context, "Bluetooth OFF")
            }
        }
    }
}