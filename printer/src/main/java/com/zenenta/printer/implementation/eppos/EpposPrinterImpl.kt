package com.zenenta.printer.implementation.eppos

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class EpposPrinterImpl(
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(EpposFunctions.connect(context, connectedPrinter, false))
    }

    override fun isConnected(): Boolean {
        return EpposFunctions.statusPrinter
    }

    override fun disconnect() {
        EpposFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            EpposFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {
        EpposFunctions.openDrawer()
    }
}