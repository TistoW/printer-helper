package com.olserapratama.printer.implementation.starmprinter

import android.content.Context
import com.olserapratama.printer.repository.Setting
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import kotlin.concurrent.thread

class StarPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
        listener(StarPrintFunctions.connectPrinter(connectedPrinter.deviceInterface, connectedPrinter.address, 10000, context))
    }

    override fun isConnected() : Boolean{
        return StarPrintFunctions.statusPrinter
    }

    override fun disconnect() {}

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            val modelId = connectedPrinter.modelId!!.toInt()
            val emulation = StarModelCapability.getEmulation(modelId)
            val commands = StarPrintFunctions.createReceiptData(context, lines, emulation, connectedPrinter.charCount)

            StarPrintFunctions.printReceipt(commands, connectedPrinter.deviceInterface, connectedPrinter.address, 10000, context)
        }
    }

    override fun openDrawer() {
        thread {
            val modelId = connectedPrinter.modelId!!.toInt()
            val emulation = StarModelCapability.getEmulation(modelId)
            val commands = StarPrintFunctions.openDrawer(emulation)

            StarCommunication.sendCommands(commands, connectedPrinter.deviceInterface, connectedPrinter.address, 10000, context)
        }
    }
}
