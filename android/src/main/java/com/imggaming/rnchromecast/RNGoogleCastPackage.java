package com.imggaming.rnchromecast;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.List;

public class RNGoogleCastPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        RNGoogleCastContext castContext = RNGoogleCastContext.getInstance();
        if (castContext == null)
            throw new NullPointerException("RNGoogleCastContext needs to be initialised using the RNGoogleCastContext.getInstance(Context context) method");
        return Arrays.<NativeModule>asList(new RNGoogleCastModule(reactContext, castContext));
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.asList();
    }
}