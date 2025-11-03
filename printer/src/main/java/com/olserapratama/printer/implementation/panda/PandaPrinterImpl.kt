package com.olserapratama.printer.implementation.panda

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class PandaPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        thread {
            listener(PandaFunctions.connect(context, connectedPrinter))
        }
    }

    override fun isConnected(): Boolean {
        return PandaFunctions.statusPrinter
    }

    override fun disconnect() {
        PandaFunctions.disconnect(connectedPrinter)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            PandaFunctions.printReceipt(context, lines, connectedPrinter.charCount)
        }
    }

    override fun openDrawer() {
        PandaFunctions.openDrawer()
    }
}