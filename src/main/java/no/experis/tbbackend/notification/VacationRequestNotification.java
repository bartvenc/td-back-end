package no.experis.tbbackend.notification;


import java.util.Date;


public class VacationRequestNotification extends Notification {


    private String message;
    private String URL = "https://localhost:8080/request/";
    private long userId;


    public VacationRequestNotification(long notification_id, String type, Date date, String datetimestamp,
                                       String message, String vr_id, Long user_id, boolean isAdmin) {
        super(notification_id, type, date, datetimestamp, user_id, isAdmin);
        this.message = message;
        this.URL = this.URL.concat(vr_id);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

}
