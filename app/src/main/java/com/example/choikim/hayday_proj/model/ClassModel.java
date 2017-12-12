package com.example.choikim.hayday_proj.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamti on 2017-12-02.
 */

public class ClassModel implements Serializable {


    public String class_image;
    public String class_name;
    public String class_index;
    public String class_content;
    public String class_local;
    public String class_time;
    public String uid;
    public String imagePath;
    public String userId;
    public String user_name;

    public int starCount = 0;
    public Map<String, String> participant = new HashMap<>();



}
