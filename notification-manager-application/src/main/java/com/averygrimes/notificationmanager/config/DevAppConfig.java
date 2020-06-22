package com.averygrimes.notificationmanager.config;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Avery Grimes-Farrow
 * Created on: 6/21/20
 * https://github.com/helloavery
 */

@Configuration
@Profile("dev")
public class DevAppConfig extends BaseAppConfig{

    private Environment env;

    @Inject
    public void setEnvironment(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() throws IOException {
        InputStream keyringInputStream = getClass().getClassLoader().getResourceAsStream(env.getProperty("secrets.keyring"));
        InputStream mailgunKeyInputStream = getClass().getClassLoader().getResourceAsStream(env.getProperty("secrets.mailgunKey"));
        if(keyringInputStream == null || mailgunKeyInputStream == null){
            throw new RuntimeException("Could not load one or more of the credential files, check if the location specified is correct");
        }
        programArguments.setMailgunKeyValue(IOUtils.toString(mailgunKeyInputStream, StandardCharsets.UTF_8));
        getKeyValuePair(IOUtils.toString(keyringInputStream, StandardCharsets.UTF_8));
    }
}
