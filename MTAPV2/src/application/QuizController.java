package application;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class QuizController {

    @FXML
    public ImageView question, optionA, optionB, optionC, optionD, profile;

    @FXML
    public Button opt1, opt2, opt3, opt4, previousBtn, nextBtn, homeBtn, endTestBtn, questionsIndexBtn, Clear;

    @FXML
    public Label questionNumberlbl, questionsRemaining, questionPrompt, name, testIdLbl, topicNameLbl, userIdLbl,option1Lbl,option2Lbl,option3Lbl,option4Lbl;

    @FXML
    private Label timerLabel;

    public static int correct = 0;
    public static int wrong = 0;

    public static double totalResult= 0.0;

    private Timeline timeline;
    private int hoursLeft, minutesLeft, secondsLeft;

    // counter variables to maintain the track of question numbers for each topic
    public static int MaxQuestions;

    public static String[] QuestionsMarked;

    public static String questionType, test_id, recentResultId, newResultId;

    public static int counter = 0;

    int temp;

    @FXML
    public void initialize() {
    	counter = 0;
    	correct = 0;
    	wrong = 0;
    	totalResult = 0.0;
    	
        testIdLbl.setText("Test ID : " + test_id);
        topicNameLbl.setText("Topic Name : " + questionType);
        userIdLbl.setText("User ID : " + UsersLoginController.users_id);

        MaxQuestions = DatabaseConnection.getNumberOfQuestionsInSubtopic(questionType);
        QuestionsMarked = new String[MaxQuestions];
        for (int i = 0; i < MaxQuestions; i++) {
            QuestionsMarked[i] = null;
        }
        counter = 0;

        // Initialize the timer values from UsersSetTestController
        hoursLeft = UsersSetTestController.hours;
        minutesLeft = UsersSetTestController.minutes;
        secondsLeft = UsersSetTestController.seconds;

        // Create and configure the Timeline for countdown timer
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play(); // Start the timer

        loadTopicQuestions();
    }

    private void ColorOption(int counter) {
        if (QuestionsMarked[counter] == null) {
            opt1.setStyle("-fx-background-color:#23fbff;");
            opt2.setStyle("-fx-background-color:#23fbff;");
            opt3.setStyle("-fx-background-color:#23fbff;");
            opt4.setStyle("-fx-background-color:#23fbff;");
        } else {
            switch (QuestionsMarked[counter]) {
                case "A":
                    opt1.setStyle("-fx-background-color:#0000ff;");
                    opt2.setStyle("-fx-background-color:#23fbff;");
                    opt3.setStyle("-fx-background-color:#23fbff;");
                    opt4.setStyle("-fx-background-color:#23fbff;");
                    break;
                case "B":
                    opt2.setStyle("-fx-background-color:#0000ff;");
                    opt1.setStyle("-fx-background-color:#23fbff;");
                    opt3.setStyle("-fx-background-color:#23fbff;");
                    opt4.setStyle("-fx-background-color:#23fbff;");
                    break;
                case "C":
                    opt3.setStyle("-fx-background-color:#0000ff;");
                    opt1.setStyle("-fx-background-color:#23fbff;");
                    opt2.setStyle("-fx-background-color:#23fbff;");
                    opt4.setStyle("-fx-background-color:#23fbff;");
                    break;
                case "D":
                    opt4.setStyle("-fx-background-color:#0000ff;");
                    opt1.setStyle("-fx-background-color:#23fbff;");
                    opt2.setStyle("-fx-background-color:#23fbff;");
                    opt3.setStyle("-fx-background-color:#23fbff;");
                    break;
            }
        }
    }

    private void updateTimer() {
        if (minutesLeft == 0 && secondsLeft == 0) {
            timeline.stop(); // Stop the timer when it reaches zero
            timerLabel.setText("00:00:00");
            
            Platform.runLater(() -> {
                try {
                    CalculateResultAndInsertDB();
                    Stage currentStage = (Stage) timerLabel.getScene().getWindow();
                    currentStage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/Result.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setTitle("Result!");
                    stage.setScene(scene);
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            if (secondsLeft > 0) {
                secondsLeft--;
            } else {
                if (minutesLeft > 0) {
                    minutesLeft--;
                    secondsLeft = 59;
                } else {
                    hoursLeft--;
                    minutesLeft = 59;
                    secondsLeft = 59;
                }
            }
            timerLabel.setText(String.format("%02d:%02d:%02d", hoursLeft, minutesLeft, secondsLeft));
        }
    }

    public void loadTopicQuestions() {
        String[] QUESTION_IDS = DatabaseConnection.getQuestionIdsForSubtopic(questionType);
        if (counter >= 0 && counter < QUESTION_IDS.length) {
            String questionId = QUESTION_IDS[counter];

            String questionDesc = DatabaseConnection.getQuestionDescFromDatabase(questionId);
            Image questionImage = DatabaseConnection.getQuestionImageFromDatabase(questionId);
            //Image imageOption1 = DatabaseConnection.getQuestionOption1ImageFromDatabase(questionId);
            //Image imageOption2 = DatabaseConnection.getQuestionOption2ImageFromDatabase(questionId);
            //Image imageOption3 = DatabaseConnection.getQuestionOption3ImageFromDatabase(questionId);
            //Image imageOption4 = DatabaseConnection.getQuestionOption4ImageFromDatabase(questionId);

            questionNumberlbl.setText("QUESTION " + (counter + 1));
            questionPrompt.setText(questionDesc);
            question.setImage(questionImage);
            option1Lbl.setText("A) " +DatabaseConnection.getOption1DescForQuestion(questionId));
            option2Lbl.setText("B) "+DatabaseConnection.getOption2DescForQuestion(questionId));
            option3Lbl.setText("C) "+DatabaseConnection.getOption3DescForQuestion(questionId));
            option4Lbl.setText("D) "+DatabaseConnection.getOption4DescForQuestion(questionId));
            
            opt1.setText("A");
			opt2.setText("B");
			opt3.setText("C");
			opt4.setText("D");

            questionsRemaining.setText("QUESTIONS REMAINING : " + (MaxQuestions - (counter + 1)));

            ColorOption(counter);
        } else {
            System.out.println("Counter value is out of range: " + counter);
        }
    }

    @FXML
    public void opt1clicked(ActionEvent event) {
        QuestionsMarked[counter] = "A";
        opt1.setStyle("-fx-background-color:#0000ff;");
        opt2.setStyle("-fx-background-color:#23fbff;");
        opt3.setStyle("-fx-background-color:#23fbff;");
        opt4.setStyle("-fx-background-color:#23fbff;");
    }

    @FXML
    public void opt2clicked(ActionEvent event) {
        QuestionsMarked[counter] = "B";
        opt2.setStyle("-fx-background-color:#0000ff;");
        opt1.setStyle("-fx-background-color:#23fbff;");
        opt3.setStyle("-fx-background-color:#23fbff;");
        opt4.setStyle("-fx-background-color:#23fbff;");
    }

    @FXML
    public void opt3clicked(ActionEvent event) {
        QuestionsMarked[counter] = "C";
        opt3.setStyle("-fx-background-color:#0000ff;");
        opt1.setStyle("-fx-background-color:#23fbff;");
        opt2.setStyle("-fx-background-color:#23fbff;");
        opt4.setStyle("-fx-background-color:#23fbff;");
    }

    @FXML
    public void opt4clicked(ActionEvent event) {
        QuestionsMarked[counter] = "D";
        opt4.setStyle("-fx-background-color:#0000ff;");
        opt1.setStyle("-fx-background-color:#23fbff;");
        opt2.setStyle("-fx-background-color:#23fbff;");
        opt3.setStyle("-fx-background-color:#23fbff;");
    }

    @FXML
    public void goToPrevious(ActionEvent event) {
        if (counter > 0) {
            counter--;
        }
        loadTopicQuestions();
    }

    @FXML
    public void goToNext(ActionEvent event) {
        if (counter < MaxQuestions - 1) {
            counter++;
        }
        loadTopicQuestions();
    }

    @FXML
    public void goToHome(ActionEvent event) {
        if (counter < MaxQuestions) {
            questionsRemaining.setText("Cannot go to home before completing the test");
        } else {
            try {
                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                thisstage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/HomePage.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Home Page!");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void Clear(ActionEvent evnt) {
        QuestionsMarked[counter] = null;
        opt1.setStyle("-fx-background-color:#23fbff;");
        opt2.setStyle("-fx-background-color:#23fbff;");
        opt3.setStyle("-fx-background-color:#23fbff;");
        opt4.setStyle("-fx-background-color:#23fbff;");
    }

    @FXML
    public void goToEnd(ActionEvent event) {
        try {
            CalculateResultAndInsertDB();
            Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/Result.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Test Results");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @FXML
	public void goToIndex(ActionEvent event)
	{
		try 
		{
				
				Stage thisstage = (Stage)((Button)event.getSource()).getScene().getWindow();
				//thisstage.close();
				
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlfiles/QuestionsIndex.fxml"));
				Scene scene = new Scene(fxmlLoader.load());
				Stage stage = new Stage();
				stage.setTitle("Questions Index Page !");
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
    
	public static void CalculateResultAndInsertDB() {
	    // Retrieve the most recent result_id and increment it
	    String recentResultId = DatabaseConnection.getRecentResultId();
	    String newResultId = DatabaseConnection.incrementResultId(recentResultId);

	    int resultValue;
	    
	    String[] questionIds = null;
	    String[] correctAnswers = null;
	    
	    // Determine the question type and fetch relevant question IDs
	    String questionType = QuizController.questionType;
	            // Fetch the question IDs based on the question type
	            questionIds = DatabaseConnection.getQuestionIdsForSubtopic(questionType);
	            correctAnswers = new String[questionIds.length];
	            // Fetch the correct answers for each question ID
	            for (int i = 0; i < questionIds.length; i++) {
	                correctAnswers[i] = DatabaseConnection.getCorrectAnswerForQuestion(questionIds[i]);
	            }
	    // Process each question, compare answers, and update results
	    for (int i = 0; i < questionIds.length; i++) {
	        // Check if the marked answer matches the correct answer
	        if (QuizController.QuestionsMarked[i] != null && QuizController.QuestionsMarked[i].equals(correctAnswers[i])) {
	            correct++;
	            resultValue = 1;  // Correct answer
	        } else {
	            wrong++;
	            resultValue = 0;  // Wrong answer
	        }
	        // Update the result table with question-specific results
	        DatabaseConnection.updateResultTable(newResultId, questionIds[i], resultValue);
	        System.out.println("Updated result table for " + questionIds[i]);
	    }

	    // Calculate the total result percentage
	    totalResult = (correct / (double) questionIds.length) * 100;

	    // Call function to update the test result in the database
	    DatabaseConnection.updateTestResult(newResultId, totalResult, QuizController.test_id);
	 }

}

