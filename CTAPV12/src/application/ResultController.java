package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class ResultController {
	
	@FXML
    public ProgressIndicator correct_progress,wrong_progress;

    @FXML
    public Label correcttext,marks,wrongtext,testIdLbl,userIdLbl;
    
    @FXML
    public Button getResult,logoutBtn,viewDetailedResultBtn,quizIndexBtn;
    
    public int result_score;
    public String users_id,topic_name;
    
	@FXML
	public void initialization(ActionEvent event)
	{
		
		correcttext.setText("Correct Answers : " + String.valueOf(QuizController.correct));
		wrongtext.setText("Incorrect Answers : " + String.valueOf(QuizController.wrong));
		
		marks.setText(QuizController.totalResult + " % ");
		
		float correctf = (float) QuizController.correct/QuizController.MaxQuestions;
		correct_progress.setProgress(correctf);
		
		float wrongf = (float) QuizController.wrong/QuizController.MaxQuestions;
		wrong_progress.setProgress(wrongf);
		
		result_score = QuizController.correct;
		users_id = UsersLoginController.users_id;
		topic_name = QuizController.questionType;
		
		testIdLbl.setText(QuizController.test_id);
		userIdLbl.setText(UsersLoginController.users_id);
		
    }
	
	@FXML
	public void quizIndexBtnClicked(ActionEvent event)
	{
		try {
            Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisstage.close();
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/UsersSetTest.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Quiz Index Page !");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	public void logoutBtnClicked(ActionEvent event)
	{
		try {
                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                thisstage.close();
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/HomePage.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Home Page !");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
	
	@FXML
	public void viewDetailedResultBtnClicked(ActionEvent event)
	{
		try {
				ResultDetailsController.testIdVar = QuizController.test_id;
                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                //thisstage.close();
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/ResultDetails.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Detailed Result Page !");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
	

}

