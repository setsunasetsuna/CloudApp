package com.setsuna.cloudapp.entity;

import java.util.List;

/**
 * Created by setsuna on 2016/9/27.
 */
public class FileEntity {

    /**
     * count : 20
     * order : user_ptime
     * uid : 305806304
     * state : true
     * error :
     * errNo : 0
     * time : 0.189
     * offset : 0
     * limit : 28
     * aid : 1
     * cid : 0
     * is_asc : 0
     * star : 0
     * is_share : 0
     * type : 0
     * data : [{"cid":"10071","aid":1,"pid":"0","n":"我的视频","cc":"","m":"0","pc":"fb8885hnf","t":1382673979},{"fid":10095,"aid":1,"pid":0,"n":"crashreporter.exe","s":"116632","ico":"apk","pc":"11085d03","m":"0","t":"1382923656","u":"http://192.168.1.235/thumb/4d/fb/4dfb7f9a94f339b4382ac93b28c7557a9b147b31_100_100?_t=1382933175","sha1":"4dfb7f9a94f339b4382ac93b28c7557a9b147b31"}]
     * path : [{"name":"网盘文件","aid":1,"cid":0,"pid":0}]
     */

    private int count;
    private String order;
    private String uid;
    private boolean state;
    private String error;
    private int errNo;
    private String time;
    private int offset;
    private int limit;
    private int aid;
    private int cid;
    private int is_asc;
    private int star;
    private int is_share;
    private int type;
    /**
     * cid : 10071
     * aid : 1
     * pid : 0
     * n : 我的视频
     * cc :
     * m : 0
     * pc : fb8885hnf
     * t : 1382673979
     */

    private List<DataBean> data;
    /**
     * name : 网盘文件
     * aid : 1
     * cid : 0
     * pid : 0
     */

    private List<PathBean> path;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getErrNo() {
        return errNo;
    }

    public void setErrNo(int errNo) {
        this.errNo = errNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getIs_asc() {
        return is_asc;
    }

    public void setIs_asc(int is_asc) {
        this.is_asc = is_asc;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getIs_share() {
        return is_share;
    }

    public void setIs_share(int is_share) {
        this.is_share = is_share;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<PathBean> getPath() {
        return path;
    }

    public void setPath(List<PathBean> path) {
        this.path = path;
    }

    public static class DataBean {
        private boolean isChecked;
        private String cid;
        private String fid;
        private long s;
        private String ico;
        private String u;
        private String sha1;
        private int aid;
        private String pid;
        private String n;
        private String cc;
        private String m;
        private String pc;
        private long t;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public long getS() {
            return s;
        }

        public void setS(long s) {
            this.s = s;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public String getSha1() {
            return sha1;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public String getCid() {
            return cid;

        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getCc() {
            return cc;
        }

        public void setCc(String cc) {
            this.cc = cc;
        }

        public String getM() {
            return m;
        }

        public void setM(String m) {
            this.m = m;
        }

        public String getPc() {
            return pc;
        }

        public void setPc(String pc) {
            this.pc = pc;
        }

        public long getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }
    }

    public static class PathBean {
        private String name;
        private int aid;
        private int cid;
        private int pid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}
