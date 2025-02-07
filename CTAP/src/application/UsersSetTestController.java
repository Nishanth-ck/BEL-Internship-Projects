package application;

import java.time.LocalTime;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UsersSetTestController {

	@FXML
	public Label userIdLbl;
	
	@FXML
	public Button endBtn,loadDetailsBtn;
	
	@FXML
	public VBox container;
	
	private DatabaseConnection dbConn = new DatabaseConnection();
	public static int hours,minutes,seconds;
	public static String test_id;

	@FXML
	public void loadDetailsBtnClicked(ActionEvent event) {
	    userIdLbl.setText(UsersLoginController.users_id);
	    
	    String[] detailsArray = DatabaseConnection.getTestDetailsForUserTest(UsersLoginController.users_id);

	    if (detailsArray != null && detailsArray.length > 0) {
	        // Clear the container VBox
	        container.getChildren().clear();

	        for (String details : detailsArray) {
	            String[] detailArray = details.split(",");

	            if (detailArray.length == 6 && detailArray[0] != null){

	                // Add a label with test details
	                Label testLabel = new Label("Test ID: " + detailArray[0] + ", Topic: " + detailArray[1] + ", Subtopic: " + detailArray[2] + ", Duration: " + detailArray[3]);
	                container.getChildren().add(testLabel);

	                // Add the 'Start Quiz' and/or 'View Results' buttons based on attempt_status
	                Button startQuizButton = new Button("Start Quiz");
	                Button viewResultsButton = new Button("View Results");
	                
	                startQuizButton.setVisible(false);
                    viewResultsButton.setVisible(false);
	                
	                if ("t".equalsIgnoreCase(detailArray[5])) {
	                    // If attempt_status is true, show the 'View Results' button only
	                     // Hide start quiz button if attempt_status is true
	                    viewResultsButton.setVisible(true); 
	                    test_id = detailArray[0];
	                    // Show results button
	                    viewResultsButton.setOnAction(e -> showResultDetails(e));
	                   
	                } 
	                else {
	                    // If attempt_status is false, show one button
	                    startQuizButton.setVisible(true); // Show start quiz button if attempt_status is false
	                    startQuizButton.setOnAction(e -> {
	                        QuizController.questionType = detailArray[2]; 
	                        QuizController.test_id = detailArray[0];
	                        LocalTime testTime = LocalTime.parse(detailArray[3]);
	    	                hours = testTime.getHour();
	    	                minutes = testTime.getMinute();
	    	                seconds = testTime.getSecond();// Set the subtopic name
	                        startTest(e);
	                    });
	                    viewResultsButton.setOnAction(e -> showResultDetails(e));
	                }
	                
	                // Add buttons to VBox
	                container.getChildren().add(startQuizButton);
	                container.getChildren().add(viewResultsButton);
	                
	            } else {
	                System.out.println("No test details found or invalid data for user: " + UsersLoginController.users_id);
	            }
	        }
	    } else {
	        System.out.println("No test details found for user: " + UsersLoginController.users_id);
	    }
	}


	// Method to show result details
	private void showResultDetails(ActionEvent event) {
		try {
			ResultDetailsController.testIdVar = test_id;
			
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/ResultDetails.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Result Details Page !");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	// Method to load the Quiz.fxml file
	public void startTest(ActionEvent event) {
	    try {
	    	Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
			thisstage.close();
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/Quiz.fxml"));
	        Scene scene = new Scene(fxmlLoader.load());
	        Stage stage = new Stage();
	        stage.setTitle("Quiz Page");
	        stage.setScene(scene);
	        stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	public void endBtnClicked(ActionEvent event)
	{
		try {
			
			Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
			thisstage.close();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/HomePage.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("Home Page !");
			stage.setScene(scene);
			//stage.initStyle(StageStyle.TRANSPARENT);
			//scene.setFill(Color.TRANSPARENT);
			stage.show();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
