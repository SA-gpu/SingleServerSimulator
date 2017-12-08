package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Simulator {

    private int route;
    private static int MAX_FLOORS = 5;
    static double MAX_MINUTES = 60;
    private EventList futureEvents;
    private QueuingSystem queuingSystem;
    private Elevator elevator;
    private double clock;

    //Random rates
    private double arrivalRates[];
    private double meanMove;
    private double meanStop;

    private int[] queueLength;

    private ObservableList<Row> tableData;

    ObservableList<Row> getTableData() {
        return tableData;
    }

    Simulator() {

        route = 0;
        clock = 0.0;
        futureEvents = new EventList();
        queuingSystem = new QueuingSystem();
        elevator = new Elevator();

        arrivalRates = new double[MAX_FLOORS];
        meanMove = 1.0;
        meanStop = 1.0;

        for (int i = 0; i < MAX_FLOORS; i++) {
            arrivalRates[i] = 5;
        }

        //Data collection
        tableData = FXCollections.observableArrayList();

        // queue length for each floor
        queueLength = new int[MAX_FLOORS];
    }

    private void initialize() {

        //Scheduling first move
        futureEvents.enqueue(new QueueEvent(QueueEvent.MOVE, clock + RandUtil.exponential(meanMove), 0));

        //Schedule arrival for each floor
        for (int i = 0; i < MAX_FLOORS; i++) {
            futureEvents.enqueue(new QueueEvent(QueueEvent.ARRIVAL, clock + RandUtil.exponential(arrivalRates[i]), i));
        }
    }

    void simulate(double time) {

        initialize();
        while (clock < time * MAX_MINUTES) {

            String type = "";
            QueueEvent event = futureEvents.dequeue();
            clock = event.getTime();

            if (event.getType() == QueueEvent.ARRIVAL) {
                processArrival(event);
                type = "Entry";
            }
            else if (event.getType() == QueueEvent.STOP) {
                processStop(event);
                type = "Stop";
            }
            else if (event.getType() == QueueEvent.MOVE) {
                processMove(event);
                type = "Move";

                // counting the elevator routes
                if(elevator.getFloor() == 0){
                    route++;
                }
            }

            tableData.add(new Row(type, clock, event.getFloor(), route, Integer.toString(elevator.space),
                    queuingSystem.length(event.getFloor())));
        }
    }

    int getTotalRoute(){
        return this.route;
    }

    int[] getEachFloorQueueLength() {
        return queueLength;
    }

    private void processStop(QueueEvent event) {

        System.out.println("Stopping at: " + elevator.currentFloor);
        elevator.stop();
        elevator.unload();
        System.out.println("Capacity: " + elevator.space);

        while (elevator.hasSpace() && queuingSystem.length(elevator.getFloor()) > 0) {

            System.out.println("Before Load: " + queuingSystem.length(elevator.getFloor()));
            QueueEvent e = queuingSystem.dequeue(elevator.getFloor());
            System.out.print("Loading..." + "Floor:" + event.getFloor() + "   ");
            System.out.println(queuingSystem.length(elevator.getFloor()));
            System.out.println();
            System.out.println();
            elevator.load();
        }

        //Schedule move
        futureEvents.enqueue(new QueueEvent(QueueEvent.MOVE, clock + RandUtil.exponential(meanMove), elevator.currentFloor));
    }

    private void processMove(QueueEvent event) {
        System.out.println("Moving from: " + elevator.currentFloor);
        System.out.println("Capacity: " + elevator.space);
        elevator.move();
        //Schedule stop
        futureEvents.enqueue(new QueueEvent(QueueEvent.STOP, clock + RandUtil.exponential(meanStop), elevator.currentFloor));
    }

    private void processArrival(QueueEvent event) {

        if (!elevator.inMotion() && event.getFloor() == elevator.getFloor() && elevator.hasSpace()) {
            System.out.print("Loading..." + "Floor:" + event.getFloor() + "   ");
            elevator.load();
        }
        else {
            queuingSystem.enqueue(event.getFloor(), event);
            // calculate the each floor queue length!
            queueLength[event.getFloor()]++;
        }
        //Schedule next Arrival
        futureEvents.enqueue(new QueueEvent(QueueEvent.ARRIVAL, clock + RandUtil.exponential(arrivalRates[event.getFloor()]), event.getFloor()));
    }
}
