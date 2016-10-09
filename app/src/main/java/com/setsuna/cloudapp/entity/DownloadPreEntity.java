package com.setsuna.cloudapp.entity;

/**
 * Created by Administrator on 2016/9/24.
 */

public class DownloadPreEntity {
    
    /**
     * url : http://fyimail.vicp.net:1443 /M00/00/00/wKg4ZVJ56MaAHQluAGN8Gyxg4aA9937687?fn=林志炫 - 蒙娜丽莎的眼泪.mp3&k=21f3d92050626fc5a9069bcf06b7c781&t=1386921085
     * state : true
     * error : 
     * errno : 
     */
    
    private String url;
    private boolean state;
    private String error;
    private String errno;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
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
