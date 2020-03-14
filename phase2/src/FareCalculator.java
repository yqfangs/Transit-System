class FareCalculator {

    /**
     * the amount of fare of a single completed trip
     */
    private double singleFare;
    /**
     * the max fare can be deduct
     */
    private static final int MAX_FARE = 6;
    /**
     * the initial bus fare
     */
    private static final int BUS_FARE = 2;
    /**
     * the station fare of one station you pass through
     */
    private static final double STATION_FARE = 0.5;

    /**
     * Constructor of class
     */
    FareCalculator() {
        this.singleFare = 0;
    }

    /**
     * getter of the local variable singleFare
     *
     * @return the local variable singleFare
     */
    double getSingleFare() {
        return singleFare;
    }

    /**
     * add the amount of fare with some given rules
     *
     * @param fare the amount of fare to add
     */
    void updateFare(double fare) {
        if (singleFare <= MAX_FARE) {
            if ((fare + singleFare) > MAX_FARE) {
                singleFare = MAX_FARE;
            } else {
                singleFare += fare;
            }
        }
    }

    /**
     * overload method of updateFare with two parameters, this method is used when transfer happens
     * (bus to subway or vice versa) to control the max amount of deduction
     *
     * @param fare the amount of fare
     * @param max  the amount of controlled max
     */
    private void updateFare(double fare, double max) {
        if (singleFare <= max) {
            if ((fare + singleFare) > max) {
                singleFare = max;
            } else {
                singleFare += fare;
            }
        }
    }

    /**
     * if the tap in is in a Bus stop , then add the fare by the amount of BUS_FARE
     */
    void tapOnBus() {
        updateFare(BUS_FARE);
    }

    /**
     * getter of variable BUS_FARE
     *
     * @return the variable BUS_FARE
     */
    static int getBusFare() {
        return BUS_FARE;
    }

    /**
     * getter of variable MAX_FARE
     *
     * @return the variable MAX_FARE
     */
    static int getMaxFare() {
        return MAX_FARE;
    }

    /**
     * there is tap out in subway station, and update the fare amount
     *
     * @param start the station user tap in
     * @param end   the station user tap out
     */
    void tapOffSubway(TransitSystem start, TransitSystem end) {
        updateFare(subwayFare(start, end));
    }

    /**
     * overload method tapOffSubway there is tap out in subway station update the fare amount,adding a parameter
     * maxFare to control the max fare deduction when transfer (bus to subway or vice versa)
     *
     * @param start   the station user tap in
     * @param end     the station user tap out
     * @param maxFare the amount of controlled max fare
     */
    void tapOffSubway(TransitSystem start, TransitSystem end, double maxFare) {
        updateFare(subwayFare(start, end), maxFare);
    }

    /**
     * Calculate the subway fare with given tap in station and tap out station
     *
     * @param start the tap in station
     * @param end   the tap out station
     * @return the amount of fare for a trip with given start and end point
     */
    private double subwayFare(TransitSystem start, TransitSystem end) {
        return STATION_FARE * numStation(start, end);
    }

    // given start and end station, find the shortest route

    /**
     * method to calculate the number of stations/stops between a start point and a end point
     *
     * @param start the start station/stop of the trip
     * @param end   the end station/stop of the trip
     * @return the number of station between two stops/stations
     */
    private int numStation(TransitSystem start, TransitSystem end) {
        return Math.abs(start.getNumber() - end.getNumber());

    }

}
