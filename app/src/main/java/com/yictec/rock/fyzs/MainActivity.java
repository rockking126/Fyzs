package com.yictec.rock.fyzs;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements EventListener {


    private TextView mTextMessage;
    private TextView T_en;
    private Button button_a;
    private EventManager asr;

    private boolean logTime = true;

    private boolean enableOffline = false; // 测试离线命令词，需要改成true
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

        initPermission();
        asr = EventManagerFactory.create(this, "asr");
        asr.registerListener(this); //  EventListener 中 onEvent方法

//        gettk("client_credentials"
//                ,"amoMVOwvDS513xUAIGhpZp3I"
//                ,"dSLiGRSpSC1IW2v5oVxGvRCoTlXIuPOT");
        translate_txt("我是中国人","cn","en","text");
        mTextMessage = (TextView) findViewById(R.id.message);
        button_a = (Button) findViewById(R.id.button);
        T_en = (TextView) findViewById(R.id.T_en);



        button_a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mTextMessage.setText(NewsModel.get_s()+"88");
                start();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void gettk(String grant_type, String client_id, String client_secret) {
        APi api = Network_func.getInstance().getApi();
        Call<Msg_backs> news = api.baidu(grant_type, client_id, client_secret);
        news.enqueue(new Callback<Msg_backs>() {
            @Override
            public void onResponse(Call<Msg_backs> call, Response<Msg_backs> response) {

                Msg_backs body = response.body();
                Log.i("onResponse:   =", body.access_token);
                T_en.setText(body.access_token.toString()+"8888");
            }

            @Override
            public void onFailure(Call<Msg_backs> call, Throwable t) {

                Log.i("onResponse:   =", t.getMessage());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        if (enableOffline) {
            unloadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
    }

    //   EventListener  回调方法
    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String logTxt = "name: " + name;


        if (params != null && !params.isEmpty()) {
            logTxt += " ;params :" + params;
        }
        if(name =="asr.partial"){
            if (params.contains("\"final_result\"")){
                Gson gson = new Gson();
//                String[] strings = gson.fromJson(params, String[].class);
                Respone_info rsp = gson.fromJson(params,Respone_info.class);
//                List<Respone_info> ps = gson.fromJson(params, new TypeToken<Respone_info<Throwable>>(){}.getType());
                mTextMessage.setText(rsp.best_result);
                translate_txt("我是中国人","cn","en","text");
//                Log.i("ss",params);
            }

        }
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            if (params.contains("\"nlu_result\"")) {
                if (length > 0 && data.length > 0) {
                    logTxt += ", 语义解析结果：" + new String(data, offset, length);
                }
            }
        } else if (data != null) {
            logTxt += " ;data length=" + data.length;
        }
//        printLog(logTxt);
    }

    private void printLog(String text) {
        if (logTime) {
            text += "  ;time=" + System.currentTimeMillis();
        }
        text += "\n";
        Log.i(getClass().getName(), text);
        mTextMessage.append(text + "\n");
    }


    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    private void start() {
        mTextMessage.setText("");
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event

        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        String json = null; // 可以替换成自己的json
        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);
        printLog("输入参数：" + json);
    }

    private void stop() {
        printLog("停止识别：ASR_STOP");
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }

    private void loadOfflineEngine() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
    }

    private void unloadOfflineEngine() {
        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0); //
    }



    public  void   translate_txt(String contents,String src,String dest,String ackey){
        APi api = Network_func.getInstance().getTrans();
        Call<Msg_from_google> news = api.google(contents,"cn","en","text","base","AIzaSyA7p8XTMiubDHSn4Q-2ULCsnuWB-0jgbwc");
        news.enqueue(new Callback<Msg_from_google>() {
            @Override
            public void onResponse(Call<Msg_from_google> call, Response<Msg_from_google> response) {

                Msg_from_google body = response.body();
                Log.i("onResponse:   =", body.data.toString());

                T_en.setText(body.data.toString()+"8888");
            }

            @Override
            public void onFailure(Call<Msg_from_google> call, Throwable t) {

                Log.i("onResponse:   =",t.getMessage());
            }
        });
    }



}

