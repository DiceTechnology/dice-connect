package com.imggaming.rnchromecast.ui;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.widget.SeekBar;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.imggaming.rnchromecast.session.RNGoogleCastSession;

public class GoogleCastProgressViewManager extends SimpleViewManager<GoogleCastProgressView> {
    public static final String REACT_CLASS = "GoogleCastProgressView";
    public static final String TAG = "GoogleCastProgressBar";

    private static final String PROP_PROGRESS_CURRENT = "progressCurrent";
    private static final String PROP_PROGRESS_MAX = "progressMax";
    private static final String PROP_PROGRESS_COLOR_HEX = "progressColorHex";
    private static final String PROP_PROGRESS_THUMB_HIDDEN = "progressThumbHidden";
    private static final String PROP_PROGRESS_TIME_HIDDEN = "progressTimeHidden";
    @ColorInt private int tintColor = Integer.MIN_VALUE;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected GoogleCastProgressView createViewInstance(ThemedReactContext reactContext) {
        GoogleCastProgressView progressView = new GoogleCastProgressView(reactContext);
        final RNGoogleCastSession session = RNGoogleCastSession.getInstance();
        if (session != null) {
            session.registerProgressListener(progressView);
            // By default the thumb is visible
            progressView.setThumbAlpha(1);
            // By default progress time is visible
            progressView.setProgressTimeVisible(true);
            progressView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) session.seek(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        return progressView;
    }

    @ReactProp(name = PROP_PROGRESS_CURRENT)
    public void setCurrentProgress(final GoogleCastProgressView progressBar, final int currentProgress) {
        progressBar.setProgress(currentProgress);
    }

    @ReactProp(name = PROP_PROGRESS_MAX)
    public void setMaxProgress(final GoogleCastProgressView progressBar, final int maxProgress) {
        progressBar.setMax(maxProgress);
    }

    @ReactProp(name = PROP_PROGRESS_COLOR_HEX)
    public void setProgressColorHex(final GoogleCastProgressView progressBar, final String tintColor) {
        try {
            setTintColor(progressBar, this.tintColor);
            this.tintColor = Color.parseColor(tintColor);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @ReactProp(name = PROP_PROGRESS_THUMB_HIDDEN)
    public void progressThumbHidden(final GoogleCastProgressView progressBar, final boolean hidden) {
        progressBar.setThumbAlpha(hidden ? 0 : 1);
        if (this.tintColor != Integer.MIN_VALUE) {
            setTintColor(progressBar, this.tintColor);
        }
    }

    @ReactProp(name = PROP_PROGRESS_TIME_HIDDEN)
    public void setProgressTimeHidden(final GoogleCastProgressView progressBar, final boolean hidden) {
        progressBar.setProgressTimeVisible(!hidden);
        if (this.tintColor != Integer.MIN_VALUE) {
            setTintColor(progressBar, this.tintColor);
        }
    }

    private void setTintColor(final GoogleCastProgressView progressBar, @ColorInt int color) {
        try {
            progressBar.setTintColor(color);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }


}
