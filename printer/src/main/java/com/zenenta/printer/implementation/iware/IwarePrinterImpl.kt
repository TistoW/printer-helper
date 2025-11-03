package com.zenenta.printer.implementation.iware

import android.content.Context
import com.zenenta.printer.repository.DeviceInterface
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

class IwarePrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    private val semaphore = Semaphore(1)
    override fun connect(listener: (message: String) -> Unit) {
        thread {
            listener(IwareFunctions.connect(context, connectedPrinter))
        }
        //listener(IwareFunctions.connect(context, connectedPrinter))
    }

    override fun isConnected(): Boolean {
        return IwareFunctions.statusPrinter
    }

    override fun disconnect() {
        IwareFunctions.disconnect(connectedPrinter)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        /*thread {
        }*/
        semaphore.acquire()
        thread {
            IwareFunctions.printUsingBluetooth(context, lines, connectedPrinter.charCount)
            semaphore.release()
        }
        /*when (connectedPrinter.deviceInterface) {
            DeviceInterface.WIFI.code -> {
                IwareFunctions.printUsingWifi(context, lines, connectedPrinter)
            }
            else -> {
                IwareFunctions.printUsingBluetooth(context, lines, connectedPrinter.charCount)
            }
        }*/
    }

    override fun openDrawer() {
        when (connectedPrinter.deviceInterface){
            DeviceInterface.WIFI.code -> {
                IwareFunctions.openDrawerWifi()
            }
            else -> {
                IwareFunctions.openDrawerBluetooth()
            }
        }
    }
}