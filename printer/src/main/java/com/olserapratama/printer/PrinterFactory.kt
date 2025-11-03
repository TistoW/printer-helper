package com.olserapratama.printer

import android.content.Context
import com.olserapratama.printer.implementation.blueprint.label.BlueprintLabelPrinterImpl
import com.olserapratama.printer.implementation.blueprint.thermal.BlueprintThermalPrinterImpl
import com.olserapratama.printer.implementation.comson.ComsonPrinterImpl
import com.olserapratama.printer.implementation.enibit.EnibitPrinterImpl
import com.olserapratama.printer.implementation.eppos.EpposPrinterImpl
import com.olserapratama.printer.implementation.epson.EpsonPrinterImpl
import com.olserapratama.printer.implementation.gprinter.GPrinterImpl
import com.olserapratama.printer.implementation.harvard.HarvardPrinterImpl
import com.olserapratama.printer.implementation.imin.IminPrinterImpl
import com.olserapratama.printer.implementation.iware.IwarePrinterImpl
import com.olserapratama.printer.implementation.ktouch.KTouchPrinterImpl
import com.olserapratama.printer.implementation.kassen.KassenPrinterImpl
import com.olserapratama.printer.implementation.kassen.label.KassenLabelImpl
import com.olserapratama.printer.implementation.kassen.xa_02.Kassen02PrinterImpl
import com.olserapratama.printer.implementation.kassen.xa_921.Kassen921PrinterImpl
import com.olserapratama.printer.implementation.kassen.xa_923.Kassen923PrinterImpl
import com.olserapratama.printer.implementation.minipos.MiniPosPrinterImpl
import com.olserapratama.printer.implementation.panda.PandaPrinterImpl
import com.olserapratama.printer.implementation.pax.PaxPrinterImpl
import com.olserapratama.printer.implementation.silicon.SiliconPrinterImpl
import com.olserapratama.printer.implementation.starmprinter.StarPrinterImpl
import com.olserapratama.printer.implementation.sunmi.SunmiPrinterImpl
import com.olserapratama.printer.implementation.unicorn.UnicornPrinterImpl
import com.olserapratama.printer.util.IPrinter
import com.olserapratama.printer.implementation.vsc.VscPrinterImpl
import com.olserapratama.printer.implementation.wintec.WintecPrinterImpl
import com.olserapratama.printer.implementation.xcheng.XchengPrinterImpl
import com.olserapratama.printer.implementation.zjiang.ZjiangPrinterImpl
import com.olserapratama.printer.implementation.zonerich.ZonerichPrinterImpl
import com.olserapratama.printer.repository.Setting

object PrinterFactory {

    fun createPrinter(
        context: Context,
        printer: Setting
    ): IPrinter {
        return when (printer.brandId) {
            0L -> {
                if (printer.modelId!! < 4L) {
                    BlueprintThermalPrinterImpl(context, printer)
                } else {
                    BlueprintLabelPrinterImpl(context, printer)
                }
            }

            1L -> EnibitPrinterImpl(context, printer)
            2L -> EpsonPrinterImpl(context, printer)
            3L -> EpposPrinterImpl(context, printer)
            4L -> GPrinterImpl(context, printer)
            5L -> HarvardPrinterImpl(context, printer)
            6L -> {
                when (printer.modelId) {
                    6L -> {
                        return Kassen923PrinterImpl(context, printer)
                    }
                    else -> {
                        return IminPrinterImpl(context, printer)
                    }
                }
            }

            7L -> {
                when (printer.modelId) {
                    3L -> {
                        return SiliconPrinterImpl(context, printer)
                    }
                    else -> {
                        return IwarePrinterImpl(context, printer)
                    }
                }
            }
            8L -> {
                when (printer.modelId) {
                    0L -> {
                        return Kassen921PrinterImpl(context, printer)
                    }

                    1L -> {
                        return Kassen923PrinterImpl(context, printer)
                    }

                    2L -> {
                        return Kassen02PrinterImpl(context, printer)
                    }

                    5L -> {
                        return KassenLabelImpl(context, printer)
                    }

                    6L -> {
                        return KassenLabelImpl(context, printer)
                    }

                    else -> {
                        return KassenPrinterImpl(context, printer)
                    }
                }
            }

            9L -> KTouchPrinterImpl(context, printer)
            10L -> MiniPosPrinterImpl(context, printer)
            11L -> PandaPrinterImpl(context, printer)
            12L -> SiliconPrinterImpl(context, printer)
            13L -> StarPrinterImpl(context, printer)
            14L -> SunmiPrinterImpl(context, printer)
            15L -> UnicornPrinterImpl(context, printer)
            16L -> VscPrinterImpl(context, printer)
            //17L -> //"Windows Print Spooler"
            18L -> WintecPrinterImpl(context, printer)
            19L -> XchengPrinterImpl(context, printer)
            20L -> ZjiangPrinterImpl(context, printer)
            21L -> ZonerichPrinterImpl(context, printer)
            22L -> PaxPrinterImpl(context, printer)
            23L -> Kassen923PrinterImpl(context, printer)
            24L -> ComsonPrinterImpl(context, printer)
            else -> throw Exception()
        }
    }
}