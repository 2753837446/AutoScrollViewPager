# AutoScrollViewPager

### 最方便的可自动无限循环ViewPager插件, 完全适用于ViewPager

#### 使用方法
```
AutoBuilder.Builder(viewPager, list, new SimpleAutoListener<String>() {
            //初始化ViewPagerItem
            @Override
            public View initItemView(int position, String o) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(o);
                return textView;
            }
        })
                //设置循环开启  默认开启
                .setAutoLoop(true)
                //设置自动循环开启  默认关闭, 循环必须开启
                .setAutomatic(true)
                //设置循环切换时间  默认3000
                .setScrollTime(3000)
                .build();
```

### SimpleAutoListener
适合不需要关注Viewpager页面切换开发者使用

### 需要ViewPager切换事件可使用AutoListener
```
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
```

>后续预计更新, 添加多样式ViewPager支持, 打造炫酷的切换动画
>添加小圆点支持
