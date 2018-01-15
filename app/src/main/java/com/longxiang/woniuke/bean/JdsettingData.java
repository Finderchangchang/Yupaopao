package com.longxiang.woniuke.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class JdsettingData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"bgid":"24","jnid":"127","djid":"134","skill":"LOL","switch":"1","price":"50.00","unit":"小时","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png"},{"bgid":"25","jnid":"157","djid":"159","skill":"王者荣耀","switch":"1","price":"5.00","unit":"小时","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/57bbaa70a9fe6.png"}]
     */

    private int retcode;
    private String msg;
    /**
     * bgid : 24
     * jnid : 127
     * djid : 134
     * skill : LOL
     * switch : 1
     * price : 50.00
     * unit : 小时
     * pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png
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
        private String bgid;
        private String jnid;
        private String djid;
        private String skill;
        @SerializedName("switch")
        private String switchX;
        private String price;
        private String unit;
        private String pic;

        public String getBgid() {
            return bgid;
        }

        public void setBgid(String bgid) {
            this.bgid = bgid;
        }

        public String getJnid() {
            return jnid;
        }

        public void setJnid(String jnid) {
            this.jnid = jnid;
        }

        public String getDjid() {
            return djid;
        }

        public void setDjid(String djid) {
            this.djid = djid;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getSwitchX() {
            return switchX;
        }

        public void setSwitchX(String switchX) {
            this.switchX = switchX;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
