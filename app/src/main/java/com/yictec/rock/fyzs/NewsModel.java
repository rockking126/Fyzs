package com.yictec.rock.fyzs;

/**
 * Created by rock on 2018-03-18.
 */

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsModel {

    public static String a = "";
    public static void baidu(String grant_type,String client_id, String client_secret ){
        APi api = Network.getInstance().getApi();
        Call<News> news = api.baidu(grant_type,client_id, client_secret);
        news.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                News body = response.body();
                Log.i("onResponse:   =",body.access_token);
                a = body.access_token.toString();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                Log.i("onResponse:   =",t.getMessage());
            }
        });
    }

    public static String get_s(){
        return a;
    }

//    public static void getNews(String number, String page) {
//        APi api = Network.getInstance().getApi();
//        Call<News> news = api.getNews(number, page);
//        news.enqueue(new Callback<News>() {
//            @Override
//            public void onResponse(Call<News> call, Response<News> response) {
//                News body = response.body();
////                Logger.i("onResponse:   ="+body.toString());
////                Log.i("onResponse:=",body.toString());
////                String a = "onResponse:   ="+body.toString();
//            }
//
//            @Override
//            public void onFailure(Call<News> call, Throwable t) {
////                Logger.i("onResponse:   ="+t.getMessage());
//                Log.i("onResponse:   =",t.getMessage());
//
//            }
//        });
//
//    }

//
//    public static void postNews(String number, String page) {
//        APi api = Network.getInstance().getApi();
//        Call<News> news = api.postNews(number, page);
//        news.enqueue(new Callback<News>() {
//            @Override
//            public void onResponse(Call<News> call, Response<News> response) {
//                News body = response.body();
////                Logger.i("onResponse:   ="+body.toString());
//            }
//
//            @Override
//            public void onFailure(Call<News> call, Throwable t) {
////                Logger.i("onResponse:   ="+t.getMessage());
//
//            }
//        });
//
//    }

//
//    public static void getTiYu(String type,String number, String page ){
//        APi api = Network.getInstance().getApi();
//        Call<News> news = api.tiYu(type,number, page);
//        news.enqueue(new Callback<News>() {
//            @Override
//            public void onResponse(Call<News> call, Response<News> response) {
//
//                News body = response.body();
////                Logger.i("onResponse:   ="+body.toString());
//            }
//
//            @Override
//            public void onFailure(Call<News> call, Throwable t) {
//
//                Log.i("onResponse:   =", t.getMessage());
//            }
//        });
//    }




}