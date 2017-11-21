package com.example.choikim.hayday_proj.model;

/**
 * Created by khy12 on 2017-11-21.
 */

public class BoardModel {
    public String uri;

    //단순 게시글과 수요조사 게시글 모델 구분
    public String flag;

    public String wTime;
    public String context;
    public String imagePath;
    public String name;
    public int cntGood;

    //board's comments
    public class comment{
        public String uri;
        public String name;
        public String wTime;
        public String context;
    }

    //수요 조사를 위한 cnt model
    public class likePeople{
        public String uri;
        public String name;
    }
}
