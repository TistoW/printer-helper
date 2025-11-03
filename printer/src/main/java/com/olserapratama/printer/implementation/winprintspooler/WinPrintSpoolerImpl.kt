package com.olserapratama.printer.implementation.winprintspooler

import android.content.Context
import android.text.TextUtils
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.olserapratama.printer.repository.Setting
import java.util.concurrent.ExecutionException


object WinPrintSpoolerImpl {

    fun printWinSpooler(context: Context, connectedPrinter: Setting, jSalesOrder: JsonObject): Boolean{
        val printerDestInfo = TextUtils.split(connectedPrinter.address, "/")
        if (printerDestInfo.size < 2) {
            return false
        }
        var url = printerDestInfo[0].trim { it <= ' ' }
        if (!url.contains("http://")) {
            url = String.format("http://%s", url)
        }
        url = String.format("%s/olserapos/printerspool", url)

        val printerDestination = printerDestInfo[1].trim { it <= ' ' }
        jSalesOrder.addProperty("printer_destination", printerDestination)

        var result: String? = null
        try {
            result = Ion.with(context)
                .load(url)
                .setJsonObjectBody(jSalesOrder)
                .asString()
                .get()
            if (result != null && result.length > 1) {
                return false
            }
            return true
        } catch (e: InterruptedException) {
            e.printStackTrace()
            return false
        } catch (e: ExecutionException) {
            e.printStackTrace()
            return false
        }
    }
}