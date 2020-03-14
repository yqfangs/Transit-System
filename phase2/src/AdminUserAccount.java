import java.util.ArrayList;

public class AdminUserAccount {

    /**
     * User password of a admin user account
     */
    private String password;

    /**
     * User name of a admin user account
     */
    private String username;

    /**
     * Email of a admin user account
     */
    private String email;

    /**
     * Operating cost of the system (for phase 2)
     */
    private double cost;

    /**
     * ArrayList of all successful checked trips
     */
    private ArrayList<ArrayList<Trip>> allFinishedTrip;

    /**
     * ArrayList of all trips that have not been checked
     */
    private ArrayList<ArrayList<Trip>> allUnfinishedTrip;

    /**
     * Class constructor
     *
     * @param username The username of an admin user account
     * @param email    The Email of an admin user account
     */
    AdminUserAccount(String username, String email, String password) {
    this.password = password;
    this.username = username;
    this.email = email;
  }

  public String getPassword(){
        return this.password;
  }
  public void setPassword(String newPassword){this.password = newPassword;}

    /**
     * A getter to get all finished trips
     * @return list of list of Trips.
     */
    public ArrayList<ArrayList<Trip>> getAllFinishedTrip() {
        return allFinishedTrip;
    }

    /**
     * A setter that takes in an ArrayList and reference  a local variable to that object
     *
     * @param allFinishedTrip An ArrayList for All finished Trip(the trip that successful deducted fare from a card)
     */
  void syncAllFinishedTrip(ArrayList<ArrayList<Trip>> allFinishedTrip) {
    this.allFinishedTrip = allFinishedTrip;
  }

    /**
     *A setter that takes in an ArrayList and reference  a local variable to that object
     *
     * @param allUnfinishedTrip An ArrayList for All unfinished Trip(the trip that has not been deducted
     *                          fare from a card)
     */
  void syncAllUnfinishedTrip(ArrayList<ArrayList<Trip>> allUnfinishedTrip) {
        this.allUnfinishedTrip = allUnfinishedTrip;
    }

    /**
     *Calculate the value of subway revenue on a specific date
     *
     * @param date the specific date of the revenue
     * @return the value of total subway revenue on a specific date
     */
    private double calculateDailySubRevenue(int date) {
    double result = 0;
    for (ArrayList<Trip> tripList : allFinishedTrip) {
      if (tripList.size() == 1
              && tripList.get(0).getLocation() instanceof SubwayStation
              && tripList.get(0).getDate() == date) {
        result += tripList.get(0).getFare().getSingleFare();
      } else if (tripList.size() == 2
              && tripList.get(1).getLocation() instanceof SubwayStation
              && tripList.get(1).getDate() == date) {
        result += tripList.get(1).getFare().getSingleFare();
      } else if (tripList.size() == 3 && tripList.get(2).getDate() == date) {
        if (tripList.get(1).getLocation() instanceof SubwayStation) {
          result += tripList.get(1).getFare().getSingleFare();
        } else if (tripList.get(1).getLocation() instanceof BusStop) {
          result += tripList.get(2).getFare().getSingleFare();
        }
      } else if (tripList.size() == 4 && tripList.get(3).getDate() == date) {
        if (tripList.get(1).getLocation() instanceof SubwayStation) {
          result += tripList.get(1).getFare().getSingleFare();
        } else if (tripList.get(1).getLocation() instanceof BusStop) {
          result += tripList.get(3).getFare().getSingleFare();
        }
      }
    }
//    for(ArrayList<Trip> tripList: allUnfinishedTrip){
//        if(tripList.size() == 3){ //subway transfer to bus but but not tap out
//            if(tripList.get(1).getLocation() instanceof SubwayStation
//                    && tripList.get(1).getDate() == date){
//                result += tripList.get(1).getFare().getSingleFare();
//            }
//        }
//    }
    return result;
  }

    /**
     * Calculate the value of bus revenue on a specific date
     *
     * @param date the specific date of the revenue
     * @return the value of total bus revenue on a specific date
     */
  private double calculateDailyBusRevenue(int date) {
      double result = 0;
      for (ArrayList<Trip> tripList : allFinishedTrip) {
          if (tripList.size() == 1
                  && tripList.get(0).getLocation() instanceof BusStop
                  && tripList.get(0).getDate() == date) {
              result += tripList.get(0).getFare().getSingleFare();
          } else if (tripList.size() == 2
                  && tripList.get(1).getLocation() instanceof BusStop
                  && tripList.get(1).getDate() == date) {
              result += tripList.get(0).getFare().getSingleFare();
          } else if (tripList.size() == 3 && tripList.get(2).getDate() == date) {
              if (tripList.get(1).getLocation() instanceof SubwayStation) {
                  result += tripList.get(2).getFare().getSingleFare();
              } else if (tripList.get(1).getLocation() instanceof BusStop) {
                  result += tripList.get(0).getFare().getSingleFare();
              }
          } else if (tripList.size() == 4 && tripList.get(3).getDate() == date) {
              if (tripList.get(1).getLocation() instanceof SubwayStation) {
                  result += tripList.get(2).getFare().getSingleFare();
              } else if (tripList.get(1).getLocation() instanceof BusStop) {
                  result += tripList.get(0).getFare().getSingleFare();
              }
          }
      }
//      for(ArrayList<Trip> tripList: allUnfinishedTrip){
//          if(tripList.size() == 1){
//              if(tripList.get(0).getLocation() instanceof BusStop
//                      && tripList.get(0).getDate() == date){
//                  result += tripList.get(0).getFare().getSingleFare();
//              }
//          }else if(tripList.size() == 3){
//              if(tripList.get(1).getLocation() instanceof SubwayStation
//                      && tripList.get(1).getDate() == date){ // sub transfer bus, bus not tap out
//                  result += tripList.get(2).getFare().getSingleFare();
//              }else if(tripList.get(1).getLocation() instanceof BusStop
//                      && tripList.get(1).getDate() == date){ // bus transfer sub, sub not tap out
//                  result += tripList.get(0).getFare().getSingleFare();
//              }
//          }
//      }
      return result;
  }

    /**
     * getter of username
     * @return the value of the local variable username
     */
  public String getName() {
    return username;
  }

    /**
     * getter of email
     * @return the value of the local variable email
     */
  public String getEmail() {
    return email;
  }

    /**
     * setter of local variable cost
     * @param cost the value of cost to be set
     */
  public void setCost(double cost) {
    this.cost = cost;
  }

    /**
     * getter of local variable cost
     * @return the local variable cost
     */
  public double getCost() {
    return cost;
  }

    /**
     * Every night after 23:59 PM, the Transit System will close.
     * And clear all unfinished trips to finished trip.
     */
    public void closeSystemClearUnfinished() {
//        for(ArrayList<Trip> trip: allUnfinishedTrip){
//            if(trip.size() == 1 && trip.get(0).getLocation() instanceof SubwayStation){
//                trip.get(0).getFare().updateFare(FareCalculator.getMaxFare());
//            }else if(trip.size() == 3 && trip.get(2).getLocation() instanceof SubwayStation){
//                trip.get(2).getFare().updateFare(FareCalculator.getMaxFare());
//            }
//        }
        allFinishedTrip.addAll(allUnfinishedTrip);
            allUnfinishedTrip = new ArrayList<>();}

    /**
     * Print a daily report of a specific date including bus revenue subway revenue and total revenue
     * @param date a specific date to print
     */
  String dailyReportToString(int date) {
    double sub = calculateDailySubRevenue(date);
    double bus = calculateDailyBusRevenue(date);
      return "The amount of all Subway revenue on " + date + " is " + sub + "\n"
              + "The amount of all Bus revenue on " + date + " is " + bus  + "\n"
              + "The total amount of revenue on " + date + " is " + (bus + sub);
  }

}
