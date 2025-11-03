/*
 * ============================================================================
 * COPYRIGHT
 *              Pax CORPORATION PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with Pax Corporation and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *      Copyright (C) 2017 - ? Pax Corporation. All rights reserved.
 * Module Date: 2017-5-23
 * Module Author: Kim.L
 * Description:
 *
 * ============================================================================
 */
package com.zenenta.printer.libs.paxLibs.imgprocessing;

import android.graphics.Bitmap;

import com.zenenta.printer.libs.paxLibs.page.IPage;
import com.pax.gl.impl.IRgbToMonoAlgorithm;

public interface IImgProcessing {

    byte[] bitmapToJbig(Bitmap bitmap, IRgbToMonoAlgorithm algo);

    Bitmap jbigToBitmap(byte[] jbig);

    byte[] bitmapToMonoDots(Bitmap bitmap, IRgbToMonoAlgorithm algo);

    byte[] bitmapToMonoBmp(Bitmap bitmap, IRgbToMonoAlgorithm algo);

    Bitmap scale(Bitmap bitmap, int w, int h);

    IPage createPage();

    Bitmap pageToBitmap(IPage page, int pageWidth);
}

/* Location:           D:\Android逆向助手_v2.2\PaxGL_V1.00.04_20170303.jar
 * Qualified Name:     com.pax.gl.imgprocessing.IImgProcessing
 * JD-Core Version:    0.6.0
 */