package bonus.deck;

import bonus.bonuses.Bonus;

import java.util.List;

public final class Deck {
    private final String collectionName;
    private final String hero;
    private final int priority;
    private final List<Bonus> collection;

    public Deck(final String collectionName, final String hero, final int priority, final List<Bonus> collection) {
        this.collectionName = collectionName;
        this.collection = collection;
        this.hero = hero;
        this.priority = priority;
    }

    public List<Bonus> getCollection() {
        return collection;
    }

    public int getPriority() {
        return priority;
    }

    public String getHero() {
        return hero;
    }

    public String getCollectionName() {
        return collectionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck that = (Deck) o;

        return priority == that.priority && collection.equals(that.collection) &&
                collectionName.equals(that.collectionName);
    }

    @Override
    public int hashCode() {
        int result = collection.hashCode();
        result = 31 * result + collectionName.hashCode();
        result = 31 * result + priority;
        return result;
    }

    @Override
    public String toString() {
        return "Deck{\n" +
                "collectionName='" + collectionName + "\n" +
                "hero: " + hero + "\n" +
                "priority=" + priority + "\n" +
                "collection=" + collection + "\n" +
                '}';
    }
}