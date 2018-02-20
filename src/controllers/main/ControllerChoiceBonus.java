package controllers.main;

import bonus.deck.Deck;
import controllers.Controller;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public final class ControllerChoiceBonus implements Initializable, Controller {

    private Map<String, List<Deck>> bonusData = null;

    private List<Deck> privilegedCollections = null;

    private Deck defaultPrimaryDeck;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        appearance();
        //загрузка бонусов
    }

    @Override
    public final void appearance() {

    }

    public void back() {

    }

    public final Map<String, List<Deck>> getBonusData() {
        return bonusData;
    }

    public final void setBonusData(Map<String, List<Deck>> bonusData) {
        this.bonusData = bonusData;
    }

    public List<Deck> getPrivilegedCollections() {
        return privilegedCollections;
    }

    public void setPrivilegedCollections(List<Deck> privilegedCollections) {
        this.privilegedCollections = privilegedCollections;
    }

    public Deck getDefaultPrimaryDeck() {
        return defaultPrimaryDeck;
    }

    public void setDefaultPrimaryDeck(Deck defaultPrimaryDeck) {
        this.defaultPrimaryDeck = defaultPrimaryDeck;
    }
}