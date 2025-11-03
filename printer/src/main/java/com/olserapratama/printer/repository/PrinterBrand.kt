package com.olserapratama.printer.repository

class PrinterBrand(
    var id: Long = 0,
    var name: String = "",
    var models: List<PrinterModel> = arrayListOf()
)