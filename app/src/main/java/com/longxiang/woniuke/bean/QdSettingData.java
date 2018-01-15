package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class QdSettingData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"bgid":"24","jnid":"127","skill":"LOL","robswitch":"2","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png"},{"bgid":"25","jnid":"157","skill":"王者荣耀","robswitch":"2","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/57bbaa70a9fe6.png"}]
     */

    private int retcode;
    private String msg;
    /**
     * bgid : 24
     * jnid : 127
     * skill : LOL
     * robswitch : 2
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
        private String skill;
        private String robswitch;
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

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getRobswitch() {
            return robswitch;
        }

        public void setRobswitch(String robswitch) {
            this.robswitch = robswitch;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
