package com.luhao.autoscroll;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.List;

/**
 * @author  by luhao
 * Date: 2017/12/21.
 */

public class AutoBuilder<T> {

    private static final String TAG = "AutoBuilder";
    private final ViewPager mViewPager;
    private final AutoListener<T> mAutoListener;
    private boolean autoLoop = true;
    private boolean automatic = false;
    private long scrollTime = 3000;
    private List<T> mParams;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    mHandler.sendEmptyMessageDelayed(100, scrollTime);
                }
            }
        }
    };

    public static <T> AutoBuilder Builder(ViewPager viewPager, List<T> params, AutoListener<T> autoListener) {
        return new AutoBuilder<>(viewPager, params, autoListener);
    }

    private AutoBuilder(ViewPager viewPager, List<T> params, AutoListener<T> autoListener) {
        mViewPager = viewPager;
        mAutoListener = autoListener;
        mParams = params;
    }


    /**
     * 是否开启循环, 默认开启
     */
    public AutoBuilder<T> setAutoLoop(boolean autoLoop) {
        this.autoLoop = autoLoop;
        if (!autoLoop) {
            this.automatic = false;
        }
        return this;
    }

    /**
     * 是否开启自动轮播
     *
     * @param automatic 是否开启
     * @return
     */
    public AutoBuilder<T> setAutomatic(boolean automatic) {
        this.automatic = automatic;
        if (automatic) {
            this.autoLoop = true;
        }
        return this;
    }

    /**
     * 设置轮播时间
     *
     * @param scrollTime 轮播时间
     * @return
     */
    public AutoBuilder<T> setScrollTime(long scrollTime) {
        this.scrollTime = scrollTime;
        this.automatic = true;
        this.autoLoop = true;
        return this;
    }

    public void build() {
        if (mViewPager == null) {
            Log.d(TAG, "ViewPager can not be null");
            return;
        }
        if (mAutoListener == null) {
            Log.d(TAG, "AutoListener can not be null");
        }
        if (mParams == null) {
            Log.d(TAG, "Params can not be null or empty");
        }
        if (mParams.size() == 1) {
            autoLoop = false;
            automatic = false;
        }
        mViewPager.addOnPageChangeListener(onPageChangeListener);
        mViewPager.setAdapter(new AutoAdapter<>(mParams, autoLoop, mAutoListener));
        if (mParams.size() > 1 && autoLoop) {
            mViewPager.setCurrentItem(1, false);
            if (automatic) {
                mHandler.sendEmptyMessageDelayed(100, scrollTime);
            }
        }
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        int nextPage = 127;
        boolean smoothScroll = true;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mAutoListener.onPageScrolled(getRealPosition(position), positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            if (autoLoop) {
                if (position == 0) {
                    nextPage = mParams.size();
                    smoothScroll = false;
                }
                if (position == mParams.size() + 1) {
                    nextPage = 1;
                    smoothScroll = false;
                }
            }
            mAutoListener.onPageSelected(getRealPosition(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 0 && autoLoop && nextPage != 127) {
                mViewPager.setCurrentItem(nextPage, smoothScroll);
                nextPage = 127;
                if (automatic) {
                    mHandler.sendEmptyMessageDelayed(100, scrollTime);
                }
            } else {
                if (automatic) {
                    mHandler.removeMessages(100);
                }
            }
            mAutoListener.onPageScrollStateChanged(state);
        }

        int getRealPosition(int position) {
            int realPosition = 0;
            if (autoLoop) {
                if (position == 0) {
                    realPosition = mParams.size() - 1;
                } else if (position == mParams.size() + 1) {
                    realPosition = 0;
                } else {
                    realPosition = position - 1;
                }
            } else {
                realPosition = position;
            }
            return realPosition;
        }
    };

    private void runAtUIThread(Runnable run) {
        if (null == run) {
            return;
        }
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            run.run();
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(run);
        }
    }

}
