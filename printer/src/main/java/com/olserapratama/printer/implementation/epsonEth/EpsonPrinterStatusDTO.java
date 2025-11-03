package com.olserapratama.printer.implementation.epsonEth;

import com.epson.epos2.printer.Printer;

/**
 * Created by alitjin on 14/9/16.
 */
public class EpsonPrinterStatusDTO {
    private Printer printer;
    private String destination;
    private String name;
    private boolean printCompleted;

    public EpsonPrinterStatusDTO() {}

    public EpsonPrinterStatusDTO(Printer printer, String name, String destination){
        this.printer = printer;
        this.name = name;
        this.destination = destination;
        this.printCompleted = false;
    }

    public Printer getPrinter(){
        return printer;
    }
    public String getName(){
        return name;
    }
    public String getDestination(){
        return destination;
    }
    public boolean isPrintCompleted(){
        return printCompleted;
    }

    public void setPrintCompleted(boolean completed){
        this.printCompleted = completed;
    }
}
