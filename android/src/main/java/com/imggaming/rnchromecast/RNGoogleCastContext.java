package com.imggaming.rnchromecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.SessionManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * This class needs to be instantiated when the application is created and the
 * {#link startCastStateDiscovery} and {#link stopCastStateDiscovery} need to be called
 * on the MainActivity's Resume/Pause lifecycles
 */
public class RNGoogleCastContext {

    @Nullable
    private static com.imggaming.rnchromecast.RNGoogleCastContext instance;

    @NonNull
    private CastContext castContext;

    private List<WeakReference<CastStateListener>> listeners = new ArrayList<>();

    private CastStateListener castStateListener = new CastStateListener() {
        @Override
        public void onCastStateChanged(int i) {
            for (WeakReference<CastStateListener> weakReference : listeners) {
                CastStateListener listener = weakReference.get();
                if (listener != null) listener.onCastStateChanged(i);
            }
        }
    };

    private RNGoogleCastContext(Context context) {
        castContext = CastContext.getSharedInstance(context);
    }

    @NonNull
    public static com.imggaming.rnchromecast.RNGoogleCastContext getInstance(Context context) {
        if (instance == null) {
            instance = new com.imggaming.rnchromecast.RNGoogleCastContext(context);
        }
        return instance;
    }

    @Nullable
    public static com.imggaming.rnchromecast.RNGoogleCastContext getInstance() {
        return instance;
    }

    public int getCastState() {
        return castContext.getCastState();
    }

    public void addCastStateListener(CastStateListener castStateListener) {
        listeners.add(new WeakReference<CastStateListener>(castStateListener));
    }

    public void removeCastStateListener(CastStateListener castStateListener) {
        WeakReference<CastStateListener> listenerToRemove = null;
        for (WeakReference<CastStateListener> l : listeners) {
            if (l.get() == castStateListener) {
                listenerToRemove = l;
                break;
            }
        }
        if (listenerToRemove != null) {
            listeners.remove(listenerToRemove);
        }
    }

    public void startCastStateDiscovery() {
        castContext.addCastStateListener(castStateListener);
    }

    public void stopCastStateDiscovery() {
        castContext.removeCastStateListener(castStateListener);
    }

    public SessionManager getSessionManager() {
        return castContext.getSessionManager();
    }
}
