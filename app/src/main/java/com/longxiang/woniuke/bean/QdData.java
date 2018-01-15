package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class QdData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"276","read":"0","oid":"151","add_time":"2016-08-09 11:07","robstate":"0","order":{"uid":"88","jnid":"94","realtime":"2016-07-09 10:41","times":"1","state":"0","typename":"歌手","typeimg":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"},"users":{"nickname":"菜","age":"24","sex":"女","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-05/20160805194116866.jpg","mobile":"13029000604"}}]
     */

    private int retcode;
    private String msg;
    /**
     * id : 276
     * read : 0
     * oid : 151
     * add_time : 2016-08-09 11:07
     * robstate : 0
     * order : {"uid":"88","jnid":"94","realtime":"2016-07-09 10:41","times":"1","state":"0","typename":"歌手","typeimg":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png"}
     * users : {"nickname":"菜","age":"24","sex":"女","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-05/20160805194116866.jpg","mobile":"13029000604"}
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
        private String read;
        private String oid;
        private String add_time;
        private String robstate;
        /**
         * uid : 88
         * jnid : 94
         * realtime : 2016-07-09 10:41
         * times : 1
         * state : 0
         * typename : 歌手
         * typeimg : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-20/578f0e9aa58a3.png
         */

        private OrderBean order;
        /**
         * nickname : 菜
         * age : 24
         * sex : 女
         * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-08-05/20160805194116866.jpg
         * mobile : 13029000604
         */

        private UsersBean users;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public UsersBean getUsers() {
            return users;
        }

        public void setUsers(UsersBean users) {
            this.users = users;
        }

        public static class OrderBean {
            private String uid;
            private String jnid;
            private String realtime;
            private String times;
            private String state;
            private String typename;
            private String typeimg;
            private String unit;
            private String uiduid;

            public String getUiduid() {
                return uiduid;
            }

            public void setUiduid(String uiduid) {
                this.uiduid = uiduid;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
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

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }

            public String getTypeimg() {
                return typeimg;
            }

            public void setTypeimg(String typeimg) {
                this.typeimg = typeimg;
            }
        }

        public static class UsersBean {
            private String nickname;
            private String age;
            private String sex;
            private String head_pic;
            private String mobile;

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
        }
    }
}
