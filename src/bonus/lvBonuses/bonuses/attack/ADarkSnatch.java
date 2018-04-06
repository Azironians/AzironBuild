package bonus.lvBonuses.bonuses.attack;

import bonus.bonuses.ExtendedBonus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import managment.actionManagement.service.engine.services.DynamicHandleService;
import managment.actionManagement.service.engine.services.RegularHandleService;
import managment.playerManagement.Player;

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
        actionManager.getEventEngine().handle();
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
                if (actionEvent.getActionType() == ActionType.SHOWED_BONUS
                        && actionEvent.getData().equals("UnstableMight")
                        && actionEvent.getPlayer() == currentPlayer) {
                    timeline.play();
                }
                if (actionEvent.getActionType() == ActionType.USED_BONUS
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
    public HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.DEAL_DAMAGE) {
                    final Object message = actionEvent.getData();
                    final String[] params = message.split(" ");
                    final double damage = Double.parseDouble(params[0]);
                    final String playerID = params[1];
                    final String heroID = params[2];
                }
            }

//            private Hero findVictim(final String playerID, final String heroID) {
//                //find in current team scope:
//                final ATeam currentTeam = playerManager.getCurrentTeam();
//                final Player player = currentTeam.getCurrentPlayer();
//                if (player.getProfile().getName().equals(playerID)) {
//
//                }
//
//
//            }
//
//            private Hero findHero(final Player player, final String heroID) {
//                final Hero mainHero = player.getHero();
//                if (mainHero.getName().equals(heroID)) {
//                    return mainHero;
//                }
//                final List<Hero> otherHeroes = player.getOtherHeroes();
//                for (final Hero hero : otherHeroes) {
//                    if (hero.getName().equals(heroID)){
//                        return mainHero;
//                    }
//                }
//
//            }



            @Override
            public final String getName() {
                return "DynamicDarkSnatch";
            }

            @Override
            public final Player getCurrentPlayer() {
                return;
            }

            @Override
            public boolean isWorking() {
                return false;
            }

            @Override
            public void setWorking(final boolean able) {

            }
        };
    }
}
