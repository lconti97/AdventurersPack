package com.kirodinstudios.dungeoneerspack;

import android.annotation.SuppressLint;

import com.kirodinstudios.dungeoneerspack.model.EquipmentStack;

public class DisplayDataConverter {

    @SuppressLint("DefaultLocale")
    public static String getStringRepresentationOfDouble(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    public static String getA(EquipmentStack stack) {
        return "";
    }


}
