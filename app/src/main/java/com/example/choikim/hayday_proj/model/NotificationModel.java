package com.example.choikim.hayday_proj.model;

import android.text.Editable;

/**

 * Created by khy12 on 2017-12-18.
 */

public class NotificationModel {
    public String to;
    //backward noti
    public Notification notification=new Notification();
    //forward noti
    public Data data = new Data();

    public static class Notification{
        public String title;
        public String text;
    }

    public static class Data{
        public String title;
        public String text;
    }

}
