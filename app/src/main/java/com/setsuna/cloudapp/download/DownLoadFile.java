package com.setsuna.cloudapp.download;

import com.setsuna.cloudapp.utils.FormatSize;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by setsuna on 2016/9/24.
 */
@Table(name = "DownLoadFile")
public class DownLoadFile {
    @Column(name = "id" ,isId = true)
    private int id;

    @Column(name = "fid")
    private String fid;

    @Column(name = "cid")
    private String cid;

    @Column(name = "name")
    private String name;

    @Column(name = "totalSize")
    private long totalSize;

    @Column(name = "downloadSize")
    private long downloadSize;

    @Column(name = "speed")
    private String speed;

    @Column(name = "state")
    private int state;

    @Column(name = "pid")
    private String pid;

    @Column(name = "url")
    private String url;

    @Column(name = "savePath")
    private String savePath;

    @Column(name = "sha1")
    private String sha1;

    @Column(name = "time")
    private long time;

    @Column(name = "totalSizeFormat")
    private String totalSizeFormat;

    @Column(name = "downloadSizeFormat")
    private String downloadSizeFormat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
        this.totalSizeFormat=FormatSize.getSize(totalSize);
    }

    public long getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(long downloadSize) {
        this.downloadSize = downloadSize;
        this.downloadSizeFormat= FormatSize.getSize(downloadSize);
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTotalSizeFormat() {
        return totalSizeFormat;
    }

    public void setTotalSizeFormat(String totalSizeFormat) {
        this.totalSizeFormat = totalSizeFormat;

    }

    public String getDownloadSizeFormat() {
        return downloadSizeFormat;
    }

    public void setDownloadSizeFormat(String downloadSizeFormat) {
        this.downloadSizeFormat = downloadSizeFormat;
    }


}
