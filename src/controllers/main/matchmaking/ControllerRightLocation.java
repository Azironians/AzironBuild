package controllers.main.matchmaking;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import managment.playerManagement.ATeam;

import java.net.URL;
import java.util.ResourceBundle;

public final class ControllerRightLocation extends ControllerLocation implements Initializable {

    @FXML
    private AnchorPane location;

    //Hero:
    @FXML
    private ImageView face;

    //Skills:
    @FXML
    private AnchorPane skillPane;

    @FXML
    private Pane firstSkill;

    @FXML
    private Pane secondSkill;

    @FXML
    private Pane thirdSkill;

    //Characteristics:
    @FXML
    private AnchorPane characteristics;

    @FXML
    private Text level;

    @FXML
    private Text attack;

    @FXML
    private Text treatment;

    @FXML
    private Text time;

    @FXML
    private Text supplyHealth;

    @FXML
    private Text hitPoints;

    @FXML
    private Text experience;

    @FXML
    private Text requiredExperience;

    //Swap heroes:
    @FXML
    private AnchorPane heroes;

    @FXML
    private Button currentHero;

    @FXML
    private Button backHero;


    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }

    public final void imageHeroOnClicked() {
        final ATeam rightATeam = playerManager.getRightATeam();
        makeHeroRequest(rightATeam);
    }

    public final void changeHeroOnClicked(){
        final ATeam rightATeam = playerManager.getRightATeam();
        makeSwapHeroRequest(rightATeam);
    }
}
