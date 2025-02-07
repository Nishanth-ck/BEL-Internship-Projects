package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InstructorRegisterController {
	
	@FXML
    private TextField InstructorName,InstructorID,MobileNumber,GmailId;

    @FXML
    private PasswordField InstructorPassword,ConfirmPassword;

    @FXML
    private Button InstructorRegisterBtn;
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private void InstructorRegisterBtnClicked(ActionEvent event) {
        String instructor_name = InstructorName.getText();
        String instructor_id = InstructorID.getText();
        String instructor_password = InstructorPassword.getText();
        String confirm_password = ConfirmPassword.getText();
        String mobileno = MobileNumber.getText();
        String gmailid = GmailId.getText();
        
        // Check if passwords match
        if (!confirm_password.equals(instructor_password)) {
            lblMessage.setText("Confirm Password does not match Instructor Password");
            // Clear password fields or focus on the incorrect field
            InstructorPassword.clear();
            ConfirmPassword.clear();
            InstructorPassword.requestFocus(); // Optional: Focus on the password field
            return; // Exit the method early if passwords do not match
        }
        
        // Proceed with registration if passwords match
        if (DatabaseConnection.InstructorRegistration(instructor_name, instructor_id, instructor_password, mobileno, gmailid)) {
            lblMessage.setText("Registration Successful");
            try {
                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                thisstage.close();
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/InstructorLogin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Instructor Login page !");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblMessage.setText("Registration failed, try again.");
        }
    }

    
}

