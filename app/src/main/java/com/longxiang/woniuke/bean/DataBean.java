package com.longxiang.woniuke.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class DataBean implements Serializable{

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"bgid":"5","uid":"82","jnid":"97","djid":"109","skill":"DOTA","price":"100.00","typepic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png"},{"bgid":"8","uid":"82","jnid":"95","djid":"103","skill":"占卜","price":"100.00","typepic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0eda129c9.png"},{"bgid":"9","uid":"82","jnid":"98","djid":"107","skill":"DOTA2","price":"100.00","typepic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0f06f3844.png"}]
     */

    private int retcode;
    private String msg;
    /**
     * bgid : 5
     * uid : 82
     * jnid : 97
     * djid : 109
     * skill : DOTA
     * price : 100.00
     * typepic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png
     */

    private List<DataEntity> data;

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

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        private String bgid;
        private String uid;
        private String jnid;
        private String djid;
        private String skill;
        private String price;
        private String typepic;
        private String level;
        private String unit;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getBgid() {
            return bgid;
        }

        public void setBgid(String bgid) {
            this.bgid = bgid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTypepic() {
            return typepic;
        }

        public void setTypepic(String typepic) {
            this.typepic = typepic;
        }
    }
}
