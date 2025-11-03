package com.olserapratama.printer.implementation.starmprinter

import android.content.Context
import com.starmicronics.stario.StarIOPort
import com.starmicronics.stario.StarIOPortException
import com.starmicronics.stario.StarPrinterStatus
import timber.log.Timber





class StarCommunicationKotlin {

    enum class Result {
        Success, ErrorUnknown, ErrorOpenPort, ErrorBeginCheckedBlock, ErrorEndCheckedBlock, ErrorWritePort, ErrorReadPort, ErrorNoData, ErrorNoPrinter
    }

    fun sendCommands(commands: ByteArray, port: StarIOPort, context: Context): Result {
        var result = Result.ErrorUnknown
        try {
            if (port == null) {
                result = Result.ErrorOpenPort
                return result
            }

//          // When using USB interface with mPOP(F/W Ver 1.0.1), you need to send the following data.
//          byte[] dummy = {0x00};
//          port.writePort(dummy, 0, dummy.length);
            var status: StarPrinterStatus
            result = Result.ErrorBeginCheckedBlock
            status = port.beginCheckedBlock()
            if (status.offline) {
                throw StarIOPortException("A printer is offline")
            }
            result = Result.ErrorWritePort
            port.writePort(commands, 0, commands.size)
            result = Result.ErrorEndCheckedBlock
            port.setEndCheckedBlockTimeoutMillis(30000) // 30000mS!!!
            status = port.endCheckedBlock()
            if (status.coverOpen) {
                throw StarIOPortException("Printer cover is open")
            } else if (status.receiptPaperEmpty) {
                throw StarIOPortException("Receipt paper is empty")
            } else if (status.offline) {
                throw StarIOPortException("Printer is offline")
            }
            result = Result.Success
        } catch (e: StarIOPortException) {
            // Nothing
        }
        return result
    }

    fun sendCommandsDoNotCheckCondition(commands: ByteArray, port: StarIOPort?, context: Context?): Result? {
        var result = Result.ErrorUnknown
        try {
            if (port == null) {
                result = Result.ErrorOpenPort
                return result
            }

//          // When using USB interface with mPOP(F/W Ver 1.0.1), you need to send the following data.
//          byte[] dummy = {0x00};
//          port.writePort(dummy, 0, dummy.length);
            var status: StarPrinterStatus
            result = Result.ErrorWritePort
            status = port.retreiveStatus()
            if (status.rawLength == 0) {
                throw StarIOPortException("A printer is offline")
            }
            result = Result.ErrorWritePort
            port.writePort(commands, 0, commands.size)
            result = Result.ErrorWritePort
            status = port.retreiveStatus()
            if (status.rawLength == 0) {
                throw StarIOPortException("A printer is offline")
            }
            result = Result.Success
        } catch (e: StarIOPortException) {
            // Nothing
        }
        return result
    }

    fun sendCommands(commands: ByteArray, portName: String, portSettings: String, timeout: Int, context: Context): Result {
        var result = Result.ErrorUnknown
        var port: StarIOPort? = null
        try {
            result = Result.ErrorOpenPort
            Timber.d("sendCommands Start printer $portName $portSettings $timeout ")
            port = StarIOPort.getPort(portName, portSettings, timeout, context)

//          // When using USB interface with mPOP(F/W Ver 1.0.1), you need to send the following data.
//          byte[] dummy = {0x00};
//          port.writePort(dummy, 0, dummy.length);
            var status: StarPrinterStatus
            result = Result.ErrorBeginCheckedBlock
            status = port.beginCheckedBlock()
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
            // Nothing
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

    fun sendCommandsDoNotCheckCondition(commands: ByteArray, portName: String, portSettings: String, timeout: Int, context: Context): Result {
        var result = Result.ErrorUnknown
        var port: StarIOPort? = null
        try {
            result = Result.ErrorOpenPort
            port = StarIOPort.getPort(portName, portSettings, timeout, context)

//          // When using USB interface with mPOP(F/W Ver 1.0.1), you need to send the following data.
//          byte[] dummy = {0x00};
//          port.writePort(dummy, 0, dummy.length);
            var status: StarPrinterStatus
            result = Result.ErrorWritePort
            status = port.retreiveStatus()
            if (status.rawLength == 0) {
                throw StarIOPortException("A printer is offline")
            }
            result = Result.ErrorWritePort
            port.writePort(commands, 0, commands.size)
            result = Result.ErrorWritePort
            status = port.retreiveStatus()
            if (status.rawLength == 0) {
                throw StarIOPortException("A printer is offline")
            }
            result = Result.Success
        } catch (e: StarIOPortException) {
            // Nothing
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

    fun retrieveStatus(portName: String, portSettings: String, timeout: Int, context: Context): StarPrinterStatus {
        var port: StarIOPort? = null
        var status = StarPrinterStatus()
        try {
            port = StarIOPort.getPort(portName, portSettings, timeout, context)

//          // When using USB interface with mPOP(F/W Ver 1.0.1), you need to send the following data.
//          byte[] dummy = {0x00};
//          port.writePort(dummy, 0, dummy.length);
            status = port.retreiveStatus()
            if (status.rawLength == 0) {
                throw StarIOPortException("A printer is offline")
            }
        } catch (e: StarIOPortException) {
            // Nothing
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
        return status
    }

}