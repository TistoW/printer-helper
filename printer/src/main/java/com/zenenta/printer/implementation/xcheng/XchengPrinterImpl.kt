package com.zenenta.printer.implementation.xcheng

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class XchengPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(XchengFunctions.connect(context))
    }

    override fun isConnected(): Boolean {
        return XchengFunctions.statusPrinter
    }

    override fun disconnect() {
        XchengFunctions.disconnect(context)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            XchengFunctions.printReceipt(context, lines, connectedPrinter.charCount)
        }
    }

    override fun openDrawer() {

    }
}