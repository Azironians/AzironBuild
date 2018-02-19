package heroes.lv.resourceSupplier;

import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;

public final class LVResourceSupplier extends HeroResourceSupplier {

    @Override
    public List<Media> getAttackVoiceList() {
        return null;
    }

    @Override
    public List<Media> getTreatmentVoiceList() {
        return null;
    }

    @Override
    public final List<GetSkill> getSuperSkillResources() {
        return new ArrayList<>(){{
            add(new GetSkill(getCannibalismSpriteInstance(), getCannibalismDescriptionInstance(), new ArrayList<>()));
            add(new GetSkill(getNightBladesSpriteInstance(), getNightBladesDescriptionInstance(), new ArrayList<>()));
            add(new GetSkill(getReincarnationSpriteInstance(), getReincarnationDescriptionInstance(), new ArrayList<>()));
        }};
    }

    @Override
    public final GetSkill getSwapSkillResources() {
        return new GetSkill(getFurySpriteInstance(), new ImageView(), new ArrayList<>());
    }

    private ImageView getFurySpriteInstance(){
        return swapSkillSpriteFormat(getSwapSkillPath());
    }

    private ImageView getCannibalismSpriteInstance(){
        return superSkillSpriteFormat(getSkillPath() + "cannibalism" + sprite);
    }

    private ImageView getCannibalismDescriptionInstance(){
        return skillDescriptionFormat(getSkillPath() + "cannibalism" + description);
    }

    private ImageView getNightBladesSpriteInstance(){
        return superSkillSpriteFormat(getSkillPath() + "nightBlades" + sprite);
    }

    private ImageView getNightBladesDescriptionInstance(){
        return skillDescriptionFormat(getSkillPath() + "nightBlades" + description);
    }

    private ImageView getReincarnationSpriteInstance(){
        return superSkillSpriteFormat(getSkillPath() + "reincarnation" + sprite);
    }

    private ImageView getReincarnationDescriptionInstance(){
        return skillDescriptionFormat(getSkillPath() + "reincarnation" + sprite);
    }

    @Override
    public final List<Media> getPresentationVoiceList() {
        return null;
    }

    @Override
    public final String getHeroPath() {
        return "file:src\\resources\\heroes\\lv\\";
    }
}