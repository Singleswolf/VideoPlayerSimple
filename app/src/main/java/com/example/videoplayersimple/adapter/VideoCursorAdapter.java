package com.example.videoplayersimple.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.videoplayersimple.R;
import com.example.videoplayersimple.bean.VideoItem;
import com.example.videoplayersimple.utils.StringUtil;

/*
 *  @项目名：  VideoPlayerSimple
 *  @创建者:   ZYONG
 *  @创建时间:  2017/1/25 0025 20:26
 */
public class VideoCursorAdapter extends CursorAdapter {

    public VideoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        VideoItem videoItem = VideoItem.getVideoItem(cursor);
        holder.video_item_name.setText(videoItem.getTitle());
        holder.video_item_duration.setText(StringUtil.parseDuration(videoItem.getDuration()));
        holder.video_item_size.setText(Formatter.formatFileSize(context,videoItem.getSize()));
    }

    static class ViewHolder {
        public TextView video_item_name;
        public TextView video_item_duration;
        public TextView video_item_size;

        public ViewHolder(View rootView) {
            video_item_name = (TextView) rootView.findViewById(R.id.video_item_name);
            video_item_duration = (TextView) rootView.findViewById(R.id.video_item_duration);
            video_item_size = (TextView) rootView.findViewById(R.id.video_item_size);
        }

    }
}
