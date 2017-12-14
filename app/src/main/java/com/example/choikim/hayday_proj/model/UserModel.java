package com.example.choikim.hayday_proj.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khy12 on 2017-11-21.
 */

public class UserModel {
    public String uid;
    public String name;
    public String profileImagePath;
    public String email;
    public String backgroundImagePath;

    public Map<String, Boolean> preference= new HashMap<>();

}
