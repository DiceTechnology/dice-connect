package com.dicetechnology.dcchromecast;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.List;

public class DCGoogleCastPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        DCGoogleCastContext castContext = DCGoogleCastContext.getInstance();
        if (castContext == null)
            throw new NullPointerException("DCGoogleCastContext needs to be initialised using the DCGoogleCastContext.getInstance(Context context) method");
        return Arrays.<NativeModule>asList(new DCGoogleCastModule(reactContext, castContext));
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.asList();
    }
}