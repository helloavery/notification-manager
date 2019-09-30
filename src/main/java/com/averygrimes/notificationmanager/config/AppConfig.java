package com.averygrimes.notificationmanager.config;

import com.averygrimes.notificationmanager.requests.EmailNotificationRequestVerification;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-14
 * https://github.com/helloavery
 */

@Configuration
@Component
public class AppConfig {

    @Bean
    public VelocityEngine velocityEngine(){
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        return velocityEngine;
    }

    @Bean
    public EmailNotificationRequestVerification emailNotificationRequestVerification(){
        return new EmailNotificationRequestVerification();
    }
}
