package com.averygrimes.notificationmanager.email;

import com.averygrimes.notificationmanager.SecretsContainer;
import com.averygrimes.notificationmanager.exceptions.EmailSenderException;
import com.averygrimes.notificationmanager.pojo.Constants;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-07
 * https://github.com/helloavery
 */

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LogManager.getLogger(EmailServiceImpl.class);
    private VelocityEngine velocityEngine;
    private SecretsContainer secretsContainer;

    @Inject
    public EmailServiceImpl(VelocityEngine velocityEngine, SecretsContainer secretsContainer) {
        this.velocityEngine = velocityEngine;
        this.secretsContainer = secretsContainer;
    }

    @Override
    public boolean sendEmailAddressVerificationEmail(String contextUrl, String emailAddress, String name, String emailToken){
        String subject = "Please verify your Forecaster E-mail Address";
        EmailContent emailContent = getVerificationEmail(emailAddress, name, emailToken, contextUrl);
        if (sendEmail(emailAddress, subject, emailContent)) {
            return true;
        } else {
            LOGGER.error("Error sending e-mail");
            throw EmailSenderException.buildResponse("Could not send account e-mail verification for e-mail: " + emailAddress);
        }
    }

    @Override
    public boolean sendPasswordChangeEmail(String contextUrl, String emailAddress, String name){
        String subject = "Forecaster Password Change";
        EmailContent emailContent = getPasswordChangeEmail(emailAddress, name);
        if (!sendEmail(emailAddress, subject, emailContent)) {
            LOGGER.error("Error sending e-mail");
            throw EmailSenderException.buildResponse("Could not send account password change for e-mail: " + emailAddress);
        }
        return true;
    }

    private EmailContent getVerificationEmail(String emailAddress, String name, String emailToken, String contextUrl) {
        return new AddressVerificationEmail(velocityEngine, name, emailAddress, emailToken, contextUrl);
    }

    private EmailContent getPasswordChangeEmail(String emailAddress, String name) {
        return new PasswordChangeEmail(velocityEngine, name, emailAddress);
    }

    private boolean sendEmail(String emailAddress, String subject, EmailContent emailContent) {
        boolean emailEventSuccessful = false;
        try {
            Configuration configuration = new Configuration()
                    .domain(Constants.MAILGUN_DOMAIN_NAME)
                    .apiKey(secretsContainer.getMailgunAPIkey())
                    .from("Do-Not-Reply", "donotreply@forecaster.itavery.com");
            Mail.using(configuration)
                    .to(emailAddress)
                    .subject(subject)
                    .text("Please take appropriate action")
                    .html(emailContent.getEmailContent())
                    .build()
                    .send();
            LOGGER.info("Successfully sent {} email to {}", subject, emailAddress);
            emailEventSuccessful = true;
        } catch (Exception e) {
            LOGGER.error("Error sending e-mail {} to {}", subject, emailAddress);
        }
        return emailEventSuccessful;
    }
}
