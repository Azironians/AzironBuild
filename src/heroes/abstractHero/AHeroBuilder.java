package heroes.abstractHero;

import bonus.bonuses.Bonus;
import org.jetbrains.annotations.Contract;

import java.util.*;

public interface AHeroBuilder {

    AHero buildHero(final List<Bonus> deck);
}