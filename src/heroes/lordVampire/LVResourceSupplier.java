package heroes.lordVampire;

import heroes.abstractHero.HeroResourceSupplier;
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
    public final List<GetSkills> getSkillInstances() {
        return new ArrayList<>(){{
            add(new GetSkills(getCannibalismSpriteInstance(), getCannibalismDescriptionInstance()));
            add(new GetSkills(getNightBladesSpriteInstance(), getNightBladesDescriptionInstance()));
            add(new GetSkills(getReincarnationSpriteInstance(), getReincarnationDescriptionInstance()));
        }};
    }

    private ImageView getCannibalismSpriteInstance(){
        return skillSpriteFormat(new ImageView(new Image(heroPath + "skills\\cannibalism\\sprite.png")));
    }

    private ImageView getCannibalismDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\cannibalism\\description.png")));
    }

    private ImageView getNightBladesSpriteInstance(){
        return skillSpriteFormat(new ImageView(new Image(heroPath + "skills\\nightBlades\\sprite.png")));
    }

    private ImageView getNightBladesDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\nightBlades\\description.png")));
    }

    private ImageView getReincarnationSpriteInstance(){
        return skillSpriteFormat(new ImageView(new Image(heroPath + "skills\\reincarnation\\sprite.png")));
    }

    private ImageView getReincarnationDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\reincarnation\\description.png")));
    }
}
