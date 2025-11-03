package com.olserapratama.printer.implementation.iware

import android.content.Context
import com.olserapratama.printer.repository.DeviceInterface
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
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