package no.experis.tbbackend.notification;


import java.util.Date;


public class CommentNotification extends Notification {


    private String message;
    private String URL = "https://localhost:8080";
    private long userId;


    public CommentNotification(String type, Date date, String datetimestamp,
                                       String message, String vr_id, Long user_id, boolean isAdmin) {
        super(type, date, datetimestamp, user_id, isAdmin);
        this.message = message;
        this.URL = this.URL.concat(vr_id);


        System.out.println("VR get url: " + this.URL);
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
