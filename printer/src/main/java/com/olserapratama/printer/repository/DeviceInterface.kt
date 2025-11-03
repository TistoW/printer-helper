package com.olserapratama.printer.repository

enum class DeviceInterface(val code: String) {
    BLUETOOTH("BT:"),
    WIFI("TCP:"),
    USB("USB:"),
    INNER("INNER:"),
}