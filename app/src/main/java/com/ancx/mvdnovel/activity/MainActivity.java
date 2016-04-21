package com.ancx.mvdnovel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.ReadBookAdapter;
import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.listener.OnItemClickListener;
import com.ancx.mvdnovel.listener.OnMoreOperationListener;
import com.ancx.mvdnovel.presenter.PresenterMain;
import com.ancx.mvdnovel.service.CacheBookService;
import com.ancx.mvdnovel.util.ImageLoader;
import com.ancx.mvdnovel.util.MsgUtil;
import com.ancx.mvdnovel.view.MainView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnMoreOperationListener, OnItemClickListener {

    private long firstExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return true;
        }
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

    private PresenterMain presenterMain;

    @Override
    protected void onResume() {
        super.onResume();
        if (NovelApp.readBookChanged)
            presenterMain.getMyBooks();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenterMain = new PresenterMain(this);

        initView();
    }

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView tv_info, tv_exit;
    private ImageView iv_title;
    private LinearLayout ll_list, ll_class;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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
        initLeftMenu();
        initMain();
    }

    private void initLeftMenu() {
        // TODO LeftMenu
        iv_title = (ImageView) findViewById(R.id.iv_title);
        ImageLoader.display("http://edu.cnr.cn/list/201307/W020130729357663094833.png", iv_title, R.mipmap.ic_launcher, R.mipmap.ic_launcher, 0, 0);
        // TODO LeftMenu的列表按钮
        ll_list = (LinearLayout) findViewById(R.id.ll_list);
        ll_list.setOnClickListener(this);
        ll_class = (LinearLayout) findViewById(R.id.ll_class);
        ll_class.setOnClickListener(this);
        // TODO LeftMenu的Bottom按钮
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_info.setOnClickListener(this);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        tv_exit.setOnClickListener(this);
    }

    private void initMain() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        presenterMain.getMyBooks();
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
            case R.id.ll_list:
                startActivity(new Intent(getApplicationContext(), RankingActivity.class));
                mDrawerLayout.closeDrawers();
                break;
            case R.id.ll_class:
                startActivity(new Intent(getApplicationContext(), ClassActivity.class));
                mDrawerLayout.closeDrawers();
                break;
        }
    }

    private AlertDialog infoDialog;

    private void showInfo() {
        if (infoDialog == null) {
            infoDialog = new AlertDialog.Builder(this)
                    .setTitle("MVD小说")
                    .setMessage("版本号：1.0\n\n希望各位看官能在评论区指出App的不足，提出宝贵的建议。MVD小说会在大家的帮助下，更加优秀。")
                    .setPositiveButton("确定", null)
                    .show();
            infoDialog.setCanceledOnTouchOutside(false);
        } else
            infoDialog.show();
    }

    private ReadBookAdapter readBookAdapter;

    @Override
    public void showBook(List<BookDetail> books) {
        if (readBookAdapter == null) {
            readBookAdapter = new ReadBookAdapter(books);
            readBookAdapter.setOnItemClickListener(this);
            readBookAdapter.setOnMoreOperationListener(this);
            mRecyclerView.setAdapter(readBookAdapter);
            presenterMain.updateBooks();
        } else {
            readBookAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        presenterMain.updateBooks();
    }

    @Override
    public void updateComplete(boolean update) {
        if (update)
            readBookAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRemoveBook(String _id, int position) {
        presenterMain.removeBook(_id, position);
    }

    @Override
    public void onCacheBook(BookDetail book) {
        Intent cacheService = new Intent(getApplicationContext(), CacheBookService.class);
        cacheService.putExtra("book", book);
        startService(cacheService);
    }

    @Override
    public void onBookDirectory(String _id, String title, int position) {
        Intent intent = new Intent(getApplicationContext(), BookDirectoryActivity.class);
        intent.putExtra("_id", _id);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(getApplicationContext(), ReadBookActivity.class);
        intent.putExtra("book", readBookAdapter.getBook(position));
        startActivity(intent);
    }
}
