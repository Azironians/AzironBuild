package heroes.devourer.builder;

import bonus.bonuses.Bonus;
import com.google.inject.Inject;
import heroes.abstractHero.builder.AHeroBuilder;
import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.presentation.Presentation;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.factory.SkillFactory;
import heroes.devourer.annotation.DevourerSource;
import heroes.devourer.hero.Devourer;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class DevourerBuilder implements AHeroBuilder {

    private static final Logger log = LoggerFactory.getLogger(DevourerBuilder.class);

    @Inject
    @DevourerSource
    private SkillFactory skillFactory;

    @Inject
    @DevourerSource
    private HeroResourceSupplier resourceSupplier;

    @NotNull
    @Override
    public final AHero buildHero(final List<Bonus> inputDeck) {
        final List<Skill> superSkills = skillFactory.getSuperSkills();
        final Skill swapSkill = skillFactory.getSwapSkill();
        final List<Media> attackVoiceList = resourceSupplier.getAttackVoiceList();
        final List<Media> treatmentVoiceList = resourceSupplier.getTreatmentVoiceList();
        final ImageView face = resourceSupplier.getFaceImageInstance();
        return new Devourer(superSkills, swapSkill, face, attackVoiceList, treatmentVoiceList);
    }

    @Override
    public final Presentation buildPresentation(final String deckName, final int deckPriority) {
        final ImageView background = resourceSupplier.getPresentationBackground();
        final List<Media> mediaList = resourceSupplier.getPresentationVoiceList();
        return new Presentation(deckName, deckPriority, background, mediaList);
    }
}
