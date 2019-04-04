package com.kirodinstudios.dungeoneerspack;

import android.annotation.SuppressLint;

public class DisplayDataConverter {

    @SuppressLint("DefaultLocale")
    public static String getStringRepresentationOfDouble(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
