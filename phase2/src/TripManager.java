import java.util.ArrayList;

class TripManager {

    /**
     * ArrayList of ArrayList of trips already been paid
     */
    private ArrayList<ArrayList<Trip>> finishedTrips;

    /**
     * ArrayList of ArrayList of trip that have not been paid
     */
    private ArrayList<ArrayList<Trip>> unfinishedTrips;

    /**
     * a CardsManager instance, this class will use some of the methods in CardsManager
     */
    private CardsManager cm;

    /**
     * Constructor of class
     */
    TripManager() {
        this.finishedTrips = new ArrayList<>();
        this.unfinishedTrips = new ArrayList<>();
    }

    /**
     * take in a CardsManager instance and make the local variable cm reference to it
     *
     * @param MainCardManager the CardsManager instance will be take in
     */
    void syncCardManager(CardsManager MainCardManager) {
        cm = MainCardManager;
    }

    /**
     * getter of local variable finishedTrips
     *
     * @return the local variable finishedTrips
     */
    ArrayList<ArrayList<Trip>> getFinishedTrips() {
        return finishedTrips;
    }

    /**
     * getter of local variable unfinishedTrips
     *
     * @return the local variable unfinishedTrips
     */
    ArrayList<ArrayList<Trip>> getUnfinishedTrips() {
        return unfinishedTrips;
    }

    /**
     * String representation of a list of trip
     *
     * @param tripList ArrayList of Trip
     * @return the String representation of a list of trip
     */
    String toString(ArrayList<Trip> tripList) {
        if (tripList.size() == 1) {
            if (tripList.get(0).isTapIn()) {
                return "Card ID: "
                        + tripList.get(0).getCardID()
                        + ", \n"
                        + "Tap in at: "
                        + tripList.get(0).getLocation().toString()
                        + ", "
                        + "Date: "
                        + String.valueOf(tripList.get(0).getDate())
                        + ", "
                        + "Time: "
                        + String.valueOf(tripList.get(0).getTime())
                        + "; "
                        + "Did not Tap Out.\n"
                        + "Total fare: "
                        + String.valueOf(cm.findCard(tripList.get(0).getCardID()).calculateTripFare(tripList));
            } else { // tap out
                return "Did not Tap In; "
                        + "Card ID: "
                        + tripList.get(0).getCardID()
                        + ", \n"
                        + "Tap out at: "
                        + tripList.get(0).getLocation().toString()
                        + ", "
                        + "Date: "
                        + String.valueOf(tripList.get(0).getDate())
                        + ", "
                        + "Time: "
                        + String.valueOf(tripList.get(0).getTime())
                        + ".\n"
                        + "Total fare: "
                        + String.valueOf(cm.findCard(tripList.get(0).getCardID()).calculateTripFare(tripList));
            }
        } else if (tripList.size() == 2) {
            return "Card ID: "
                    + tripList.get(0).getCardID()
                    + ", \n"
                    + "Tap in at: "
                    + tripList.get(0).getLocation().toString()
                    + ", "
                    + "Date: "
                    + String.valueOf(tripList.get(0).getDate())
                    + ", "
                    + "Time: "
                    + String.valueOf(tripList.get(0).getTime())
                    + "; \n"
                    + "Tap out at: "
                    + tripList.get(1).getLocation().toString()
                    + ", "
                    + "Date: "
                    + String.valueOf(tripList.get(1).getDate())
                    + ", "
                    + "Time: "
                    + String.valueOf(tripList.get(1).getTime())
                    + ".\n"
                    + "Total fare: "
                    + String.valueOf(cm.findCard(tripList.get(0).getCardID()).calculateTripFare(tripList));
        } else if (tripList.size() == 3) {
            return "Card ID: "
                    + tripList.get(0).getCardID()
                    + ", \n"
                    + "Tap in at: "
                    + tripList.get(0).getLocation().toString()
                    + ", "
                    + "Date: "
                    + String.valueOf(tripList.get(0).getDate())
                    + ", "
                    + "Time: "
                    + String.valueOf(tripList.get(0).getTime())
                    + "; "
                    + "Transfer at: "
                    + tripList.get(2).getLocation().toString()
                    + ", "
                    + "Date: "
                    + String.valueOf(tripList.get(2).getDate())
                    + ", "
                    + "Time: "
                    + String.valueOf(tripList.get(2).getTime())
                    + "; "
                    + "Did not Tap Out.\n"
                    + "Total fare: "
                    + String.valueOf(cm.findCard(tripList.get(0).getCardID()).calculateTripFare(tripList));
        } else if (tripList.size() == 4) {
            return "Card ID: "
                    + tripList.get(0).getCardID()
                    + ", \n"
                    + "Tap in at: "
                    + tripList.get(0).getLocation().toString()
                    + ", "
                    + "Date: "
                    + String.valueOf(tripList.get(0).getDate())
                    + ", "
                    + "Time: "
                    + String.valueOf(tripList.get(0).getTime())
                    + "; \n"
                    + "Transfer at: "
                    + tripList.get(2).getLocation().toString()
                    + ", "
                    + "Date: "
                    + String.valueOf(tripList.get(2).getDate())
                    + ", "
                    + "Time: "
                    + String.valueOf(tripList.get(2).getTime())
                    + "; \n"
                    + "Tap out at: "
                    + tripList.get(3).getLocation().toString()
                    + ", "
                    + "Date: "
                    + String.valueOf(tripList.get(3).getDate())
                    + ", "
                    + "Time: "
                    + String.valueOf(tripList.get(3).getTime())
                    + ".\n"
                    + "Total fare: "
                    + String.valueOf(cm.findCard(tripList.get(0).getCardID()).calculateTripFare(tripList));
        }
        return "There is no Trip.";
    }

    /**
     * determine the bus trip take in is valid/invalid and add it into an ArrayList of ArrayList of trip
     *
     * @param trip a trip to be take in
     */
    private void addBusTrip(Trip trip) {
        Card tripCard = cm.findCard(trip.getCardID());
        if (trip.isTapIn()) {
            boolean didILoopUnfinishedTrips = false;
            for (ArrayList<Trip> tripList : unfinishedTrips) {
                if (trip.getCardID()
                        == tripList.get(0).getCardID()) { // person tap in twice without tapping out
                    if (tripList.get(tripList.size() - 1).getLocation()
                            instanceof SubwayStation) { // last trip is on subway
                        didILoopUnfinishedTrips = true;
                        trip.getFare().tapOnBus(); // bus tap in charge
                        tripCard.deductFare(trip.getFare()); // deduct fare
                        doubleTapInAfterSubway(tripList.get(tripList.size() - 1), tripCard, tripList, trip);
                        break;
                    } else if (tripList.get(tripList.size() - 1).getLocation()
                            instanceof BusStop) { // last trip is on bus
                        didILoopUnfinishedTrips = true;
                        trip.getFare().tapOnBus(); // bus tap in charge
                        tripCard.deductFare(trip.getFare()); // deduct fare
                        doubleTapInAfterBus(tripCard, tripList, trip);
                        break;
                    }
                }
            }
            if (!didILoopUnfinishedTrips) {
                boolean isThereTripOnFinishedTrips = false;
                for (ArrayList<Trip> tripList1 : finishedTrips) { // find if there is a transfer
                    if (tripList1.get(tripList1.size() - 1).getLocation() instanceof SubwayStation
                            && tripList1.size() == 2
                            && validTransfer(tripList1.get(0), tripList1.get(1), trip)) { // do transfer
                        isThereTripOnFinishedTrips = true;
                        if (tripList1.get(1).getFare().getSingleFare() > 4
                                && tripList1.get(1).getFare().getSingleFare() < 6) {
                            trip.getFare()
                                    .updateFare(
                                            FareCalculator.getMaxFare() - tripList1.get(1).getFare().getSingleFare());
                            tripCard.deductFare(trip.getFare()); // deduct the fare
                            transferringTrip(tripCard, tripList1, trip);
                            break;
                        } else if (tripList1.get(1).getFare().getSingleFare() == 6) { // max fare, no deduct
                            transferringTrip(tripCard, tripList1, trip);
                            break;
                        } else if (tripList1.get(1).getFare().getSingleFare() <= 4) {
                            trip.getFare().tapOnBus();
                            tripCard.deductFare(trip.getFare());
                            transferringTrip(tripCard, tripList1, trip);
                            break;
                        }
                    }
                }
                if (!isThereTripOnFinishedTrips) { // valid trip
                    trip.getFare().tapOnBus();
                    tripCard.deductFare(trip.getFare());
                    ArrayList<Trip> newTripList = new ArrayList<>();
                    newTripList.add(trip);
                    unfinishedTrips.add(newTripList);
                }
            }
        } else if (trip.isTapOut()) {
            boolean isThereTripOnUnfinishedTrips = false;
            for (ArrayList<Trip> tripList2 : unfinishedTrips) {
                if (trip.getCardID() == tripList2.get(0).getCardID()) {
                    isThereTripOnUnfinishedTrips = true;
                    if (tripList2.get(tripList2.size() - 1).getLocation()
                            instanceof BusStop // last tap in on bus
                            && tripList2.get(tripList2.size() - 1).getDate() == trip.getDate()) { // same data
                        tapOut(tripCard, tripList2, trip);
                        break;
                    } else if (tripList2.get(tripList2.size() - 1).getLocation() instanceof BusStop
                            && tripList2.get(tripList2.size() - 1).getDate()
                            != trip.getDate()) { // tap out noe sam data
                        // person forget to tap out on the bus last trip
                        trip.getFare().tapOnBus();
                        tripCard.deductFare(trip.getFare());
                        notMatchingTapInTapOut(tripCard, tripList2, trip);
                        break;
                    } else if (tripList2.get(tripList2.size() - 1).getLocation() instanceof SubwayStation) {
                        // person did not tap out on subway station last trip
                        tripList2.get(tripList2.size() - 1).getFare().updateFare(FareCalculator.getMaxFare());
                        tripCard.deductFare(tripList2.get(tripList2.size() - 1).getFare());
                        trip.getFare().tapOnBus();
                        tripCard.deductFare(trip.getFare());
                        notMatchingTapInTapOut(tripCard, tripList2, trip);
                        break;
                    }
                }
            }
            if (!isThereTripOnUnfinishedTrips) { // there is not same cardID on unfinishedTrips
                // this trip is only tap out without tapping in
                trip.getFare().tapOnBus();
                tripCard.deductFare(trip.getFare());
                ArrayList<Trip> newTripList = new ArrayList<>();
                newTripList.add(trip);
                finishedTrips.add(newTripList);
                tripCard.addFinishedTrip(newTripList);
            }
        }
    }

    /**
     * determine the subway trip take in is valid/invalid and add it into an ArrayList of ArrayList of trip
     *
     * @param trip a trip to be take in
     */
    private void addSubwayTrip(Trip trip) {
        Card tripCard = cm.findCard(trip.getCardID());
        if (trip.isTapIn()) {
            for (ArrayList<Trip> tripList : unfinishedTrips) {
                if (trip.getCardID()
                        == tripList.get(0).getCardID()) { // person tap in two times without tapping out
                    Trip lastTapIn = tripList.get(tripList.size() - 1);
                    if (lastTapIn.getLocation() instanceof SubwayStation) { // The previous trip is subway
                        doubleTapInAfterSubway(lastTapIn, tripCard, tripList, trip);
                        break;
                    } else if (lastTapIn.getLocation() instanceof BusStop) { // The previous trip is bus
                        doubleTapInAfterBus(tripCard, tripList, trip);
                        break;
                    }
                }
            }
            boolean isThereTripOnFinishedTrips = false;
            for (ArrayList<Trip> tripList1 : finishedTrips) { // find if there is a transfer
                if ((tripList1.size() == 2)
                        && validTransfer(tripList1.get(0), tripList1.get(1), trip)
                        && (tripList1.get(tripList1.size() - 1).getLocation()
                        instanceof BusStop)) { // within 2 hours limit, conditions satisfied continuous trip
                    isThereTripOnFinishedTrips = true;
                    transferringTrip(tripCard, tripList1, trip);
                    break;
                }
            }
            if (!isThereTripOnFinishedTrips) { // There is no transferring, create a brand new trip and
                // add to unfinishedList.
                ArrayList<Trip> newTripList = new ArrayList<>();
                newTripList.add(trip);
                unfinishedTrips.add(newTripList);
            }
        } else if (trip.isTapOut()) {
            boolean isThereTripOnUnfinishedTrips = false;
            for (ArrayList<Trip> tripList2 : unfinishedTrips) {
                if (trip.getCardID() == tripList2.get(0).getCardID()) { // found a corresponding tap-in
                    isThereTripOnUnfinishedTrips = true;
                    Trip lastTapIn = tripList2.get(tripList2.size() - 1);
                    if (lastTapIn.getLocation()
                            instanceof BusStop) { // did not tap out at subway and did not tap in on bus
                        trip.getFare().updateFare(FareCalculator.getMaxFare()); // set fare to max fare
                        tripCard.deductFare(trip.getFare()); // Deduct fare from the card
                        notMatchingTapInTapOut(tripCard, tripList2, trip);
                        break;
                    } else if (lastTapIn.getLocation() instanceof SubwayStation) {
                        if (tripList2.size() == 1) {
                            trip.getFare().tapOffSubway(tripList2.get(0).getLocation(), trip.getLocation());
                            tripCard.deductFare(trip.getFare());
                            tapOut(tripCard, tripList2, trip);
                            break;
                        }
                        if (tripList2.size() == 3) { //
                            trip.getFare()
                                    .tapOffSubway(
                                            tripList2.get(2).getLocation(),
                                            trip.getLocation(),
                                            FareCalculator.getMaxFare() - FareCalculator.getBusFare());
                            tripCard.deductFare(trip.getFare());
                            tapOut(tripCard, tripList2, trip);
                            break;
                        }
                    }
                }
            }
            if (!isThereTripOnUnfinishedTrips) { // There is no corresponding tap-in.
                trip.getFare().updateFare(FareCalculator.getMaxFare());
                tripCard.deductFare(trip.getFare());
                ArrayList<Trip> newTripList = new ArrayList<>();
                newTripList.add(trip);
                finishedTrips.add(newTripList);
                tripCard.addFinishedTrip(newTripList);
            }
        }
    }

    /**
     * when a transfer happens (bus to subway, subway to bus), determine whether the transfer is valid
     * we say the transfer is valid only when the latest trip of the user have both tap in and tap out from
     * a stop/station and then he can transfer to a station/stop
     *
     * @param oldTrip1 the latest trip of the user where the tap in status is true
     * @param oldTrip2 the latest trip of the user where the tap out status is true
     * @param newTrip  the trip which the user is going to take
     * @return true if the latest trip of the user have both tap in and tap out from
     * a stop/station and then he can transfer to a station/stop, otherwise return false
     */
    private boolean validTransfer(Trip oldTrip1, Trip oldTrip2, Trip newTrip) {
        return (oldTrip1.getCardID() == newTrip.getCardID())
                && (newTrip.getTime() - oldTrip1.getTime() <= 200) // not pass 2 hours
                && (oldTrip1.getDate() == newTrip.getDate()) // on the same date
                && (oldTrip2.getLocation().getName().equals(newTrip.getLocation().getName()));
    }

    /**
     * deal with the problem when the user tap in two times in a row in a subway station
     *
     * @param lastTrip         the last trip of the user
     * @param card1            the Card that just tapped in twice
     * @param previousTripList the list of the last trip, right here it means the previous trip where the user tapped in
     * @param newTrip          after two tapped in happens, put this two trips with both tap in status true inside an ArrayList
     *                         and put it in the unfinishedTrips list
     */
    private void doubleTapInAfterSubway(
            Trip lastTrip, Card card1, ArrayList<Trip> previousTripList, Trip newTrip) {
        lastTrip.getFare().updateFare(FareCalculator.getMaxFare()); // set fare to max fare
        card1.deductFare(lastTrip.getFare()); // Deduct fare from the card
        finishedTrips.add(previousTripList); // add tripList to finishedTrips List
        card1.addFinishedTrip(previousTripList); // add tripList to to card
        unfinishedTrips.remove(previousTripList); // removed the finished trip on unfinishedTrips list

        ArrayList<Trip> newTripList = new ArrayList<>();
        newTripList.add(newTrip); // Encapsulate the new trip inside a ArrayList
        unfinishedTrips.add(newTripList); // add the new trip tap in to unfinishedTrips list
    }

    /**
     * deal with the problem when the user tap in two times in a row in a bus station
     *
     * @param card1            the Card that just tapped in twice
     * @param previousTripList the list of the last trip, right here it means the previous trip where the user tapped in
     * @param newTrip          after two tapped in happens, put this two trips with both tap in status true inside an ArrayList
     *                         and put it in the unfinishedTrips list
     */
    private void doubleTapInAfterBus(Card card1, ArrayList<Trip> previousTripList, Trip newTrip) {
        unfinishedTrips.remove(previousTripList);
        finishedTrips.add(previousTripList); // finished trip without tap out
        card1.addFinishedTrip(previousTripList);
        ArrayList<Trip> newTripList = new ArrayList<>();
        newTripList.add(newTrip);
        unfinishedTrips.add(newTripList);
    }

    /**
     * when a user successful transferred, put the trip in transfer to the unfinished trip list and remove the trip
     * before he transferred from the finishedTrips list
     *
     * @param card1    the Card which the user is using
     * @param tripList an ArrayList of trip which contains of successful paid trip
     * @param newTrip  an ArrayList of trip which contains the new transferred trip
     */
    private void transferringTrip(Card card1, ArrayList<Trip> tripList, Trip newTrip) {
        finishedTrips.remove(tripList);
        card1.removeFinishedTrip(tripList);
        tripList.add(newTrip);
        unfinishedTrips.add(tripList);
    }

    /**
     * when a user successful taped out, put the trip in transfer to the finished trip list and remove the trip
     * from the unfinished trip
     *
     * @param card1    the Card which the user is using
     * @param tripList an ArrayList of trip which contains of successful paid trip
     * @param newTrip  an ArrayList of trip which contains the new transferred trip
     */
    private void tapOut(Card card1, ArrayList<Trip> tripList, Trip newTrip) {
        unfinishedTrips.remove(tripList);
        tripList.add(newTrip);
        finishedTrips.add(tripList);
        card1.addFinishedTrip(tripList);
    }

    /**
     * when a user tap in and forgot to tap out, but the next trip is a tap out, remove the single tap in trip from
     * the unfinished trip ArrayList and add it in the finished trip ArrayList, create a new ArrayList and add the trip
     * with only tap out to the list, and add this ArrayList of Trip to the finished trip ArrayList
     *
     * @param tripCard  the Card Instance which the user is using
     * @param tripList2 an ArrayList of trip which contains the single tap in trip
     * @param trip      an ArrayList of trip which contains the single tap out trip
     */
    private void notMatchingTapInTapOut(Card tripCard, ArrayList<Trip> tripList2, Trip trip) {
        unfinishedTrips.remove(tripList2); // remove the tap-in but no tap-out bus trip from unfinished
        finishedTrips.add(tripList2); // add it in the finished trip
        tripCard.addFinishedTrip(tripList2);
        ArrayList<Trip> newTripList = new ArrayList<>(); // create new arrayList to encapsulate trip
        newTripList.add(trip);
        finishedTrips.add(newTripList); // add the tap-out only subway trip into finished trips
        tripCard.addFinishedTrip(newTripList);
    }

    /**
     * call the helper function addSubwayTrip or addBusTrip if the location is station or stop
     *
     * @param trip the trip to be add
     */
    void addTrip(Trip trip) {
        if (trip.getLocation() instanceof SubwayStation) {
            addSubwayTrip(trip);
        } else if (trip.getLocation() instanceof BusStop) {
            addBusTrip(trip);
        }
    }
}
