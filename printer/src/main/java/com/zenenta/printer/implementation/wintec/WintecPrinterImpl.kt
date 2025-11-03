package com.zenenta.printer.implementation.wintec

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class WintecPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(WintecFunctions.connect(context))
    }

    override fun isConnected(): Boolean {
        return WintecFunctions.statusPrinter
    }

    override fun disconnect() {
        WintecFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            WintecFunctions.printReceipt(context, lines, connectedPrinter.charCount)
        }
    }

    override fun openDrawer() {

    }
}
