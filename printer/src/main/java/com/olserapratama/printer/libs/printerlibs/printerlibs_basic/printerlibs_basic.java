package com.olserapratama.printer.libs.printerlibs.printerlibs_basic;

import android.graphics.Bitmap;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;

import java.io.ByteArrayOutputStream;

public interface printerlibs_basic extends Library {

    // static interface method need jdk1.8. here we use inner class to avoid this porblem.
    public class GetLibraryPath_Helper {
        // can replaced by absolute path
        public static String GetLibraryPath() {
            // force call JNI_OnLoad
            if (Platform.isAndroid())
                System.loadLibrary("PrinterLibs");
            return "PrinterLibs";
        }
    }
    public static final printerlibs_basic INSTANCE = (printerlibs_basic) Native.loadLibrary(GetLibraryPath_Helper.GetLibraryPath(), printerlibs_basic.class);

    public static final int ComDataBits_4 = 4;
    public static final int ComDataBits_5 = 5;
    public static final int ComDataBits_6 = 6;
    public static final int ComDataBits_7 = 7;
    public static final int ComDataBits_8 = 8;

    public static final int ComParity_NoParity = 0;
    public static final int ComParity_OddParity = 1;
    public static final int ComParity_EvenParity = 2;
    public static final int ComParity_MarkParity = 3;
    public static final int ComParity_SpaceParity = 4;

    public static final int ComStopBits_One = 0;
    public static final int ComStopBits_OnePointFive = 1;
    public static final int ComStopBits_Two = 2;

    public static final int ComFlowControl_None = 0;
    public static final int ComFlowControl_XonXoff = 1;
    public static final int ComFlowControl_RtsCts = 2;
    public static final int ComFlowControl_DtrDsr = 3;

    public static final int PL_CHECKRESULT_SUCCESS_ISKNOWNPRINTER = 0;
    public static final int PL_CHECKRESULT_FAILED_PORT_CLOSED = -1;
    public static final int PL_CHECKRESULT_FAILED_PORT_WRITEFAILED = -2;
    public static final int PL_CHECKRESULT_FAILED_PORT_READFAILED = -3;
    public static final int PL_CHECKRESULT_FAILED_PRINTER_NORESPONSE = -4;
    public static final int PL_CHECKRESULT_SUCCESS_UNKNOWNPRINTER = -104;
    public static final int PL_CHECKRESULT_FAILED_OTHER_RERROR = -101;

    public static final int PL_QUERYBUFFER_EMTPY = 0;
    public static final int PL_QUERYBUFFER_FAILED_PORT_CLOSED = -1;
    public static final int PL_QUERYBUFFER_FAILED_PORT_WRITEFAILED = -2;
    public static final int PL_QUERYBUFFER_FAILED_PORT_READFAILED = -3;
    public static final int PL_QUERYBUFFER_NOT_EMPTY = -4;

    public static final int PL_PRINTRESULT_SUCCESS = 0;
    public static final int PL_PRINTRESULT_PORT_CLOSED = -1;
    public static final int PL_PRINTRESULT_PORT_WRITEFAILED = -2;
    public static final int PL_PRINTRESULT_PORT_READFAILED = -3;
    public static final int PL_PRINTRESULT_PRINTER_OFFLINE = -102;
    public static final int PL_PRINTRESULT_PRINTER_NOPAPER = -103;
    public static final int PL_PRINTRESULT_OTHER_RERROR = -101;

    public class PL_PRINTERSTATUS_Helper {
        public static boolean PL_PRINTERSTATUS_DRAWER_OPENED(long status) { return (((status >> 0) & 0x04) == 0x00); };
        public static boolean PL_PRINTERSTATUS_OFFLINE(long status) { return (((status >> 0) & 0x08) == 0x08); };
        public static boolean PL_PRINTERSTATUS_COVERUP(long status) { return (((status >> 8) & 0x04) == 0x04); };
        public static boolean PL_PRINTERSTATUS_FEED_PRESSED(long status) { return (((status >> 8) & 0x08) == 0x08); };
        public static boolean PL_PRINTERSTATUS_NOPAPER(long status) { return (((status >> 8) & 0x20) == 0x20); };
        public static boolean PL_PRINTERSTATUS_ERROR_OCCURED(long status) { return (((status >> 8) & 0x40) == 0x40); };
        public static boolean PL_PRINTERSTATUS_CUTTER_ERROR(long status) { return (((status >> 16) & 0x08) == 0x08); };
        public static boolean PL_PRINTERSTATUS_UNRECOVERABLE_ERROR(long status) { return (((status >> 16) & 0x20) == 0x20); };
        public static boolean PL_PRINTERSTATUS_DEGREE_OR_VOLTAGE_OVERRANGE(long status) { return (((status >> 16) & 0x40) == 0x40); };
        public static boolean PL_PRINTERSTATUS_PAPER_NEAREND(long status) { return (((status >> 24) & 0x0C) == 0x0C); };
        public static boolean PL_PRINTERSTATUS_PAPER_TAKEOUT(long status) { return (((status >> 24) & 0x04) == 0x04); };
    }

    public static final int PrintDensity_Light = 0;
    public static final int PrintDensity_Normal = 1;
    public static final int PrintDensity_Dark = 2;

    public static final int PosAlignment_Left = 0;
    public static final int PosAlignment_HCenter = 1;
    public static final int PosAlignment_Right = 2;

    public static final int DrawAlignment_Left = -1;
    public static final int DrawAlignment_HCenter = -2;
    public static final int DrawAlignment_Right = -3;
    public static final int DrawAlignment_Top = -1;
    public static final int DrawAlignment_VCenter = -2;
    public static final int DrawAlignment_Bottom = -3;

    public static final int PageModeDrawDirection_LeftToRight = 0;
    public static final int PageModeDrawDirection_BottomToTop = 1;
    public static final int PageModeDrawDirection_RightToLeft = 2;
    public static final int PageModeDrawDirection_TopToBottom = 3;

    public static final int MultiByteModeEncoding_GBK = 0;
    public static final int MultiByteModeEncoding_UTF8 = 1;
    public static final int MultiByteModeEncoding_BIG5 = 3;
    public static final int MultiByteModeEncoding_ShiftJIS = 4;
    public static final int MultiByteModeEncoding_EUCKR = 5;

    public static final int AsciiTextFontType_A = 0;
    public static final int AsciiTextFontType_B = 1;
    public static final int AsciiTextFontType_C = 2;
    public static final int AsciiTextFontType_D = 3;
    public static final int AsciiTextFontType_E = 4;

    public static final int TextUnderline_None = 0;
    public static final int TextUnderline_One = 1;
    public static final int TextUnderline_Two = 2;

    public static final int CharacterSet_USA = 0;
    public static final int CharacterSet_FRANCE = 1;
    public static final int CharacterSet_GERMANY = 2;
    public static final int CharacterSet_UK = 3;
    public static final int CharacterSet_DENMARK_I = 4;
    public static final int CharacterSet_SWEDEN = 5;
    public static final int CharacterSet_ITALY = 6;
    public static final int CharacterSet_SPAIN_I = 7;
    public static final int CharacterSet_JAPAN = 8;
    public static final int CharacterSet_NORWAY = 9;
    public static final int CharacterSet_DENMARK_II = 10;
    public static final int CharacterSet_SPAIN_II = 11;
    public static final int CharacterSet_LATIN = 12;
    public static final int CharacterSet_KOREA = 13;
    public static final int CharacterSet_SLOVENIA = 14;
    public static final int CharacterSet_CHINA = 15;

    public static final int CharacterCodepage_CP437 = 0;
    public static final int CharacterCodepage_KATAKANA = 1;
    public static final int CharacterCodepage_CP850 = 2;
    public static final int CharacterCodepage_CP860 = 3;
    public static final int CharacterCodepage_CP863 = 4;
    public static final int CharacterCodepage_CP865 = 5;
    public static final int CharacterCodepage_WCP1251 = 6;
    public static final int CharacterCodepage_CP866 = 7;
    public static final int CharacterCodepage_MIK = 8;
    public static final int CharacterCodepage_CP755 = 9;
    public static final int CharacterCodepage_IRAN = 10;
    public static final int CharacterCodepage_CP862 = 15;
    public static final int CharacterCodepage_WCP1252 = 16;
    public static final int CharacterCodepage_WCP1253 = 17;
    public static final int CharacterCodepage_CP852 = 18;
    public static final int CharacterCodepage_CP858 = 19;
    public static final int CharacterCodepage_IRAN_II = 20;
    public static final int CharacterCodepage_LATVIAN = 21;
    public static final int CharacterCodepage_CP864 = 22;
    public static final int CharacterCodepage_ISO_8859_1 = 23;
    public static final int CharacterCodepage_CP737 = 24;
    public static final int CharacterCodepage_WCP1257 = 25;
    public static final int CharacterCodepage_THAI = 26;
    public static final int CharacterCodepage_CP720 = 27;
    public static final int CharacterCodepage_CP855 = 28;
    public static final int CharacterCodepage_CP857 = 29;
    public static final int CharacterCodepage_WCP1250 = 30;
    public static final int CharacterCodepage_CP775 = 31;
    public static final int CharacterCodepage_WCP1254 = 32;
    public static final int CharacterCodepage_WCP1255 = 33;
    public static final int CharacterCodepage_WCP1256 = 34;
    public static final int CharacterCodepage_WCP1258 = 35;
    public static final int CharacterCodepage_ISO_8859_2 = 36;
    public static final int CharacterCodepage_ISO_8859_3 = 37;
    public static final int CharacterCodepage_ISO_8859_4 = 38;
    public static final int CharacterCodepage_ISO_8859_5 = 39;
    public static final int CharacterCodepage_ISO_8859_6 = 40;
    public static final int CharacterCodepage_ISO_8859_7 = 41;
    public static final int CharacterCodepage_ISO_8859_8 = 42;
    public static final int CharacterCodepage_ISO_8859_9 = 43;
    public static final int CharacterCodepage_ISO_8859_15 = 44;
    public static final int CharacterCodepage_THAI_2 = 45;
    public static final int CharacterCodepage_CP856 = 46;
    public static final int CharacterCodepage_CP874 = 47;
    public static final int CharacterCodepage_TCVN3 = 48;

    public static final int BarcodeReadableTextFontType_Standard = 0;
    public static final int BarcodeReadableTextFontType_Small = 1;

    public static final int BarcodeReadableTextPosition_None = 0;
    public static final int BarcodeReadableTextPosition_AboveBarcode = 1;
    public static final int BarcodeReadableTextPosition_BelowBarcode = 2;
    public static final int BarcodeReadableTextPosition_AboveAndBelowBarcode = 3;

    public static final int PosBarcodeType_UPCA = 0x41;
    public static final int PosBarcodeType_UPCE = 0x42;
    public static final int PosBarcodeType_EAN13 = 0x43;
    public static final int PosBarcodeType_EAN8 = 0x44;
    public static final int PosBarcodeType_CODE39 = 0x45;
    public static final int PosBarcodeType_ITF = 0x46;
    public static final int PosBarcodeType_CODEBAR = 0x47;
    public static final int PosBarcodeType_CODE93 = 0x48;
    public static final int PosBarcodeType_CODE128 = 0x49;

    public static final int QRCodeECC_L = 1;
    public static final int QRCodeECC_M = 2;
    public static final int QRCodeECC_Q = 3;
    public static final int QRCodeECC_H = 4;

    public static final int ImagePixelsFormat_MONO = 1;
    public static final int ImagePixelsFormat_MONOLSB = 2;
    public static final int ImagePixelsFormat_GRAY8 = 3;
    public static final int ImagePixelsFormat_BYTEORDERED_RGB24 = 4;
    public static final int ImagePixelsFormat_BYTEORDERED_BGR24 = 5;
    public static final int ImagePixelsFormat_BYTEORDERED_ARGB32 = 6;
    public static final int ImagePixelsFormat_BYTEORDERED_RGBA32 = 7;
    public static final int ImagePixelsFormat_BYTEORDERED_ABGR32 = 8;
    public static final int ImagePixelsFormat_BYTEORDERED_BGRA32 = 9;

    public static final int ImageBinarizationMethod_Dithering = 0;
    public static final int ImageBinarizationMethod_Thresholding = 1;

    public static final int ImageCompressionMethod_None = 0;
    public static final int ImageCompressionMethod_Level1 = 1;
    public static final int ImageCompressionMethod_Level2 = 2;

    public static final int LabelRotation_0 = 0;
    public static final int LabelRotation_90 = 1;
    public static final int LabelRotation_180 = 2;
    public static final int LabelRotation_270 = 3;

    public static final int PL_LABEL_TEXT_STYLE_BOLD = (1<<0);
    public static final int PL_LABEL_TEXT_STYLE_UNDERLINE = (1<<1);
    public static final int PL_LABEL_TEXT_STYLE_HIGHLIGHT = (1<<2);
    public static final int PL_LABEL_TEXT_STYLE_STRIKETHROUGH = (1<<3);
    public static final int PL_LABEL_TEXT_STYLE_ROTATION_0 = (0<<4);
    public static final int PL_LABEL_TEXT_STYLE_ROTATION_90 = (1<<4);
    public static final int PL_LABEL_TEXT_STYLE_ROTATION_180 = (2<<4);
    public static final int PL_LABEL_TEXT_STYLE_ROTATION_270 = (3<<4);
    public class PL_LABEL_TEXT_STYLE_Helper {
        public static int PL_LABEL_TEXT_STYLE_WIDTH_ENLARGEMENT(int n) { return ((n)<<8); };
        public static int PL_LABEL_TEXT_STYLE_HEIGHT_ENLARGEMENT(int n) { return ((n)<<12); };
    }

    public static final int LabelBarcodeType_UPCA = 0;
    public static final int LabelBarcodeType_UPCE = 1;
    public static final int LabelBarcodeType_EAN13 = 2;
    public static final int LabelBarcodeType_EAN8 = 3;
    public static final int LabelBarcodeType_CODE39 = 4;
    public static final int LabelBarcodeType_ITF = 5;
    public static final int LabelBarcodeType_CODEBAR = 6;
    public static final int LabelBarcodeType_CODE93 = 7;
    public static final int LabelBarcodeType_CODE128 = 8;
    public static final int LabelBarcodeType_CODE11 = 9;
    public static final int LabelBarcodeType_MSI = 10;
    public static final int LabelBarcodeType_128M = 11;
    public static final int LabelBarcodeType_EAN128 = 12;
    public static final int LabelBarcodeType_25C = 13;
    public static final int LabelBarcodeType_39C = 14;
    public static final int LabelBarcodeType_39 = 15;
    public static final int LabelBarcodeType_EAN13PLUS2 = 16;
    public static final int LabelBarcodeType_EAN13PLUS5 = 17;
    public static final int LabelBarcodeType_EAN8PLUS2 = 18;
    public static final int LabelBarcodeType_EAN8PLUS5 = 19;
    public static final int LabelBarcodeType_POST = 20;
    public static final int LabelBarcodeType_UPCAPLUS2 = 21;
    public static final int LabelBarcodeType_UPCAPLUS5 = 22;
    public static final int LabelBarcodeType_UPCEPLUS2 = 23;
    public static final int LabelBarcodeType_UPCEPLUS5 = 24;
    public static final int LabelBarcodeType_CPOST = 25;
    public static final int LabelBarcodeType_MSIC = 26;
    public static final int LabelBarcodeType_PLESSEY = 27;
    public static final int LabelBarcodeType_ITF14 = 28;
    public static final int LabelBarcodeType_EAN14 = 29;

    public static final int LabelColor_White = 0;
    public static final int LabelColor_Black = 1;

    public String Pos_LibraryVersion();

    public int Pos_EnumComA(byte[] pBuf, int cbBuf, IntByReference pcbNeeded);
    //public int Pos_EnumComW(byte[] pBuf, int cbBuf, IntByReference pcbNeeded);
    public class Pos_EnumCom_Helper {
        public static String[] Pos_EnumComA() {
            IntByReference pcbNeeded = new IntByReference();
            INSTANCE.Pos_EnumComA(null, 0, pcbNeeded);
            if (pcbNeeded.getValue() > 0) {
                byte[] pBuf = new byte[pcbNeeded.getValue()];
                if (pBuf != null) {
                    INSTANCE.Pos_EnumComA(pBuf, pBuf.length, null);
                    String s = new String(pBuf);
                    String[] ss = s.split("\0");
                    return ss;
                }
            }
            return null;
        }
    }

    public int Pos_EnumUsbVidPidA(byte[] pBuf, int cbBuf, IntByReference pcbNeeded);
    //public int Pos_EnumUsbVidPidW(byte[] pBuf, int cbBuf, IntByReference pcbNeeded);
    public class Pos_EnumUsbVidPid_Helper {
        public static String[] Pos_EnumUsbVidPidA() {
            IntByReference pcbNeeded = new IntByReference();
            INSTANCE.Pos_EnumUsbVidPidA(null, 0, pcbNeeded);
            if (pcbNeeded.getValue() > 0) {
                byte[] pBuf = new byte[pcbNeeded.getValue()];
                if (pBuf != null) {
                    INSTANCE.Pos_EnumUsbVidPidA(pBuf, pBuf.length, null);
                    String s = new String(pBuf);
                    String[] ss = s.split("\0");
                    return ss;
                }
            }
            return null;
        }
    }

    interface on_netprinter_discovered_a_callback extends Callback {
        void on_netprinter_discovered_a(String local_ip, String discovered_mac, String discovered_ip, String discovered_name, Pointer private_data);
    }
    public void Pos_EnumNetPrinterA(int timeout, IntByReference cancel, on_netprinter_discovered_a_callback callback, Pointer data);

    interface on_btdevice_discovered_a_callback extends Callback {
        void on_btdevice_discovered_a(String device_name, String device_address, Pointer private_data);
    }
    public void Pos_EnumBtDeviceA(int timeout, IntByReference cancel, on_btdevice_discovered_a_callback callback, Pointer data);

    public void Pos_EnumBleDeviceA(int timeout, IntByReference cancel, on_btdevice_discovered_a_callback callback, Pointer data);

    public Pointer Pos_OpenComA(String name, int baudrate, int databits, int parity, int stopbits, int flowcontrol);
    public Pointer Pos_OpenComW(WString name, int baudrate, int databits, int parity, int stopbits, int flowcontrol);

    public Pointer Pos_OpenTcpA(String ip, short port, int timeout);
    public Pointer Pos_OpenTcpW(WString ip, short port, int timeout);

    public Pointer Pos_OpenTcpBindInterfaceA(String ip, short port, String bind_local_addr, int timeout);
    public Pointer Pos_OpenTcpBindInterfaceW(WString ip, short port, WString bind_local_addr, int timeout);

    public Pointer Pos_OpenUsbVidPid(short vid, short pid);
    public Pointer Pos_OpenUsbVidPidStringA(String name);
    public Pointer Pos_OpenUsbVidPidStringW(WString name);

    public Pointer Pos_OpenBT2ByConnectA(String address);
    public Pointer Pos_OpenBT2ByConnectW(WString address);

    public Pointer Pos_OpenBT2ByListenA(int timeout, byte[] address);

    public Pointer Pos_OpenBT4ByConnectA(String address);
    public Pointer Pos_OpenBT4ByConnectW(WString address);

    public Pointer Pos_OpenFileNewA(String name);
    public Pointer Pos_OpenFileNewW(WString name);

    public Pointer Pos_OpenFileAppendA(String name);
    public Pointer Pos_OpenFileAppendW(WString name);

    public Pointer Pos_OpenMemory(int nMemorySpaceSize);

    public Pointer Pos_MemoryData(Pointer handle);
    public class Pos_MemoryData_Helper {
        public static byte[] Pos_MemoryByteArray(Pointer handle) {
            Pointer pdata = INSTANCE.Pos_MemoryData(handle);
            int data_size = INSTANCE.Pos_MemoryDataLength(handle);
            if (pdata != Pointer.NULL) {
                byte[] buffer = pdata.getByteArray(0, data_size);
                return buffer;
            }
            return null;
        }
    }

    public int Pos_MemoryDataLength(Pointer handle);

    public void Pos_ClearMemoryData(Pointer handle);

    interface on_bytes_writed_callback extends Callback {
        // callback not support byte[]
        void on_bytes_writed(Pointer buffer, int count, Pointer private_data);
    }
    public int Pos_SetWritedEvent(Pointer handle, on_bytes_writed_callback callback, Pointer private_data);

    interface on_bytes_readed_callback extends Callback {
        // callback not support byte[]
        void on_bytes_readed(Pointer buffer, int count, Pointer private_data);
    }
    public int Pos_SetReadedEvent(Pointer handle, on_bytes_readed_callback callback, Pointer private_data);

    interface on_port_closed_callback extends Callback {
        void on_port_closed(Pointer private_data);
    }
    public int Pos_SetClosedEvent(Pointer handle, on_port_closed_callback callback, Pointer private_data);

    public int Pos_Write(Pointer handle, byte[] buffer, int count, int timeout);

    public int Pos_Read(Pointer handle, byte[] buffer, int count, int timeout);

    public int Pos_ReadUntilByte(Pointer handle, byte[] buffer, int count, int timeout, byte breakByte);

    public void Pos_SkipAvailable(Pointer handle);

    public void Pos_FlushBuffer(Pointer handle);

    public void Pos_Close(Pointer handle);

    public int Pos_CheckPrinter(Pointer handle, int timeout);

    public int Pos_QueryPrinterBufferEmpty(Pointer handle, int timeout);

    public int Pos_QueryPrinterStatus(Pointer handle, int timeout);

    public int Pos_QueryPrintResult(Pointer handle, int timeout);

    public int Pos_KickOutDrawer(Pointer handle, int nDrawerIndex, int nHighLevelTime, int nLowLevelTime);

    public int Pos_Beep(Pointer handle, int nBeepCount, int nBeepMs);

    public int Pos_FeedAndHalfCutPaper(Pointer handle);

    public int Pos_FullCutPaper(Pointer handle);

    public int Pos_HalfCutPaper(Pointer handle);

    public int Pos_ResetPrinter(Pointer handle);

    public int Pos_SetPrinter(Pointer handle, int setType, byte[] buffer, int count);

    public int Pos_SetPrintSpeed(Pointer handle, int nSpeed);

    public int Pos_SetPrintDensity(Pointer handle, int nDensity);

    public int Pos_SetPrintHeatPara(Pointer handle, int nMaxHeatDots, int nHeatOnTime, int nHeatOffTime);

    public int Pos_PrintSelfTestPage(Pointer handle);

    public int Pos_SetMovementUnit(Pointer handle, int nHorizontalMovementUnit, int nVerticalMovementUnit);

    public int Pos_SetPrintAreaLeftMargin(Pointer handle, int nLeftMargin);

    public int Pos_SetPrintAreaWidth(Pointer handle, int nWidth);

    public int Pos_SelectPageMode(Pointer handle);

    public int Pos_SelectPageModeEx(Pointer handle, int nHorizontalMovementUnit, int nVerticalMovementUnit, int x, int y, int width, int height);

    public int Pos_ExitPageMode(Pointer handle);

    public int Pos_PrintPage(Pointer handle);

    public int Pos_ClearPage(Pointer handle);

    public int Pos_SetPageArea(Pointer handle, int x, int y, int width, int height);

    public int Pos_SetPageModeDrawDirection(Pointer handle, int nDirection);

    public int Pos_SetHorizontalAbsolutePrintPosition(Pointer handle, int nPosition);

    public int Pos_SetHorizontalRelativePrintPosition(Pointer handle, int nPosition);

    public int Pos_SetVerticalAbsolutePrintPosition(Pointer handle, int nPosition);

    public int Pos_SetVerticalRelativePrintPosition(Pointer handle, int nPosition);

    public int Pos_SetAlignment(Pointer handle, int nAlignment);

    public int Pos_FeedLine(Pointer handle, int numLines);

    public int Pos_FeedDot(Pointer handle, int numDots);

    public int Pos_PrintTextA(Pointer handle, String str);

    public int Pos_PrintTextInUTF8W(Pointer handle, WString str);

    public int Pos_PrintTextInGBKW(Pointer handle, WString str);

    public int Pos_PrintTextInBIG5W(Pointer handle, WString str);

    public int Pos_PrintTextInShiftJISW(Pointer handle, WString str);

    public int Pos_PrintTextInEUCKRW(Pointer handle, WString str);

    public int Pos_SetTextScale(Pointer handle, int nWidthScale, int nHeightScale);

    public int Pos_SetAsciiTextFontType(Pointer handle, int nFontType);

    public int Pos_SetTextBold(Pointer handle, int nBold);

    public int Pos_SetTextUnderline(Pointer handle, int nUnderline);

    public int Pos_SetTextUpsideDown(Pointer handle, int nUpsideDown);

    public int Pos_SetTextWhiteOnBlack(Pointer handle, int nWhiteOnBlack);

    public int Pos_SetTextRotate(Pointer handle, int nRotate);

    public int Pos_SetTextLineHeight(Pointer handle, int nLineHeight);

    public int Pos_SetAsciiTextCharRightSpacing(Pointer handle, int nSpacing);

    public int Pos_SetKanjiTextCharSpacing(Pointer handle, int nLeftSpacing, int nRightSpacing);

    public int Pos_SetSingleByteMode(Pointer handle);

    public int Pos_SetCharacterSet(Pointer handle, int nCharacterSet);

    public int Pos_SetCharacterCodepage(Pointer handle, int nCharacterCodepage);

    public int Pos_SetMultiByteMode(Pointer handle);

    public int Pos_SetMultiByteEncoding(Pointer handle, int nEncoding);

    public int Pos_SetUserCharacterPatternFromFileA(Pointer handle, byte ch, String pszFile);
    public int Pos_SetUserCharacterPatternFromFileW(Pointer handle, byte ch, WString pszFile);

    public int Pos_SetUserCharacterPatternFromData(Pointer handle, byte ch, byte[] data, int data_size);
    public class Pos_SetUserCharacterPattern_Helper {
        public static int Pos_SetUserCharacterPatternFromBitmap(Pointer handle, byte ch, Bitmap bitmap) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_SetUserCharacterPatternFromData(handle, ch, data, data.length);
            }
            return result;
        }
    }

    public int Pos_SetUserCharacterPatternFromPixels(Pointer handle, byte ch, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format);

    public int Pos_ClearUserCharacterPattern(Pointer handle, byte ch);

    public int Pos_SetUserCharacterEnable(Pointer handle, int enable);

    public int Pos_SetUserKanjiPatternFromFileA(Pointer handle, byte c1, byte c2, String pszFile);
    public int Pos_SetUserKanjiPatternFromFileW(Pointer handle, byte c1, byte c2, WString pszFile);

    public int Pos_SetUserKanjiPatternFromData(Pointer handle, byte c1, byte c2, byte[] data, int data_size);
    public class Pos_SetUserKanjiPattern_Helper {
        public static int Pos_SetUserKanjiPatternFromBitmap(Pointer handle, byte c1, byte c2, Bitmap bitmap) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_SetUserKanjiPatternFromData(handle, c1, c2, data, data.length);
            }
            return result;
        }
    }

    public int Pos_SetUserKanjiPatternFromPixels(Pointer handle, byte c1, byte c2, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format);

    public int Pos_PrintBarcodeA(Pointer handle, int nBarcodeType, String str);
    public int Pos_PrintBarcodeW(Pointer handle, int nBarcodeType, WString str);

    public int Pos_SetBarcodeUnitWidth(Pointer handle, int nBarcodeUnitWidth);

    public int Pos_SetBarcodeHeight(Pointer handle, int nBarcodeHeight);

    public int Pos_SetBarcodeReadableTextFontType(Pointer handle, int nFontType);

    public int Pos_SetBarcodeReadableTextPosition(Pointer handle, int nTextPosition);

    public int Pos_PrintQRCodeA(Pointer handle, int nVersion, int nECCLevel, String str);
    public int Pos_PrintQRCodeW(Pointer handle, int nVersion, int nECCLevel, WString str);

    public int Pos_PrintQRCodeUseEpsonCmdA(Pointer handle, int nQRCodeUnitWidth, int nECCLevel, String str);
    public int Pos_PrintQRCodeUseEpsonCmdW(Pointer handle, int nQRCodeUnitWidth, int nECCLevel, WString str);

    public int Pos_PrintDoubleQRCodeA(Pointer handle, int nQRCodeUnitWidth, int nQR1Position, int nQR1Version, int nQR1ECCLevel, String strQR1, int nQR2Position, int nQR2Version, int nQR2ECCLevel, String strQR2);
    public int Pos_PrintDoubleQRCodeW(Pointer handle, int nQRCodeUnitWidth, int nQR1Position, int nQR1Version, int nQR1ECCLevel, WString strQR1, int nQR2Position, int nQR2Version, int nQR2ECCLevel, WString strQR2);

    public int Pos_PrintPDF417BarcodeUseEpsonCmdA(Pointer handle, int columnCount, int rowCount, int unitWidth, int rowHeight, int nECCLevel, int dataProcessingMode, String str);
    public int Pos_PrintPDF417BarcodeUseEpsonCmdW(Pointer handle, int columnCount, int rowCount, int unitWidth, int rowHeight, int nECCLevel, int dataProcessingMode, WString str);

    public int Pos_GetImageSizeFromFileA(String pszFile, IntByReference depth, IntByReference width, IntByReference height);
    public int Pos_GetImageSizeFromFileW(WString pszFile, IntByReference depth, IntByReference width, IntByReference height);

    public int Pos_GetImageSizeFromData(byte[] data, int data_size, IntByReference depth, IntByReference width, IntByReference height);

    public int Pos_PrintRasterImageFromFileA(Pointer handle, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int Pos_PrintRasterImageFromFileW(Pointer handle, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int Pos_PrintRasterImageFromData(Pointer handle, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class Pos_PrintRasterImage_Helper {
        public static int Pos_PrintRasterImageFromBitmap(Pointer handle, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_PrintRasterImageFromData(handle, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Pos_PrintRasterImageFromPixels(Pointer handle, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format, int binaryzation_method);

    public int Pos_PrintRasterImageWithCompressFromFileA(Pointer handle, int dstw, int dsth, String pszFile, int binaryzation_method, int compress_method);
    public int Pos_PrintRasterImageWithCompressFromFileW(Pointer handle, int dstw, int dsth, WString pszFile, int binaryzation_method, int compress_method);

    public int Pos_PrintRasterImageWithCompressFromData(Pointer handle, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method, int compress_method);
    public class Pos_PrintRasterImageWithCompress_Helper {
        public static int Pos_PrintRasterImageWithCompressFromBitmap(Pointer handle, int dstw, int dsth, Bitmap bitmap, int binaryzation_method, int compress_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_PrintRasterImageWithCompressFromData(handle, dstw, dsth, data, data.length, binaryzation_method, compress_method);
            }
            return result;
        }
    }

    public int Pos_PrintRasterImageWithCompressFromPixels(Pointer handle, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format, int binaryzation_method, int compress_method);

    public int Pos_FillDotImageToCurrentLineFromFileA(Pointer handle, String pszFile, int binaryzation_method);
    public int Pos_FillDotImageToCurrentLineFromFileW(Pointer handle, WString pszFile, int binaryzation_method);

    public int Pos_FillDotImageToCurrentLineFromData(Pointer handle, byte[] data, int data_size, int binaryzation_method);
    public class Pos_FillDotImageToCurrentLine_Helper {
        public static int Pos_FillDotImageToCurrentLineFromBitmap(Pointer handle, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_FillDotImageToCurrentLineFromData(handle, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Pos_FillDotImageToCurrentLineFromPixels(Pointer handle, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format, int binaryzation_method);

    public int Pos_PrintDotImageSpecifyHorizontalPositionFromFileA(Pointer handle, int nPosition, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int Pos_PrintDotImageSpecifyHorizontalPositionFromFileW(Pointer handle, int nPosition, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int Pos_PrintDotImageSpecifyHorizontalPositionFromData(Pointer handle, int nPosition, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class Pos_PrintDotImageSpecifyHorizontalPosition_Helper {
        public static int Pos_PrintDotImageSpecifyHorizontalPositionFromBitmap(Pointer handle, int nPosition, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_PrintDotImageSpecifyHorizontalPositionFromData(handle, nPosition, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Pos_PrintDotImageSpecifyHorizontalPositionFromPixels(Pointer handle, int nPosition, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format, int binaryzation_method);

    public int Pos_SetNVImageFromFilesA(Pointer handle, int count, String[] pszFiles);
    public int Pos_SetNVImageFromFilesW(Pointer handle, int count, WString[] pszFiles);

    //current not support pass byte[][]
    //public int Pos_SetNVImageFromDatas(Pointer handle, int count, byte[][] pdata, int[] pdata_size);

    public int Pos_PrintNVImage(Pointer handle, int no);

    public int Pos_ClearNVImage(Pointer handle);

    public int Pos_SetRAMImageFromFileA(Pointer handle, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int Pos_SetRAMImageFromFileW(Pointer handle, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int Pos_SetRAMImageFromData(Pointer handle, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class Pos_SetRAMImage_Helper {
        public static int Pos_SetRAMImageFromBitmap(Pointer handle, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_SetRAMImageFromData(handle, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Pos_SetRAMImageFromPixels(Pointer handle, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format, int binaryzation_method);

    public int Pos_PrintRAMImage(Pointer handle);

    public int Pos_PrintHorizontalLine(Pointer handle, int nLineStartPosition, int nLineEndPosition);

    public int Pos_PrintHorizontalLineSpecifyThickness(Pointer handle, int nLineStartPosition, int nLineEndPosition, int nLineThickness);

    public int Pos_PrintMultipleHorizontalLinesAtOneRow(Pointer handle, int nLineCount, int[] pLineStartPosition, int[] pLineEndPosition);

    public int Pos_PrintBitRasterImageUseDC2StarCmdFromFileA(Pointer handle, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int Pos_PrintBitRasterImageUseDC2StarCmdFromFileW(Pointer handle, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int Pos_PrintBitRasterImageUseDC2StarCmdFromData(Pointer handle, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class Pos_PrintBitRasterImageUseDC2StarCmd_Helper {
        public static int Pos_PrintBitRasterImageUseDC2StarCmdFromBitmap(Pointer handle, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_PrintBitRasterImageUseDC2StarCmdFromData(handle, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Pos_PrintMsbRasterImageUseDC2VCmdFromFileA(Pointer handle, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int Pos_PrintMsbRasterImageUseDC2VCmdFromFileW(Pointer handle, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int Pos_PrintMsbRasterImageUseDC2VCmdFromData(Pointer handle, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class Pos_PrintMsbRasterImageUseDC2VCmd_Helper {
        public static int Pos_PrintMsbRasterImageUseDC2VCmdFromBitmap(Pointer handle, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_PrintMsbRasterImageUseDC2VCmdFromData(handle, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Pos_PrintLsbRasterImageUseDC2vCmdFromFileA(Pointer handle, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int Pos_PrintLsbRasterImageUseDC2vCmdFromFileW(Pointer handle, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int Pos_PrintLsbRasterImageUseDC2vCmdFromData(Pointer handle, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class Pos_PrintLsbRasterImageUseDC2vCmd_Helper {
        public static int Pos_PrintLsbRasterImageUseDC2vCmdFromBitmap(Pointer handle, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_PrintLsbRasterImageUseDC2vCmdFromData(handle, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Pos_PrintEpsonTM88IVImageUseGS8CmdFromFileA(Pointer handle, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int Pos_PrintEpsonTM88IVImageUseGS8CmdFromFileW(Pointer handle, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int Pos_PrintEpsonTM88IVImageUseGS8CmdFromData(Pointer handle, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class Pos_PrintEpsonTM88IVImageUseGS8Cmd_Helper {
        public static int Pos_PrintEpsonTM88IVImageUseGS8CmdFromBitmap(Pointer handle, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Pos_PrintEpsonTM88IVImageUseGS8CmdFromData(handle, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Pos_PrintEpsonTM88IVImageUseGS8CmdFromPixels(Pointer handle, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format, int binaryzation_method);

    public int Pos_EnableBlackMarkMode(Pointer handle);

    public int Pos_DisableBlackMarkMode(Pointer handle);

    public int Pos_SetBlackMarkMaxFindLength(Pointer handle, int maxFindLength);

    public int Pos_SetBlackMarkMaxFindLengthRuntime(Pointer handle, int maxFindLength);

    public int Pos_FindBlackMark(Pointer handle);

    public int Pos_SetBlackMarkPaperPrintPosition(Pointer handle, int position);

    public int Pos_SetBlackMarkPaperCutPosition(Pointer handle, int position);

    public int Pos_FullCutBlackMarkPaper(Pointer handle);

    public int Pos_HalfCutBlackMarkPaper(Pointer handle);

    public int Page_DrawRect(Pointer handle, int x, int y, int width, int height, int color);

    public int Page_DrawBox(Pointer handle, int x, int y, int width, int height, int borderwidth, int bordercolor);

    public int Page_DrawTextSpecifyPositionA(Pointer handle, int x, int y, String str);

    public int Page_DrawTextSpecifyPositionInUTF8W(Pointer handle, int x, int y, WString str);

    public int Page_DrawTextSpecifyPositionInGBKW(Pointer handle, int x, int y, WString str);

    public int Page_DrawTextSpecifyPositionInBIG5W(Pointer handle, int x, int y, WString str);

    public int Page_DrawTextSpecifyPositionInShiftJISW(Pointer handle, int x, int y, WString str);

    public int Page_DrawTextSpecifyPositionInEUCKRW(Pointer handle, int x, int y, WString str);

    public int Page_DrawBarcodeSpecifyPositionA(Pointer handle, int x, int y, int nBarcodeType, String str);
    public int Page_DrawBarcodeSpecifyPositionW(Pointer handle, int x, int y, int nBarcodeType, WString str);

    public int Page_DrawQRCodeSpecifyPositionA(Pointer handle, int x, int y, int nVersion, int nECCLevel, String str);
    public int Page_DrawQRCodeSpecifyPositionW(Pointer handle, int x, int y, int nVersion, int nECCLevel, WString str);

    public int Page_DrawQRCodeUseEpsonCmdSpecifyPositionA(Pointer handle, int x, int y, int nQRCodeUnitWidth, int nECCLevel, String str);
    public int Page_DrawQRCodeUseEpsonCmdSpecifyPositionW(Pointer handle, int x, int y, int nQRCodeUnitWidth, int nECCLevel, WString str);

    public int Page_DrawImageSpecifyPositionFromFileA(Pointer handle, int x, int y, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int Page_DrawImageSpecifyPositionFromFileW(Pointer handle, int x, int y, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int Page_DrawImageSpecifyPositionFromData(Pointer handle, int x, int y, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class Page_DrawImageSpecifyPosition_Helper {
        public static int Page_DrawImageSpecifyPositionFromBitmap(Pointer handle, int x, int y, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Page_DrawImageSpecifyPositionFromData(handle, x, y, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Page_DrawImageSpecifyPositionFromPixels(Pointer handle, int x, int y, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format, int binaryzation_method);

    public int Label_EnableLabelMode(Pointer handle);

    public int Label_DisableLabelMode(Pointer handle);

    public int Label_CalibrateLabel(Pointer handle);

    public int Label_FeedLabel(Pointer handle);

    public int Label_PageBegin(Pointer handle, int x, int y, int width, int height, int rotation);

    public int Label_PageEnd(Pointer handle);

    public int Label_PagePrint(Pointer handle, int copies);

    public int Label_DrawTextA(Pointer handle, int x, int y, int font, int style, String str);

    public int Label_DrawTextInUTF8W(Pointer handle, int x, int y, int font, int style, WString str);

    public int Label_DrawTextInGBKW(Pointer handle, int x, int y, int font, int style, WString str);

    public int Label_DrawTextInBIG5W(Pointer handle, int x, int y, int font, int style, WString str);

    public int Label_DrawTextInShiftJISW(Pointer handle, int x, int y, int font, int style, WString str);

    public int Label_DrawTextInEUCKRW(Pointer handle, int x, int y, int font, int style, WString str);

    public int Label_DrawBarcodeA(Pointer handle, int x, int y, int nBarcodeType, int height, int unitwidth, int rotation, String str);
    public int Label_DrawBarcodeW(Pointer handle, int x, int y, int nBarcodeType, int height, int unitwidth, int rotation, WString str);

    public int Label_DrawQRCodeA(Pointer handle, int x, int y, int nVersion, int nECCLevel, int unitwidth, int rotation, String str);
    public int Label_DrawQRCodeW(Pointer handle, int x, int y, int nVersion, int nECCLevel, int unitwidth, int rotation, WString str);

    public int Label_DrawPDF417CodeA(Pointer handle, int x, int y, int column, int nAspectRatio, int nECCLevel, int unitwidth, int rotation, String str);
    public int Label_DrawPDF417CodeW(Pointer handle, int x, int y, int column, int nAspectRatio, int nECCLevel, int unitwidth, int rotation, WString str);

    public int Label_DrawImageFromFileA(Pointer handle, int x, int y, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int Label_DrawImageFromFileW(Pointer handle, int x, int y, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int Label_DrawImageFromData(Pointer handle, int x, int y, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class Label_DrawImage_Helper {
        public static int Label_DrawImageFromBitmap(Pointer handle, int x, int y, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.Label_DrawImageFromData(handle, x, y, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int Label_DrawImageFromPixels(Pointer handle, int x, int y, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format, int binaryzation_method);

    public int Label_DrawLine(Pointer handle, int startx, int starty, int endx, int endy, int linewidth, int linecolor);

    public int Label_DrawRect(Pointer handle, int x, int y, int width, int height, int color);

    public int Label_DrawBox(Pointer handle, int x, int y, int width, int height, int borderwidth, int bordercolor);

}

