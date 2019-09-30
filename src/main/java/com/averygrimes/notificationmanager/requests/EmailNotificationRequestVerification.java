package com.averygrimes.notificationmanager.requests;

import com.averygrimes.core.pojo.EmailNotificationRequest;
import com.averygrimes.notificationmanager.exceptions.InvalidEmailNotificationRequestException;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-14
 * https://github.com/helloavery
 */

public class EmailNotificationRequestVerification {

    public boolean isValidEmailNotificationRequest(EmailNotificationRequest emailNotificationRequest){
        boolean isValidRequest = false;
        if(emailNotificationRequest.getEmailNotification() == null){
            throw InvalidEmailNotificationRequestException.buildResponse("Email Notification Type is missing");
        }
        switch (emailNotificationRequest.getEmailNotification()){
            case VERIFICATION:
                if(emailNotificationRequest.getContextURL() != null && emailNotificationRequest.getRecipientEmailAddress() != null
                    && emailNotificationRequest.getRecipientName() != null && emailNotificationRequest.getEmailToken() != null){
                    isValidRequest = true;
                }
                break;
            case PASSWORD_CHANGE:
                if(emailNotificationRequest.getContextURL() != null && emailNotificationRequest.getRecipientEmailAddress() != null
                && emailNotificationRequest.getRecipientName() != null){
                    isValidRequest = true;
                }
                break;
        }
        return isValidRequest;
    }
}
