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

public class UsersRegisterController {
	
	@FXML
    private TextField UserName,UserID,MobileNumber,GmailId;

    @FXML
    private PasswordField UserPassword,ConfirmPassword;

    @FXML
    private Button UserRegisterBtn;
    
    @FXML
    private Label lblMessage;
    
    @FXML
    private void UserRegisterBtnClicked(ActionEvent event) {
        String users_name = UserName.getText();
        String users_id = UserID.getText();
        String users_password = UserPassword.getText();
        String confirm_password = ConfirmPassword.getText();
        String mobileno = MobileNumber.getText();
        String gmailid = GmailId.getText();
        
        // Check if passwords match
        if (!confirm_password.equals(users_password)) {
            lblMessage.setText("Confirm Password does not match Instructor Password");
            // Clear password fields or focus on the incorrect field
            UserPassword.clear();
            ConfirmPassword.clear();
            UserPassword.requestFocus(); // Optional: Focus on the password field
            return; // Exit the method early if passwords do not match
        }
        
        // Proceed with registration if passwords match
        if (DatabaseConnection.UserRegistration(users_name, users_id, users_password, mobileno, gmailid)) {
            lblMessage.setText("Registration Successful");
            try {
                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                thisstage.close();
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/UsersLogin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Users Login page !");
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
