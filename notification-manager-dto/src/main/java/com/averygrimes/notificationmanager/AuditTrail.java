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
public class AuditTrail implements Serializable {

    private static final long serialVersionUID = 3465980847788081898L;

    private String auditAction;
    private String actionedBy;
    private Timestamp dateActioned;
    private String auditDescription;
}
