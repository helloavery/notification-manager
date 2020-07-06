package com.averygrimes.notificationmanager;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Avery Grimes-Farrow
 * Created on: 5/13/20
 * https://github.com/helloavery
 */

@Data
public class AuditNotificationRequest implements Serializable {

    private static final long serialVersionUID = -1730632010600938156L;

    private String productType;
    private String auditAction;
    private String actionedBy;
    private Timestamp dateActioned;
    private AuditType auditType;
    private String auditDescription;
    private String requestUUID;
}
