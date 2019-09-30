package com.averygrimes.notificationmanager.email;

import com.averygrimes.notificationmanager.pojo.Constants;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-07
 * https://github.com/helloavery
 */

public class AddressVerificationEmail implements EmailContent{

    private final VelocityEngine velocityEngine;
    private final String name;
    private final String email;
    private final String emailToken;
    private final String contextUrl;

    protected AddressVerificationEmail(final VelocityEngine velocityEngine, final String name, final String email, final String emailToken, final String contextUrl){
        this.velocityEngine = velocityEngine;
        this.name = name;
        this.email = email;
        this.emailToken = emailToken;
        this.contextUrl = contextUrl;
    }

    @Override
    public String getEmailContent(){
        Template template = velocityEngine.getTemplate("VerificationEmailTemplate.vm");
        VelocityContext context = new VelocityContext();
        context.put("name", name);
        context.put("com/averygrimes/notificationmanager/email", email);
        context.put("VERIFY_EMAIL_LINK", String.format(contextUrl + Constants.VERIFY_EMAIL_ADDRESS_API, emailToken));
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

}
