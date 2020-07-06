package com.averygrimes.notificationmanager.config;

import com.averygrimes.common.mongodb.MongoDBBase;
import com.averygrimes.credentials.SecretsChestUtils;
import com.averygrimes.notificationmanager.requests.EmailNotificationRequestVerification;
import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Avery Grimes-Farrow
 * Created on: 6/21/20
 * https://github.com/helloavery
 */

@Configuration
public class AppConfig {

    private Environment env;
    protected ProgramArguments programArguments;
    private String sendGridApiKey;
    private String keyringKey;
    private String keyringValue;

    @Inject
    public void setEnvironment(Environment env) {
        this.env = env;
    }

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @PostConstruct
    public void init() throws IOException {
        boolean isDevProfile = Arrays.stream(env.getActiveProfiles()).anyMatch(profile -> profile.equalsIgnoreCase("dev"));
        if(isDevProfile){
            Path keyringFileName = Path.of(getClass().getClassLoader().getResource(env.getProperty("secrets.keyring")).getPath());
            Path sendGridFileName = Path.of(getClass().getClassLoader().getResource(env.getProperty("secrets.sendgridKey")).getPath());
            sendGridApiKey =  Files.readString(sendGridFileName);
            getKeyValuePair(Files.readString(keyringFileName));
        }else{
            SecretsChestUtils secretsChestUtils = new SecretsChestUtils();
        }
    }

    @Bean(name = "AuditMongoDB")
    public MongoDBBase createProductMongoDBBase(){
        return new MongoDBBase(programArguments.getDatasourceHost(), programArguments.getDatasourcePort(), programArguments.getAuditSchema(), keyringKey, keyringValue);
    }

    @Bean
    public SendGrid sendGrid(){
        return new SendGrid(sendGridApiKey);
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
