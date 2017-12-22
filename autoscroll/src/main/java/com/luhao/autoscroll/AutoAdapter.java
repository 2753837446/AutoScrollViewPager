package com.luhao.autoscroll;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luhao
 * Date: 2017/12/21.
 */

public class AutoAdapter<T> extends PagerAdapter {

    private final List<T> params;
    private boolean autoLoop;
    private final AutoListener autoListener;
    private List<View> mViews;

    public AutoAdapter(List<T> params, boolean autoLoop, AutoListener autoListener) {
        this.params = params;
        this.autoLoop = autoLoop;
        this.autoListener = autoListener;
        mViews = new ArrayList<>();
        if (autoLoop) {
            mViews.add(autoListener.initItemView(params.size() - 1, params.get(params.size() - 1)));
            for (int i = 0; i < params.size(); i++) {
                mViews.add(autoListener.initItemView(i, params.get(i)));
            }
            mViews.add(autoListener.initItemView(0, params.get(0)));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return autoLoop ? params.size() + 2 : params.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
