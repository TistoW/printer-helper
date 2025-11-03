package com.zenenta.printer.util

import android.content.Context
import com.zenenta.printer.repository.Setting

interface IPrinter {
    val context: Context
    var connectedPrinter: Setting

    fun connect(listener: (message: String) -> Unit)
    fun isConnected(): Boolean
    fun disconnect()
    fun printInvoice(lines: List<PrintLine>)
    fun openDrawer()
}