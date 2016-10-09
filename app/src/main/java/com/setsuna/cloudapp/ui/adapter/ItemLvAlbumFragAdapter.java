package com.setsuna.cloudapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.entity.PicEntity;
import com.setsuna.cloudapp.ui.views.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class ItemLvAlbumFragAdapter extends BaseAdapter {

    private List<PicEntity> mEntities = new ArrayList<PicEntity>();
    private Context context;
    private LayoutInflater layoutInflater;
    private ItemGvAlbumFragAdapter mItemGvAlbumFragAdapter;

    public ItemLvAlbumFragAdapter(Context context, List<PicEntity> entities) {
        this.context = context;
        mEntities = entities;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setEntities(List<PicEntity> entities) {
        mEntities = entities;
        notifyDataSetChanged();
    }

    public List<PicEntity> getEntities() {
        return mEntities;
    }

    public void addEntities(List<PicEntity> entities) {
        mEntities.addAll(entities);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public PicEntity getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_lv_album_frag, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((PicEntity) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(PicEntity object, ViewHolder holder) {
        //TODO implement
        if (object.isTime()) {
            holder.tvDateItemLvAlbumFrag.setVisibility(View.VISIBLE);
            holder.gvPicItemLvAlbumFrag.setVisibility(View.GONE);
            holder.tvDateItemLvAlbumFrag.setText(object.getTime());
        }
        if (object.isPic()) {
            holder.tvDateItemLvAlbumFrag.setVisibility(View.GONE);
            holder.gvPicItemLvAlbumFrag.setVisibility(View.VISIBLE);
            mItemGvAlbumFragAdapter = new ItemGvAlbumFragAdapter(context, object.getDataBeen());
            holder.gvPicItemLvAlbumFrag.setAdapter(mItemGvAlbumFragAdapter);
        }

    }

    protected class ViewHolder {
        private TextView tvDateItemLvAlbumFrag;
        private MyGridView gvPicItemLvAlbumFrag;

        public ViewHolder(View view) {
            tvDateItemLvAlbumFrag = (TextView) view.findViewById(R.id.tv_date_item_lv_album_frag);
            gvPicItemLvAlbumFrag = (MyGridView) view.findViewById(R.id.gv_pic_item_lv_album_frag);
        }
    }
}
