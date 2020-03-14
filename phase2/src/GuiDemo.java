import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.*;

/**
 * Some codes were adapted from the YouTube JavaFX teaching video by thenewboston;
 * URL: https://bit.ly/2hG0e2q
 */


public class GuiDemo extends Application{

    //instances
    private final static Logger my_log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Stage window;
    private Scene mainScene, userLogInScene, adminLogInScene, rideScene, userSignUpScene,
            adminSignUpScene, busTapScene, subTapScene, busTapInScene, busTapOutScene,
            subTapInScene, subTapOutScene, accountInterface, checkDailyReportScene, findUserScene, checkFeedbackScene,
            adminAccountInterface, loadCardScene, changePWScene, accountDetailInfoScene, userReportScene,
            manageCardsScene, suspendCardScene, feedbackScene, threeTripsScene, changeAdminPWScene,previewCostsScene,
            Transfer, NTransfer,subwayPreview, subwayTransfer, busTransfer;
    private TableView<Card> cardTableView;
    private TextField enterCardID;
    private static ArrayList<String> stationNames = new ArrayList<>();
    private static ArrayList<String> stopNames = new ArrayList<>();
    private static CardsManager CManager = new CardsManager();
    private static TripManager TManager = new TripManager();
    private static AccountManager ACManager = new AccountManager();
    private CardUserAccount currentUser;
    private AdminUserAccount currentAdminUser;
    private String AdminUserCode = "group0127";
    private ListView<String> feedbackView;
    private ObservableList<Integer> cardNumList = FXCollections.observableArrayList();
    private ObservableList<String> feedbackContainer = FXCollections.observableArrayList();



    /*----------------------------------------------------------------------*/
    public static void main(String[] args){
        TransitSystem.stations.add(new SubwayStation("Spadina", 1));
        TransitSystem.stations.add(new SubwayStation("St.George", 2));
        TransitSystem.stations.add(new SubwayStation("Museum", 3));
        TransitSystem.stations.add(new SubwayStation("Queen'sPark", 4));
        TransitSystem.stations.add(new SubwayStation("St.Patrick", 5));
        TransitSystem.stations.add(new SubwayStation("Osgoode", 6));
        TransitSystem.stations.add(new SubwayStation("St.Andrew", 7));
        TransitSystem.stations.add(new SubwayStation("Union", 8));
        TransitSystem.stations.add(new SubwayStation("King", 9));
        TransitSystem.stations.add(new SubwayStation("Queen", 10));
        TransitSystem.stations.add(new SubwayStation("Dundas", 11));
        TransitSystem.stations.add(new SubwayStation("College", 12));
        TransitSystem.stations.add(new SubwayStation("Wellesley", 13));
        TransitSystem.stations.add(new SubwayStation("Bloor-Yonge", 14));
        TransitSystem.stations.add(new SubwayStation("Sheppard-Yonge", 15));
        TransitSystem.stations.add(new SubwayStation("Finch", 16));
        TransitSystem.stops.add(new BusStop("Finch", 100));
        TransitSystem.stops.add(new BusStop("Steeles", 101));
        TransitSystem.stops.add(new BusStop("Bayview", 102));
        TransitSystem.stops.add(new BusStop("DonMills", 103));
        TransitSystem.stops.add(new BusStop("Woodbine", 104));
        TransitSystem.stops.add(new BusStop("VictoriaPark", 105));
        for(SubwayStation s: TransitSystem.stations){
            stationNames.add(s.getName());
        }
        for(BusStop b: TransitSystem.stops){
            stopNames.add(b.getName());
        }
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("log");

            my_log.addHandler(fh);
            fh.setLevel(Level.ALL);
            my_log.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            my_log.log(Level.INFO,"My first log");

        } catch (java.io.IOException e) {

            my_log.log(Level.SEVERE, "File logger function problem", e);
        }
        my_log.log(Level.INFO,"Transit System Log starts Here");
        window = primaryStage;
        window.setTitle("Demo Transit System");
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set main scene

        //user log in button
        Button userButton = new Button("User Log In");
        userButton.setOnAction(e -> window.setScene(userLogInScene));

        Button adminButton = new Button();
        adminButton.setText("Admin User Log In");
        adminButton.setOnAction(e -> window.setScene(adminLogInScene));

        Button rideButton = new Button();
        rideButton.setText("Take a Ride");
        rideButton.setOnAction(e -> window.setScene(rideScene));

        Button previewButton = new Button();
        previewButton.setText("Preview ride cost");
        previewButton.setOnAction(e -> window.setScene(previewCostsScene));

        //set main scene
        VBox mainWindow = new VBox(20);
        mainWindow.getChildren().addAll(userButton, adminButton, rideButton, previewButton);
        mainWindow.setAlignment(Pos.CENTER);
        mainScene = new Scene(mainWindow, 400, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set user log in scene

        GridPane userLoginWindow = new GridPane();
        userLoginWindow.setPadding(new Insets(10, 10, 10, 10));
        userLoginWindow.setVgap(8);
        userLoginWindow.setHgap(10);
        userLoginWindow.setAlignment(Pos.CENTER);

        //set label
        Label userLogIn = new Label("User Log In");
        userLogIn.setFont(new Font("Arial", 20));

        //set email
        Label emailLabel = new Label("User Email");
        GridPane.setConstraints(emailLabel, 0, 0);
        TextField emailInput = new TextField();
        emailInput.setPromptText("Your Email Address");
        GridPane.setConstraints(emailInput, 1, 0);

        //set password
        Label passwordLabel = new Label("Password");
        GridPane.setConstraints(passwordLabel, 0,1);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Your Password");
        GridPane.setConstraints(passwordInput, 1, 1);

        //Log in button and sign Up button
        Button logInButton = new Button("Log In");
        logInButton.setOnAction(e -> {
            if(emailInput.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please enter your email address.");
            } else if (passwordInput.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please enter your password");
            }
            else if(ACManager.findUserByEmail(emailInput.getText()) == null){
                PopUpWindow.popUp("Warning", "Email has not been signed yet!");
                emailInput.clear();
            }
            else if(!ACManager.checkPassword(emailInput.getText(), passwordInput.getText())){
                PopUpWindow.popUp("Warning", "Wrong Password! \nPlease try again!");
                passwordInput.clear();
            } else {
                currentUser = ACManager.findUserByEmail(emailInput.getText());

                window.setScene(accountInterface);
                emailInput.clear();passwordInput.clear();
            }
        });
        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> {window.setScene(userSignUpScene);
            emailInput.clear(); passwordInput.clear();});
        GridPane.setConstraints(logInButton,1, 2);
        GridPane.setConstraints(signUpButton, 1, 3);

        //back button
        Button back = new Button("Back");
        GridPane.setConstraints(back,2, 5);
        back.setOnAction(e -> {window.setScene(mainScene); emailInput.clear(); passwordInput.clear();});

        userLoginWindow.getChildren().addAll(emailLabel,emailInput,
                passwordLabel,passwordInput,logInButton,signUpButton, back);
        VBox userLoginWindowV = new VBox();
        userLoginWindowV.getChildren().addAll(userLogIn, userLoginWindow);
        userLoginWindowV.setAlignment(Pos.CENTER);
        userLogInScene = new Scene(userLoginWindowV, 400, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //User account interface

        // Load card

        Label loadCardMessage = new Label("Load your card");
        loadCardMessage.setFont(new Font("Arial", 20));
        Label chooseCard= new Label("Choose the card to load");
        Label amountLoad = new Label("Enter the amount to load: ");
        TextField amountLoadField = new TextField();
        amountLoadField.setPromptText("Enter amount from $1 - $500");
        amountLoadField.setMinWidth(200);

        if(currentUser != null){
        for(Card c : currentUser.getCardCollection()){
            cardNumList.add(c.getCardId());
        }}
        ChoiceBox<Integer> cardList = new ChoiceBox<>();
        cardList.setItems(cardNumList);
        //cardList.getSelectionModel().getSelectedItem();

        Button loadButton = new Button("Load");
        loadButton.setOnAction(e -> {if(cardList.getValue() == null){
            PopUpWindow.popUp("Warning", "You have not select a card to load");
        }else if(amountLoadField.getText().equals("") || Integer.parseInt(amountLoadField.getText()) < 1 ||
                Integer.parseInt(amountLoadField.getText()) > 500){
            PopUpWindow.popUp("Warning", "Please enter amount between $1 to $500");}
            else{currentUser.addMoney(cardList.getValue(), Integer.parseInt(amountLoadField.getText()));
                PopUpWindow.popUp("Successful", "You have successfully loaded $" +
                amountLoadField.getText()+ "into you card ID: " + cardList.getValue());
            my_log.log(Level.INFO,"You have successfully loaded" +
                    amountLoadField.getText()+ "into you card ID: " + cardList.getValue());
                window.setScene(accountInterface); amountLoadField.clear();
            cardList.getSelectionModel().clearSelection();}
        });

        GridPane.setConstraints(chooseCard, 0, 1);
        GridPane.setConstraints(cardList, 1, 1);
        GridPane.setConstraints(amountLoad, 0, 2);
        GridPane.setConstraints(amountLoadField, 1, 2);
        GridPane.setConstraints(loadButton, 2, 4);

        //Back Button
        Button backInLoad = new Button("Back");
        GridPane.setConstraints(backInLoad,3, 4);
        backInLoad.setOnAction(e -> {window.setScene(accountInterface); amountLoadField.clear();
            cardList.getSelectionModel().clearSelection();});

        VBox loadCardLayoutV = new VBox(10);
        loadCardLayoutV.setPadding(new Insets(10,10,10,10));

        GridPane loadCardLayout = new GridPane();
        loadCardLayout.setHgap(12);
        loadCardLayout.setVgap(15);

        loadCardLayout.setAlignment(Pos.CENTER);
        loadCardLayoutV.setAlignment(Pos.CENTER);

        loadCardLayout.getChildren().addAll(chooseCard, cardList, amountLoad, amountLoadField, backInLoad, loadButton);
        loadCardLayoutV.getChildren().addAll(loadCardMessage, loadCardLayout);
        loadCardScene = new Scene(loadCardLayoutV, 600, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //Change User password
        Label changePWMessage = new Label("Change Password ");
        changePWMessage.setFont(new Font("Arial", 20));

        Label enterCurrentPW = new Label("Current password: ");
        PasswordField currentPWField = new PasswordField();
        Label enterNewPW = new Label("New password: ");
        PasswordField newPWField = new PasswordField();
        Label changePasswordHint = new Label("(6-10 characters; only contains: " +
                "\nletters/numbers/mix letters and numbers)");
        changePasswordHint.setFont(new Font(10));
        Label confirmNewPW = new Label("Confirm new password: ");
        PasswordField confirmPWField = new PasswordField();

        //Submit Button
        Button submitPW = new Button("Submit");
        GridPane.setConstraints(submitPW,2, 5);
        submitPW.setOnAction(e -> {if(currentPWField.getText().equals("") || newPWField.getText().equals("") ||
                confirmPWField.getText().equals("")){
            PopUpWindow.popUp("Warning", "Please enter all fields before submit.");
        }else if(!ACManager.checkPassword(currentUser.getEmail(), currentPWField.getText())){
            PopUpWindow.popUp("Warning", "Your current password is wrong. \nPlease try again!");
            currentPWField.clear();
        }else if(!newPWField.getText().matches("[a-zA-Z0-9]{6,10}")){
            PopUpWindow.popUp("Warning",
                    "Invalid password! \nUse 6-10 characters with a mix of letters and numbers," +
                            "\nor only letters or only numbers.");
            newPWField.clear(); confirmPWField.clear();}
        else if(!confirmPWField.getText().equals(newPWField.getText())){
            PopUpWindow.popUp("Warning", "Your new password does not match! \nPlease try again!");
            confirmPWField.clear();
        }else {
            PopUpWindow.popUp("Successful", "Your password has been changed.");
            currentUser.setPassword(newPWField.getText());
            window.setScene(accountInterface); currentPWField.clear(); newPWField.clear(); confirmPWField.clear();}});

        //Back Button
        Button backInChangePW = new Button("Back");
        GridPane.setConstraints(backInChangePW,3, 5);
        backInChangePW.setOnAction(e -> {window.setScene(accountInterface); currentPWField.clear();
            newPWField.clear(); confirmPWField.clear();});

        VBox changePWLayoutV = new VBox(10);
        changePWLayoutV.setPadding(new Insets(10,10,10,10));

        GridPane changePWLayout = new GridPane();
        changePWLayout.setHgap(12);
        changePWLayout.setVgap(15);

        GridPane.setConstraints(enterCurrentPW, 0, 1);
        GridPane.setConstraints(currentPWField, 1, 1);
        GridPane.setConstraints(enterNewPW, 0, 2);
        GridPane.setConstraints(newPWField, 1, 2);
        GridPane.setConstraints(confirmNewPW, 0, 4);
        GridPane.setConstraints(confirmPWField, 1, 4);
        GridPane.setConstraints(changePasswordHint, 1, 3);

        changePWLayout.setAlignment(Pos.CENTER);
        changePWLayoutV.setAlignment(Pos.CENTER);

        changePWLayout.getChildren().addAll(enterCurrentPW, currentPWField, newPWField, enterNewPW,
                confirmNewPW, confirmPWField, backInChangePW, submitPW, changePasswordHint);
        changePWLayoutV.getChildren().addAll(changePWMessage, changePWLayout);

        changePWScene = new Scene(changePWLayoutV, 600,400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //Account Detail Info
        Button changeName = new Button("Change My Name");
        Button viewDetail = new Button("View More Details ");

        viewDetail.setOnAction(e -> {PopUpWindow.popUp("My Account Detail Info",
                currentUser.toString());});

        Label changeNameLabel = new Label("Enter the name you want to change to:");
        TextField nameText = new TextField();

        changeName.setOnAction(e -> {if(nameText.getText().equals("")){
            PopUpWindow.popUp("Warning", "You did not input a name.");
        }else if (nameText.getText().equals(currentUser.getName())){
            //}else if (nameText.getText().equals("Daniel")){
            PopUpWindow.popUp("Warning", "You did not input a new name");
        }else if (!nameText.getText().matches("[a-zA-Z]+")){
            PopUpWindow.popUp("Warning","Invalid name input! \nName should only contain letters!");
            nameText.clear();
        }else{currentUser.setName(nameText.getText());
        PopUpWindow.popUp("Successful", "You name has been changed successfully.");
        nameText.clear();
        window.setScene(accountInterface);
        } });


        GridPane accountDetailLayout = new GridPane();
        VBox accountDetailLayoutV = new VBox(10);
        accountDetailLayoutV.setPadding(new Insets(10,10,10,10));
        accountDetailLayout.setHgap(12);
        accountDetailLayout.setVgap(15);
        GridPane.setConstraints(changeNameLabel, 0, 1);
        GridPane.setConstraints(changeName, 2, 2);
        GridPane.setConstraints(viewDetail, 2, 3);
        GridPane.setConstraints(nameText, 0, 2);


        //Back Button
        Button backInAccountDetail = new Button("Back");
        GridPane.setConstraints(backInAccountDetail,3, 4);
        backInAccountDetail.setOnAction(e -> {window.setScene(accountInterface); nameText.clear();});

        accountDetailLayout.getChildren().addAll(changeName, changeNameLabel, nameText, backInAccountDetail, viewDetail);
        accountDetailLayout.setAlignment(Pos.CENTER);

        accountDetailInfoScene = new Scene(accountDetailLayout, 600, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //Monthly report for user
        Label userReportMessage = new Label("Monthly Ride Report");
        Label userReportDescription = new Label("Show your monthly report of number of rides and fares used.");
        userReportDescription.setFont(new Font("Arial", 14));
        userReportMessage.setFont(new Font("Arial", 20));
        Label monthInputLabel = new Label("Enter the month you want to look at");
        TextField monthInput = new TextField();
        monthInput.setPromptText("Format:YYYYMM");
        Button submitForReport = new Button("Go");
        submitForReport.setOnAction(e -> {if(monthInput.getText().equals("")){
            PopUpWindow.popUp("Warning","Please enter the the month.");
        }else if (!monthInput.getText().matches("^[2][0][0-9]{4}")){
            PopUpWindow.popUp("Warning", "Please enter in the format of YYYYMM. No earlier than 200001.");
        }else{int monthInputInt = Integer.parseInt(monthInput.getText());
            PopUpWindow.popUp("Monthly Report", "Number of Trips Taken: " +
                    currentUser.monthlyRideTimes(monthInputInt) + "\n"
                    + "Total Fare Paid: " +
                    currentUser.monthlyRideFare(monthInputInt));}});

        //Back Button
        Button backInMonthlyReport = new Button("Back");
        GridPane.setConstraints(backInAccountDetail,3, 6);
        backInMonthlyReport.setOnAction(e -> {window.setScene(accountInterface); monthInput.clear();});

        HBox monthlyReportLayoutH = new HBox(20);
        monthlyReportLayoutH.getChildren().addAll(monthInputLabel, monthInput, submitForReport);
        VBox monthlyReportLayout = new VBox(30);
        monthlyReportLayout.getChildren().addAll(userReportMessage, userReportDescription,
                monthlyReportLayoutH, backInMonthlyReport);
        monthlyReportLayoutH.setAlignment(Pos.CENTER);
        monthlyReportLayout.setAlignment(Pos.CENTER);
        userReportScene = new Scene(monthlyReportLayout, 600, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        // Manage cards
        Label manageCardMessage = new Label("Manage Card(s)");
        manageCardMessage.setFont(new Font("Arial", 20));
        Button addCardButton = new Button("Add Card");
        Button removeCardButton = new Button("Remove Card");
        Button checkBalance = new Button("Check Balance");
        Button checkStatus = new Button("Check Status");
        enterCardID = new TextField();
        enterCardID.setPromptText("Enter Card ID");
        addCardButton.setOnAction(e -> {if(enterCardID.getText().equals("")){PopUpWindow.popUp("Warning",
                "You have not enter a card ID yet!");
        }else{cardNumList.add(Integer.parseInt(enterCardID.getText())); addButtonClicked();}});

        removeCardButton.setOnAction(e -> {Card cSelected = cardTableView.getSelectionModel().getSelectedItem();
        if (cSelected != null){
            boolean confirmRemoval = PopUpWindow.confirmationBox("Confirm",
                    "Are you sure you want to remove this card?");
            if (confirmRemoval) {
                cardNumList.remove(cardNumList.indexOf(cSelected.getCardId())); removeButtonClicked();}
        }else{PopUpWindow.popUp("Warning", "You have not select a card yet.");}});
        //check balance
        checkBalance.setOnAction(e -> {Card cSelected = cardTableView.getSelectionModel().getSelectedItem();
        if (cSelected != null){PopUpWindow.popUp("Balance",
                "The balance for Card ID " + cSelected.getCardId() + " is $" + cSelected.getBalance());
        }else{PopUpWindow.popUp("Warning", "You have not select a card yet.");}});
        //Check status
        checkStatus.setOnAction(e -> {Card cSelected = cardTableView.getSelectionModel().getSelectedItem();
        if (cSelected != null){if(CManager.isSuspend(cSelected.getCardId())){
            PopUpWindow.popUp("Status",
                    "Your card ID " + cSelected.getCardId() + " is suspended." );
        }else{ PopUpWindow.popUp("Status",
                    "Your card ID " + cSelected.getCardId() + " is active." );
        }}else{PopUpWindow.popUp("Warning", "You have not select a card yet.");}});

        //Card Id column
        TableColumn<Card, Integer> cardNumCol = new TableColumn<>("Card ID");
        cardNumCol.setMinWidth(700);
        cardNumCol.setStyle("-fx-alignment: CENTER;");
        cardNumCol.setCellValueFactory(new PropertyValueFactory<>("cardId"));

        cardTableView = new TableView<>();
        cardTableView.setItems(getCard());
        cardTableView.getColumns().addAll(cardNumCol);

        //Back Button
        Button backInManageCards = new Button("Back");
        backInManageCards.setOnAction(e -> {window.setScene(accountInterface); enterCardID.clear();});

        HBox addRemoveCard = new HBox(10);
        addRemoveCard.getChildren().addAll(enterCardID, addCardButton, removeCardButton,
                checkBalance, checkStatus, backInManageCards);
        VBox manageCardsLayout = new VBox(10);
        manageCardsLayout.setPadding(new Insets(10,10,10,10));
        manageCardsLayout.setAlignment(Pos.CENTER);
        manageCardsLayout.getChildren().addAll(manageCardMessage, cardTableView, addRemoveCard);

        manageCardsScene = new Scene(manageCardsLayout);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        // Suspend Card Scene
        Label suspendCardMessage = new Label("Suspend/Unsuspend My Card");
        suspendCardMessage.setFont(new Font("Arial", 20));
        Label suspendCardLabel = new Label("Choose the card you want to unsuspend/suspend:");
        ChoiceBox<Integer> cardListForSuspend = new ChoiceBox<>();
        cardListForSuspend.setItems(cardNumList);
        Button suspendButton = new Button("Suspend/Unsuspend");
        suspendButton.setOnAction(e -> {if (cardListForSuspend.getValue() == null) {
            PopUpWindow.popUp("Warning", "You have not select a card yet.");
        }else {
            int idForSuspend = cardListForSuspend.getSelectionModel().getSelectedItem();
            if(! CManager.isSuspend(idForSuspend)){
                boolean confirmSuspend = PopUpWindow.confirmationBox("Confirmation",
                    "Are you sure you want to suspend " + idForSuspend + " ?");
                if(confirmSuspend){
                    currentUser.suspendCard(idForSuspend);
                    PopUpWindow.popUp("Successful","Card ID" + idForSuspend + " is now suspended");
                    my_log.log(Level.INFO, "Card ID " + idForSuspend + " is now suspended" );
                    window.setScene(accountInterface);
                }
            }else { boolean confirmUnsuspend = PopUpWindow.confirmationBox("Confirmation",
                    "Are you sure you want to activate " + idForSuspend + " ?");
                    if(confirmUnsuspend){
                    currentUser.unsuspendCard(idForSuspend);
                    PopUpWindow.popUp("Successful","Card ID " + idForSuspend + " is now activated");
                    my_log.log(Level.INFO, "Card ID " + idForSuspend + " is now activated" );
                    window.setScene(accountInterface);
                    }
            }
        }
        });
        //Back Button
        Button backInSuspendCard = new Button("Back");
        backInSuspendCard.setOnAction(e -> {window.setScene(accountInterface);
            cardListForSuspend.getSelectionModel().clearSelection();});

        VBox suspendCardLayoutV = new VBox(20);
        GridPane suspendCardLayout = new GridPane();
        GridPane.setConstraints(suspendCardLabel, 0, 2);
        GridPane.setConstraints(cardListForSuspend, 2, 2);
        GridPane.setConstraints(suspendButton, 2, 3);
        GridPane.setConstraints(backInSuspendCard, 2, 6);
        suspendCardLayout.setAlignment(Pos.CENTER);
        suspendCardLayout.getChildren().addAll(suspendButton, suspendCardLabel, cardListForSuspend, backInSuspendCard);
        suspendCardLayout.setVgap(10);
        suspendCardLayout.setHgap(25);
        suspendCardLayoutV.getChildren().addAll(suspendCardMessage, suspendCardLayout);
        suspendCardLayoutV.setPadding(new Insets(10,10,10,10));
        suspendCardLayoutV.setAlignment(Pos.CENTER);
        suspendCardScene = new Scene(suspendCardLayoutV, 600, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //Feedback Page
        Label feedbackLabel = new Label("Any question or comment?");
        TextArea feedbackText = new TextArea();
        feedbackText.setPrefHeight(400);
        feedbackText.setMaxWidth(300);
        feedbackText.setWrapText(true);

        //Back Button
        Button backInFeedback = new Button("Back");
        backInFeedback.setOnAction(e -> {window.setScene(accountInterface); feedbackText.clear();});

        Button submitFeedback = new Button("Submit");
        submitFeedback.setOnAction(e ->
        {if(feedbackText.getText().equals("")) {
            PopUpWindow.popUp("Warning", "You may not submit a empty feedback.");
        }else{PopUpWindow.popUp("Successful",
                "Your feedback has been submitted. Thank you!");
        feedbackContainer.addAll(feedbackText.getText());
        feedbackText.clear();
        window.setScene(accountInterface);}});
        HBox feedbackButtonsLayout = new HBox(10);
        feedbackButtonsLayout.getChildren().addAll(submitFeedback, backInFeedback);
        feedbackButtonsLayout.setAlignment(Pos.CENTER);

        VBox feedbackLayout = new VBox(10);
        feedbackLayout.setPadding(new Insets(10,10,10,10));
        feedbackLayout.setAlignment(Pos.TOP_CENTER);
        feedbackLayout.getChildren().addAll(feedbackLabel, feedbackText, feedbackButtonsLayout);

        feedbackScene = new Scene(feedbackLayout, 400, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        // Recent three trips
        Label recentTripsMessage = new Label("Recent Three Trips");
        recentTripsMessage.setFont(new Font("Arial", 20));
        VBox threeTripsLayout = new VBox(30);
        threeTripsLayout.getChildren().add(recentTripsMessage);
        Button checkTrips = new Button("Check");
        checkTrips.setOnAction(e -> {
                ArrayList<ArrayList<Trip>> threeTripsList = currentUser.getRecentThreeTrips();
                if(threeTripsList.size() == 0){
                   PopUpWindow.popUp("Recent Trips", "There is no trip in the current account.");
                } else if(threeTripsList.size() == 1){
                    PopUpWindow.popUp("Recent Trips", "There is only one recent trip:"+ "\n"
                            + TManager.toString(threeTripsList.get(0)));
                }else if(threeTripsList.size() == 2){
                            PopUpWindow.popUp("Recent Trips", "There are two recent trips:" + "\n" +
                                    "1. " + TManager.toString(threeTripsList.get(0)) + ";" + "\n" +
                                    "2. " + TManager.toString(threeTripsList.get(1)));
                }else if(threeTripsList.size() == 3){
                            PopUpWindow.popUp("Recent Trips", "There are three recent trips:" + "\n" +
                                            "1. " + TManager.toString(threeTripsList.get(0)) + ";" + "\n" +
                                            "2. " + TManager.toString(threeTripsList.get(1)) + ";" + "\n" +
                                            "3. " + TManager.toString(threeTripsList.get(2)));
                }
        });
        //Back Button
        Button backInRecentTrips = new Button("Back");
        backInRecentTrips.setOnAction(e -> window.setScene(accountInterface));

        threeTripsLayout.getChildren().addAll(checkTrips, backInRecentTrips);
        threeTripsLayout.setAlignment(Pos.CENTER);
        threeTripsScene = new Scene(threeTripsLayout, 600, 400);

        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //Account menu
        Menu accountMenu = new Menu("_Account");
        MenuItem accountDetail = new MenuItem("_View Account Detail");
        accountDetail.setOnAction(e -> window.setScene(accountDetailInfoScene));
        MenuItem changePassword = new MenuItem("_Change My Password");
        changePassword.setOnAction(event -> window.setScene(changePWScene));
        MenuItem recentTrips = new MenuItem("View Recent Three Trips");
        recentTrips.setOnAction(e -> window.setScene(threeTripsScene));
        MenuItem activitySummary = new MenuItem("View Loyalty Summary");
        activitySummary.setOnAction(e -> window.setScene(userReportScene));
        accountMenu.getItems().addAll(accountDetail, changePassword, recentTrips, activitySummary);

        //Manage menu
        Menu manageMenu = new Menu("_Manage");
        MenuItem load = new MenuItem("Load My Card");
        load.setOnAction(event -> window.setScene(loadCardScene));
        MenuItem manageMyCards = new MenuItem("Manage My Cards");
        manageMyCards.setOnAction(e -> window.setScene(manageCardsScene));
        MenuItem suspend = new MenuItem("Suspend My Card");
        suspend.setOnAction(e -> window.setScene(suspendCardScene));
        manageMenu.getItems().addAll(load, manageMyCards, suspend);

        //Feedback menu
        Menu contactUs = new Menu("Contact Us");
        MenuItem feedback = new MenuItem("Feedback");
        contactUs.getItems().addAll(feedback);
        feedback.setOnAction(event -> window.setScene(feedbackScene));

        MenuBar accountMenuBar = new MenuBar();
        accountMenuBar.getMenus().addAll(accountMenu, manageMenu, contactUs);

        Label welcomeMessage = new Label();
        welcomeMessage.setText("Welcome! \n Please Choose Any Actions from the \n Above Menu Bar. ");
        welcomeMessage.setTextAlignment(TextAlignment.CENTER);
        welcomeMessage.setFont(new Font("Arial", 30));
        Button userLogOut = new Button("Log Out");
        userLogOut.setOnAction(e -> {window.setScene(mainScene); currentUser = null;});

        HBox userLogOutLayout = new HBox(10);
        userLogOutLayout.setPadding(new Insets(10,10,10,10));
        userLogOutLayout.getChildren().addAll(userLogOut);
        BorderPane userAccountLayout = new BorderPane();
        userAccountLayout.setTop(accountMenuBar);
        userAccountLayout.setCenter(welcomeMessage);
        userAccountLayout.setBottom(userLogOutLayout);

        accountInterface = new Scene(userAccountLayout, 600, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set user sign up scene

        GridPane userSignUpWindow = new GridPane();
        userSignUpWindow.setPadding(new Insets(20, 20, 20, 20));
        userSignUpWindow.setVgap(8);
        userSignUpWindow.setHgap(10);
        userSignUpWindow.setAlignment(Pos.CENTER);

        //set label
        Label userSignUpLabel = new Label("User Sign Up");
        userSignUpLabel.setFont(new Font("Arial", 20));

        //set name
        Label nameLabel = new Label("Your Name");
        GridPane.setConstraints(nameLabel, 0, 0);
        Label hint = new Label("(Name only letters; \nno space or other characters)");
        hint.setFont(new Font(10));
        GridPane.setConstraints(hint,1,1);
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your name");
        GridPane.setConstraints(nameInput, 1, 0);

        //set email
        Label signEmailLabel = new Label("Your Email");
        GridPane.setConstraints(signEmailLabel, 0, 2);
        TextField signEmailInput = new TextField();
        signEmailInput.setPromptText("Enter your email address to sign up");
        GridPane.setConstraints(signEmailInput, 1, 2);

        //set password
        Label signPasswordLabel = new Label("Password");
        GridPane.setConstraints(signPasswordLabel, 0,3);
        Label hint_ = new Label("(6-10 characters; only contains: " +
                "\nletters/numbers/mix letters and numbers)");
        hint_.setFont(new Font(10));
        GridPane.setConstraints(hint_,1,4);
        PasswordField signPasswordInput = new PasswordField();
        signPasswordInput.setPromptText("Enter your password");
        GridPane.setConstraints(signPasswordInput, 1, 3);

        //set confirm password
        Label signConfirmPasswordLabel = new Label("Confirm Password");
        GridPane.setConstraints(signConfirmPasswordLabel, 0,5);
        PasswordField signConfirmPasswordInput = new PasswordField();
        signConfirmPasswordInput.setPromptText("Enter your password again");
        GridPane.setConstraints(signConfirmPasswordInput, 1, 5);

        //sign Up button
        Button userSignUp = new Button("Sign Up");
        userSignUp.setOnAction(e -> {
            if(nameInput.getText().equals("") || signEmailInput.getText().equals("")
                    || signPasswordInput.getText().equals("") || signConfirmPasswordInput.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please fill out all information required.");
            }else if(ACManager.isEmailUsed(signEmailInput.getText())){
                PopUpWindow.popUp("Warning", "This email is registered already.");
            }else if(!nameInput.getText().matches("[a-zA-Z]+")){
                PopUpWindow.popUp("Warning","Invalid name input! \nName should only contain letters!");
                nameInput.clear();}
            else if(!signEmailInput.getText().matches(
                    "[a-zA-Z0-9]+[.]*[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+")){
                PopUpWindow.popUp("Warning",
                        "Invalid email address! \nPlease enter a valid email address!");
                signEmailInput.clear(); }
            else if(!signPasswordInput.getText().matches("[a-zA-Z0-9]{6,10}")){
                PopUpWindow.popUp("Warning",
                        "Invalid password! \nUse 6-10 characters with a mix of letters and numbers," +
                                "\nor only letters or only numbers.");
                signPasswordInput.clear(); signConfirmPasswordInput.clear();}
            else if(!signConfirmPasswordInput.getText().equals(signPasswordInput.getText())){
                PopUpWindow.popUp("Warning", "Password does not match! \nPlease try again!");
                signConfirmPasswordInput.clear();
            }
            else if(ACManager.findUserByEmail(signEmailInput.getText())!= null){
                PopUpWindow.popUp("Warning","Email has been signed up!" +
                        "\nPlease enter a new email address! " +
                        "\nOr try to log in!");
                signEmailInput.clear();
            }
            else{
                ACManager.addCardUser(new CardUserAccount(nameInput.getText(),
                        signEmailInput.getText(), signPasswordInput.getText()));
                PopUpWindow.popUp("Successful", "Congratulation! You've successfully signed up!");
                window.setScene(userLogInScene);
                nameInput.clear();signEmailInput.clear();
                signPasswordInput.clear();signConfirmPasswordInput.clear();}
        });
        GridPane.setConstraints(userSignUp,1, 6);

        //back button
        Button userBack = new Button("Back");
        GridPane.setConstraints(userBack,2, 7);
        userBack.setOnAction(e -> {
            window.setScene(userLogInScene);nameInput.clear();signEmailInput.clear();
            signPasswordInput.clear();signConfirmPasswordInput.clear();});

        userSignUpWindow.getChildren().addAll(nameLabel, nameInput, signEmailLabel, signEmailInput,
                signPasswordLabel, signPasswordInput, userSignUp, userBack,
                signConfirmPasswordInput, signConfirmPasswordLabel,hint,hint_);
        VBox userSignUpWindowV = new VBox();
        userSignUpWindowV.getChildren().addAll(userSignUpLabel, userSignUpWindow);
        userSignUpWindowV.setAlignment(Pos.CENTER);
        userSignUpScene = new Scene(userSignUpWindowV, 600, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //Admin User Interface
        GridPane adminAccountInterFaceLayout = new GridPane();
        adminAccountInterFaceLayout.setPadding(new Insets(20, 20, 20, 20));
        adminAccountInterFaceLayout.setVgap(8);
        adminAccountInterFaceLayout.setHgap(10);
        adminAccountInterFaceLayout.setAlignment(Pos.CENTER);

        //welcome label
        Label AdminWelcome = new Label("Welcome!");
        AdminWelcome.setFont(new Font(20));
        GridPane.setConstraints(AdminWelcome, 0, 0);

        //Log out button
        Button LogOutAdmin = new Button("Log Out");
        LogOutAdmin.setOnAction(e -> {window.setScene(mainScene);currentAdminUser=null;});
        GridPane.setConstraints(LogOutAdmin, 2, 6);


        //check system status
        Button checkSystemStatus = new Button("Check System Status");
        checkSystemStatus.setOnAction(e -> {
            if(TransitSystem.checkIsSystemOpen()){
                PopUpWindow.popUp("System Status", "System is open!");}
            else{PopUpWindow.popUp("System Status", "System is close!");} });
        GridPane.setConstraints(checkSystemStatus, 0, 1);

        //Shut down button
        Button shutDown = new Button("Shut Down System");
        shutDown.setOnAction(e -> {if(TransitSystem.checkIsSystemOpen()){
            boolean shutDownOrNot = PopUpWindow.confirmationBox("Confirm",
                    "Are you sure you want to shut down the system?");
            if(shutDownOrNot){TransitSystem.closeSystem();
                currentAdminUser.syncAllFinishedTrip(TManager.getFinishedTrips());
                currentAdminUser.syncAllUnfinishedTrip(TManager.getUnfinishedTrips());
                currentAdminUser.closeSystemClearUnfinished();
                for(Card card: CManager.getAllCardsList()){
                    card.clearOldFinishedTrip(currentAdminUser.getAllFinishedTrip().
                            get(currentAdminUser.getAllFinishedTrip().size() - 1).get(0).getDate());}
                PopUpWindow.popUp("System Status", "System shut down successfully!");}}
        else{PopUpWindow.popUp("System Status", "System already been shut down!");}
        });
        GridPane.setConstraints(shutDown, 0,2);

        //open button
        Button open = new Button("Open System");
        open.setOnAction(e -> {
            if(TransitSystem.checkIsSystemOpen()){
                PopUpWindow.popUp("System Status", "System already been opened");}
            else{boolean openOrNot = PopUpWindow.confirmationBox("Confirm",
                    "Are you sure you want to open the system?");
                if(openOrNot){TransitSystem.openSystem();
                    currentAdminUser.syncAllFinishedTrip(TManager.getFinishedTrips());
                    currentAdminUser.syncAllUnfinishedTrip(TManager.getUnfinishedTrips());
                    PopUpWindow.popUp("System Status", "System open successfully!");}}
        });
        GridPane.setConstraints(open, 0, 3);

        //print daily report
        Button dailyReport = new Button("Daily Report");
        dailyReport.setOnAction(e -> window.setScene(checkDailyReportScene));
        GridPane.setConstraints(dailyReport, 1, 1);

        //find user
        Button findUser = new Button("Find User");
        findUser.setOnAction(e -> window.setScene(findUserScene));
        GridPane.setConstraints(findUser, 1, 2);

        //check feedback
        Button checkFeedback = new Button("Check Feedback");
        checkFeedback.setOnAction(e -> window.setScene(checkFeedbackScene));
        GridPane.setConstraints(checkFeedback, 1, 3);

        //change password
        Button changeAdminPW = new Button("Change Password");
        changeAdminPW.setOnAction(e -> window.setScene(changeAdminPWScene));
        GridPane.setConstraints(changeAdminPW, 2, 1);


        adminAccountInterFaceLayout.getChildren().addAll(AdminWelcome, LogOutAdmin, checkSystemStatus, shutDown, open,
                dailyReport, findUser, checkFeedback, changeAdminPW);
        adminAccountInterFaceLayout.setAlignment(Pos.CENTER);
        adminAccountInterface = new Scene(adminAccountInterFaceLayout, 500, 300);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set print daily report scene
        GridPane printDailyReport = new GridPane();
        printDailyReport.setPadding(new Insets(20, 20, 20, 20));
        printDailyReport.setVgap(8);
        printDailyReport.setHgap(10);
        printDailyReport.setAlignment(Pos.CENTER);

        //set label
        Label dailyReportLabel = new Label("Check Daily Report");
        dailyReportLabel.setFont(new Font(20));
        dailyReportLabel.setAlignment(Pos.CENTER);

        //set date
        Label dailyReportDate = new Label("Date");
        GridPane.setConstraints(dailyReportDate,0,0);
        Label dateHint = new Label("Enter the date you want to see its report; " +
                "\nIf you want to see today's report, \nyou must make sure system is closed. \nDate format: YYYYMMDD ");
        dateHint.setFont(new Font(10));
        GridPane.setConstraints(dateHint, 1, 1);
        TextField dailyReportDateInput = new TextField();
        dailyReportDateInput.setPromptText("YYYYMMDD");
        GridPane.setConstraints(dailyReportDateInput, 1, 0);

        //submit
        Button dailyReportSubmit = new Button("Submit");
        GridPane.setConstraints(dailyReportSubmit,2,0);
        dailyReportSubmit.setOnAction(e -> {
            if(TManager.getFinishedTrips().size() == 0){PopUpWindow.popUp("Warning",
                    "There is no available report!");}else{
                int today = TManager.getFinishedTrips().get(TManager.getFinishedTrips().size() - 1).get(0).getDate();
                int earliestDate = TManager.getFinishedTrips().get(0).get(0).getDate();
                if(dailyReportDateInput.getText().equals("")){PopUpWindow.popUp("Warning",
                        "Please fill out the date!");}
                else if(dailyReportDateInput.getText().length() != 8 ||
                        !dailyReportDateInput.getText().matches("[0-9]+")){
                    PopUpWindow.popUp("Warning", "Invalid Date Input! \nPlease try again!");}
                else if(Integer.parseInt(dailyReportDateInput.getText()) == today && TransitSystem.checkIsSystemOpen()){
                    PopUpWindow.popUp("Warning",
                            "System is still open! \nYou can not check today's report if system is working! " +
                                    "\nWait system close or check other date!"); }
                else if(Integer.parseInt(dailyReportDateInput.getText()) > today){
                    PopUpWindow.popUp("Warning", "you entered a date that is too far ahead!");}
                else if(Integer.parseInt(dailyReportDateInput.getText()) < earliestDate){
                    PopUpWindow.popUp("Warning", "you entered a date that is too far before!");}
                else{PopUpWindow.popUp("Daily Report",
                        currentAdminUser.dailyReportToString(Integer.parseInt(dailyReportDateInput.getText())));}}
            dailyReportDateInput.clear();});

        //back
        Button dailyReportBack = new Button("Back");
        GridPane.setConstraints(dailyReportBack, 2, 2);
        dailyReportBack.setOnAction(e -> {window.setScene(adminAccountInterface); dailyReportDateInput.clear();});


        printDailyReport.getChildren().addAll(dailyReportDate,dailyReportDateInput,dateHint,
                dailyReportSubmit, dailyReportBack);
        VBox printDailyReportV = new VBox();
        printDailyReportV.getChildren().addAll(dailyReportLabel, printDailyReport);
        printDailyReportV.setAlignment(Pos.CENTER);
        checkDailyReportScene = new Scene(printDailyReportV, 400, 300);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set find user scene
        GridPane findUserLayout = new GridPane();
        findUserLayout.setPadding(new Insets(20, 20, 20, 20));
        findUserLayout.setVgap(8);
        findUserLayout.setHgap(10);
        findUserLayout.setAlignment(Pos.CENTER);

        //set label
        Label findUserLabel = new Label("Find User's Information");
        findUserLabel.setFont(new Font(20));
        findUserLabel.setAlignment(Pos.CENTER);

        //set email
        Label checkByUserEmail = new Label("User's Email");
        GridPane.setConstraints(checkByUserEmail, 0, 0);
        TextField checkByUserEmailInput = new TextField();
        checkByUserEmailInput.setPromptText("Enter user's email");
        GridPane.setConstraints(checkByUserEmailInput, 1, 0);

        //submit
        Button findUserSubmit = new Button("Submit");
        GridPane.setConstraints(findUserSubmit, 1,1);
        findUserSubmit.setOnAction(e -> {
            if(checkByUserEmailInput.getText().equals("")){PopUpWindow.popUp("Warning",
                    "Please fill out user's email");}
            else if(!checkByUserEmailInput.getText().
                    matches("[a-zA-Z0-9]+[.]*[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+")){
                PopUpWindow.popUp("Warning", "Invalid email address! \nPlease try again!");}
            else if(ACManager.findUserByEmailAdmin(checkByUserEmailInput.getText()) == null){
                PopUpWindow.popUp("Warning",
                        "This Email not been signed yet! \nPlease try again!");}
            else{PopUpWindow.popUp("User's Information",
                    ACManager.findUserByEmail(checkByUserEmailInput.getText()).toString());}
            checkByUserEmailInput.clear();
        });

        //back
        Button findUserBack = new Button("Back");
        GridPane.setConstraints(findUserBack, 2, 3);
        findUserBack.setOnAction(e -> {checkByUserEmailInput.clear();window.setScene(adminAccountInterface);});


        findUserLayout.getChildren().addAll(checkByUserEmail, checkByUserEmailInput, findUserSubmit, findUserBack);
        VBox findUserLayoutV = new VBox();
        findUserLayoutV.getChildren().addAll(findUserLabel, findUserLayout);
        findUserLayoutV.setAlignment(Pos.CENTER);
        findUserScene = new Scene(findUserLayoutV, 400, 300);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set check feedback scene
        feedbackView = new ListView<>(feedbackContainer);
        VBox checkFeedbackLayoutV = new VBox();

        Label FeedbackLabel = new Label();
        FeedbackLabel.setFont(new Font(20));
        FeedbackLabel.setAlignment(Pos.CENTER);
        FeedbackLabel.setText("There are user's feedback");
        feedbackView.setItems(feedbackContainer);


        //delete
        Button deleteFeedback = new Button("Delete");
        deleteFeedback.setOnAction(e -> {boolean confirmDelete = PopUpWindow.confirmationBox("Confirm",
                "Are you sure you want to delete this feedback?");
            if(confirmDelete){deleteButtonClicked();}});

        //back
        Button feedbackBack = new Button("Back");
        feedbackBack.setOnAction(e -> window.setScene(adminAccountInterface));

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(deleteFeedback, feedbackBack);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        checkFeedbackLayoutV.getChildren().addAll(FeedbackLabel, feedbackView, buttonBox);
        checkFeedbackLayoutV.setAlignment(Pos.CENTER);
        checkFeedbackLayoutV.setPadding(new Insets(10,20,10,20));
        checkFeedbackScene = new Scene(checkFeedbackLayoutV, 400, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //Change Admin User password
        Label changeAdminPWMessage = new Label("Change Password ");
        changeAdminPWMessage.setFont(new Font("Arial", 20));

        Label enterCurrentAdminPW = new Label("Current password: ");
        PasswordField currentAdminPWField = new PasswordField();
        Label enterNewAdminPW = new Label("New password: ");
        PasswordField newAdminPWField = new PasswordField();
        Label changeAdminPasswordHint = new Label("(6-10 characters; only contains: " +
                "\nletters/numbers/mix letters and numbers)");
        changeAdminPasswordHint.setFont(new Font(10));
        Label confirmNewAdminPW = new Label("Confirm new password: ");
        PasswordField confirmAdminPWField = new PasswordField();

        //Submit Button
        Button submitAdminPW = new Button("Submit");
        GridPane.setConstraints(submitAdminPW,2, 5);
        submitAdminPW.setOnAction(e -> {if(currentAdminPWField.getText().equals("")
                || newAdminPWField.getText().equals("") || confirmAdminPWField.getText().equals("")){
            PopUpWindow.popUp("Warning", "Please enter all fields before submit.");
        }else if(!ACManager.checkPasswordAdmin(currentAdminUser.getEmail(), currentAdminPWField.getText())){
            PopUpWindow.popUp("Warning", "Your current password is wrong. \nPlease try again!");
            currentAdminPWField.clear();
        }else if(!newAdminPWField.getText().matches("[a-zA-Z0-9]{6,10}")){
            PopUpWindow.popUp("Warning",
                    "Invalid password! \nUse 6-10 characters with a mix of letters and numbers," +
                            "\nor only letters or only numbers.");
            newAdminPWField.clear(); confirmAdminPWField.clear();}
        else if(!confirmAdminPWField.getText().equals(newAdminPWField.getText())){
            PopUpWindow.popUp("Warning", "Your new password does not match! \nPlease try again!");
            confirmAdminPWField.clear();
        }else {
            PopUpWindow.popUp("Successful", "Your password has been changed.");
            currentAdminUser.setPassword(newAdminPWField.getText());
            window.setScene(adminAccountInterface); currentAdminPWField.clear(); newAdminPWField.clear();
            confirmAdminPWField.clear();}});

        //Back Button
        Button backInChangeAdminPW = new Button("Back");
        GridPane.setConstraints(backInChangeAdminPW,3, 5);
        backInChangeAdminPW.setOnAction(e -> {window.setScene(adminAccountInterface); currentAdminPWField.clear();
            newAdminPWField.clear(); confirmAdminPWField.clear();});

        VBox changeAdminPWLayoutV = new VBox(10);
        changeAdminPWLayoutV.setPadding(new Insets(10,10,10,10));

        GridPane changeAdminPWLayout = new GridPane();
        changeAdminPWLayout.setHgap(12);
        changeAdminPWLayout.setVgap(15);

        GridPane.setConstraints(enterCurrentAdminPW, 0, 1);
        GridPane.setConstraints(currentAdminPWField, 1, 1);
        GridPane.setConstraints(enterNewAdminPW, 0, 2);
        GridPane.setConstraints(newAdminPWField, 1, 2);
        GridPane.setConstraints(confirmNewAdminPW, 0, 4);
        GridPane.setConstraints(confirmAdminPWField, 1, 4);
        GridPane.setConstraints(changeAdminPasswordHint, 1, 3);

        changeAdminPWLayout.setAlignment(Pos.CENTER);
        changeAdminPWLayoutV.setAlignment(Pos.CENTER);

        changeAdminPWLayout.getChildren().addAll(enterCurrentAdminPW, currentAdminPWField, newAdminPWField,
                enterNewAdminPW, confirmNewAdminPW, confirmAdminPWField, backInChangeAdminPW, submitAdminPW,
                changeAdminPasswordHint);
        changeAdminPWLayoutV.getChildren().addAll(changeAdminPWMessage, changeAdminPWLayout);

        changeAdminPWScene = new Scene(changeAdminPWLayoutV, 600,400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set Admin User log in scene

        GridPane adminLoginWindow = new GridPane();
        adminLoginWindow.setPadding(new Insets(10, 10, 10, 10));
        adminLoginWindow.setVgap(8);
        adminLoginWindow.setHgap(10);
        adminLoginWindow.setAlignment(Pos.CENTER);

        //set label
        Label adminUserLogInLabel = new Label("Admin User Log In");
        adminUserLogInLabel.setFont(new Font("Arial", 20));

        //set email
        Label AdminEmailLabel = new Label("Admin User Email");
        GridPane.setConstraints(AdminEmailLabel, 0, 0);
        TextField AdminEmailInput = new TextField();
        AdminEmailInput.setPromptText("Your Email Address");
        GridPane.setConstraints(AdminEmailInput, 1, 0);

        //set password
        Label AdminPasswordLabel = new Label("Password");
        GridPane.setConstraints(AdminPasswordLabel, 0,1);
        PasswordField AdminPasswordInput = new PasswordField();
        AdminPasswordInput.setPromptText("Your Password");
        GridPane.setConstraints(AdminPasswordInput, 1, 1);

        //Log in button and sign up button
        Button AdminLogInButton = new Button("Log In");
        AdminLogInButton.setOnAction(e -> {
            if(AdminEmailInput.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please enter your email address.");
            } else if (AdminPasswordInput.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please enter your password");
            }
            else if(ACManager.findUserByEmailAdmin(AdminEmailInput.getText()) == null){
                PopUpWindow.popUp("Warning", "Email has not been signed yet!");
                AdminEmailInput.clear();
            }
            else if(!ACManager.checkPasswordAdmin(AdminEmailInput.getText(), AdminPasswordInput.getText())){
                PopUpWindow.popUp("Warning", "Wrong Password! \nPlease try again!");
                AdminPasswordInput.clear();
            }else { window.setScene(adminAccountInterface);
                currentAdminUser = ACManager.findUserByEmailAdmin(AdminEmailInput.getText());
                AdminEmailInput.clear();AdminPasswordInput.clear();
                window.setScene(adminAccountInterface);}
        });
        Button AdminSignUpButton = new Button("Sign Up");
        AdminSignUpButton.setOnAction(e -> {
            window.setScene(adminSignUpScene);AdminEmailInput.clear();AdminPasswordInput.clear();});
        GridPane.setConstraints(AdminLogInButton,1, 2);
        GridPane.setConstraints(AdminSignUpButton, 1, 3);

        //back button
        Button back1 = new Button("Back");
        GridPane.setConstraints(back1,2, 5);
        back1.setOnAction(e -> {
            window.setScene(mainScene);AdminEmailInput.clear();AdminPasswordInput.clear();});

        adminLoginWindow.getChildren().addAll(AdminEmailLabel,AdminEmailInput,
                AdminPasswordLabel,AdminPasswordInput,AdminLogInButton,AdminSignUpButton, back1);
        VBox adminLoginWindowV = new VBox();
        adminLoginWindowV.getChildren().addAll(adminUserLogInLabel, adminLoginWindow);
        adminLoginWindowV.setAlignment(Pos.CENTER);
        adminLogInScene = new Scene(adminLoginWindowV, 400, 400);



        /*------------------------------------------------------------------------------------*/

        //previewCosts
        //set label
        Label previewTransfer = new Label("preview costs transfer select");
        previewTransfer.setFont(new Font("Arial", 20));
        Label previewCost = new Label("preview costs");
        previewCost.setFont(new Font("Arial", 20));

        Button Transfer1 = new Button("Transfer trip");
        Transfer1.setOnAction(e -> window.setScene(Transfer));

        Button noTransfer = new Button("Non Transfer trip");
        noTransfer.setOnAction(e -> window.setScene(NTransfer));

        Button back6 = new Button("Back");
        back6.setOnAction(e -> window.setScene(mainScene));

        VBox transferChooseWindow = new VBox(20);
        transferChooseWindow.getChildren().addAll( previewTransfer, Transfer1, noTransfer, back6);
        transferChooseWindow.setAlignment(Pos.CENTER);
        previewCostsScene = new Scene(transferChooseWindow, 400, 400);
        //


        Button byBus7 = new Button("by bus");
        byBus7.setOnAction(e -> PopUpWindow.popUp("Dear Customer", "Single Bus trip without a transfer to"+
                " subway cost 2 dollars from any stops to any destination bus stop"));

        Button bySubway7 = new Button("by subway");
        bySubway7.setOnAction(e -> window.setScene(subwayPreview));

        Button back7 = new Button("Back");
        back7.setOnAction(e -> window.setScene(previewCostsScene));

        VBox NTransferWindow = new VBox(20);
        NTransferWindow.getChildren().addAll(byBus7,bySubway7,back7);
        NTransferWindow.setAlignment(Pos.CENTER);
        NTransfer = new Scene(NTransferWindow, 400, 400);


        //title
        Label stationSelect = new Label("Station selection");
        stationSelect.setFont(new Font("Arial", 18));
        GridPane.setConstraints(stationSelect, 0, 1);

        //chose tap in or out Subway station
        Label station1 = new Label("Current Station");
        GridPane.setConstraints(station1, 0, 2);
        ComboBox<String> stationList1 = new ComboBox<>();
        stationList1.getItems().addAll(stationNames);
        GridPane.setConstraints(stationList1, 1, 2);

        //chose tap in or out Subway station
        Label dest = new Label("Destination Station");
        GridPane.setConstraints(dest, 0, 3);
        ComboBox<String> destStationList = new ComboBox<>();
        destStationList.getItems().addAll(stationNames);
        GridPane.setConstraints(destStationList, 1, 3);

        //set subTapInScene

        GridPane subStart = new GridPane();
        subStart.setPadding(new Insets(10, 10, 10, 10));
        subStart.setVgap(8);
        subStart.setHgap(10);

        //submit button
        Button submit9 = new Button("Submit");
        submit9.setOnAction(e ->
        {
            if(stationList1.getSelectionModel().getSelectedItem() == null ||
                    destStationList.getSelectionModel().getSelectedItem() == null)

            {PopUpWindow.popUp("Dear Customer", "Please select a start and destination before you submit");}
            else {
                FareCalculator previewSubFare = new FareCalculator();
                previewSubFare.tapOffSubway(TransitSystem.findSubLocation(stationList1.getSelectionModel().getSelectedItem()),
                        TransitSystem.findSubLocation(destStationList.getSelectionModel().getSelectedItem()));
                PopUpWindow.popUp("Dear Customer", "Your ride cost is " + previewSubFare.getSingleFare());
            }});
        GridPane.setConstraints(submit9,1, 4);

        //back button
        Button subTapBack9 = new Button("Back");
        GridPane.setConstraints(subTapBack9,2, 5);
        subTapBack9.setOnAction(e -> {
            stationList1.getSelectionModel().clearSelection();
            destStationList.getSelectionModel().clearSelection();
            window.setScene(NTransfer);

        });
        //
        subStart.getChildren().addAll(stationSelect,dest,station1,stationList1,destStationList, submit9,subTapBack9);
        subStart.setAlignment(Pos.CENTER);
        subwayPreview = new Scene(subStart, 400, 400);
        //transfer trip scenes

        Button byBus8 = new Button("Start with bus end with subway");
        byBus8.setOnAction(e -> window.setScene(busTransfer));

        Button bySubway8 = new Button("Start with subway end with bus");
        bySubway8.setOnAction(e -> window.setScene(subwayTransfer));

        Button back8 = new Button("Back");
        back8.setOnAction(e -> window.setScene(previewCostsScene));

        VBox TransferWindow = new VBox(20);
        TransferWindow.getChildren().addAll(byBus8,bySubway8,back8);
        TransferWindow.setAlignment(Pos.CENTER);
        Transfer = new Scene(TransferWindow, 400, 400);
        //bus transfer scene

        //chose tap in or out bus
        Label stop9 = new Label("Current Stop");
        GridPane.setConstraints(stop9, 0, 2);
        ComboBox<String> stopList8 = new ComboBox<>();
        stopList8.getItems().addAll(stopNames);
        GridPane.setConstraints(stopList8, 1, 2);

        //chose tap in or out Subway station
        Label dest1 = new Label("Destination Station");
        GridPane.setConstraints(dest1, 0, 3);
        ComboBox<String> destStationList1 = new ComboBox<>();
        destStationList1.getItems().addAll(stationNames);
        GridPane.setConstraints(destStationList1, 1, 3);
        //submit

        Button back81 = new Button("Back");
        back81.setOnAction(e ->
        {
            destStationList1.getSelectionModel().clearSelection();
            stopList8.getSelectionModel().clearSelection();
            window.setScene(Transfer);
        });
        GridPane.setConstraints(back81, 2, 5);

        Button submit7 = new Button("Submit");
        submit7.setOnAction(e ->
        {
            if(stopList8.getSelectionModel().getSelectedItem() == null ||
                    destStationList1.getSelectionModel().getSelectedItem() == null)

            {PopUpWindow.popUp("Dear Customer", "Please select a start and destination before you submit");}
            else {
                FareCalculator transferBus = new FareCalculator();
                transferBus.tapOnBus();
                transferBus.tapOffSubway(TransitSystem.findSubLocation("Finch"),
                        TransitSystem.findSubLocation(destStationList1.getSelectionModel().getSelectedItem()));
                PopUpWindow.popUp("Dear Customer", "Your ride cost is " + transferBus.getSingleFare());
            }});
        GridPane.setConstraints(submit7,1, 4);

        //set scene
        Label busLabel = new Label("Select start and destination");
        busLabel.setFont(new Font("Arial", 16));
        GridPane.setConstraints(busLabel,0,0);
        GridPane busStart = new GridPane();
        busStart.setPadding(new Insets(10, 10, 10, 10));
        busStart.setVgap(8);
        busStart.setHgap(10);
        busStart.getChildren().addAll(dest1,stop9,destStationList1,stopList8,busLabel,submit7,back81);
        busStart.setAlignment(Pos.CENTER);
        busTransfer = new Scene(busStart, 500, 500);


        /////////////////////////////////////////////////////////
        //subway transfer scene

        //chose tap in or out bus
        Label stop6 = new Label("Current Station");
        GridPane.setConstraints(stop6, 0, 2);
        ComboBox<String> stationList7 = new ComboBox<>();
        stationList7.getItems().addAll(stationNames);
        GridPane.setConstraints(stationList7, 1, 2);

        //chose tap in or out Subway station
        Label dest2 = new Label("Destination Stop");
        GridPane.setConstraints(dest2, 0, 3);
        ComboBox<String> destStationList2 = new ComboBox<>();
        destStationList2.getItems().addAll(stopNames);
        GridPane.setConstraints(destStationList2, 1, 3);
        //submit

        Button submit6 = new Button("Submit");
        submit6.setOnAction(e ->
        {

            if(stationList7.getSelectionModel().getSelectedItem() == null ||
                    destStationList2.getSelectionModel().getSelectedItem() == null)

            {PopUpWindow.popUp("Dear Customer", "Please select a start and destination before you submit");}
            else
            {
                FareCalculator transferSub = new FareCalculator();

                transferSub.tapOffSubway(TransitSystem.findSubLocation("Finch"),
                        TransitSystem.findSubLocation(stationList7.getSelectionModel().getSelectedItem()));
                transferSub.tapOnBus();
                PopUpWindow.popUp("Dear Customer", "Your ride cost is " + transferSub.getSingleFare());
            }
        });
        GridPane.setConstraints(submit6,1, 4);


        Button back82 = new Button("Back");
        back82.setOnAction(e -> {
            window.setScene(Transfer);
            stationList7.getSelectionModel().clearSelection();
            destStationList2.getSelectionModel().clearSelection();

        });
        GridPane.setConstraints(back82, 2, 4);
        //set scene
        Label subLabel = new Label("Select start and destination");
        subLabel.setFont(new Font("Arial", 16));
        GridPane.setConstraints(busLabel,0,0);
        GridPane subStart1 = new GridPane();
        subStart1.setPadding(new Insets(10, 10, 10, 10));
        subStart1.setVgap(8);
        subStart1.setHgap(10);
        subStart1.getChildren().addAll(subLabel,stop6, stationList7,dest2,destStationList2,submit6,back82 );
        subStart1.setAlignment(Pos.CENTER);
        subwayTransfer = new Scene(subStart1, 500, 500);

        /*------------------------------------------------------------------------------------*/

        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set admin user sign up scene

        GridPane adminUserSignUpWindow = new GridPane();
        adminUserSignUpWindow.setPadding(new Insets(20, 20, 20, 20));
        adminUserSignUpWindow.setVgap(8);
        adminUserSignUpWindow.setHgap(10);
        adminUserSignUpWindow.setAlignment(Pos.CENTER);

        //set label
        Label adminUserSignUpLabel = new Label("Admin User Sign Up");
        adminUserSignUpLabel.setFont(new Font("Arial", 20));

        //set admin user code
        Label adminCodeLabel = new Label("Admin User Code");
        GridPane.setConstraints(adminCodeLabel, 0, 0);
        TextField adminCodeInput = new TextField();
        adminCodeInput.setPromptText("Enter your code");
        GridPane.setConstraints(adminCodeInput, 1, 0);

        //set name
        Label adminNameLabel = new Label("Your Name");
        GridPane.setConstraints(adminNameLabel, 0, 1);
        Label nameHint = new Label("(Name only letters; \nno space or other characters)");
        nameHint.setFont(new Font(10));
        GridPane.setConstraints(nameHint,1,2);
        TextField adminNameInput = new TextField();
        adminNameInput.setPromptText("Enter your name");
        GridPane.setConstraints(adminNameInput, 1, 1);

        //set email
        Label adminSignEmailLabel = new Label("Your Email");
        GridPane.setConstraints(adminSignEmailLabel, 0, 3);
        TextField adminSignEmailInput = new TextField();
        adminSignEmailInput.setPromptText("Enter your email address to sign up");
        GridPane.setConstraints(adminSignEmailInput, 1, 3);

        //set password
        Label adminSignPasswordLabel = new Label("Password");
        GridPane.setConstraints(adminSignPasswordLabel, 0,4);
        Label passwordHint = new Label("(6-10 characters; only contains: " +
                "\nletters/numbers/mix letters and numbers)");
        passwordHint.setFont(new Font(10));
        GridPane.setConstraints(passwordHint,1,5);
        PasswordField adminSignPasswordInput = new PasswordField();
        adminSignPasswordInput.setPromptText("Enter your password");
        GridPane.setConstraints(adminSignPasswordInput, 1, 4);

        //set confirm password
        Label adminSignConfirmPasswordLabel = new Label("Confirm Password");
        GridPane.setConstraints(adminSignConfirmPasswordLabel, 0,6);
        PasswordField adminSignConfirmPasswordInput = new PasswordField();
        adminSignConfirmPasswordInput.setPromptText("Enter your password again");
        GridPane.setConstraints(adminSignConfirmPasswordInput, 1, 6);

        //sign Up button
        Button adminUserSignUp = new Button("Sign Up");
        adminUserSignUp.setOnAction(e -> {
            if(adminCodeInput.getText().equals("") || adminNameInput.getText().equals("")
                    || adminSignEmailInput.getText().equals("") || adminSignPasswordInput.getText().equals("")
                    || adminSignConfirmPasswordInput.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please fill out all information required.");
            }
            else if(!adminCodeInput.getText().equals(AdminUserCode)){
                PopUpWindow.popUp("Warning", "Wrong Admin User Code!");adminCodeInput.clear();}
            else if(!adminNameInput.getText().matches("[a-zA-Z]+")){
                PopUpWindow.popUp("Warning","Invalid name input! \nName should only contains letters!");
                adminNameInput.clear();}
            else if(!adminSignEmailInput.getText().matches(
                    "[a-zA-Z0-9]+[.]*[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+")){
                PopUpWindow.popUp("Warning",
                        "Invalid email address! \nPleas enter a valid email address!");
                adminSignEmailInput.clear();}
            else if(!adminSignPasswordInput.getText().matches("[a-zA-Z0-9]{6,10}")){
                PopUpWindow.popUp("Warning",
                        "Invalid password! \nUse 6-10 characters with a mix of letters and numbers," +
                                "\nor only letters or only numbers.");
                adminSignPasswordInput.clear(); adminSignConfirmPasswordInput.clear();}
            else if(!adminSignConfirmPasswordInput.getText().equals(adminSignPasswordInput.getText())){
                PopUpWindow.popUp("Warning", "Password does not match! \nPleas try again!");
                adminSignConfirmPasswordInput.clear();
            }else if(ACManager.isUserEmailUsedAdmin(adminSignEmailInput.getText())){
                PopUpWindow.popUp("Warning","Email has been signed up!" +
                        "\nPlease enter a new email address! " +
                        "\nOr try to log in!");}
            else{
                ACManager.addAdminUser(new AdminUserAccount(adminNameInput.getText(),
                        adminSignEmailInput.getText(), adminSignPasswordInput.getText()));
                PopUpWindow.popUp("Successful", "Congratulation! Sign Up Successful!");
                window.setScene(adminLogInScene);
                adminCodeInput.clear();adminNameInput.clear();
                adminSignEmailInput.clear();adminSignPasswordInput.clear(); adminSignConfirmPasswordInput.clear();
            }
        });
        GridPane.setConstraints(adminUserSignUp,1, 7);

        //back button
        Button adminUserBack = new Button("Back");
        GridPane.setConstraints(adminUserBack,2, 8);
        adminUserBack.setOnAction(e -> {
            window.setScene(adminLogInScene);adminCodeInput.clear();
            adminNameInput.clear();adminSignEmailInput.clear();
            adminSignPasswordInput.clear();adminSignConfirmPasswordInput.clear();});

        adminUserSignUpWindow.getChildren().addAll(adminCodeLabel, adminCodeInput, adminNameLabel,
                adminNameInput, adminSignEmailLabel, adminSignEmailInput,
                adminSignPasswordLabel, adminSignPasswordInput, adminUserSignUp, adminUserBack,
                adminSignConfirmPasswordInput, adminSignConfirmPasswordLabel, passwordHint, nameHint);
        VBox adminUserSignUpWindowV = new VBox();
        adminUserSignUpWindowV.getChildren().addAll(adminUserSignUpLabel, adminUserSignUpWindow);
        adminUserSignUpWindowV.setAlignment(Pos.CENTER);
        adminSignUpScene = new Scene(adminUserSignUpWindowV, 450, 450);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //bus and subway ride button, also back button

        Button bus = new Button("By Bus");
        bus.setOnAction(e -> {if(!TransitSystem.checkIsSystemOpen()){
            PopUpWindow.popUp("Warning", "System close now! \nTry again after 05:00!");}
            else{window.setScene(busTapScene);}});
        Button subway = new Button("By Subway");
        subway.setOnAction(e -> {if(!TransitSystem.checkIsSystemOpen()){
            PopUpWindow.popUp("Warning", "System close now! \nTry again after 05:00!");}
        else{window.setScene(subTapScene);}});
        Button back2 = new Button("Back");
        back2.setOnAction(e -> window.setScene(mainScene));

        //set label
        Label tapLabel = new Label("Please Notice That: \nOur System will close at 23:59 and open at 05:00.");
        tapLabel.setFont(new Font(10));
        tapLabel.setAlignment(Pos.CENTER);

        //set ride scene
        VBox rideChooseWindow = new VBox(20);
        rideChooseWindow.getChildren().addAll(tapLabel, bus, subway, back2);
        rideChooseWindow.setAlignment(Pos.CENTER);
        rideScene = new Scene(rideChooseWindow, 400, 400);

        //set busTapScene
        Button tapIn = new Button("Tap In");
        tapIn.setOnAction(e -> {window.setScene(busTapInScene);});
        Button tapOut = new Button("Tap Out");
        tapOut.setOnAction(e -> window.setScene(busTapOutScene));
        Button back3 = new Button("Back");
        back3.setOnAction(e -> window.setScene(rideScene));
        VBox tapWindow = new VBox(20);
        tapWindow.getChildren().addAll(tapIn, tapOut, back3);
        tapWindow.setAlignment(Pos.CENTER);
        busTapScene = new Scene(tapWindow, 400, 400);

        //set subTapScene
        Button tapIn1 = new Button("Tap In");
        tapIn1.setOnAction(e -> window.setScene(subTapInScene));
        Button tapOut1 = new Button("Tap Out");
        tapOut1.setOnAction(e -> window.setScene(subTapOutScene));
        Button back4 = new Button("Back");
        back4.setOnAction(e -> window.setScene(rideScene));
        VBox tapWindow1 = new VBox(20);
        tapWindow1.getChildren().addAll(tapIn1, tapOut1, back4);
        tapWindow1.setAlignment(Pos.CENTER);
        subTapScene = new Scene(tapWindow1, 400, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set busTapInScene

        GridPane busTapIn = new GridPane();
        busTapIn.setPadding(new Insets(10, 10, 10, 10));
        busTapIn.setVgap(8);
        busTapIn.setHgap(10);
        busTapIn.setAlignment(Pos.CENTER);

        //enter card ID
        Label cardID = new Label("Card ID");
        GridPane.setConstraints(cardID, 0, 0);
        TextField cardIDInput = new TextField();
        cardIDInput.setPromptText("Enter User's Card ID");
        GridPane.setConstraints(cardIDInput, 1, 0);

        //enter date
        Label date = new Label("Date");
        GridPane.setConstraints(date, 0, 1);
        TextField dateInput = new TextField();
        dateInput.setPromptText("YYYYMMDD");
        GridPane.setConstraints(dateInput, 1, 1);

        //enter time
        Label time = new Label("Current Time");
        GridPane.setConstraints(time, 0, 2);
        TextField timeInput = new TextField();
        timeInput.setPromptText("HHMM ex. 1830");
        GridPane.setConstraints(timeInput, 1, 2);

        //chose tap in or out bus stop
        Label stop = new Label("Current Stop");
        GridPane.setConstraints(stop, 0, 3);
        ComboBox<String> stopList = new ComboBox<>();
        stopList.getItems().addAll(stopNames);
        GridPane.setConstraints(stopList, 1, 3);

        //submit button
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            if(cardIDInput.getText().equals("")||dateInput.getText().equals("")||timeInput.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please fill out all information required."); }
            else if(!cardIDInput.getText().matches("[0-9]+") ||
                    CManager.findCard(Integer.parseInt(cardIDInput.getText())) == null){
                PopUpWindow.popUp("Warning", "Invalid Card ID! \nPlease try again!");
                cardIDInput.clear();}
            else if(dateInput.getText().length() != 8 || !dateInput.getText().matches("[0-9]+")){
                PopUpWindow.popUp("Warning", "Invalid Date Input! \nPlease try again!");
                dateInput.clear();}
            else if(timeInput.getText().length() != 4 || !timeInput.getText().matches("[0-9]+")){
                PopUpWindow.popUp("Warning", "Invalid Time Input! \nPlease try again!");
                timeInput.clear();}
            else if(stopList.getSelectionModel().getSelectedItem() == null){
                PopUpWindow.popUp("Warning", "Please choose one bus stop!"); }
            else if(CManager.isSuspend(Integer.parseInt(cardIDInput.getText()))){
                PopUpWindow.popUp("Warning","Your card is suspended! \nPlease try other card!");
                cardIDInput.clear();}
            else if(!CManager.validBalance(Integer.parseInt(cardIDInput.getText()))){
                PopUpWindow.popUp("Warning","Insufficient Balance! \nPlease load your card!");
                cardIDInput.clear();}
            else {
                Trip newTrip = new Trip(Integer.parseInt(cardIDInput.getText()),
                        Integer.parseInt(timeInput.getText()), Integer.parseInt(dateInput.getText()),
                        TransitSystem.findBusLocation(stopList.getValue()));
                newTrip.changeTapInStatus();
                TManager.syncCardManager(CManager);
                TManager.addTrip(newTrip);
                String message_ = "Successfully Tap In! \n"
                        + "Bus Stop: " + stopList.getSelectionModel().getSelectedItem() + "\n"
                        + "Current Time: " + timeInput.getText() + "; \n"
                        + "Today's Date: " + dateInput.getText() + "; \n"
                        + "Card ID: " + cardIDInput.getText() + "; \n" + "Card Balance: "
                        + String.valueOf(CManager.findCard(Integer.parseInt
                        (cardIDInput.getText())).getBalance());
                if(CManager.findCard(Integer.parseInt(cardIDInput.getText())).checkBalanceLessThanSix()){
                    message_ += "\nYour card balance is below $6." ;
                    message_ += "\nPlease load your card to avoid any inconvenience.";
                }
                my_log.log(Level.INFO, "Successfully Tap In! \n"
                        + "Bus Stop: " + stopList.getSelectionModel().getSelectedItem() + "\n"
                        + "Current Time: " + timeInput.getText() + "; \n"
                        + "Today's Date: " + dateInput.getText() + "; \n"
                        + "Card ID: " + cardIDInput.getText() + "; \n" + "Card Balance: "
                        + String.valueOf(CManager.findCard(Integer.parseInt
                        (cardIDInput.getText())).getBalance()));
                PopUpWindow.popUp("Trip Summary", message_);
                window.setScene(rideScene);
                timeInput.clear();dateInput.clear();cardIDInput.clear();
                stopList.getSelectionModel().clearSelection();}
        });
        GridPane.setConstraints(submit,1, 4);

        //back button
        Button busTapBack = new Button("Back");
        GridPane.setConstraints(busTapBack,2, 5);
        busTapBack.setOnAction(e -> {
            window.setScene(busTapScene);timeInput.clear();dateInput.clear();cardIDInput.clear();
            stopList.getSelectionModel().clearSelection();
        });

        busTapIn.getChildren().addAll(date, dateInput, time, timeInput, busTapBack, stop,
                stopList, submit, cardID, cardIDInput);
        busTapIn.setAlignment(Pos.CENTER);
        busTapInScene = new Scene(busTapIn, 400, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set busTapOutScene

        GridPane busTapOut = new GridPane();
        busTapOut.setPadding(new Insets(10, 10, 10, 10));
        busTapOut.setVgap(8);
        busTapOut.setHgap(10);
        busTapOut.setAlignment(Pos.CENTER);

        //enter card ID
        Label cardID_ = new Label("Card ID");
        GridPane.setConstraints(cardID_, 0, 0);
        TextField cardIDInput_ = new TextField();
        cardIDInput_.setPromptText("Enter User's Card ID");
        GridPane.setConstraints(cardIDInput_, 1, 0);

        //enter date
        Label date_ = new Label("Date");
        GridPane.setConstraints(date_, 0, 1);
        TextField dateInput_ = new TextField();
        dateInput_.setPromptText("YYYYMMDD");
        GridPane.setConstraints(dateInput_, 1, 1);

        //enter time
        Label time_ = new Label("Current Time");
        GridPane.setConstraints(time_, 0, 2);
        TextField timeInput_ = new TextField();
        timeInput_.setPromptText("HHMM ex. 1830");
        GridPane.setConstraints(timeInput_, 1, 2);

        //chose tap in or out bus stop
        Label stop_ = new Label("Current Stop");
        GridPane.setConstraints(stop_, 0, 3);
        ComboBox<String> stopList_ = new ComboBox<>();
        stopList_.getItems().addAll(stopNames);
        GridPane.setConstraints(stopList_, 1, 3);

        //submit button
        Button submit_ = new Button("Submit");
        submit_.setOnAction(e -> {
            if(cardIDInput_.getText().equals("")||dateInput_.getText().equals("")||timeInput_.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please fill out all information required."); }
            else if(!cardIDInput_.getText().matches("[0-9]+") ||
                    CManager.findCard(Integer.parseInt(cardIDInput_.getText())) == null){
                PopUpWindow.popUp("Warning", "Invalid Card ID! \nPlease try again!");
                cardIDInput_.clear();}
            else if(dateInput_.getText().length() != 8 || !dateInput_.getText().matches("[0-9]+")){
                PopUpWindow.popUp("Warning", "Invalid Date Input! \nPlease try again!");
                dateInput_.clear();}
            else if(timeInput_.getText().length() != 4 || !timeInput_.getText().matches("[0-9]+")){
                PopUpWindow.popUp("Warning", "Invalid Time Input! \nPlease try again!");
                timeInput_.clear();}
            else if(stopList_.getSelectionModel().getSelectedItem() == null){
                PopUpWindow.popUp("Warning", "Please choose one bus stop!"); }
            else if(CManager.isSuspend(Integer.parseInt(cardIDInput_.getText()))){
                PopUpWindow.popUp("Warning","Your card is suspended! \nPlease try other card!");
                cardIDInput_.clear();}
            else {
                Trip newTrip = new Trip(Integer.parseInt(cardIDInput_.getText()),
                        Integer.parseInt(timeInput_.getText()), Integer.parseInt(dateInput_.getText()),
                        TransitSystem.findBusLocation(stopList_.getValue()));
                newTrip.changeTapOutStatus();
                TManager.syncCardManager(CManager);
                TManager.addTrip(newTrip);
                String message_ = "Successfully Tap out! \n"
                        + "Bus Stop: " + stopList_.getSelectionModel().getSelectedItem() + "\n"
                        + "Current Time: " + timeInput_.getText() + "; \n"
                        + "Today's Date: " + dateInput_.getText() + "; \n"
                        + "Card ID: " + cardIDInput_.getText() + "; \n" + "Card Balance: "
                        + String.valueOf(CManager.findCard(Integer.parseInt
                        (cardIDInput_.getText())).getBalance());
                if(CManager.findCard(Integer.parseInt(cardIDInput_.getText())).checkBalanceLessThanSix()){
                    message_ += "\nYour card balance is below $6." ;
                    message_ += "\nPlease load your card to avoid any inconvenience.";
                }
                PopUpWindow.popUp("Trip Summary", message_);
                my_log.log(Level.INFO, "Successfully Tap out! \n"
                        + "Bus Stop: " + stopList_.getSelectionModel().getSelectedItem() + "\n"
                        + "Current Time: " + timeInput_.getText() + "; \n"
                        + "Today's Date: " + dateInput_.getText() + "; \n"
                        + "Card ID: " + cardIDInput_.getText() + "; \n" + "Card Balance: "
                        + String.valueOf(CManager.findCard(Integer.parseInt
                        (cardIDInput_.getText())).getBalance()));
                window.setScene(rideScene);
                timeInput_.clear();dateInput_.clear();cardIDInput_.clear();
                stopList_.getSelectionModel().clearSelection();}
        });
        GridPane.setConstraints(submit_,1, 4);

        //back button
        Button busTapBack_ = new Button("Back");
        GridPane.setConstraints(busTapBack_,2, 5);
        busTapBack_.setOnAction(e -> {
            window.setScene(busTapScene);timeInput_.clear();dateInput_.clear();cardIDInput_.clear();
            stopList_.getSelectionModel().clearSelection();});

        busTapOut.getChildren().addAll(date_, dateInput_, time_, timeInput_, busTapBack_, stop_,
                stopList_, submit_, cardID_, cardIDInput_);
        busTapOut.setAlignment(Pos.CENTER);
        busTapOutScene = new Scene(busTapOut, 400, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set subTapInScene

        GridPane subTapIn = new GridPane();
        subTapIn.setPadding(new Insets(10, 10, 10, 10));
        subTapIn.setVgap(8);
        subTapIn.setHgap(10);
        subTapIn.setAlignment(Pos.CENTER);

        //enter card ID
        Label cardID1 = new Label("Card ID");
        GridPane.setConstraints(cardID1, 0, 0);
        TextField cardIDInput1 = new TextField();
        cardIDInput1.setPromptText("Enter User's Card ID");
        GridPane.setConstraints(cardIDInput1, 1, 0);

        //enter date
        Label date1 = new Label("Date");
        GridPane.setConstraints(date1, 0, 1);
        TextField dateInput1 = new TextField();
        dateInput1.setPromptText("YYYYMMDD");
        GridPane.setConstraints(dateInput1, 1, 1);

        //enter time
        Label time1 = new Label("Current Time");
        GridPane.setConstraints(time1, 0, 2);
        TextField timeInput1 = new TextField();
        timeInput1.setPromptText("HHMM ex. 1830");
        GridPane.setConstraints(timeInput1, 1, 2);

        //chose tap in or out Subway station
        Label station = new Label("Current Station");
        GridPane.setConstraints(station, 0, 3);
        ComboBox<String> stationList = new ComboBox<>();
        stationList.getItems().addAll(stationNames);
        GridPane.setConstraints(stationList, 1, 3);

        //submit button
        Button submit1 = new Button("Submit");
        submit1.setOnAction(e -> {
            if(cardIDInput1.getText().equals("")||dateInput1.getText().equals("")||timeInput1.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please fill out all information required."); }
            else if(!cardIDInput1.getText().matches("[0-9]+") ||
                    CManager.findCard(Integer.parseInt(cardIDInput1.getText())) == null){
                PopUpWindow.popUp("Warning", "Invalid Card ID! \nPlease try again!");
                cardIDInput1.clear();}
            else if(dateInput1.getText().length() != 8 || !dateInput1.getText().matches("[0-9]+")){
                PopUpWindow.popUp("Warning", "Invalid Date Input! \nPlease try again!");
                dateInput1.clear();}
            else if(timeInput1.getText().length() != 4 || !timeInput1.getText().matches("[0-9]+")){
                PopUpWindow.popUp("Warning", "Invalid Time Input! \nPlease try again!");
                timeInput1.clear();}
            else if(stationList.getSelectionModel().getSelectedItem() == null){
                PopUpWindow.popUp("Warning", "Please choose one subway station!");}
            else if(CManager.isSuspend(Integer.parseInt(cardIDInput1.getText()))){
                PopUpWindow.popUp("Warning","Your card is suspended! \nPlease try other card!");
                cardIDInput1.clear();}
            else if(!CManager.validBalance(Integer.parseInt(cardIDInput1.getText()))){
                PopUpWindow.popUp("Warning","Insufficient Balance! \nPlease load your card!");
                cardIDInput1.clear();}
            else {
                Trip newTrip = new Trip(Integer.parseInt(cardIDInput1.getText()),
                        Integer.parseInt(timeInput1.getText()), Integer.parseInt(dateInput1.getText()),
                        TransitSystem.findSubLocation(stationList.getValue()));
                newTrip.changeTapInStatus();
                TManager.syncCardManager(CManager);
                TManager.addTrip(newTrip);
                String message = "Successfully Tap In! \n"
                        + "Subway Station: " + stationList.getSelectionModel().getSelectedItem() + "\n"
                        + "Current Time: " + timeInput1.getText() + "; \n"
                        + "Today's Date: " + dateInput1.getText() + "; \n"
                        + "Card ID: " + cardIDInput1.getText() + "; \n" + "Card Balance: "
                        + String.valueOf(CManager.findCard(Integer.parseInt
                        (cardIDInput1.getText())).getBalance());
                if(CManager.findCard(Integer.parseInt(cardIDInput1.getText())).checkBalanceLessThanSix()){
                    message += "\nYour card balance is below $6." ;
                    message += "\nPlease load your card to avoid any inconvenience."; }
                PopUpWindow.popUp("Trip Summary", message);
                my_log.log(Level.INFO,"Successfully Tap In! \n"
                        + "Subway Station: " + stationList.getSelectionModel().getSelectedItem() + "\n"
                        + "Current Time: " + timeInput1.getText() + "; \n"
                        + "Today's Date: " + dateInput1.getText() + "; \n"
                        + "Card ID: " + cardIDInput1.getText() + "; \n" + "Card Balance: "
                        + String.valueOf(CManager.findCard(Integer.parseInt
                        (cardIDInput1.getText())).getBalance()));
                window.setScene(rideScene);
                timeInput1.clear();dateInput1.clear();cardIDInput1.clear();
                stationList.getSelectionModel().clearSelection();}
        });
        GridPane.setConstraints(submit1,1, 4);

        //back button
        Button subTapBack = new Button("Back");
        GridPane.setConstraints(subTapBack,2, 5);
        subTapBack.setOnAction(e -> {
            window.setScene(subTapScene);timeInput1.clear();dateInput1.clear();cardIDInput1.clear();
            stationList.getSelectionModel().clearSelection();});

        subTapIn.getChildren().addAll(date1, dateInput1, time1, timeInput1, subTapBack, station,
                stationList, submit1, cardID1, cardIDInput1);
        subTapIn.setAlignment(Pos.CENTER);
        subTapInScene = new Scene(subTapIn, 400, 400);
        /*------------------------------------------------------------------------------------*/



        /*------------------------------------------------------------------------------------*/
        //set subTapOutScene

        GridPane subTapOut = new GridPane();
        subTapOut.setPadding(new Insets(10, 10, 10, 10));
        subTapOut.setVgap(8);
        subTapOut.setHgap(10);
        subTapOut.setAlignment(Pos.CENTER);

        //enter card ID
        Label cardID1_ = new Label("Card ID");
        GridPane.setConstraints(cardID1_, 0, 0);
        TextField cardIDInput1_ = new TextField();
        cardIDInput1_.setPromptText("Enter User's Card ID");
        GridPane.setConstraints(cardIDInput1_, 1, 0);

        //enter date
        Label date1_ = new Label("Date");
        GridPane.setConstraints(date1_, 0, 1);
        TextField dateInput1_ = new TextField();
        dateInput1_.setPromptText("YYYYMMDD");
        GridPane.setConstraints(dateInput1_, 1, 1);

        //enter time
        Label time1_ = new Label("Current Time");
        GridPane.setConstraints(time1_, 0, 2);
        TextField timeInput1_ = new TextField();
        timeInput1_.setPromptText("HHMM ex. 1830");
        GridPane.setConstraints(timeInput1_, 1, 2);

        //chose tap in or out Subway station
        Label station_ = new Label("Current Station");
        GridPane.setConstraints(station_, 0, 3);
        ComboBox<String> stationList_ = new ComboBox<>();
        stationList_.getItems().addAll(stationNames);
        GridPane.setConstraints(stationList_, 1, 3);

        //submit button
        Button submit1_ = new Button("Submit");
        submit1_.setOnAction(e -> {
            if(cardIDInput1_.getText().equals("")||dateInput1_.getText().equals("")||timeInput1_.getText().equals("")){
                PopUpWindow.popUp("Warning", "Please fill out all information required."); }
            else if(!cardIDInput1_.getText().matches("[0-9]+") ||
                    CManager.findCard(Integer.parseInt(cardIDInput1_.getText())) == null){
                PopUpWindow.popUp("Warning", "Invalid Card ID! \nPlease try again!");
                cardIDInput1_.clear();}
            else if(dateInput1_.getText().length() != 8 || !dateInput1_.getText().matches("[0-9]+")){
                PopUpWindow.popUp("Warning", "Invalid Date Input! \nPlease try again!");
                dateInput1_.clear();}
            else if(timeInput1_.getText().length() != 4 || !timeInput1_.getText().matches("[0-9]+")){
                PopUpWindow.popUp("Warning", "Invalid Time Input! \nPlease try again!");
                timeInput1_.clear();}
            else if(stationList_.getSelectionModel().getSelectedItem() == null){
                PopUpWindow.popUp("Warning", "Please choose one subway station!"); }
            else if(CManager.isSuspend(Integer.parseInt(cardIDInput1_.getText()))){
                PopUpWindow.popUp("Warning","Your card is suspended! \nPlease try other card!");
                cardIDInput1_.clear();}
            else {
                Trip newTrip = new Trip(Integer.parseInt(cardIDInput1_.getText()),
                        Integer.parseInt(timeInput1_.getText()), Integer.parseInt(dateInput1_.getText()),
                        TransitSystem.findSubLocation(stationList_.getValue()));
                newTrip.changeTapOutStatus();
                TManager.syncCardManager(CManager);
                TManager.addTrip(newTrip);
                String message = "Successfully Tap out! \n"
                        + "Subway Station: " + stationList_.getSelectionModel().getSelectedItem() + "\n"
                        + "Current Time: " + timeInput1_.getText() + "; \n"
                        + "Today's Date: " + dateInput1_.getText() + "; \n"
                        + "Card ID: " + cardIDInput1_.getText() + "; \n" + "Card Balance: "
                        + String.valueOf(CManager.findCard(Integer.parseInt
                        (cardIDInput1_.getText())).getBalance());
                if(CManager.findCard(Integer.parseInt(cardIDInput1_.getText())).checkBalanceLessThanSix()){
                    message += "\nYour card balance is below $6." ;
                    message += "\nPlease load your card to avoid any inconvenience."; }
                PopUpWindow.popUp("Trip Summary", message);
                my_log.log(Level.INFO,"Successfully Tap out! \n"
                        + "Subway Station: " + stationList_.getSelectionModel().getSelectedItem() + "\n"
                        + "Current Time: " + timeInput1_.getText() + "; \n"
                        + "Today's Date: " + dateInput1_.getText() + "; \n"
                        + "Card ID: " + cardIDInput1_.getText() + "; \n" + "Card Balance: "
                        + String.valueOf(CManager.findCard(Integer.parseInt
                        (cardIDInput1_.getText())).getBalance()));
                window.setScene(rideScene);
                timeInput1_.clear();dateInput1_.clear();cardIDInput1_.clear();
                stationList_.getSelectionModel().clearSelection();}
        });
        GridPane.setConstraints(submit1_,1, 4);

        //back button
        Button subTapBack_ = new Button("Back");
        GridPane.setConstraints(subTapBack_,2, 5);
        subTapBack_.setOnAction(e -> {
            window.setScene(subTapScene);timeInput1_.clear();dateInput1_.clear();cardIDInput1_.clear();
            stationList_.getSelectionModel().clearSelection(); });

        subTapOut.getChildren().addAll(date1_, dateInput1_, time1_, timeInput1_, subTapBack_, station_,
                stationList_, submit1_, cardID1_, cardIDInput1_);
        subTapOut.setAlignment(Pos.CENTER);
        subTapOutScene = new Scene(subTapOut, 400, 400);
        /*------------------------------------------------------------------------------------*/


        window.setScene(mainScene);
        window.show();

    }
    private ObservableList<Card> getCard(){
        ObservableList<Card> cards = FXCollections.observableArrayList();
        if(currentUser != null){
        cards.addAll(currentUser.getCardCollection());}
        return cards;
    }
    private void addButtonClicked(){
        try{
            Card card = new Card(Integer.parseInt(enterCardID.getText()));
            cardTableView.getItems().add(card);
            CManager.addCard(card);
            currentUser.addCard(card);
            enterCardID.clear();
        }catch(NumberFormatException e){
            PopUpWindow.popUp("Invalid Card ID", "Warning! The card ID you input is not valid. Card ID " +
                    "can only contain numbers.");
        }
    }
    private void removeButtonClicked(){

        Card cardSelected = cardTableView.getSelectionModel().getSelectedItem();
        ObservableList<Card> allCards = cardTableView.getItems();
        allCards.remove(cardSelected);
        CManager.removeCard(cardSelected);
        currentUser.removeCard(cardSelected.getCardId());
    }
    private void deleteButtonClicked(){

        String feedbackSelected = feedbackView.getSelectionModel().getSelectedItem();
        ObservableList<String> allFeedback = feedbackView.getItems();
        allFeedback.remove(feedbackSelected);
    }
}