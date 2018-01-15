package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class SqlvData {


    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"128","title":"黄铜","price":["30"],"unit":"小时"},{"id":"129","title":"白银","price":["30","35"],"unit":"小时"},{"id":"130","title":"黄金","price":["30","35","45"],"unit":"小时"},{"id":"131","title":"白金","price":["35","40","45"],"unit":"小时"},{"id":"132","title":"钻石","price":["40","45","50"],"unit":"小时"},{"id":"133","title":"大师","price":["45","50","55"],"unit":"小时"},{"id":"134","title":"王者","price":["50","55","60"],"unit":"小时"}]
     */

    private int retcode;
    private String msg;
    /**
     * id : 128
     * title : 黄铜
     * price : ["30"]
     * unit : 小时
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
        private String unit;
        private List<String> price;

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

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public List<String> getPrice() {
            return price;
        }

        public void setPrice(List<String> price) {
            this.price = price;
        }
    }
}
