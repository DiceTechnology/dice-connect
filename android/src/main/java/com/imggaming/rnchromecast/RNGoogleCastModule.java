package com.imggaming.rnchromecast;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.SessionManager;
import com.imggaming.rnchromecast.models.RNGoogleCastEventNames;
import com.imggaming.rnchromecast.models.RNGoogleCastPayloadNames;
import com.imggaming.rnchromecast.models.RNVideoStreamType;
import com.imggaming.rnchromecast.session.RNGoogleCastSession;
import com.imggaming.rnchromecast.utils.EventEmitter;


public class RNGoogleCastModule extends ReactContextBaseJavaModule implements EventEmitter {
    private final static String TAG = "RNGoogleCastModule";

    @NonNull
    private ReactApplicationContext reactContext;
    @NonNull
    RNGoogleCastContext rnGoogleCastContext;

    @Nullable
    private RNGoogleCastSession castSession;

    private final CastStateListener castStateListener = new CastStateListener() {
        @Override
        public void onCastStateChanged(int i) {
            RNGoogleCastPayloadNames state = convertToRNGoogleCastStates(i);
            sendEvent(RNGoogleCastEventNames.DCE_DEVICES_STATE, state);
        }
    };

    public RNGoogleCastModule(@NonNull final ReactApplicationContext reactContext, @NonNull final RNGoogleCastContext rnGoogleCastContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.rnGoogleCastContext = rnGoogleCastContext;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                SessionManager sessionManager = rnGoogleCastContext.getSessionManager();
                castSession = new RNGoogleCastSession(sessionManager, com.imggaming.rnchromecast.RNGoogleCastModule.this);
            }
        });
        rnGoogleCastContext.addCastStateListener(castStateListener);
    }

    @Override
    public String getName() {
        return "RNGoogleCast";
    }

    @ReactMethod
    public void getState(final Callback callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                RNGoogleCastPayloadNames state = convertToRNGoogleCastStates(rnGoogleCastContext.getCastState());
                callback.invoke(null, state.toString());
            }
        });
    }

    @ReactMethod
    public void requestQueuedItems() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (castSession != null) {
                    castSession.requestQueuedItems();
                }
            }
        });
    }

    /**
     * @param url           url to play
     * @param rawStreamType Must be a string corresponding to RNVideoStreamType enum type
     * @param contentType   the content type of the video (i.e. application/vnd.apple.mpegurl)
     */
    @ReactMethod
    public void start(final String url, final String rawStreamType, final String contentType, @Nullable final ReadableMap metadata) {
        RNVideoStreamType streamType = RNVideoStreamType.valueOf(rawStreamType);
        if (castSession != null) {
            castSession.start(url, streamType, contentType, metadata);
        }
    }

    @ReactMethod
    public void play() {
        if (castSession != null) castSession.play();
    }

    @ReactMethod
    public void pause() {
        if (castSession != null) castSession.pause();
    }

    @ReactMethod
    public void stop() {
        if (castSession != null) castSession.stop();
    }

    @ReactMethod
    public void seek(final int positionMillis, boolean relative) {
        if (castSession != null) {
            if (relative) castSession.seekRelative(positionMillis);
            else castSession.seek(positionMillis);
        }
    }

    @ReactMethod
    public void disconnect() {
        if (castSession != null) castSession.disconnect();
    }


    /*
     * Implements EventEmitter
     */
    @Override
    public void sendEvent(@NonNull RNGoogleCastEventNames eventName, @NonNull RNGoogleCastPayloadNames params) {
        if (reactContext.hasActiveCatalystInstance()) {
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName.toString(), params.toString());
        }
    }

    @Override
    public void sendEvent(@NonNull RNGoogleCastEventNames eventName, @NonNull WritableMap map) {
        if (reactContext.hasActiveCatalystInstance()) {
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName.toString(), map);
        }
    }

    @Override
    public void sendEvent(@NonNull RNGoogleCastEventNames eventName, @NonNull WritableArray array) {
        if (reactContext.hasActiveCatalystInstance()) {
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName.toString(), array);
        }
    }

    /*
     * Private Methods
     */

    @NonNull
    private static RNGoogleCastPayloadNames convertToRNGoogleCastStates(int castState) {
        switch (castState) {
            case CastState.NO_DEVICES_AVAILABLE:
                return RNGoogleCastPayloadNames.NO_DEVICES_AVAILABLE;
            case CastState.NOT_CONNECTED:
                return RNGoogleCastPayloadNames.NOT_CONNECTED;
            case CastState.CONNECTING:
                return RNGoogleCastPayloadNames.CONNECTING;
            case CastState.CONNECTED:
                return RNGoogleCastPayloadNames.CONNECTED;
            default:
                return RNGoogleCastPayloadNames.NO_DEVICES_AVAILABLE;
        }
    }
}

