package com.averygrimes.notificationmanager.config;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-08
 * https://github.com/helloavery
 */

public class ProgramArguments {

    private String bootstrapAddress;
    private String S3bucket;
    private String S3bucketObjectMailgun;

    public String getBootstrapAddress() {
        return bootstrapAddress;
    }

    public void setBootstrapAddress(String bootstrapAddress) {
        this.bootstrapAddress = bootstrapAddress;
    }

    public String getS3bucket() {
        return S3bucket;
    }

    public void setS3bucket(String s3bucket) {
        S3bucket = s3bucket;
    }

    public String getS3bucketObjectMailgun() {
        return S3bucketObjectMailgun;
    }

    public void setS3bucketObjectMailgun(String s3bucketObjectMailgun) {
        S3bucketObjectMailgun = s3bucketObjectMailgun;
    }
}
