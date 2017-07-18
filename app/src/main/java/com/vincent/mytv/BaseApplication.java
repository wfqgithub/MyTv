package com.vincent.mytv;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "b866d8b088f64f125e37ee5b2505db73");
    }

}
