package com.setsuna.cloudapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.mylib.util.StringEmptyUtil;
import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.entity.FileEntity;
import com.setsuna.cloudapp.utils.GetIcon;
import com.squareup.picasso.Picasso;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemGvAlbumFragAdapter extends BaseAdapter {

    private List<FileEntity.DataBean> mEntities = new ArrayList<FileEntity.DataBean>();

    private Context context;
    private LayoutInflater layoutInflater;
    private int mHeight,mWidth;

    public ItemGvAlbumFragAdapter(Context context, List<FileEntity.DataBean> entities) {
        this.context = context;
        mEntities = entities;
        this.layoutInflater = LayoutInflater.from(context);
        mHeight=jsHeight();
        mWidth=jsHeight();
    }

    public void setEntities(List<FileEntity.DataBean> entities) {
        mEntities = entities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public FileEntity.DataBean getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_gv_album_frag, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((FileEntity.DataBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(FileEntity.DataBean object, ViewHolder holder) {
        //TODO implement

        if (StringEmptyUtil.isEmpty(object.getU())) {
            Picasso.with(context).load(GetIcon.getResID(object.getIco())).
                    placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).resize(mWidth,mHeight)
                    .into(holder.imgvItemGvAlbumFrag);
        } else {
            Picasso.with(context).load(object.getU()).
                    placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).resize(mWidth,mHeight)
                    .into(holder.imgvItemGvAlbumFrag);
        }
    }

    protected class ViewHolder {
        private ImageView imgvItemGvAlbumFrag;

        public ViewHolder(View view) {
            imgvItemGvAlbumFrag = (ImageView) view.findViewById(R.id.imgv_item_gv_album_frag);

//            imgvItemGvAlbumFrag.post(new Runnable() {
//                @Override
//                public void run() {
//                    int width = imgvItemGvAlbumFrag.getWidth();
//                    ViewGroup.LayoutParams layoutParams = imgvItemGvAlbumFrag.getLayoutParams();
//                    layoutParams.height = width;
//                    imgvItemGvAlbumFrag.setLayoutParams(layoutParams);
//                }
//            });
//            setImgvHeight(imgvItemGvAlbumFrag);
        }

    }
//
//    private void setImgvHeight(ImageView imgvItemGvPic) {
//        ViewGroup.LayoutParams layoutParams = imgvItemGvPic.getLayoutParams();
//        layoutParams.height = mHeight;
//        imgvItemGvPic.setLayoutParams(layoutParams);
//    }
//
//////
    private int jsHeight() {

        return (DensityUtil.getScreenWidth() - DensityUtil.dip2px(10) * 2 - DensityUtil.dip2px(20) * 2) / 3;
    }
}
