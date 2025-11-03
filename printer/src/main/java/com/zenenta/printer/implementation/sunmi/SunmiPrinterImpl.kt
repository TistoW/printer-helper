package com.zenenta.printer.implementation.sunmi

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class SunmiPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
//        SunmiFunctions.initPrinter()
        listener(SunmiFunctions.connect(context))
    }

    override fun isConnected(): Boolean {
        return SunmiFunctions.statusPrinter
    }

    override fun disconnect() {
        SunmiFunctions.disconnect(context)
    }

    override fun printInvoice(lines: List<PrintLine>) {
        SunmiFunctions.initPrinter()
        thread {
            SunmiFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {
        SunmiFunctions.openDrawer()
    }
}
