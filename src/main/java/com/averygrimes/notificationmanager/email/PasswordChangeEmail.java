package com.averygrimes.notificationmanager.email;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-07
 * https://github.com/helloavery
 */

public class PasswordChangeEmail implements EmailContent{

    private final VelocityEngine velocityEngine;
    private final String name;
    private final String emailAddress;

    protected PasswordChangeEmail(final VelocityEngine velocityEngine, final String name, final String emailAddress){
        this.velocityEngine = velocityEngine;
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public String getEmailContent(){
        Template template = velocityEngine.getTemplate("/PasswordChangeEmailTemplate.vm");
        VelocityContext context = new VelocityContext();
        context.put("name", name);
        context.put("dateActioned", "");
        context.put("com/averygrimes/notificationmanager/email", emailAddress);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
}
