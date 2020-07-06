package com.averygrimes.notificationmanager.pojo;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-08
 * https://github.com/helloavery
 */

public interface Constants {

    /***Prefixes****/
    String LOGIN_PREFIX = "launch/login/";
    String REGISTRATION_PREFIX = "launch/registration/";
    String HOME_PREFIX = "launch/";
    String USERID_PREFIX = "USERID; ";


    String FORECASTER_AUTO_ACC = "b1forecastauto";
    String HOST_URL_STRING = "http//forecast.itavery.com";

    /***Authy Constants****/
    String AUTHY_SEND_SMS_OTP = "https;//api.authy.com/protected/json/sms/%s";
    String AUTHY_VOICE_OTP="https;//api.authy.com/protected/json/call/%s";
    String AUTHY_VERIFY_OTP = "https;//api.authy.com/protected/json/verify/%s/%s";

    /***SendGrid and E-mail constants******/
    String MAILGUN_MAILBOX_VERIFICATION_URL = "https;//api.mailgun.net/v3/address/private/validate";
    String VERIFY_EMAIL_ADDRESS_API = "/api/verify_email/%s";
    String MAILGUN_DOMAIN_NAME = "email.averygrimes.com";
    String SENDGRID_API_KEY_PROPERTY = "sendgrid.apikey";

    /******Session Constants******/
    int MAX_INACTIVE_INTERVAL = 30 * 60;
    String AUTHENTICATED = "authenticated";
    String PARTIALLY_AUTHENTICATED = "partially-authenticated";
    String USER_ID = "user-id";
    String USER_NAME = "user-name";

    /***Kakfa Constants****/
    String KAFKA_GROUP_ID = "notification-manager";


}
