package com.olserapratama.printer.repository

data class Setting(
    var name: String = "",
    var brandId: Long? = null,
    var modelId: Long? = null,
    var deviceInterface: String = "",
    var address: String = "",
    var charCount: Int = 32,
    var numberCopy: Int = 1,
    var printLogo: Boolean = false,
) {
    fun getDeviceDestination(): String {
        return address
    }

    fun getSeriesId(): Int {
        return modelId?.toInt() ?: 0
    }
    fun getNumberCols(): Int {
        return charCount
    }

    fun getReceiptHeaderLeftCols(): Int {
        return when (charCount) {
            32 -> 20
            34 -> 20
            36 -> 20
            38 -> 20
            40 -> 20
            42 -> 21
            44 -> 22
            46 -> 23
            48 -> 24
            50 -> 25
            52 -> 26
            54 -> 27
            56 -> 28
            58 -> 29
            60 -> 30
            62 -> 31
            else -> 20
        }
    }

    fun getReceiptHeaderRightCols(): Int {
        return charCount - getReceiptHeaderLeftCols()
    }
}