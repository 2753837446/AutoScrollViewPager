package com.luhao.autoscroll;

import android.view.View;

/**
 * Created by luhaot
 * Date: 2017/12/21.
 */

public interface AutoListener<T> {

    /**
     * 初始化view
     *
     * @param position
     * @return
     */
     View initItemView(int position, T t);

    /**
     * ViewPager页面滑动事件状态改变
     *
     * @param state
     */
    void onPageScrollStateChanged(int state);

    /**
     * ViewPager页面滑动中
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    /**
     * ViewPager页面被选中
     *
     * @param position
     */
    void onPageSelected(int position);
}
