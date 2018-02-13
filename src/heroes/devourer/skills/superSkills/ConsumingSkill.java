package heroes.devourer.skills.superSkills;

import heroes.abstractHero.skills.superSkills.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.battleManagement.BattleManager;
import managment.playerManagement.PlayerManager;

import java.util.List;

public final class ConsumingSkill extends AbstractSkill {

    protected ConsumingSkill(String name, int reload, int requiredLevel, List<Double> coefficients, ImageView sprite, ImageView pictureOfDescription, List<Media> listOfVoices) {
        super(name, reload, requiredLevel, coefficients, sprite, pictureOfDescription, listOfVoices);
    }

    @Override
    public void use(BattleManager battleManager, PlayerManager playerManager) {

    }

    @Override
    public void showAnimation() {

    }
}
