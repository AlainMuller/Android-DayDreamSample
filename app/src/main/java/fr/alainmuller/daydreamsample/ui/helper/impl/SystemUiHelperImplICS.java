package fr.alainmuller.daydreamsample.ui.helper.impl;


import android.annotation.TargetApi;
import android.os.Build;
import android.service.dreams.DreamService;
import android.view.View;

import fr.alainmuller.daydreamsample.ui.helper.SystemUiHelper;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class SystemUiHelperImplICS extends SystemUiHelperImplHC {

    public SystemUiHelperImplICS(DreamService dreamService, int level, int flags,
                                 SystemUiHelper.OnVisibilityChangeListener onVisibilityChangeListener) {
        super(dreamService, level, flags, onVisibilityChangeListener);
    }

    @Override
    protected int createShowFlags() {
        return View.SYSTEM_UI_FLAG_VISIBLE;
    }

    @Override
    protected int createTestFlags() {
        if (mLevel >= SystemUiHelper.LEVEL_LEAN_BACK) {
            // Intentionally override test flags.
            return View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        return View.SYSTEM_UI_FLAG_LOW_PROFILE;
    }

    @Override
    protected int createHideFlags() {
        int flag = View.SYSTEM_UI_FLAG_LOW_PROFILE;

        if (mLevel >= SystemUiHelper.LEVEL_LEAN_BACK) {
            flag |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        return flag;
    }
}
