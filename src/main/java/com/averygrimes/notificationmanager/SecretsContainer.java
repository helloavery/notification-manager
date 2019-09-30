package com.averygrimes.notificationmanager;

import com.averygrimes.core.credentials.CoreSecretsRetrieval;
import com.averygrimes.core.pojo.S3BucketMap;
import com.averygrimes.core.pojo.SecretCategory;
import com.averygrimes.notificationmanager.config.ProgramArguments;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-08
 * https://github.com/helloavery
 */

@Component
public class SecretsContainer implements InitializingBean {

    private CoreSecretsRetrieval secretsRetrieval;
    private ProgramArguments programArguments;

    @Inject
    public void setSecretsRetrieval(CoreSecretsRetrieval secretsRetrieval) {
        this.secretsRetrieval = secretsRetrieval;
    }

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    private Map<SecretCategory, String> secretCategoryStringMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<SecretCategory, S3BucketMap> s3BucketMap = new HashMap<>();
        s3BucketMap.put(SecretCategory.MAILGUN, new S3BucketMap(programArguments.getS3bucket(),programArguments.getS3bucketObjectMailgun()));
        secretCategoryStringMap = secretsRetrieval.getApplicationSecrets(s3BucketMap);
    }

    public String getMailgunAPIkey(){
        return secretCategoryStringMap.get(SecretCategory.MAILGUN) != null ? secretCategoryStringMap.get(SecretCategory.MAILGUN) : "";
    }
}
