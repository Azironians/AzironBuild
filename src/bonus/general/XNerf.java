package bonus.general;

import bonus.bonuses.Bonus;
import heroes.abstractHero.AHero;
import javafx.scene.image.ImageView;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class XNerf extends Bonus {

    private static final Logger log = LoggerFactory.getLogger(XNerf.class);

    private static final double BONUS_COEFFICIENT = 2.0;

    public XNerf(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final AHero currentHero = player.getHero();

        final double EXPERIENCE_BOOST = currentHero.getAttack() * BONUS_COEFFICIENT;

        final List<AHero.Skill> skills = currentHero.getCollectionOfSkills();
        for (final AHero.Skill skill : skills) {
            final boolean levelReached = currentHero.getLevel() >= skill.getRequiredLevel();
            if (skill.isReady() && levelReached) {
                if (currentHero.addExperience(EXPERIENCE_BOOST)) {
                    log.info("+" + EXPERIENCE_BOOST + " XP");
                    actionManager.getBonusEventEngine().handle();
                }
                skill.reset();
            }
        }
        log.info("SKILL RESET WAS SUCCESSFUL");
    }
}
