package heroes.abstractHero.builder;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.presentation.Presentation;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.skills.Skill;
import heroes.abstractHero.skills.factory.SkillFactory;
import heroes.devourer.hero.Devourer;
import heroes.lv.hero.LV;
import heroes.orcBash.hero.OrcBash;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public interface HeroBuilder {

    Logger log = LoggerFactory.getLogger(HeroBuilder.class);

    default Hero buildHero(){
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

    default Hero getHeroInstance(final Class clazz, final List<Skill> superSkills, final Skill swapSkill
            , final ImageView face, final List<Media> attackVoices, final List<Media> treatmentVoices){
        Hero hero = null;
        if (clazz.equals(Devourer.class)) {
            hero = new Devourer(superSkills, swapSkill, face, attackVoices, treatmentVoices);
        }
        if (clazz.equals(LV.class)){
            hero = new LV(superSkills, swapSkill, face, attackVoices, treatmentVoices);
        }
        if (clazz.equals(OrcBash.class)){
            hero = new OrcBash(superSkills, swapSkill, face, attackVoices, treatmentVoices);
        }
        if (hero != null){
            log.info(hero.toString());
        }
        return hero;
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