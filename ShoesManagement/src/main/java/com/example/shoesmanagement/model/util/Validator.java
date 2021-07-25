package com.example.shoesmanagement.model.util;

import com.example.shoesmanagement.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.shoesmanagement.model.util.ModelConstant.PASSWORD_NOT_MATCH;
import static com.example.shoesmanagement.model.util.ModelConstant.WRONG_EMAIL_FORMAT;


/**
 * @author Phil Conal
 */
public class Validator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);

    public static void validateEmail(String emailAddress) {
        boolean isEmailFormat = isEmailFormat(emailAddress);
        if (!isEmailFormat) {
            throw new ApplicationException(WRONG_EMAIL_FORMAT, HttpStatus.BAD_REQUEST);
        }
    }

    private static boolean isEmailFormat(String valueToValidate) {
        // Regex
        String regexExpression = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        Pattern regexPattern = Pattern.compile(regexExpression);

        if (valueToValidate != null) {
            if (valueToValidate.indexOf("@") <= 0) {
                return false;
            }
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(valueToValidate);
            return matcher.matches();
        } else { // The case of empty Regex expression must be accepted
            Matcher matcher = regexPattern.matcher("");
            return matcher.matches();
        }
    }

    public static void checkExistingObject(Object object, String message) {
        if (object != null)
            throw new ApplicationException(message, HttpStatus.BAD_REQUEST);

    }

    public static void checkNotFound(Object object, String message) {
        if (object == null)
            throw new ApplicationException(message, HttpStatus.NOT_FOUND);

    }

    public static void checkMatchObject(Object object_a, Object object_b) {
        if (!object_a.equals(object_b))
            throw new ApplicationException(PASSWORD_NOT_MATCH, HttpStatus.BAD_REQUEST);
    }
}

