package com.olserapratama.printer.implementation.pax

import android.content.Context
import com.olserapratama.printer.repository.DeviceInterface
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class PaxPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(PaxFunctions.connect(context, connectedPrinter))
    }

    override fun isConnected(): Boolean {
        return PaxFunctions.statusPrinter
    }

    override fun disconnect() {
        PaxFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            PaxFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {
        PaxFunctions.openDrawer()
    }
}