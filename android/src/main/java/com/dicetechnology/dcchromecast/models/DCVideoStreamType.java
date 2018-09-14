package com.dicetechnology.dcchromecast.models;

public enum DCVideoStreamType {
    VOD("VOD"),
    LIVE("LIVE");

    private final String text;

    /**
     * @param text
     */
    DCVideoStreamType(final String text) {
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
