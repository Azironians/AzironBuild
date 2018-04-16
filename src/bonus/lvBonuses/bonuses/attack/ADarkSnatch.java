package bonus.lvBonuses.bonuses.attack;

import bonus.bonuses.ExtendedBonus;
import heroes.abstractHero.hero.Hero;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.actionManagement.service.engine.services.RegularHandleService;
import managment.playerManagement.Player;

import java.util.HashSet;
import java.util.Set;

public final class ADarkSnatch extends ExtendedBonus implements RegularHandleService, DynamicHandleService {

    private final Text text;

    private double attackBoostCoefficient;

    private static final double ATTACK_BOOST_COEFFICIENT = 1.20;

    private static final double SECOND_FRAME = 1;

    private static final int FRAME_CYCLES = 20;

    private Timeline timeline;

    public ADarkSnatch(final String name, final int id, ImageView sprite) {
        super(name, id, sprite);
        this.attackBoostCoefficient = ATTACK_BOOST_COEFFICIENT;
        this.text = new Text("Увеличивает наносимый урон\nна этом ходу на" + (attackBoostCoefficient - 1) + "%");
        installContainer(text);
        installTimeline();
    }

    private void installTimeline() {
        final KeyFrame keyFrame = new KeyFrame(Duration.seconds(SECOND_FRAME), event -> {
            if (attackBoostCoefficient - 0.01 >= 0) {
                attackBoostCoefficient -= 0.01;
                text.setText("Увеличивает наносимый урон\nна этом ходу\n" +
                        "на" + (attackBoostCoefficient - 1) + "%");
            }
        });
        this.timeline = new Timeline(keyFrame);
        this.timeline.setCycleCount(FRAME_CYCLES);
    }

    @Override
    public final void use() {
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    @Override
    public final HandleComponent getRegularHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player currentPlayer = player;

            @Override
            public final void setup() {
                this.currentPlayer = player;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.SHOWED_BONUSES
                        && actionEvent.getData().equals("UnstableMight")
                        && actionEvent.getPlayer() == currentPlayer) {
                    timeline.play();
                }
                if (actionEvent.getActionType() == ActionType.AFTER_USED_BONUS
                        && actionEvent.getPlayer() == currentPlayer) {
                    attackBoostCoefficient = ATTACK_BOOST_COEFFICIENT;
                    timeline.pause();
                    timeline.setCycleCount(FRAME_CYCLES);
                }
            }

            @Override
            public final String getName() {
                return "RegularDarkSnatch";
            }

            @Override
            public Player getCurrentPlayer() {
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

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private Set<Hero> victims;

            private boolean isWorking;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.victims = new HashSet<>();
                this.isWorking = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                switch (actionEvent.getActionType()){
                    case BEFORE_DEAL_DAMAGE:
                        final Pair<Hero, Double> heroVsDamage = (Pair) actionEvent.getData();
                        final Hero victim = heroVsDamage.getKey();
                        double damageCoefficientBoost = victim.getDamageCoefficient() * ATTACK_BOOST_COEFFICIENT;
                        victim.setDamageCoefficient(damageCoefficientBoost);
                        this.victims.add(victim);
                        break;
                    case AFTER_DEAL_DAMAGE:
                        this.victims.forEach(hero -> {
                            final double damageCoefficient = hero.getDamageCoefficient();
                            hero.setDamageCoefficient(damageCoefficient / ATTACK_BOOST_COEFFICIENT);
                        });
                        this.victims.clear();
                        break;
                    case END_TURN:
                        this.isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "DynamicDarkSnatch";
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}