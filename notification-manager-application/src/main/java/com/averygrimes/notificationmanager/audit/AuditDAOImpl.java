package com.averygrimes.notificationmanager.audit;

import com.averygrimes.common.mongodb.MongoDBBase;
import com.averygrimes.notificationmanager.AuditNotificationRequest;
import com.averygrimes.notificationmanager.AuditTrail;
import com.averygrimes.notificationmanager.pojo.AuditRepoResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 5/13/20
 * https://github.com/helloavery
 */

@Repository
public class AuditDAOImpl implements AuditDAO{

    private static final Logger LOGGER = LogManager.getLogger(AuditDAOImpl.class);

    private MongoDBBase mongoDBBase;

    private static final String AUDIT_TRAIL_MONGO_COLLECTION = "auditTrail";
    private static final String AUDIT_SUCCESS = "Successfully created audit event %s for user %s";

    @Resource(name = "AuditMongoDB")
    public void setMongoDBBase(MongoDBBase mongoDBBase) {
        this.mongoDBBase = mongoDBBase;
    }

    @Override
    public AuditRepoResponse createUserAuditEvent(AuditNotificationRequest auditNotificationRequest) {
        AuditRepoResponse auditRepoResponse = new AuditRepoResponse();
        try {
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setAuditAction(auditNotificationRequest.getAuditType().getAuditCode());
            auditTrail.setAuditDescription(auditNotificationRequest.getAuditType().getMessage() + auditNotificationRequest.getActionedBy());
            auditTrail.setActionedBy(auditNotificationRequest.getActionedBy());
            auditTrail.setDateActioned(new Timestamp(new Date().getTime()));
            mongoDBBase.insertDocument(AUDIT_TRAIL_MONGO_COLLECTION, AuditTrailAdaptor.toDBObject(auditTrail));
            auditRepoResponse.setResult(String.format(AUDIT_SUCCESS, auditNotificationRequest.getAuditType().getAuditCode(), auditNotificationRequest.getActionedBy()));
            auditRepoResponse.setSuccessful(true);
        } catch (Exception e) {
            LOGGER.error("Could not create audit event for audit code {} and user {}, {}", auditNotificationRequest.getAuditType().getAuditCode(), auditNotificationRequest.getActionedBy(), e.getMessage());
            List<String> errors = new ArrayList<>();
            errors.add("Audit DAO: Error creating user audit events" + "," + e.getMessage());
            auditRepoResponse.setErrors(errors);
        }
        return auditRepoResponse;
    }
}
