package com.wbsrisktaskerx.wbsrisktaskerx.common.constants;

public class EmailConstants {
    public static final String PLACEHOLDER_TEMPLATES_EMAIL = "templates/email-otp.html";
    public static final String PLACEHOLDER_SUBJECT_OTP_CODE = "{{OTP_CODE}}";
    public static final String PLACEHOLDER_SUBJECT_EMAIL_SUBJECT = "{{EMAIL_SUBJECT}}";
    public static final String PLACEHOLDER_SUBJECT_SEND_OTP = "OTP Verification";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static final String PLACEHOLDER_TEMPLATES_TEMPORARY_PASSWORD_EMAIL = "templates/email-temporary-password.html";
    public static final String PLACEHOLDER_SUBJECT_SEND_TEMPORARY_PASSWORD = "Admin Temporary Password";
    public static final String PLACEHOLDER_ADMIN_NAME = "{{ADMIN_NAME}}";
    public static final String PLACEHOLDER_ADMIN_EMAIL = "{{ADMIN_EMAIL}}";
    public static final String PLACEHOLDER_TEMPORARY_PASSWORD = "{{TEMPORARY_PASSWORD}}";
}
