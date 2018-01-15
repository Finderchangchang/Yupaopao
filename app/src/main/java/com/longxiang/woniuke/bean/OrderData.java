package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class OrderData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"467","content":"您有一个订单已完成，服务费用已到您的账户！","read":"1","oid":"303","add_time":"2016-08-26 17:56","robstate":"0","order":{"uid":"98","bgid":"24","jnid":"127","realtime":"2016-08-26 06:00","times":"1","state":"4","amount":"60.00","typename":"LOL","unit":"小时","typeimg":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png"},"god":{"nickname":"迪哥","age":"26","sex":"男","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg","mobile":"18730403516","level":2},"bguid":"97"}]
     */

    private int retcode;
    private String msg;
    /**
     * id : 467
     * content : 您有一个订单已完成，服务费用已到您的账户！
     * read : 1
     * oid : 303
     * add_time : 2016-08-26 17:56
     * robstate : 0
     * order : {"uid":"98","bgid":"24","jnid":"127","realtime":"2016-08-26 06:00","times":"1","state":"4","amount":"60.00","typename":"LOL","unit":"小时","typeimg":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png"}
     * god : {"nickname":"迪哥","age":"26","sex":"男","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg","mobile":"18730403516","level":2}
     * bguid : 97
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
        private String content;
        private String read;
        private String oid;
        private String add_time;
        private String robstate;
        /**
         * uid : 98
         * bgid : 24
         * jnid : 127
         * realtime : 2016-08-26 06:00
         * times : 1
         * state : 4
         * amount : 60.00
         * typename : LOL
         * unit : 小时
         * typeimg : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-22/57bab60684d70.png
         */

        private OrderBean order;
        /**
         * nickname : 迪哥
         * age : 26
         * sex : 男
         * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-23/20160823144448181.jpg
         * mobile : 18730403516
         * level : 2
         */

        private GodBean god;
        private String bguid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getRobstate() {
            return robstate;
        }

        public void setRobstate(String robstate) {
            this.robstate = robstate;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public GodBean getGod() {
            return god;
        }

        public void setGod(GodBean god) {
            this.god = god;
        }

        public String getBguid() {
            return bguid;
        }

        public void setBguid(String bguid) {
            this.bguid = bguid;
        }

        public static class OrderBean {
            private String uid;
            private String bgid;
            private String jnid;
            private String realtime;
            private String times;
            private String state;
            private String amount;
            private String typename;
            private String unit;
            private String typeimg;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

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

            public String getRealtime() {
                return realtime;
            }

            public void setRealtime(String realtime) {
                this.realtime = realtime;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getTypeimg() {
                return typeimg;
            }

            public void setTypeimg(String typeimg) {
                this.typeimg = typeimg;
            }
        }

        public static class GodBean {
            private String nickname;
            private String age;
            private String sex;
            private String head_pic;
            private String mobile;
            private float level;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public float getLevel() {
                return level;
            }

            public void setLevel(float level) {
                this.level = level;
            }
        }
    }
}
