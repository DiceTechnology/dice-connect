package com.imggaming.rnchromecast.models;

public enum RNVideoStreamType {
    VOD("VOD"),
    LIVE("LIVE");

    private final String text;

    /**
     * @param text
     */
    RNVideoStreamType(final String text) {
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
