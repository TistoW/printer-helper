package com.olserapratama.printer.libs.gprinterlibs;

/**
 * Created by alitjin on 15/10/16.
 */

public class GPrinterFunctions {

//    public static int getPortTypeId(String deviceDestination) {
//        if (deviceDestination != null && deviceDestination.length() > 4) {
//            if (deviceDestination.substring(0, Helper.IF_TYPE_ETHERNET.length()).equals(Helper.IF_TYPE_ETHERNET))
//                return 3;
//            else if (deviceDestination.substring(0, Helper.IF_TYPE_BLUETOOTH.length()).equals(Helper.IF_TYPE_BLUETOOTH))
//                return 4;
//            else if (deviceDestination.substring(0, Helper.IF_TYPE_USB.length()).equals(Helper.IF_TYPE_USB))
//                return 2;
//        }
//        return 5;
//    }
//
//    public static int getPortTypePortNo(String deviceDestination) {
//        if (deviceDestination != null && deviceDestination.length() > 4) {
//            if (deviceDestination.substring(0, Helper.IF_TYPE_ETHERNET.length()).equals(Helper.IF_TYPE_ETHERNET))
//                return 9100;
//        }
//
//        return 0;
//    }
//
//    public static boolean isPortTypeBluetooth(String deviceDestination) {
//        if (deviceDestination != null && deviceDestination.length() > 4) {
//            if (deviceDestination.substring(0, Helper.IF_TYPE_BLUETOOTH.length()).equals(Helper.IF_TYPE_BLUETOOTH))
//                return true;
//        }
//        return false;
//    }
//
//    public static String getAddress(String deviceDestination) {
//        if (deviceDestination != null && deviceDestination.length() > 4) {
//            if (deviceDestination.substring(0, Helper.IF_TYPE_ETHERNET.length()).equals(Helper.IF_TYPE_ETHERNET))
//                return deviceDestination.substring(Helper.IF_TYPE_ETHERNET.length());
//            else if (deviceDestination.substring(0, Helper.IF_TYPE_BLUETOOTH.length()).equals(Helper.IF_TYPE_BLUETOOTH))
//                return deviceDestination.substring(Helper.IF_TYPE_BLUETOOTH.length());
//            else if (deviceDestination.substring(0, Helper.IF_TYPE_USB.length()).equals(Helper.IF_TYPE_USB))
//                return deviceDestination.substring(Helper.IF_TYPE_USB.length());
//        }
//        return "";
//    }
//
//    public static void connect(final Context context, final int printerIdx, String name, String deviceDestination, boolean async) {
//
//        //Bluetooth
//        if (getPortTypeId(deviceDestination) == 4) {
//            new GPDeviceConnFactoryManager.Build()
//                    .setId(printerIdx)
//                    .setContext(context)
//                    .setName(name)
//                    //设置连接方式
//                    .setConnMethod(GPDeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
//                    //设置连接的蓝牙mac地址
//                    .setMacAddress(getAddress(deviceDestination))
//                    .build();
//        } else if (getPortTypeId(deviceDestination) == 3) {
//            new GPDeviceConnFactoryManager.Build()
//                    .setId(printerIdx)
//                    .setContext(context)
//                    .setName(name)
//                    //设置连接方式
//                    .setConnMethod(GPDeviceConnFactoryManager.CONN_METHOD.WIFI)
//                    //设置连接的蓝牙mac地址
//                    .setIp(getAddress(deviceDestination))
//                    //设置连接的热点端口号
//                    .setPort(getPortTypePortNo(deviceDestination))
//                    .build();
//        } else if (getPortTypeId(deviceDestination) == 2) {
//            UsbDevice usbDevice = GPUtils.getUsbDeviceFromName(context, getAddress(deviceDestination));
//
//            if (usbDevice == null) {
//                Toast.makeText(context, "USB Device not valid", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            new GPDeviceConnFactoryManager.Build()
//                    .setId(printerIdx)
//                    .setContext(context)
//                    .setName(name)
//                    //设置连接方式
//                    .setConnMethod(GPDeviceConnFactoryManager.CONN_METHOD.USB)
//                    //设置连接的蓝牙mac地址
//                    .setUsbDevice(usbDevice)
//                    //设置连接的热点端口号
//                    .setPort(getPortTypePortNo(deviceDestination))
//                    .build();
//        }
//
//        if (async) {
//            GPThreadPool threadPool = GPThreadPool.getInstantiation();
//            threadPool.addTask(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        //打开端口
//                        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers().length > printerIdx)
//                            GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].openPort();
//
//                    } catch (Exception ex) {
//                        //Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        } else {
//            try {
//                //打开端口
//                if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers().length > printerIdx)
//                    GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].openPort();
//
//            } catch (Exception ex) {
//                //Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        /*
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers().length > printerIdx){
//            GPDeviceConnFactoryManager deviceConnFactoryManager = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx];
//            if (deviceConnFactoryManager.getConnState()){
//                if (deviceConnFactoryManager.getPort() == 0 && !getAddress(deviceDestination).equals(deviceConnFactoryManager.getMacAddress())){
//                    deviceConnFactoryManager.closePort(0);
//                }
//                else if (deviceConnFactoryManager.getPort() == 1 && !getAddress(deviceDestination).equals(deviceConnFactoryManager.getSerialPortPath())){
//                    deviceConnFactoryManager.closePort(0);
//                }
//            }
//
//        }
//        */
//
//    }
//
//    public static void disconnectAll() {
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers().length > 0) {
//            GPDeviceConnFactoryManager.closeAllPort();
//        }
//    }
//
//    /*
//    public static void printIntoCustomerDisplay(CustomerDisplay customerDisplay, String text){
//        try {
//            if (!customerDisplay.isPortOpen()) {
//                customerDisplay.openPort();
//            }
//
//            customerDisplay.clear();
//            customerDisplay.setCursorPosition(0, 0);
//            customerDisplay.setTextCurrentCursor(text);
//            customerDisplay.setBacklight(true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    */
//
//    public static boolean printTest(Context context, int printerIdx, Store store, Printer printerSetting) {
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx] == null ||
//                !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].getConnState()) {
//            Toast.makeText(context, String.format(context.getString(R.string.str_printer_not_connected), printerSetting.getName()), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        String dotlines = "";
//        for (int i = 0; i < printerSetting.getNumberCols(); i++) {
//            dotlines += "-";
//        }
//
//        EscCommand esc = new EscCommand();
//        esc.addInitializePrinter();
//        esc.addPrintAndFeedLines((byte) 2);
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//
//        if (printerSetting.isWithLogo()) {
//            Bitmap logo = Helper.getLogoBitmap(context, store.getId());
//            if (logo != null) {
//                esc.addRastBitImage(logo, 320, 0);
//            }
//        }
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
//        esc.addText(store.getReceiptHeader());
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        esc.addPrintAndFeedLines((byte) 2);
//
//        esc.addText(dotlines);
//        esc.addPrintAndLineFeed();
//
//        esc.addText(Helper.paddingRight(context.getString(R.string.item), printerSetting.getReceiptHeaderLeftCols()));
//        esc.addText(Helper.paddingLeft(context.getString(R.string.amount), printerSetting.getReceiptHeaderRightCols()));
//        esc.addPrintAndLineFeed();
//
//        esc.addText(dotlines);
//        esc.addPrintAndLineFeed();
//
//        esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
//
//        if (printerSetting.getBrandId() == PrinterBrandOptions.EPPOS.getValue()) {
//            esc.addPrintAndFeedLines((byte) 3);
//        } else {
//            esc.addPrintAndFeedLines((byte) 5);
//        }
//        esc.addCutPaper();
//
//        /*
//        //添加缓冲区打印完成查询
//        byte [] bytes={0x1D,0x72,0x01};
//        //添加用户指令
//        esc.addUserCommand(bytes);
//        */
//
//        Vector<Byte> datas = esc.getCommand();
//        boolean result = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].sendDataImmediately(datas);
//        return result;
//        /*
//        byte[] bytes = GpUtils.ByteTo_byte(datas);
//        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
//        int rs;
//        try {
//            rs = gpService.sendEscCommand(printerIdx, sss);
//            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
//            if (r != GpCom.ERROR_CODE.SUCCESS) {
//                Toast.makeText(context, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//        */
//    }
//
//    public static boolean openDrawer(Context context, int printerIdx, Printer printerSetting) {
//
//        return openDrawerData(context, printerIdx, printerSetting);
//    }
//
//    public static boolean printReceipt(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder, boolean isCheckOrder, boolean openDrawer, boolean isReprint, String paymentChargeName) {
//
//        boolean isSuccess = true;
//        for (int i = 0; i < printerSetting.getPrintCopies(); i++) {
//            if (i > 0) openDrawer = false;
//
//            if (i > 0 && isCheckOrder) return isSuccess;
//            isSuccess = printReceiptData(context, printerIdx, store, printerSetting, salesOrder, isCheckOrder, openDrawer, isReprint, paymentChargeName);
//        }
//
//        return isSuccess;
//    }
//
//    public static boolean printPaymentQrCode(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder, Bitmap qrCode, String paymentMode, double paymentAmount) {
//
//        return printPaymentQRCodeData(context, printerIdx, store, printerSetting, salesOrder, qrCode, paymentMode, paymentAmount);
//    }
//
//    public static boolean printReceiptGroup(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder, boolean isReprint) {
//
//        for (int i = 0; i < printerSetting.getPrintCopies(); i++) {
//            if (printerSetting.getName().contains("[LBL]")) {
//                boolean isSuccess = printLabelReceipt(context, printerIdx, store, printerSetting, salesOrder);
//                if (!isSuccess) {
//                    return false;
//                }
//            } else {
//                boolean isSuccess = printReceiptGroupData(context, printerIdx, store, printerSetting, salesOrder, isReprint);
//                if (!isSuccess) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }
//
//    private static boolean printLabelReceipt(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder) {
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers().length <= printerIdx)
//            return false;
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx] == null ||
//                !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].getConnState()) {
//            Toast.makeText(context, String.format(context.getString(R.string.str_printer_not_connected), printerSetting.getName()), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        Calendar calDefault = Calendar.getInstance();
//        calDefault.set(1970, 1, 1);
//
//        NumberFormat numberFormat = NumberFormat.getNumberInstance().getInstance(Locale.getDefault());
//        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
//
//        EscCommand esc = new EscCommand();
//        esc.addInitializePrinter();
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//
//        double totalQty = 0;
//        for (SalesOrderItem orderItem : salesOrder.getActiveOrderItemList()) {
//            boolean isFound = false;
//            if (orderItem.getProductComboId() > 0) {
//                isFound = true;
//            } else {
//                if (printerSetting.getValidProductGroups() != null && printerSetting.getValidProductGroups().size() > 0) {
//                    for (ProductGroup productGroup : printerSetting.getValidProductGroups()) {
//                        if (orderItem.getProductGroupId() == productGroup.getId()) {
//                            isFound = true;
//                            break;
//                        }
//                    }
//                } else {
//                    isFound = true;
//                }
//            }
//
//            if (isFound) {
//                totalQty += orderItem.getQty();
//            }
//        }
//
//        int i = 1;
//        for (SalesOrderItem orderItem : salesOrder.getActiveOrderItemList()) {
//            boolean isFound = false;
//            if (orderItem.getProductComboId() > 0) {
//                isFound = true;
//            } else {
//                if (printerSetting.getValidProductGroups() != null && printerSetting.getValidProductGroups().size() > 0) {
//                    for (ProductGroup productGroup : printerSetting.getValidProductGroups()) {
//                        if (orderItem.getProductGroupId() == productGroup.getId()) {
//                            isFound = true;
//                            break;
//                        }
//                    }
//                } else {
//                    isFound = true;
//                }
//
//            }
//
//            if (isFound) {
//                for (int j = 0; j < orderItem.getQty(); j++) {
//                    esc.addText(Helper.paddingRight(String.format("No: %s", Helper.getQueueNo(store, salesOrder)), printerSetting.getReceiptHeaderLeftCols()));
//                    esc.addText(Helper.paddingLeft(String.format("%s/%s", numberFormat.format(i), numberFormat.format(totalQty)), printerSetting.getReceiptHeaderRightCols()));
//                    esc.addPrintAndLineFeed();
//
//                    if (salesOrder.getCustomerName() != null && salesOrder.getCustomerName().length() > 0) {
//                        esc.addText(String.format("%s : %s", context.getString(R.string.customer), salesOrder.getCustomerName()));
//                        esc.addPrintAndLineFeed();
//                    }
//
//                    if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                        esc.addText(String.format("%s (%s)", orderItem.getItemName(), orderItem.getItemVariantName()));
//                    } else {
//                        esc.addText(orderItem.getItemName());
//                    }
//
//                    esc.addPrintAndLineFeed();
//
//                    if (orderItem.getOrderItemProducts() != null && orderItem.getOrderItemProducts().size() > 0){
//                        for (SalesOrderItemProduct orderItemProduct : orderItem.getOrderItemProducts()) {
//                            if (orderItemProduct.getItemVariantName() != null && orderItemProduct.getItemVariantName().length() > 0) {
//                                esc.addText(String.format("%sx %s (%s)", numberFormat.format(orderItem.getQty() * orderItemProduct.getQty()), orderItemProduct.getItemName(), orderItemProduct.getItemVariantName()));
//                            } else {
//                                esc.addText(String.format("%sx %s", numberFormat.format(orderItem.getQty() * orderItemProduct.getQty()), orderItemProduct.getItemName()));
//                            }
//                            esc.addText("\n");
//                        }
//                    }
//
//                    if (orderItem.getOrderItemAddOns() != null && orderItem.getOrderItemAddOns().size() > 0) {
//                        for (SalesOrderItemAddOn orderItemAddOn : orderItem.getOrderItemAddOns()) {
//                            esc.addText(String.format("* %s", orderItemAddOn.getName()));
//                            esc.addPrintAndLineFeed();
//                        }
//                    }
//
//                    if (orderItem.getFNotes() != null && orderItem.getFNotes().length() > 0) {
//                        esc.addText(String.format("(%s)", orderItem.getFNotes()));
//                        esc.addPrintAndLineFeed();
//                    }
//
//                    esc.addText(dateTimeFormat.format(new Date()));
//                    esc.addPrintAndLineFeed();
//
//                    esc.addPrintAndFeedLines((byte) 5);
//
//                    esc.addCutPaper();
//
//                    i++;
//                }
//            }
//        }
//
//        Vector<Byte> datas = esc.getCommand();
//        boolean result = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].sendDataImmediately(datas);
//        return result;
//
//    }
//
//    public static boolean printAdditionalCancelledReceiptGroup(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder, boolean isAdditional, boolean isCancelled) {
//
//        for (int i = 0; i < printerSetting.getPrintCopies(); i++) {
//            if (isAdditional) {
//                boolean isSuccess = printAdditionalReceiptGroupData(context, printerIdx, store, printerSetting, salesOrder);
//                if (!isSuccess) {
//                    return false;
//                }
//            }
//
//            if (isCancelled) {
//                boolean isSuccess = printCancelledReceiptGroupData(context, printerIdx, store, printerSetting, salesOrder);
//                if (!isSuccess) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }
//
//    public static boolean openDrawerData(Context context, int printerIdx, Printer printerSetting) {
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx] == null ||
//                !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].getConnState()) {
//            Toast.makeText(context, String.format(context.getString(R.string.str_printer_not_connected), printerSetting.getName()), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        EscCommand esc = new EscCommand();
//        esc.addInitializePrinter();
//
//        //Open Drawer
//        esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
//        //esc.addGeneratePlus(LabelCommand.FOOT.F2, (byte) 255, (byte) 255);
//        //esc.addGeneratePluseAtRealtime(LabelCommand.FOOT.F2, (byte)8);
//
//        /*
//        //添加缓冲区打印完成查询
//        byte [] bytes={0x1D,0x72,0x01};
//        //添加用户指令
//        esc.addUserCommand(bytes);
//        */
//
//        Vector<Byte> datas = esc.getCommand();
//        boolean result = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].sendDataImmediately(datas);
//        return result;
//
//        /*
//        byte[] bytes = GpUtils.ByteTo_byte(datas);
//        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
//        int rs;
//        try {
//            rs = gpService.sendEscCommand(printerIdx, sss);
//            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
//            if (r != GpCom.ERROR_CODE.SUCCESS) {
//                Toast.makeText(context, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//        */
//    }
//
//    public static boolean printReceiptData(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder, boolean isCheckOrder, boolean openDrawer, boolean isReprint, String paymentChargeName) {
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx] == null ||
//                !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].getConnState()) {
//            Toast.makeText(context, String.format(context.getString(R.string.str_printer_not_connected), printerSetting.getName()), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        Calendar calDefault = Calendar.getInstance();
//        calDefault.set(1970, 1, 1);
//
//        NumberFormat numberFormat = NumberFormat.getNumberInstance().getInstance(Locale.getDefault());
//        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//        String dotlines = "";
//        for (int i = 0; i < printerSetting.getNumberCols(); i++) {
//            dotlines += "-";
//        }
//
//        EscCommand esc = new EscCommand();
//        esc.addInitializePrinter();
//        esc.addPrintAndFeedLines((byte) 2);
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//
//        if (printerSetting.isWithLogo()) {
//            Bitmap logo = Helper.getLogoBitmap(context, store.getId());
//            if (logo != null) {
//                esc.addRastBitImage(logo, 320, 0);
//            }
//        }
//        esc.addPrintAndLineFeed();
//
//        if (salesOrder.getStatus().equals("X")) {
//            esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//            esc.addText(context.getString(R.string.voided));
//            esc.addPrintAndLineFeed();
//        }
//
//        esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
//        if (isCheckOrder && !store.isReceiptCheckTitleNormal()) {
//            esc.addText(context.getString(R.string.not_receipt));
//        } else {
//            if (isReprint) {
//                esc.addText(context.getString(R.string.reprint));
//                esc.addPrintAndLineFeed();
//            }
//
//            esc.addText(store.getReceiptHeader());
//        }
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        esc.addPrintAndLineFeed();
//
//        if (isCheckOrder) {
//            esc.addText(Helper.paddingRight(context.getString(R.string.order_time), printerSetting.getReceiptHeaderLeftCols()));
//            esc.addText(Helper.paddingLeft(context.getString(R.string.served_by), printerSetting.getReceiptHeaderRightCols()));
//            esc.addPrintAndLineFeed();
//
//            esc.addText(Helper.paddingRight(dateTimeFormat.format(salesOrder.getOrderTime()), printerSetting.getReceiptHeaderLeftCols()));
//            esc.addText(Helper.paddingLeft(salesOrder.getServeByName(), printerSetting.getReceiptHeaderRightCols()));
//        } else {
//            esc.addText(Helper.paddingRight(context.getString(R.string.sale_time), printerSetting.getReceiptHeaderLeftCols()));
//            esc.addText(Helper.paddingLeft(context.getString(R.string.cashier), printerSetting.getReceiptHeaderRightCols()));
//            esc.addPrintAndLineFeed();
//
//            esc.addText(Helper.paddingRight(dateTimeFormat.format(salesOrder.getOrderTime()), printerSetting.getReceiptHeaderLeftCols()));
//            esc.addText(Helper.paddingLeft(salesOrder.getCashierName(), printerSetting.getReceiptHeaderRightCols()));
//        }
//
//        if (printerSetting.getBrandId() == PrinterBrandOptions.PANDA.getValue()) {
//            esc.addPrintAndLineFeed();
//        } else {
//            esc.addPrintAndFeedLines((byte) 2);
//        }
//
//
//        if (salesOrder.getCustomerName() != null && salesOrder.getCustomerName().length() > 0) {
//            esc.addText(String.format("%s : %s", context.getString(R.string.customer), salesOrder.getCustomerName()));
//            if (printerSetting.getBrandId() == PrinterBrandOptions.PANDA.getValue()) {
//                esc.addPrintAndLineFeed();
//            } else {
//                esc.addPrintAndFeedLines((byte) 2);
//            }
//        }
//
//        if (salesOrder.getOrderNo() != null && salesOrder.getOrderNo().length() > 0 && !store.isHideOrderNo()) {
//            esc.addText(Helper.paddingRight(String.format("#%s", salesOrder.getOrderNo()), printerSetting.getReceiptHeaderLeftCols()));
//        } else {
//            esc.addText(Helper.paddingRight("", printerSetting.getReceiptHeaderLeftCols()));
//        }
//
//        if (store.isRestoMode() && salesOrder.getTotalGuest() > 0) {
//            esc.addText(Helper.paddingLeft(String.format("%d %s", salesOrder.getTotalGuest(), context.getString(R.string.guest)), printerSetting.getReceiptHeaderRightCols()));
//        } else if (salesOrder.getRemark() != null && salesOrder.getRemark().length() > 0 && !salesOrder.getRemark().contains("DINE-IN") && !salesOrder.getRemark().contains("TAKE-AWAY") && !salesOrder.getRemark().contains("DELIVERY") && !salesOrder.getRemark().contains("GOFOOD") && !salesOrder.getRemark().contains("GRABFOOD") && !salesOrder.getRemark().contains("SHOPEEFOOD") && !salesOrder.getRemark().contains("TRAVELOKA-EATS")) {
//            esc.addText(Helper.paddingLeft(salesOrder.getRemark(), printerSetting.getReceiptHeaderRightCols()));
//        }
//
//        esc.addPrintAndLineFeed();
//        esc.addText(dotlines);
//        esc.addPrintAndLineFeed();
//
//        if (printerSetting.getBrandId() != PrinterBrandOptions.PANDA.getValue()) {
//            esc.addText(Helper.paddingRight(context.getString(R.string.item), printerSetting.getReceiptHeaderLeftCols()));
//            esc.addText(Helper.paddingLeft(context.getString(R.string.amount), printerSetting.getReceiptHeaderRightCols()));
//            esc.addPrintAndLineFeed();
//
//            esc.addText(dotlines);
//            esc.addPrintAndLineFeed();
//        }
//
//        if (store.isRestoModeOnly() && ((salesOrder.getRemark() != null && (salesOrder.getRemark().contains("DINE-IN") || salesOrder.getRemark().contains("TAKE-AWAY") || salesOrder.getRemark().contains("DELIVERY") || salesOrder.getRemark().contains("GOFOOD") || salesOrder.getRemark().contains("GRABFOOD") || salesOrder.getRemark().contains("SHOPEEFOOD") || salesOrder.getRemark().contains("TRAVELOKA-EATS"))) || (salesOrder.getTableNo() != null && salesOrder.getTableNo().length() > 0) || store.isWithQueue())) {
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//
//            if (salesOrder.getTableNo() != null && salesOrder.getTableNo().length() > 0) {
//                if (salesOrder.getRemark() != null && (salesOrder.getRemark().contains("DINE-IN") || salesOrder.getRemark().contains("TAKE-AWAY") || salesOrder.getRemark().contains("DELIVERY") || salesOrder.getRemark().contains("GOFOOD") || salesOrder.getRemark().contains("GRABFOOD") || salesOrder.getRemark().contains("SHOPEEFOOD") || salesOrder.getRemark().contains("TRAVELOKA-EATS"))) {
//                    esc.addText(String.format("%s (%s)", salesOrder.getRemark(), salesOrder.getTableNo()));
//                } else {
//                    if (store.isWithQueue() && Helper.getQueueNo(store, salesOrder).length() > 0)
//                        esc.addText(String.format("Q No. %s (%s)", Helper.getQueueNo(store, salesOrder), salesOrder.getTableNo()));
//                    else
//                        esc.addText(salesOrder.getTableNo());
//                }
//            } else {
//                /*
//                if (salesOrder.getCustomerId() == 0 && salesOrder.getCustomerName() != null && salesOrder.getCustomerName().length() > 0){
//                    esc.addText(String.format("%s (%s)", salesOrder.getRemark(), salesOrder.getCustomerName()));
//                }
//                else {
//                    esc.addText(salesOrder.getRemark());
//                }
//                */
//                if (store.isWithQueue() && Helper.getQueueNo(store, salesOrder).length() > 0) {
//                    if (salesOrder.getRemark() != null && (salesOrder.getRemark().contains("DINE-IN") || salesOrder.getRemark().contains("TAKE-AWAY") || salesOrder.getRemark().contains("DELIVERY") || salesOrder.getRemark().contains("GOFOOD") || salesOrder.getRemark().contains("GRABFOOD") || salesOrder.getRemark().contains("SHOPEEFOOD") || salesOrder.getRemark().contains("TRAVELOKA-EATS")))
//                        esc.addText(String.format("Q No. %s (%s)", Helper.getQueueNo(store, salesOrder), salesOrder.getRemark()));
//                    else
//                        esc.addText(String.format("Q No. %s", Helper.getQueueNo(store, salesOrder)));
//                } else
//                    esc.addText(salesOrder.getRemark());
//            }
//
//            esc.addPrintAndLineFeed();
//
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        }
//
//        double totalQty = 0;
//
//        if (printerSetting.getName() != null && printerSetting.getName().length() > 0 && printerSetting.getName().contains("[MOD]")) {
//            //by mod
//            List<String> cashiers = salesOrder.getCashiersMod();
//            int position = 0;
//            String cashierName = "";
//            for (SalesOrderItem orderItem : salesOrder.getActiveOrderItemsByModifiedBy(cashiers)){
//                totalQty += orderItem.getQty();
//                String tempCashiersName = salesOrder.getCashierName(orderItem);
//                boolean isSame = false;
//
//                if (!cashierName.equals(tempCashiersName)) {
//                    cashierName = tempCashiersName;
//                } else {
//                    isSame = true;
//                }
//
//                if (!isSame){
//                    if (position == 0) {
//                        esc.addPrintAndLineFeed();
//                    }
//                    esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//                    esc.addText(dotlines);
//                    esc.addPrintAndLineFeed();
//
//                    esc.addText(cashierName.toUpperCase());
//
//                    esc.addPrintAndLineFeed();
//                    esc.addText(dotlines);
//                    esc.addPrintAndLineFeed();
//                }
//
//                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//
//                if (store.isReceiptCompact()) {
//                    String itemName = orderItem.getItemName();
//                    if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                        itemName = String.format("%s - %s", orderItem.getItemName(), orderItem.getItemVariantName());
//                    }
//
//                    if (itemName.length() > printerSetting.getReceiptCompactItemCols()) {
//                        itemName = itemName.substring(0, printerSetting.getReceiptCompactItemCols() - 1);
//                    }
//
//                    esc.addText(Helper.paddingLeft(String.format("%s ", numberFormat.format(orderItem.getQty())), printerSetting.getReceiptCompactQtyCols()));
//                    esc.addText(Helper.paddingRight(itemName, printerSetting.getReceiptCompactItemCols()));
//                    esc.addText(Helper.paddingLeft(numberFormat.format(orderItem.getAmount()), printerSetting.getReceiptCompactAmountCols()));
//                    esc.addPrintAndLineFeed();
//                } else {
//                    if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                        esc.addText(String.format("%s\n%s", orderItem.getItemName(), orderItem.getItemVariantName()));
//                    } else {
//                        esc.addText(orderItem.getItemName());
//                    }
//                    esc.addPrintAndLineFeed();
//
//                    if (orderItem.getSerialNo() != null && orderItem.getSerialNo().length() > 0) {
//                        esc.addText(String.format("%s : %s", context.getString(R.string.serial_no), orderItem.getSerialNo()));
//                        esc.addPrintAndLineFeed();
//                    }
//
//                    if ((!store.isRestoMode() || store.isReceiptPrintItemNotes()) && orderItem.getNotes() != null && orderItem.getNotes().length() > 0) {
//                        esc.addText(orderItem.getNotes());
//                        esc.addPrintAndLineFeed();
//                    }
//
//
//                    esc.addText(Helper.paddingLeft(String.format("%s ", numberFormat.format(orderItem.getPrice())), printerSetting.getReceiptItemPriceCols()));
//                    esc.addText(Helper.paddingRight(String.format("x%s", numberFormat.format(orderItem.getQty())), printerSetting.getReceiptItemQtyCols()));
//
//                    esc.addText(Helper.paddingLeft(numberFormat.format(orderItem.getAmount()), printerSetting.getReceiptItemAmountCols()));
//                    esc.addPrintAndLineFeed();
//                }
//
//                if (!store.isReceiptHideComboItems()) {
//                    if (orderItem.getOrderItemProducts() != null && orderItem.getOrderItemProducts().size() > 0) {
//                        for (SalesOrderItemProduct orderItemProduct : orderItem.getOrderItemProducts()) {
//                            if (orderItemProduct.getItemVariantName() != null && orderItemProduct.getItemVariantName().length() > 0) {
//                                esc.addText(String.format("%sx %s (%s)", numberFormat.format(orderItem.getQty() * orderItemProduct.getQty()), orderItemProduct.getItemName(), orderItemProduct.getItemVariantName()));
//                            } else {
//                                esc.addText(String.format("%sx %s", numberFormat.format(orderItem.getQty() * orderItemProduct.getQty()), orderItemProduct.getItemName()));
//                            }
//                            esc.addPrintAndLineFeed();
//                        }
//                    }
//                }
//
//                if (orderItem.getOrderItemAddOns() != null && orderItem.getOrderItemAddOns().size() > 0) {
//                    for (SalesOrderItemAddOn orderItemAddOn : orderItem.getOrderItemAddOns()) {
//                        esc.addText(Helper.paddingLeft(String.format("+%s ", numberFormat.format(orderItemAddOn.getPrice())), printerSetting.getReceiptItemPriceCols()));
//                        esc.addText(Helper.paddingRight(String.format("x%s %s", numberFormat.format(orderItem.getQty()), orderItemAddOn.getName()), printerSetting.getReceiptItemQtyAmountCols()));
//                        esc.addPrintAndLineFeed();
//                    }
//                }
//
//                if (orderItem.getDiscountAmount() > 0) {
//                    String discountName = Helper.getOrderItemDiscountName(orderItem);
//                    if (orderItem.getDiscountRate() > 0) {
//                        esc.addText(String.format("%s -%s (%s%%)", discountName, numberFormat.format(orderItem.getDiscountAmount()), numberFormat.format(orderItem.getDiscountPct())));
//                    } else {
//                        esc.addText(String.format("%s -%s", discountName, numberFormat.format(orderItem.getDiscountAmount())));
//                    }
//
//                    esc.addPrintAndLineFeed();
//                }
//                position++;
//            }
//        } else {
//            //by default
//            for (SalesOrderItem orderItem : salesOrder.getReceiptActiveOrderItemList(store.isReceiptMergeQty())) {
//                totalQty += orderItem.getQty();
//
//                if (store.isReceiptCompact()) {
//                    String itemName = orderItem.getItemName();
//                    if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                        itemName = String.format("%s - %s", orderItem.getItemName(), orderItem.getItemVariantName());
//                    }
//
//                    if (itemName.length() > printerSetting.getReceiptCompactItemCols()) {
//                        itemName = itemName.substring(0, printerSetting.getReceiptCompactItemCols() - 1);
//                    }
//
//                    esc.addText(Helper.paddingLeft(String.format("%s ", numberFormat.format(orderItem.getQty())), printerSetting.getReceiptCompactQtyCols()));
//                    esc.addText(Helper.paddingRight(itemName, printerSetting.getReceiptCompactItemCols()));
//                    esc.addText(Helper.paddingLeft(numberFormat.format(orderItem.getAmount()), printerSetting.getReceiptCompactAmountCols()));
//                    esc.addPrintAndLineFeed();
//                } else {
//                    if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                        esc.addText(String.format("%s\n%s", orderItem.getItemName(), orderItem.getItemVariantName()));
//                    } else {
//                        esc.addText(orderItem.getItemName());
//                    }
//                    esc.addPrintAndLineFeed();
//
//                    if (orderItem.getSerialNo() != null && orderItem.getSerialNo().length() > 0) {
//                        esc.addText(String.format("%s : %s", context.getString(R.string.serial_no), orderItem.getSerialNo()));
//                        esc.addPrintAndLineFeed();
//                    }
//
//                    if ((!store.isRestoMode() || store.isReceiptPrintItemNotes()) && orderItem.getNotes() != null && orderItem.getNotes().length() > 0) {
//                        esc.addText(orderItem.getNotes());
//                        esc.addPrintAndLineFeed();
//                    }
//
//
//                    esc.addText(Helper.paddingLeft(String.format("%s ", numberFormat.format(orderItem.getPrice())), printerSetting.getReceiptItemPriceCols()));
//                    esc.addText(Helper.paddingRight(String.format("x%s", numberFormat.format(orderItem.getQty())), printerSetting.getReceiptItemQtyCols()));
//
//                    esc.addText(Helper.paddingLeft(numberFormat.format(orderItem.getAmount()), printerSetting.getReceiptItemAmountCols()));
//                    esc.addPrintAndLineFeed();
//                }
//
//                if (!store.isReceiptHideComboItems()) {
//                    if (orderItem.getOrderItemProducts() != null && orderItem.getOrderItemProducts().size() > 0) {
//                        for (SalesOrderItemProduct orderItemProduct : orderItem.getOrderItemProducts()) {
//                            if (orderItemProduct.getItemVariantName() != null && orderItemProduct.getItemVariantName().length() > 0) {
//                                esc.addText(String.format("%sx %s (%s)", numberFormat.format(orderItem.getQty() * orderItemProduct.getQty()), orderItemProduct.getItemName(), orderItemProduct.getItemVariantName()));
//                            } else {
//                                esc.addText(String.format("%sx %s", numberFormat.format(orderItem.getQty() * orderItemProduct.getQty()), orderItemProduct.getItemName()));
//                            }
//                            esc.addPrintAndLineFeed();
//                        }
//                    }
//                }
//
//                if (orderItem.getOrderItemAddOns() != null && orderItem.getOrderItemAddOns().size() > 0) {
//                    for (SalesOrderItemAddOn orderItemAddOn : orderItem.getOrderItemAddOns()) {
//                        esc.addText(Helper.paddingLeft(String.format("+%s ", numberFormat.format(orderItemAddOn.getPrice())), printerSetting.getReceiptItemPriceCols()));
//                        esc.addText(Helper.paddingRight(String.format("x%s %s", numberFormat.format(orderItem.getQty()), orderItemAddOn.getName()), printerSetting.getReceiptItemQtyAmountCols()));
//                        esc.addPrintAndLineFeed();
//                    }
//                }
//
//                if (orderItem.getDiscountAmount() > 0) {
//                    String discountName = Helper.getOrderItemDiscountName(orderItem);
//                    if (orderItem.getDiscountRate() > 0) {
//                        esc.addText(String.format("%s -%s (%s%%)", discountName, numberFormat.format(orderItem.getDiscountAmount()), numberFormat.format(orderItem.getDiscountPct())));
//                    } else {
//                        esc.addText(String.format("%s -%s", discountName, numberFormat.format(orderItem.getDiscountAmount())));
//                    }
//
//                    esc.addPrintAndLineFeed();
//                }
//            }
//        }
//
//        esc.addText(dotlines);
//        esc.addPrintAndLineFeed();
//
//        esc.addText(Helper.paddingRight(context.getString(R.string.subtotal), printerSetting.getReceiptSummaryLabelCols()));
//        esc.addText(Helper.paddingLeft(numberFormat.format(salesOrder.getOrderAmount()), printerSetting.getReceiptSummaryAmountCols()));
//        esc.addPrintAndLineFeed();
//
//        if (salesOrder.getDiscountAmount() > 0) {
//            if (salesOrder.getDiscountName() != null && salesOrder.getDiscountName().length() > 0) {
//                esc.addText(Helper.paddingRight(salesOrder.getDiscountName(), printerSetting.getReceiptSummaryLabelCols()));
//            } else if (salesOrder.getDiscountPct() > 0) {
//                esc.addText(Helper.paddingRight(String.format("Disc. %s%%", numberFormat.format(salesOrder.getDiscountPct())), printerSetting.getReceiptSummaryLabelCols()));
//            } else {
//                esc.addText(Helper.paddingRight("Disc. ", printerSetting.getReceiptSummaryLabelCols()));
//
//            }
//
//            esc.addText(Helper.paddingLeft(String.format("-%s", numberFormat.format(salesOrder.getDiscountAmount())), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//        }
//
//
//        if (salesOrder.getServiceChargeAmount() > 0) {
//            if (store.getServiceChargeName() != null && store.getServiceChargeName().length() > 0)
//                esc.addText(Helper.paddingRight(String.format("%s %s%%", store.getServiceChargeName(), numberFormat.format(salesOrder.getServiceChargePct())), printerSetting.getReceiptSummaryLabelCols()));
//            else
//                esc.addText(Helper.paddingRight(String.format("%s %s%%", context.getString(R.string.service_charge), numberFormat.format(salesOrder.getServiceChargePct())), printerSetting.getReceiptSummaryLabelCols()));
//
//            esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getServiceChargeAmount())), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//        }
//
//        if (salesOrder.getShippingCost() > 0) {
//            esc.addText(Helper.paddingRight(context.getString(R.string.shipping), printerSetting.getReceiptSummaryLabelCols()));
//            esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getShippingCost())), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//        }
//
//        if (salesOrder.getTaxAmount() > 0) {
//            esc.addText(Helper.paddingRight(String.format("%s %s%%", store.getTaxName(), numberFormat.format(salesOrder.getTaxPct())), printerSetting.getReceiptSummaryLabelCols()));
//            esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getTaxAmount())), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//        }
//
//        if (salesOrder.getRedeemedAmount() > 0) {
//            esc.addText(Helper.paddingRight(String.format("%s -%s pts", context.getString(R.string.redeemed), numberFormat.format(salesOrder.getRedeemedPoints())), printerSetting.getReceiptSummaryLabelCols()));
//            esc.addText(Helper.paddingLeft(String.format("-%s", numberFormat.format(salesOrder.getRedeemedAmount())), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//        }
//
//        if (salesOrder.getPaymentChargeRate() == 0 && salesOrder.getPaymentChargeAmount() > 0) {
//            String paymentchargeInfo = String.format("%s", context.getString(R.string.payment_charge));
//
//            esc.addText(Helper.paddingRight(paymentchargeInfo, printerSetting.getReceiptSummaryLabelCols()));
//            esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getPaymentChargeAmount())), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//        }
//
//        if (salesOrder.getPaymentChargeAmount() == 0) {
//            double transFeeAmount = salesOrder.getOnlineTransFeeAmount();
//            if (transFeeAmount > 0) {
//                String paymentchargeInfo = String.format("%s", context.getString(R.string.payment_charge));
//
//                esc.addText(Helper.paddingRight(paymentchargeInfo, printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(transFeeAmount)), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//            }
//        }
//
//        if (salesOrder.getRoundedAmount() != 0) {
//            esc.addText(Helper.paddingRight(context.getString(R.string.rounding), printerSetting.getReceiptSummaryLabelCols()));
//            esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getRoundedAmount() * -1)), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//        }
//
//        if (salesOrder.getPaymentChargeRate() > 0 && salesOrder.getPaymentChargeAmount() > 0) {
//            String paymentchargeInfo = String.format("%s %s%%", paymentChargeName, salesOrder.getPaymentChargePct());
//
//            esc.addText(Helper.paddingRight(paymentchargeInfo, printerSetting.getReceiptSummaryLabelCols()));
//            esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getPaymentChargeAmount())), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//        }
//
//        esc.addPrintAndLineFeed();
//
//        esc.addText(Helper.paddingRight(context.getString(R.string.grand_total), printerSetting.getReceiptSummaryLabelCols()));
//        esc.addText(Helper.paddingLeft(String.format("%s %s", salesOrder.getCurrencySymbol(), numberFormat.format(salesOrder.getTotalAmount())), printerSetting.getReceiptSummaryAmountCols()));
//        esc.addPrintAndLineFeed();
//
//
//        if (salesOrder.getPaymentModeId() > 0) {
//
//            double grabFundPromo = salesOrder.getGrabFundPromo();
//            if (grabFundPromo > 0) {
//                esc.addText(Helper.paddingRight("Grab Promo", printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(grabFundPromo * -1)), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//            }
//
//            esc.addText(Helper.paddingRight(salesOrder.getPaymentModeName(), printerSetting.getReceiptSummaryLabelCols()));
//
//            double paymentAmount = salesOrder.getPaymentAmount();
//            if (salesOrder.getPaymentModeId() == 1 && salesOrder.getPaymentModeId2() == 0) {
//                paymentAmount = salesOrder.getTenderedAmount();
//            }
//
//            if (salesOrder.getShippingCost() >= 0) {
//                paymentAmount += salesOrder.getShippingCost();
//            }
//
//            if (paymentAmount - grabFundPromo >= 0) {
//                paymentAmount -= grabFundPromo;
//            }
//
//            esc.addText(Helper.paddingLeft(String.format("%s %s", salesOrder.getCurrencySymbol(), numberFormat.format(paymentAmount)), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//
//            if (salesOrder.getPaymentRef() != null && salesOrder.getPaymentRef().length() > 0) {
//                esc.addText(String.format("Ref : %s", salesOrder.getPaymentRef()));
//                esc.addPrintAndLineFeed();
//            }
//
//            if (salesOrder.getPaymentDueDate() != null && salesOrder.getPaymentDueDate().after(calDefault.getTime())) {
//                esc.addText(String.format("%s : %s", context.getString(R.string.payment_due_date), dateFormat.format(salesOrder.getPaymentDueDate())));
//                esc.addPrintAndLineFeed();
//            }
//
//            if (salesOrder.getPaymentModeId2() > 0) {
//                esc.addText(Helper.paddingRight(salesOrder.getPaymentModeName2(), printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s %s", salesOrder.getCurrencySymbol(), numberFormat.format(salesOrder.getPaymentAmount2())), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//
//                if (salesOrder.getPaymentRef2() != null && salesOrder.getPaymentRef2().length() > 0) {
//                    esc.addText(String.format("Ref : %s", salesOrder.getPaymentRef2()));
//                    esc.addPrintAndLineFeed();
//                }
//            }
//
//            if (salesOrder.getCreditPaymentAmount() > 0) {
//                for (CreditPayment creditPayment : salesOrder.getCreditPayments()) {
//                    esc.addText(Helper.paddingRight(creditPayment.getPaymentModeName(), printerSetting.getReceiptSummaryLabelCols()));
//                    esc.addText(Helper.paddingLeft(String.format("%s %s", salesOrder.getCurrencySymbol(), numberFormat.format(creditPayment.getPaymentAmount())), printerSetting.getReceiptSummaryAmountCols()));
//                    esc.addPrintAndLineFeed();
//
//                    esc.addText(String.format("%s : %s", context.getString(R.string.payment_date), dateFormat.format(creditPayment.getPaymentDate())));
//                    esc.addPrintAndLineFeed();
//                }
//
//                double outstandingAmount = salesOrder.getTotalAmount() - salesOrder.getCreditPaymentAmount();
//
//                esc.addText(Helper.paddingRight(context.getString(R.string.outstanding_amount), printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s %s", salesOrder.getCurrencySymbol(), numberFormat.format(outstandingAmount)), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//            }
//
//            if (salesOrder.getChangeAmount() > 0) {
//                esc.addText(Helper.paddingRight(context.getString(R.string.change_due), printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s %s", salesOrder.getCurrencySymbol(), numberFormat.format(salesOrder.getChangeAmount())), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//            }
//
//            if (store.isPriceTaxIncluded() && salesOrder.getTaxAmount() == 0 && store.getTaxPercent() > 0) {
//                esc.addPrintAndLineFeed();
//                if (printerSetting.getName().contains("[HT]")) {
//                    esc.addText(String.format("%s %s", context.getString(R.string.inclusive), store.getTaxName()));
//                } else {
//                    esc.addText(String.format("%s %s %s%% (%s %s)", context.getString(R.string.inclusive), store.getTaxName(), numberFormat.format(store.getTaxPercent()), salesOrder.getCurrencySymbol(), numberFormat.format(salesOrder.getInclusiveTaxAmount(store))));
//                }
//                esc.addPrintAndLineFeed();
//            }
//
//            if (store.isReceiptPrintNumberItems()) {
//                String qtyItems = String.format("%s: %s", context.getString(R.string.number_of_items), numberFormat.format(totalQty));
//                esc.addPrintAndLineFeed();
//                esc.addText(qtyItems);
//                esc.addPrintAndLineFeed();
//            }
//
//            if (salesOrder.getNotes() != null && salesOrder.getNotes().length() > 0) {
//                esc.addPrintAndLineFeed();
//                esc.addText(salesOrder.getNotes());
//                esc.addPrintAndLineFeed();
//            }
//
//            if (store.isShowCollect()) {
//                String collectionInfo = salesOrder.getCollectionInfo(context);
//                if (collectionInfo != null && collectionInfo.length() > 0) {
//                    esc.addPrintAndLineFeed();
//                    esc.addText(collectionInfo);
//                }
//            }
//
//            if (salesOrder.getShippingAddress() != null && salesOrder.getShippingAddress().length() > 0) {
//                esc.addPrintAndLineFeed();
//                esc.addText(String.format("%s :", context.getString(R.string.shipping_to)));
//                if (salesOrder.getShippingTo() != null && salesOrder.getShippingTo().length() > 0) {
//                    esc.addPrintAndLineFeed();
//                    if (salesOrder.getShippingPhone() != null && salesOrder.getShippingPhone().length() > 0) {
//                        esc.addText(String.format("%s (%s)", salesOrder.getShippingTo(), salesOrder.getShippingPhone()));
//                    } else {
//                        esc.addText(String.format("%s", salesOrder.getShippingTo()));
//                    }
//                }
//                esc.addPrintAndLineFeed();
//                esc.addText(String.format("%s", salesOrder.getShippingAddress()));
//
//                if (salesOrder.getShippingSubdistrictName() != null && salesOrder.getShippingSubdistrictName().length() > 0) {
//                    esc.addPrintAndLineFeed();
//                    esc.addText(String.format("%s", salesOrder.getShippingSubdistrictName()));
//                }
//
//                if (salesOrder.getShippingCityName() != null && salesOrder.getShippingCityName().length() > 0) {
//                    esc.addPrintAndLineFeed();
//                    esc.addText(String.format("%s", salesOrder.getShippingCityName()));
//                }
//
//                if (salesOrder.getShippingPostalCode() != null && salesOrder.getShippingPostalCode().length() > 0) {
//                    esc.addText(String.format(" %s", salesOrder.getShippingPostalCode()));
//                }
//
//                esc.addPrintAndLineFeed();
//            }
//
//            if (store.isWithLPoint() && salesOrder.getCustomerId() > 0) {
//                if (salesOrder.getBeginningPoints() > 0 || salesOrder.getBalancePoints() > 0) {
//                    esc.addPrintAndLineFeed();
//
//                    esc.addText(Helper.paddingRight(context.getString(R.string.beginning_points), printerSetting.getReceiptSummaryLabelCols()));
//                    esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getBeginningPoints())), printerSetting.getReceiptSummaryAmountCols()));
//                    esc.addPrintAndLineFeed();
//
//                    esc.addText(Helper.paddingRight(context.getString(R.string.earned_points), printerSetting.getReceiptSummaryLabelCols()));
//                    esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getEarnedPoints())), printerSetting.getReceiptSummaryAmountCols()));
//                    esc.addPrintAndLineFeed();
//
//                    if (salesOrder.getRedeemedPoints() > 0) {
//                        esc.addText(Helper.paddingRight(context.getString(R.string.redeemed_points), printerSetting.getReceiptSummaryLabelCols()));
//                        esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getRedeemedPoints())), printerSetting.getReceiptSummaryAmountCols()));
//                        esc.addPrintAndLineFeed();
//                    }
//
//                    esc.addText(Helper.paddingRight(context.getString(R.string.total_points), printerSetting.getReceiptSummaryLabelCols()));
//                    esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(salesOrder.getBalancePoints())), printerSetting.getReceiptSummaryAmountCols()));
//                    esc.addPrintAndLineFeed();
//                    esc.addPrintAndLineFeed();
//                }
//            }
//
//            if (salesOrder.isDeposit()) {
//                DepositSummary depositSummary = salesOrder.getDepositSummary();
//
//                int beginningDeposit = 0;
//                int valueDeposit = 0;
//                int balanceDeposit = 0;
//                try { beginningDeposit = depositSummary.getBeginning(); } catch(Exception ignored) {}
//                try { valueDeposit = depositSummary.getTopup(); } catch(Exception ignored) {}
//                try { balanceDeposit = depositSummary.getBalance(); } catch(Exception ignored) {}
//
//                esc.addText(Helper.paddingRight(context.getString(R.string.beginning_deposit), printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(beginningDeposit)), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//
//                esc.addText(Helper.paddingRight(context.getString(R.string.topup_deposit), printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(valueDeposit)), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//
//                esc.addText(Helper.paddingRight(context.getString(R.string.total_deposit), printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(balanceDeposit)), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//            }
//
//            if (salesOrder.getPaymentModeName().equals(Constant.PAYMENT_DEPOSIT)) {
//                DepositSummary depositSummary = salesOrder.getDepositSummary();
//
//                int beginningDeposit = 0;
//                int valueDeposit = 0;
//                int balanceDeposit = 0;
//                try { beginningDeposit = depositSummary.getBeginning(); } catch(Exception ignored) {}
//                try { valueDeposit = depositSummary.getTopup(); } catch(Exception ignored) {}
//                try { balanceDeposit = depositSummary.getBalance(); } catch(Exception ignored) {}
//
//                esc.addText(Helper.paddingRight(context.getString(R.string.beginning_deposit), printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(beginningDeposit)), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//
//                esc.addText(Helper.paddingRight(context.getString(R.string.redeemed_deposit), printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(valueDeposit)), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//
//                esc.addText(Helper.paddingRight(context.getString(R.string.total_deposit), printerSetting.getReceiptSummaryLabelCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s", numberFormat.format(balanceDeposit)), printerSetting.getReceiptSummaryAmountCols()));
//                esc.addPrintAndLineFeed();
//            }
//
//            if (store.isTrackServer() || salesOrder.getServeById() != salesOrder.getCashierId()) {
//                esc.addPrintAndLineFeed();
//                esc.addText(String.format("%s : %s", context.getString(R.string.served_by), salesOrder.getServeByName()));
//                esc.addPrintAndLineFeed();
//            }
//
//            esc.addPrintAndLineFeed();
//
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//
//            esc.addText(store.getReceiptFooter());
//        } else {
//            if (store.isReceiptPrintNumberItems()) {
//                String qtyItems = String.format("%s: %s", context.getString(R.string.number_of_items), numberFormat.format(totalQty));
//                esc.addPrintAndLineFeed();
//                esc.addText(qtyItems);
//                esc.addPrintAndLineFeed();
//            }
//
//            if (salesOrder.getNotes() != null && salesOrder.getNotes().length() > 0) {
//                esc.addPrintAndLineFeed();
//                esc.addText(salesOrder.getNotes());
//                esc.addPrintAndLineFeed();
//            }
//
//            if (store.isShowCollect()) {
//                String collectionInfo = salesOrder.getCollectionInfo(context);
//                if (collectionInfo != null && collectionInfo.length() > 0) {
//                    esc.addPrintAndLineFeed();
//                    esc.addText(collectionInfo);
//                }
//            }
//
//            if (salesOrder.getShippingAddress() != null && salesOrder.getShippingAddress().length() > 0) {
//                esc.addPrintAndLineFeed();
//                esc.addText(String.format("%s :", context.getString(R.string.shipping_to)));
//                if (salesOrder.getShippingTo() != null && salesOrder.getShippingTo().length() > 0) {
//                    esc.addPrintAndLineFeed();
//                    if (salesOrder.getShippingPhone() != null && salesOrder.getShippingPhone().length() > 0) {
//                        esc.addText(String.format("%s (%s)", salesOrder.getShippingTo(), salesOrder.getShippingPhone()));
//                    } else {
//                        esc.addText(String.format("%s", salesOrder.getShippingTo()));
//                    }
//                }
//                esc.addPrintAndLineFeed();
//                esc.addText(String.format("%s", salesOrder.getShippingAddress()));
//
//                if (salesOrder.getShippingSubdistrictName() != null && salesOrder.getShippingSubdistrictName().length() > 0) {
//                    esc.addPrintAndLineFeed();
//                    esc.addText(String.format("%s", salesOrder.getShippingSubdistrictName()));
//                }
//
//                if (salesOrder.getShippingCityName() != null && salesOrder.getShippingCityName().length() > 0) {
//                    esc.addPrintAndLineFeed();
//                    esc.addText(String.format("%s", salesOrder.getShippingCityName()));
//                }
//
//                if (salesOrder.getShippingPostalCode() != null && salesOrder.getShippingPostalCode().length() > 0) {
//                    esc.addText(String.format(" %s", salesOrder.getShippingPostalCode()));
//                }
//
//                esc.addPrintAndLineFeed();
//            }
//        }
//
//        if (((salesOrder.getPaymentModeId() == 1 && salesOrder.getPaymentAmount() > 0) || (salesOrder.getPaymentModeId2() == 1 && salesOrder.getPaymentAmount2() > 0)) && openDrawer) {
//            if (!(isReprint && store.isDisableReprintOpenDrawer())) {
//                esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
//            }
//        }
//
//        if (printerSetting.getBrandId() == PrinterBrandOptions.EPPOS.getValue()) {
//            esc.addPrintAndFeedLines((byte) 3);
//        } else {
//            esc.addPrintAndFeedLines((byte) 5);
//        }
//
//        esc.addCutPaper();
//
//        /*
//        //添加缓冲区打印完成查询
//        byte [] bytes={0x1D,0x72,0x01};
//        //添加用户指令
//        esc.addUserCommand(bytes);
//        */
//
//        Vector<Byte> datas = esc.getCommand();
//        boolean result = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].sendDataImmediately(datas);
//        return result;
//
//        /*
//        byte[] bytes = GpUtils.ByteTo_byte(datas);
//        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
//        int rs;
//        try {
//            rs = gpService.sendEscCommand(printerIdx, sss);
//            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
//            if (r != GpCom.ERROR_CODE.SUCCESS) {
//                Toast.makeText(context, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//        */
//    }
//
//    public static boolean printPaymentQRCodeData(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder, Bitmap qrCode, String paymentMode, double paymentAmount) {
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx] == null ||
//                !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].getConnState()) {
//            Toast.makeText(context, String.format(context.getString(R.string.str_printer_not_connected), printerSetting.getName()), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        Calendar calDefault = Calendar.getInstance();
//        calDefault.set(1970, 1, 1);
//
//        NumberFormat numberFormat = NumberFormat.getNumberInstance().getInstance(Locale.getDefault());
//        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//        String dotlines = "";
//        for (int i = 0; i < printerSetting.getNumberCols(); i++) {
//            dotlines += "-";
//        }
//
//        EscCommand esc = new EscCommand();
//        esc.addInitializePrinter();
//        esc.addPrintAndFeedLines((byte) 2);
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//        esc.addPrintAndLineFeed();
//
//        esc.addText(store.getReceiptHeader());
//        esc.addPrintAndLineFeed();
//
//        esc.addText(dotlines);
//        esc.addPrintAndLineFeed();
//
//        esc.addRastBitImage(qrCode, 320, 0);
//        esc.addPrintAndLineFeed();
//
//        esc.addText(paymentMode);
//        esc.addPrintAndLineFeed();
//
//        esc.addText(String.format("%s %s", salesOrder.getCurrencyId(), numberFormat.format(paymentAmount)));
//        esc.addPrintAndLineFeed();
//
//        if (printerSetting.getBrandId() == PrinterBrandOptions.EPPOS.getValue()) {
//            esc.addPrintAndFeedLines((byte) 3);
//        } else {
//            esc.addPrintAndFeedLines((byte) 5);
//        }
//
//        esc.addCutPaper();
//
//        Vector<Byte> datas = esc.getCommand();
//        boolean result = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].sendDataImmediately(datas);
//        return result;
//
//        /*
//        byte[] bytes = GpUtils.ByteTo_byte(datas);
//        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
//        int rs;
//        try {
//            rs = gpService.sendEscCommand(printerIdx, sss);
//            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
//            if (r != GpCom.ERROR_CODE.SUCCESS) {
//                Toast.makeText(context, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//        */
//    }
//
//    public static boolean printReceiptGroupData(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder, boolean isReprint) {
//        return printReceiptGroupData(context, printerIdx, store, printerSetting, salesOrder, false, false, isReprint);
//    }
//
//    public static boolean printAdditionalReceiptGroupData(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder) {
//        return printReceiptGroupData(context, printerIdx, store, printerSetting, salesOrder, true, false, false);
//    }
//
//    public static boolean printCancelledReceiptGroupData(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder) {
//        return printReceiptGroupData(context, printerIdx, store, printerSetting, salesOrder, false, true, false);
//    }
//
//    private static void populateOrderItemReceiptGroupTextData(Context context, Printer printerSetting, NumberFormat numberFormat, SalesOrderItem orderItem, EscCommand esc, String bigdotlines) {
//        if (orderItem.getProductComboId() > 0) {
//            boolean isFound = false;
//            for (SalesOrderItemProduct orderItemProduct : orderItem.getOrderItemProducts()) {
//                if (printerSetting.getValidProductGroups() != null && printerSetting.getValidProductGroups().size() > 0) {
//                    for (ProductGroup productGroup : printerSetting.getValidProductGroups()) {
//                        if (productGroup.getId() == orderItemProduct.getProductGroupId()) {
//                            if (!isFound) {
//                                //esc.addPrintAndLineFeed();
//                                if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                                    esc.addText(String.format("%s (%s)", orderItem.getItemName(), orderItem.getItemVariantName()));
//                                } else {
//                                    esc.addText(String.format("%s", orderItem.getItemName()));
//                                }
//                            }
//
//                            isFound = true;
//                            break;
//                        }
//                    }
//                } else {
//                    if (!isFound) {
//                        //esc.addPrintAndLineFeed();
//                        if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                            esc.addText(String.format("%s (%s)", orderItem.getItemName(), orderItem.getItemVariantName()));
//                        } else {
//                            esc.addText(String.format("%s", orderItem.getItemName()));
//                        }
//                    }
//
//                    isFound = true;
//                }
//
//                if (isFound) {
//                    boolean isProductGroupValid = false;
//
//                    if (printerSetting.getValidProductGroups() != null && printerSetting.getValidProductGroups().size() > 0) {
//                        for (ProductGroup productGroup : printerSetting.getValidProductGroups()) {
//                            if (orderItemProduct.getProductGroupId() == productGroup.getId()) {
//                                isProductGroupValid = true;
//                                break;
//                            }
//                        }
//                    } else {
//                        isProductGroupValid = true;
//                    }
//
//                    if (isProductGroupValid) {
//                        esc.addPrintAndLineFeed();
//                        if (orderItemProduct.getItemVariantName() != null && orderItemProduct.getItemVariantName().length() > 0) {
//                            esc.addText(String.format("   %sx %s (%s)", numberFormat.format(orderItemProduct.getQty() * orderItem.getQty()), orderItemProduct.getItemName(), orderItemProduct.getItemVariantName()));
//                        } else {
//                            esc.addText(String.format("   %sx %s", numberFormat.format(orderItemProduct.getQty() * orderItem.getQty()), orderItemProduct.getItemName()));
//                        }
//
//                        if (orderItem.getOrderItemAddOns() != null && orderItem.getOrderItemAddOns().size() > 0) {
//                            for (SalesOrderItemAddOn orderItemAddOn : orderItem.getOrderItemAddOns()) {
//                                if (orderItemAddOn.getProductId() == orderItemProduct.getProductId()) {
//                                    esc.addPrintAndLineFeed();
//                                    esc.addText(String.format("   +%s", orderItemAddOn.getName()));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (isFound) {
//                if (orderItem.getFNotes() != null && orderItem.getFNotes().length() > 0) {
//                    esc.addPrintAndLineFeed();
//                    esc.addText(context.getString(R.string.notes));
//                    esc.addPrintAndLineFeed();
//                    esc.addText(orderItem.getFNotes());
//
//                    //esc.addPrintAndLineFeed();
//                }
//
//                esc.addPrintAndLineFeed();
//            }
//        } else {
//            boolean isFound = false;
//
//            if (printerSetting.getValidProductGroups() != null && printerSetting.getValidProductGroups().size() > 0) {
//                for (ProductGroup productGroup : printerSetting.getValidProductGroups()) {
//                    if (orderItem.getProductGroupId() == productGroup.getId()) {
//                        isFound = true;
//                        break;
//                    }
//                }
//            } else {
//                isFound = true;
//            }
//
//            if (isFound) {
//                if (printerSetting.getName().contains("[K1]")) {
//                    //esc.addPrintAndLineFeed();
//
//                    for (int k = 0; k < orderItem.getQty(); k++) {
//                        if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                            esc.addText(String.format("%s (%s)", orderItem.getItemName(), orderItem.getItemVariantName()));
//                        } else {
//                            esc.addText(String.format("%s", orderItem.getItemName()));
//                        }
//
//                        if (orderItem.getOrderItemAddOns() != null && orderItem.getOrderItemAddOns().size() > 0) {
//                            for (SalesOrderItemAddOn orderItemAddOn : orderItem.getOrderItemAddOns()) {
//                                esc.addPrintAndLineFeed();
//                                esc.addText(String.format("   +%s", orderItemAddOn.getName()));
//                            }
//                        }
//
//                        if (orderItem.getFNotes() != null && orderItem.getFNotes().length() > 0) {
//                            esc.addPrintAndLineFeed();
//                            esc.addText(context.getString(R.string.notes));
//                            esc.addPrintAndLineFeed();
//                            esc.addText(orderItem.getFNotes());
//
//                            //esc.addPrintAndLineFeed();
//                        }
//
//                        esc.addPrintAndLineFeed();
//                    }
//                } else {
//                    //esc.addPrintAndLineFeed();
//                    if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                        esc.addText(String.format("%sx %s (%s)", numberFormat.format(orderItem.getQty()), orderItem.getItemName(), orderItem.getItemVariantName()));
//                    } else {
//                        esc.addText(String.format("%sx %s", numberFormat.format(orderItem.getQty()), orderItem.getItemName()));
//                    }
//
//                    if (orderItem.getOrderItemAddOns() != null && orderItem.getOrderItemAddOns().size() > 0) {
//                        for (SalesOrderItemAddOn orderItemAddOn : orderItem.getOrderItemAddOns()) {
//                            esc.addPrintAndLineFeed();
//                            esc.addText(String.format("   +%s", orderItemAddOn.getName()));
//                        }
//                    }
//
//                    if (orderItem.getFNotes() != null && orderItem.getFNotes().length() > 0) {
//                        esc.addPrintAndLineFeed();
//                        esc.addText(context.getString(R.string.notes));
//                        esc.addPrintAndLineFeed();
//                        esc.addText(orderItem.getFNotes());
//
//                        //esc.addPrintAndLineFeed();
//                    }
//
//                    esc.addPrintAndLineFeed();
//                }
//
//            }
//
//        }
//    }
//
//    private static boolean printReceiptGroupData(Context context, int printerIdx, Store store, Printer printerSetting, SalesOrder salesOrder, boolean isAdditional, boolean isCancelled, boolean isReprint) {
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers().length <= printerIdx)
//            return false;
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx] == null ||
//                !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].getConnState()) {
//            Toast.makeText(context, String.format(context.getString(R.string.str_printer_not_connected), printerSetting.getName()), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        Calendar calDefault = Calendar.getInstance();
//        calDefault.set(1970, 1, 1);
//
//        NumberFormat numberFormat = NumberFormat.getNumberInstance().getInstance(Locale.getDefault());
//        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//        String dotlines = "";
//        for (int i = 0; i < printerSetting.getNumberCols(); i++) {
//            dotlines += "-";
//        }
//
//        String bigdotlines = "";
//        for (int i = 0; i < 20; i++) {
//            bigdotlines += "-";
//        }
//
//        EscCommand esc = new EscCommand();
//        esc.addInitializePrinter();
//        esc.addPrintAndFeedLines((byte) 2);
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//
//        if (printerSetting.isWithLogo()) {
//            Bitmap logo = Helper.getLogoBitmap(context, store.getId());
//            if (logo != null) {
//                esc.addRastBitImage(logo, 320, 0);
//            }
//        }
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//
//        if (printerSetting.isForCashier()) {
//            esc.addText("* BACKUP PRINTER *");
//            esc.addPrintAndLineFeed();
//        }
//
//        if (printerSetting.getName().contains("[PN]")) {
//            esc.addText(printerSetting.getName().replace("[PN]", ""));
//            esc.addPrintAndLineFeed();
//        }
//
//        if (isAdditional) {
//            esc.addText(context.getString(R.string.additionals));
//        } else if (isCancelled) {
//            esc.addText(context.getString(R.string.cancelled));
//        } else {
//            if (isReprint)
//                esc.addText(String.format("%s (R)", context.getString(R.string.new_order)));
//            else
//                esc.addText(context.getString(R.string.new_order));
//        }
//
//        esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
//
//        esc.addPrintAndLineFeed();
//        esc.addPrintAndLineFeed();
//
//        esc.addText(store.getName());
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        esc.addPrintAndLineFeed();
//
//        esc.addText(Helper.paddingRight(context.getString(R.string.order_time), printerSetting.getReceiptHeaderLeftCols()));
//        esc.addText(Helper.paddingLeft(context.getString(R.string.served_by), printerSetting.getReceiptHeaderRightCols()));
//        esc.addPrintAndLineFeed();
//
//        esc.addText(Helper.paddingRight(dateTimeFormat.format(salesOrder.getOrderTime()), printerSetting.getReceiptHeaderLeftCols()));
//        esc.addText(Helper.paddingLeft(salesOrder.getServeByName(), printerSetting.getReceiptHeaderRightCols()));
//
//        //esc.addPrintAndFeedLines((byte) 2);
//        esc.addPrintAndLineFeed();
//
//
//        if (salesOrder.getCustomerName() != null && salesOrder.getCustomerName().length() > 0) {
//            esc.addText(String.format("%s : %s", context.getString(R.string.customer), salesOrder.getCustomerName()));
//            //esc.addPrintAndFeedLines((byte) 2);
//            esc.addPrintAndLineFeed();
//        }
//
//        if (salesOrder.getOrderNo() != null && salesOrder.getOrderNo().length() > 0) {
//            esc.addText(Helper.paddingRight(String.format("#%s", salesOrder.getOrderNo()), printerSetting.getReceiptHeaderLeftCols()));
//        } else {
//            esc.addText(Helper.paddingRight("", printerSetting.getReceiptHeaderLeftCols()));
//        }
//
//        if (store.isRestoMode() && salesOrder.getTotalGuest() > 0) {
//            esc.addText(Helper.paddingLeft(String.format("%d %s", salesOrder.getTotalGuest(), context.getString(R.string.guest)), printerSetting.getReceiptHeaderRightCols()));
//        } else if (salesOrder.getRemark() != null && salesOrder.getRemark().length() > 0 && !salesOrder.getRemark().contains("DINE-IN") && !salesOrder.getRemark().contains("TAKE-AWAY") && !salesOrder.getRemark().contains("DELIVERY") && !salesOrder.getRemark().contains("GOFOOD") && !salesOrder.getRemark().contains("GRABFOOD") && !salesOrder.getRemark().contains("SHOPEEFOOD") && !salesOrder.getRemark().contains("TRAVELOKA-EATS")) {
//            esc.addText(Helper.paddingLeft(salesOrder.getRemark(), printerSetting.getReceiptHeaderRightCols()));
//        }
//
//        esc.addPrintAndLineFeed();
//
//
//        esc.addText(dotlines);
//        esc.addPrintAndLineFeed();
//
//        /*
//        esc.addText(context.getString(R.string.item));
//        esc.addPrintAndLineFeed();
//
//        esc.addText(dotlines);
//        esc.addPrintAndLineFeed();
//        */
//
//        if (!store.isPrintKitchenNormalFont()) {
//            esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//        }
//
//        RealmList<SalesOrderItem> orderItems = null;
//        RealmList<SalesOrderItem> takeAwayOrderItems = null;
//        if (isAdditional) {
//            if (store.isRestoMode() && salesOrder.getRemark() != null && salesOrder.getRemark().equals("DINE-IN")) {
//                List<RealmList<SalesOrderItem>> dineInTakeAwayOrderItems = salesOrder.getAdditionalOrderItemListDineInTakeAway();
//                orderItems = dineInTakeAwayOrderItems.get(0);
//                takeAwayOrderItems = dineInTakeAwayOrderItems.get(1);
//            } else {
//                orderItems = salesOrder.getAdditionalOrderItemList();
//            }
//        } else if (isCancelled) {
//            if (store.isRestoMode() && salesOrder.getRemark() != null && salesOrder.getRemark().equals("DINE-IN")) {
//                List<RealmList<SalesOrderItem>> dineInTakeAwayOrderItems = salesOrder.getCancelledOrderItemListDineInTakeAway();
//                orderItems = dineInTakeAwayOrderItems.get(0);
//                takeAwayOrderItems = dineInTakeAwayOrderItems.get(1);
//            } else {
//                orderItems = salesOrder.getCancelledOrderItemList();
//            }
//        } else {
//            if (store.isRestoMode() && salesOrder.getRemark() != null && salesOrder.getRemark().equals("DINE-IN")) {
//                List<RealmList<SalesOrderItem>> dineInTakeAwayOrderItems = salesOrder.getActiveOrderItemListDineInTakeAway();
//                orderItems = dineInTakeAwayOrderItems.get(0);
//                takeAwayOrderItems = dineInTakeAwayOrderItems.get(1);
//            } else {
//                orderItems = salesOrder.getActiveOrderItemList();
//            }
//        }
//
//        if (orderItems.size() > 0) {
//            if (store.isRestoMode() && ((salesOrder.getRemark() != null && (salesOrder.getRemark().contains("DINE-IN") || salesOrder.getRemark().contains("TAKE-AWAY") || salesOrder.getRemark().contains("DELIVERY") || salesOrder.getRemark().contains("GOFOOD") || salesOrder.getRemark().contains("GRABFOOD") || salesOrder.getRemark().contains("SHOPEEFOOD") || salesOrder.getRemark().contains("TRAVELOKA-EATS"))) || (salesOrder.getTableNo() != null && salesOrder.getTableNo().length() > 0) || store.isWithQueue())) {
//                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//
//                if (salesOrder.getTableNo() != null && salesOrder.getTableNo().length() > 0) {
//                    if (salesOrder.getRemark() != null && (salesOrder.getRemark().contains("DINE-IN") || salesOrder.getRemark().contains("TAKE-AWAY") || salesOrder.getRemark().contains("DELIVERY") || salesOrder.getRemark().contains("GOFOOD") || salesOrder.getRemark().contains("GRABFOOD") || salesOrder.getRemark().contains("SHOPEEFOOD") || salesOrder.getRemark().contains("TRAVELOKA-EATS"))) {
//                        esc.addText(String.format("%s (%s)", salesOrder.getRemark(), salesOrder.getTableNo()));
//                    } else {
//                        if (store.isWithQueue() && Helper.getQueueNo(store, salesOrder).length() > 0)
//                            esc.addText(String.format("Q No. %s (%s)", Helper.getQueueNo(store, salesOrder), salesOrder.getTableNo()));
//                        else
//                            esc.addText(salesOrder.getTableNo());
//                    }
//                } else {
//                    /*
//                    if (salesOrder.getCustomerId() == 0 && salesOrder.getCustomerName() != null && salesOrder.getCustomerName().length() > 0){
//                        esc.addText(String.format("%s (%s)", salesOrder.getRemark(), salesOrder.getCustomerName()));
//                    }
//                    else {
//                        esc.addText(salesOrder.getRemark());
//                    }
//                    */
//                    if (store.isWithQueue() && Helper.getQueueNo(store, salesOrder).length() > 0) {
//                        if (salesOrder.getRemark() != null && (salesOrder.getRemark().contains("DINE-IN") || salesOrder.getRemark().contains("TAKE-AWAY") || salesOrder.getRemark().contains("DELIVERY") || salesOrder.getRemark().contains("GOFOOD") || salesOrder.getRemark().contains("GRABFOOD") || salesOrder.getRemark().contains("SHOPEEFOOD") || salesOrder.getRemark().contains("TRAVELOKA-EATS")))
//                            esc.addText(String.format("Q No. %s (%s)", Helper.getQueueNo(store, salesOrder), salesOrder.getRemark()));
//                        else
//                            esc.addText(String.format("Q No. %s", Helper.getQueueNo(store, salesOrder)));
//                    } else
//                        esc.addText(salesOrder.getRemark());
//                }
//
//                esc.addPrintAndLineFeed();
//
//                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//            }
//
//            for (SalesOrderItem orderItem : orderItems) {
//                populateOrderItemReceiptGroupTextData(context, printerSetting, numberFormat, orderItem, esc, bigdotlines);
//            }
//        }
//
//        if (takeAwayOrderItems != null && takeAwayOrderItems.size() > 0) {
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//            esc.addPrintAndLineFeed();
//            esc.addText(bigdotlines);
//            esc.addPrintAndLineFeed();
//            if (salesOrder.getTableNo() != null && salesOrder.getTableNo().length() > 0) {
//                esc.addText(String.format("TAKE-AWAY (%s)", salesOrder.getTableNo()));
//            } else {
//                esc.addText("TAKE-AWAY");
//            }
//
//            esc.addPrintAndLineFeed();
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//
//            for (SalesOrderItem takeAwayOrderItem : takeAwayOrderItems) {
//                populateOrderItemReceiptGroupTextData(context, printerSetting, numberFormat, takeAwayOrderItem, esc, bigdotlines);
//            }
//        }
//
//        if (salesOrder.getNotes() != null && salesOrder.getNotes().length() > 0) {
//            esc.addPrintAndLineFeed();
//            esc.addText(salesOrder.getNotes());
//            esc.addPrintAndLineFeed();
//        }
//
//        if (printerSetting.getBrandId() == PrinterBrandOptions.EPPOS.getValue()) {
//            esc.addPrintAndFeedLines((byte) 3);
//        } else {
//            esc.addPrintAndFeedLines((byte) 5);
//        }
//        esc.addCutPaper();
//
//        Vector<Byte> datas = esc.getCommand();
//        boolean result = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].sendDataImmediately(datas);
//        return result;
//
//        /*
//        byte[] bytes = GpUtils.ByteTo_byte(datas);
//        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
//        int rs;
//        try {
//            rs = gpService.sendEscCommand(printerIdx, sss);
//            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
//            if (r != GpCom.ERROR_CODE.SUCCESS) {
//                Toast.makeText(context, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//        */
//    }
//
//    public static boolean printClosingSalesData(Context context, int printerIdx, Store store, Printer printerSetting, List<ShiftBalanceListItemDTO> closingSalesObjects) {
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx] == null ||
//                !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].getConnState()) {
//            Toast.makeText(context, String.format(context.getString(R.string.str_printer_not_connected), printerSetting.getName()), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        Calendar calDefault = Calendar.getInstance();
//        calDefault.set(1970, 1, 1);
//
//        NumberFormat numberFormat = NumberFormat.getNumberInstance().getInstance(Locale.getDefault());
//        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//        String dotlines = "";
//        for (int i = 0; i < printerSetting.getNumberCols(); i++) {
//            dotlines += "-";
//        }
//
//        EscCommand esc = new EscCommand();
//        esc.addInitializePrinter();
//        esc.addPrintAndFeedLines((byte) 2);
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//
//        if (printerSetting.isWithLogo()) {
//            Bitmap logo = Helper.getLogoBitmap(context, store.getId());
//            if (logo != null) {
//                esc.addRastBitImage(logo, 320, 0);
//            }
//        }
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
//        esc.addText(store.getName());
//        esc.addPrintAndLineFeed();
//
//        esc.addText(context.getString(R.string.closing_sales));
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        esc.addPrintAndLineFeed();
//
//        esc.addText(Helper.paddingRight(context.getString(R.string.printed), printerSetting.getClosingSalesHeaderLeftCols()));
//        esc.addText(Helper.paddingRight(String.format(": %s", dateTimeFormat.format(new Date())), printerSetting.getClosingSalesHeaderRightCols()));
//        esc.addPrintAndFeedLines((byte) 2);
//
//        for (ShiftBalanceListItemDTO closingSalesObject : closingSalesObjects) {
//
//            if (closingSalesObject.isHeader()) {
//                esc.addText(Helper.paddingRight(closingSalesObject.getTitle(), printerSetting.getClosingSalesHeaderLeftCols()));
//                esc.addText(Helper.paddingLeft(String.format("%s", closingSalesObject.getValue()), printerSetting.getClosingSalesHeaderRightCols()));
//                esc.addPrintAndLineFeed();
//            } else {
//                if (closingSalesObject.isGroup()) {
//                    esc.addPrintAndLineFeed();
//                }
//
//                if (closingSalesObject.isSummary()) {
//                    esc.addText(dotlines);
//                    esc.addPrintAndLineFeed();
//                }
//
//                if (closingSalesObject.isWithSpace()) {
//                    esc.addText(Helper.paddingRight(String.format(" %s", closingSalesObject.getTitle()), printerSetting.getClosingSalesItemLeftCols()));
//                    esc.addText(Helper.paddingLeft(String.format("%s", closingSalesObject.getValue()), printerSetting.getClosingSalesItemRightCols()));
//                    esc.addPrintAndLineFeed();
//                } else {
//                    esc.addText(Helper.paddingRight(closingSalesObject.getTitle(), printerSetting.getClosingSalesItemLeftCols()));
//                    esc.addText(Helper.paddingLeft(closingSalesObject.getValue(), printerSetting.getClosingSalesItemRightCols()));
//                    esc.addPrintAndLineFeed();
//                }
//
//                if (closingSalesObject.isGroup()) {
//                    esc.addText(dotlines);
//                    esc.addPrintAndLineFeed();
//                }
//            }
//        }
//
//        if (printerSetting.getBrandId() == PrinterBrandOptions.EPPOS.getValue()) {
//            esc.addPrintAndFeedLines((byte) 3);
//        } else {
//            esc.addPrintAndFeedLines((byte) 5);
//        }
//        esc.addCutPaper();
//
//        Vector<Byte> datas = esc.getCommand();
//        boolean result = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].sendDataImmediately(datas);
//        return result;
//
//        /*
//        byte[] bytes = GpUtils.ByteTo_byte(datas);
//        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
//        int rs;
//        try {
//            rs = gpService.sendEscCommand(printerIdx, sss);
//            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
//            if (r != GpCom.ERROR_CODE.SUCCESS) {
//                Toast.makeText(context, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//        */
//    }
//
//    public static boolean printProductSalesData(Context context, int printerIdx, Store store, Printer printerSetting, List<ProductGroupSalesDTO> productGroupSalesDTOs, List<ProductSalesDTO> productSalesDTOs, Date startDate, Date endDate) {
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx] == null ||
//                !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].getConnState()) {
//            Toast.makeText(context, String.format(context.getString(R.string.str_printer_not_connected), printerSetting.getName()), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        Calendar calDefault = Calendar.getInstance();
//        calDefault.set(1970, 1, 1);
//
//        NumberFormat numberFormat = NumberFormat.getNumberInstance().getInstance(Locale.getDefault());
//        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//        String dotlines = "";
//        for (int i = 0; i < printerSetting.getNumberCols(); i++) {
//            dotlines += "-";
//        }
//
//        EscCommand esc = new EscCommand();
//        esc.addInitializePrinter();
//        esc.addPrintAndFeedLines((byte) 2);
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//
//        if (printerSetting.isWithLogo()) {
//            Bitmap logo = Helper.getLogoBitmap(context, store.getId());
//            if (logo != null) {
//                esc.addRastBitImage(logo, 320, 0);
//            }
//        }
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
//        esc.addText(store.getName());
//        esc.addPrintAndLineFeed();
//
//        esc.addText(context.getString(R.string.product_sold_summary));
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        esc.addPrintAndLineFeed();
//
//        if (!startDate.equals(endDate)) {
//            esc.addText(Helper.paddingRight(context.getString(R.string.start), printerSetting.getProductSalesHeaderLeftCols()));
//            esc.addText(Helper.paddingRight(String.format(": %s", dateTimeFormat.format(startDate)), printerSetting.getProductSalesHeaderRightCols()));
//            esc.addPrintAndLineFeed();
//
//            esc.addText(Helper.paddingRight(context.getString(R.string.end), printerSetting.getProductSalesHeaderLeftCols()));
//            esc.addText(Helper.paddingRight(String.format(": %s", dateTimeFormat.format(endDate)), printerSetting.getProductSalesHeaderRightCols()));
//            esc.addPrintAndLineFeed();
//        } else {
//            esc.addText(Helper.paddingRight(context.getString(R.string.date), printerSetting.getProductSalesHeaderLeftCols()));
//            esc.addText(Helper.paddingRight(String.format(": %s", dateFormat.format(startDate)), printerSetting.getProductSalesHeaderRightCols()));
//            esc.addPrintAndLineFeed();
//        }
//
//        esc.addText(Helper.paddingRight(context.getString(R.string.printed), printerSetting.getProductSalesHeaderLeftCols()));
//        esc.addText(Helper.paddingRight(String.format(": %s", dateTimeFormat.format(new Date())), printerSetting.getProductSalesHeaderRightCols()));
//        esc.addPrintAndLineFeed();
//
//        long currentProductGroupId = -3;
//        for (ProductSalesDTO productSalesDTO : productSalesDTOs) {
//            if (productSalesDTO.getProductGroupId() != currentProductGroupId) {
//                currentProductGroupId = productSalesDTO.getProductGroupId();
//
//                for (ProductGroupSalesDTO xProductGroupSalesDTO : productGroupSalesDTOs) {
//                    if (xProductGroupSalesDTO.getId() == currentProductGroupId) {
//                        esc.addPrintAndLineFeed();
//                        esc.addText(Helper.paddingLeft(String.format("%s / %s", numberFormat.format(xProductGroupSalesDTO.getTotalQty()), numberFormat.format(xProductGroupSalesDTO.getTotalAmount())), printerSetting.getNumberCols()));
//                        esc.addPrintAndLineFeed();
//                        esc.addText(xProductGroupSalesDTO.getName());
//                        esc.addPrintAndLineFeed();
//                        esc.addText(dotlines);
//                        esc.addPrintAndLineFeed();
//                        break;
//                    }
//                }
//            }
//
//            esc.addText(Helper.paddingLeft(String.format("%s / %s", numberFormat.format(productSalesDTO.getQty()), numberFormat.format(productSalesDTO.getAmount())), printerSetting.getNumberCols()));
//            esc.addPrintAndLineFeed();
//            if (productSalesDTO.getItemVariantName() != null && productSalesDTO.getItemVariantName().length() > 0) {
//                esc.addText(String.format("%s - %s", productSalesDTO.getItemName(), productSalesDTO.getItemVariantName()));
//            } else {
//                esc.addText(productSalesDTO.getItemName());
//            }
//            esc.addPrintAndLineFeed();
//        }
//
//        if (printerSetting.getBrandId() == PrinterBrandOptions.EPPOS.getValue()) {
//            esc.addPrintAndFeedLines((byte) 3);
//        } else {
//            esc.addPrintAndFeedLines((byte) 5);
//        }
//        esc.addCutPaper();
//
//        Vector<Byte> datas = esc.getCommand();
//        boolean result = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].sendDataImmediately(datas);
//        return result;
//
//        /*
//        byte[] bytes = GpUtils.ByteTo_byte(datas);
//        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
//        int rs;
//        try {
//            rs = gpService.sendEscCommand(printerIdx, sss);
//            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
//            if (r != GpCom.ERROR_CODE.SUCCESS) {
//                Toast.makeText(context, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//        */
//    }
//
//    public static boolean printRefund(Context context, int printerIdx, Store store, Printer printerSetting, SalesReturn salesReturn, boolean isCheckOrder, boolean openDrawer, boolean isReprint) {
//
//        if (GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx] == null ||
//                !GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].getConnState()) {
//            Toast.makeText(context, String.format(context.getString(R.string.str_printer_not_connected), printerSetting.getName()), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        Calendar calDefault = Calendar.getInstance();
//        calDefault.set(1970, 1, 1);
//
//        NumberFormat numberFormat = NumberFormat.getNumberInstance().getInstance(Locale.getDefault());
//        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//        String dotlines = "";
//        for (int i = 0; i < printerSetting.getNumberCols(); i++) {
//            dotlines += "-";
//        }
//
//        EscCommand esc = new EscCommand();
//        esc.addInitializePrinter();
//        esc.addPrintAndFeedLines((byte) 2);
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//
//        if (printerSetting.isWithLogo()) {
//            Bitmap logo = Helper.getLogoBitmap(context, store.getId());
//            if (logo != null) {
//                esc.addRastBitImage(logo, 320, 0);
//            }
//        }
//        esc.addPrintAndLineFeed();
//
//        if (salesReturn.getStatus().equals("X")) {
//            esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//            esc.addText(context.getString(R.string.voided));
//            esc.addPrintAndLineFeed();
//        }
//
//        esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
//        if (isCheckOrder && !store.isReceiptCheckTitleNormal()) {
//            esc.addText(context.getString(R.string.not_receipt));
//        } else {
//            if (isReprint) {
//                esc.addText(context.getString(R.string.reprint));
//                esc.addPrintAndLineFeed();
//            }
//
//            esc.addText(context.getString(R.string.refund));
//            esc.addPrintAndFeedLines((byte) 2);
//
//            esc.addText(store.getReceiptHeader());
//        }
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        esc.addPrintAndLineFeed();
//
//        esc.addText(Helper.paddingRight(context.getString(R.string.time_r), printerSetting.getReceiptHeaderLeftCols()));
//        esc.addText(Helper.paddingLeft(context.getString(R.string.cashier), printerSetting.getReceiptHeaderRightCols()));
//        esc.addPrintAndLineFeed();
//
//        esc.addText(Helper.paddingRight(dateTimeFormat.format(salesReturn.getReturnTime()), printerSetting.getReceiptHeaderLeftCols()));
//        esc.addText(Helper.paddingLeft(salesReturn.getCashierName(), printerSetting.getReceiptHeaderRightCols()));
//
//        if (printerSetting.getBrandId() == PrinterBrandOptions.PANDA.getValue()) {
//            esc.addPrintAndLineFeed();
//        } else {
//            esc.addPrintAndFeedLines((byte) 2);
//        }
//
//
//        if (salesReturn.getCustomerName() != null && salesReturn.getCustomerName().length() > 0) {
//            esc.addText(String.format("%s : %s", context.getString(R.string.customer), salesReturn.getCustomerName()));
//            if (printerSetting.getBrandId() == PrinterBrandOptions.PANDA.getValue()) {
//                esc.addPrintAndLineFeed();
//            } else {
//                esc.addPrintAndFeedLines((byte) 2);
//            }
//        }
//
//        if (salesReturn.getOrderNo() != null && salesReturn.getOrderNo().length() > 0 && !store.isHideOrderNo()) {
//            esc.addText(Helper.paddingRight(String.format("#%s", salesReturn.getOrderNo()), printerSetting.getReceiptHeaderLeftCols()));
//        } else {
//            esc.addText(Helper.paddingRight("", printerSetting.getReceiptHeaderLeftCols()));
//        }
//
//        esc.addPrintAndLineFeed();
//        esc.addText(dotlines);
//        esc.addPrintAndLineFeed();
//
//        if (printerSetting.getBrandId() != PrinterBrandOptions.PANDA.getValue()) {
//            esc.addText(Helper.paddingRight(context.getString(R.string.item), printerSetting.getReceiptHeaderLeftCols()));
//            esc.addText(Helper.paddingLeft(context.getString(R.string.amount), printerSetting.getReceiptHeaderRightCols()));
//            esc.addPrintAndLineFeed();
//
//            esc.addText(dotlines);
//            esc.addPrintAndLineFeed();
//        }
//
//        for (SalesReturnItem orderItem : salesReturn.getReturnItems()) {
//            if (store.isReceiptCompact()) {
//                String itemName = orderItem.getItemName();
//                if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                    itemName = String.format("%s - %s", orderItem.getItemName(), orderItem.getItemVariantName());
//                }
//
//                if (itemName.length() > printerSetting.getReceiptCompactItemCols()) {
//                    itemName = itemName.substring(0, printerSetting.getReceiptCompactItemCols() - 1);
//                }
//
//                esc.addText(Helper.paddingLeft(String.format("%s ", numberFormat.format(orderItem.getQty())), printerSetting.getReceiptCompactQtyCols()));
//                esc.addText(Helper.paddingRight(itemName, printerSetting.getReceiptCompactItemCols()));
//                esc.addText(Helper.paddingLeft(numberFormat.format(orderItem.getAmount()), printerSetting.getReceiptCompactAmountCols()));
//                esc.addPrintAndLineFeed();
//            } else {
//                if (orderItem.getItemVariantName() != null && orderItem.getItemVariantName().length() > 0) {
//                    esc.addText(String.format("%s\n%s", orderItem.getItemName(), orderItem.getItemVariantName()));
//                } else {
//                    esc.addText(orderItem.getItemName());
//                }
//                esc.addPrintAndLineFeed();
//
//                if (orderItem.getSerialNo() != null && orderItem.getSerialNo().length() > 0) {
//                    esc.addText(String.format("%s : %s", context.getString(R.string.serial_no), orderItem.getSerialNo()));
//                    esc.addPrintAndLineFeed();
//                }
//
//                if ((!store.isRestoMode() || store.isReceiptPrintItemNotes()) && orderItem.getNotes() != null && orderItem.getNotes().length() > 0) {
//                    esc.addText(orderItem.getNotes());
//                    esc.addPrintAndLineFeed();
//                }
//
//
//                esc.addText(Helper.paddingLeft(String.format("%s ", numberFormat.format(orderItem.getPrice())), printerSetting.getReceiptItemPriceCols()));
//                esc.addText(Helper.paddingRight(String.format("x%s", numberFormat.format(orderItem.getQty())), printerSetting.getReceiptItemQtyCols()));
//
//                esc.addText(Helper.paddingLeft(numberFormat.format(orderItem.getAmount()), printerSetting.getReceiptItemAmountCols()));
//                esc.addPrintAndLineFeed();
//            }
//
//            if (orderItem.getDiscountAmount() > 0) {
//                String discountName = Helper.getOrderItemDiscountName(orderItem);
//                if (orderItem.getDiscountRate() > 0) {
//                    esc.addText(String.format("%s -%s (%s%%)", discountName, numberFormat.format(orderItem.getDiscountAmount()), numberFormat.format(orderItem.getDiscountRate())));
//                } else {
//                    esc.addText(String.format("%s -%s", discountName, numberFormat.format(orderItem.getDiscountAmount())));
//                }
//
//                esc.addPrintAndLineFeed();
//            }
//        }
//
//        esc.addText(dotlines);
//        esc.addPrintAndLineFeed();
//
//        esc.addText(Helper.paddingRight(context.getString(R.string.subtotal), printerSetting.getReceiptSummaryLabelCols()));
//        esc.addText(Helper.paddingLeft(numberFormat.format(salesReturn.getSubtotalAmount()), printerSetting.getReceiptSummaryAmountCols()));
//        esc.addPrintAndLineFeed();
//        if (salesReturn.getAdditionalAmount() > 0) {
//            esc.addText(Helper.paddingRight(salesReturn.getAdditionalDescription(), printerSetting.getReceiptSummaryLabelCols()));
//            esc.addText(Helper.paddingLeft(String.format("%s %s", salesReturn.getCurrencySymbol(), numberFormat.format(salesReturn.getAdditionalAmount())), printerSetting.getReceiptSummaryAmountCols()));
//            esc.addPrintAndLineFeed();
//        }
//        esc.addText(Helper.paddingRight(context.getString(R.string.grand_total), printerSetting.getReceiptSummaryLabelCols()));
//        esc.addText(Helper.paddingLeft(String.format("%s %s", salesReturn.getCurrencySymbol(), numberFormat.format(salesReturn.getTotalAmount())), printerSetting.getReceiptSummaryAmountCols()));
//
//        if (printerSetting.getBrandId() == PrinterBrandOptions.EPPOS.getValue()) {
//            esc.addPrintAndFeedLines((byte) 3);
//        } else {
//            esc.addPrintAndFeedLines((byte) 5);
//        }
//
//        esc.addCutPaper();
//
//        Vector<Byte> datas = esc.getCommand();
//        boolean result = GPDeviceConnFactoryManager.getDeviceConnFactoryManagers()[printerIdx].sendDataImmediately(datas);
//        return result;
//    }
}
