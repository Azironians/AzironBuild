package heroes.devourer.recourceSupplier;

import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;

public final class DevourerResourceSupplier implements HeroResourceSupplier{

    private final String heroPath = "file:src\\resources\\heroes\\devourer\\";

    public final ImageView getFaceImageInstance() {
        return new ImageView(new Image(heroPath + "face\\devourer.png"));
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
            add(new GetSkill(getFlameSnakesSpriteInstance(), getFlameSnakesDescriptionInstance(), new ArrayList<>())); //temporary
            add(new GetSkill(getRegenerationSpriteInstance(), getRegenerationDescriptionInstance(), new ArrayList<>()));
            add(new GetSkill(getConsumingSpriteInstance(), getConsumingDescriptionInstance(), new ArrayList<>()));
        }};
    }

    @Override
    public final GetSkill getSwapSkillResources() {
        return new GetSkill(getDepletionSpriteInstance(), getDepletionDescriptionInstance(), new ArrayList<>());
    }

    private ImageView getDepletionSpriteInstance(){
        return superSkillSpriteFormat(new ImageView(new Image(heroPath + "skills")));
    }

    private ImageView getDepletionDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills")));
    }

    private ImageView getFlameSnakesSpriteInstance(){
        return superSkillSpriteFormat(new ImageView(new Image(heroPath + "skills\\flameSnakes\\sprite.png")));
    }

    private ImageView getFlameSnakesDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\flameSnakes\\description.png")));
    }

    private ImageView getRegenerationSpriteInstance(){
        return superSkillSpriteFormat(new ImageView(new Image(heroPath + "skills\\regeneration\\sprite.png")));
    }

    private ImageView getRegenerationDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\regeneration\\description.png")));
    }

    private ImageView getConsumingSpriteInstance(){
        return superSkillSpriteFormat(new ImageView(new Image(heroPath + "skills\\consuming\\sprite.png")));
    }

    private ImageView getConsumingDescriptionInstance(){
        return skillDescriptionFormat(new ImageView(new Image(heroPath + "skills\\consuming\\description.png")));
    }

    //Presentation:
    @Override
    public ImageView getPresentationBackground() {
        return new ImageView(new Image(heroPath + "presentation\\spotlight.png"));
    }

    @Override
    public List<Media> getPresentationVoiceList() {
        return null;
    }
}
