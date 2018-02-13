package heroes.abstractHero.builder;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.AHero;
import heroes.abstractHero.presentation.Presentation;

import java.util.*;

public interface AHeroBuilder {

    AHero buildHero(final List<Bonus> deck);

    Presentation buildPresentation();
}