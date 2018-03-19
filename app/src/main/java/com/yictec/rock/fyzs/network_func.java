package com.yictec.rock.fyzs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rock on 2018-03-18.
 */
public class Network_func {

    private static volatile Network_func mInstance;
    private APi mApi;

    private Network_func(){

    }

    public static Network_func getInstance(){
        if(mInstance==null){
            synchronized (Network_func.class){
                if(mInstance==null){
                    mInstance=new Network_func();
                }
            }
        }
        return mInstance;
    }

    public APi getApi(){
        if(mApi==null){
            synchronized (Network_func.class){
                if(mApi==null){
                    Retrofit retrofit = new Retrofit.Builder()
                            //使用自定义的mGsonConverterFactory
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://aip.baid343ubce.com/")
                            .build();
                    mApi = retrofit.create(APi.class);
                }
            }

        }

        return mApi;

    }



    public APi getTrans(){
        if(mApi==null){
            synchronized (Network_func.class){
                if(mApi==null){
                    Retrofit retrofit = new Retrofit.Builder()
                            //使用自定义的mGsonConverterFactory
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://translation.googleapis.com/")
                            .build();
                    mApi = retrofit.create(APi.class);
                }
            }

        }

        return mApi;

    }
}