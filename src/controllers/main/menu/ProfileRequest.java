package controllers.main.menu;

public enum ProfileRequest {
    PRIMARY_LEFT, SECONDARY_LEFT,
    PRIMARY_RIGHT, SECONDARY_RIGHT;

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    private boolean isAuthorized = false;
}
