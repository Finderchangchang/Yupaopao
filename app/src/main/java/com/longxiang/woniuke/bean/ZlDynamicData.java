package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ZlDynamicData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"dmid":"15","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/20160727112617267.jpg"},{"dmid":"14","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/20160727112412551.jpg"},{"dmid":"13","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/20160727111913237.jpg"},{"dmid":"12","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/20160727110246423.jpg"}]
     */

    private int retcode;
    private String msg;
    /**
     * dmid : 15
     * pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/20160727112617267.jpg
     */

    private List<DataBean> data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String dmid;
        private String pic;

        public String getDmid() {
            return dmid;
        }

        public void setDmid(String dmid) {
            this.dmid = dmid;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
