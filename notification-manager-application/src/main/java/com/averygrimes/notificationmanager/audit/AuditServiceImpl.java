package com.averygrimes.notificationmanager.audit;

import com.averygrimes.notificationmanager.AuditNotificationRequest;
import com.averygrimes.notificationmanager.AuditType;
import com.averygrimes.notificationmanager.pojo.AuditRepoResponse;
import com.averygrimes.notificationmanager.pojo.AuditResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 5/13/20
 * https://github.com/helloavery
 */

public class AuditServiceImpl implements AuditService{

    private final static Logger LOGGER = LogManager.getLogger(AuditServiceImpl.class);

    private final static String USER_ACCOUNT_ACTIVITY = "ACCOUNT";
    private final static String EMAIL_ACCOUNT_ACTIVITY = "EMAIL";
    private final static String ENTRY_ACCOUNT_ACTIVITY = "ENTRY";
    private AuditDAO auditDAO;

    @Inject
    public AuditServiceImpl(AuditDAO auditDAO){
        this.auditDAO = auditDAO;
    }

    @Override
    public AuditResponse createAudit(AuditNotificationRequest auditNotificationRequest) {
        AuditResponse auditResponse = new AuditResponse();
        AuditType auditType = auditNotificationRequest.getAuditType();
        try {
            AuditRepoResponse repositoryResponse = new AuditRepoResponse();
            LOGGER.info("Determining auditing type for {}", auditType);
            if (auditType.getAuditCode().contains(USER_ACCOUNT_ACTIVITY) || auditType.getAuditCode().contains(EMAIL_ACCOUNT_ACTIVITY)) {
                LOGGER.info("Event is user account related, creating user audit event");
                repositoryResponse = auditDAO.createUserAuditEvent(auditNotificationRequest);
                auditResponse.setSuccess(true);
            } else if (auditType.getAuditCode().contains(ENTRY_ACCOUNT_ACTIVITY)) {
                LOGGER.info("Event is entry related, creating entry audit event");
                repositoryResponse = auditDAO.createUserAuditEvent(auditNotificationRequest);
                auditResponse.setSuccess(true);
            }else{
                LOGGER.warn("Invalid audit entry event {}", auditType);
            }

            if(CollectionUtils.isNotEmpty(repositoryResponse.getErrors())){
                auditResponse.getErrors().addAll(repositoryResponse.getErrors());
            }
        } catch (Exception e) {
            LOGGER.error("AuditServiceImpl Error: Could not create audit event for username {} and code {}, {}", auditNotificationRequest.getActionedBy(), auditType, e.getMessage());
            auditResponse.getErrors().add(e.getMessage());
        }
        return auditResponse;
    }
}
