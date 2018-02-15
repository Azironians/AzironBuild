package security.loadSuppliers.bonusSupplier;

import annotations.bindingAnnotations.BonusService;
import bonus.bonuses.Bonus;
import bonus.bonuses.factory.BonusFactory;
import bonus.deck.Deck;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import controllers.main.ControllerChoiceBonus;
import gui.endavour.Endeavour;
import gui.windows.WindowType;
import main.AGame;
import managment.profileManagement.ProfileManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import security.assistants.ProfileAssistant;
import security.loadSuppliers.LoadSupplier;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static heroes.heroUtils.HeroUtils.heroNames;

public final class BonusLoadSupplier implements LoadSupplier<BonusData> {

    private static final Logger log = Logger.getLogger(BonusLoadSupplier.class.getName());

    @Inject
    @Named("SUCCESSFUL_LOGIN")
    private static String SUCCESSFUL_LOGIN;

    @Inject
    @Named("BAD_COLLECTIONS")
    private static String BAD_COLLECTIONS;

    @Inject
    @Named("DECK_SIZE")
    private static int DECK_SIZE;

    @Inject
    @Named("HERO_SPLITTER")
    private static int HERO_SPLITTER; //Разделитель id бонусов между героями.

    @Inject
    @Named("HERO_SHIFT")
    private static int HERO_SHIFT; //Кол-во бонусов у каждого героя:

    @Inject
    @Named("WITHOUT_FILE_FORMAT")
    private static int WITHOUT_FILE_FORMAT; //without ".hoa"

    @Inject
    @BonusService
    private Provider<BonusFactory> bonusFactoryProvider;

    @Inject
    private AGame aGame;

    @Inject
    private ProfileManager profileManager;

    private Map<String, List<Deck>> heroDecks;

    private List<Deck> privilegedDecks;

    private Deck bestDeck;

    private BonusData bonusData;

    @NotNull
    public final Endeavour readData(final String login, final String password) {
        initialize();
        try {
            int splitter = HERO_SPLITTER;
            for (final File file : getHeroFiles(login)) {
                final String heroName = getFileName(file);
                final List<Deck> heroDecks = readAndTransformHeroDecks(file, heroName, splitter);
                this.privilegedDecks.add(getHighPriorityDeck(heroDecks));
                this.heroDecks.put(heroName, heroDecks);
                splitter += HERO_SHIFT;
            }
            this.bestDeck = getHighPriorityDeck(privilegedDecks);
            this.bonusData = new BonusData(bestDeck, privilegedDecks, heroDecks);
            if (this.heroDecks.size() == heroNames.size()) {
                return new Endeavour(SUCCESSFUL_LOGIN, true);
            } else {
                return new Endeavour(BAD_COLLECTIONS, false);
            }
        } catch (final Exception e) {
            return new Endeavour(BAD_COLLECTIONS, false);
        }
    }

    private void initialize() {
        this.heroDecks = new HashMap<>(); //all decks
        this.privilegedDecks = new ArrayList<>(); //best deck for each hero
    }

    private Collection<File> getHeroFiles(final String login) {
        return new ArrayList<>() {{
            for (final String hero : heroNames) {
                add(new File(ProfileAssistant.heroCollectionPath(login, hero)));
            }
        }};
    }

    private List<Deck> readAndTransformHeroDecks(final File file, final String heroName
            , final int splitter) throws FileNotFoundException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        final List<String> inputDecks = bufferedReader.lines().collect(Collectors.toList());
        return transformToDecks(heroName, inputDecks, splitter);
    }

    private String getFileName(final File file) {
        return file.getName().substring(0, file.getName().length() - WITHOUT_FILE_FORMAT);
    }

    @Nullable
    private Deck getHighPriorityDeck(final List<Deck> deckList) {
        if (deckList.size() > 0) {
            Deck privilegedDeck = deckList.get(0);
            for (final Deck deck : deckList) {
                if (deck.getPriority() > privilegedDeck.getPriority()) {
                    privilegedDeck = deck;
                }
            }
            return privilegedDeck;
        } else {
            return null;
        }
    }

    @Contract(pure = true)
    private boolean isClassicBonus(int bonus) {
        return bonus > 0 && bonus <= 16;
    }

    private List<Deck> transformToDecks(final String heroName, final List<String> inputDecks, final int start) {
        final List<Deck> outputList = new ArrayList<>();
        for (final String inputCollection : inputDecks) {
            final String[] data = inputCollection.split(" ");
            final Set<Bonus> bonusSet = new HashSet<>();
            for (int i = 2; i < data.length; i++) {
                int bonus = Integer.parseInt(data[i]);
                if (isClassicBonus(bonus) || (bonus >= start && bonus <= start + 48)) {
                    bonusSet.add(bonusFactoryProvider.get().getMapOfBonus().get(bonus));
                } else {
                    throw new IllegalArgumentException("This bonus is not valid");
                }
            }
            if (bonusSet.size() != DECK_SIZE) {
                throw new IllegalArgumentException("This bonus is not valid");
            }
            final List<Bonus> bonusList = new ArrayList<>(bonusSet);
            outputList.add(new Deck(data[0], heroName, Integer.parseInt(data[1]), bonusList));
        }
        return outputList;
    }

    public final void sendData() {
        profileManager.setBonusData(bonusData);

        log.finest("PRIMARY DECK: \n" + bestDeck.toString());
        log.finest("PRIVILEGED DECKS: \n" + privilegedDecks.toString());
        log.finest("BONUS DATA: \n" + heroDecks.toString());
    }

    public final void writeData() {


    }

    @Override
    public final BonusData get() {
        return bonusData;
    }

    @Override
    public final void setData(final BonusData bonusData) {
        this.bonusData = bonusData;
    }

//    @NotNull
//    private String joinToString(List<Bonus> bonusList) {
//        StringBuilder sb = new StringBuilder();
//        for (Bonus b : bonusList) {
//            sb.append(b.getId());
//            sb.append(" ");
//        }
//        sb.deleteCharAt(sb.length() - 1);
//        return sb.toString();
//    }

    private ControllerChoiceBonus getControllerChoiceBonus() {
        return (ControllerChoiceBonus) aGame.getWindowMap().get(WindowType.CHOICE_BONUS).getController();
    }
}