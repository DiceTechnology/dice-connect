package com.dicetechnology.dcchromecast.models;

public enum DCGoogleCastPayloadNames {
    NO_DEVICES_AVAILABLE("NO_DEVICES_AVAILABLE"),
    NOT_CONNECTED("NOT_CONNECTED"),
    CONNECTING("CONNECTING"),
    CONNECTED("CONNECTED"),
    VIDEO_STARTING("VIDEO_STARTING"),
    VIDEO_STARTED("VIDEO_STARTED"),
    VIDEO_BUFFERING("VIDEO_BUFFERING"),
    VIDEO_PAUSED("VIDEO_PAUSED"),
    VIDEO_PLAYING("VIDEO_PLAYING"),
    VIDEO_ENDED("VIDEO_ENDED"),
    VIDEO_FAILED("VIDEO_FAILED");
    private final String text;

    /**
     * @param text
     */
    DCGoogleCastPayloadNames(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
