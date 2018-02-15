package gui.animation;

import com.google.inject.Singleton;
import heroes.abstractHero.hero.Hero;

@Singleton
public final class AnimationManager {

    public final void setAttackRequest(final Hero hero) {
        hero.showAttackAnimation();
    }

    public final void setHealingRequest(final Hero hero) {
        hero.showTreatmentAnimation();
    }

    public final void setBonusRequest(){}

    public final void setSkillRequest(){}
}
