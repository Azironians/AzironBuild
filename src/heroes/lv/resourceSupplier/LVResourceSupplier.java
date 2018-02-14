package heroes.lv.resourceSupplier;

import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;

public final class LVResourceSupplier implements HeroResourceSupplier {

    private final String heroPath = "file:src\\resources\\heroes\\lv\\";

    @Override
    public final ImageView getFaceImageInstance() {
        return getImageView(heroPath + "face\\face.png");
    }

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
    public GetSkill getSwapSkillResources() {
        return null;
    }

    private ImageView getCannibalismSpriteInstance(){
        return superSkillSpriteFormat(heroPath + "skills\\cannibalism\\sprite.png");
    }

    private ImageView getCannibalismDescriptionInstance(){
        return skillDescriptionFormat(heroPath + "skills\\cannibalism\\description.png");
    }

    private ImageView getNightBladesSpriteInstance(){
        return superSkillSpriteFormat(heroPath + "skills\\nightBlades\\sprite.png");
    }

    private ImageView getNightBladesDescriptionInstance(){
        return skillDescriptionFormat(heroPath + "skills\\nightBlades\\description.png");
    }

    private ImageView getReincarnationSpriteInstance(){
        return superSkillSpriteFormat(heroPath + "skills\\reincarnation\\sprite.png");
    }

    private ImageView getReincarnationDescriptionInstance(){
        return skillDescriptionFormat(heroPath + "skills\\reincarnation\\description.png");
    }

    @Override
    public List<Media> getPresentationVoiceList() {
        return null;
    }

    @Override
    public final String getHeroPath() {
        return heroPath;
    }
}
