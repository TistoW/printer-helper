package com.olserapratama.printer.implementation.starmprinter

//import com.bumptech.glide.Glide
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.olserapratama.printer.util.*
import com.olserapratama.printer.util.PrinterUtil.stringToBarcode
import com.olserapratama.printer.util.PrinterUtil.stringToQrCode
import com.starmicronics.stario.StarIOPort
import com.starmicronics.stario.StarIOPortException
import com.starmicronics.stario.StarPrinterStatus
import com.starmicronics.starioextension.ICommandBuilder
import com.starmicronics.starioextension.StarIoExt
import timber.log.Timber
import java.nio.charset.Charset

object  StarPrintFunctions {
    var statusPrinter = false
    enum class Result {
        Success,
        ErrorUnknown,
        ErrorOpenPort,
        ErrorBeginCheckedBlock,
        ErrorEndCheckedBlock,
        ErrorWritePort,
        ErrorReadPort,
        ErrorNoData,
        ErrorNoPrinter
    }

    private val encoding = Charset.forName("US-ASCII")

    fun connectPrinter(portName: String, portSettings: String, timeout: Int, context: Context): String{
        var port: StarIOPort? = null
        try {
            //var port: StarIOPort
            port = StarIOPort.getPort(portName, portSettings, timeout, context)

            val status: StarPrinterStatus = port.beginCheckedBlock()
            return if (status.offline) {
                statusPrinter = false
                "Star printer is offline"
            } else {
                statusPrinter = true
                "Star printer is online"
            }
        } catch (e: StarIOPortException) {
            statusPrinter = false
            return e.message.toString()
        } finally {
            try {
                StarIOPort.releasePort(port)
                port = null
            } catch (e: StarIOPortException) {
                // Nothing
            }
        }
    }

    fun createReceiptData(
        context: Context,
        lines: List<PrintLine>,
        emulation: StarIoExt.Emulation,
        charCount: Int
    ): ByteArray {
        return if (emulation == StarIoExt.Emulation.StarGraphic) {
            createRasterReceipt(context, lines, emulation, charCount)
        } else {
            createTextReceipt(context, lines, emulation, charCount)
        }
    }

    private fun createRasterReceipt(
        context: Context,
        lines: List<PrintLine>,
        emulation: StarIoExt.Emulation,
        charCount: Int
    ): ByteArray {
        val builder = StarIoExt.createCommandBuilder(emulation)
        return builder.commands
    }

    private fun createTextReceipt(context: Context, lines: List<PrintLine>, emulation: StarIoExt.Emulation, charCount: Int): ByteArray {
        val builder = StarIoExt.createCommandBuilder(emulation)

        builder.beginDocument()
        builder.appendCodePage(ICommandBuilder.CodePageType.CP998)
        builder.appendInternational(ICommandBuilder.InternationalType.USA)
        builder.appendCharacterSpace(0)

        lines.forEach {
            when (it) {
                is EmptyLine -> {
                    builder.addText("\n".repeat(it.lineCount))
                }
                is TextCenter -> {
                    builder.appendAlignment(ICommandBuilder.AlignmentPosition.Center)
                    builder.addText(it.text + "\n")
                }
                is TextLeft -> {
                    builder.appendAlignment(ICommandBuilder.AlignmentPosition.Left)
                    builder.addText(it.text + "\n")
                }
                is TextRight -> {
                    builder.appendAlignment(ICommandBuilder.AlignmentPosition.Right)
                    builder.addText(it.text + "\n")
                }
                is TextLeftRight -> {
                    builder.appendAlignment(ICommandBuilder.AlignmentPosition.Left)
                    builder.addText(PrinterUtil.formatLeftRight(it.left, it.right, charCount) + "\n")
                }
                is Image -> {
                    val bitmap = Glide.with(context)
                        .asBitmap()
                        .load(it.url)
                        .apply(RequestOptions().override(it.width, it.height).downsample(DownsampleStrategy.CENTER_INSIDE))
                        .submit(it.width, it.height)
                        .get()
                    builder.appendAlignment(ICommandBuilder.AlignmentPosition.Center)
                    builder.appendBitmap(bitmap, false)
                    builder.addText("\n")
                }
                is Line -> {
                    builder.appendAlignment(ICommandBuilder.AlignmentPosition.Center)
                    builder.addText("-".repeat(charCount) + "\n")
                }
                is Barcode -> {
                    val bitmap = stringToBarcode(it.text, it.width)
                    builder.appendAlignment(ICommandBuilder.AlignmentPosition.Center)
                    builder.appendBitmap(bitmap, false)
                    builder.addText("\n")
                }
                is QRCode -> {
                    val bitmap = stringToQrCode(it.text, it.width, it.height)
                    builder.appendAlignment(ICommandBuilder.AlignmentPosition.Center)
                    builder.appendBitmap(bitmap, false)
                    builder.addText("\n")
                }
                else -> {}
            }
        }

        builder.appendCutPaper(ICommandBuilder.CutPaperAction.PartialCutWithFeed)
        builder.endDocument()
        return builder.commands
    }

    fun printReceipt(commands: ByteArray, portName: String, portSettings: String, timeout: Int, context: Context): Result {
        var result = Result.ErrorUnknown
        var port: StarIOPort? = null
        try {
            result = Result.ErrorOpenPort
            Timber.d("sendCommands Start printer $portName $portSettings $timeout ")
            port = StarIOPort.getPort(portName, portSettings, timeout, context)

            result = Result.ErrorBeginCheckedBlock
            var status: StarPrinterStatus = port.beginCheckedBlock()
            if (status.offline) {
                throw StarIOPortException("Star $portName is offline")
            }
            result = Result.ErrorWritePort
            port.writePort(commands, 0, commands.size)
            result = Result.ErrorEndCheckedBlock
            port.setEndCheckedBlockTimeoutMillis(30000) // 30000mS!!!
            status = port.endCheckedBlock()
            if (status.coverOpen) {
                throw StarIOPortException("Star $portName cover is open")
            } else if (status.receiptPaperEmpty) {
                throw StarIOPortException("Star $portName, receipt paper is empty")
            } else if (status.offline) {
                throw StarIOPortException("Star $portName is offline")
            }
            result = Result.Success
        } catch (e: StarIOPortException) {
            Timber.d("StarIOPortException " + e.message + " " + e.stackTrace)
        } finally {
            if (port != null) {
                try {
                    StarIOPort.releasePort(port)
                    port = null
                } catch (e: StarIOPortException) {
                    // Nothing
                }
            }
        }
        return result
    }

    private fun ICommandBuilder.addText(text: String) {
        this.append(text.toByteArray(encoding))
    }

    fun openDrawer(emulation: StarIoExt.Emulation): ByteArray{
        val builder = StarIoExt.createCommandBuilder(emulation)

        builder.beginDocument()

        builder.appendPeripheral(ICommandBuilder.PeripheralChannel.No1)

        builder.endDocument()

        return builder.commands
    }
}