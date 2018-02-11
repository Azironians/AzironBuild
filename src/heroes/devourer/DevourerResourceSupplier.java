package heroes.devourer;

import heroes.abstractHero.HeroResourceSupplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public final class DevourerResourceSupplier implements HeroResourceSupplier{

    private final String heroPath = "file:src\\resources\\heroes\\devourer\\";

    public final ImageView getFaceImageInstance() {
        return new ImageView(new Image(heroPath + "face\\devourer.png"));
    }

    @Override
    public final List<GetSkills> getSkillInstances() {
        return new ArrayList<>(){{
            add(new GetSkills(getFlameSnakesSpriteInstance(), getFlameSnakesDescriptionInstance()));
            add(new GetSkills(getRegenerationSpriteInstance(), getRegenerationDescriptionInstance()));
            add(new GetSkills(getConsumingSpriteInstance(), getConsumingDescriptionInstance()));
        }};
    }


    private ImageView getFlameSnakesSpriteInstance(){
        return skillSpriteFormat(new ImageView(new Image(heroPath + "skills\\flameSnakes\\sprite.png")));
    }

    private ImageView getFlameSnakesDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\flameSnakes\\description.png")));
    }

    private ImageView getRegenerationSpriteInstance(){
        return skillSpriteFormat(new ImageView(new Image(heroPath + "skills\\regeneration\\sprite.png")));
    }

    private ImageView getRegenerationDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\regeneration\\description.png")));
    }

    private ImageView getConsumingSpriteInstance(){
        return skillSpriteFormat(new ImageView(new Image(heroPath + "skills\\consuming\\sprite.png")));
    }

    private ImageView getConsumingDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\consuming\\description.png")));
    }

}
