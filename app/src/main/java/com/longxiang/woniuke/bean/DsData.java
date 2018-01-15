package com.longxiang.woniuke.bean;

/**
 * Created by Administrator on 2016/7/30.
 */
public class DsData {
    String bgid;
    String xmid;
    String state;

    public DsData(String bgid,String xmid, String state) {
        this.bgid=bgid;
        this.xmid = xmid;
        this.state = state;
    }

    public String getBgid() {
        return bgid;
    }

    public void setBgid(String bgid) {
        this.bgid = bgid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
