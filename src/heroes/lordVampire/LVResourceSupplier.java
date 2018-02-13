package heroes.lordVampire;

import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public final class LVResourceSupplier implements HeroResourceSupplier {

    private final String heroPath = "file:src\\resources\\heroes\\lordVampire\\";

    @Override
    public final ImageView getFaceImageInstance() {
        return new ImageView(new Image(heroPath + "face\\lordVampire.png"));
    }

    @Override
    public final List<GetSkill> getSuperSkillResources() {
        return new ArrayList<>(){{
            add(new GetSkill(getCannibalismSpriteInstance(), getCannibalismDescriptionInstance()));
            add(new GetSkill(getNightBladesSpriteInstance(), getNightBladesDescriptionInstance()));
            add(new GetSkill(getReincarnationSpriteInstance(), getReincarnationDescriptionInstance()));
        }};
    }

    private ImageView getCannibalismSpriteInstance(){
        return superSkillSpriteFormat(new ImageView(new Image(heroPath + "skills\\cannibalism\\sprite.png")));
    }

    private ImageView getCannibalismDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\cannibalism\\description.png")));
    }

    private ImageView getNightBladesSpriteInstance(){
        return superSkillSpriteFormat(new ImageView(new Image(heroPath + "skills\\nightBlades\\sprite.png")));
    }

    private ImageView getNightBladesDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\nightBlades\\description.png")));
    }

    private ImageView getReincarnationSpriteInstance(){
        return superSkillSpriteFormat(new ImageView(new Image(heroPath + "skills\\reincarnation\\sprite.png")));
    }

    private ImageView getReincarnationDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\reincarnation\\description.png")));
    }
}
