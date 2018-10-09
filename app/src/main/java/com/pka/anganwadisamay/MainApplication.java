package com.pka.anganwadisamay;

import android.app.Application;
import android.content.Context;

import com.pka.anganwadisamay.Helper.LocaleHelper;

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"en"));
    }
}
