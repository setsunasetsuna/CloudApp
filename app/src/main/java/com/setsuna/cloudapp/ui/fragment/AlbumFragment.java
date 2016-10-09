package com.setsuna.cloudapp.ui.fragment;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.mylib.net.JsonGet;
import com.example.mylib.net.NetCallback;
import com.setsuna.cloudapp.Constant;
import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.base.CloudFragment;
import com.setsuna.cloudapp.entity.FileEntity;
import com.setsuna.cloudapp.entity.PicEntity;
import com.setsuna.cloudapp.ui.adapter.ItemLvAlbumFragAdapter;
import com.setsuna.cloudapp.utils.FormatTime;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends CloudFragment implements OnRefreshListener, OnLoadMoreListener {
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private ListView mSwipeTarget;
    private int mTotalCount;
    private int mOffset;
    private List<FileEntity.DataBean> mDataBeensOld;
    private ItemLvAlbumFragAdapter mItemLvAlbumFragAdapter;
    private JsonGet mJsonGetLoadMore;
    private JsonGet mJsonGetReferesh;
    private List<FileEntity.DataBean> mDataBeanList;
    private PicEntity mPicEntityOld=null;
    private String mTimePrevious;
    @Override
    public int setRootView() {
        return R.layout.fragment_album;
    }

    @Override
    public boolean isUseTitle() {
        return false;
    }

    @Override
    public void initViews() {
        mSwipeToLoadLayout = (SwipeToLoadLayout) mRootView.findViewById(R.id.swipeToLoadLayout);
        mSwipeTarget = (ListView) mRootView.findViewById(R.id.swipe_target);

        //ct=list&aid=1&cid=0&o=user_ptime&asc=0&offset=0&limit=28
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);

    }

    @Override
    public void initDatas() {
        intiNet();
        mSwipeToLoadLayout.setRefreshing(true);
        Log.d("NetCallBackAlbumLoadmor", "mOffset:" + mOffset);
    }

    private void intiNet() {
        intiRefresh();
        intiLoadMore();
    }

    private void intiLoadMore() {
        mJsonGetLoadMore = new JsonGet(Constant.INDEX, new NetCallBackAlbumLoadmore());
        mJsonGetLoadMore.addParams("ct", "list");
        mJsonGetLoadMore.addParams("aid", "1");
        mJsonGetLoadMore.addParams("cid", "0");
        mJsonGetLoadMore.addParams("o", "user_ptime");
        mJsonGetLoadMore.addParams("asc", "0");
        mJsonGetLoadMore.addParams("offset", "0");
        mJsonGetLoadMore.addParams("limit", "50");
        mJsonGetLoadMore.addParams("type", "2");
        intiCookie(mJsonGetLoadMore);
    }

    private void intiRefresh() {
        mJsonGetReferesh = new JsonGet(Constant.INDEX, new NetCallBackAlbumRefrsh());
        mJsonGetReferesh.addParams("ct", "list");
        mJsonGetReferesh.addParams("aid", "1");
        mJsonGetReferesh.addParams("cid", "0");
        mJsonGetReferesh.addParams("o", "user_ptime");
        mJsonGetReferesh.addParams("asc", "0");
        mJsonGetReferesh.addParams("offset", "0");
        mJsonGetReferesh.addParams("limit", "50");
        mJsonGetReferesh.addParams("type", "2");
        intiCookie(mJsonGetReferesh);

    }

    @Override
    public void onRefresh() {
        mOffset=0;

        mJsonGetReferesh.excute();
    }

    @Override
    public void onLoadMore() {
        if (mTotalCount>mOffset){
            mJsonGetLoadMore.replaceParams("offset",mOffset);
            mJsonGetLoadMore.excute();
        }else {
            Toast.makeText(mActivity, "没有了", Toast.LENGTH_SHORT).show();
            mSwipeToLoadLayout.setLoadingMore(false);
        }


    }


    private class NetCallBackAlbumRefrsh extends NetCallback<FileEntity> {

        @Override
        public void success(FileEntity entity) {
            mDataBeanList = entity.getData();
            List<PicEntity>picEntities=getPicEntities();
            mItemLvAlbumFragAdapter=new ItemLvAlbumFragAdapter(mActivity,picEntities);
            mSwipeTarget.setAdapter(mItemLvAlbumFragAdapter);
            mTotalCount=entity.getCount();
            for (PicEntity p:picEntities){
                if (p.isPic()){
                    mOffset+=p.getDataBeen().size();
                }

            }
        }

        @Override
        public void fail() {
            Toast.makeText(mActivity, "网络异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void finish() {
            mSwipeToLoadLayout.setRefreshing(false);
        }
    }

    private class NetCallBackAlbumLoadmore extends NetCallback<FileEntity>{

        @Override
        public void success(FileEntity entity) {
            mDataBeanList = entity.getData();
            String time=mTimePrevious;
            List<PicEntity>picEntities=getPicEntities();
            for (PicEntity p:picEntities){
                if (p.isPic()){

                    mOffset+=p.getDataBeen().size();
                }
            }

            if (mItemLvAlbumFragAdapter==null){
                mItemLvAlbumFragAdapter=new ItemLvAlbumFragAdapter(mActivity,picEntities);
                mSwipeTarget.setAdapter(mItemLvAlbumFragAdapter);
            }else {
                if (picEntities.get(0).getTime().equalsIgnoreCase(time)){
                    picEntities.remove(0);
                    List<PicEntity> entities = mItemLvAlbumFragAdapter.getEntities();
                    mDataBeensOld=entities.get(entities.size()-1).getDataBeen();
                    mDataBeensOld.addAll(picEntities.get(0).getDataBeen());

                    PicEntity picEntity=new PicEntity();

                    picEntity.setDataBeen(mDataBeensOld);
                    picEntities.remove(0);
                    picEntities.add(0,picEntity);
                    entities.remove(entities.size()-1);
                    picEntities.addAll(0,entities);
                }

                mItemLvAlbumFragAdapter.setEntities(picEntities);
            }
            mTotalCount=entity.getCount();

        }

        @Override
        public void fail() {
            Toast.makeText(mActivity, "网络异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void finish() {
            mSwipeToLoadLayout.setLoadingMore(false);
        }
    }
    private List<PicEntity> getPicEntities(){
        List<PicEntity>picEntities=new ArrayList<>();
        PicEntity picEntityPics=null;
        mTimePrevious = "";
        for (FileEntity.DataBean dataBean: mDataBeanList){
            String timeNow= FormatTime.getTimeNYR(dataBean.getT());

            if (!timeNow.equalsIgnoreCase(mTimePrevious)){
                PicEntity picEntityTime=new PicEntity();
                picEntityTime.setTime(timeNow);
                picEntities.add(picEntityTime);

                picEntityPics=new PicEntity();
                picEntityPics.addDataBeen(dataBean);
                picEntities.add(picEntityPics);


            }else {
                picEntityPics.addDataBeen(dataBean);
            }

            mTimePrevious =timeNow;
        }



        return picEntities;
    }

}
