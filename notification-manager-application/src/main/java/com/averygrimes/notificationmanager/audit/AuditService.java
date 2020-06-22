package com.averygrimes.notificationmanager.audit;

import com.averygrimes.notificationmanager.AuditNotificationRequest;
import com.averygrimes.notificationmanager.pojo.AuditResponse;

/**
 * @author Avery Grimes-Farrow
 * Created on: 5/13/20
 * https://github.com/helloavery
 */

public interface AuditService {

    AuditResponse createAudit(AuditNotificationRequest auditNotificationRequest);
}
