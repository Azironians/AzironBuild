package heroes.orcBash.resourceSupplier;

import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;

public final class OrcBashResourceSupplier extends HeroResourceSupplier {

    @Override
    public final List<Media> getAttackVoiceList() {
        return null;
    }

    @Override
    public final List<Media> getTreatmentVoiceList() {
        return null;
    }

    @Override
    public final GetSkill getSwapSkillResources() {
        return null;
    }

    @Override
    public final List<GetSkill> getSuperSkillResources() {
        return new ArrayList<>(){{
            add(new GetSkill(getBashSpriteInstance(), getBashDescriptionInstance(), new ArrayList<>()));
            add(new GetSkill(getFavouriteBeaterSpriteInstance(), getFavouriteBeaterDescriptionInstance(), new ArrayList<>()));
            add(new GetSkill(getRushSpriteInstance(), getRushDescriptionInstance(), new ArrayList<>()));
        }};
    }

    private ImageView getBashSpriteInstance(){
        return superSkillSpriteFormat(getSkillPath() + "bash" + sprite);
    }

    private ImageView getBashDescriptionInstance(){
        return skillDescriptionFormat(getSkillPath() + "bash" + description);
    }

    private ImageView getFavouriteBeaterSpriteInstance(){
        return superSkillSpriteFormat(getSkillPath() + "favouriteBeater" + sprite);
    }

    private ImageView getFavouriteBeaterDescriptionInstance(){
        return skillDescriptionFormat(getSkillPath() + "favouriteBeater" + description);
    }

    private ImageView getRushSpriteInstance(){
        return superSkillSpriteFormat(getSkillPath() + "rush" + sprite);
    }

    private ImageView getRushDescriptionInstance(){
        return skillDescriptionFormat(getSkillPath() + "rush" + description);
    }

    @Override
    public final List<Media> getPresentationVoiceList() {
        return null;
    }

    @Override
    public final String getHeroPath() {
        return "file:src\\resources\\heroes\\orcBash\\";
    }
}
