package com.example;

import android.app.Application;

import com.brentvatne.react.ReactVideoPackage;
import com.dicetechnology.dcchromecast.DCGoogleCastContext;
import com.dicetechnology.dcchromecast.DCGoogleCastPackage;
import com.dicetechnology.dcchromecast.ui.GoogleCastPackage;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.horcrux.svg.SvgPackage;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            DCGoogleCastContext.getInstance(getApplicationContext());
            return Arrays.asList(
                    new MainReactPackage(),
                    new DCGoogleCastPackage(),
                    new GoogleCastPackage(),
                    new SvgPackage(),
                    new ReactVideoPackage()
            );
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
    }
}
