package com.olserapratama.printer.util

import android.content.Context
import com.olserapratama.printer.repository.Setting

interface IPrinter {
    val context: Context
    var connectedPrinter: Setting

    fun connect(listener: (message: String) -> Unit)
    fun isConnected(): Boolean
    fun disconnect()
    fun printInvoice(lines: List<PrintLine>)
    fun openDrawer()
}