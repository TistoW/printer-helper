package com.olserapratama.printer.implementation.kassen.xa_923

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class Kassen923PrinterImpl(
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(Kassen923PrintFunctions.connectPrinter(context))
    }

    override fun isConnected(): Boolean {
        return Kassen923PrintFunctions.printerStatus
    }

    override fun disconnect() {
        Kassen923PrintFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            Kassen923PrintFunctions.printReceipt(context, lines, connectedPrinter.charCount)
        }
    }

    override fun openDrawer() {

    }
}