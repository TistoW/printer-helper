package com.zenenta.printer.implementation.silicon

import android.content.Context
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.util.PrintLine
import com.zenenta.printer.repository.Setting
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

class SiliconPrinterImpl (
    override val context: Context,
    override var connectedPrinter: Setting
) : IPrinter {
    private val semaphore = Semaphore(1)
    override fun connect(listener: (message: String) -> Unit) {
        thread {
            listener(SiliconFunctions.connect(context, connectedPrinter))
        }
        //listener(SiliconFunctions.connect(context, connectedPrinter))
    }

    override fun isConnected(): Boolean {
        return SiliconFunctions.isConnected()
    }

    override fun disconnect() {
        SiliconFunctions.disconnect()
    }

    override fun printInvoice(lines: List<PrintLine>) {
        semaphore.acquire()
        thread {
            /*if (connectedPrinter.numberCopy < 1) {
                SiliconFunctions.printReceipt(context, lines, connectedPrinter)
            } else {
                for (i in 0 until connectedPrinter.numberCopy) {
                    SiliconFunctions.printReceipt(context, lines, connectedPrinter)
                }
            }*/
            SiliconFunctions.printReceipt(context, lines, connectedPrinter)
            semaphore.release()
        }
        //SiliconFunctions.printReceipt(context, lines, connectedPrinter)
    }

    override fun openDrawer() {
        SiliconFunctions.openDrawer()
    }
}

