package com.example.mainservice.constants;

public class EmailConstant {
    /*
     * List constants for email configuration
     */
    public static final String KEY_PROTOCOL = "mail.transport.protocol";
    public static final String KEY_AUTH = "mail.smtp.auth";
    public static final String KEY_STARTTLS = "mail.smtp.starttls.enable";
    public static final String KEY_DEBUG = "mail.debug";
    public static final String KEY_SECURE = "mail.smtp.ssl.enable";
    public static final String VALUE_SMTP = "smtp";
    public static final String VALUE_TRUE = "true";
    public static final String VALUE_UTF_8 = "UTF-8";
    public static final String BODY_EMAIL_TEST = "This is a test email";
    public static final String SUBJECT_EMAIL_TEST = "Test email";

    public static final String BODY_EMAIL_FORGOT_PASSWORD = "Room management system\n" +
            "You have requested to reset your password. Your new password is: ";
    public static final String SUBJECT_EMAIL_FORGOT_PASSWORD = "Reset password in room management system";
}
