package fr.alainmuller.daydreamsample.ui.helper.impl;


import android.annotation.TargetApi;
import android.os.Build;
import android.service.dreams.DreamService;
import android.view.View;

import fr.alainmuller.daydreamsample.ui.helper.SystemUiHelper;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SystemUiHelperImplJB extends SystemUiHelperImplICS {

    public SystemUiHelperImplJB(DreamService dreamService, int level, int flags,
                                SystemUiHelper.OnVisibilityChangeListener onVisibilityChangeListener) {
        super(dreamService, level, flags, onVisibilityChangeListener);
    }

    @Override
    protected int createShowFlags() {
        int flag = super.createShowFlags();

        if (mLevel >= SystemUiHelper.LEVEL_HIDE_STATUS_BAR) {
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

            if (mLevel >= SystemUiHelper.LEVEL_LEAN_BACK) {
                flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            }
        }

        return flag;
    }

    @Override
    protected int createHideFlags() {
        int flag = super.createHideFlags();

        if (mLevel >= SystemUiHelper.LEVEL_HIDE_STATUS_BAR) {
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;

            if (mLevel >= SystemUiHelper.LEVEL_LEAN_BACK) {
                flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            }
        }

        return flag;
    }

    @Override
    protected void onSystemUiShown() {
        setIsShowing(true);
    }

    @Override
    protected void onSystemUiHidden() {
        setIsShowing(false);
    }
}