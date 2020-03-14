import java.util.ArrayList;

public class AccountManager {

    /**
     * ArrayList of CardUserAccount
     */
    private ArrayList<CardUserAccount> CardUserList;
    private ArrayList<AdminUserAccount> AdminUserList;
    /**
     * constructor of class
     */
    AccountManager() {
        CardUserList = new ArrayList<>();
        AdminUserList = new ArrayList<>();
    }


    void addCardUser(CardUserAccount user) {
        if (!CardUserList.contains(user)) {
            CardUserList.add(user);
        }
    }
    void addAdminUser(AdminUserAccount adminUser){
        if (!AdminUserList.contains(adminUser)) {
            AdminUserList.add(adminUser);
        }
    }

    boolean checkPasswordAdmin(String email, String password){
        return this.findUserByEmailAdmin(email).getPassword().equals(password);
    }

    boolean isUserEmailUsedAdmin(String email){
        return this.findUserByEmailAdmin(email)!= null;}

    boolean checkPassword(String email, String password){
        return this.findUserByEmail(email).getPassword().equals(password);
    }

    boolean isEmailUsed(String email){
        return this.findUserByEmail(email)!= null;}

    /**
     * return the CardUserAccount by name
     *
     * @param name the Username of the CardUserAccount
     * @return a CardUserAccount instance
     */
    CardUserAccount findUserByName(String name) {
        int i = 0;
        while (i < CardUserList.size()) {
            if (CardUserList.get(i).getName().equals(name)) {
                return CardUserList.get(i);
            }
            i++;
        }
        return null;
    }

    /**
     * return the CardUserAccount by email
     *
     * @param username username of the admin user account
     * @return a CardUserAccount
     */

    AdminUserAccount findUserByNameAdmin(String username){
        int i = 0;
        while (i < AdminUserList.size()) {
            if (AdminUserList.get(i).getName().equals(username)) {
                return AdminUserList.get(i);
            }
            i++;
        }
        return null;
    }
    AdminUserAccount findUserByEmailAdmin(String email){
        int i = 0;
        while (i < AdminUserList.size()) {
            if (AdminUserList.get(i).getEmail().equals(email)) {
                return AdminUserList.get(i);
            }
            i++;
        }
        return null;

    }

    CardUserAccount findUserByEmail(String email) {
        int i = 0;
        while (i < CardUserList.size()) {
            if (CardUserList.get(i).getEmail().equals(email)) {
                return CardUserList.get(i);
            }
            i++;
        }
        return null;
    }

    /**
     * a getter of the local variable getCardUserList
     *
     * @return the local variable getCardUserList
     */
    public ArrayList<CardUserAccount> getCardUserList() {
        return CardUserList;
    }


}
