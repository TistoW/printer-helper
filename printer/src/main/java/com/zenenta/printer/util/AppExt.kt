package com.zenenta.printer.util

import android.hardware.usb.UsbDevice
import com.printer.sdk.usb.USBPort

class AppExt {
}

fun isUsbPrinter(device: UsbDevice): Boolean {
    return USBPort.isUsbPrinter(device)
}