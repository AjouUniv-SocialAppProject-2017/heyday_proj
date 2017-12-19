package com.example.choikim.hayday_proj.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khy12 on 2017-11-21.
 */

public class UserModel implements Cloneable{
    public String uid;
    public String name;
    public String profileImagePath;
    public String email;
    public String backgroundImagePath;
    public String introduce;
    public long age;
    public String location;
    public String gender;
    public String pushToken;

    public Map<String, Boolean> preference= new HashMap<>();

    public UserModel(){
    }


}
