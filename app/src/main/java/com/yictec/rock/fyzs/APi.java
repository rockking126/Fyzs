package com.yictec.rock.fyzs;

/**
 * Created by rock on 2018-03-18.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @ explain:
 * @ author：xujun on 2016-8-25 15:06
 * @ email：gdutxiaoxu@163.com
 */
public interface APi {

    @Headers({"Ocp-Apim-Subscription-Key:ef5639ae9d004594a123e336d0a48968"})
    @POST("sts/v1.0/issueToken")
    Call<Msg_backs> getNews(@Query("num") String num, @Query("page")String page);



//    @Headers({"Ocp-Apim-Subscription-Key:ef5639ae9d004594a123e336d0a48968" ,"Content-Type:application/json"})
//    @GET("world/world")
//    Call<Msg_backs> getNews(@Query("num") String num, @Query("page")String page);
//    @FormUrlEncoded
//    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
//    @POST("world/world")
//    Call<Msg_backs> postNews(@Field("num") String num, @Field("page")String page);

    @Headers({"Content-Type:application/json; charset=UTF-8"})
    @GET("oauth/2.0/token")
    Call<Msg_backs> baidu(@Query("grant_type") String grant_type, @Query("client_id") String client_id, @Query("client_secret") String client_secret);



    @Headers({"Content-Type: application/json"})
    @POST("language/translate/v2")
    Call<Msg_from_google> google(@Query("q") String q, @Query("source") String source, @Query("target") String target, @Query("format") String format, @Query("model") String model, @Query("key") String key);

//
//    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
//    @GET("{type}/{type}")
//    Call<Msg_backs> tiYu(@Path("type") String type, @Query("num") String num, @Query("page")String page);
//
//    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
//    @GET("{type1}/{type2}")
//    Call<Msg_backs> tiYu(@Path("type1") String type1, @Path("type2") String type2, @Query("num") String num, @Query("page")String page);
//
//    @FormUrlEncoded
//    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
//    @POST("keji/keji")
//    Call<Msg_backs> keji(@Query("num") String num, @Query("page")String page);

}