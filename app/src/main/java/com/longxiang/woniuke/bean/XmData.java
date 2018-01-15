package com.longxiang.woniuke.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class XmData {


    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"112","title":"声优聊天","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57baa87fc70f4.png","unit":"小时","price":["25","30","35"]},{"id":"116","title":"歌手","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57baac423c037.png","unit":"首","price":["12","15","18"]},{"id":"120","title":"视频聊天","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57baae24caa27.png","unit":"小时","price":["80","120","150"]},{"id":"124","title":"QQ游戏","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab54fab8ef.png","unit":"小时","price":["30","50"]},{"id":"127","title":"LOL","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png","unit":"小时","price":["30","35","40","45","50","55","60"]},{"id":"135","title":"DOTA","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-21/57902fa1bcb94.png","unit":"小时","price":["30","35","50"]},{"id":"139","title":"DOTA2","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab9b23a1d0.png","unit":"小时","price":["35","40","60"]},{"id":"143","title":"守望先锋","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/57bba6af27783.png","unit":"小时","price":["30","40","60"]},{"id":"148","title":"手绘","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/57bba91ec8c90.png","unit":"次","price":["10","40"]},{"id":"151","title":"占卜","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/57bba90410cad.png","unit":"次","price":["10","50"]},{"id":"154","title":"叫醒","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/57bba9f597abb.png","unit":"次","price":["5","10"]},{"id":"157","title":"王者荣耀","icon":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/57bbaa70a9fe6.png","unit":"小时","price":["5","10"]}]
     */

    private int retcode;
    private String msg;
    /**
     * id : 112
     * title : 声优聊天
     * icon : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57baa87fc70f4.png
     * unit : 小时
     * price : ["25","30","35"]
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
        private String id;
        private String title;
        private String icon;
        private String unit;
        private ArrayList<String> price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public ArrayList<String> getPrice() {
            return price;
        }

        public void setPrice(ArrayList<String> price) {
            this.price = price;
        }
    }
}
