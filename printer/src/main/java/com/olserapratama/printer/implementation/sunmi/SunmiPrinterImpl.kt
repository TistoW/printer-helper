package com.olserapratama.printer.implementation.sunmi

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
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
