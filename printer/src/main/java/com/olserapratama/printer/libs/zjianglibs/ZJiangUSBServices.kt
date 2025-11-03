package com.olserapratama.printer.libs.zjianglibs

import android.hardware.usb.UsbDevice

import com.zj.usbsdk.UsbController




object ZJiangUSBServices {

    fun getDevice(usbCtrl: UsbController): UsbDevice? {
        val u_infor = Array(8) { IntArray(2) }
        u_infor[0][0] = 0x1CBE
        u_infor[0][1] = 0x0003
        u_infor[1][0] = 0x1CB0
        u_infor[1][1] = 0x0003
        u_infor[2][0] = 0x0483
        u_infor[2][1] = 0x5740
        u_infor[3][0] = 0x0493
        u_infor[3][1] = 0x8760
        u_infor[4][0] = 0x0416
        u_infor[4][1] = 0x5011
        u_infor[5][0] = 0x0416
        u_infor[5][1] = 0xAABB
        u_infor[6][0] = 0x1659
        u_infor[6][1] = 0x8965
        u_infor[7][0] = 0x0483
        u_infor[7][1] = 0x5741
        var dev: UsbDevice? = null
        usbCtrl.close()
        var i = 0
        i = 0
        while (i < 8) {
            dev = usbCtrl.getDev(u_infor[i][0], u_infor[i][1])
            if (dev != null) break
            i++
        }
        return dev
    }

}