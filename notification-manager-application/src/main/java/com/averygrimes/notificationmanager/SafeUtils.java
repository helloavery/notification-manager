package com.averygrimes.notificationmanager;

/**
 * @author Avery Grimes-Farrow
 * Created on: 6/23/20
 * https://github.com/helloavery
 */

public class SafeUtils {

    public static <T> T safe(T object, Class<T> instance){
        try{
            return object == null ? instance.newInstance() : object;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
