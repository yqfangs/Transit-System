import java.util.ArrayList;

public class TransitSystem {
    /**
     * name of the station/stop
     */
    private String name;

    /**
     * System open or close
     */
    private static boolean isSystemOpen;

    /**
     * number of station/stop
     */
    private int number;

    /**
     * ArrayList of stations
     */
    static ArrayList<SubwayStation> stations = new ArrayList<>();

    /**
     * ArrayList of Stops
     */
    static ArrayList<BusStop> stops = new ArrayList<>();

    /**
     * Constructor of class
     *
     * @param name   name of station/stop
     * @param number number of station/stop
     */
    TransitSystem(String name, int number) {
        this.name = name;
        this.number = number;
        this.isSystemOpen = false;
    }

    /**
     * equal method of TransitSystem
     *
     * @param other the object to compare
     * @return true if other is in instance of TransitSystem, have the same name, same station/stop number
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        TransitSystem s = (TransitSystem) other;
        if (this.name == null) {
            if (s.name != null) {
                return false;
            }
        } else if (this.number != s.number) {
            return false;
        }
        return true;
    }

    /**
     * getter of local variable name
     *
     * @return the local variable name
     */
    String getName() {
        return name;
    }

    /**
     * getter of local variable number
     *
     * @return the local variable number
     */
    int getNumber() {
        return number;
    }

    /**
     * find bus stop by bus stop name
     *
     * @param busName bus stop name
     * @return the stop if found, return null otherwise
     */
    static BusStop findBusLocation(String busName) {
        for (BusStop stop : stops) {
            if (stop.getName().equals(busName)) {
                return stop;
            }
        }
        return null;
    }

    /**
     * find subway station by subway station name
     *
     * @param subName subway station name
     * @return the station if found, return null otherwise
     */
    static SubwayStation findSubLocation(String subName) {
        for (SubwayStation station : stations) {
            if (station.getName().equals(subName)) {
                return station;
            }
        }
        return null;
    }

    /**
     * Check if the system is open or close.
     * @return the boolean whether the system is open or not.
     */
    public static boolean checkIsSystemOpen() {
        return isSystemOpen;
    }

    /**
     * Every night after 23:59 PM, the Transit System will close.
     */
    public static void closeSystem() {
        isSystemOpen = false;
    }

    /**
     * Every morning after 5:00 AM, the Transit System will open.
     */
    public static void openSystem() {
        isSystemOpen = true;
    }
}
