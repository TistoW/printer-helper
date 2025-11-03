package com.olserapratama.printer.implementation.xcheng

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
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