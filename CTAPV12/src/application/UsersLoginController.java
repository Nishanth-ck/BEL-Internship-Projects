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

public class UsersLoginController {

    @FXML
    private TextField UsersID;

    @FXML
    private PasswordField UsersPassword;

    @FXML
    private Button UsersRegisterBtn,UsersLoginBtn;
    
    @FXML
    private Label lblMessage;
    
    public static String users_id;
    public static String users_password;

    // This method is triggered when the "Login" button is pressed
    @FXML
    private void UsersLoginBtnClicked(ActionEvent event) {
    	
        users_id = UsersID.getText();
        users_password = UsersPassword.getText();
        
        if (DatabaseConnection.validateUserLogin(users_id, users_password)) {
            lblMessage.setText("Login successful!");
	        try
	        {
	        	Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
				thisstage.close();
				
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/UsersSetTest.fxml"));
				Scene scene = new Scene(fxmlLoader.load());
				Stage stage = new Stage();
				stage.setTitle("Users Test page !");
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
        else {
            lblMessage.setText("Invalid credentials!");
        }
      

    }
    
    @FXML
    private void UsersRegisterBtnClicked(ActionEvent event) {
        
        try
        {
            
            Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
			thisstage.close();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/UsersRegister.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("Users Registration page !");
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


