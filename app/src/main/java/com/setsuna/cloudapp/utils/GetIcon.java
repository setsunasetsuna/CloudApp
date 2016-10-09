package com.setsuna.cloudapp.utils;

import com.example.mylib.util.StringEmptyUtil;
import com.setsuna.cloudapp.R;

/**
 * Created by setsuna on 2016/9/27.
 */
public class GetIcon {
    public static  int getResID(String ico){
        if (StringEmptyUtil.isEmpty(ico)){
            return R.drawable.ic_type_unknow;
        }
        if ("mp3".equalsIgnoreCase(ico)){
            return R.drawable.ft_mp3;
        }
        if ("mp4".equalsIgnoreCase(ico)){
            return R.drawable.ft_mp4;
        }
        if ("m4a".equalsIgnoreCase(ico)){
            return R.drawable.ft_m4a;
        }
        if ("txt".equalsIgnoreCase(ico)){
            return R.drawable.ft_txt;
        }
        if ("jpg".equalsIgnoreCase(ico)){
            return R.drawable.ft_jpg;
        }
        if ("java".equalsIgnoreCase(ico)){
            return R.drawable.ft_java;
        }
        if ("gif".equalsIgnoreCase(ico)){
            return R.drawable.ft_gif;
        }
        if ("rar".equalsIgnoreCase(ico)){
            return R.drawable.ft_rar;
        }
        if ("zip".equalsIgnoreCase(ico)){
            return R.drawable.ft_zip;
        }
        if ("doc".equalsIgnoreCase(ico)){
            return R.drawable.ft_doc;
        }
        if ("docx".equalsIgnoreCase(ico)){
            return R.drawable.ft_docx;
        }
        if ("xml".equalsIgnoreCase(ico)){
            return R.drawable.ft_xml;
        }
        if ("html".equalsIgnoreCase(ico)){
            return R.drawable.ft_html;
        }
        if ("htm".equalsIgnoreCase(ico)){
            return R.drawable.ft_html;
        }
        if ("apk".equalsIgnoreCase(ico)){
            return R.drawable.ft_apk;
        }
        if ("png".equalsIgnoreCase(ico)){
            return R.drawable.ft_png;
        }

        return R.drawable.ic_type_unknow;
    }
}
