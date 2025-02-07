package application;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ResultDetailsController {

    public static String setTestId; // Holds the testId passed from another controller

    @FXML
    public Label userIdLbl, instructorIdLbl, overallResultLbl, timestampLbl,resultIdLbl,fetchLbl;

    @FXML
    public TextField testIdTxt;

    @FXML
    public Button loadDetailsBtn, exitBtn;

    @FXML
    public VBox container;
    
    public static String testIdVar = null;

    @FXML
    public void loadDetailsBtnClicked(ActionEvent event) {
        String testId = testIdTxt.getText();
        
        if (testId == null || testId.isEmpty()) {
            if (testIdVar != null) {
                testIdTxt.setText(testIdVar);
                testId = testIdVar;
            } else {
                System.out.println("Please enter a valid Test ID.");
                return;
            }
        }
        
        loadDetailsForTestId(testId);
    }


    // Method to load details using testId
    private void loadDetailsForTestId(String testId) {
        System.out.println("Loading details for Test ID: " + testId);

        // Retrieve static label values (user, instructor, overall result, timestamp, result_id)
        String[] staticDetails = DatabaseConnection.getStaticDetailsByTestId(testId);

        if (staticDetails != null && staticDetails.length > 0) {
            // Set the static labels
            userIdLbl.setText("USER ID: " + staticDetails[0]);
            instructorIdLbl.setText("INSTRUCTOR ID: " + staticDetails[1]);
            resultIdLbl.setText("RESULT ID: " + staticDetails[2]);
            overallResultLbl.setText("OVERALL RESULT (%): " + staticDetails[3]);
            timestampLbl.setText("TEST ATTEMPTED TIMESTAMP: " + staticDetails[4]);
            fetchLbl.setText("Fetching of Data Successful!");

            // Clear the VBox before adding new elements
            container.getChildren().clear();
            System.out.println("Cleared VBox. Adding new details...");

            // Retrieve dynamic question_id and result_value pairs
            List<String[]> dynamicDetails = DatabaseConnection.getQuestionResultsByTestId(testId);

            // Check if dynamicDetails is empty
            if (dynamicDetails.isEmpty()) {
                System.out.println("The user has not attempted the test yet !");
                fetchLbl.setText("The user has not attempted the test yet !");
            } else {
                // Loop through each question and result pair
                for (String[] detail : dynamicDetails) {
                    String questionId = detail[0];
                    String resultValue = detail[1];

                    // Create a new label with the question id and result value
                    Label label = new Label("Question ID: " + questionId + " | Result Scored: " + resultValue);
                    label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
                    container.getChildren().add(label); // Add each label to the VBox
                    System.out.println("Added label for Question ID: " + questionId);
                }
            }

        } else {
            System.out.println("No results found for the given Test ID.");
            fetchLbl.setText("No results found for the given Test ID!");
        }
    }

    @FXML
    public void exitBtnClicked(ActionEvent event) {
        try {
            Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
