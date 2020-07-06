package com.averygrimes.notificationmanager.requests;


import com.averygrimes.notificationmanager.EmailNotificationRequest;
import com.averygrimes.notificationmanager.exceptions.InvalidEmailNotificationRequestException;
import org.apache.commons.lang.StringUtils;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-14
 * https://github.com/helloavery
 */

public class EmailNotificationRequestVerification {

    public boolean isValidEmailNotificationRequest(EmailNotificationRequest emailNotificationRequest){
        boolean isValidRequest = false;
        if(emailNotificationRequest.getEmailNotificationType() == null){
            throw InvalidEmailNotificationRequestException.buildResponse("Email Notification Type is missing");
        }
        switch (emailNotificationRequest.getEmailNotificationType()){
            case VERIFICATION:
                if(StringUtils.isNotBlank(emailNotificationRequest.getContextURL()) && StringUtils.isNotBlank(emailNotificationRequest.getRecipientEmailAddress())
                    && StringUtils.isNotBlank(emailNotificationRequest.getRecipientName()) && StringUtils.isNotBlank(emailNotificationRequest.getEmailToken())
                && StringUtils.isNotBlank(emailNotificationRequest.getRequestUUID())){
                    isValidRequest = true;
                }
                break;
            case PASSWORD_CHANGE:
                if(StringUtils.isNotBlank(emailNotificationRequest.getContextURL()) && StringUtils.isNotBlank(emailNotificationRequest.getRecipientEmailAddress())
                && StringUtils.isNotBlank(emailNotificationRequest.getRecipientName()) && StringUtils.isNotBlank(emailNotificationRequest.getRequestUUID())){
                    isValidRequest = true;
                }
                break;
        }
        return isValidRequest;
    }
}
