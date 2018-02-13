package heroes.abstractHero.resourceSupplier;


import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.List;


public interface HeroResourceSupplier {

    int SUPER_SKILL_WIDTH = 115;

    int SUPER_SKILL_HEIGHT = 134;

    int SWAP_SKILL_WIDTH = 0;

    int SWAP_SKILL_HEIGHT = 0;

    ImageView getFaceImageInstance();

    List<Media> getAttackVoiceList();

    List<Media> getTreatmentVoiceList();



    default ImageView swapSkillSpriteFormat(final ImageView imageView) {
        imageView.setFitWidth(SWAP_SKILL_WIDTH);
        imageView.setFitHeight(SWAP_SKILL_HEIGHT);
        return imageView;
    }

    default ImageView superSkillSpriteFormat(final ImageView imageView) {
        imageView.setFitWidth(SUPER_SKILL_WIDTH);
        imageView.setFitHeight(SUPER_SKILL_HEIGHT);
        return imageView;
    }

    default ImageView skillDescriptionFormat(final ImageView imageView) {
        return imageView;
    }

    List<GetSkill> getSuperSkillResources();

    GetSkill getSwapSkillResources();

    // FIXME: 01.02.2018 add media params in the future
    final class GetSkill {
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

    ImageView getPresentationBackground();

    List<Media> getPresentationVoiceList();

}
