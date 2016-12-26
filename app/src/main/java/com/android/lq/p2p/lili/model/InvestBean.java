package com.android.lq.p2p.lili.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/25.
 */

public class InvestBean {

    /**
     * invests : [{"amount":"2000.0","id":"9336","apr":"13.0","count":"2","userName":"1***"},{"amount":"4374.71","id":"7895","apr":"15.0","count":"41","userName":"1***"},{"amount":"600.0","id":"9359","apr":"14.5","count":"3","userName":"1***"}]
     * homeAds : [{"entityId":200,"file_format":"","file_size":"","id":200,"image_filename":"https://dn-liyedai.qbox.me/FoAOtLzfmCQG9aOx5OZrvKKGLPLk","is_link_enabled":true,"is_use":false,"location":"","no":"","persistent":false,"resolution":"","target":2,"time":null,"url":"https://www.liyedai.cn/front/activity/index?src=2016_christmas&client=m"},{"entityId":201,"file_format":"","file_size":"","id":201,"image_filename":"https://dn-liyedai.qbox.me/FrpNffjj_EfTPbd-ErOHNy5X5uBX","is_link_enabled":true,"is_use":false,"location":"","no":"","persistent":false,"resolution":"","target":2,"time":null,"url":"http://www.liyedai.cn/front/activity/index?src=openSchool&client=m"},{"entityId":202,"file_format":"","file_size":"","id":202,"image_filename":"https://dn-liyedai.qbox.me/FkNqUQ5xi7K6M-LklEXOpr1C_0VY","is_link_enabled":true,"is_use":false,"location":"","no":"","persistent":false,"resolution":"","target":2,"time":null,"url":"http://www.liyedai.cn/front/activity/index?src=septemberPlan&client=m"},{"entityId":204,"file_format":"","file_size":"","id":204,"image_filename":"https://dn-liyedai.qbox.me/FpuQbdQQUZOA2hZjQMu-wFW6RBa7","is_link_enabled":true,"is_use":false,"location":"","no":"","persistent":false,"resolution":"","target":2,"time":null,"url":"http://mp.weixin.qq.com/s?__biz=MjM5NzY2NTkwNQ==&mid=404491647&idx=1&sn=e7317b8c2e7bbbf849ffed38856122fe&scene=0#wechat_redirect"},{"entityId":205,"file_format":"","file_size":"","id":205,"image_filename":"https://dn-liyedai.qbox.me/Fl4OgDhdo6qwSnYr6jZeLvsXt2z9","is_link_enabled":true,"is_use":false,"location":"","no":"","persistent":false,"resolution":"","target":2,"time":null,"url":"http://www.liyedai.cn/front/activity/index?src=operation_data_report&client=m"}]
     * error : -1
     * investingBids : [{"amount":100000,"id":860,"title":"新手标-16122401期","apr":13,"isNovice":1,"loanSchedule":29.55,"type":101,"period":1,"periodUnit":0}]
     * msg : 查询成功
     * url : http://119.29.103.214/front/activity/index?src=may&client=m#stattwoSign
     */

    private int error;
    private String msg;
    private String url;
    private List<InvestsBean> invests;
    private List<HomeAdsBean> homeAds;
    private List<InvestingBidsBean> investingBids;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<InvestsBean> getInvests() {
        return invests;
    }

    public void setInvests(List<InvestsBean> invests) {
        this.invests = invests;
    }

    public List<HomeAdsBean> getHomeAds() {
        return homeAds;
    }

    public void setHomeAds(List<HomeAdsBean> homeAds) {
        this.homeAds = homeAds;
    }

    public List<InvestingBidsBean> getInvestingBids() {
        return investingBids;
    }

    public void setInvestingBids(List<InvestingBidsBean> investingBids) {
        this.investingBids = investingBids;
    }

    public static class InvestsBean {
        /**
         * amount : 2000.0
         * id : 9336
         * apr : 13.0
         * count : 2
         * userName : 1***
         */

        private String amount;
        private String id;
        private String apr;
        private String count;
        private String userName;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getApr() {
            return apr;
        }

        public void setApr(String apr) {
            this.apr = apr;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class HomeAdsBean {
        /**
         * entityId : 200
         * file_format :
         * file_size :
         * id : 200
         * image_filename : https://dn-liyedai.qbox.me/FoAOtLzfmCQG9aOx5OZrvKKGLPLk
         * is_link_enabled : true
         * is_use : false
         * location :
         * no :
         * persistent : false
         * resolution :
         * target : 2
         * time : null
         * url : https://www.liyedai.cn/front/activity/index?src=2016_christmas&client=m
         */

        private int entityId;
        private String file_format;
        private String file_size;
        private int id;
        private String image_filename;
        private boolean is_link_enabled;
        private boolean is_use;
        private String location;
        private String no;
        private boolean persistent;
        private String resolution;
        private int target;
        private Object time;
        private String url;

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public String getFile_format() {
            return file_format;
        }

        public void setFile_format(String file_format) {
            this.file_format = file_format;
        }

        public String getFile_size() {
            return file_size;
        }

        public void setFile_size(String file_size) {
            this.file_size = file_size;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage_filename() {
            return image_filename;
        }

        public void setImage_filename(String image_filename) {
            this.image_filename = image_filename;
        }

        public boolean isIs_link_enabled() {
            return is_link_enabled;
        }

        public void setIs_link_enabled(boolean is_link_enabled) {
            this.is_link_enabled = is_link_enabled;
        }

        public boolean isIs_use() {
            return is_use;
        }

        public void setIs_use(boolean is_use) {
            this.is_use = is_use;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public boolean isPersistent() {
            return persistent;
        }

        public void setPersistent(boolean persistent) {
            this.persistent = persistent;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class InvestingBidsBean {
        /**
         * amount : 100000
         * id : 860
         * title : 新手标-16122401期
         * apr : 13
         * isNovice : 1
         * loanSchedule : 29.55
         * type : 101
         * period : 1
         * periodUnit : 0
         */

        private int amount;
        private int id;
        private String title;
        private int apr;
        private int isNovice;
        private double loanSchedule;
        private int type;
        private int period;
        private int periodUnit;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getApr() {
            return apr;
        }

        public void setApr(int apr) {
            this.apr = apr;
        }

        public int getIsNovice() {
            return isNovice;
        }

        public void setIsNovice(int isNovice) {
            this.isNovice = isNovice;
        }

        public double getLoanSchedule() {
            return loanSchedule;
        }

        public void setLoanSchedule(double loanSchedule) {
            this.loanSchedule = loanSchedule;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public int getPeriodUnit() {
            return periodUnit;
        }

        public void setPeriodUnit(int periodUnit) {
            this.periodUnit = periodUnit;
        }
    }
}
