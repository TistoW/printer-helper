package com.olserapratama.printer.repository

open class PrinterModel(
    var id: Long = 0,
    var name: String = "",
    var charCount: Int = 44,
    var hasBuiltInPrinter: Boolean = false
)