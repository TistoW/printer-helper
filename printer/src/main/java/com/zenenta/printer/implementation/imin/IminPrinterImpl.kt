package com.zenenta.printer.implementation.imin

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread
class IminPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(IminFunctions.connect(context, connectedPrinter))
    }

    override fun isConnected(): Boolean {
        return IminFunctions.statusPrinter
    }

    override fun disconnect() {
        IminFunctions.disconnect(context)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            IminFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {
        IminFunctions.openDrawer(context, connectedPrinter)
    }
}