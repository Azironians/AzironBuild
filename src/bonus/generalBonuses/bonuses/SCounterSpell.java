package bonus.generalBonuses.bonuses;

import bonus.bonuses.Bonus;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.DynamicHandleService;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class SCounterSpell extends Bonus implements DynamicHandleService {

    private static final Logger log = LoggerFactory.getLogger(SCounterSpell.class);

    public SCounterSpell(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final Hero opponentHero = opponentPlayer.getHero();
        final List<Skill> opponentSkills = opponentHero.getCollectionOfSkills();

        for (final Skill skill : opponentSkills) {
            skill.setSkillAccess(false);
        }
        log.info("COUNTER SPELL IS ACTIVATED");
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private boolean isWorking = true;

            private Player player;

            private Player opponent;

            private Player alternativeOpponent;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponent = playerManager.getOpponentATeam().getCurrentPlayer();
                this.alternativeOpponent = playerManager.getOpponentATeam().getAlternativePlayer();
            }

            @Override
            public final void handle(ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.END_TURN
                        && (actionEvent.getPlayer() == opponent || actionEvent.getPlayer() == alternativeOpponent)) {
                    final List<Skill> opponentSkills = opponent.getHero().getCollectionOfSkills();
                    for (final Skill skill : opponentSkills) {
                        skill.setSkillAccess(true);
                    }
                    isWorking = false;
                    log.info("COUNTER SPELL IS DEACTIVATED");
                }
            }

            @Override
            public final String getName() {
                return "CounterSpell";
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
            public final void setAble(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}
