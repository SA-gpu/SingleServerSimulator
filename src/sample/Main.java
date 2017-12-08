package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class Main extends Application {

    // Bar chart
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<Double, Double> barChart = new BarChart(xAxis, yAxis);

    // for other window
    private Group tableGroup, chartGroup;

    private TextField timeTextField;

    private Simulator simulator = new Simulator();

    private Text firstFloorTextView, secondFloorTextView, thirdFloorTextView,
            fourthFloorTextView, fifthFloorTextView, totalRouteTextView, hintTextView;

    @Override
    public void start(Stage primaryStage) {

        Pane pane = new Pane();
        primaryStage.setTitle("Elevator");
        primaryStage.setScene(new Scene(pane, 500, 300));
        primaryStage.show();

        hintTextView = new Text("Enter time in hours");
        hintTextView.setTranslateX(190);
        hintTextView.setTranslateY(30);

        timeTextField = new TextField();
        timeTextField.setTranslateX(170);
        timeTextField.setTranslateY(50);

        Button submitButton = new Button("Submit");
        submitButton.setTranslateX(180);
        submitButton.setTranslateY(90);

        Button tableButton = new Button("Table");
        tableButton.setTranslateX(260);
        tableButton.setTranslateY(90);

        firstFloorTextView = new Text();
        firstFloorTextView.setTranslateX(140);
        firstFloorTextView.setTranslateY(140);

        secondFloorTextView = new Text();
        secondFloorTextView.setTranslateX(140);
        secondFloorTextView.setTranslateY(160);

        thirdFloorTextView = new Text();
        thirdFloorTextView.setTranslateX(140);
        thirdFloorTextView.setTranslateY(180);

        fourthFloorTextView = new Text();
        fourthFloorTextView.setTranslateX(140);
        fourthFloorTextView.setTranslateY(200);

        fifthFloorTextView = new Text();
        fifthFloorTextView.setTranslateX(140);
        fifthFloorTextView.setTranslateY(220);

        totalRouteTextView = new Text();
        totalRouteTextView.setTranslateX(140);
        totalRouteTextView.setTranslateY(260);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                handleSubmitButton();
            }
        });

        tableButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                new TableWindow(tableGroup, "Tabular Summary");
            }
        });

        pane.getChildren().addAll(timeTextField, submitButton, tableButton,
                firstFloorTextView, secondFloorTextView, thirdFloorTextView,
                fourthFloorTextView, fifthFloorTextView, totalRouteTextView,
                hintTextView);
    }

    private void handleSubmitButton() {

        simulator.simulate(Integer.parseInt(timeTextField.getText()));

        ObservableList<Row> tableData = simulator.getTableData();
        generateTableReport(tableData);
    }

    private void generateTableReport(ObservableList<Row> tableData) {

        tableGroup = new Group();

        TableView<Row> tableView = new TableView<>();
        Button chartButton = new Button("Bar Chart");

        javafx.scene.control.TableColumn<Row, String> type = new javafx.scene.control.TableColumn<>("Type");
        javafx.scene.control.TableColumn<Row, Double> clock = new javafx.scene.control.TableColumn<>("Clock");
        javafx.scene.control.TableColumn<Row, Integer> floor = new javafx.scene.control.TableColumn<>("Floor");
        javafx.scene.control.TableColumn<Row, Integer> routes = new javafx.scene.control.TableColumn<>("Routes");
        javafx.scene.control.TableColumn<Row, String> capacity = new javafx.scene.control.TableColumn<>("Capacity");
        javafx.scene.control.TableColumn<Row, Integer> floorQueue = new javafx.scene.control.TableColumn<>("Floor Queue");

        type.setCellValueFactory(new PropertyValueFactory<Row, String>("type"));
        clock.setCellValueFactory(new PropertyValueFactory<Row, Double>("clock"));
        floor.setCellValueFactory(new PropertyValueFactory<Row, Integer>("floor"));
        routes.setCellValueFactory(new PropertyValueFactory<Row, Integer>("routes"));
        capacity.setCellValueFactory(new PropertyValueFactory<Row, String>("capacity"));
        floorQueue.setCellValueFactory(new PropertyValueFactory<Row, Integer>("floorQueue"));

        tableView.getColumns().addAll(type, clock, floor, routes, capacity, floorQueue);
        tableView.setItems(tableData);

        // for the chart button
        chartButton.setTranslateX(210);
        chartButton.setTranslateY(440);

        chartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                generateChart();

                chartGroup = new Group();
                new ChartWindow(chartGroup, "Bar Chart View");

                // add the bar chart to the group window
                chartGroup.getChildren().addAll(barChart);
            }
        });

        tableGroup.getChildren().addAll(tableView, chartButton);
    }

    private void generateChart() {

        int[] eachFloorLength = simulator.getEachFloorQueueLength();
        xAxis.setLabel("Each Floor");
        yAxis.setLabel("Queue Length");

        // For making the X and Y plane on the bar chart
        XYChart.Series series = new XYChart.Series();
        series.setName("Queue length for each floor!");
        for (int count = 0; count < eachFloorLength.length; count++) {
            series.getData().add(new XYChart.Data(Integer.toString(count), eachFloorLength[count]));
        }
        barChart.getData().add(series);

        // set the average queue size in a text view
        for (int count = 0; count < eachFloorLength.length; count++) {

            switch (count) {

                case 0:
                    firstFloorTextView.setText("First floor average queue: " +
                            eachFloorLength[count] / Simulator.MAX_MINUTES);
                case 1:
                    secondFloorTextView.setText("Second floor average queue: " +
                            eachFloorLength[count] / Simulator.MAX_MINUTES);
                case 2:
                    thirdFloorTextView.setText("Third floor average queue: " +
                            eachFloorLength[count] / Simulator.MAX_MINUTES);
                case 3:
                    fourthFloorTextView.setText("Fourth floor average queue: " +
                            eachFloorLength[count] / Simulator.MAX_MINUTES);
                case 4:
                    fifthFloorTextView.setText("Fifth floor average queue: " +
                            eachFloorLength[count] / Simulator.MAX_MINUTES);
            }
        }

        totalRouteTextView.setText("Total route of elevator: " + simulator.getTotalRoute());
    }

    public static void main(String[] args) {
        launch(args);
    }
}