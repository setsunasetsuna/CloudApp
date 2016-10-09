package com.setsuna.cloudapp.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.entity.FileEntity;
import com.setsuna.cloudapp.utils.FormatSize;
import com.setsuna.cloudapp.utils.FormatTime;
import com.setsuna.cloudapp.utils.GetIcon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemFilelistDiskFragAdapter extends BaseAdapter implements View.OnClickListener {

    private List<FileEntity.DataBean> mEntities = new ArrayList<FileEntity.DataBean>();
    private List<FileEntity.DataBean> checkedDatas = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public ItemFilelistDiskFragAdapter(Context context, List<FileEntity.DataBean> entities) {
        this.context = context;
        mEntities = entities;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setEntities(List<FileEntity.DataBean> entities) {
        mEntities = entities;
        checkedDatas.clear();
        notifyDataSetChanged();
    }

    public void addEntities(List<FileEntity.DataBean> entitys) {
        mEntities.addAll(entitys);
        notifyDataSetChanged();
    }

    public List<FileEntity.DataBean> getDataEntitiesChecked() {


        return checkedDatas;
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
            convertView = layoutInflater.inflate(R.layout.item_filelist_disk_frag, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((FileEntity.DataBean) getItem(position), (ViewHolder) convertView.getTag(),position);
        return convertView;
    }

    private void initializeViews(FileEntity.DataBean object, ViewHolder holder,int position) {
        //TODO implement
        if (object.getCid() != null) {
            holder.rlFileDiskFrag.setVisibility(View.GONE);
            holder.rlDirDiskFrag.setVisibility(View.VISIBLE);
            holder.tvDirDiskFrag.setText(object.getN());
        } else {
            holder.rlFileDiskFrag.setVisibility(View.VISIBLE);
            holder.rlDirDiskFrag.setVisibility(View.GONE);
            holder.tvTitleFileDiskFrag.setText(object.getN());
            holder.tvSizeFileDiskFrag.setText(FormatSize.getSize(object.getS()));
            holder.tvTimeFileDiskFrag.setText(FormatTime.getTime(object.getT()));
            if (object.getU() == null) {
                Picasso.with(context).load(GetIcon.getResID(object.getIco())).fit().into(holder.imgvFileDiskFrag);
            } else {
                Picasso.with(context).load(object.getU()).fit().into(holder.imgvFileDiskFrag);
            }

        }
        holder.cbDiskFrag.setChecked(object.isChecked());
        holder.cbDiskFrag.setTag(position);
        holder.cbDiskFrag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        CheckBox checkBox = (CheckBox) v;
        int position= (int) checkBox.getTag();
        if (checkBox.isChecked()) {
            mEntities.get(position).setChecked(true);
            checkedDatas.add(mEntities.get(position));
            Log.d("ItemFilelistDiskFragAda", "checkedDatas:" + checkedDatas);
        } else {
            mEntities.get(position).setChecked(false);
            checkedDatas.remove(mEntities.get(position));
            Log.d("ItemFilelistDiskFragAda", "checkedDatasremove:" + checkedDatas);
        }
    }


    protected class ViewHolder {
        private RelativeLayout rlDirDiskFrag;
        private TextView tvDirDiskFrag;
        private RelativeLayout rlFileDiskFrag;
        private ImageView imgvFileDiskFrag;
        private TextView tvTitleFileDiskFrag;
        private TextView tvSizeFileDiskFrag;
        private TextView tvTimeFileDiskFrag;
        private CheckBox cbDiskFrag;

        public ViewHolder(View view) {
            rlDirDiskFrag = (RelativeLayout) view.findViewById(R.id.rl_dir_disk_frag);
            tvDirDiskFrag = (TextView) view.findViewById(R.id.tv_dir_disk_frag);
            rlFileDiskFrag = (RelativeLayout) view.findViewById(R.id.rl_file_disk_frag);
            imgvFileDiskFrag = (ImageView) view.findViewById(R.id.imgv_file_disk_frag);
            tvTitleFileDiskFrag = (TextView) view.findViewById(R.id.tv_title_file_disk_frag);
            tvSizeFileDiskFrag = (TextView) view.findViewById(R.id.tv_size_file_disk_frag);
            tvTimeFileDiskFrag = (TextView) view.findViewById(R.id.tv_time_file_disk_frag);
            cbDiskFrag = (CheckBox) view.findViewById(R.id.cb_disk_frag);
        }
    }
}
