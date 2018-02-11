package bonus.deck.fictionalDecks;

import bonus.bonuses.factory.BonusFactory;
import bonus.deck.Deck;

import java.util.ArrayList;

public final class FictionalDecks {

    public static Deck getGeneralDeck(){
        return new Deck("base collection", "orcBash", 100
                , new ArrayList<>(new BonusFactory().getMapOfBonus().values()));
    }
}
