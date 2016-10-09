package com.setsuna.cloudapp.ui.fragment;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.mylib.net.JsonGet;
import com.example.mylib.net.NetCallback;
import com.example.mylib.util.StringEmptyUtil;
import com.setsuna.cloudapp.Constant;
import com.setsuna.cloudapp.R;
import com.setsuna.cloudapp.base.CloudFragment;
import com.setsuna.cloudapp.download.DownloadControl;
import com.setsuna.cloudapp.entity.FileEntity;
import com.setsuna.cloudapp.entity.NewFloderEntity;
import com.setsuna.cloudapp.ui.dialog.NewFloderDig;
import com.setsuna.cloudapp.ui.activity.DownloadActivity;
import com.setsuna.cloudapp.ui.adapter.ItemFilelistDiskFragAdapter;
import com.setsuna.cloudapp.ui.dialog.SearchDig;
import com.setsuna.cloudapp.ui.pop.Pop_more;
import com.setsuna.cloudapp.ui.views.NaviBar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiskFragment extends CloudFragment implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener, NewFloderDig.OnFileNameConfirm, NaviBar.OnNaviBarClickListenner, SearchDig.OnSearchConfirm {
    private JsonGet mJsonGetReferesh;
    private JsonGet mJsonGetLoadMore;
    private JsonGet mJsonGetCreateNewFloder;
    private JsonGet mJsonGetSearch;
    private ItemFilelistDiskFragAdapter mItemFilelistDiskFragAdapter;
    private ListView mLvDiskFrag;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private String mCidDirNow;
    private int mOffset = 0;
    private int mTotalCount = -1;
    private List<FileEntity.DataBean> mDataBeenNow;
    private List<FileEntity.PathBean> mPathBeenNow;
    private FrameLayout mFlUploadBottomDiskFrag;
    private FrameLayout mFlShareBottomDiskFrag;
    private FrameLayout mFlDownloadBottomDiskFrag;
    private FrameLayout mFlMoreBottomDiskFrag;
    private LinearLayout mLlDiskFrag;
    private Pop_more mPop_more;
    private TextView mTvMoreBottomDiskFrag;
    private NewFloderDig mNewFloderDig;
    private DownloadControl mDownloadControl;
    private TextView mTvShowdownloadDiskFrag;
    private NaviBar mNavibarDiskFrag;
    private SearchDig mSearchDig;
    private String mSearchWord;
    @Override
    public int setRootView() {
        return R.layout.fragment_disk;
    }

    @Override
    public boolean isUseTitle() {
        return false;
    }

    @Override
    public void initViews() {
        mNavibarDiskFrag = (NaviBar) mRootView.findViewById(R.id.navibar_disk_frag);
        mLvDiskFrag = (ListView) mRootView.findViewById(R.id.swipe_target);
        mSwipeToLoadLayout = (SwipeToLoadLayout) mRootView.findViewById(R.id.swipeToLoadLayout);
        mFlUploadBottomDiskFrag = (FrameLayout) mRootView.findViewById(R.id.fl_upload_bottom_disk_frag);
        mFlShareBottomDiskFrag = (FrameLayout) mRootView.findViewById(R.id.fl_share_bottom_disk_frag);
        mFlDownloadBottomDiskFrag = (FrameLayout) mRootView.findViewById(R.id.fl_download_bottom_disk_frag);
        mFlMoreBottomDiskFrag = (FrameLayout) mRootView.findViewById(R.id.fl_more_bottom_disk_frag);
        mLlDiskFrag = (LinearLayout) mRootView.findViewById(R.id.ll_disk_frag);
        mTvShowdownloadDiskFrag = (TextView) mRootView.findViewById(R.id.tv_showdownload_disk_frag);

        mTvMoreBottomDiskFrag = (TextView) mRootView.findViewById(R.id.tv_more_bottom_disk_frag);
        mPop_more=new Pop_more(mActivity);

        mNewFloderDig=new NewFloderDig(mActivity);
        mNewFloderDig.setOnFileNameConfirm(this);

        mPop_more.setOnclickListenner(this);
        mFlUploadBottomDiskFrag.setOnClickListener(this);
        mFlShareBottomDiskFrag.setOnClickListener(this);
        mFlDownloadBottomDiskFrag.setOnClickListener(this);
        mFlMoreBottomDiskFrag.setOnClickListener(this);
        mTvShowdownloadDiskFrag.setOnClickListener(this);
        mNavibarDiskFrag.setOnNaviBarClickListenner(this);
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mLvDiskFrag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileEntity.DataBean dataBeen=mDataBeenNow.get(position);
                if (StringEmptyUtil.isEmpty(dataBeen.getCid())){

                }else {
                    openDir(dataBeen.getCid());
                }
            }
        });

        mSearchDig=new SearchDig(mActivity);
        mSearchDig.setOnSearchConfirm(this);
    }

    @Override
    public void initDatas() {

        intiNet();
        mDownloadControl=new DownloadControl();
        mSwipeToLoadLayout.setRefreshing(true);
    }

    private void intiNet() {
        intiRefresh();
        intiLoadMore();
        intiCreateFloder();
    }

    private void intiCreateFloder() {
        mJsonGetCreateNewFloder=new JsonGet(Constant.CREATE_FOLDER_URL,new NetCallBackCreateNewFloder(),true);
        intiCookie(mJsonGetCreateNewFloder);
    }

    private void openDir(String cid){
        mJsonGetLoadMore.replaceParams("cid",cid);
        mJsonGetReferesh.replaceParams("cid",cid);
        mSwipeToLoadLayout.setRefreshing(true);
    }

    private void intiLoadMore() {
        mJsonGetLoadMore=new JsonGet(Constant.INDEX,new NetCallBackLoadMore());
        mJsonGetLoadMore.addParams("ct","list");
        mJsonGetLoadMore.addParams("aid","1");
        mJsonGetLoadMore.addParams("cid","0");
        mJsonGetLoadMore.addParams("o","user_ptime");
        mJsonGetLoadMore.addParams("asc","0");
        mJsonGetLoadMore.addParams("offset","0");
        mJsonGetLoadMore.addParams("limit","50");
        intiCookie(mJsonGetLoadMore);
    }

    private void intiRefresh() {
        //ct=list&aid=1&cid=0&o=user_ptime&asc=0&offset=0&limit=28

        mJsonGetReferesh=new JsonGet(Constant.INDEX,new NetCallBackRefresh());
        mJsonGetReferesh.addParams("ct","list");
        mJsonGetReferesh.addParams("aid","1");
        mJsonGetReferesh.addParams("cid","0");
        mJsonGetReferesh.addParams("o","user_ptime");
        mJsonGetReferesh.addParams("asc","0");
        mJsonGetReferesh.addParams("offset","0");
        mJsonGetReferesh.addParams("limit","50");
        intiCookie(mJsonGetReferesh);
    }

    @Override
    public void onRefresh() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_upload_bottom_disk_frag:

                break;
            case R.id.fl_share_bottom_disk_frag:

                break;
            case R.id.fl_download_bottom_disk_frag:
                doDownload();
                break;
            case R.id.fl_more_bottom_disk_frag:
                mPop_more.showAsDropDown(mLlDiskFrag);
                if (mPop_more.isShowing()){
                    mTvMoreBottomDiskFrag.setCompoundDrawablesWithIntrinsicBounds(null, mActivity.getResources().getDrawable(R.drawable.menu_more_selected_gray),null,null);
                }
                mPop_more.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTvMoreBottomDiskFrag.setCompoundDrawablesWithIntrinsicBounds(null, mActivity.getResources().getDrawable(R.drawable.menu_more_gray),null,null);
                    }
                });
                break;
             case R.id.fl_newfloder_disk_frag:
                createNewFloder();
                break;
             case R.id.fl_sort_disk_frag:
                break;
             case R.id.fl_search_disk_frag:
                search();
                break;
             case R.id.fl_refresh_disk_frag:
                mSwipeToLoadLayout.setRefreshing(true);
                break;
             case R.id.fl_uploaded_disk_frag:

                break;
             case R.id.fl_downloaded_disk_frag:

                break;
            case R.id.tv_showdownload_disk_frag:
                goToActivity(DownloadActivity.class);
                mTvShowdownloadDiskFrag.setVisibility(View.GONE);
                break;

        }
    }

    private void doDownload() {
        Toast.makeText(mActivity, "downloading....", Toast.LENGTH_SHORT).show();
        mTvShowdownloadDiskFrag.setVisibility(View.VISIBLE);
        List<FileEntity.DataBean> dataEntitiesChecked = mItemFilelistDiskFragAdapter.getDataEntitiesChecked();
        if (dataEntitiesChecked== null || dataEntitiesChecked.size()==0) {
            Toast.makeText(mActivity, "请选择至少一个文件", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("DiskFragment", "dataEntitiesChecked:" + dataEntitiesChecked);
        for (FileEntity.DataBean dataEntity:dataEntitiesChecked){
            mDownloadControl.addToQueue(dataEntity,mPathBeenNow);
        }

    }

    private void createNewFloder(){
        mNewFloderDig.show();
    }

    private void search(){
        mSearchDig.show();
    }

    @Override
    public void onConfirm(String fileName) {
       for (FileEntity.DataBean d:mDataBeenNow){
           if (StringEmptyUtil.isEmpty(d.getCid())){
               break;
           }
           if (fileName.equalsIgnoreCase(d.getN())){
               Toast.makeText(mActivity, "已存在此文件夹", Toast.LENGTH_SHORT).show();
               return;
           }

       }
        mJsonGetCreateNewFloder.replaceParams("cname",fileName);
        mJsonGetCreateNewFloder.replaceParams("pid",mCidDirNow);
        mJsonGetCreateNewFloder.excute();
    }

    @Override
    public void onItemClick(View v, FileEntity.PathBean pathEntity) {
        openDir(pathEntity.getCid()+"");
    }

    @Override
    public void onSearch(String fileName) {
//        offset=0&limit=28&value=bbb ct=file&ac=search
        mSearchWord=fileName;
        mJsonGetSearch=new JsonGet(Constant.SEARCH_URL,new NetCallBackSearch(),true);
        mJsonGetSearch.replaceParams("offset",0);
        mJsonGetSearch.replaceParams("limit",28);
        mJsonGetSearch.replaceParams("value",fileName);
        intiCookie(mJsonGetSearch);
        mJsonGetSearch.excute();
    }

    private class NetCallBackSearch extends NetCallback<FileEntity>{

        @Override
        public void success(FileEntity entity) {
            Log.d("NetCallBackSearch", "entity.getData().size():" + entity.isState());
            if (mItemFilelistDiskFragAdapter==null){
                mItemFilelistDiskFragAdapter=new ItemFilelistDiskFragAdapter(mActivity,entity.getData());
                mLvDiskFrag.setAdapter(mItemFilelistDiskFragAdapter);
            }else {
                mItemFilelistDiskFragAdapter.setEntities(entity.getData());
            }
            FileEntity.PathBean pathEntity=new FileEntity.PathBean();
            pathEntity.setName(mSearchWord+" 的搜索结果");
            mPathBeenNow.add(pathEntity);
            mNavibarDiskFrag.searchItem(mSearchWord);

        }

        @Override
        public void fail() {
            Toast.makeText(mActivity, "网络异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void finish() {

        }
    }

    private class NetCallBackCreateNewFloder extends NetCallback<NewFloderEntity>{

    @Override
    public void success(NewFloderEntity entity) {
        if (entity.isState()){
            Toast.makeText(mActivity, "创建成功", Toast.LENGTH_SHORT).show();
            mSwipeToLoadLayout.setRefreshing(true);
        }else {
            Toast.makeText(mActivity, "创建失败", Toast.LENGTH_SHORT).show();
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

    private class NetCallBackLoadMore extends NetCallback<FileEntity>{

        @Override
        public void success(FileEntity entity) {
            if (mItemFilelistDiskFragAdapter==null){
                mItemFilelistDiskFragAdapter=new ItemFilelistDiskFragAdapter(mActivity,entity.getData());
                mLvDiskFrag.setAdapter(mItemFilelistDiskFragAdapter);
                mDataBeenNow=entity.getData();
            }else {
                mItemFilelistDiskFragAdapter.addEntities(entity.getData());
                mDataBeenNow=entity.getData();
            }
            mOffset=mItemFilelistDiskFragAdapter.getCount();
            mTotalCount=entity.getCount();

        }

        @Override
        public void fail() {
            Toast.makeText(mActivity, "网不好", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void finish() {
            mSwipeToLoadLayout.setLoadingMore(false);
        }
    }

    private class NetCallBackRefresh extends NetCallback<FileEntity>{


        @Override
        public void success(FileEntity entity) {
            if (mItemFilelistDiskFragAdapter==null){
                mItemFilelistDiskFragAdapter=new ItemFilelistDiskFragAdapter(mActivity,entity.getData());
                mLvDiskFrag.setAdapter(mItemFilelistDiskFragAdapter);
            }else {
                mItemFilelistDiskFragAdapter.setEntities(entity.getData());
            }
            mCidDirNow=entity.getCid()+"";
            mOffset=mItemFilelistDiskFragAdapter.getCount();
            mTotalCount=entity.getCount();
            mDataBeenNow=entity.getData();
            mPathBeenNow=entity.getPath();
            mNavibarDiskFrag.changeItem(mPathBeenNow);

        }

        @Override
        public void fail() {
            Toast.makeText(mActivity, "网不好", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void finish() {
            mSwipeToLoadLayout.setRefreshing(false);
        }
    }
    public boolean onBackPressed(){
        if (mSwipeToLoadLayout.isLoadingMore()){
            mJsonGetLoadMore.cancle();
            mSwipeToLoadLayout.setLoadingMore(false);
            return true;
        }
        if (mSwipeToLoadLayout.isRefreshing()){
            mJsonGetReferesh.cancle();
            mSwipeToLoadLayout.setRefreshing(false);
            return true;
        }
        return canGoback();
    }
    public boolean canGoback(){
        if (mPathBeenNow==null || mPathBeenNow.size()<2){
            return false;
        }
        openDir(mPathBeenNow.get(mPathBeenNow.size()-2).getCid()+"");
        return true;
    }
}
