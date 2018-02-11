package heroes.orcBash;

import heroes.abstractHero.HeroResourceSupplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public final class OrcBashResourceSupplier implements HeroResourceSupplier {
    private final String heroPath = "file:src\\resources\\heroes\\orcBash\\";

    @Override
    public final ImageView getFaceImageInstance() {
        return skillSpriteFormat(new ImageView(new Image(heroPath + "face\\orcBash.png")));
    }

    @Override
    public final List<GetSkills> getSkillInstances() {
        return new ArrayList<>(){{
            add(new GetSkills(getBashSpriteInstance(), getBashDescriptionInstance()));
            add(new GetSkills(getFavouriteBeaterSpriteInstance(), getFavouriteBeaterDescriptionInstance()));
            add(new GetSkills(getRushSpriteInstance(), getRushDescriptionInstance()));
        }};
    }

    private ImageView getBashSpriteInstance(){
        return skillSpriteFormat(new ImageView(new Image(heroPath + "skills\\bash\\sprite.png")));
    }

    private ImageView getBashDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\bash\\description.png")));
    }

    private ImageView getFavouriteBeaterSpriteInstance(){
        return skillSpriteFormat(new ImageView(new Image(heroPath + "skills\\favouriteBeater\\sprite.png")));
    }

    private ImageView getFavouriteBeaterDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\favouriteBeater\\description.png")));
    }

    private ImageView getRushSpriteInstance(){
        return skillSpriteFormat(new ImageView(new Image(heroPath + "skills\\rush\\sprite.png")));
    }

    private ImageView getRushDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\rush\\description.png")));
    }
}
