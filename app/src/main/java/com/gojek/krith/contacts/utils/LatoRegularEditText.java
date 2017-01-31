package com.gojek.krith.contacts.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by krith on 31/01/17.
 */

public class LatoRegularEditText extends EditText {
    public LatoRegularEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public LatoRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public LatoRegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Lato-Regular.ttf", context);
        setTypeface(customFont);
    }
}