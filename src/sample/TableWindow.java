package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TableWindow {

    TableWindow(Group root, String title) {

        Stage subStage = new Stage();
        subStage.setTitle(title);
        subStage.sizeToScene();
        Scene scene = new Scene(root, 480, 500);

        subStage.setScene(scene);
        subStage.show();
    }
}
