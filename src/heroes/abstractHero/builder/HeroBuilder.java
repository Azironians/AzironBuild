package heroes.abstractHero.builder;

import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.presentation.Presentation;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.factory.SkillFactory;
import heroes.devourer.hero.Devourer;
import heroes.lv.hero.LV;
import heroes.orcBash.hero.OrcBash;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.*;

public interface HeroBuilder {

    default AHero buildHero(){
        final Class clazz = getHeroClass();
        final SkillFactory skillFactory = getSkillFactory();
        final HeroResourceSupplier resourceSupplier = getHeroResourceSupplier();
        final List<Skill> superSkills = skillFactory.getSuperSkills();
        final Skill swapSkill = skillFactory.getSwapSkill();
        final List<Media> attackVoiceList = resourceSupplier.getAttackVoiceList();
        final List<Media> treatmentVoiceList = resourceSupplier.getTreatmentVoiceList();
        final ImageView face = resourceSupplier.getFaceImageInstance();
        return getHeroInstance(clazz, superSkills, swapSkill, face, attackVoiceList, treatmentVoiceList);
    }

    private AHero getHeroInstance(final Class clazz, final List<Skill> superSkills, final Skill swapSkill
            , final ImageView face, final List<Media> attackVoices, final List<Media> treatmentVoices){
        if (clazz.equals(Devourer.class)) {
            return new Devourer(superSkills, swapSkill, face, attackVoices, treatmentVoices);
        }
        if (clazz.equals(LV.class)){
            return new LV(superSkills, swapSkill, face, attackVoices, treatmentVoices);
        }
        if (clazz.equals(OrcBash.class)){
            new OrcBash(superSkills, swapSkill, face, attackVoices, treatmentVoices);
        }
        return null;
    }

    default Presentation buildPresentation(final String deckName, final int deckPriority){
        final HeroResourceSupplier resourceSupplier = getHeroResourceSupplier();
        final ImageView background = resourceSupplier.getPresentationBackground();
        final List<Media> mediaList = resourceSupplier.getPresentationVoiceList();
        return new Presentation(deckName, deckPriority, background, mediaList);
    }

    SkillFactory getSkillFactory();

    HeroResourceSupplier getHeroResourceSupplier();

    Class getHeroClass();
}