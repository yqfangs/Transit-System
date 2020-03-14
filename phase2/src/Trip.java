public class Trip {
    /**
     * card ID of a card
     */
    private int cardID;

    /**
     * time of the trip happen
     */
    private int time;

    /**
     * date of the trip happen
     */
    private int date;

    /**
     * location of the trip happen
     */
    private TransitSystem location;

    /**
     * tap in status
     */
    private boolean tapIn;

    /**
     * tap out status
     */
    private boolean tapOut;

    /**
     * fare of a trip
     */
    private FareCalculator fare;

    /**
     * Constructor of class
     *
     * @param cardID   card ID of a card
     * @param time     time of the trip happen
     * @param date     date of the trip happen
     * @param location location of the trip happen
     */
    Trip(int cardID, int time, int date, TransitSystem location) {
        this.cardID = cardID;
        this.time = time;
        this.date = date;
        this.location = location;
        this.tapIn = false;
        this.tapOut = false;
        this.fare = new FareCalculator();
    }

    /**
     * change the tap in status true to false, false to true
     */
    void changeTapInStatus() {
        this.tapIn = !tapIn;
    }

    /**
     * change the tap out status true to false, false to true
     */
    void changeTapOutStatus() {
        this.tapOut = !tapOut;
    }

    /**
     * getter of variable tapIn
     *
     * @return true if tapIn status is true vice versa
     */
    boolean isTapIn() {
        return tapIn;
    }

    /**
     * getter of variable tapOut
     *
     * @return true if tapOut status is true vice versa
     */
    boolean isTapOut() {
        return tapOut;
    }

    /**
     * getter of variable cardId
     *
     * @return variable cardID
     */
    int getCardID() {
        return cardID;
    }

    /**
     * getter of variable date
     *
     * @return variable date
     */
    int getDate() {
        return date;
    }

    /**
     * getter of variable time
     *
     * @return the variable time
     */
    int getTime() {
        return time;
    }

    /**
     * getter of variable location
     *
     * @return the variable location
     */
    TransitSystem getLocation() {
        return location;
    }

    /**
     * getter of fare
     *
     * @return the variable fare
     */
    FareCalculator getFare() {
        return fare;
    }

    /**
     * return true if the location is subway station vice versa
     *
     * @return return true if the location is subway station, vice versa
     */
    public boolean isStation() {
        return location instanceof SubwayStation;
    }

    /**
     * return true if the location is bus stop vice versa
     *
     * @return return true if the location of bus stop, vice versa
     */
    public boolean isStop() {
        return location instanceof BusStop;
    }
}
