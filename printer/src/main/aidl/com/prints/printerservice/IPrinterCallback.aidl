// IPrinterCallback.aidl
package com.prints.printerservice;

// Declare any non-default types here with import statements

interface IPrinterCallback {

	oneway void  onException(int code, String msg);

	oneway void  onLength(long current, long total);

	oneway void  onComplete();
}
