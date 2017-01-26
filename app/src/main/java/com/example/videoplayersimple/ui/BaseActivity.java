package com.example.videoplayersimple.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.videoplayersimple.R;

/*
 *  @项目名：  MobilePlayer 
 *  @包名：    com.example.mobileplayer.base
 *  @文件名:   BaseActivity
 *  @创建者:   ZYONG
 *  @创建时间:  2017/1/24 0024 22:21
 *  @描述：    TODO
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initListener();
        initData();
        //处理公用按钮点击事件
        regCommonBtn();
    }

    private void regCommonBtn() {
        View back = findViewById(R.id.back);
        if (back!=null){
            back.setOnClickListener(this);
        }
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.back){
            finish();
        }else{
            processClick(v);
        }
    }
    //处理除公用按钮外的其他事件
    protected abstract void processClick(View v);

    //开启新的Activity
    protected void startNewActivity(Class clazz,Boolean finish){
        Intent newActivity = new Intent(this, clazz);
        startActivity(newActivity);
        if (finish){
            finish();
        }
    }
}
