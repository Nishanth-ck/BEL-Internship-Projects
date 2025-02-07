package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class InstructorLoginController {

    @FXML
    private TextField InstructorID;
    @FXML
    private ImageView instructor_profile;

    @FXML
    private PasswordField InstructorPassword;

    @FXML
    private Button InstructorRegisterBtn,InstructorLoginBtn;
    
    @FXML
    private Label lblMessage;
    
    public static String instructor_id;
    public static String instructor_password;

    // This method is triggered when the "Login" button is pressed
    @FXML
    private void InstructorLoginBtnClicked(ActionEvent event) {
    	
        instructor_id = InstructorID.getText();
        instructor_password = InstructorPassword.getText();
       
        if (DatabaseConnection.validateInstructorLogin(instructor_id, instructor_password)) {
            lblMessage.setText("Login successful!");
	        try
	        {
	        	Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
				thisstage.close();
				
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/InstructorSetTest.fxml"));
				Scene scene = new Scene(fxmlLoader.load());
				Stage stage = new Stage();
				stage.setTitle("Instructors Setting Test page !");
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
        else 
        {
            lblMessage.setText("Invalid credentials!");
        }
    
    }
    
    @FXML
    private void InstructorRegisterBtnClicked(ActionEvent event) {
        
        try
        {
            
            Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
			thisstage.close();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/InstructorRegister.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("Instructors Registration page !");
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

