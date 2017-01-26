package com.example.videoplayersimple.ui;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.videoplayersimple.R;
import com.example.videoplayersimple.adapter.VideoCursorAdapter;
import com.example.videoplayersimple.bean.VideoItem;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    private ListView mListView;
    private VideoCursorAdapter mAdapter;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mCursor = (Cursor) msg.obj;
                    //设置新Cursor刷新cursor
                    mAdapter.swapCursor(mCursor);
                    break;
            }
        }
    };
    private Cursor mCursor;

    @Override
    protected void initData() {
        final ContentResolver resolver = getBaseContext().getContentResolver();
        //第一种异步查询方式：new Thread新建子线程
//        queryData(resolver);
        //第二种异步查询方式：AsyncTask
//        queryData2(resolver);
        //第三种异步查询方式：AsyncQueryHandler
        queryData3(resolver);
    }

    private void queryData2(ContentResolver resolver) {
        //传入参数
        new MyTask().execute(resolver);
    }

    private void queryData3(final ContentResolver resolver) {
        AsyncQueryHandler handler = new AsyncQueryHandler(resolver){
            //查询完成时回调
            /**
             * @param token 令牌
             * @param cookie startQuery中传入的对象
             * @param cursor 查找返回的Cursor
             */
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);
                ((VideoCursorAdapter)cookie).swapCursor(cursor);
            }
        };
        handler.startQuery(0,mAdapter, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
        }, null, null, null);
    }

    private void queryData(final ContentResolver resolver) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{
                        MediaStore.Video.Media._ID,
                        MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.TITLE,
                        MediaStore.Video.Media.SIZE,
                }, null, null, null, null);
                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = cursor;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    @Override
    protected void initListener() {
        mAdapter = new VideoCursorAdapter(getBaseContext(), null);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放cursor
        mAdapter.changeCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //获取当前条目的viewItem
        VideoItem videoItem = VideoItem.getVideoItem((Cursor) parent.getItemAtPosition(position));
        Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
        intent.putExtra("videoItem",videoItem);
        startActivity(intent);
    }

    class MyTask extends AsyncTask<ContentResolver,Void,Cursor> {
        //子线程执行
        @Override
        protected Cursor doInBackground(ContentResolver... params) {
            Cursor cursor = params[0].query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.SIZE,
            }, null, null, null, null);
            return cursor;
        }

        //主线程执行
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mAdapter.swapCursor(cursor);
        }
    }
}
