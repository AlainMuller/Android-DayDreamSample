package fr.alainmuller.daydreamsample.ui.helper.impl;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.dreams.DreamService;
import android.view.View;
import android.view.WindowManager;

import fr.alainmuller.daydreamsample.ui.helper.SystemUiHelper;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SystemUiHelperImplHC extends SystemUiHelper.SystemUiHelperImpl implements View.OnSystemUiVisibilityChangeListener {
    final View mDecorView;

    public SystemUiHelperImplHC(DreamService dreamService, int level, int flags,
                                SystemUiHelper.OnVisibilityChangeListener onVisibilityChangeListener) {
        super(dreamService, level, flags, onVisibilityChangeListener);

        mDecorView = dreamService.getWindow().getDecorView();
        mDecorView.setOnSystemUiVisibilityChangeListener(this);
    }


    @Override
    public void show() {
        mDecorView.setSystemUiVisibility(createShowFlags());
    }

    @Override
    public void hide() {
        mDecorView.setSystemUiVisibility(createHideFlags());
    }

    @Override
    public final void onSystemUiVisibilityChange(int visibility) {
        if ((visibility & createTestFlags()) != 0) {
            onSystemUiHidden();
        } else {
            onSystemUiShown();
        }
    }

    protected void onSystemUiShown() {
        mDreamService.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setIsShowing(true);
    }

    protected void onSystemUiHidden() {
        mDreamService.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setIsShowing(false);
    }

    protected int createShowFlags() {
        return View.STATUS_BAR_VISIBLE;
    }

    protected int createHideFlags() {
        return View.STATUS_BAR_HIDDEN;
    }

    protected int createTestFlags() {
        return View.STATUS_BAR_HIDDEN;
    }
}
