package com.zenenta.printer.repository

object PrinterDatabase {
    private val modelsStar = arrayListOf(
        PrinterModel(0, "mPOP", 32),
        PrinterModel(1, "mPOP_W_Scanner", 32),
        PrinterModel(2, "FVP10", 44),
        PrinterModel(3, "TSP100", 44),
        PrinterModel(4, "TSP650II", 44),
        PrinterModel(5, "TSP700II", 44),
        PrinterModel(6, "TSP800II", 44),
        PrinterModel(7, "SP700", 44),
        PrinterModel(8, "SM-S210i", 44),
        PrinterModel(9, "SM-S220i", 44),
        PrinterModel(10, "SM-S230i", 44),
        PrinterModel(11, "SM-T300i/T300", 44),
        PrinterModel(12, "SM-T400i", 44),
        PrinterModel(13, "SM-L200", 44),
        PrinterModel(14, "BSC10", 44),
        PrinterModel(15, "SM-S210i StarPRNT", 44),
        PrinterModel(18, "SM-T300i StarPRNT", 44),
        PrinterModel(19, "SM-T400i StarPRNT", 44),
    )

    private val modelsGprinter = arrayListOf(
        PrinterModel(0, "AT-280", 32),
        PrinterModel(1, "Q280", 32),
    )

    private val modelsBluePrint = arrayListOf(
        PrinterModel(0, "Lite-58", 32),
        PrinterModel(1, "Lite-58D", 32),
        PrinterModel(2, "Lite-80", 42),
        PrinterModel(3, "Lite-80D", 42),
        PrinterModel(4, "Eco-58", 32),
        PrinterModel(5, "TD-110D", 48),
        PrinterModel(6, "GAP80x30", 48),
        PrinterModel(7, "GAP80x40", 48),
        PrinterModel(8, "GAP80x50", 48),
    )

    private val modelsHarvard = arrayListOf(PrinterModel(0, "Harvard-01", 32, true))

    private val modelsEpson = arrayListOf(
        PrinterModel(0, "TM-m10", 48),
        PrinterModel(1, "TM-m30", 48),
        PrinterModel(2, "TM-P20", 48),
        PrinterModel(3, "TM-P60", 48),
        PrinterModel(4, "TM-P60II", 48),
        PrinterModel(5, "TM-P80", 48),
        PrinterModel(6, "TM-T20", 48),
        PrinterModel(7, "TM-T60", 48),
        PrinterModel(8, "TM-T70", 48),
        PrinterModel(9, "TM-T81", 48),
        PrinterModel(10, "TM-T82", 48),
        PrinterModel(11, "TM-T83", 48),
        PrinterModel(12, "TM-T88", 48),
        PrinterModel(13, "TM-T90", 48),
        PrinterModel(14, "TM-T90KP", 48),
        PrinterModel(15, "TM-U220", 48),
        PrinterModel(16, "TM-U330", 48),
        PrinterModel(17, "TM-L90", 48),
        PrinterModel(18, "TM-H6000", 48),
    )

    private val modelsSunmi = arrayListOf(
        PrinterModel(0, "V1", 32, true),
        PrinterModel(1, "V2", 32, true),
        PrinterModel(2, "T1", 48, true),
        PrinterModel(3, "T1-7inch", 48, true),
        PrinterModel(4, "T1mini", 48, true),
        PrinterModel(5, "T2mini", 48, true),
        PrinterModel(6, "D2mini", 36, true),
        PrinterModel(7, "D2s Combo", 32, true),
    )

    private val modelsKuangshan = arrayListOf(PrinterModel(0, "Anypos 80", 48))

    private val modelsKassen = arrayListOf(
        PrinterModel(0, "XA-921", 32, true),
        PrinterModel(1, "XA-923", 48, true),
        PrinterModel(2, "XA-02", 32, true),
        PrinterModel(3, "BT-P290", 32),
        PrinterModel(4, "MT-200VL", 32),
        PrinterModel(5, "DT-643 (GAP 30mm)", 48),
        PrinterModel(6, "DT-643 (GAP 80mm)", 48),
    )

    private val modelsKtouch = arrayListOf(PrinterModel(0, "02", 32, true))

    private val modelsMiniPos = arrayListOf(
        PrinterModel(0, "MP-RPP 02", 32),
        PrinterModel(1, "MP-58 UB", 32),
        PrinterModel(2, "MP-80 UL", 62),
        PrinterModel(3, "MP-80 UW", 62),
    )

    private val modelsVsc = arrayListOf(PrinterModel(0, "TM-58D Pro", 32))

    private val modelsEnibit = arrayListOf(PrinterModel(0, "BT", 32))
    private val modelsEppos = arrayListOf(PrinterModel(0, "PT-02", 32))
    private val modelsWintec = arrayListOf(PrinterModel(0, "Anypos 80", 48, true))
    private val modelsXcMobilePos = arrayListOf(PrinterModel(0, "B06", 32, true))

    private val modelsImin = arrayListOf(
        PrinterModel(0, "D1", 32),
        PrinterModel(1, "D4", 48),
        PrinterModel(2, "D4-Timbangan", 48),
        PrinterModel(3, "M2 Max", 32),
        PrinterModel(4, "M2-202n", 32),
        PrinterModel(5, "Falcon 1", 48),
        PrinterModel(6, "S1", 48, true),
    )

    private val modelsIware = arrayListOf(
        PrinterModel(0, "C58BT", 32),
        PrinterModel(1, "MP-58MPC", 32),
        PrinterModel(2, "C80MPO", 48),
        PrinterModel(3, "80AT II", 48),
    )

    private val modelsPanda = arrayListOf(
        PrinterModel(0, "PRJ-R58D", 32),
        PrinterModel(1, "PRJ-R58B", 32),
        PrinterModel(2, "PRJ-R80B", 48),
        PrinterModel(3, "PRJ-80BT", 48),
    )

    private val modelsSilicon = arrayListOf(PrinterModel(0, "SP-201", 48))

    private val modelsUnicorn = arrayListOf(PrinterModel(0, "C58BT", 32))

    private val modelsWindowsPrintSpooler = arrayListOf(
        PrinterModel(0, "Native", 42),
        PrinterModel(1, "OPOS", 42),
    )

    private val modelsZJiang = arrayListOf(
        PrinterModel(0, "BT", 32),
        PrinterModel(1, "USB", 32),
    )

    private val modelsZoneRich = arrayListOf(PrinterModel(0, "AB-320M", 32))

    private val modelsPax = arrayListOf(
        PrinterModel(0, "SK700", 48, true),
        PrinterModel(1, "L1450", 48, true),
        PrinterModel(2, "M9200", 32, true)
    )

    private val modelTelpo = arrayListOf(
        PrinterModel(1, "K8", 48, true),
    )

    private val modelComson = arrayListOf(
        PrinterModel(1, "CSP 893 UE", 48),
    )

    val printerBrands = listOf(
        PrinterBrand(0, "BluePrint", modelsBluePrint),
        PrinterBrand(1, "Enibit", modelsEnibit),
        PrinterBrand(2, "Epson", modelsEpson),
        PrinterBrand(3, "EPPOS", modelsEppos),
        PrinterBrand(4, "GPrinter", modelsGprinter),
        PrinterBrand(5, "Harvard", modelsHarvard),
        PrinterBrand(6, "IMIN", modelsImin),
        PrinterBrand(7, "IWARE", modelsIware),
        PrinterBrand(8, "Kassen", modelsKassen),
        PrinterBrand(9, "K-Touch", modelsKtouch),
        PrinterBrand(10, "MiniPos", modelsMiniPos),
        PrinterBrand(11, "Panda", modelsPanda),
        PrinterBrand(12, "Silicon", modelsSilicon),
        PrinterBrand(13, "Star Micronics", modelsStar),
        PrinterBrand(14, "Sunmi", modelsSunmi),
        PrinterBrand(15, "Unicorn", modelsUnicorn),
        PrinterBrand(16, "VSC", modelsVsc),
        PrinterBrand(17, "Windows Print Spooler", modelsWindowsPrintSpooler),
        PrinterBrand(18, "WINTEC", modelsWintec),
        PrinterBrand(19, "Xcheng Mobile POS", modelsXcMobilePos),
        PrinterBrand(20, "ZJiang", modelsZJiang),
        PrinterBrand(21, "ZoneRich", modelsZoneRich),
        PrinterBrand(22, "PAX", modelsPax),
        PrinterBrand(23, "TELPO", modelTelpo),
        PrinterBrand(24, "COMSON", modelComson),
    )
}