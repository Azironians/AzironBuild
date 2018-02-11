package controllers.main.matchmaking;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import managment.playerManagement.ATeam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public final class ControllerLeftLocation extends ControllerLocation implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(ControllerLeftLocation.class);

    @FXML
    private AnchorPane location;

    //Hero:
    @FXML
    private ImageView face;

    @FXML
    private AnchorPane skillPane;

    //Skills:
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
    public final void initialize(final URL location, final ResourceBundle resources) {




    }

    public final void imageHeroOnClicked() {
        final ATeam leftATeam = playerManager.getLeftATeam();
        makeHeroRequest(leftATeam);
    }

    public final void changeHeroOnClicked(){
        final ATeam leftTeam = playerManager.getLeftATeam();
        makeSwapHeroRequest(leftTeam);
    }
}
