package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by mac on 16/9/14.
 */
public class OfficalData {

    /**
     * retcode : 2000
     * msg : 成功！
     * data : [{"id":"18","title":"测试3","url":"http://www.baidu.com","icon":"371","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-11/57ac3edfb582b.png"},{"id":"17","title":"测试2","url":"http://www.baidu.com","icon":"372","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-11/57ac3eec21141.png"},{"id":"15","title":"测试1","url":"http://www.baidu.com","icon":"373","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-11/57ac3ef78efae.png"}]
     */

    private int retcode;
    private String msg;
    /**
     * id : 18
     * title : 测试3
     * url : http://www.baidu.com
     * icon : 371
     * pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-11/57ac3edfb582b.png
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
        private String url;
        private String icon;
        private String pic;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
