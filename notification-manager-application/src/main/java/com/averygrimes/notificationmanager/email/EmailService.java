package com.averygrimes.notificationmanager.email;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-07
 * https://github.com/helloavery
 */

public interface EmailService {

    boolean sendEmailAddressVerificationEmail(String contextUrl, String emailAddress, String name, String emailToken);

    boolean sendPasswordChangeEmail(String contextUrl, String emailAddress, String name);
}
