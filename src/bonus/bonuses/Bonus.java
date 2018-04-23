package bonus.bonuses;

import javafx.animation.ScaleTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import managment.actionManagement.ActionManager;
import managment.actionManagement.actions.ActionEvent;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public abstract class Bonus {
    protected final String name;

    private final int id;

    protected ImageView sprite;

    protected ActionManager actionManager;

    protected BattleManager battleManager;

    protected PlayerManager playerManager;

    public Bonus(final String name, final int id, final ImageView sprite) {
        this.name = name;
        this.id = id;
        this.actionEvents = new ArrayList<>();
        sprite.setFitHeight(353.0);
        sprite.setFitWidth(309.0);
        sprite.setOnMouseClicked(event -> actionManager.setBonusRequest(this));
        sprite.setOnMouseEntered(event -> increaseBonusEntered());
        sprite.setOnMouseExited(event -> decreaseBonusExited());
        this.sprite = sprite;
    }

    public abstract void use();

    protected final List<ActionEvent> actionEvents;

    //Animation:
    private void increaseBonusEntered(){
        this.sprite.toFront();
        scaleBonus(this.sprite, 1.8);
    }

    private void decreaseBonusExited(){
        this.sprite.toBack();
        scaleBonus(this.sprite, 1);
    }

    private void scaleBonus(final ImageView imageView, final double scale){
        final ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), imageView);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        scaleTransition.play();
    }

    public int getId() {
        return id;
    }

    @Override
    public final String toString() {
        return "Bonus{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", sprite=" + sprite +
                '}';
    }

    public final List<ActionEvent> getActionEvents() {
        return actionEvents;
    }

    public final String getName() {
        return name;
    }

    public final ImageView getSprite() {
        return sprite;
    }

    public final void setActionManager(final ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    public final void setBattleManager(final BattleManager battleManager) {
        this.battleManager = battleManager;
    }

    public final void setPlayerManager(final PlayerManager playerManager) {
        this.playerManager = playerManager;
    }
}
