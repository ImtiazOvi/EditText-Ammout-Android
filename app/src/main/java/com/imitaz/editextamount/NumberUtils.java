package com.imitaz.editextamount;


import android.annotation.SuppressLint;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;

import java.util.Locale;

public class NumberUtils {

    public static String getConverdNumber(String ammount) {
        String numberValue = ammount.replaceAll("\\D+", "");
        return numberValue;
    }

    @SuppressLint("NewApi")

    public static String getValue(String number) {

        number=number+".0";

        String[] valuesArray = number.split("\\.");
        Long longval;
        String value = "";
        if (valuesArray.length > 1) {
            int lstValue = Integer.parseInt(valuesArray[1]);
            if (lstValue > 0) {
                longval = Long.parseLong(valuesArray[0]);
                DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                formatter.applyPattern("#,###,###,###");
                String formattedString = formatter.format(longval);
                value=formattedString+"."+valuesArray[1];

            } else {
                longval = Long.parseLong(valuesArray[0]);
                DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                formatter.applyPattern("#,###,###,###");
                value= formatter.format(longval);

            }
        } else {
            value = number;
        }
        return value;
    }



}
