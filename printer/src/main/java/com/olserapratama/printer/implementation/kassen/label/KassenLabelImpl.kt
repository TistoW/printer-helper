package com.olserapratama.printer.implementation.kassen.label

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class KassenLabelImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        KassenLabelFunctions.connect(context, connectedPrinter)
    }

    override fun isConnected(): Boolean {
        return KassenLabelFunctions.statusPrinter
    }

    override fun disconnect() {
        KassenLabelFunctions.disconnect(context)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            KassenLabelFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {

    }

}

