package com.averygrimes.notificationmanager.audit;

import com.averygrimes.notificationmanager.AuditTrail;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 5/13/20
 * https://github.com/helloavery
 */

public class AuditTrailAdaptor {

    public static DBObject toDBObject(AuditTrail auditTrail){
        return new BasicDBObject().append("auditAction", auditTrail.getAuditAction())
                .append("actionedBy",  auditTrail.getActionedBy()).append("dateActioned", auditTrail.getDateActioned())
                .append("auditDescription", auditTrail.getAuditDescription());
    }
}
