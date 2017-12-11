package com.example.choikim.hayday_proj.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamti on 2017-12-02.
 */

public class ClassModel {

    public String class_image;
    public String class_name;
    public String class_index;
    public String class_content;
    public String uid;
    public String userId;
    public String user_name;

    public Map<String,Participant> participants=new HashMap<>();//댓글 기능

    public static class Participant{
        public String name;
        public String uid;
        public String email;
    }

}
