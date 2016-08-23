package com.deeal.exchange.Bean;

import com.deeal.exchange.model.User;

/**
 * Created by Administrator on 2015/7/13.
 */
public class MessageItemBean {
    private User sender;
    private String message;
    private String time;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
