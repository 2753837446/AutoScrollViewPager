package com.luhao.autoscrollviewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.luhao.autoscroll.AutoBuilder;
import com.luhao.autoscroll.AutoListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        AutoBuilder.Builder(viewPager, list, new AutoListener<String>() {
            //初始化ViewPagerItem
            @Override
            public View initItemView(int position, String o) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(o);
                return textView;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }
        })
                //设置循环开启  默认开启
                .setAutoLoop(true)
                //设置自动循环开启  默认关闭, 循环必须开启
                .setAutomatic(true)
                //设置循环切换时间  默认3000
                .setScrollTime(3000)
                .build();

    }
}
