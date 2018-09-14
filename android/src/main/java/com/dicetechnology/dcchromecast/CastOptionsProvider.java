package com.dicetechnology.dcchromecast;

import android.content.Context;

import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.MediaIntentReceiver;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.reactlibrary.R;

import java.util.Arrays;
import java.util.List;


public class CastOptionsProvider implements OptionsProvider {
    @Override
    public CastOptions getCastOptions(Context appContext) {
        NotificationOptions notificationOptions = new NotificationOptions.Builder()
                .setActions(Arrays.asList(MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK), new int[]{0})
                .build();

        CastMediaOptions mediaOptions = new CastMediaOptions.Builder()
                .setNotificationOptions(notificationOptions)
                .build();

        String appId = appContext.getString(R.string.cast_id);
        if (appId == null || appId.length() == 0) {
            appId = CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID;
        }
        CastOptions castOptions = new CastOptions.Builder()
                .setReceiverApplicationId(appId)
                .setCastMediaOptions(mediaOptions)
                .build();
        return castOptions;
    }

    @Override
    public List<SessionProvider> getAdditionalSessionProviders(Context context) {
        return null;
    }
}