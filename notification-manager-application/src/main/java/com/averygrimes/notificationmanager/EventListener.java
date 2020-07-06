package com.averygrimes.notificationmanager;

import com.averygrimes.notificationmanager.audit.AuditDAO;
import com.averygrimes.notificationmanager.email.EmailService;
import com.averygrimes.notificationmanager.exceptions.InvalidEmailNotificationRequestException;
import com.averygrimes.notificationmanager.pojo.AuditRepoResponse;
import com.averygrimes.notificationmanager.requests.EmailNotificationRequestVerification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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
    private final Map<EmailNotificationType, Set<String>> emailMessageTracker = new HashMap<>();
    private final Map<AuditType, Set<String>> auditMessageTracker = new HashMap<>();

    @Inject
    public void setEmailService(EmailNotificationRequestVerification requestVerification, EmailService emailService, AuditDAO auditDAO) {
        this.requestVerification = requestVerification;
        this.emailService = emailService;
        this.auditDAO = auditDAO;
    }

    @PostConstruct
    public void init(){
        Stream.of(EmailNotificationType.values()).forEach(notificationType -> emailMessageTracker.put(notificationType, new HashSet<>()));
        Stream.of(AuditType.values()).forEach(auditType -> auditMessageTracker.put(auditType, new HashSet<>()));
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

            EmailNotificationType notificationType = emailNotificationRequest.getEmailNotificationType();
            if(!emailMessageTracker.get(notificationType).contains(emailNotificationRequest.getRequestUUID())){
                boolean isSuccessful = false;
                switch (notificationType){
                    case VERIFICATION:
                        isSuccessful = emailService.sendEmailAddressVerificationEmail(emailNotificationRequest.getContextURL(), emailNotificationRequest.getRecipientEmailAddress(),
                                emailNotificationRequest.getRecipientName(), emailNotificationRequest.getEmailToken());
                        break;
                    case PASSWORD_CHANGE:
                        isSuccessful = emailService.sendPasswordChangeEmail(emailNotificationRequest.getContextURL(), emailNotificationRequest.getRecipientEmailAddress(),
                                emailNotificationRequest.getRecipientName());
                        break;
                }
                if(isSuccessful){
                    emailMessageTracker.get(notificationType).add(emailNotificationRequest.getRequestUUID());
                }else{
                    LOGGER.warn("Operation {} not successful, trying again with subsequent message {}", emailNotificationRequest.getEmailNotificationType(), emailNotificationRequest.getRequestUUID());
                }
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
            if(!auditMessageTracker.get(auditNotificationRequest.getAuditType()).contains(auditNotificationRequest.getRequestUUID())){
                AuditRepoResponse auditRepoResponse = auditDAO.createUserAuditEvent(auditNotificationRequest);
                if(auditRepoResponse.isSuccessful()){
                    auditMessageTracker.get(auditNotificationRequest.getAuditType()).add(auditNotificationRequest.getRequestUUID());
                }
            }
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