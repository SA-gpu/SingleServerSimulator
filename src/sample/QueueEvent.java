package sample;

/**
 * Created by HarisMehmood on 1/2/17.
 */
public class QueueEvent {

    static final int ARRIVAL = 0;
    static final int STOP = 1;
    static final int MOVE = 2;

    private int type;
    private double time;
    private int floor;

    QueueEvent(int type, double time, int floor) {
        this.type = type;
        this.time = time;
        this.floor = floor;
    }

    int getType() {
        return type;
    }

    double getTime() {
        return time;
    }

    int getFloor() {
        return floor;
    }

}
