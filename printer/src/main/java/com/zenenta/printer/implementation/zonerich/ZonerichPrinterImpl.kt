package com.zenenta.printer.implementation.zonerich

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class ZonerichPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(ZonerichFunctions.connect(context, connectedPrinter))
    }

    override fun isConnected(): Boolean {
        return ZonerichFunctions.statusPrinter
    }

    override fun disconnect() {
        ZonerichFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            ZonerichFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {
        ZonerichFunctions.openDrawer()
    }
}