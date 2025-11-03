package com.olserapratama.printer.implementation.kassen.xa_02

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class Kassen02PrinterImpl(
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(Kassen02PrintFunctions.connectPrinter(context))
    }

    override fun isConnected(): Boolean {
        return Kassen02PrintFunctions.printerStatus
    }

    override fun disconnect() {}

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            Kassen02PrintFunctions.printReceipt(context, lines, connectedPrinter.charCount)
        }
    }

    override fun openDrawer() {

    }

}