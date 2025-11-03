package com.olserapratama.printer.libs.zjianglibs

import zj.com.customize.sdk.Other;
import java.io.UnsupportedEncodingException

object PrinterCommand {

    fun POS_Set_PrtInit(): ByteArray {
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_Init
            )
        )
    }

    fun POS_Set_LF(): ByteArray {
        return Other.byteArraysToBytes(
            arrayOf(
                Command.LF
            )
        )
    }

    fun POS_Set_PrtAndFeedPaper(feed: Int): ByteArray? {
        if (feed > 255 || feed < 0) return null
        Command.ESC_J[2] = feed.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_J
            )
        )
    }

    fun POS_Set_PrtSelfTest(): ByteArray? {
        return Other.byteArraysToBytes(
            arrayOf(
                Command.US_vt_eot
            )
        )
    }

    fun POS_Set_Beep(m: Int, t: Int): ByteArray? {
        if ((m < 1 || m > 9) or (t < 1 || t > 9)) return null
        Command.ESC_B_m_n[2] = m.toByte()
        Command.ESC_B_m_n[3] = t.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_B_m_n
            )
        )
    }

    fun POS_Set_Cut(cut: Int): ByteArray? {
        if (cut > 255 || cut < 0) return null
        Command.GS_V_m_n[3] = cut.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.GS_V_m_n
            )
        )
    }

    fun POS_Set_Cashbox(nMode: Int, nTime1: Int, nTime2: Int): ByteArray? {
        if ((nMode < 0 || nMode > 1) or (nTime1 < 0) or (nTime1 > 255) or (nTime2 < 0) or (nTime2 > 255)) return null
        Command.ESC_p[2] = nMode.toByte()
        Command.ESC_p[3] = nTime1.toByte()
        Command.ESC_p[4] = nTime2.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_p
            )
        )
    }

    fun POS_Set_Absolute(absolute: Int): ByteArray? {
        if (absolute > 65535 || absolute < 0) return null
        Command.ESC_Relative[2] = (absolute % 0x100).toByte()
        Command.ESC_Relative[3] = (absolute / 0x100).toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_Relative
            )
        )
    }

    fun POS_Set_Relative(relative: Int): ByteArray? {
        if (relative < 0 || relative > 65535) return null
        Command.ESC_Absolute[2] = (relative % 0x100).toByte()
        Command.ESC_Absolute[3] = (relative / 0x100).toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_Absolute
            )
        )
    }

    fun POS_Set_LeftSP(left: Int): ByteArray? {
        if (left > 255 || left < 0) return null
        Command.GS_LeftSp[2] = (left % 100).toByte()
        Command.GS_LeftSp[3] = (left / 100).toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.GS_LeftSp
            )
        )
    }

    fun POS_S_Align(align: Int): ByteArray? {
        if ((align < 0 || align > 2) or (align < 48 || align > 50)) return null
        val data = Command.ESC_Align
        data[2] = align.toByte()
        return data
    }

    fun POS_Set_PrintWidth(width: Int): ByteArray? {
        if (width < 0 || width > 255) return null
        Command.GS_W[2] = (width % 100).toByte()
        Command.GS_W[3] = (width / 100).toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.GS_W
            )
        )
    }

    fun POS_Set_DefLineSpace(): ByteArray {
        return Command.ESC_Two
    }

    fun POS_Set_LineSpace(space: Int): ByteArray? {
        if (space < 0 || space > 255) return null
        Command.ESC_Three[2] = space.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_Three
            )
        )
    }

    fun POS_Set_CodePage(page: Int): ByteArray? {
        if (page > 255) return null
        Command.ESC_t[2] = page.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_t
            )
        )
    }

    fun POS_Print_Text(
        pszString: String?, encoding: String?, codepage: Int,
        nWidthTimes: Int, nHeightTimes: Int, nFontType: Int
    ): ByteArray? {
        if (codepage < 0 || codepage > 255 || pszString == null || "" == pszString || pszString.length < 1) {
            return null
        }
        var pbString: ByteArray? = null
        pbString = try {
            pszString.toByteArray(charset(encoding!!))
        } catch (e: UnsupportedEncodingException) {
            return null
        }
        val intToWidth = byteArrayOf(0x00, 0x10, 0x20, 0x30)
        val intToHeight = byteArrayOf(0x00, 0x01, 0x02, 0x03)
        Command.GS_ExclamationMark[2] = (intToWidth[nWidthTimes] + intToHeight[nHeightTimes]).toByte()
        Command.ESC_t[2] = codepage.toByte()
        Command.ESC_M[2] = nFontType.toByte()
        return if (codepage == 0) {
            Other.byteArraysToBytes(
                arrayOf(
                    Command.GS_ExclamationMark, Command.ESC_t, Command.FS_and, Command.ESC_M, pbString
                )
            )
        } else {
            Other.byteArraysToBytes(
                arrayOf(
                    Command.GS_ExclamationMark, Command.ESC_t, Command.FS_dot, Command.ESC_M, pbString
                )
            )
        }
    }

    fun POS_Set_Bold(bold: Int): ByteArray? {
        Command.ESC_E[2] = bold.toByte()
        Command.ESC_G[2] = bold.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_E, Command.ESC_G
            )
        )
    }

    fun POS_Set_LeftBrace(brace: Int): ByteArray? {
        Command.ESC_LeftBrace[2] = brace.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_LeftBrace
            )
        )
    }

    fun POS_Set_UnderLine(line: Int): ByteArray? {
        if (line < 0 || line > 2) return null
        Command.ESC_Minus[2] = line.toByte()
        Command.FS_Minus[2] = line.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_Minus, Command.FS_Minus
            )
        )
    }

    fun POS_Set_FontSize(size1: Int, size2: Int): ByteArray? {
        if ((size1 < 0) or (size1 > 7) or (size2 < 0) or (size2 > 7)) return null
        val intToWidth = byteArrayOf(0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, 0x70)
        val intToHeight = byteArrayOf(0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07)
        Command.GS_ExclamationMark[2] = (intToWidth[size1] + intToHeight[size2]).toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.GS_ExclamationMark
            )
        )
    }

    fun POS_Set_Inverse(inverse: Int): ByteArray? {
        Command.GS_B[2] = inverse.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.GS_B
            )
        )
    }

    fun POS_Set_Rotate(rotate: Int): ByteArray? {
        if (rotate < 0 || rotate > 1) return null
        Command.ESC_V[2] = rotate.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_V
            )
        )
    }

    fun POS_Set_ChoseFont(font: Int): ByteArray? {
        if (font > 1 || font < 0) return null
        Command.ESC_M[2] = font.toByte()
        return Other.byteArraysToBytes(
            arrayOf(
                Command.ESC_M
            )
        )
    }

    fun getBarCommand(
        str: String, nVersion: Int, nErrorCorrectionLevel: Int,
        nMagnification: Int
    ): ByteArray? {
        if ((nVersion < 0) or (nVersion > 19) or (nErrorCorrectionLevel < 0) or (nErrorCorrectionLevel > 3
                    ) or (nMagnification < 1) or (nMagnification > 8)
        ) {
            return null
        }
        var bCodeData: ByteArray? = null
        bCodeData = try {
            str.toByteArray(charset("GBK"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }

        if (bCodeData == null){
            return null
        }
        val command = ByteArray(bCodeData.size + 7)
        command[0] = 27
        command[1] = 90
        command[2] = nVersion.toByte()
        command[3] = nErrorCorrectionLevel.toByte()
        command[4] = nMagnification.toByte()
        command[5] = (bCodeData.size and 0xff).toByte()
        command[6] = (bCodeData.size and 0xff00 shr 8).toByte()
        System.arraycopy(bCodeData, 0, command, 7, bCodeData.size)
        return command
    }

    fun getCodeBarCommand(
        str: String, nType: Int, nWidthX: Int, nHeight: Int,
        nHriFontType: Int, nHriFontPosition: Int
    ): ByteArray? {
        if ((nType < 0x41) or (nType > 0x49) or (nWidthX < 2) or (nWidthX > 6
                    ) or (nHeight < 1) or (nHeight > 255) or (str.isEmpty())
        ) return null
        var bCodeData: ByteArray? = null
        bCodeData = try {
            str.toByteArray(charset("GBK"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }
        if (bCodeData == null){
            return null
        }
        val command = ByteArray(bCodeData.size + 16)
        command[0] = 29
        command[1] = 119
        command[2] = nWidthX.toByte()
        command[3] = 29
        command[4] = 104
        command[5] = nHeight.toByte()
        command[6] = 29
        command[7] = 102
        command[8] = (nHriFontType and 0x01).toByte()
        command[9] = 29
        command[10] = 72
        command[11] = (nHriFontPosition and 0x03).toByte()
        command[12] = 29
        command[13] = 107
        command[14] = nType.toByte()
        command[15] = bCodeData.size.toByte()
        System.arraycopy(bCodeData, 0, command, 16, bCodeData.size)
        return command
    }

    fun POS_Set_Font(str: String, bold: Int, font: Int, widthsize: Int, heigthsize: Int): ByteArray? {
        if ((str.isEmpty()) or (widthsize < 0) or (widthsize > 4) or (heigthsize < 0) or (heigthsize > 4
                    ) or (font < 0) or (font > 1)
        ) return null
        var strData: ByteArray? = null
        strData = try {
            str.toByteArray(charset("GBK"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }
        if (strData == null){
            return null
        }
        val command = ByteArray(strData.size + 9)
        val intToWidth = byteArrayOf(0x00, 0x10, 0x20, 0x30)
        val intToHeight = byteArrayOf(0x00, 0x01, 0x02, 0x03)
        command[0] = 27
        command[1] = 69
        command[2] = bold.toByte()
        command[3] = 27
        command[4] = 77
        command[5] = font.toByte()
        command[6] = 29
        command[7] = 33
        command[8] = (intToWidth[widthsize] + intToHeight[heigthsize]).toByte()
        System.arraycopy(strData, 0, command, 9, strData.size)
        return command
    }


}