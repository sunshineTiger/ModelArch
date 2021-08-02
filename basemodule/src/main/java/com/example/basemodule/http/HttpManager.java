package com.example.basemodule.http;

/**
 * @ClassName HttpManager
 * @Author zhangHong
 * @Date 2021/8/2 10:22
 */
public class HttpManager {
    public static volatile HttpManager instance;

    public HttpManager getInstance() {
        if (instance != null) {
            instance = new HttpManager();
        }
        return instance;
    }
}
