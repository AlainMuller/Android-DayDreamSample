package fr.alainmuller.daydreamsample;

import android.service.dreams.DreamService;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import fr.alainmuller.daydreamsample.ui.Bouncer;

public class BouncyDreamService extends DreamService {

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        // Our content view will take care of animating its children.
        final Bouncer bouncer = new Bouncer(this);
        bouncer.setLayoutParams(new
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bouncer.setSpeed(200); // pixels/sec

        // Add some views that will be bounced around.
        // Here I'm using ImageViews but they could be any kind of
        // View or ViewGroup, constructed in Java or inflated from
        // resources.
        for (int i = 0; i < 5; i++) {
            final FrameLayout.LayoutParams lp
                    = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.ic_launcher);
            image.setBackgroundColor(0xFF004000);
            bouncer.addView(image, lp);
        }
        setContentView(bouncer);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(true);
        setFullscreen(true);
    }
}
