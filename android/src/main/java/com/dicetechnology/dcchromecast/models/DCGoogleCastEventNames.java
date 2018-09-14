package com.dicetechnology.dcchromecast.models;

public enum DCGoogleCastEventNames {
    DCE_DEVICES_STATE("DCE_DEVICES_STATE"),
    DCE_SESSION_MANAGER_QUEUED_ITEMS("DCE_SESSION_MANAGER_QUEUED_ITEMS");

    private final String text;

    /**
     * @param text
     */
    DCGoogleCastEventNames(final String text) {
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
