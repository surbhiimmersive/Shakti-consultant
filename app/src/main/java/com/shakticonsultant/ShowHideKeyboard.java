package com.shakticonsultant;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ShowHideKeyboard {

    public static void showKeyboard(View view){
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

}
