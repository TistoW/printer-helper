package com.zenenta.printer

import android.content.Context
import com.zenenta.printer.implementation.blueprint.label.BlueprintLabelPrinterImpl
import com.zenenta.printer.implementation.blueprint.thermal.BlueprintThermalPrinterImpl
import com.zenenta.printer.implementation.comson.ComsonPrinterImpl
import com.zenenta.printer.implementation.enibit.EnibitPrinterImpl
import com.zenenta.printer.implementation.eppos.EpposPrinterImpl
import com.zenenta.printer.implementation.epson.EpsonPrinterImpl
import com.zenenta.printer.implementation.gprinter.GPrinterImpl
import com.zenenta.printer.implementation.harvard.HarvardPrinterImpl
import com.zenenta.printer.implementation.imin.IminPrinterImpl
import com.zenenta.printer.implementation.iware.IwarePrinterImpl
import com.zenenta.printer.implementation.ktouch.KTouchPrinterImpl
import com.zenenta.printer.implementation.kassen.KassenPrinterImpl
import com.zenenta.printer.implementation.kassen.label.KassenLabelImpl
import com.zenenta.printer.implementation.kassen.xa_02.Kassen02PrinterImpl
import com.zenenta.printer.implementation.kassen.xa_921.Kassen921PrinterImpl
import com.zenenta.printer.implementation.kassen.xa_923.Kassen923PrinterImpl
import com.zenenta.printer.implementation.minipos.MiniPosPrinterImpl
import com.zenenta.printer.implementation.panda.PandaPrinterImpl
import com.zenenta.printer.implementation.pax.PaxPrinterImpl
import com.zenenta.printer.implementation.silicon.SiliconPrinterImpl
import com.zenenta.printer.implementation.starmprinter.StarPrinterImpl
import com.zenenta.printer.implementation.sunmi.SunmiPrinterImpl
import com.zenenta.printer.implementation.unicorn.UnicornPrinterImpl
import com.zenenta.printer.util.IPrinter
import com.zenenta.printer.implementation.vsc.VscPrinterImpl
import com.zenenta.printer.implementation.wintec.WintecPrinterImpl
import com.zenenta.printer.implementation.xcheng.XchengPrinterImpl
import com.zenenta.printer.implementation.zjiang.ZjiangPrinterImpl
import com.zenenta.printer.implementation.zonerich.ZonerichPrinterImpl
import com.zenenta.printer.repository.Setting

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