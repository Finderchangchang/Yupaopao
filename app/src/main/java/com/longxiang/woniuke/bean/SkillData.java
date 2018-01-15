package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 */
public class SkillData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"bgid":"2","price":null,"times":"0","skill":"DOTA","level":"娱乐局","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/20160721162636784.jpg"}]
     */

    private int retcode;
    private String msg;
    /**
     * bgid : 2
     * price : null
     * times : 0
     * skill : DOTA
     * level : 娱乐局
     * pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/20160721162636784.jpg
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
        private double price;
        private String times;
        private String skill;
        private String level;
        private String pic;
        private String unit;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getBgid() {
            return bgid;
        }

        public void setBgid(String bgid) {
            this.bgid = bgid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
