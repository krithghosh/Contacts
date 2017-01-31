package com.gojek.krith.contacts.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by krith on 31/01/17.
 */

public class SnackbarUtils {
    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}