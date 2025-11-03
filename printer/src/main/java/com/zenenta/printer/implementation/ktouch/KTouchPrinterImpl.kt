package com.zenenta.printer.implementation.ktouch

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class KTouchPrinterImpl(
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(KTouchFunctions.onPrinterStart(context))
    }

    override fun isConnected(): Boolean {
        return KTouchFunctions.statusPrinter
    }

    override fun disconnect() {
        KTouchFunctions.onPrinterStop(context)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            KTouchFunctions.printerInit()
            KTouchFunctions.printReceipt(context, lines, connectedPrinter.charCount)
        }
    }

    override fun openDrawer() {
        KTouchFunctions.openDrawer()
    }

}