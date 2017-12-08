package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by HarisMehmood on 1/8/2017.
 */
public class ChartWindow {

    ChartWindow(Group root, String title) {

        Stage subStage = new Stage();
        subStage.setTitle(title);
        subStage.sizeToScene();
        Scene scene = new Scene(root, 500, 420);

        subStage.setScene(scene);
        subStage.show();
    }
}
