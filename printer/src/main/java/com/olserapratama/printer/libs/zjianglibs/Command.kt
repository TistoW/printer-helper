package com.olserapratama.printer.libs.zjianglibs

object Command {
    private val ESC: Byte = 0x1B
    private val FS: Byte = 0x1C
    private val GS: Byte = 0x1D
    private val US: Byte = 0x1F
    private val DLE: Byte = 0x10
    private val DC4: Byte = 0x14
    private val DC1: Byte = 0x11
    private val SP: Byte = 0x20
    private val NL: Byte = 0x0A
    private val FF: Byte = 0x0C
    val PIECE = 0xFF.toByte()
    val NUL = 0x00.toByte()

    var ESC_Init = byteArrayOf(ESC, '@'.code.toByte())

    var LF = byteArrayOf(NL)

    var ESC_J = byteArrayOf(ESC, 'J'.code.toByte(), 0x00)
    var ESC_d = byteArrayOf(ESC, 'd'.code.toByte(), 0x00)

    var US_vt_eot = byteArrayOf(US, DC1, 0x04)

    var ESC_B_m_n = byteArrayOf(ESC, 'B'.code.toByte(), 0x00, 0x00)

    var GS_V_n = byteArrayOf(GS, 'V'.code.toByte(), 0x00)
    var GS_V_m_n = byteArrayOf(GS, 'V'.code.toByte(), 'B'.code.toByte(), 0x00)
    var GS_i = byteArrayOf(ESC, 'i'.code.toByte())
    var GS_m = byteArrayOf(ESC, 'm'.code.toByte())

    var ESC_SP = byteArrayOf(ESC, SP, 0x00)

    var ESC_ExclamationMark = byteArrayOf(ESC, '!'.code.toByte(), 0x00)
    var GS_ExclamationMark = byteArrayOf(GS, '!'.code.toByte(), 0x00)

    var GS_B = byteArrayOf(GS, 'B'.code.toByte(), 0x00)
    var ESC_V = byteArrayOf(ESC, 'V'.code.toByte(), 0x00)
    var ESC_M = byteArrayOf(ESC, 'M'.code.toByte(), 0x00)
    var ESC_G = byteArrayOf(ESC, 'G'.code.toByte(), 0x00)
    var ESC_E = byteArrayOf(ESC, 'E'.code.toByte(), 0x00)

    var ESC_LeftBrace = byteArrayOf(ESC, '{'.code.toByte(), 0x00)

    var ESC_Minus = byteArrayOf(ESC, 45, 0x00)

    var FS_dot = byteArrayOf(FS, 46)

    var FS_and = byteArrayOf(FS, '&'.code.toByte())

    var FS_ExclamationMark = byteArrayOf(FS, '!'.code.toByte(), 0x00)

    var FS_Minus = byteArrayOf(FS, 45, 0x00)

    var FS_S = byteArrayOf(FS, 'S'.code.toByte(), 0x00, 0x00)

    var ESC_t = byteArrayOf(ESC, 't'.code.toByte(), 0x00)

    var ESC_Two = byteArrayOf(ESC, 50)

    var ESC_Three = byteArrayOf(ESC, 51, 0x00)

    var ESC_Align = byteArrayOf(ESC, 'a'.code.toByte(), 0x00)

    var GS_LeftSp = byteArrayOf(GS, 'L'.code.toByte(), 0x00, 0x00)

    var ESC_Relative = byteArrayOf(ESC, '$'.code.toByte(), 0x00, 0x00)

    var ESC_Absolute = byteArrayOf(ESC, 92, 0x00, 0x00)

    var GS_W = byteArrayOf(GS, 'W'.code.toByte(), 0x00, 0x00)

    var DLE_eot = byteArrayOf(DLE, 0x04, 0x00)

    var DLE_DC4 = byteArrayOf(DLE, DC4, 0x00, 0x00, 0x00)

    var ESC_p = byteArrayOf(ESC, 'F'.code.toByte(), 0x00, 0x00, 0x00)

    var GS_H = byteArrayOf(GS, 'H'.code.toByte(), 0x00)

    var GS_h = byteArrayOf(GS, 'h'.code.toByte(), 0xa2.toByte())

    var GS_w = byteArrayOf(GS, 'w'.code.toByte(), 0x00)

    var GS_f = byteArrayOf(GS, 'f'.code.toByte(), 0x00)

    var GS_x = byteArrayOf(GS, 'x'.code.toByte(), 0x00)

    var GS_k = byteArrayOf(GS, 'k'.code.toByte(), 'A'.code.toByte(), FF)

    var GS_k_m_v_r_nL_nH = byteArrayOf(ESC, 'Z'.code.toByte(), 0x03, 0x03, 0x08, 0x00, 0x00)


}