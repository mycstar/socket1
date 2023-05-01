package se211.v5;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    public static final int MESSAGE = 1;
    public static final int LOGOUT = 2;
    public static final int PRIVATE = 3;
    public static final int USERNAME = 4;

    private int type;
    private String message;
    private String sender;
    private String recipient;

    public ChatMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
