package heroes.abstractHero;


import javafx.scene.image.ImageView;

import java.util.List;


public interface HeroResourceSupplier {

    ImageView getFaceImageInstance();

    default ImageView skillSpriteFormat(final ImageView imageView) {
        imageView.setFitHeight(115);
        imageView.setFitWidth(134);
        return imageView;
    }

    default ImageView skillDescriptionFormat(final ImageView imageView) {
        return imageView;
    }

    List<GetSkills> getSkillInstances();

    // FIXME: 01.02.2018 add media params in the future
    final class GetSkills {
        private final ImageView sprite;
        private final ImageView description;

        public GetSkills(ImageView sprite, ImageView description) {
            this.sprite = sprite;
            this.description = description;
        }

        public final ImageView getSprite() {
            return sprite;
        }

        public final ImageView getDescription() {
            return description;
        }
    }

}
