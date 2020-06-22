package com.averygrimes.notificationmanager.audit;

import com.averygrimes.notificationmanager.AuditNotificationRequest;
import com.averygrimes.notificationmanager.pojo.AuditRepoResponse;

/**
 * @author Avery Grimes-Farrow
 * Created on: 5/13/20
 * https://github.com/helloavery
 */

public interface AuditDAO {

    AuditRepoResponse createUserAuditEvent(AuditNotificationRequest auditNotificationRequest) throws Exception;
}
