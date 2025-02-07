package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomePageController {
	
	@FXML
	private Button instructorbtn,usersbtn;
	
	@FXML
	private void instructorbtnClicked(ActionEvent event)
	{
		try {
			
			Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
			thisstage.close();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/InstructorLogin.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("Instructor Login Page !");
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
	
    @FXML
	private void usersbtnClicked(ActionEvent event)
	{
		try {
			
			Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
			thisstage.close();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/UsersLogin.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("Users Login page !");
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
