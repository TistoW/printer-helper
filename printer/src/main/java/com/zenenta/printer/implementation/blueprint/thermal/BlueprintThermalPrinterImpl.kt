package com.zenenta.printer.implementation.blueprint.thermal

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import kotlin.concurrent.thread

class BlueprintThermalPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(BlueprintThermalFunctions.connect(context, connectedPrinter))
    }

    override fun isConnected(): Boolean {
        return BlueprintThermalFunctions.statusPrinter
    }

    override fun disconnect() {
        BlueprintThermalFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            BlueprintThermalFunctions.printReceipt(context, lines, connectedPrinter)
        }
    }

    override fun openDrawer() {
        BlueprintThermalFunctions.openDrawer()
    }
}