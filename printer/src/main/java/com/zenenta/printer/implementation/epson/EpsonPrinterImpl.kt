package com.zenenta.printer.implementation.epson

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class EpsonPrinterImpl  (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(EpsonFunctions.connect(context, connectedPrinter))
    }

    override fun isConnected(): Boolean {
        return EpsonFunctions.statusPrinter
    }

    override fun disconnect() {
        EpsonFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            EpsonFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {
        EpsonFunctions.openDrawer()
    }

}