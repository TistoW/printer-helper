package com.olserapratama.printer.implementation.vsc

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class VscPrinterImpl(
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        thread {
            listener(VscFunctions.connect(context, connectedPrinter))
        }
    }

    override fun isConnected(): Boolean {
        return VscFunctions.statusPrinter
    }

    override fun disconnect() {
        VscFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            VscFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {
        VscFunctions.openDrawer()
    }
}