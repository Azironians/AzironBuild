package gui.animation;

import com.google.inject.Singleton;
import heroes.abstractHero.hero.AHero;

@Singleton
public final class AnimationManager {

    public final void setAttackRequest(final AHero aHero) {
        aHero.showAttackAnimation();
    }

    public final void setHealingRequest(final AHero aHero) {
        aHero.showTreatmentAnimation();
    }

    public final void setBonusRequest(){}

    public final void setSkillRequest(){}
}
