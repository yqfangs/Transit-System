public class SubwayStation extends TransitSystem {
    /**
     * Constructor of class
     *
     * @param name   name of the station
     * @param number number of the station
     */
    SubwayStation(String name, int number) {
        super(name, number);
    }

    /**
     * toString method of SubwayStation
     *
     * @return the String representation of SubwayStation
     */
    public String toString() {
        return "Subway Station #" + String.valueOf(getNumber()) + " " + getName();
    }
}
