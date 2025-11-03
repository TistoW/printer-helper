package com.olserapratama.printer.implementation.starmprinter;

import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.starmicronics.starioextension.StarIoExt;

public class StarModelCapability  {
    public static final int NONE = -1;
    public static final int MPOP = 0;
    public static final int MPOP_W_SCANNER = 1;
    public static final int FVP10 = 2;
    public static final int TSP100 = 3;
    public static final int TSP650II = 4;
    public static final int TSP700II = 5;
    public static final int TSP800II = 6;
    public static final int SP700 = 7;
    public static final int SM_S210I = 8;
    public static final int SM_S220I = 9;
    public static final int SM_S230I = 10;
    public static final int SM_T300I_T300 = 11;
    public static final int SM_T400I = 12;
    public static final int SM_L200 = 13;
    public static final int BSC10 = 14;
    public static final int SM_S210I_StarPRNT = 15;
    public static final int SM_S220I_StarPRNT = 16;
    public static final int SM_S230I_StarPRNT = 17;
    public static final int SM_T300I_T300_StarPRNT = 18;
    public static final int SM_T400I_StarPRNT = 19;

    private static final SparseArray<String> mModelTitleMap = new SparseArray<String>() {
        {
            put(MPOP, "mPOP");
            put(MPOP_W_SCANNER, "mPOP_W_Scanner");
            put(FVP10, "FVP10");
            put(TSP100, "TSP100");
            put(TSP650II, "TSP650II");
            put(TSP700II, "TSP700II");
            put(TSP800II, "TSP800II");
            put(SP700, "SP700");
            put(SM_S210I, "SM-S210i");
            put(SM_S220I, "SM-S220i");
            put(SM_S230I, "SM-S230i");
            put(SM_T300I_T300, "SM-T300i/T300");
            put(SM_T400I, "SM-T400i");
            put(SM_L200, "SM-L200");
            put(BSC10, "BSC10");
            put(SM_S210I_StarPRNT, "SM-S210i StarPRNT");
            put(SM_S220I_StarPRNT, "SM-S220i StarPRNT");
            put(SM_S230I_StarPRNT, "SM-S230i StarPRNT");
            put(SM_T300I_T300_StarPRNT, "SM-T300i StarPRNT");
            put(SM_T400I_StarPRNT, "SM-T400i StarPRNT");
        }
    };

    private static final SparseArray<StarIoExt.Emulation> mEmulationMap = new SparseArray<StarIoExt.Emulation>() {
        {
            put(MPOP, StarIoExt.Emulation.StarPRNT);
            put(MPOP_W_SCANNER, StarIoExt.Emulation.StarPRNT);
            put(FVP10, StarIoExt.Emulation.StarLine);
            put(TSP100, StarIoExt.Emulation.StarGraphic);
            put(TSP650II, StarIoExt.Emulation.StarLine);
            put(TSP700II, StarIoExt.Emulation.StarLine);
            put(TSP800II, StarIoExt.Emulation.StarLine);
            put(SP700, StarIoExt.Emulation.StarDotImpact);
            put(SM_S210I, StarIoExt.Emulation.EscPosMobile);
            put(SM_S220I, StarIoExt.Emulation.EscPosMobile);
            put(SM_S230I, StarIoExt.Emulation.EscPosMobile);
            put(SM_T300I_T300, StarIoExt.Emulation.EscPosMobile);
            put(SM_T400I, StarIoExt.Emulation.EscPosMobile);
            put(SM_L200, StarIoExt.Emulation.StarPRNT);
            put(BSC10, StarIoExt.Emulation.EscPos);
            put(SM_S210I_StarPRNT, StarIoExt.Emulation.StarPRNT);
            put(SM_T300I_T300_StarPRNT, StarIoExt.Emulation.StarPRNT);
            put(SM_T400I_StarPRNT, StarIoExt.Emulation.StarPRNT);
        }
    };

    private static final SparseArray<String> mPortSettingsMap = new SparseArray<String>() {
        {
            put(MPOP, "");
            put(MPOP_W_SCANNER, "");
            put(FVP10, "");
            put(TSP100, "");
            put(TSP650II, "");
            put(TSP700II, "");
            put(TSP800II, "");
            put(SP700, "");
            put(SM_S210I, "mini");
            put(SM_S220I, "mini");
            put(SM_S230I, "mini");
            put(SM_T300I_T300, "mini");
            put(SM_T400I, "mini");
            put(SM_L200, "Portable");
            put(BSC10, "escpos");
            put(SM_S210I_StarPRNT, "Portable");
            put(SM_S220I_StarPRNT, "Portable");
            put(SM_S230I_StarPRNT, "Portable");
            put(SM_T300I_T300_StarPRNT, "Portable");
            put(SM_T400I_StarPRNT, "Portable");
        }
    };

    private static final SparseArray<String[]> mModelNameArrayMap = new SparseArray<String[]>() {
        {
            put(MPOP, new String[]{ "STAR mPOP-",                       // <-Bluetooth interface
                    "mPOP" });                          // <-USB interface
            put(MPOP_W_SCANNER, new String[]{ "STAR mPOP-",                       // <-Bluetooth interface
                    "mPOP" });                          // <-USB interface
            put(FVP10, new String[]{ "FVP10 (STR_T-001)",               // <-LAN interface
                    "Star FVP10" });                   // <-USB interface
            put(TSP100, new String[]{ "TSP113", "TSP143",               // <-LAN model
                    "TSP100-",                        // <-Bluetooth model
                    "Star TSP113", "Star TSP143"});   // <-USB model
            put(TSP650II, new String[]{ "TSP654II (STR_T-001)",         // Only LAN model->
                    "TSP654 (STR_T-001)",
                    "TSP651 (STR_T-001)" });
            put(TSP700II, new String[]{ "TSP743II (STR_T-001)",
                    "TSP743 (STR_T-001)" });
            put(TSP800II, new String[]{ "TSP847II (STR_T-001)",
                    "TSP847 (STR_T-001)" });        // <-Only LAN model
            put(SP700, new String[]{ "SP712 (STR-001)",                 // Only LAN model
                    "SP717 (STR-001)",
                    "SP742 (STR-001)",
                    "SP747 (STR-001)" });
//            put(SM_S210I, new String[]{ "SM-S210i" });
//            put(SM_S220I, new String[]{ "SM-S220i" });
//            put(SM_S230I, new String[]{ "SM-S230i" });
//            put(SM_T300I_T300, new String[]{ "SM-T300i" });
//            put(SM_T400I, new String[]{ "SM-T400i" });
            put(SM_L200, new String[]{ "STAR L200-", "STAR L204-",          // <-Bluetooth interface
                    "Star SM-L200", "Star SM-L204" });   // <-USB interface
            put(BSC10, new String[]{ "BSC10",                           // <-LAN model
                    "Star BSC10"});                    // <-USB model
//            put(SM_S210I_StarPRNT, new String[]{ "SM-S210i" });
//            put(SM_S220I_StarPRNT, new String[]{ "SM-S220i" });
//            put(SM_S230I_StarPRNT, new String[]{ "SM-S230i" });
//            put(SM_T300I_T300_StarPRNT, new String[]{ "SM-T300i" });
//            put(SM_T400I_StarPRNT, new String[]{ "SM-T400i" });
        }
    };

    private static final SparseBooleanArray mDrawerOpenStatusArrayMap = new SparseBooleanArray() {
        {
            put(MPOP,                   false);
            put(MPOP_W_SCANNER,           false);
            put(FVP10,                  true);
            put(TSP100,                 true);
            put(TSP650II,               true);
            put(TSP700II,               true);
            put(TSP800II,               true);
            put(SP700,                  true);
            put(SM_S210I,               false);
            put(SM_S220I,               false);
            put(SM_S230I,               false);
            put(SM_T300I_T300,          false);
            put(SM_T400I,               false);
            put(SM_L200,                false);
            put(BSC10,                  true);
            put(SM_S210I_StarPRNT,      false);
            put(SM_S220I_StarPRNT,      false);
            put(SM_S230I_StarPRNT,      false);
            put(SM_T300I_T300_StarPRNT, false);
            put(SM_T400I_StarPRNT,      false);
        }
    };

    public static String getModelTitle(int model) {
        return mModelTitleMap.get(model);
    }

    public static StarIoExt.Emulation getEmulation(int model) {
        return mEmulationMap.get(model);
    }

    public static String getPortSettings(int model) {
        return mPortSettingsMap.get(model);
    }

    public static Boolean getDrawerOpenStatus(int model) {
        return mDrawerOpenStatusArrayMap.get(model);
    }

    /**
     * get a model constant from model name string that can be got by PortInfo.getModelName() or PortInfo.getPortName();
     */
    public static int getModel(String modelNameSrc) {
        for (int i = 0; i < mModelNameArrayMap.size(); i++) {
            for (String modelName : mModelNameArrayMap.valueAt(i)) {
                if (modelNameSrc.startsWith(modelName)) {
                    return mModelNameArrayMap.keyAt(i);
                }
            }
        }

        return NONE;
    }
}
