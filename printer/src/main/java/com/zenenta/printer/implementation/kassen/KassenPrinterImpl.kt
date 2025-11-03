package com.zenenta.printer.implementation.kassen

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class KassenPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        thread {
            listener(KassenFunctions.connect(context, connectedPrinter))
        }
    }

    override fun isConnected(): Boolean {
        return KassenFunctions.statusPrinter
    }

    override fun disconnect() {
        KassenFunctions.disconnect(connectedPrinter)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        /*thread {
            KassenFunctions.printReceipt(context, lines, connectedPrinter.charCount)
        }*/
        KassenFunctions.printReceipt(context, lines, connectedPrinter.charCount)
    }

    override fun openDrawer() {
        KassenFunctions.openDrawer()
    }

}