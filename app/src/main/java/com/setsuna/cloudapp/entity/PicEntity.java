package com.setsuna.cloudapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by setsuna on 2016/9/30.
 */
public class PicEntity {
    private String time;
    private List<FileEntity.DataBean> dataBeens;
    private boolean isTime;
    private boolean isPic;
    public boolean isTime() {
        return isTime;
    }

    public boolean isPic() {
        return isPic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        isTime=true;
        this.time = time;
    }

    public List<FileEntity.DataBean> getDataBeen() {
        return dataBeens;
    }

    public void setDataBeen(List<FileEntity.DataBean> dataBeens) {
        isPic=true;
        this.dataBeens = dataBeens;
    }

    public void addDataBeen(FileEntity.DataBean dataBean){
        if (dataBeens==null){
            dataBeens=new ArrayList<>();
        }
        isPic=true;
        dataBeens.add(dataBean);
    }
}
