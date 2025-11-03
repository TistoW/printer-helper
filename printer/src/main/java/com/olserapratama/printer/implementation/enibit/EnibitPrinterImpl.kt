package com.olserapratama.printer.implementation.enibit

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class EnibitPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        thread {
            listener(EnibitFunctions.connect(context, connectedPrinter))
        }
    }

    override fun isConnected(): Boolean {
        return EnibitFunctions.statusPrinter
    }

    override fun disconnect() {
        EnibitFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            EnibitFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {

    }
}