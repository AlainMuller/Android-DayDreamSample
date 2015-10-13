package fr.alainmuller.daydreamsample.ui.activity;

import android.graphics.BitmapFactory;
import android.service.dreams.DreamService;
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

public class SlideShowDayDream extends DreamService {

    SlideShowView mSlideShowView;
    View mDecorView;
    SlideShowAdapter mAdapter;

    // OVERRIDE METHODS ****************************************************************************


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

        // Force hiding navbar on older devices
        mSlideShowView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);

        //hideSystemUI();
        startSlideShow();
    }

    @Override
    public void onDetachedFromWindow() {
        if (mAdapter != null) {
            ((GenericBitmapAdapter) mAdapter).shutdown();
        }
        super.onDetachedFromWindow();
    }

    // PRIVATE METHODS *****************************************************************************

    private SlideShowAdapter createRemoteAdapter() {
        String[] slideUrls = new String[]{
                "http://lorempixel.com/1024/768/sports",
                "http://lorempixel.com/1024/768/nature",
                "http://lorempixel.com/1024/768/people",
                "http://lorempixel.com/1024/768/city",
        };
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        mAdapter = new RemoteBitmapAdapter(this, Arrays.asList(slideUrls), opts);
        return mAdapter;
    }

    private void startSlideShow() {
        mAdapter = createRemoteAdapter();
        mSlideShowView.setAdapter(mAdapter);
        // Optional customisation
        mSlideShowView.setTransitionFactory(new SlideAndZoomTransitionFactory());
        // mSlideShowView.setPlaylist(new RandomPlayList());
        mSlideShowView.setOnSlideShowEventListener(slideShowListener);
        mSlideShowView.play();
    }

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

}