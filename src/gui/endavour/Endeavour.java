package gui.endavour;

import org.jetbrains.annotations.Contract;

public final class Endeavour {
    private final String message;
    private final boolean isSuccessful;

    public Endeavour(final String message, final boolean isSuccessful) {
        this.message = message;
        this.isSuccessful = isSuccessful;
    }

    @Contract(pure = true)
    public final String getMessage() {
        return message;
    }

    @Contract(pure = true)
    public final boolean isSuccessful() {
        return isSuccessful;
    }
}
