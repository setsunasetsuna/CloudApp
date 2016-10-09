package com.setsuna.cloudapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.download.DownLoadFile;
import com.setsuna.cloudapp.download.DownloadControl;
import com.setsuna.cloudapp.download.DownloadState;
import com.setsuna.cloudapp.utils.GetDownloadState;

import java.util.ArrayList;
import java.util.List;

public class ItemLvDownloadListAdapter extends BaseAdapter implements View.OnClickListener {

    private List<DownLoadFile> mEntities = new ArrayList<DownLoadFile>();
    private List<ViewHolder> mViewHolders = new ArrayList<>();
    private DownloadControl mDownloadControl;
    private Context context;
    private LayoutInflater layoutInflater;

    public ItemLvDownloadListAdapter(Context context, List<DownLoadFile> entities) {
        this.context = context;
        mEntities = entities;
        this.layoutInflater = LayoutInflater.from(context);
        mDownloadControl = new DownloadControl();
    }

    public void setEntities(List<DownLoadFile> entities) {
        mEntities = entities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public DownLoadFile getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_lv_download_list, null);
            ViewHolder viewHolder=new ViewHolder(convertView);
            mViewHolders.add(viewHolder);
            convertView.setTag(viewHolder);
        }
        initializeViews((DownLoadFile) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(DownLoadFile object, ViewHolder holder) {
        //TODO implement
        holder.setdownloadFile(object);
        holder.tvTitleItemLvDownload.setText(object.getName());
        holder.tvSizeItemLvDownload.setText(object.getDownloadSizeFormat() + " / " + object.getTotalSizeFormat());
        if (object.getState() != DownloadState.STATE_DOWNLOADING) {
            holder.tvTimeItemLvDownload.setVisibility(View.GONE);
            holder.tvStateItemLvDownload.setVisibility(View.VISIBLE);
            holder.tvStateItemLvDownload.setText(GetDownloadState.getMsg(object.getState())+"/s");
        } else {
            holder.tvTimeItemLvDownload.setVisibility(View.VISIBLE);
            holder.tvStateItemLvDownload.setVisibility(View.GONE);
            holder.tvTimeItemLvDownload.setText(object.getSpeed());
        }
        holder.ibDeleteItemLvDownload.setTag(object);
        holder.ibDeleteItemLvDownload.setOnClickListener(this);

        holder.cbStopItemLvDownload.setTag(object);
        holder.cbStopItemLvDownload.setOnClickListener(this);
        if (object.getState() == DownloadState.STATE_PAUSE) {
            holder.cbStopItemLvDownload.setChecked(true);
        } else {
            holder.cbStopItemLvDownload.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CheckBox) {
            DownLoadFile downloadTask = (DownLoadFile) v.getTag();
            CheckBox checkBox = (CheckBox) v;
            if (checkBox.isChecked()) {
                mDownloadControl.updateState(downloadTask, DownloadState.STATE_PAUSE);
                refreshState(downloadTask);
            } else {
                mDownloadControl.updateState(downloadTask, DownloadState.STATE_WAITING);
                refreshState(downloadTask);
                mDownloadControl.startDownload();
            }
            return;

        } else {
            DownLoadFile entity = (DownLoadFile) v.getTag();
            mEntities.remove(entity);
            mDownloadControl.cancelTask(entity);
            notifyDataSetChanged();
        }
    }

    public void refreshState(DownLoadFile downloadTask) {
        for (ViewHolder viewHolder : mViewHolders) {
            DownLoadFile entity = viewHolder.mDownLoadFile;
            if (entity.getId() == downloadTask.getId()) {
                //说明我们找到了指定位置的holder
                viewHolder.tvStateItemLvDownload.setText(GetDownloadState.getMsg(downloadTask.getState()));

                viewHolder.tvTimeItemLvDownload.setVisibility(View.GONE);
                viewHolder.tvStateItemLvDownload.setVisibility(View.VISIBLE);
                break;
            }

        }
    }

    public void refreshSpeed(DownLoadFile downloadTask) {
        for (ViewHolder viewHolder : mViewHolders) {
            DownLoadFile entity = viewHolder.mDownLoadFile;
            if (entity.getId() == downloadTask.getId()) {
                //说明我们找到了指定位置的holder
                viewHolder.tvTimeItemLvDownload.setText(downloadTask.getSpeed());
                viewHolder.tvTimeItemLvDownload.setVisibility(View.VISIBLE);
                viewHolder.tvStateItemLvDownload.setVisibility(View.GONE);
                break;
            }

        }
    }

    public void refreshProgress(DownLoadFile downloadTask) {
        for (ViewHolder viewHolder : mViewHolders) {
            DownLoadFile entity = viewHolder.mDownLoadFile;


            if (entity.getId() == downloadTask.getId()) {
                //说明我们找到了指定位置的holder
                viewHolder.tvSizeItemLvDownload.setText(downloadTask.getDownloadSizeFormat() + " / " + downloadTask.getTotalSizeFormat());
                double progress = (downloadTask.getDownloadSize() * 1.0 / downloadTask.getTotalSize()) * 100;
//                Logger.e("收到了。。"+progress);
                viewHolder.progressBar.setProgress((int) progress);
                break;
            }

        }
    }

    protected class ViewHolder {
        private TextView tvTitleItemLvDownload;
        private TextView tvSizeItemLvDownload;
        private TextView tvTimeItemLvDownload;
        private TextView tvStateItemLvDownload;
        private CheckBox cbStopItemLvDownload;
        private ImageButton ibDeleteItemLvDownload;
        private DownLoadFile mDownLoadFile;
        private ProgressBar progressBar;
        public ViewHolder(View view) {
            tvTitleItemLvDownload = (TextView) view.findViewById(R.id.tv_title_item_lv_download);
            tvSizeItemLvDownload = (TextView) view.findViewById(R.id.tv_size_item_lv_download);
            tvTimeItemLvDownload = (TextView) view.findViewById(R.id.tv_time_item_lv_download);
            tvStateItemLvDownload = (TextView) view.findViewById(R.id.tv_state_item_lv_download);
            cbStopItemLvDownload = (CheckBox) view.findViewById(R.id.cb_stop_item_lv_download);
            ibDeleteItemLvDownload = (ImageButton) view.findViewById(R.id.ib_delete_item_lv_download);
            progressBar= (ProgressBar) view.findViewById(R.id.pb_item_lv_download);
        }

        public void setdownloadFile(DownLoadFile object) {
            mDownLoadFile = object;
        }
    }
}
