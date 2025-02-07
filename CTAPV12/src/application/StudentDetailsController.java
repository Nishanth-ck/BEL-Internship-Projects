package application;

import java.io.IOException;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentDetailsController {
	
	@FXML
	public Label userIdLbl,userNameLbl,mobileLbl,gmailLbl,testAllotedLbl,testAttemptedLbl,testNotAttemptedLbl,statusLbl;
	
	@FXML
	public Button getDetails,exitBtn;
	
	@FXML
	public TextField userIdText;
	
	 @FXML
	private VBox container1,container2;
	
	 @FXML 
	 public void getDetailsClicked(ActionEvent event) {
	     String instructor_id = InstructorLoginController.instructor_id;
	     String users_id = userIdText.getText();
	     
	     // Fetch user details
	     String[] userDetails = DatabaseConnection.getUserDetails(users_id);
	     
	     if(userDetails == null || userDetails[1] == null || userDetails[1].trim().isEmpty())
	     {
	    	 statusLbl.setText("The user has not registered yet ");
	     }
	     else {
	    	 
	    	 statusLbl.setText("The user has registered ! ");
	         userNameLbl.setText(userDetails[1]);  
	         mobileLbl.setText(userDetails[2]);    
	         gmailLbl.setText(userDetails[3]);   
	     }
	     

	     String attemptedResult = DatabaseConnection.getAttemptedTests(instructor_id, users_id);
	     String notAttemptedResult = DatabaseConnection.getNotAttemptedTests(instructor_id, users_id);

	     // Filter empty strings from attempted and not attempted tests
	     String[] attemptedTests = attemptedResult.split(";");
	     String[] notAttemptedTests = notAttemptedResult.split(";");

	     // Filter out empty strings to get actual count
	     int actualAttemptedTests = (int) Arrays.stream(attemptedTests)
	                                            .filter(test -> !test.trim().isEmpty())
	                                            .count();
	     int actualNotAttemptedTests = (int) Arrays.stream(notAttemptedTests)
	                                               .filter(test -> !test.trim().isEmpty())
	                                               .count();
	     
	     // Clear containers
	     container1.getChildren().clear();
	     container2.getChildren().clear();

	     // Process actual attempted tests
	     int testCounter = 1;
	     for (String test : attemptedTests) {
	         if (!test.trim().isEmpty()) {
	             String[] testDetails = test.split(",");
	             String testId = testDetails[0];
	             String topicName = testDetails[1];
	             String totalResult = testDetails[2];
	             String timestamp = testDetails[3];

	             Label testDetailsLabel = new Label("Test " + testCounter + ": " + topicName + " | Result: " + totalResult + " | Attempted on: " + timestamp);
	             Button viewDetailsButton = new Button("View Details");
	             viewDetailsButton.setOnAction(e -> viewTestDetails(testId));

	             VBox testBox = new VBox(testDetailsLabel, viewDetailsButton);
	             container1.getChildren().add(testBox);

	             testCounter++;
	         }
	     }

	     // Process actual not attempted tests
	     int notAttemptedCounter = 1;
	     for (String test : notAttemptedTests) {
	         if (!test.trim().isEmpty()) {
	             String[] testDetails = test.split(",");
	             String testId = testDetails[0];
	             String topicName = testDetails[1];

	             Label testDetailsLabel = new Label("Test " + notAttemptedCounter + ": " + topicName + " | Not Attempted");
	             Button viewDetailsButton = new Button("View Details");
	             viewDetailsButton.setOnAction(e -> viewTestDetails(testId));

	             VBox testBox = new VBox(testDetailsLabel, viewDetailsButton);
	             container2.getChildren().add(testBox);

	             notAttemptedCounter++;
	         }
	     }

	     // Set label values
	     int totalTestsNo = actualAttemptedTests + actualNotAttemptedTests;
	     testAllotedLbl.setText(Integer.toString(totalTestsNo));
	     testAttemptedLbl.setText(Integer.toString(actualAttemptedTests));
	     testNotAttemptedLbl.setText(Integer.toString(actualNotAttemptedTests));
	 }
  

	
	 public void viewTestDetails(String testId) {
		    try {
		    	ResultDetailsController.testIdVar = testId;
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/ResultDetails.fxml"));
		        Parent root = loader.load();

		        Stage stage = new Stage();
		        stage.setTitle("Result Details Page !");
		        stage.setScene(new Scene(root));
		        stage.show();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}


	
	@FXML
	public void exitBtnClicked(ActionEvent event)
	{
		try {
            Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisstage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
