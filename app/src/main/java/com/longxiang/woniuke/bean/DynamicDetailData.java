package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class DynamicDetailData {


    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"uid":"88","content":"志伟篮子2","pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/2016072717072464.jpg","nickname":"菜","sex":"女","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg","landtimes":"168","age":"24","followstate":3,"comment":[{"cmid":"24","uid":"88","otheruid":"0","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg","nickname":"菜","content":"天峰傻屌","addtime":"1469776553","time":"刚刚"}]}
     */

    private int retcode;
    private String msg;
    /**
     * uid : 88
     * content : 志伟篮子2
     * pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-27/2016072717072464.jpg
     * nickname : 菜
     * sex : 女
     * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg
     * landtimes : 168
     * age : 24
     * followstate : 3
     * comment : [{"cmid":"24","uid":"88","otheruid":"0","head_pic":"http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg","nickname":"菜","content":"天峰傻屌","addtime":"1469776553","time":"刚刚"}]
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String uid;
        private String content;
        private String pic;
        private String nickname;
        private String sex;
        private String head_pic;
        private String landtimes;
        private String age;
        private int followstate;
        private String rewardtimes;

        public String getRewardtimes() {
            return rewardtimes;
        }

        public void setRewardtimes(String rewardtimes) {
            this.rewardtimes = rewardtimes;
        }

        /**
         * cmid : 24
         * uid : 88
         * otheruid : 0
         * head_pic : http://bubblefish.jbserver.cn/Uploads/Picture/2016-07-18/20160718151847882.jpg
         * nickname : 菜
         * content : 天峰傻屌
         * addtime : 1469776553
         * time : 刚刚
         */

        private List<CommentBean> comment;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getLandtimes() {
            return landtimes;
        }

        public void setLandtimes(String landtimes) {
            this.landtimes = landtimes;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public int getFollowstate() {
            return followstate;
        }

        public void setFollowstate(int followstate) {
            this.followstate = followstate;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class CommentBean {
            private String cmid;
            private String uid;
            private String otheruid;
            private String head_pic;
            private String nickname;
            private String content;
            private String addtime;
            private String time;

            public String getCmid() {
                return cmid;
            }

            public void setCmid(String cmid) {
                this.cmid = cmid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getOtheruid() {
                return otheruid;
            }

            public void setOtheruid(String otheruid) {
                this.otheruid = otheruid;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
