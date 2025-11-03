package com.olserapratama.printer.implementation.imin

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
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