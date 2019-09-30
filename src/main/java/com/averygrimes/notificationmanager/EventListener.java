package com.averygrimes.notificationmanager;

import com.averygrimes.core.pojo.EmailNotificationRequest;
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
import java.util.concurrent.CountDownLatch;

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
    private CountDownLatch emailLatch = new CountDownLatch(1);

    @Inject
    public void setEmailService(EmailNotificationRequestVerification requestVerification, EmailService emailService) {
        this.requestVerification = requestVerification;
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "itavery-email")
    public void emailNotificationListen(@Payload String request){
        LOGGER.info("Received greeting message: {}", request);
        EmailNotificationRequest emailNotificationRequest = convertMessage(request);
        if(!requestVerification.isValidEmailNotificationRequest(emailNotificationRequest)){
            throw InvalidEmailNotificationRequestException.buildResponse("Invalid Email Notification Request");
        }
        switch (emailNotificationRequest.getEmailNotification()){
            case VERIFICATION:
                emailService.sendEmailAddressVerificationEmail(emailNotificationRequest.getContextURL(), emailNotificationRequest.getRecipientEmailAddress(),
                        emailNotificationRequest.getRecipientName(), emailNotificationRequest.getEmailToken());
                break;
            case PASSWORD_CHANGE:
                emailService.sendPasswordChangeEmail(emailNotificationRequest.getContextURL(), emailNotificationRequest.getRecipientEmailAddress(),
                        emailNotificationRequest.getRecipientName());
                break;
        }
    }

    private EmailNotificationRequest convertMessage(String request){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(request, EmailNotificationRequest.class);
        }
        catch(Exception e){
            return new EmailNotificationRequest();
        }
    }
}
