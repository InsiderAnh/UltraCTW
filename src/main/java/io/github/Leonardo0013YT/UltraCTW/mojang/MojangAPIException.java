package io.github.Leonardo0013YT.UltraCTW.mojang;

public class MojangAPIException extends Exception {

    private static final long serialVersionUID = 7588175957523148088L;
    private Reason reason;

    public MojangAPIException(Reason var1) {
        this.reason = var1;
    }

    public Reason getReason() {
        return this.reason;
    }

    public enum Reason {
        UNKNOWN,
        NOT_PREMIUM,
        RATE_LIMITED,
        SERVER_UNAVAILABLE;

        private Reason() {
        }
    }

}
