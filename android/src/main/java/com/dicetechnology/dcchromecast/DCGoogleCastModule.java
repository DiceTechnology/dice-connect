package com.dicetechnology.dcchromecast;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dicetechnology.dcchromecast.session.DCGoogleCastSession;
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
import com.dicetechnology.dcchromecast.models.DCGoogleCastEventNames;
import com.dicetechnology.dcchromecast.models.DCGoogleCastPayloadNames;
import com.dicetechnology.dcchromecast.models.DCVideoStreamType;
import com.dicetechnology.dcchromecast.utils.EventEmitter;


public class DCGoogleCastModule extends ReactContextBaseJavaModule implements EventEmitter {
    private final static String TAG = "DCGoogleCastModule";

    @NonNull
    private ReactApplicationContext reactContext;
    @NonNull
    DCGoogleCastContext rnGoogleCastContext;

    @Nullable
    private DCGoogleCastSession castSession;

    private final CastStateListener castStateListener = new CastStateListener() {
        @Override
        public void onCastStateChanged(int i) {
            DCGoogleCastPayloadNames state = convertToRNGoogleCastStates(i);
            sendEvent(DCGoogleCastEventNames.DCE_DEVICES_STATE, state);
        }
    };

    public DCGoogleCastModule(@NonNull final ReactApplicationContext reactContext, @NonNull final DCGoogleCastContext rnGoogleCastContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.rnGoogleCastContext = rnGoogleCastContext;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                SessionManager sessionManager = rnGoogleCastContext.getSessionManager();
                castSession = new DCGoogleCastSession(sessionManager, DCGoogleCastModule.this);
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
                DCGoogleCastPayloadNames state = convertToRNGoogleCastStates(rnGoogleCastContext.getCastState());
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
     * @param rawStreamType Must be a string corresponding to DCVideoStreamType enum type
     * @param contentType   the content type of the video (i.e. application/vnd.apple.mpegurl)
     */
    @ReactMethod
    public void start(final String url, final String rawStreamType, final String contentType, @Nullable final ReadableMap metadata) {
        DCVideoStreamType streamType = DCVideoStreamType.valueOf(rawStreamType);
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
    public void sendEvent(@NonNull DCGoogleCastEventNames eventName, @NonNull DCGoogleCastPayloadNames params) {
        if (reactContext.hasActiveCatalystInstance()) {
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName.toString(), params.toString());
        }
    }

    @Override
    public void sendEvent(@NonNull DCGoogleCastEventNames eventName, @NonNull WritableMap map) {
        if (reactContext.hasActiveCatalystInstance()) {
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName.toString(), map);
        }
    }

    @Override
    public void sendEvent(@NonNull DCGoogleCastEventNames eventName, @NonNull WritableArray array) {
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
    private static DCGoogleCastPayloadNames convertToRNGoogleCastStates(int castState) {
        switch (castState) {
            case CastState.NO_DEVICES_AVAILABLE:
                return DCGoogleCastPayloadNames.NO_DEVICES_AVAILABLE;
            case CastState.NOT_CONNECTED:
                return DCGoogleCastPayloadNames.NOT_CONNECTED;
            case CastState.CONNECTING:
                return DCGoogleCastPayloadNames.CONNECTING;
            case CastState.CONNECTED:
                return DCGoogleCastPayloadNames.CONNECTED;
            default:
                return DCGoogleCastPayloadNames.NO_DEVICES_AVAILABLE;
        }
    }
}

