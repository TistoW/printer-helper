package com.olserapratama.printer.implementation.unicorn

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class UnicornPrinterImpl(
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        thread {
            listener(UnicornFunctions.connect(context, connectedPrinter))
        }
    }

    override fun isConnected(): Boolean {
        return UnicornFunctions.statusPrinter
    }

    override fun disconnect() {
        UnicornFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            UnicornFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {
        UnicornFunctions.openDrawer()
    }
}