package com.longxiang.woniuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class GodPriceData {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"priceandrebate":{"price":"5.00","basicprice":"5","rebate":"1"},"pricerange":["5","10"]}
     */

    private int retcode;
    private String msg;
    /**
     * priceandrebate : {"price":"5.00","basicprice":"5","rebate":"1"}
     * pricerange : ["5","10"]
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
        /**
         * price : 5.00
         * basicprice : 5
         * rebate : 1
         */

        private PriceandrebateBean priceandrebate;
        private List<String> pricerange;

        public PriceandrebateBean getPriceandrebate() {
            return priceandrebate;
        }

        public void setPriceandrebate(PriceandrebateBean priceandrebate) {
            this.priceandrebate = priceandrebate;
        }

        public List<String> getPricerange() {
            return pricerange;
        }

        public void setPricerange(List<String> pricerange) {
            this.pricerange = pricerange;
        }

        public static class PriceandrebateBean {
            private String price;
            private String basicprice;
            private String rebate;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getBasicprice() {
                return basicprice;
            }

            public void setBasicprice(String basicprice) {
                this.basicprice = basicprice;
            }

            public String getRebate() {
                return rebate;
            }

            public void setRebate(String rebate) {
                this.rebate = rebate;
            }
        }
    }
}
