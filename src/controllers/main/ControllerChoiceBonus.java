package controllers.main;

import bonus.deck.Deck;
import controllers.Controller;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerChoiceBonus implements Initializable, Controller {
    private Map<String, List<Deck>> bonusData = null;

    private List<Deck> privilegedCollections = null;

    private Deck defaultPrimaryDeck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appearance();
        //загрузка бонусов
    }


    @Override
    public void appearance() {

    }

    public void back() {

    }


    public Map<String, List<Deck>> getBonusData() {
        return bonusData;
    }

    public void setBonusData(Map<String, List<Deck>> bonusData) {
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
