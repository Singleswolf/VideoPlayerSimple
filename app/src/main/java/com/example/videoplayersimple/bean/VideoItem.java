package com.example.videoplayersimple.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

/*
 *  @项目名：  VideoPlayerSimple 
 *  @创建者:   ZYONG
 *  @创建时间:  2017/1/25 0025 20:37
 */
public class VideoItem implements Serializable {
    private String path;
    private String title;
    private int duration;
    private long size;

    public static VideoItem getVideoItem(Cursor cursor){
        VideoItem videoItem = new VideoItem();
        videoItem.path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        videoItem.title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
        videoItem.duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
        videoItem.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
        return videoItem;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
