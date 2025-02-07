package application;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TopicSelectionController implements Initializable {

    @FXML
    private VBox container;

    @FXML
    private Button exitBtn,confirmBtn;

    @FXML
    private TreeView<String> treeView;
    
    @FXML
	private ChoiceBox<String> hoursChoiceBox,minutesChoiceBox,secondsChoiceBox;
    
    @FXML
	public Label hourLbl,minuteLbl,secondLbl,hour2Lbl,minute2Lbl,second2Lbl;

    public static int MaxTopics = 4;
    public static int SubTopics = 12;

    public static String[] topicsSelected = new String[MaxTopics + SubTopics];

    public static int labelCounter = 0;
    
    private String[] hours_values = {"0","1","2","3","4","5","6","7","8","9","10","11","12"};
	private String[] minutes_values = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
		    "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
		    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", 
		    "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", 
		    "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", 
		    "51", "52", "53", "54", "55", "56", "57", "58", "59"};
	private String[] seconds_values = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
		    "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
		    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", 
		    "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", 
		    "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", 
		    "51", "52", "53", "54", "55", "56", "57", "58", "59"};
	
	public static int hours,minutes,seconds;
	
	public String users_id;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		    TreeItem<String> rootItem = new TreeItem<>("QUESTIONS");
		    
		    String[] branchItemsArray = DatabaseConnection.getTopicNamesFromInfoTable();
		    
		    for(int i=0;i<branchItemsArray.length;i++)
		    {
		    	TreeItem<String> branchItem = new TreeItem<>(branchItemsArray[i]);
		    	
		    	String[] leafItemsArray = DatabaseConnection.getSubTopicNamesFromInfoTable(branchItemsArray[i]);
		    	
		    	for(int j=0;j<leafItemsArray.length;j++)
		    	{
		    		 TreeItem<String> leafItem = new TreeItem<>(leafItemsArray[j]);
		    		 
		    		 branchItem.getChildren().add(leafItem);
		    	}
		    	
		    	rootItem.getChildren().add(branchItem);
		    }

	        treeView.setRoot(rootItem);

	        // Add a ChangeListener to handle selection
	        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue != null) {
	                // Only add if labelCounter is within bounds
	                if (labelCounter < topicsSelected.length) {
	                    topicsSelected[labelCounter] = newValue.getValue();
	                    System.out.println(topicsSelected[labelCounter]);
	                    addElement();
	                }
	            }
	        });
		
	        hoursChoiceBox.getItems().addAll(hours_values);
			hoursChoiceBox.setOnAction(this :: getHours);
			
			minutesChoiceBox.getItems().addAll(minutes_values);
			minutesChoiceBox.setOnAction(this :: getMinutes);
			
			secondsChoiceBox.getItems().addAll(seconds_values);
			secondsChoiceBox.setOnAction(this :: getSeconds);
	}
	
	public void getHours(ActionEvent event)
	{
		String hoursVal = hoursChoiceBox.getValue();
		hourLbl.setText(hoursVal);
		hour2Lbl.setText(" hours");
	}
	
	public void getMinutes(ActionEvent event)
	{
		String minutesVal = minutesChoiceBox.getValue();
		minuteLbl.setText(minutesVal);
		minute2Lbl.setText(" minutes");
	}
	
	public void getSeconds(ActionEvent event)
	{
		String secondsVal = secondsChoiceBox.getValue();
		secondLbl.setText(secondsVal);
		second2Lbl.setText(" seconds");
	}
	
	@FXML
	public void confirmBtnClicked(ActionEvent event) {
	    users_id = SettingTestController.users_id;
	    String time = hourLbl.getText() + ":" + minuteLbl.getText() + ":" + secondLbl.getText();
	    Time test_duration = Time.valueOf(time);
	    String instructor_id = InstructorLoginController.instructor_id;
	    Boolean attempt_status = false;
	    
	    if (DatabaseConnection.InstructorSetTest(instructor_id, users_id,topicsSelected, test_duration, attempt_status)) {
	        try {
	            Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
	            thisstage.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Cannot insert the value");
	    }
	}

    @FXML
    public void addElement() {
        if (labelCounter >= topicsSelected.length) {
            System.out.println("Maximum number of topics reached");
            return;
        }

        // Create a new HBox to hold the label and the remove button
        HBox hbox = new HBox(15); // 10 is the spacing between elements

        // Create a new label
        Label newLabel = new Label(topicsSelected[labelCounter]);
        newLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: blue;");

        // Create a new remove button
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        removeButton.setOnAction(event -> {
            // Remove the HBox (which contains the label and remove button) from the VBox
            container.getChildren().remove(hbox);
            // Update topicsSelected array and labelCounter
            removeElementFromArray(topicsSelected[labelCounter - 1]);
        });

        // Add the label and remove button to the HBox
        hbox.getChildren().addAll(newLabel, removeButton);

        // Add the HBox to the VBox
        container.getChildren().add(hbox);

        // Increment the labelCounter
        labelCounter++;
    }

    @FXML
    public void removeElement() {
        if (!container.getChildren().isEmpty()) {
            // Remove the last HBox (which contains a label and its remove button)
            HBox lastHBox = (HBox) container.getChildren().remove(container.getChildren().size() - 1);
            // Update topicsSelected array and labelCounter
            removeElementFromArray(((Label) lastHBox.getChildren().get(0)).getText());
        }
    }

    private void removeElementFromArray(String topic) {
        for (int i = 0; i < topicsSelected.length; i++) {
            if (topicsSelected[i] != null && topicsSelected[i].equals(topic)) {
                // Shift the array elements to the left
                for (int j = i; j < topicsSelected.length - 1; j++) {
                    topicsSelected[j] = topicsSelected[j + 1];
                }
                topicsSelected[topicsSelected.length - 1] = null;
                // Decrement the labelCounter
                labelCounter--;
                break;
            }
        }
    }

    @FXML
    public void exitBtnClicked(ActionEvent event) {
       try {
            Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisstage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
