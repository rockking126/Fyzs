package com.yictec.rock.fyzs;

import java.util.List;

public class Respone_info<T> {
    //省略其它
    public List results_recognition;
    public C origin_result;
    public String error;
    public String best_result;
    public String result_type;

    public static class B {
        String word;
    }

    public static class C {
        String best_result;
    }
}
