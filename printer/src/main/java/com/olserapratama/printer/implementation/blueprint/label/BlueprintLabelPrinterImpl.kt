package com.olserapratama.printer.implementation.blueprint.label

import android.content.Context
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.util.PrintLine
import com.olserapratama.printer.repository.Setting
import kotlin.concurrent.thread

class BlueprintLabelPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    override fun connect(listener: (message: String) -> Unit) {
    }

    override fun isConnected(): Boolean {
        return true
    }

    override fun disconnect() {
        BlueprintLabelFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        thread {
            BlueprintLabelFunctions.printReceipt(context, lines, connectedPrinter)
//            if (connectedPrinter.modelId == 5L){
//                BlueprintLabelFunctions.printReceipt(context, lines, connectedPrinter)
//            } else {
//                BlueprintLabelFunctions.printReceiptWithGap(context, lines, connectedPrinter)
//            }
        }
    }

    override fun openDrawer() {
        BlueprintLabelFunctions.openDrawer()
    }
}