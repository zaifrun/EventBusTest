package org.webdevelopment.eventbustest;

/**
 * Created by makn on 28-08-2016.
 */
public class MessageEvent {
    public String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

}
