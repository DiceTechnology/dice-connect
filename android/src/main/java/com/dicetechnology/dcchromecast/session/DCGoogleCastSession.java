package com.dicetechnology.dcchromecast.session;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dicetechnology.dcchromecast.models.DCVideoStreamType;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.MediaQueue;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.images.WebImage;
import com.dicetechnology.devicemanager.DeviceManagerSession;
import com.dicetechnology.dcchromecast.models.DCGoogleCastEventNames;
import com.dicetechnology.dcchromecast.models.DCGoogleCastPayloadNames;
import com.dicetechnology.dcchromecast.utils.EventEmitter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DCGoogleCastSession implements DeviceManagerSession,
        RemoteMediaClient.ProgressListener, SessionManagerListener<Session> {

    private static final String TAG = "DCGoogleCastSession";

    private static final String CUSTOM_DATA_KEY_EVENT_INFO_SERIALISED = "eventInfoSerialized";
    private static final String CUSTOM_DATA_KEY_CHROMECAST_SESSION_SERIALIZED = "chromecastSessionSerialized";

    @Nullable
    private static DCGoogleCastSession instance;

    private int mediaPlayerState = MediaStatus.PLAYER_STATE_UNKNOWN;

    @NonNull
    private EventEmitter eventEmitter;
    @NonNull
    private SessionManager sessionManager;
    @Nullable
    private RemoteMediaClient remoteMediaClient;
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<RemoteMediaClient.ProgressListener> progressListeners = new ArrayList<>();
    private RemoteMediaClient.Callback remoteMediaClientCallback = new RemoteMediaClient.Callback() {

        @Override
        public void onStatusUpdated() {
            super.onStatusUpdated();
            if (remoteMediaClient != null && mediaPlayerState != remoteMediaClient.getPlayerState()) {
                requestQueuedItems();
                switch (remoteMediaClient.getPlayerState()) {
                    case MediaStatus.PLAYER_STATE_UNKNOWN:
                        onProgressUpdated(0, 0);
                        eventEmitter.sendEvent(DCGoogleCastEventNames.DCE_DEVICES_STATE, DCGoogleCastPayloadNames.VIDEO_ENDED);
                        break;
                    case MediaStatus.PLAYER_STATE_IDLE:
                        onProgressUpdated(0, 0);
                        eventEmitter.sendEvent(DCGoogleCastEventNames.DCE_DEVICES_STATE, DCGoogleCastPayloadNames.VIDEO_ENDED);
                        break;
                    case MediaStatus.PLAYER_STATE_BUFFERING:
                        eventEmitter.sendEvent(DCGoogleCastEventNames.DCE_DEVICES_STATE, DCGoogleCastPayloadNames.VIDEO_BUFFERING);
                        break;
                    case MediaStatus.PLAYER_STATE_PAUSED:
                        eventEmitter.sendEvent(DCGoogleCastEventNames.DCE_DEVICES_STATE, DCGoogleCastPayloadNames.VIDEO_PAUSED);
                        break;
                    case MediaStatus.PLAYER_STATE_PLAYING:
                        eventEmitter.sendEvent(DCGoogleCastEventNames.DCE_DEVICES_STATE, DCGoogleCastPayloadNames.VIDEO_PLAYING);
                        break;
                }
                mediaPlayerState = remoteMediaClient.getPlayerState();
            }
        }
    };


    @Nullable
    public static DCGoogleCastSession getInstance() {
        return instance;
    }

    public DCGoogleCastSession(@NonNull SessionManager sessionManager,
                               @NonNull EventEmitter eventEmitter) {
        this.sessionManager = sessionManager;
        this.eventEmitter = eventEmitter;
        sessionManager.removeSessionManagerListener(this);
        sessionManager.addSessionManagerListener(this);
        // The remoteMediaClient is also updated on the SessionManagerListener callbacks
        CastSession castSession = sessionManager.getCurrentCastSession();
        if (castSession != null) {
            remoteMediaClient = castSession.getRemoteMediaClient();
            if (remoteMediaClient != null) {
                remoteMediaClient.unregisterCallback(remoteMediaClientCallback);
                remoteMediaClient.registerCallback(remoteMediaClientCallback);
            }
        }
        instance = this;
    }

    @Override
    public void start(final String url, final DCVideoStreamType videoStreamType, final String videoContentType, @Nullable final ReadableMap metadataMap) {
        handler.post(new Runnable() {
            @Override
            public void run() {

                MediaMetadata metadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                Integer playPosition = 0;
                Integer duration = 0;
                JSONObject eventInfo = new JSONObject();

                if (metadataMap != null) {
                    String title = metadataMap.hasKey("title") ? metadataMap.getString("title") : null;
                    String imageUrl = metadataMap.hasKey("imageUrl") ? metadataMap.getString("imageUrl") : null;
                    duration = metadataMap.hasKey("duration") ? metadataMap.getInt("duration") : 0;
                    duration = duration * 1000;
                    playPosition = metadataMap.hasKey("startPosition") ? metadataMap.getInt("startPosition") : 0;
                    playPosition = playPosition * 1000; // use milliseconds for progress position - startPosition is sent in seconds
                    playPosition = playPosition <= duration ? playPosition : 0;

                    metadata.putString(MediaMetadata.KEY_TITLE, title);
                    metadata.addImage(new WebImage(Uri.parse(imageUrl)));

                    try {
                        eventInfo.put(CUSTOM_DATA_KEY_EVENT_INFO_SERIALISED, metadataMap.getString(CUSTOM_DATA_KEY_EVENT_INFO_SERIALISED));
                        eventInfo.put(CUSTOM_DATA_KEY_CHROMECAST_SESSION_SERIALIZED, metadataMap.getString(CUSTOM_DATA_KEY_CHROMECAST_SESSION_SERIALIZED));
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }

                MediaInfo mediaInfo = new MediaInfo.Builder(url)
                        .setStreamType(videoStreamType == DCVideoStreamType.LIVE ? MediaInfo.STREAM_TYPE_LIVE : MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType(videoContentType)
                        .setMetadata(metadata)
                        .setCustomData(eventInfo)
                        .setStreamDuration(duration)
                        .build();

                MediaLoadOptions mediaLoadOptions = new MediaLoadOptions.Builder()
                        .setAutoplay(true)
                        .setPlayPosition(playPosition)
                        .build();
                if (remoteMediaClient != null) {
                    remoteMediaClient.removeProgressListener(DCGoogleCastSession.this);
                    PendingResult<RemoteMediaClient.MediaChannelResult> result = remoteMediaClient.load(mediaInfo, mediaLoadOptions);
                    result.setResultCallback(new ResultCallback<RemoteMediaClient.MediaChannelResult>() {
                        @Override
                        public void onResult(@NonNull RemoteMediaClient.MediaChannelResult mediaChannelResult) {
                            Status status = mediaChannelResult.getStatus();
                            if (status.isSuccess()) {
                                eventEmitter.sendEvent(DCGoogleCastEventNames.DCE_DEVICES_STATE, DCGoogleCastPayloadNames.VIDEO_STARTED);
                                remoteMediaClient.addProgressListener(DCGoogleCastSession.this, 1000);
                            } else {
                                eventEmitter.sendEvent(DCGoogleCastEventNames.DCE_DEVICES_STATE, DCGoogleCastPayloadNames.VIDEO_FAILED);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void play() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (remoteMediaClient != null) remoteMediaClient.play();
            }
        });
    }

    @Override
    public void pause() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (remoteMediaClient != null) remoteMediaClient.pause();
            }
        });
    }

    @Override
    public void stop() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (remoteMediaClient != null) remoteMediaClient.stop();
            }
        });
    }

    @Override
    public void seek(final int positionMillis) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (remoteMediaClient != null) {
                    remoteMediaClient.seek(positionMillis);
                }
            }
        });
    }

    @Override
    public void seekRelative(final int relativeMillis) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (remoteMediaClient != null) {
                    Long streamPosition = remoteMediaClient.getApproximateStreamPosition();
                    remoteMediaClient.seek(streamPosition + relativeMillis);
                }
            }
        });

    }

    @Override
    public void disconnect() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                sessionManager.endCurrentSession(true);
            }
        });
    }

    public void requestQueuedItems() {
        if (remoteMediaClient != null) {
            // Since at the startup the progress listener would not exist, ensure it is registered
            remoteMediaClient.removeProgressListener(DCGoogleCastSession.this);
            remoteMediaClient.addProgressListener(DCGoogleCastSession.this, 1000);

            WritableArray array = Arguments.createArray();
            if (remoteMediaClient.getCurrentItem() != null &&
                    remoteMediaClient.getCurrentItem().getMedia() != null &&
                    remoteMediaClient.getCurrentItem().getMedia().getMetadata() != null) {
                try {
                    array.pushString(remoteMediaClient.getCurrentItem().getMedia().getCustomData().getString(CUSTOM_DATA_KEY_EVENT_INFO_SERIALISED));
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
            if (remoteMediaClient.getMediaQueue() != null) {
                MediaQueue queue = remoteMediaClient.getMediaQueue();
                for (int i = 0; i < queue.getItemCount(); i++) {
                    MediaQueueItem item = queue.getItemAtIndex(i);
                    if (item != null && item.getMedia() != null && item.getMedia().getMetadata() != null) {
                        try {
                            array.pushString(remoteMediaClient.getCurrentItem().getMedia().getCustomData().getString(CUSTOM_DATA_KEY_EVENT_INFO_SERIALISED));
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                }
            }
            eventEmitter.sendEvent(DCGoogleCastEventNames.DCE_SESSION_MANAGER_QUEUED_ITEMS, array);
        }
    }

    public void registerProgressListener(@NonNull RemoteMediaClient.ProgressListener listener) {
        progressListeners.add(listener);
    }

    /**
     * Implements RemoteMediaClient.ProgressListener
     */
    @Override
    public void onProgressUpdated(long progressMs, long durationMs) {
        for (RemoteMediaClient.ProgressListener listener : progressListeners) {
            listener.onProgressUpdated(progressMs, durationMs);
        }
    }

    /**
     * Implements SessionManagerListener<Session>
     */
    @Override
    public void onSessionStarting(Session session) {
        Log.d(TAG, "onSessionStarting");
    }

    @Override
    public void onSessionStarted(Session session, String sessionId) {
        Log.d(TAG, "onSessionStarted");
        if (session instanceof CastSession) {
            remoteMediaClient = ((CastSession) session).getRemoteMediaClient();
            remoteMediaClient.unregisterCallback(remoteMediaClientCallback);
            remoteMediaClient.registerCallback(remoteMediaClientCallback);
        }
    }

    @Override
    public void onSessionStartFailed(Session session, int error) {
        Log.d(TAG, "onSessionStartFailed");
        if (remoteMediaClient != null) {
            remoteMediaClient.unregisterCallback(remoteMediaClientCallback);
            remoteMediaClient = null;
        }
    }

    @Override
    public void onSessionEnding(Session session) {
        Log.d(TAG, "onSessionEnding");
    }

    @Override
    public void onSessionEnded(Session session, int error) {
        Log.d(TAG, "onSessionEnded: " + error);
        if (remoteMediaClient != null) {
            remoteMediaClient.unregisterCallback(remoteMediaClientCallback);
            remoteMediaClient = null;
        }
    }

    @Override
    public void onSessionResuming(Session session, String sessionId) {
        Log.d(TAG, "onSessionResuming: " + sessionId);
    }

    @Override
    public void onSessionResumed(Session session, boolean wasSuspended) {
        Log.d(TAG, "onSessionResumed");
        if (session instanceof CastSession) {
            remoteMediaClient = ((CastSession) session).getRemoteMediaClient();
            remoteMediaClient.unregisterCallback(remoteMediaClientCallback);
            remoteMediaClient.registerCallback(remoteMediaClientCallback);
        }
    }

    @Override
    public void onSessionResumeFailed(Session session, int error) {
        Log.d(TAG, "onSessionResumeFailed: " + error);
        if (remoteMediaClient != null) {
            remoteMediaClient.unregisterCallback(remoteMediaClientCallback);
            remoteMediaClient = null;
        }
    }

    @Override
    public void onSessionSuspended(Session session, int reason) {
        //  reason: GoogleApiClient.ConnectionCallbacks.CAUSE_*.
        Log.d(TAG, "onSessionSuspended: " + reason);
    }
}
