package com.dicetechnology.dcchromecast.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.MediaRouteButton;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.google.android.gms.cast.framework.CastButtonFactory;

public class GoogleCastButton extends SimpleViewManager<MediaRouteButton> {
    public static final String REACT_CLASS = "GoogleCastButton";

    private final static String TAG = "GoogleCastButton";
    private ThemedReactContext context;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected MediaRouteButton createViewInstance(ThemedReactContext reactContext) {
        context = reactContext;
        MediaRouteButton mediaRouteButton = new MediaRouteButton(reactContext);
        com.dicetechnology.dcchromecast.ui.GoogleCastButton.setTintColor(reactContext, mediaRouteButton, "#FFFFFF");
        CastButtonFactory.setUpMediaRouteButton(reactContext, mediaRouteButton);
        return mediaRouteButton;
    }

    private static void setTintColor(ThemedReactContext context, final MediaRouteButton mediaRouteButton, String tintColor) {
        try {
            Context castContext = new ContextThemeWrapper(context, android.support.v7.mediarouter.R.style.Theme_MediaRouter);
            TypedArray array = castContext.obtainStyledAttributes(null, android.support.v7.mediarouter.R.styleable.MediaRouteButton, android.support.v7.mediarouter.R.attr.mediaRouteButtonStyle, 0);
            Drawable drawable = array.getDrawable(android.support.v7.mediarouter.R.styleable.MediaRouteButton_externalRouteEnabledDrawable);
            array.recycle();

            DrawableCompat.setTint(drawable, Color.parseColor(tintColor));
            mediaRouteButton.setRemoteIndicatorDrawable(drawable);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
