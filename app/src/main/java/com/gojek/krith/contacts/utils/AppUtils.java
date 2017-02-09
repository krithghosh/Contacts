package com.gojek.krith.contacts.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by krith on 08/02/17.
 */

public class AppUtils {

    public static final String SERVER_ID = "server_id";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidName(final String name) {
        return (!name.isEmpty() && name.length() >= 3);
    }

    public static boolean validatePhoneNumber(final String phone) {
        return (!phone.isEmpty() &&
                ((phone.startsWith("+9199") && phone.length() == 15) ||
                        (phone.startsWith("99") && phone.length() == 12) ||
                        (phone.startsWith("099") && phone.length() == 13)));
    }
}
