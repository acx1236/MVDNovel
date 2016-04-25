package com.ancx.mvdnovel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.util.ImageLoader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends AppCompatActivity {

    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
    }

    private ImageView iv_welcome;

    private void initView() {
        iv_welcome = (ImageView) findViewById(R.id.iv_welcome);
        ImageLoader.display("http://pic41.nipic.com/20140519/10889345_121329527002_2.jpg", iv_welcome, R.mipmap.ic_launcher, R.mipmap.ic_launcher, 0, 0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);
    }

}
