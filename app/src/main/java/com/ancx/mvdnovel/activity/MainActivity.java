package com.ancx.mvdnovel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.util.ImageLoader;
import com.ancx.mvdnovel.util.MsgUtil;
import com.ancx.mvdnovel.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    private long firstExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - firstExitTime < 1500) {
                exit();
            } else {
                MsgUtil.ToastShort("再按一次退出小说！");
            }
            firstExitTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        finish();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView tv_info, tv_exit;
    private ImageView iv_title;
    private TextView tv_list, tv_class;

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mToolbar.setTitle("我的书架");
        mToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // TODO LeftMenu
        iv_title = (ImageView) findViewById(R.id.iv_title);
        ImageLoader.display("http://edu.cnr.cn/list/201307/W020130729357663094833.png", iv_title, R.mipmap.ic_launcher, R.mipmap.ic_launcher, 0, 0);
        // TODO LeftMenu的列表按钮
        tv_list = (TextView) findViewById(R.id.tv_list);
        tv_list.setOnClickListener(this);
        tv_class = (TextView) findViewById(R.id.tv_class);
        tv_class.setOnClickListener(this);
        // TODO LeftMenu的Bottom按钮
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_info.setOnClickListener(this);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        tv_exit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_info:
                showInfo();
                break;
            case R.id.tv_exit:
                exit();
                break;
            case R.id.tv_list:
                startActivity(new Intent(getApplicationContext(), ListActivity.class));
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_class:
                startActivity(new Intent(getApplicationContext(), ClassActivity.class));
                mDrawerLayout.closeDrawers();
                break;
        }
    }

    private void showInfo() {
        MsgUtil.ToastShort("感谢追书神器!");
    }

    @Override
    public void showNovel() {

    }

}
