package com.setsuna.cloudapp.utils;

import com.setsuna.cloudapp.entity.FileEntity;

import java.util.List;

/**
 * Created by setsuna on 2016/9/28.
 */
public class GetSavaPath {
    public static String getPath(List<FileEntity.PathBean >pathBeens){
        if (pathBeens.size()==0 || pathBeens==null){
            return "";
        }else {
            StringBuilder stringBuilder=new StringBuilder();
            for (FileEntity.PathBean pathBean:pathBeens){
                stringBuilder.append(pathBean.getName());
                stringBuilder.append("/");
            }
            return stringBuilder.toString();
        }
    }
}
