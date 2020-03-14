import java.util.ArrayList;

public class Card {

    /**
     * Card ID of a card
     */
    private int cardId;

    /**
     * Card Balance of a card
     */
    private double balance = 19;

    /**
     * the suspend status of a card
     */
    private boolean suspend = false;

    /**
     * the ArrayList of all checked trips specifically for one/this card
     */
    private ArrayList<ArrayList<Trip>> finishedTrip;

    /**
     * Constructor of class
     *
     * @param cardId Card ID of card
     */
    Card(int cardId) {
        this.cardId = cardId;
        this.finishedTrip = new ArrayList<>();
    }

    /**
     * getter of local variable cardId
     *
     * @return the local variable cardId
     */
    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId){
        this.cardId = cardId;
    }

    /**
     * getter of balance in this card
     *
     * @return the value of balance in this card
     */
    public double getBalance() {
        return balance;
    }


    /**
     * add balance of this card with specific amount
     *
     * @param amount the amount to add
     */
    void addMoney(double amount) {
        balance += amount;
    }

    /**
     * Add a paid trip
     *
     * @param tripList an ArrayList of trip that have already been paid by this card
     */
    void addFinishedTrip(ArrayList<Trip> tripList) {
        finishedTrip.add(tripList);
    }

    /**
     * remove a paid trip
     *
     * @param tripList an ArrayList of trip that have been paid by this card you want to remove
     */
    void removeFinishedTrip(ArrayList<Trip> tripList) {
        finishedTrip.remove(tripList);
    }

    /**
     * clear old trips which was a year ago, from finished trips list,
     *
     * @param date integer which is date of today
     */
    public void clearOldFinishedTrip(int date){
        for(ArrayList<Trip> trip: finishedTrip){
            if((date - (trip.get(trip.size() - 1).getDate()) > 10000)){
                removeFinishedTrip(trip);
            }
        }
    }

    /**
     * getter of finished trip
     *
     * @return the finished trip
     */
    ArrayList<ArrayList<Trip>> getFinishedTrip() {
        return finishedTrip;
    }

    /**
     * getter of suspend status
     *
     * @return the suspend status
     */
    boolean getSuspend() {
        return suspend;
    }

    /**
     * suspend the card
     */
    void suspendCard() {
        suspend = true;
    }

    /**
     * unsuspend the card
     */
    void unsuspendCard(){suspend = false;}

    /**
     * equal method of Card
     *
     * @param otherCard the other Card to compare
     * @return whether if two card have the same card ID, then the two cards are the same
     */
    public boolean equals(Card otherCard) {
        return this.cardId == otherCard.cardId;
    }

    /**
     * deduct balance with a given fare instance, and call the method in the FareCalculator to get the amount of
     * deduction
     *
     * @param fare the fare instance where the amount of fare is stored
     */
    void deductFare(FareCalculator fare) {
        balance -= fare.getSingleFare();
    }

    /**
     * Calculate the fare of a trip
     *
     * @param tripList an ArrayList of trip which will often contain a paid trip
     * @return the amount of fare of a trip
     */
    double calculateTripFare(ArrayList<Trip> tripList) {
        double result = 0;
        for (Trip trip : tripList) {
            result += trip.getFare().getSingleFare();
        }
        return result;
    }

    /**
     * Check if the balance is less than 6 or not.
     *
     * @return the balance less than six is true or false.
     */
    public boolean checkBalanceLessThanSix(){
        return balance < 6;
    }

    /**
     * the toString method of Card
     *
     * @return the string representation of a Card
     */
    public String toString() {
        String word;
        if (suspend) {
            word = "Suspended";
        } else {
            word = "not suspend";
        }
        return "Card Id :" + String.valueOf(cardId) + " with Current balance $" + String.valueOf(balance) + " , this card" +
                " is currently " + word;
    }


}
