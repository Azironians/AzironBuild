package security.loadSuppliers.bonusSupplier;

import bonus.deck.Deck;

import java.util.List;
import java.util.Map;

public final class BonusData {

    private final Deck bestDeck;

    private final List<Deck> privilegedDecks;

    private final Map<String, List<Deck>> heroDecks;

    public BonusData(final Deck bestDeck, final List<Deck> privilegedDecks, final Map<String, List<Deck>> heroDecks) {
        this.bestDeck = bestDeck;
        this.privilegedDecks = privilegedDecks;
        this.heroDecks = heroDecks;
    }

    public final Deck getBestDeck() {
        return bestDeck;
    }

    public final List<Deck> getPrivilegedDecks() {
        return privilegedDecks;
    }

    public final Map<String, List<Deck>> getHeroDecks() {
        return heroDecks;
    }
}
