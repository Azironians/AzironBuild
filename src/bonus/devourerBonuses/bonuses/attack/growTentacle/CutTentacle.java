package bonus.devourerBonuses.bonuses.attack.growTentacle;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class CutTentacle extends Bonus{

    private static final Logger log = LoggerFactory.getLogger(CutTentacle.class);

    private static final int ATTACK_FIX = 4;

    private Player usedGrowTentaclePlayer;

    public CutTentacle(String name, int id, ImageView sprite, final Player usedGrowTentaclePlayer) {
        super(name, id, sprite);
        this.usedGrowTentaclePlayer = usedGrowTentaclePlayer;
    }

    @Override
    public void use() {
        final Hero hero = usedGrowTentaclePlayer.getCurrentHero();
        hero.setAttack(hero.getAttack() - ATTACK_FIX);
        log.info("-4 BEFORE_ATTACK");
    }
}
