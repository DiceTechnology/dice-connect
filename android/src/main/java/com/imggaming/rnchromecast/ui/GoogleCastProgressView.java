package com.imggaming.rnchromecast.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.reactlibrary.R;

import java.util.Locale;

public class GoogleCastProgressView extends FrameLayout implements RemoteMediaClient.ProgressListener {
    private TextView currentTimeTextView;
    private TextView durationTextView;
    private AppCompatSeekBar progressBar;
    private Drawable thumbDrawable;

    public GoogleCastProgressView(Context context) {
        super(context);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        View view = inflate(context, R.layout.rngooglecast_progress_view, null);
        view.setLayoutParams(params);
        progressBar = (AppCompatSeekBar) view.findViewById(R.id.rnGoogleCastProgressBar);
        currentTimeTextView = (TextView) view.findViewById(R.id.rnGoogleCastCurrentTimeTextView);
        durationTextView = (TextView) view.findViewById(R.id.rnGoogleCastDurationTextView);
        thumbDrawable = progressBar.getThumb();
        addView(view);
    }

    /**
     * Implements RemoteMediaClient.ProgressListener
     */
    @Override
    public void onProgressUpdated(long progressMs, long durationMs) {
        Log.d("CHECK", String.format("%d", progressMs));
        setMax((int) durationMs);
        setProgress((int) progressMs);

        if (durationMs != C.TIME_UNSET && durationTextView != null) {
            double durationSecs = Math.floor(durationMs / 1000);
            int secs = (int) (durationSecs % 60);
            int mins = (int) ((durationSecs / 60) % 60);
            int hours = (int) ((durationSecs / (60 * 60)) % 24);
            String durationString = "";
            if (hours > 0) {
                durationString = String.format(Locale.UK, "%02d:%02d:%02d", hours, mins, secs);
            } else {
                durationString = String.format(Locale.UK, "%02d:%02d", mins, secs);
            }

            durationTextView.setText(durationString);
        }

        if (progressMs != C.TIME_UNSET && currentTimeTextView != null) {
            double progressSecs = Math.floor(progressMs / 1000);
            int secs = (int) (progressSecs % 60);
            int mins = (int) ((progressSecs / 60) % 60);
            int hours = (int) ((progressSecs / (60 * 60)) % 24);
            String currentString = "";
            if (hours > 0) {
                currentString = String.format(Locale.UK, "%02d:%02d:%02d", hours, mins, secs);
            } else {
                currentString = String.format(Locale.UK, "%02d:%02d", mins, secs);
            }
            currentTimeTextView.setText(currentString);
        }
    }

    public void setTintColor(@ColorInt int color) {
        if (progressBar.getThumb() != null) {
            progressBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setProgressTintList(ColorStateList.valueOf(color));
            progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        } else {
            Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            progressBar.setProgressDrawable(progressDrawable);

        }
    }

    public void setProgressTimeVisible(boolean visible) {
        @IntegerRes int visibility = visible ? VISIBLE : GONE;
        durationTextView.setVisibility(visibility);
        currentTimeTextView.setVisibility(visibility);

    }

    public void setMax(int max) {
        progressBar.setMax(max);
    }

    public void setProgress(int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, true);
        } else {
            progressBar.setProgress(progress);
        }
    }

    public void setThumbAlpha(int alpha) {
        boolean hide = alpha == 0;
        int padding = getResources().getDimensionPixelSize(R.dimen.rnchromecast_progress_thumb_size) / 2;
        if (hide) {
            progressBar.setThumb(null);
            progressBar.setPadding(0, 0, 0, 0);
        } else {
            progressBar.setThumb(thumbDrawable);
            progressBar.setPadding(padding, 0, padding, 0);
        }
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener listener) {
        progressBar.setOnSeekBarChangeListener(listener);
    }

}
