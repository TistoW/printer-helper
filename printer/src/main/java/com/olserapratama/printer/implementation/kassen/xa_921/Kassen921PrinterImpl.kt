package com.olserapratama.printer.implementation.kassen.xa_921

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class Kassen921PrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(Kassen921PrintFunctions.connectPrinter(context))
    }

    override fun isConnected() :Boolean{
        Kassen921PrintFunctions.initPrinter(context)
        return Kassen921PrintFunctions.printerStatus
    }

    override fun disconnect() {
        Kassen921PrintFunctions.disconnectPrinter()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            Kassen921PrintFunctions.printReceipt(context, lines, connectedPrinter.charCount)
        }
    }

    override fun openDrawer() {
        Kassen921PrintFunctions.openDrawer()
    }
}
