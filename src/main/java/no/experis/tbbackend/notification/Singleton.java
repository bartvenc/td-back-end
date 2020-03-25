package no.experis.tbbackend.notification;

import java.util.ArrayList;
import java.util.List;

public class Singleton {


    private List<Notification> arrayList;

    private static Singleton instance;

    private Singleton() {
        arrayList = new ArrayList<Notification>();
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
            System.out.println("--------------------------New instance---------------------------------------------");
        }
        return instance;
    }

    public List<Notification> getArrayList() {
        return arrayList;
    }


}
