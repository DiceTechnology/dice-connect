package com.dicetechnology.devicemanager;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.dicetechnology.dcchromecast.models.DCVideoStreamType;

public interface DeviceManagerSession {
    void start(final String url, final DCVideoStreamType videoStreamType, final String videoContentType, @Nullable final ReadableMap metadataMap);

    void play();

    void pause();

    void stop();

    void seek(int positionMillis);

    void seekRelative(int relativeMillis);

    void disconnect();
}
