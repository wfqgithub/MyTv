package com.vincent.mytv.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class TvInfo extends BmobObject {

    private String name;
    private String url;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TvInfo{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
