package fr.alainmuller.daydreamsample.ui;

import android.graphics.BitmapFactory;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.marvinlabs.widget.slideshow.SlideShowAdapter;
import com.marvinlabs.widget.slideshow.SlideShowView;
import com.marvinlabs.widget.slideshow.adapter.GenericBitmapAdapter;
import com.marvinlabs.widget.slideshow.adapter.RemoteBitmapAdapter;

import java.util.Arrays;

import fr.alainmuller.daydreamsample.R;

/**
 * Not really an Activity but just pretend it is one
 * <p>
 * Where onCreate is onDreamingStarted
 * Where onPause is onDreamingStopped
 */
public class DayDreamActivity extends DreamService {

    SlideShowView mSlideShowView;
    private SlideShowAdapter mAdapter;

    // OVERRIDE METHODS ****************************************************************************


    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        // Removed the status bar
        setFullscreen(true);
        // TODO : set to true to enable onClickListener
        setInteractive(false);
        setScreenBright(true);

        // Acts just like an Activity
        setContentView(R.layout.activity_daydream);
        mSlideShowView = (SlideShowView) findViewById(R.id.slideshow);
        mSlideShowView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Go back to home activity", Toast.LENGTH_LONG).show();
                        // TODO
                        finish();
                    }
                }
        );

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
                "http://lorempixel.com/1280/720/sports",
                "http://lorempixel.com/1280/720/nature",
                "http://lorempixel.com/1280/720/people",
                "http://lorempixel.com/1280/720/city",
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
        // mSlideShowView.setTransitionFactory(new RandomTransitionFactory());
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