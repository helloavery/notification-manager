package com.averygrimes.notificationmanager;

import com.averygrimes.notificationmanager.audit.AuditDAO;
import com.averygrimes.notificationmanager.email.EmailService;
import com.averygrimes.notificationmanager.exceptions.InvalidEmailNotificationRequestException;
import com.averygrimes.notificationmanager.requests.EmailNotificationRequestVerification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-06
 * https://github.com/helloavery
 */

@Service
public class EventListener {

    private static final Logger LOGGER = LogManager.getLogger(EventListener.class);
    private EmailNotificationRequestVerification requestVerification;
    private EmailService emailService;
    private AuditDAO auditDAO;

    @Inject
    public void setEmailService(EmailNotificationRequestVerification requestVerification, EmailService emailService, AuditDAO auditDAO) {
        this.requestVerification = requestVerification;
        this.emailService = emailService;
        this.auditDAO = auditDAO;
    }

    @KafkaListener(
            topics = "itavery-email")
    public void emailNotificationListen(@Payload String request){
        LOGGER.info("Received greeting message: {}", request);
        try{
            EmailNotificationRequest emailNotificationRequest = convertMessage(request, EmailNotificationRequest.class);
            if(!requestVerification.isValidEmailNotificationRequest(emailNotificationRequest)){
                throw InvalidEmailNotificationRequestException.buildResponse("Invalid Email Notification Request");
            }
            switch (emailNotificationRequest.getEmailNotificationType()){
                case VERIFICATION:
                    emailService.sendEmailAddressVerificationEmail(emailNotificationRequest.getContextURL(), emailNotificationRequest.getRecipientEmailAddress(),
                            emailNotificationRequest.getRecipientName(), emailNotificationRequest.getEmailToken());
                    break;
                case PASSWORD_CHANGE:
                    emailService.sendPasswordChangeEmail(emailNotificationRequest.getContextURL(), emailNotificationRequest.getRecipientEmailAddress(),
                            emailNotificationRequest.getRecipientName());
                    break;
            }
        } catch(Exception e){
            LOGGER.error("Error completing email notification request {}", request, e);
        }
    }

    @KafkaListener(
            topics = "itavery-audit")
    public void auditNotificationListen(@Payload String request){
        LOGGER.info("Received audit request: {}", request);
        try{
            AuditNotificationRequest auditNotificationRequest = convertMessage(request, AuditNotificationRequest.class);
            auditDAO.createUserAuditEvent(auditNotificationRequest);
        }catch(Exception e){
            LOGGER.error("Error completing audit notification request {}", request, e);
        }
    }

    private <T> T convertMessage(String request, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(request, clazz);
        }
        catch(Exception e){
            return clazz.newInstance();
        }
    }
}
