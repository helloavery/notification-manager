package com.averygrimes.notificationmanager.config;

import com.averygrimes.common.mongodb.MongoDBBase;
import com.averygrimes.notificationmanager.requests.EmailNotificationRequestVerification;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;

import javax.inject.Inject;
import java.util.Scanner;

/**
 * @author Avery Grimes-Farrow
 * Created on: 6/21/20
 * https://github.com/helloavery
 */

public class BaseAppConfig {

    protected ProgramArguments programArguments;
    private String keyringKey;
    private String keyringValue;

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @Bean(name = "AuditMongoDB")
    public MongoDBBase createProductMongoDBBase(){
        return new MongoDBBase(programArguments.getDatasourceHost(), programArguments.getDatasourcePort(), programArguments.getAuditSchema(), keyringKey, keyringValue);
    }

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

    protected void getKeyValuePair(String keyValueString){
        try (Scanner scanner = new Scanner(keyValueString)) {
            keyringKey = scanner.nextLine().replace("KEY=","");
            keyringValue = scanner.nextLine().replace("VALUE=","");
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve credentials from secrets string provided", e);
        }
    }
}
