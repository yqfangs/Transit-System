import java.util.ArrayList;

public class CardsManager {

    /**
     * an ArrayList of Card
     */
    private static ArrayList<Card> allCardsList;

    /**
     * Constructor of class
     */
    CardsManager() {
        allCardsList = new ArrayList<Card>();
    }

    void addCard(Card c) {
        if (!allCardsList.contains(c)) {
            allCardsList.add(c);
        }
    }

    void removeCard(Card c){
        allCardsList.remove(c);
    }

    /**
     * find a Card in the all CardList
     *
     * @param cardId the cardId you wanna find
     * @return a Card instance match with the cardId, or return null if didn't find a match card
     */
    Card findCard(int cardId) {
        int i = 0;
        while (i < allCardsList.size()) {
            if (allCardsList.get(i).getCardId() == (cardId)) {
                return allCardsList.get(i);
            }
            i++;
        }
        return null;
    }

    /**
     * determine whether a card have a valid balance for the trip
     *
     * @param cardId the cardId of the card you wanna test
     * @return true if the balance if valid false if the balance is invalid
     */
    boolean validBalance(int cardId) {
        return findCard(cardId).getBalance() >= 0;

    }

    /**
     * determine whether a card is suspended by the user
     *
     * @param cardId the cardId of the card you want to see
     * @return true if suspended false if not suspended
     */
    boolean isSuspend(int cardId) {
        return findCard(cardId).getSuspend();
    }

    /**
     * getter of the local variable allCardList
     *
     * @return the local variable allCardList
     */
    public ArrayList<Card> getAllCardsList() {
        return allCardsList;
    }
}
