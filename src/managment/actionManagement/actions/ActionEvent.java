package managment.actionManagement.actions;

import managment.playerManagement.Player;

public final class ActionEvent {

    private final ActionType actionType;
    private final Player player;
    private Object data; //everything type of data

    public ActionEvent(final ActionType actionType, final Player player, final Object data) {
        this.actionType = actionType;
        this.player = player;
        this.data = data;
    }

    public ActionEvent(final ActionType actionType, final Player player) {
        this.actionType = actionType;
        this.player = player;
    }

    public final ActionType getActionType() {
        return actionType;
    }

    public final Player getPlayer() {
        return player;
    }

    public final Object getData() {
        return data;
    }
}