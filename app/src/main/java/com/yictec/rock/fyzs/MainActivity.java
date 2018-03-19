package com.yictec.rock.fyzs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private TextView mTextMessage;
    private Button button_a;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gettk("client_credentials"
                ,"amoMVOwvDS513xUAIGhpZp3I"
                ,"dSLiGRSpSC1IW2v5oVxGvRCoTlXIuPOT");

        mTextMessage = (TextView) findViewById(R.id.message);
        button_a = (Button) findViewById(R.id.button);



        button_a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mTextMessage.setText(NewsModel.get_s()+"88");
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void gettk(String grant_type, String client_id, String client_secret) {
        APi api = Network.getInstance().getApi();
        Call<News> news = api.baidu(grant_type, client_id, client_secret);
        news.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                News body = response.body();
                Log.i("onResponse:   =", body.access_token);
                mTextMessage.setText(body.access_token.toString()+"8888");
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                Log.i("onResponse:   =", t.getMessage());
            }
        });
    }
}


