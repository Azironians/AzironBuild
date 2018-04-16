package bonus.lvBonuses.bonuses.attack;

import bonus.bonuses.ExtendedBonus;
import heroes.abstractHero.hero.Hero;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import managment.actionManagement.service.engine.services.RegularHandleService;
import managment.playerManagement.Player;

public final class AUnstableMight extends ExtendedBonus implements RegularHandleService {

    private final Text text;

    private double attackBoost;

    private static final double ATTACK_BOOST = 3;

    private static final double SECOND_FRAME = 1;

    private static final int FRAME_CYCLES = 3;

    private Timeline timeline;

    public AUnstableMight(final String name, final int id, ImageView sprite) {
        super(name, id, sprite);
        this.attackBoost = ATTACK_BOOST;
        this.text = new Text("+ " + attackBoost + " к атаке до \nконца матча");
        installContainer(text);
        installTimeline();
    }

    private void installTimeline(){
        final KeyFrame keyFrame = new KeyFrame(Duration.seconds(SECOND_FRAME), event -> {
            if (attackBoost - 1 >= 0){
                attackBoost--;
                text.setText("+ " + attackBoost + " к атаке до \nконца матча");
            }
        });
        this.timeline = new Timeline(keyFrame);
        this.timeline.setCycleCount(FRAME_CYCLES);
    }

    @Override
    public final void use() {
        final Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        hero.setAttack(hero.getAttack());
    }

    @Override
    public final HandleComponent getRegularHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = player;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.SHOWED_BONUSES
                        && actionEvent.getData().equals("UnstableMight")
                        && actionEvent.getPlayer() == currentPlayer){
                    timeline.play();
                }
                if (actionEvent.getActionType() == ActionType.AFTER_USED_BONUS
                        && actionEvent.getPlayer() == currentPlayer){
                    attackBoost = ATTACK_BOOST;
                    timeline.pause();
                    timeline.setCycleCount(FRAME_CYCLES);
                }
            }

            @Override
            public final String getName() {
                return "UnstableMight";
            }

            @Override
            public final Player getCurrentPlayer() {
                return currentPlayer;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(final boolean able) throws IllegalSwitchOffHandleComponentException {
                throw new IllegalSwitchOffHandleComponentException();
            }
        };
    }
}