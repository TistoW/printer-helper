package com.zenenta.printer.implementation.comson

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class ComsonPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(ComsonFunctions.connect(context, connectedPrinter))
    }

    override fun isConnected(): Boolean {
        return ComsonFunctions.statusPrinter
    }

    override fun disconnect() {
        ComsonFunctions.disconnect(connectedPrinter)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            ComsonFunctions.printReceipt(context, lines, connectedPrinter.charCount)
        }
    }

    override fun openDrawer() {
        ComsonFunctions.openDrawer()
    }

}