package com.olserapratama.printer.implementation.pax

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.olserapratama.printer.R
import com.olserapratama.printer.libs.paxLibs.IGL
import com.olserapratama.printer.libs.paxLibs.imgprocessing.IImgProcessing
import com.olserapratama.printer.libs.paxLibs.impl.GL
import com.olserapratama.printer.libs.paxLibs.page.IPage
import com.olserapratama.printer.repository.Setting
import com.olserapratama.printer.util.*
import com.olserapratama.printer.util.PrinterUtil.stringToBarcode
import com.olserapratama.printer.util.PrinterUtil.stringToQrCode
import com.pax.dal.IDAL
import com.pax.dal.IPrinter
import com.pax.neptunelite.api.NeptuneLiteUser

object PaxFunctions {
    var statusPrinter = false

    private var dal: IDAL? = null
    private var iPrinter: IPrinter? = null

    @SuppressLint("LogNotTimber")
    fun connect(context: Context, printerSetting: Setting): String {
        try {
            Log.d("PaxFunctions", "connect: " + printerSetting.name)
            dal = NeptuneLiteUser.getInstance().getDal(context)
            iPrinter = dal?.printer
            iPrinter?.init()

            if (iPrinter?.status != 0) {
                statusPrinter = false
                return getPrinterStatusMessage()
            }
        } catch (e: Exception) {
            Log.d("TAG", "connectError: " + e.message)
            if (iPrinter?.status != 0) {
                return getPrinterStatusMessage()
            }
            statusPrinter = false
        }

        statusPrinter = true
        return context.getString(R.string.printer_connected)
    }

    @SuppressLint("LogNotTimber")
    private fun getPrinterStatusMessage(): String {
        val message = when (iPrinter?.status) {
            1 -> "Printer is busy"
            2 -> "Out of paper"
            3 -> "The format of print data packet error"
            4 -> "Printer malfunctions"
            8 -> "Printer over heats"
            9 -> "Printer voltage is too low"
            -16 -> "Printing is unfinished"
            -6 -> "cut jam error(only support:E500,E800)"
            -5 -> "cover open error(only support:E500,E800,SK600,SK800)"
            -4 -> "The printer has not installed font library"
            -2 -> "Data package is too long"
            else -> "Printer is not connected!"
        }
        Log.d("PaxFunctions", "connect: $message")
        return message
    }

    fun disconnect() {
        statusPrinter = false
//        iPrinter?.disconnect()
    }

    private lateinit var page: IPage
    private fun generatePage(context: Context): IPage {
        val gl: IGL = GL(context)
        val page: IPage = gl.imgProcessing.createPage()
        page.adjustLineSpace(-9)
        page.typeFace = Typeface.createFromAsset(context.assets, "Roboto-Regular.ttf")
        return page
    }

//    fun addText(
//        text: String?,
//        alignment: Alignment = Alignment.Left,
//        style: Style? = null,
//        size: Size? = null,
//        newLine: Boolean = true
//    ) {
//        val temp = createText(text, alignment, style, size)
//        val space = if (newLine) "\n" else ""
//        printText += "$temp$space"
//    }
//
//    fun leftText(text: String?, size: Size? = null) {
//        addText(text, size = size)
//    }

    private fun leftText(text: String?) {
        page.addLine().addUnit(page.createUnit().setText(text).setGravity(Gravity.LEFT))
    }

    private fun rightText(text: String?) {
        page.addLine().addUnit(page.createUnit().setText(text).setGravity(Gravity.END))
    }

    private fun centerText(text: String?) {
        page.addLine().addUnit(page.createUnit().setText(text).setGravity(Gravity.CENTER))
    }

    private fun leftRightText(leftText: String?, rightText: String?) {
        page.addLine().addUnit(
                page.createUnit()
                        .setText(leftText)
                        .setGravity(Gravity.START)
        )
                .addUnit(
                        page.createUnit()
                                .setText(rightText)
                                .setGravity(Gravity.END)
                )
    }

    private fun printBitmap(bitmap: Bitmap?) {
        page.addLine().addUnit(page.createUnit().setBitmap(bitmap).setGravity(Gravity.CENTER))
    }

    fun printReceipt(context: Context, lines: List<PrintLine>, printerSetting: Setting): Boolean {
        try {
            if (iPrinter?.status != 0) {
                statusPrinter = false
                getPrinterStatusMessage()
                return false
            }

            // 384 = small paper | 550 = large paper
            val width = if (printerSetting.charCount in 32..48) 384
            else if (printerSetting.charCount <= 48) 550
            else 384

            fun createPage(width: Int): Bitmap? {
                page = generatePage(context)
                lines.forEach {
                    when (it) {
                        is EmptyLine -> {
                            for (i in 0..it.lineCount) {
                                leftText(" ")
                            }
                        }

                        is TextCenter -> {
                            centerText(it.text)
                        }

                        is TextLeft -> {
                            leftText(it.text)
                        }

                        is TextRight -> {
                            rightText(it.text)
                        }

                        is TextLeftRight -> {
                            leftRightText(it.left, it.right)
                        }

                        is TextCustomSize -> {
                            centerText(it.text)
                        }
                        is Image -> {
                            val bitmap = Glide.with(context)
                                    .asBitmap()
                                    .load(it.url)
                                    .apply(
                                            RequestOptions().override(it.width, it.height)
                                                    .downsample(DownsampleStrategy.CENTER_INSIDE)
                                    )
                                    .submit(it.width, it.height)
                                    .get()
                            if (bitmap != null)
                                printBitmap(bitmap)
                        }

                        is BitmapImage -> {
                            try {
                                printBitmap(it.bitmap)
                            } catch (e: Exception) {
                                //
                            }
                        }

                        is Line -> {
                            centerText("-".repeat(printerSetting.charCount))
                        }

                        is Barcode -> {
                            val bitmap = stringToBarcode(it.text, it.width)
                            printBitmap(bitmap)
                        }

                        is QRCode -> {
                            val bitmap = stringToQrCode(it.text, it.width, it.height)
                            printBitmap(bitmap)
                        }
                    }
                }
                leftText(" ")
                leftText(" ")
                leftText(" ")
                val gl: IGL = GL.getInstance(context)
                val imgProcessing: IImgProcessing = gl.imgProcessing
                return imgProcessing.pageToBitmap(page, width)
            }

            iPrinter?.setGray(3)
            iPrinter?.printBitmap(createPage(width)) //for print bitmap
            iPrinter?.start()

            cutPaper()
        } catch (e: Exception) {
            e.printStackTrace()
            statusPrinter = false
            return false
        }
        return true
    }

    @SuppressLint("LogNotTimber")
    private fun cutPaper() {
        /**
        0:Only support full paper cut
        1:Only support partial paper cutting
        2:support partial paper and full paper cutting
        -1:No cutting knife,not support
         **/

        val cuterStatus = iPrinter?.cutMode ?: 0
        try {
            val cutMode: Int = when (cuterStatus) {
                0, 2 -> 0
                1 -> 1
                else -> -1
            }
            if (cutMode != -1) {
                iPrinter?.cutPaper(cutMode)
            } else {
                Log.d("TAG", "No cutting knife")
            }

        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
        }
    }

    fun openDrawer() {
        try {
            if (iPrinter?.status != 0) {
                statusPrinter = false
                getPrinterStatusMessage()
            }

            dal?.cashDrawer?.open()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}