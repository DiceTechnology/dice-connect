package com.imggaming.rnchromecast.utils;

import android.support.annotation.NonNull;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.imggaming.rnchromecast.models.RNGoogleCastEventNames;
import com.imggaming.rnchromecast.models.RNGoogleCastPayloadNames;

public interface EventEmitter {
    void sendEvent(@NonNull RNGoogleCastEventNames eventName, @NonNull RNGoogleCastPayloadNames params);

    void sendEvent(@NonNull RNGoogleCastEventNames eventName, @NonNull WritableMap map);

    void sendEvent(@NonNull RNGoogleCastEventNames eventName, @NonNull WritableArray array);
}