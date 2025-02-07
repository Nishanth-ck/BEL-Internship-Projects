package application;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SettingTestController  {
	
	@FXML
	public TextField useridTxt;
	
	@FXML
	public Button exitBtn,topicSelectionBtn;
	
	@FXML
	public Label instructoridLbl,instructornameLbl,instructormobileLbl,instructorgmailLbl;
	
	public static String users_id;
	
	public String instructor_id = InstructorLoginController.instructor_id;
	
	@FXML
    public void initialize() 
	{
        // Fetch instructor details using a String array
        String[] instructorDetails = DatabaseConnection.getInstructorDetails(instructor_id);
        
        // If instructor details are fetched, set the labels
        if (instructorDetails != null) {
            instructoridLbl.setText("Instructor ID: " + instructorDetails[0]);                   // Set instructor ID
            instructornameLbl.setText("Instructor Name: " + instructorDetails[1]);        // Set name
            instructormobileLbl.setText("Instructor Mobile: " + instructorDetails[2]);    // Set mobile
            instructorgmailLbl.setText("Instructor Gmail ID: " + instructorDetails[3]);   // Set Gmail
        }
    }
	
	@FXML
	 public void topicSelectionBtnClicked(ActionEvent event)
	 {
		 try {
			 	users_id = useridTxt.getText();
	            Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
	            //thisstage.close();
	            
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/TopicSelection.fxml"));
	            Scene scene = new Scene(fxmlLoader.load());
	            Stage stage = new Stage();
	            stage.setTitle("Topic Selection Page !");
	            stage.setScene(scene);
	            stage.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 }

	@FXML
	public void exitBtnClicked(ActionEvent event)
	{
		try {
            Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisstage.close();
            
           /* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/HomePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Home Page !");
            stage.setScene(scene);
            stage.show(); */
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	

}
