package logic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ViewMenager {

    private SceneMenager sceneMenager;

    private StageMenager stageMenager;

    private PaneMenager paneMenager;


    public ViewMenager() {
        this.sceneMenager = new SceneMenager();
        this.paneMenager = new PaneMenager();
        this.stageMenager = new StageMenager();
    }

    public SceneMenager getSceneMenager() {
        return this.sceneMenager;
    }

    public StageMenager getStageMenager() {
        return this.stageMenager;
    }

    public PaneMenager getPaneMenager() {
        return this.paneMenager;
    }

    public class SceneMenager {

        private Map<String, Scene> scenes;

        public SceneMenager() {
            scenes = new HashMap<>();
        }

        public Scene getScenes(String name) {
            return scenes.get(name);
        }

        public void setScenes(String name, Scene pane) {
            this.scenes.put(name, pane);
        }

    }

    public class StageMenager {

        private Map<String, Stage> stages;

        public StageMenager() {
            stages = new HashMap<>();
        }

        public Stage getStages(String name) {
            return stages.get(name);
        }

        public void setStages(String name, Stage pane) {
            this.stages.put(name, pane);
        }

        public void closeAll() {

            for (Map.Entry s : stages.entrySet()) {
                Stage x = (Stage) s.getValue();
                x.close();
            }
        }

    }

    public class PaneMenager {

        private Map<String, Pane> panes;

        public PaneMenager() {
            panes = new HashMap<>();
        }

        public Pane getPanes(String name) {
            return panes.get(name);
        }

        public void setPanes(String name, Pane pane) {
            this.panes.put(name, pane);
        }

    }

    public Pane createPane(String FXMLuri, String CSS, Object controller) {
        // uwaga wykona sie initialize kontrolera !
        FXMLLoader xxx = new FXMLLoader(getClass().getResource(FXMLuri));
        xxx.setController(controller);

        Pane pane = new Pane();

        try {
            pane = xxx.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pane;
    }

    // nalezy wpisac na taka ref jaki jest tag kontener w pliku fxml!
    // kazda z metod przenosi kod programu do podanego jej kontrolera !!
    // do kazdego kontrolera powinien byc wstrzykniety ten menager aby moc zarzadzac aktualna scena stagem itp
    // kiedy wywolales jedna z tych metod reszt koduj w podanym do nich kontrolerze

    public Pane createAndShowViewWithNewStage(String FXMLuri, String CSS, Object controller, String PaneName, String stageName, String iconUri) {

        Pane pane = this.createPane(FXMLuri, CSS, controller);

        this.paneMenager.setPanes(PaneName, pane);
        Stage thisStage = new Stage();
        this.stageMenager.setStages(stageName, thisStage);
        thisStage.setScene(new Scene(pane));
        thisStage.getScene().getStylesheets()
                .add(getClass().getResource(CSS).toExternalForm());
        thisStage.getIcons().add(new Image(iconUri));
        thisStage.show();

        return pane;
    }


    // czasami Layout wariuje jak ustawia sie go na poprzedniej scenie (gubi wymiary) - ustawiaj na poprzednim stage (nie pane.getChildren()setAll())
    // albo wgl na nowym
    public Pane createAndShowViewOnGivenStage(String FXMLuri, String CSS, Object controller, String PaneName, Stage thisSavedStage, String iconUri) {

        Pane pane = this.createPane(FXMLuri, CSS, controller);

        this.paneMenager.setPanes(PaneName, pane);
        thisSavedStage.setScene(new Scene(pane));
        thisSavedStage.getScene().getStylesheets()
                .add(getClass().getResource(CSS).toExternalForm());
        thisSavedStage.getIcons().add(new Image(iconUri));
        thisSavedStage.show();

        return pane;
    }

    public Pane replaceViewOnGivenContainerPane(String FXMLuri, String CSS, Object controller, String PaneName, Pane containerPane, String iconUri) {

        Pane pane = this.createPane(FXMLuri, CSS, controller);

        this.paneMenager.setPanes(PaneName, pane);
        containerPane.getChildren().setAll(pane);
        containerPane.getScene().getWindow().centerOnScreen();

        return pane;
    }


}
