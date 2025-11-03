package com.olserapratama.printer.libs.printerlibs.printerlibs_caysnlabel;

import android.graphics.Bitmap;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;

import java.io.ByteArrayOutputStream;

public interface printerlibs_caysnlabel extends Library {

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
    public static final printerlibs_caysnlabel INSTANCE = (printerlibs_caysnlabel) Native.loadLibrary(GetLibraryPath_Helper.GetLibraryPath(), printerlibs_caysnlabel.class);

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

    public static final int DrawAlignment_Left = -1;
    public static final int DrawAlignment_HCenter = -2;
    public static final int DrawAlignment_Right = -3;
    public static final int DrawAlignment_Top = -1;
    public static final int DrawAlignment_VCenter = -2;
    public static final int DrawAlignment_Bottom = -3;

    public static final int MultiByteModeEncoding_GBK = 0;
    public static final int MultiByteModeEncoding_UTF8 = 1;
    public static final int MultiByteModeEncoding_BIG5 = 3;
    public static final int MultiByteModeEncoding_ShiftJIS = 4;
    public static final int MultiByteModeEncoding_EUCKR = 5;

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

    public static final int BarcodeReadableTextPosition_None = 0;
    public static final int BarcodeReadableTextPosition_AboveBarcode = 1;
    public static final int BarcodeReadableTextPosition_BelowBarcode = 2;
    public static final int BarcodeReadableTextPosition_AboveAndBelowBarcode = 3;

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

    public String CaysnLabel_LibraryVersion();

    public int CaysnLabel_EnumComA(byte[] pBuf, int cbBuf, IntByReference pcbNeeded);
    //public int CaysnLabel_EnumComW(byte[] pBuf, int cbBuf, IntByReference pcbNeeded);
    public class CaysnLabel_EnumCom_Helper {
        public static String[] CaysnLabel_EnumComA() {
            IntByReference pcbNeeded = new IntByReference();
            INSTANCE.CaysnLabel_EnumComA(null, 0, pcbNeeded);
            if (pcbNeeded.getValue() > 0) {
                byte[] pBuf = new byte[pcbNeeded.getValue()];
                if (pBuf != null) {
                    INSTANCE.CaysnLabel_EnumComA(pBuf, pBuf.length, null);
                    String s = new String(pBuf);
                    String[] ss = s.split("\0");
                    return ss;
                }
            }
            return null;
        }
    }

    public int CaysnLabel_EnumUsbVidPidA(byte[] pBuf, int cbBuf, IntByReference pcbNeeded);
    //public int CaysnLabel_EnumUsbVidPidW(byte[] pBuf, int cbBuf, IntByReference pcbNeeded);
    public class CaysnLabel_EnumUsbVidPid_Helper {
        public static String[] CaysnLabel_EnumUsbVidPidA() {
            IntByReference pcbNeeded = new IntByReference();
            INSTANCE.CaysnLabel_EnumUsbVidPidA(null, 0, pcbNeeded);
            if (pcbNeeded.getValue() > 0) {
                byte[] pBuf = new byte[pcbNeeded.getValue()];
                if (pBuf != null) {
                    INSTANCE.CaysnLabel_EnumUsbVidPidA(pBuf, pBuf.length, null);
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
    public void CaysnLabel_EnumNetPrinterA(int timeout, IntByReference cancel, on_netprinter_discovered_a_callback callback, Pointer data);

    interface on_btdevice_discovered_a_callback extends Callback {
        void on_btdevice_discovered_a(String device_name, String device_address, Pointer private_data);
    }
    public void CaysnLabel_EnumBtDeviceA(int timeout, IntByReference cancel, on_btdevice_discovered_a_callback callback, Pointer data);

    public void CaysnLabel_EnumBleDeviceA(int timeout, IntByReference cancel, on_btdevice_discovered_a_callback callback, Pointer data);

    public Pointer CaysnLabel_OpenComA(String name, int baudrate, int databits, int parity, int stopbits, int flowcontrol);
    public Pointer CaysnLabel_OpenComW(WString name, int baudrate, int databits, int parity, int stopbits, int flowcontrol);

    public Pointer CaysnLabel_OpenTcpA(String ip, short port, int timeout);
    public Pointer CaysnLabel_OpenTcpW(WString ip, short port, int timeout);

    public Pointer CaysnLabel_OpenTcpBindInterfaceA(String ip, short port, String bind_local_addr, int timeout);
    public Pointer CaysnLabel_OpenTcpBindInterfaceW(WString ip, short port, WString bind_local_addr, int timeout);

    public Pointer CaysnLabel_OpenUsbVidPid(short vid, short pid);
    public Pointer CaysnLabel_OpenUsbVidPidStringA(String name);
    public Pointer CaysnLabel_OpenUsbVidPidStringW(WString name);

    public Pointer CaysnLabel_OpenBT2ByConnectA(String address);
    public Pointer CaysnLabel_OpenBT2ByConnectW(WString address);

    public Pointer CaysnLabel_OpenBT2ByListenA(int timeout, byte[] address);

    public Pointer CaysnLabel_OpenBT4ByConnectA(String address);
    public Pointer CaysnLabel_OpenBT4ByConnectW(WString address);

    public Pointer CaysnLabel_OpenFileNewA(String name);
    public Pointer CaysnLabel_OpenFileNewW(WString name);

    public Pointer CaysnLabel_OpenFileAppendA(String name);
    public Pointer CaysnLabel_OpenFileAppendW(WString name);

    public Pointer CaysnLabel_OpenMemory(int nMemorySpaceSize);

    public Pointer CaysnLabel_MemoryData(Pointer handle);
    public class CaysnLabel_MemoryData_Helper {
        public static byte[] CaysnLabel_MemoryByteArray(Pointer handle) {
            Pointer pdata = INSTANCE.CaysnLabel_MemoryData(handle);
            int data_size = INSTANCE.CaysnLabel_MemoryDataLength(handle);
            if (pdata != Pointer.NULL) {
                byte[] buffer = pdata.getByteArray(0, data_size);
                return buffer;
            }
            return null;
        }
    }

    public int CaysnLabel_MemoryDataLength(Pointer handle);

    public void CaysnLabel_ClearMemoryData(Pointer handle);

    interface on_bytes_writed_callback extends Callback {
        // callback not support byte[]
        void on_bytes_writed(Pointer buffer, int count, Pointer private_data);
    }
    public int CaysnLabel_SetWritedEvent(Pointer handle, on_bytes_writed_callback callback, Pointer private_data);

    interface on_bytes_readed_callback extends Callback {
        // callback not support byte[]
        void on_bytes_readed(Pointer buffer, int count, Pointer private_data);
    }
    public int CaysnLabel_SetReadedEvent(Pointer handle, on_bytes_readed_callback callback, Pointer private_data);

    interface on_port_closed_callback extends Callback {
        void on_port_closed(Pointer private_data);
    }
    public int CaysnLabel_SetClosedEvent(Pointer handle, on_port_closed_callback callback, Pointer private_data);

    public int CaysnLabel_Write(Pointer handle, byte[] buffer, int count, int timeout);

    public int CaysnLabel_Read(Pointer handle, byte[] buffer, int count, int timeout);

    public int CaysnLabel_ReadUntilByte(Pointer handle, byte[] buffer, int count, int timeout, byte breakByte);

    public void CaysnLabel_SkipAvailable(Pointer handle);

    public void CaysnLabel_FlushBuffer(Pointer handle);

    public void CaysnLabel_Close(Pointer handle);

    public int CaysnLabel_QueryPrinterBufferEmpty(Pointer handle, int timeout);

    public int CaysnLabel_QueryPrinterStatus(Pointer handle, int timeout);

    public int CaysnLabel_QueryPrintResult(Pointer handle, int timeout);

    public int CaysnLabel_KickOutDrawer(Pointer handle, int nDrawerIndex, int nHighLevelTime, int nLowLevelTime);

    public int CaysnLabel_Beep(Pointer handle, int nBeepCount, int nBeepMs);

    public int CaysnLabel_ResetPrinter(Pointer handle);

    public int CaysnLabel_SetPrinter(Pointer handle, int setType, byte[] buffer, int count);

    public int CaysnLabel_SetPrintSpeed(Pointer handle, int nSpeed);

    public int CaysnLabel_SetPrintDensity(Pointer handle, int nDensity);

    public int CaysnLabel_SetPrintHeatPara(Pointer handle, int nMaxHeatDots, int nHeatOnTime, int nHeatOffTime);

    public int CaysnLabel_PrintSelfTestPage(Pointer handle);

    public int CaysnLabel_SetSingleByteMode(Pointer handle);

    public int CaysnLabel_SetCharacterSet(Pointer handle, int nCharacterSet);

    public int CaysnLabel_SetCharacterCodepage(Pointer handle, int nCharacterCodepage);

    public int CaysnLabel_SetMultiByteMode(Pointer handle);

    public int CaysnLabel_SetMultiByteEncoding(Pointer handle, int nEncoding);

    public int CaysnLabel_SetBarcodeReadableTextPosition(Pointer handle, int nTextPosition);

    public int CaysnLabel_GetImageSizeFromFileA(String pszFile, IntByReference depth, IntByReference width, IntByReference height);
    public int CaysnLabel_GetImageSizeFromFileW(WString pszFile, IntByReference depth, IntByReference width, IntByReference height);

    public int CaysnLabel_GetImageSizeFromData(byte[] data, int data_size, IntByReference depth, IntByReference width, IntByReference height);

    public int CaysnLabel_EnableLabelMode(Pointer handle);

    public int CaysnLabel_DisableLabelMode(Pointer handle);

    public int CaysnLabel_CalibrateLabel(Pointer handle);

    public int CaysnLabel_FeedLabel(Pointer handle);

    public int CaysnLabel_PageBegin(Pointer handle, int x, int y, int width, int height, int rotation);

    public int CaysnLabel_PageEnd(Pointer handle);

    public int CaysnLabel_PagePrint(Pointer handle, int copies);

    public int CaysnLabel_DrawTextA(Pointer handle, int x, int y, int font, int style, String str);

    public int CaysnLabel_DrawTextInUTF8W(Pointer handle, int x, int y, int font, int style, WString str);

    public int CaysnLabel_DrawTextInGBKW(Pointer handle, int x, int y, int font, int style, WString str);

    public int CaysnLabel_DrawTextInBIG5W(Pointer handle, int x, int y, int font, int style, WString str);

    public int CaysnLabel_DrawTextInShiftJISW(Pointer handle, int x, int y, int font, int style, WString str);

    public int CaysnLabel_DrawTextInEUCKRW(Pointer handle, int x, int y, int font, int style, WString str);

    public int CaysnLabel_DrawBarcodeA(Pointer handle, int x, int y, int nBarcodeType, int height, int unitwidth, int rotation, String str);
    public int CaysnLabel_DrawBarcodeW(Pointer handle, int x, int y, int nBarcodeType, int height, int unitwidth, int rotation, WString str);

    public int CaysnLabel_DrawQRCodeA(Pointer handle, int x, int y, int nVersion, int nECCLevel, int unitwidth, int rotation, String str);
    public int CaysnLabel_DrawQRCodeW(Pointer handle, int x, int y, int nVersion, int nECCLevel, int unitwidth, int rotation, WString str);

    public int CaysnLabel_DrawPDF417CodeA(Pointer handle, int x, int y, int column, int nAspectRatio, int nECCLevel, int unitwidth, int rotation, String str);
    public int CaysnLabel_DrawPDF417CodeW(Pointer handle, int x, int y, int column, int nAspectRatio, int nECCLevel, int unitwidth, int rotation, WString str);

    public int CaysnLabel_DrawImageFromFileA(Pointer handle, int x, int y, int dstw, int dsth, String pszFile, int binaryzation_method);
    public int CaysnLabel_DrawImageFromFileW(Pointer handle, int x, int y, int dstw, int dsth, WString pszFile, int binaryzation_method);

    public int CaysnLabel_DrawImageFromData(Pointer handle, int x, int y, int dstw, int dsth, byte[] data, int data_size, int binaryzation_method);
    public class CaysnLabel_DrawImage_Helper {
        public static int CaysnLabel_DrawImageFromBitmap(Pointer handle, int x, int y, int dstw, int dsth, Bitmap bitmap, int binaryzation_method) {
            int result = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)) {
                byte[] data = os.toByteArray();
                result = INSTANCE.CaysnLabel_DrawImageFromData(handle, x, y, dstw, dsth, data, data.length, binaryzation_method);
            }
            return result;
        }
    }

    public int CaysnLabel_DrawImageFromPixels(Pointer handle, int x, int y, byte[] img_data, int img_datalen, int img_width, int img_height, int img_stride, int img_format, int binaryzation_method);

    public int CaysnLabel_DrawLine(Pointer handle, int startx, int starty, int endx, int endy, int linewidth, int linecolor);

    public int CaysnLabel_DrawRect(Pointer handle, int x, int y, int width, int height, int color);

    public int CaysnLabel_DrawBox(Pointer handle, int x, int y, int width, int height, int borderwidth, int bordercolor);

}

