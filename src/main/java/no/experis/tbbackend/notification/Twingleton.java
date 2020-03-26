package no.experis.tbbackend.notification;

public class Twingleton {


    private int max_vacation_days;

    private static Twingleton instance;

    private Twingleton() {
        max_vacation_days = 0;
    }

    public static Twingleton getInstance() {
        if (instance == null) {
            instance = new Twingleton();
        }
        return instance;
    }

    public int getMax_vacation_days() {
        return max_vacation_days;
    }
    public void setMax_vacation_days(int max_vacation_days) {
        this.max_vacation_days = max_vacation_days;
    }

}
