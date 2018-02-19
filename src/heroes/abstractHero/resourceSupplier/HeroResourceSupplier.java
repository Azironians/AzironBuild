package heroes.abstractHero.resourceSupplier;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.List;

public abstract class HeroResourceSupplier {

    //Paths:
    @Inject
    @Named("facePath")
    private static String facePath;

    @Inject
    @Named("spritePath")
    protected static String sprite;

    @Inject
    @Named("descriptionPath")
    protected static String description;

    //Size:
    @Inject
    @Named("SUPER_SKILL_WIDTH")
    private static int SUPER_SKILL_WIDTH;

    @Inject
    @Named("SUPER_SKILL_HEIGHT")
    private static int SUPER_SKILL_HEIGHT;

    @Inject
    @Named("SWAP_SKILL_WIDTH")
    private static int SWAP_SKILL_WIDTH;

    @Inject
    @Named("SWAP_SKILL_HEIGHT")
    private static int SWAP_SKILL_HEIGHT;

    protected final String getSkillPath() {
        return getHeroPath() + "skills\\";
    }

    protected final String getSwapSkillPath(){
        return getHeroPath() + "face\\swapFace.png";
    }

    public ImageView getFaceImageInstance() {
        return getImageView(getHeroPath() + facePath);
    }

    public abstract List<Media> getAttackVoiceList();

    public abstract List<Media> getTreatmentVoiceList();

    protected ImageView swapSkillSpriteFormat(final String path) {
        final ImageView imageView = getImageView(path);
        imageView.setFitWidth(SWAP_SKILL_WIDTH);
        imageView.setFitHeight(SWAP_SKILL_HEIGHT);
        return imageView;
    }

    protected ImageView superSkillSpriteFormat(final String path) {
        final ImageView imageView = getImageView(path);
        imageView.setFitWidth(SUPER_SKILL_WIDTH);
        imageView.setFitHeight(SUPER_SKILL_HEIGHT);
        return imageView;
    }

    private ImageView getImageView(final String path) {
        return new ImageView(new Image(path));
    }

    protected ImageView skillDescriptionFormat(final String path) {
        final ImageView imageView = getImageView(path);
        return imageView;
    }

    public abstract String getHeroPath();

    public abstract List<GetSkill> getSuperSkillResources();

    public abstract GetSkill getSwapSkillResources();

    // FIXME: 01.02.2018 add media params in the future
    public final class GetSkill {
        private final ImageView sprite;
        private final ImageView description;
        private final List<Media> voiceList;

        public GetSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
            this.sprite = sprite;
            this.description = description;
            this.voiceList = voiceList;
        }

        public final ImageView getSprite() {
            return sprite;
        }

        public final ImageView getDescription() {
            return description;
        }

        public List<Media> getVoiceList() {
            return voiceList;
        }
    }

    //Presentation:
//    @Inject
//    @Named("presentationPath")
    private static String presentationPath = "presentation\\spotlight.png";

    public ImageView getPresentationBackground() {
        return getImageView(getHeroPath() + presentationPath);
    }

    public abstract List<Media> getPresentationVoiceList();
}
