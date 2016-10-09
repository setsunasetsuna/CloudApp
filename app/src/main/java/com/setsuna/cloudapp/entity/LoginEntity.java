package com.setsuna.cloudapp.entity;

/**
 * Created by setsuna on 2016/9/26.
 */
public class LoginEntity {

    /**
     * state : true
     * id : 10018
     * token : YKmiSdZN2sJ1c9DPWTSNQjTRtmnVTn
     * space : 838860800
     * uspace : 827594974
     * name : wangcai
     * nick_name :
     * head_id : 15197
     * head_url : http://101.200.183.103:9999/thumb/98/86/9886210f33f9647c0e700b91b45048b62b9705e8_100_100?_t=1474853699
     */

    private boolean state;
    private String id;
    private String token;
    private int space;
    private int uspace;
    private String name;
    private String nick_name;
    private int head_id;
    private String head_url;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getUspace() {
        return uspace;
    }

    public void setUspace(int uspace) {
        this.uspace = uspace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getHead_id() {
        return head_id;
    }

    public void setHead_id(int head_id) {
        this.head_id = head_id;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }
}
