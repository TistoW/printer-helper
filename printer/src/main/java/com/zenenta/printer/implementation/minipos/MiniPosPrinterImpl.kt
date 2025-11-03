package com.zenenta.printer.implementation.minipos

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class MiniPosPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        thread {
            listener(MiniPosFunctions.connect(context, connectedPrinter))
        }
    }

    override fun isConnected(): Boolean {
        return MiniPosFunctions.statusPrinter
    }

    override fun disconnect() {
        MiniPosFunctions.disconnect(connectedPrinter)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        /*thread {
            MiniPosFunctions.printReceipt(context, lines, connectedPrinter)
        }*/
        MiniPosFunctions.printReceipt(context, lines, connectedPrinter)
    }

    override fun openDrawer() {
        MiniPosFunctions.openDrawer()
    }
}