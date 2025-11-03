package com.olserapratama.printer.implementation.epson

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
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