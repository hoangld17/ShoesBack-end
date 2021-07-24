package com.example.shoesmanagement.model.util;

/**
 * @author DiGiEx
 */
public interface Constant {

    String HEADER_TOKEN = "Auth-Token";
    // API format date
    String API_FORMAT_DATE_TIME = "MM/dd/yyyy hh:mm:ss";
    String API_FORMAT_DATE = "MM/dd/yyyy";

    String VALID_XSS = "^((?!<|>)[\\s\\S])*?$";
    String VALID_CURLY_BRACES = "^((?!\\{|\\})[\\s\\S])*?$";

    int SALT_LENGTH = 6;

    String SORT_BY_USER_NAME = "userName";
    String SORT_BY_EMAIL = "email";
    String SORT_BY_LAST_NAME = "lastName";
    String SORT_BY_FIRST_NAME = "firstName";
}


