package no.experis.tbbackend.notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public abstract class Notification {

    private long notification_id;
    private String type;
    private String datetimestamp;
    private Date date;
    private long user_id;
    private boolean isAdmin;


    public Notification(long notification_id, String type, Date date, String datetimestamp, Long user_id, boolean isAdmin) {
        this.notification_id = notification_id;
        this.type = type;
        this.date = date;
        this.datetimestamp = datetimestamp;
        this.user_id = user_id;
        this.isAdmin = isAdmin;
    }

    public long getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatetimestamp() {
        return datetimestamp;
    }

    public void setDatetimestamp(String datetimestamp) {
        this.datetimestamp = datetimestamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
