package com.xiaoqqq.l_time.activity;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.base.BaseActivity;
import com.xiaoqqq.l_time.constants.RouterPath;

import java.io.IOException;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.activity
 * @date gift-07-21
 * @describe todo
 */
@Route(path = RouterPath.splashActivity)
public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private MediaPlayer mPlayer;
    private TextView mGoHome;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState, @Nullable final PersistableBundle persistentState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState, persistentState);
        getActionBar().hide();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mSurfaceView = findViewById(R.id.splash_surface_view);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                mPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height) {
                if (null != mPlayer && !mPlayer.isPlaying()) {
                    mPlayer.start();
                }
            }

            @Override
            public void surfaceDestroyed(final SurfaceHolder holder) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
            }
        });
        mHolder.setKeepScreenOn(true);
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSurfaceView.setLayoutParams(layoutParams);
                if (!mPlayer.isPlaying()) {
                    mPlayer.start();
                }
            }
        });

        mGoHome = findViewById(R.id.spalsh_tv_go_home);
    }

    @Override
    protected void initData() {
        super.initData();
        if (checkIsQixi()) {
            playLocalVideo();
        } else {
            ARouter.getInstance().build(RouterPath.mainActivity).navigation();
            finish();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mGoHome.setOnClickListener(this);
    }

    private boolean checkIsQixi() {
        return System.currentTimeMillis() > 1565139600000L && System.currentTimeMillis() < 1565193599000L;
    }

    private void playLocalVideo() {
        try {
            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.qixi_liwu);
            mPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                    file.getLength());
            mPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            mPlayer.setLooping(true);
            mPlayer.prepare();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            mPlayer.release();
        }
        mPlayer = null;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.spalsh_tv_go_home:
                ARouter.getInstance().build(RouterPath.mainActivity).navigation();
                finish();
                break;
        }
    }
}
