package com.example.choikim.hayday_proj.model;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khy12 on 2017-11-21.
 */

public class BoardModel {
    public String uid;//write board user

    //단순 게시글과 수요조사 게시글 모델 구분
    public String flag;
    public String wTime;
    public String context;
    public String imagePath;
    public String name;
    public int cntGood;

    public Map<String,Comment> comment=new HashMap<>();//댓글 기능

    public static class Comment{
        public String name;
        public String uid;
        public String message;
        public String wTime;
    }

}
