package com.olserapratama.printer.implementation.wintec

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
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
