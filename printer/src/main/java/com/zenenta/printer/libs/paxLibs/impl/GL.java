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
package com.zenenta.printer.libs.paxLibs.impl;

import android.content.Context;

import com.zenenta.printer.libs.paxLibs.IGL;
import com.zenenta.printer.libs.paxLibs.imgprocessing.IImgProcessing;

public class GL implements IGL {

    private ImgProcessingImp imgProcessing;

    private static GL instance = null;

    public GL(Context context) {
        imgProcessing = new ImgProcessingImp(context);
    }

    public static GL getInstance(Context context) {
        if (instance == null) {
            instance = new GL(context);
        }
        return instance;
    }

    @Override
    public IImgProcessing getImgProcessing() {
        return imgProcessing;
    }
}
