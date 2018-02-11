package controllers.main;

import controllers.Controller;
import managment.playerManagement.Player;
import managment.profileManagement.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerTotalMatch implements Initializable, Controller {
    //Статистика:
    @FXML
    private Text textPlayer1Name;
    @FXML
    private Text textPlayer2Name;
    @FXML
    private Text textPlayer1DealDamage;
    @FXML
    private Text textPlayer2DealDamage;
    @FXML
    private Text textPlayer1RestoreHitPoints;
    @FXML
    private Text textPlayer2RestoreHitPoints;
    @FXML
    private Text textPlayer1ReachedLevel;
    @FXML
    private Text textPlayer2ReachedLevel;
    @FXML
    private Text textPlayer1UsedSkills;
    @FXML
    private Text textPlayer2UsedSkills;
    @FXML
    private Text textPlayer1FavouriteBonus;
    @FXML
    private Text textPlayer2FavouriteBonus;
    @FXML
    private Text textPlayer1RemainingTime;
    @FXML
    private Text textPlayer2RemainingTime;
    @FXML
    private Text textPlayer1Result;
    @FXML
    private Text textPlayer2Result;

    private Player playerBlue = null;
    private Player playerRed = null;

    private Profile profile1;
    private Profile profile2;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appearance();
        resetGame();
    }

    private void resetGame(){
        playerBlue = null;
        playerRed = null;

    }


    //Style & interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void appearance() {
        setResultsOfMatch();
    }

    private void setResultsOfMatch(){
        //Игрок 1:
        textPlayer1Name.setText(profile1.getName());
        textPlayer1DealDamage.setText(String.valueOf(playerBlue.getDealDamage()));
        textPlayer1RestoreHitPoints.setText(String.valueOf(playerBlue.getRestoredHitPoints()));
        textPlayer1ReachedLevel.setText(String.valueOf(playerBlue.getReachedLevel()));
        textPlayer1UsedSkills.setText(String.valueOf(playerBlue.getUsedSkills()));
        textPlayer1FavouriteBonus.setText(String.valueOf(playerBlue.getFavouriteBonus()));
        textPlayer1RemainingTime.setText(String.valueOf(playerBlue.getRemainingTime()));
        if (playerBlue.isWinner()) {
            textPlayer1Result.setText("Победа");
        } else {
            textPlayer1Result.setText("Поражение");
        }

        //Игрок 2:
        textPlayer2Name.setText(profile2.getName());
        textPlayer2DealDamage.setText(String.valueOf(playerRed.getDealDamage()));
        textPlayer2RestoreHitPoints.setText(String.valueOf(playerRed.getRestoredHitPoints()));
        textPlayer2ReachedLevel.setText(String.valueOf(playerRed.getReachedLevel()));
        textPlayer2UsedSkills.setText(String.valueOf(playerRed.getUsedSkills()));
        textPlayer2FavouriteBonus.setText(String.valueOf(playerRed.getFavouriteBonus()));
        textPlayer2RemainingTime.setText(String.valueOf(playerRed.getRemainingTime()));
        if (playerRed.isWinner()) {
            textPlayer2Result.setText("Победа");
        } else {
            textPlayer2Result.setText("Поражение");
        }
    }

    public void buttonPlayAgainClicked(){

    }

    public void buttonBackToMenuClicked(){

    }
}
