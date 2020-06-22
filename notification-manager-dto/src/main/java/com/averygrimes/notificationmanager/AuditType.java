package com.averygrimes.notificationmanager;

/**
 * @author Avery Grimes-Farrow
 * Created on: 5/13/20
 * https://github.com/helloavery
 */

public enum AuditType {

    ACCOUNT_CREATED("ACCOUNT_CREATED", "Account has been created for account: "),
    ACCOUNT_UPDATED("ACCOUNT_UPDATED", "Account has been updated for account: "),
    ACCOUNT_SUSPENDED("ACCOUNT_SUSPENDED", "Following account has been suspended: "),
    ACCOUNT_DEACTIVATED("ACCOUNT_DEACTIVATED", "Following account has been deactivated: "),
    ACCOUNT_ACTIVATED("ACCOUNT_ACTIVATED", "Following account has been activated: "),
    ACCOUNT_REACTIVATED("ACCOUNT_REACTIVATED", "Following account has been reactivated: "),
    ACCOUNT_ROLE_UPDATED("ACCOUNT_ROLE_UPDATED", "Following user has had their roles modified: "),
    EMAIL_VERIFICATION_CREATED("EMAIL_VERIFICATION_CREATED", "Following account has requested activation code: "),
    EMAIL_VERIFIED("EMAIL_VERIFIED", "Email verified for account: "),
    EMAIL_VERIFICATION_EXPIRED("EMAIL_VERIFICATION_EXPIRED", "Email verification has expired for account: "),
    ENTRY_ADDED("ENTRY_ADDED", "Entry added by: "),
    ENTRY_UPDATED("ENTRY_UPDATED", "Entry removed by: "),
    ENTRY_REMOVED("ENTRY_REMOVED", "Entry removed for account: ");

    private final String auditCode;
    private final String message;

    AuditType(String auditCode, String message) {
        this.auditCode = auditCode;
        this.message = message;
    }

    public String getAuditCode() {
        return auditCode;
    }

    public String getMessage() {
        return message;
    }
}
