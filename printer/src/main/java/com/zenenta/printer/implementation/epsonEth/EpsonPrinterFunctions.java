package com.zenenta.printer.implementation.epsonEth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.epson.epos2.Epos2CallbackCode;
import com.epson.epos2.Epos2Exception;
import com.epson.epos2.discovery.Discovery;
import com.epson.epos2.discovery.FilterOption;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.zenenta.printer.R;
import com.zenenta.printer.repository.Setting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Tisto on 19/02/24.
 */

@SuppressLint("LogNotTimber")
public class EpsonPrinterFunctions {


    private static Context mContext;
    private static List<EpsonPrinterStatusDTO> mListPrinterStatus = new ArrayList<EpsonPrinterStatusDTO>();

    private static EpsonPrinterStatusDTO getPrinterStatus(Setting printerSetting) {
        for (EpsonPrinterStatusDTO printerStatusDTO : mListPrinterStatus) {
            if (printerStatusDTO.getName().equals(printerSetting.getName()) && printerStatusDTO.getDestination().equals(printerSetting.getDeviceDestination())) {
                return printerStatusDTO;
            }
        }
        return null;
    }

    public static void startEpsonPrinterDiscovery(Context mContext) {
        FilterOption mEpsonFilterOption = new FilterOption();
        mEpsonFilterOption.setDeviceType(Discovery.TYPE_PRINTER);
        mEpsonFilterOption.setEpsonFilter(Discovery.FILTER_NAME);
        mEpsonFilterOption.setPortType(Discovery.PORTTYPE_TCP);
        try {
            Discovery.start(mContext, mEpsonFilterOption, deviceInfo -> {
                Log.d("Discovery", "onDiscovery -> DeviceName : " + deviceInfo.getDeviceName() + " | Target : " + deviceInfo.getTarget());
            });
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private static EpsonPrinterStatusDTO getPrinterStatusByObj(Printer printer) {
        for (EpsonPrinterStatusDTO printerStatusDTO : mListPrinterStatus) {
            if (printerStatusDTO.getPrinter().equals(printer)) {
                return printerStatusDTO;
            }
        }
        return null;
    }

    private static boolean initializeObject(Context context, Setting printerSetting) {

        EpsonPrinterStatusDTO epsonPrinterStatusDTO = getPrinterStatus(printerSetting);
        if (epsonPrinterStatusDTO != null && epsonPrinterStatusDTO.isPrintCompleted()) {

            showError(epsonPrinterStatusDTO.getName() + ", has not received callback from printer");

            Log.e("Epson", "Failed re-intialized Printer");
            return false;
        }

        mContext = context;

        if (epsonPrinterStatusDTO == null) {
            try {
                Printer printer = new Printer(printerSetting.getSeriesId(), 0, mContext);
                Log.e("Epson", "Intantiate " + printerSetting.getSeriesId());

                printer.setReceiveEventListener((ReceiveListener) mContext);
                Log.e("Epson", "Set Receiver");

                epsonPrinterStatusDTO = new EpsonPrinterStatusDTO(null, printerSetting.getName(), printerSetting.getDeviceDestination());
                Log.e("Epson", "Add Printer : " + epsonPrinterStatusDTO.getName());
                mListPrinterStatus.add(epsonPrinterStatusDTO);
            } catch (Exception e) {
                Log.e("Epson Init", e.getMessage());
                showException(e, printerSetting.getName() + " -> Initialize");
                return false;
            }
        }

        Log.e("Epson", "Print init count :" + mListPrinterStatus.size());

        return true;
    }

    private static boolean connectPrinter(EpsonPrinterStatusDTO epsonPrinterStatusDTO, Setting printerSetting) {
        //boolean isBeginTransaction = false;
        try {
            Log.e("Epson", "Connect");
            epsonPrinterStatusDTO.getPrinter().connect(epsonPrinterStatusDTO.getDestination(), Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            Log.e("Epson", "Connect Error");
            showException(e, printerSetting.getName() + " -> connect");
            return false;
        }
        return true;
    }


    private static boolean printData(EpsonPrinterStatusDTO epsonPrinterStatusDTO, Setting printerSetting) {
        if (!connectPrinter(epsonPrinterStatusDTO, printerSetting)) {
            epsonPrinterStatusDTO.getPrinter().clearCommandBuffer();
            Log.e("Epson", "Connection error");
            return false;
        }

        epsonPrinterStatusDTO.setPrintCompleted(false);

        try {
            epsonPrinterStatusDTO.getPrinter().sendData(60 * 1000);
        } catch (Exception e) {
            if (epsonPrinterStatusDTO.getPrinter() != null)
                epsonPrinterStatusDTO.getPrinter().clearCommandBuffer();
            showException(e, printerSetting.getName() + " -> sendData");
            try {
                if (epsonPrinterStatusDTO.getPrinter() != null) {
                    epsonPrinterStatusDTO.getPrinter().disconnect();
                    Log.e("Epson", "Direct Disconnected");
                } else {
                    Log.e("Epson", "NULL Disconnected");
                }
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        epsonPrinterStatusDTO.setPrintCompleted(true);
        Log.e("Epson", "Print Completed");

        return true;
    }

    public static boolean printTest(Context context, Setting printerSetting) {
        if (!initializeObject(context, printerSetting)) {
            return false;
        }

        EpsonPrinterStatusDTO epsonPrinterStatusDTO = getPrinterStatus(printerSetting);
        if (epsonPrinterStatusDTO == null || epsonPrinterStatusDTO.getPrinter() == null) {
            return false;
        }

        if (!createTestReceiptData(context, epsonPrinterStatusDTO, printerSetting)) {
            //finalizeObject();
            return false;
        }

        if (!printData(epsonPrinterStatusDTO, printerSetting)) {
            //finalizeObject();
            return false;
        }

        return true;
    }

    private static boolean createTestReceiptData(Context context, EpsonPrinterStatusDTO epsonPrinterStatusDTO, Setting mPrinterSetting) {
        Calendar calDefault = Calendar.getInstance();
        calDefault.set(1970, 1, 1);

        String dotlines = "";
        for (int i = 0; i < mPrinterSetting.getNumberCols(); i++) {
            dotlines += "-";
        }
        String method = "";
        StringBuilder textData = new StringBuilder();

        Printer mPrinter = epsonPrinterStatusDTO.getPrinter();
        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);

//            if (mPrinterSetting.isWithLogo()) {
//                Bitmap logoData = Helper.getLogoBitmap(mContext, mStore.getId());
//                if (logoData != null) {
//                    method = "addImage";
//                    mPrinter.addImage(logoData, 0, 0,
//                            logoData.getWidth(),
//                            logoData.getHeight(),
//                            Printer.COLOR_1,
//                            Printer.MODE_MONO,
//                            Printer.HALFTONE_DITHER,
//                            Printer.PARAM_DEFAULT,
//                            Printer.COMPRESS_AUTO);
//                }
//            }

            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            method = "addText";
            mPrinter.addText("Store Header");

            mPrinter.addTextAlign(Printer.ALIGN_LEFT);

            textData.append("\n");

            textData.append(dotlines);
            textData.append("\n");

            textData.append("Item");
            mPrinter.addTextAlign(Printer.ALIGN_RIGHT);
            textData.append("Amount");
            textData.append("\n");

            textData.append(dotlines);
            textData.append("\n");

            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            mPrinter.addFeedLine(1);

            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);

//            if (!mStore.isDisableReprintOpenDrawer())
//                mPrinter.addPulse(0, 0);

        } catch (Exception e) {
            showException(e, mPrinterSetting.getName() + " -> " + method);
            return false;
        }

        return true;
    }

    public static boolean openDrawer(Context context, Setting printerSetting) {

        if (!initializeObject(context, printerSetting)) {
            return false;
        }

        EpsonPrinterStatusDTO epsonPrinterStatusDTO = getPrinterStatus(printerSetting);
        if (epsonPrinterStatusDTO == null || epsonPrinterStatusDTO.getPrinter() == null) {
            return false;
        }

        if (!createOpenDrawer(context, epsonPrinterStatusDTO, printerSetting)) {
            //finalizeObject();
            return false;
        }

        if (!printData(epsonPrinterStatusDTO, printerSetting)) {
            //finalizeObject();
            return false;
        }

        return true;
    }

    private static boolean createOpenDrawer(Context context, EpsonPrinterStatusDTO epsonPrinterStatusDTO, Setting printerSetting) {

        Printer mPrinter = epsonPrinterStatusDTO.getPrinter();
        if (mPrinter == null) {
            return false;
        }

        String method = "";

        try {
            mPrinter.addPulse(0, 0);
        } catch (Exception e) {
            showException(e, printerSetting.getName() + " -> " + method);
            return false;
        }

        return true;
    }

    public static String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += mContext.getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += mContext.getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += mContext.getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += mContext.getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += mContext.getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += mContext.getString(R.string.handlingmsg_err_autocutter);
            msg += mContext.getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += mContext.getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += mContext.getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += mContext.getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }


    public static void dispPrinterWarnings(PrinterStatusInfo status) {
        String warningsMsg = "";

        if (status == null) {
            return;
        }

        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += mContext.getString(R.string.handlingmsg_warn_receipt_near_end);
        }

        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += mContext.getString(R.string.handlingmsg_warn_battery_near_end);
        }

        if (warningsMsg.length() > 0)
            showWarning(warningsMsg);
    }

    public static void showException(Exception e, String method) {
        String msg = "";
        if (e instanceof Epos2Exception) {
            msg = String.format(
                    "%s\n\t%s\n%s\n\t%s",
                    mContext.getString(R.string.title_err_code),
                    "Epson " + getEposExceptionText(((Epos2Exception) e).getErrorStatus()),
                    mContext.getString(R.string.title_err_method),
                    method);
        } else {
            msg = e.toString();
        }

        showError(msg);
    }

    private static String getEposExceptionText(int state) {
        String return_text = "";
        switch (state) {
            case Epos2Exception.ERR_PARAM:
                return_text = "ERR_PARAM";
                break;
            case Epos2Exception.ERR_CONNECT:
                return_text = "ERR_CONNECT";
                break;
            case Epos2Exception.ERR_TIMEOUT:
                return_text = "ERR_TIMEOUT";
                break;
            case Epos2Exception.ERR_MEMORY:
                return_text = "ERR_MEMORY";
                break;
            case Epos2Exception.ERR_ILLEGAL:
                return_text = "ERR_ILLEGAL";
                break;
            case Epos2Exception.ERR_PROCESSING:
                return_text = "ERR_PROCESSING";
                break;
            case Epos2Exception.ERR_NOT_FOUND:
                return_text = "ERR_NOT_FOUND";
                break;
            case Epos2Exception.ERR_IN_USE:
                return_text = "ERR_IN_USE";
                break;
            case Epos2Exception.ERR_TYPE_INVALID:
                return_text = "ERR_TYPE_INVALID";
                break;
            case Epos2Exception.ERR_DISCONNECT:
                return_text = "ERR_DISCONNECT";
                break;
            case Epos2Exception.ERR_ALREADY_OPENED:
                return_text = "ERR_ALREADY_OPENED";
                break;
            case Epos2Exception.ERR_ALREADY_USED:
                return_text = "ERR_ALREADY_USED";
                break;
            case Epos2Exception.ERR_BOX_COUNT_OVER:
                return_text = "ERR_BOX_COUNT_OVER";
                break;
            case Epos2Exception.ERR_BOX_CLIENT_OVER:
                return_text = "ERR_BOX_CLIENT_OVER";
                break;
            case Epos2Exception.ERR_UNSUPPORTED:
                return_text = "ERR_UNSUPPORTED";
                break;
            case Epos2Exception.ERR_FAILURE:
                return_text = "ERR_FAILURE";
                break;
            default:
                return_text = String.format("%d", state);
                break;
        }
        return return_text;
    }

    private static String getCodeText(int state) {
        String return_text = "";
        switch (state) {
            case Epos2CallbackCode.CODE_SUCCESS:
                return_text = "PRINT_SUCCESS";
                break;
            case Epos2CallbackCode.CODE_PRINTING:
                return_text = "PRINTING";
                break;
            case Epos2CallbackCode.CODE_ERR_AUTORECOVER:
                return_text = "ERR_AUTORECOVER";
                break;
            case Epos2CallbackCode.CODE_ERR_COVER_OPEN:
                return_text = "ERR_COVER_OPEN";
                break;
            case Epos2CallbackCode.CODE_ERR_CUTTER:
                return_text = "ERR_CUTTER";
                break;
            case Epos2CallbackCode.CODE_ERR_MECHANICAL:
                return_text = "ERR_MECHANICAL";
                break;
            case Epos2CallbackCode.CODE_ERR_EMPTY:
                return_text = "ERR_EMPTY";
                break;
            case Epos2CallbackCode.CODE_ERR_UNRECOVERABLE:
                return_text = "ERR_UNRECOVERABLE";
                break;
            case Epos2CallbackCode.CODE_ERR_FAILURE:
                return_text = "ERR_FAILURE";
                break;
            case Epos2CallbackCode.CODE_ERR_NOT_FOUND:
                return_text = "ERR_NOT_FOUND";
                break;
            case Epos2CallbackCode.CODE_ERR_SYSTEM:
                return_text = "ERR_SYSTEM";
                break;
            case Epos2CallbackCode.CODE_ERR_PORT:
                return_text = "ERR_PORT";
                break;
            case Epos2CallbackCode.CODE_ERR_TIMEOUT:
                return_text = "ERR_TIMEOUT";
                break;
            case Epos2CallbackCode.CODE_ERR_JOB_NOT_FOUND:
                return_text = "ERR_JOB_NOT_FOUND";
                break;
            case Epos2CallbackCode.CODE_ERR_SPOOLER:
                return_text = "ERR_SPOOLER";
                break;
            case Epos2CallbackCode.CODE_ERR_BATTERY_LOW:
                return_text = "ERR_BATTERY_LOW";
                break;
            default:
                return_text = String.format("%d", state);
                break;
        }
        return return_text;
    }

    private static void showWarning(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    private static void showError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void disconnectPrinter(final Context context, Printer printer) {
        EpsonPrinterStatusDTO epsonPrinterStatusDTO = getPrinterStatusByObj(printer);
        if (epsonPrinterStatusDTO == null || epsonPrinterStatusDTO.getPrinter() == null) {
            Log.e("Epson", "Disconnect Printer Null");
            return;
        }

        while (true) {
            try {
                //mPrintCompleted = false;
                //mPrinter.disconnect();
                epsonPrinterStatusDTO.setPrintCompleted(false);
                epsonPrinterStatusDTO.getPrinter().disconnect();

                Log.e("Epson", "Disconnected");
                break;
            } catch (final Exception e) {
                Log.e("Epson Disconnect", e.getMessage());
                if (e instanceof Epos2Exception) {
                    //Note: If printer is processing such as printing and so on, the disconnect API returns ERR_PROCESSING.
                    if (((Epos2Exception) e).getErrorStatus() == Epos2Exception.ERR_PROCESSING) {
                        try {
                            Thread.sleep(500);
                        } catch (Exception ex) {
                        }
                    } else {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            public synchronized void run() {
                                showException(e, "disconnect", context);
                            }
                        });
                        break;
                    }
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        public synchronized void run() {
                            showException(e, "disconnect", context);
                        }
                    });
                    break;
                }
            }
        }

        //printer.clearCommandBuffer();

        finalizeObject(epsonPrinterStatusDTO);
    }

    private static void finalizeObject(EpsonPrinterStatusDTO epsonPrinterStatusDTO) {
        if (epsonPrinterStatusDTO == null || epsonPrinterStatusDTO.getPrinter() == null) {
            return;
        }

        Printer mPrinter = epsonPrinterStatusDTO.getPrinter();
        mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);

        mPrinter = null;

        Log.e("Epson", "Removing Printer : " + epsonPrinterStatusDTO.getName());
        mListPrinterStatus.remove(epsonPrinterStatusDTO);
        Log.e("Epson", "Finalize Print count :" + mListPrinterStatus.size());
    }

    public static void showException(Exception e, String method, Context context) {
        String msg = "";
        if (e instanceof Epos2Exception) {
            msg = String.format(
                    "%s\n\t%s\n%s\n\t%s",
                    context.getString(R.string.title_err_code),
                    getEposExceptionText(((Epos2Exception) e).getErrorStatus()),
                    context.getString(R.string.title_err_method),
                    method);
        } else {
            msg = e.toString();
        }

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String makeErrorMessage(Context context, PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += context.getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += context.getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += context.getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += context.getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += context.getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += context.getString(R.string.handlingmsg_err_autocutter);
            msg += context.getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += context.getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += context.getString(R.string.handlingmsg_err_overheat);
                msg += context.getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += context.getString(R.string.handlingmsg_err_overheat);
                msg += context.getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += context.getString(R.string.handlingmsg_err_overheat);
                msg += context.getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += context.getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += context.getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }

    public static void dispPrinterWarnings(Context context, PrinterStatusInfo status) {
        String warningsMsg = "";

        if (status == null) {
            return;
        }

        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += context.getString(R.string.handlingmsg_warn_receipt_near_end);
        }

        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += context.getString(R.string.handlingmsg_warn_battery_near_end);
        }

        if (warningsMsg.length() > 0)
            Toast.makeText(context, warningsMsg, Toast.LENGTH_SHORT).show();
    }
}
