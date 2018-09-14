package com.dicetechnology.dcchromecast.utils;

import android.support.annotation.NonNull;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.dicetechnology.dcchromecast.models.DCGoogleCastEventNames;
import com.dicetechnology.dcchromecast.models.DCGoogleCastPayloadNames;

public interface EventEmitter {
    void sendEvent(@NonNull DCGoogleCastEventNames eventName, @NonNull DCGoogleCastPayloadNames params);

    void sendEvent(@NonNull DCGoogleCastEventNames eventName, @NonNull WritableMap map);

    void sendEvent(@NonNull DCGoogleCastEventNames eventName, @NonNull WritableArray array);
}