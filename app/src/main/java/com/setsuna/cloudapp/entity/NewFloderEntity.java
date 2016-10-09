package com.setsuna.cloudapp.entity;

/**
 * Created by setsuna on 2016/9/27.
 */
public class NewFloderEntity {

    /**
     * aid : 1
     * cid : 10164
     * cname : test
     * pid : 0
     * state : true
     * error :
     * errno :
     */

    private int aid;
    private String cid;
    private String cname;
    private String pid;
    private boolean state;
    private String error;
    private String errno;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }
}
