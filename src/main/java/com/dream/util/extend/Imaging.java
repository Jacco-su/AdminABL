package com.dream.util.extend;

public class Imaging {
    public String fname; // 文件名
    public String fpath; // 文件路径
    public String createTime; // 文件创建时间
    public long fszie;// 文件大小
    public String formatSize; // 转换后的文件的小


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFpath() {
        return fpath;
    }

    public void setFpath(String fpath) {
        this.fpath = fpath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getFszie() {
        return fszie;
    }

    public void setFszie(long fszie) {
        this.fszie = fszie;
    }

    public String getFormatSize() {
        return formatSize;
    }

    public void setFormatSize(String formatSize) {
        this.formatSize = formatSize;
    }
}
