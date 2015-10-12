package fr.alainmuller.daydreamsample;

import android.graphics.Color;
import android.service.dreams.DreamService;
import android.widget.TextView;

public class TextViewDreamService extends DreamService {
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(true);
        setFullscreen(true);

        TextView tv = new TextView(this);
        setContentView(tv);
        tv.setText("This is your own Dream Hurray !!");
        tv.setTextColor(Color.rgb(184, 245, 0));
        tv.setTextSize(30);

    }
}