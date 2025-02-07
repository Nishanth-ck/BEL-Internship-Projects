package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QuestionsIndexController {

	@FXML
	public Label questionTopicName,questionsAnswered,questionsUnanswered;
	
	@FXML
	public Button goToQuiz;
	
	
	public void initialize() {
	    // Set the topic name
	    questionTopicName.setText(QuizController.questionType);

	    // Create string builders to hold the list of answered and unanswered questions
	    StringBuilder answeredList = new StringBuilder();
	    StringBuilder unansweredList = new StringBuilder();

	    // Loop through questions and build the lists
	    for (int i = 0; i < QuizController.MaxQuestions; i++) {
	        if (QuizController.QuestionsMarked[i] != null) {
	            answeredList.append((i + 1)).append(" "); // Add question number to answered list
	        }
	    }

	    for (int i = 0; i < QuizController.MaxQuestions; i++) {
	        if (QuizController.QuestionsMarked[i] == null) {
	            unansweredList.append((i + 1)).append(" "); // Add question number to unanswered list
	        }
	    }

	    // Set the label text with the concatenated lists
	    questionsAnswered.setText(answeredList.toString());
	    questionsUnanswered.setText(unansweredList.toString());
	}

	
	public void goToQuizClicked(ActionEvent event)
	{
		try 
		{
			
			Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
			thisstage.close();
			
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/Quiz.fxml"));
//			Scene scene = new Scene(fxmlLoader.load());
//			Stage stage = new Stage();
//			stage.setTitle("Quiz !");
//			stage.setScene(scene);
//			stage.initStyle(StageStyle.TRANSPARENT);
//			scene.setFill(Color.TRANSPARENT);
//			stage.show();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
