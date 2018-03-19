package com.yictec.rock.fyzs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rock on 2018-03-18.
 */
public class Network {

    private static volatile Network mInstance;
    private APi mApi;

    private Network(){

    }

    public static Network getInstance(){
        if(mInstance==null){
            synchronized (Network.class){
                if(mInstance==null){
                    mInstance=new Network();
                }
            }
        }
        return mInstance;
    }

    public APi getApi(){
        if(mApi==null){
            synchronized (Network.class){
                if(mApi==null){
                    Retrofit retrofit = new Retrofit.Builder()
                            //使用自定义的mGsonConverterFactory
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://aip.baidubce.com/")
                            .build();
                    mApi = retrofit.create(APi.class);
                }
            }

        }

        return mApi;

    }
}