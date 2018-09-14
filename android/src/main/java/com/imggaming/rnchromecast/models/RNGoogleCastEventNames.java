package com.imggaming.rnchromecast.models;

public enum RNGoogleCastEventNames {
    DCE_DEVICES_STATE("DCE_DEVICES_STATE"),
    DCE_SESSION_MANAGER_QUEUED_ITEMS("DCE_SESSION_MANAGER_QUEUED_ITEMS");

    private final String text;

    /**
     * @param text
     */
    RNGoogleCastEventNames(final String text) {
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
