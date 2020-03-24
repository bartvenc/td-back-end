package no.experis.tbbackend.notification;

import java.util.Date;


public abstract class Notification {

    private int notification_id;
    private String type;
    private String datetimestamp;
    private Date date;

    public Notification(String type, Date date, String datetimestamp) {
        this.type = type;
        this.date = null;
        this.datetimestamp = "null";
    }

    public int getNotification_id() {
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

}
