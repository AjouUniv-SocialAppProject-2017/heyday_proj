package com.example.choikim.hayday_proj;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by jamti on 2017-11-19.
 */

public class ClassActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class,container,false);

    }

    private View.OnClickListener health = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(),"헬스/뷰티 버튼이 눌렸어요",Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener foregin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(),"외국어 버튼이 눌렸어요",Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener computer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(),"컴퓨터 버튼이 눌렸어요",Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener art = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(),"음악/미술 버튼이 눌렸어요",Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener socity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(),"사회이슈 버튼이 눌렸어요",Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener sport = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(),"스포츠 버튼이 눌렸어요",Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener hobby = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(),"이색취미 버튼이 눌렸어요",Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener all = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(),"전체 버튼이 눌렸어요",Toast.LENGTH_LONG).show();
        }
    };


    public View.OnClickListener getHealth() {
        return health;
    }

    public void setHealth(View.OnClickListener health) {
        this.health = health;
    }
}
