package com.averygrimes.notificationmanager.email;

import com.averygrimes.notificationmanager.config.ProgramArguments;
import com.averygrimes.notificationmanager.exceptions.EmailSenderException;
import com.averygrimes.notificationmanager.pojo.Constants;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private ProgramArguments programArguments;
    private SendGrid sendGridClient;

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @Inject
    public void setSendGridClient(SendGrid sendGridClient) {
        this.sendGridClient = sendGridClient;
    }

    @Override
    public boolean sendEmailAddressVerificationEmail(String contextUrl, String emailAddress, String name, String emailToken){
        String subject = "Please verify your ITAvery E-mail Address";
        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("subject", subject);
        personalization.addDynamicTemplateData("VERIFY_EMAIL_LINK", String.format(contextUrl + Constants.VERIFY_EMAIL_ADDRESS_API, emailToken));
        personalization.addTo(new Email(emailAddress));
        Mail mailContent = generateMailContent(programArguments.getVerificationTemplateId(), personalization);
        if (sendEmail(mailContent)) {
            return true;
        } else {
            LOGGER.error("Error sending e-mail");
            throw EmailSenderException.buildResponse("Could not send account e-mail verification for e-mail: " + emailAddress);
        }
    }

    @Override
    public boolean sendPasswordChangeEmail(String contextUrl, String emailAddress, String name){
        String subject = "Forecaster Password Change";
        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("subject", subject);
        personalization.addTo(new Email(emailAddress));
        Mail mailContent = generateMailContent(programArguments.getPasswordChangeTemplateId(), personalization);
        if (!sendEmail(mailContent)) {
            LOGGER.error("Error sending e-mail");
            throw EmailSenderException.buildResponse("Could not send account password change for e-mail: " + emailAddress);
        }
        return true;
    }

    private Mail generateMailContent(String templateId, Personalization personalization){
        Mail mail = new Mail();
        mail.setFrom(new Email("Do-Not-Reply <donotreply@averygrimes.com>"));
        mail.setTemplateId(templateId);
        mail.addPersonalization(personalization);
        return mail;
    }

    private boolean sendEmail(Mail mailContent) {
        boolean emailEventSuccessful = false;
        try {
            Request sendGridRequest = new Request();
            sendGridRequest.setMethod(Method.POST);
            sendGridRequest.setEndpoint("mail/send");
            sendGridRequest.setBody(mailContent.build());
            sendGridClient.api(sendGridRequest);
            Response sendGridResponse = sendGridClient.api(sendGridRequest);

            if(sendGridResponse != null){
                int statusCode = sendGridResponse.getStatusCode();
                emailEventSuccessful = statusCode >= 200 && statusCode <= 299;
            }
            LOGGER.info("Email with subject {} to {} result: {}", mailContent.getSubject(), mailContent.getPersonalization().get(0).getTos(), emailEventSuccessful);
        } catch (Exception e) {
            LOGGER.error("Error sending e-mail {} to {}", mailContent.getSubject(), mailContent.getPersonalization().get(0).getTos(), e);
        }
        return emailEventSuccessful;
    }
}