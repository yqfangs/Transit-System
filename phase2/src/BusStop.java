public class BusStop extends TransitSystem { // Subclass of TransitSystem
    /**
     * Constructor of class
     *
     * @param name   name of bus stop
     * @param number number of bus stop
     */
    BusStop(String name, int number) {
        super(name, number);
    }

    /**
     * toString method of BusStop class
     *
     * @return the string representation of a BusStop instance
     */
    @Override
    public String toString() {
        return "Bus Stop #" + String.valueOf(getNumber()) + " " + getName();
    }

}
