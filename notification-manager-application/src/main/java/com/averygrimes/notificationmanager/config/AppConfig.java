package com.averygrimes.notificationmanager.config;

import com.averygrimes.credentials.SecretsChestUtils;
import com.averygrimes.credentials.pojo.CredentialsResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-14
 * https://github.com/helloavery
 */

@Configuration
@Profile("!dev")
public class AppConfig extends BaseAppConfig {

    private SecretsChestUtils secretsChestUtils;

    @Inject
    public void setSecretsChestUtils(SecretsChestUtils secretsChestUtils) {
        this.secretsChestUtils = secretsChestUtils;
    }

    @PostConstruct
    public void init(){
        CredentialsResponse credentialsResponse = secretsChestUtils.getApplicationSecret(programArguments.getMailgunSecretReference());
        programArguments.setMailgunKeyValue(credentialsResponse.getSecretAsString());
    }
}
