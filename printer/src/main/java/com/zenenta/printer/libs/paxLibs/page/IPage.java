package com.zenenta.printer.libs.paxLibs.page;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
// from PaxGLPage
//

import android.graphics.Bitmap;
import android.graphics.Typeface;

import java.util.List;

public interface IPage {

    ILine addLine();

    ILine.IUnit createUnit();

    List<ILine> getLines();

    Typeface getTypeFace();

    void setTypeFace(Typeface typeface);

    void adjustLineSpace(int spacingAdd);

    int getLineSpaceAdjustment();

    interface ILine {
        List<IUnit> getUnits();

        ILine addUnit(IUnit unit);

        ILine adjustTopSpace(int spacingAdd);

        int getTopSpaceAdjustment();

        interface IUnit {
            int NORMAL = Typeface.NORMAL;
            int BOLD = Typeface.BOLD;
            int ITALIC = Typeface.ITALIC;
            int BOLD_ITALIC = Typeface.BOLD_ITALIC;
            int UNDERLINE = 1 << 4;

            String getText();

            IUnit setText(String var1);

            Bitmap getBitmap();

            IUnit setBitmap(Bitmap bitmap);

            int getFontSize();

            IUnit setFontSize(int fontSize);

            int getGravity();

            IUnit setGravity(int gravity);

            IUnit setTextStyle(int textStyle);

            int getTextStyle();

            float getWeight();

            IUnit setWeight(float weight);
        }
    }
}
