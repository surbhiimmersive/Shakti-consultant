package com.shakticonsultant.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.shakticonsultant.R;

public class ProgressClass {

    public static Dialog LoadingSpinner(Context mContext){
        Dialog pd = new Dialog(mContext, android.R.style.Theme_Black);
        View view = LayoutInflater.from(mContext).inflate(R.layout.progressbar, null);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.getWindow().setBackgroundDrawableResource(in.akshit.horizontalcalendar.R.color.transparent);
        pd.setContentView(view);
        return pd;
    }
}
