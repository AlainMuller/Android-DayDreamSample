package fr.alainmuller.daydreamsample.ui.activity;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.service.dreams.DreamService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.marvinlabs.widget.slideshow.SlideShowAdapter;
import com.marvinlabs.widget.slideshow.SlideShowView;
import com.marvinlabs.widget.slideshow.adapter.GenericBitmapAdapter;
import com.marvinlabs.widget.slideshow.adapter.RemoteBitmapAdapter;
import com.marvinlabs.widget.slideshow.transition.SlideAndZoomTransitionFactory;

import java.util.Arrays;

import fr.alainmuller.daydreamsample.R;
import fr.alainmuller.daydreamsample.ui.helper.SystemUiHelper;

public class SlideShowDayDream extends DreamService {

    SlideShowView mSlideShowView;
    View mDecorView;
    SlideShowAdapter mAdapter;

    // OVERRIDE METHODS ****************************************************************************
    private SlideShowView.OnSlideShowEventListener slideShowListener = new SlideShowView.OnSlideShowEventListener() {
        @Override
        public void beforeSlideShown(SlideShowView parent, int position) {
            Log.d("SlideShowDemo", "OnSlideShowEventListener.beforeSlideShown: " + position);
        }

        @Override
        public void onSlideShown(SlideShowView parent, int position) {
            Log.d("SlideShowDemo", "OnSlideShowEventListener.onSlideShown: " + position);
        }

        @Override
        public void beforeSlideHidden(SlideShowView parent, int position) {
            Log.d("SlideShowDemo", "OnSlideShowEventListener.beforeSlideHidden: " + position);
        }

        @Override
        public void onSlideHidden(SlideShowView parent, int position) {
            Log.d("SlideShowDemo", "OnSlideShowEventListener.onSlideHidden: " + position);
        }
    };

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        setFullscreen(true);
        // TODO : set to true to enable onClickListener
        setInteractive(false);
        setScreenBright(true);

        final LayoutInflater inflater = LayoutInflater.from(this);
        mSlideShowView = (SlideShowView) inflater.inflate(R.layout.activity_daydream, null);
        mSlideShowView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(getClass().getCanonicalName(), "OnClick > slideShow");
                        Toast.makeText(v.getContext(), "Go back to home activity", Toast.LENGTH_LONG).show();
                        // TODO
                        finish();
                    }
                }
        );

        setContentView(mSlideShowView);

        // TODO : remove this call, helper not working =(
        final SystemUiHelper helper = new SystemUiHelper(this, SystemUiHelper.LEVEL_IMMERSIVE, SystemUiHelper.FLAG_IMMERSIVE_STICKY);
        helper.hide();

        // Force IMMERSIVE MODE on Daydream
        mSlideShowView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ? View.SYSTEM_UI_FLAG_IMMERSIVE : 0));

        // Fetch screen height and width, to use as our max size when loading images as this
        // activity runs full screen
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;
        // LoremPixel cap images at 1920px max
        startSlideShow(width > 1920 ? 1920 : width, height > 1920 ? 1920 : height);
    }

    // PRIVATE METHODS *****************************************************************************

    @Override
    public void onDetachedFromWindow() {
        if (mAdapter != null) {
            ((GenericBitmapAdapter) mAdapter).shutdown();
        }
        super.onDetachedFromWindow();
    }

    private SlideShowAdapter createRemoteAdapter(final int width, final int height) {
        Log.d(getClass().getSimpleName(), "createRemoteAdapter > Generating image URLs for screen : " + width + "x" + height);

        String[] slideUrls = new String[]{
                "http://lorempixel.com/" + width + "/" + height + "/sports",
                "http://lorempixel.com/" + width + "/" + height + "/nature",
                "http://lorempixel.com/" + width + "/" + height + "/people",
                "http://lorempixel.com/" + width + "/" + height + "/city",
        };
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        mAdapter = new RemoteBitmapAdapter(this, Arrays.asList(slideUrls), opts);
        return mAdapter;
    }

    private void startSlideShow(final int width, final int height) {
        mAdapter = createRemoteAdapter(width, height);
        mSlideShowView.setAdapter(mAdapter);
        // Optional customisation
        mSlideShowView.setTransitionFactory(new SlideAndZoomTransitionFactory());
        // mSlideShowView.setPlaylist(new RandomPlayList());
        mSlideShowView.setOnSlideShowEventListener(slideShowListener);
        mSlideShowView.play();
    }

}