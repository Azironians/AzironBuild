package bonus.general;

import bonus.bonuses.Bonus;
import bonus.bonuses.HandlerBonus;
import heroes.abstractHero.hero.AHero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class SGiveFire extends Bonus implements HandlerBonus {

    private static final Logger log = LoggerFactory.getLogger(SGiveFire.class);

    private static final double COEFFICIENT_BOOST = 0.2;

    public SGiveFire(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final AHero hero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
        final List<AHero.Skill> skills = hero.getCollectionOfSkills();
        for (final AHero.Skill skill : skills) {
            final List<Double> coefficients = skill.getCoefficients();
            final List<Double> newCoefficients = new ArrayList<>();
            for (final double coefficient : coefficients) {
                newCoefficients.add(coefficient * (1 + COEFFICIENT_BOOST));
            }
            skill.setCoefficients(newCoefficients);
        }
        log.info("SKILL POWER INCREASED BY 20%");
        final GetAHandler handler = getHandlerInstance();
        actionManager.getBonusEventEngine().addHandler(handler);
    }

    @Override
    public final GetAHandler getHandlerInstance() {
        return new GetAHandler() {

            private Player player;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.END_TURN) {
                    final AHero hero = playerManager.getCurrentTeam().getCurrentPlayer().getHero();
                    final List<AHero.Skill> skills = hero.getCollectionOfSkills();
                    for (final AHero.Skill skill : skills) {
                        final List<Double> coefficients = skill.getCoefficients();
                        final List<Double> newCoefficients = new ArrayList<>();
                        for (final double coefficient : coefficients) {
                            newCoefficients.add(coefficient / (1 + COEFFICIENT_BOOST));
                        }
                        skill.setCoefficients(newCoefficients);
                    }
                    isWorking = false;
                    log.info("SKILL POWER DECREASED BY 20%");
                }
            }

            @Override
            public final String getName() {
                return "GiveFire";
            }

            @Override
            public final Player getPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setAble(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}
