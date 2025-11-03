package com.olserapratama.printer.implementation.zjiang

import android.content.Context
import com.olserapratama.printer.repository.DeviceInterface
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class ZjiangPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(ZjiangFunctions.connect(context, connectedPrinter))
        //ZjiangFunctions.connect(context, connectedPrinter)
    }

    override fun isConnected(): Boolean {
        return ZjiangFunctions.statusPrinter
    }

    override fun disconnect() {
        ZjiangFunctions.disconnect(connectedPrinter)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            when (connectedPrinter.deviceInterface){
                DeviceInterface.BLUETOOTH.code -> {
                    ZjiangFunctions.printReceiptByBluetooth(context, lines, connectedPrinter)
                }
                DeviceInterface.WIFI.code -> {

                }
                else -> {
                    ZjiangFunctions.printReceiptByUsb(context, lines, connectedPrinter)
                }
            }
        }
    }

    override fun openDrawer() {
        when (connectedPrinter.deviceInterface){
            DeviceInterface.BLUETOOTH.code -> {
                ZjiangFunctions.openDrawerByBluetooth()
            }
            DeviceInterface.WIFI.code -> {

            }
            else -> {
                ZjiangFunctions.openDrawerByUsb(context)
            }
        }
    }
}