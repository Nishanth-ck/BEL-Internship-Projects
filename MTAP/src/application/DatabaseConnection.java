package application;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;

public class DatabaseConnection {
	 private static final String URL = "jdbc:postgresql://localhost:5432/your_database_name"; // Your database URL
	    private static final String USER = "postgres"; //  database username
	    private static final String PASSWORD = "database_password"; // Your database password

    // Method to establish a connection to the database
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL database!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Method to validate login credentials
    public static boolean validateInstructorLogin(String username, String password) {
        String sql = "SELECT * FROM public.instructors WHERE instructor_id = ? AND instructor_password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a matching record is found
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public static boolean validateUserLogin(String username, String password) {
        String sql = "SELECT * FROM public.users WHERE users_id = ? AND users_password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a matching record is found
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public static boolean InstructorRegistration(String instructor_name,String instructor_id,String instructor_password,String mobileno,String gmailid) {
    	 String sql = "INSERT INTO public.instructors (instructor_name, instructor_id, instructor_password, mobileno, gmailid) VALUES (?, ?, ?, ?, ?)";

    	    try (Connection conn = connect();
    	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

    	        // Set the values for the INSERT query
    	        pstmt.setString(1, instructor_name);
    	        pstmt.setString(2, instructor_id);
    	        pstmt.setString(3, instructor_password);
    	        pstmt.setString(4, mobileno);  // Since mobileno is a string (VARCHAR or TEXT in the database)
    	        pstmt.setString(5, gmailid);

    	        // Execute the query
    	        int affectedRows = pstmt.executeUpdate();

    	        // Check if the insertion was successful
    	        if (affectedRows > 0) {
    	            System.out.println("Instructor registration successful.");
    	            return true;
    	        } else {
    	            System.out.println("Instructor registration failed.");
    	        }
    	    } catch (SQLException e) {
    	        System.out.println(e.getMessage());
    	    }
       return false;
    }
    
    public static boolean UserRegistration(String users_name,String users_id,String users_password,String mobileno,String gmailid) {
   	 String sql = "INSERT INTO public.users (users_name, users_id, users_password, mobileno, gmailid) VALUES (?, ?, ?, ?, ?)";

   	    try (Connection conn = connect();
   	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

   	        // Set the values for the INSERT query
   	        pstmt.setString(1, users_name);
   	        pstmt.setString(2, users_id);
   	        pstmt.setString(3, users_password);
   	        pstmt.setString(4, mobileno);  // Since mobileno is a string (VARCHAR or TEXT in the database)
   	        pstmt.setString(5, gmailid);

   	        // Execute the query
   	        int affectedRows = pstmt.executeUpdate();

   	        // Check if the insertion was successful
   	        if (affectedRows > 0) {
   	            System.out.println("User registration successful.");
   	            return true;
   	        } else {
   	            System.out.println("User registration failed.");
   	        }
   	    } catch (SQLException e) {
   	        System.out.println(e.getMessage());
   	    }
      return false;
   }
    
    public static boolean InstructorSetTest(String instructor_id, String users_id, String topic_name, Time test_duration, boolean attempt_status) {
        String sql = "INSERT INTO public.settest (instructor_id, users_id, topic_name, test_duration, attempt_status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the values for the INSERT query
            pstmt.setString(1, instructor_id);
            pstmt.setString(2, users_id);
            pstmt.setString(3, topic_name);
            pstmt.setTime(4, test_duration); // Assuming test_duration is a string in "HH:MM:SS" format
            pstmt.setBoolean(5, attempt_status);

            // Execute the query
            int affectedRows = pstmt.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows > 0) {
                System.out.println("Data inserted successfully.");
                return true;
            } else {
                System.out.println("Data insertion failed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

        public static String[] getTestDetailsByUserId(String users_id) {
        	String query = "SELECT instructor_id, topic_name, test_duration FROM public.settest WHERE users_id = ?";
            String[] testDetails = new String[3]; // Array to hold instructor_id, topic_name, and test_duration

            try (Connection conn = connect(); // Assuming connect() establishes the connection
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, users_id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    testDetails[0] = rs.getString("instructor_id");
                    testDetails[1] = rs.getString("topic_name");
                    testDetails[2] = rs.getString("test_duration");
                }
                
                System.out.println("Fetched Data: " + testDetails[0] + ", " + testDetails[1] + ", " + testDetails[2]);

            } 
            catch (SQLException e) 
            {
                System.out.println(e.getMessage());
            }

            return testDetails;
        }
    
        public static boolean ResultUpdate(String users_id,String topic_name,int result_score) {
        	String sql = "UPDATE public.settest SET result_score = ? WHERE users_id = ? AND topic_name = ?";

            try (Connection conn = connect();  // Assuming `DatabaseConnection.getConnection()` provides the database connection
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Set the values for the UPDATE query
                pstmt.setInt(1, result_score);
                pstmt.setString(2, users_id);
                pstmt.setString(3, topic_name);

                // Execute the query
                int affectedRows = pstmt.executeUpdate();

                // Check if the update was successful
                if (affectedRows > 0) {
                    System.out.println("Result update successful.");
                    return true;
                } else {
                    System.out.println("Result update failed.");
                }

            } catch (SQLException e) {
                System.out.println("Error during result update: " + e.getMessage());
            }
            return false;
       } 
        
        public static String[] getUserAndInstructorDetailsByUserId(String users_id) {
            // Correct SQL query with space added between column list and FROM keyword
            String query = "SELECT users_name, mobileno, gmailid " +
                           "FROM public.users " +
                           "WHERE users_id = ?";
            
            String[] details = new String[3]; // Array to hold users_id, users_name, mobileno, gmailid

            try (Connection conn = connect(); // Assuming connect() establishes the connection
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                // Set the user ID in the query
                pstmt.setString(1, users_id);
                
                // Execute the query
                ResultSet rs = pstmt.executeQuery();

                // If user details are found, fill the array
                if (rs.next()) {
                    details[0] = rs.getString("users_name");
                    details[1] = rs.getString("mobileno");
                    details[2] = rs.getString("gmailid");
                }
                
                System.out.println("Fetched Data: " + details[0] + ", " + details[1] + ", " + details[2]);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return details; // Return the array containing user details
        }

        public static String[] getInstructorDetails(String instructor_id) {
            String[] instructorDetails = new String[4];  // Assuming you have name, mobile, and gmail fields
            
            String query = "SELECT instructor_id, instructor_name, mobileno, gmailid FROM public.instructors WHERE instructor_id = ?";
            
            try (Connection conn = connect(); 
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                 
                pstmt.setString(1, instructor_id);
                
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                	instructorDetails[0] = rs.getString("instructor_id");
                    instructorDetails[1] = rs.getString("instructor_name");    // Name
                    instructorDetails[2] = rs.getString("mobileno");  // Mobile
                    instructorDetails[3] = rs.getString("gmailid");   // Gmail
                }
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
            
            return instructorDetails;
        }
        
        public static String[] getUserDetails(String users_id) {
            String[] userDetails = new String[4];  // Assuming you have name, mobile, and gmail fields
            
            String query = "SELECT users_id, users_name, mobileno, gmailid FROM public.users WHERE users_id = ?";
            
            try (Connection conn = connect(); 
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                 
                pstmt.setString(1, users_id);
                
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                	userDetails[0] = rs.getString("users_id");
                    userDetails[1] = rs.getString("users_name");    // Name
                    userDetails[2] = rs.getString("mobileno");  // Mobile
                    userDetails[3] = rs.getString("gmailid");   // Gmail
                }
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
            
            for(int i=0;i<4;i++)
            System.out.println(userDetails[i]);
            
            return userDetails;
        }
        
        public static String getAttemptedTests(String instructorId, String userId) {
            String query = "SELECT test_id, topic_name, total_result, test_attempt_timestamp FROM public.test WHERE instructor_id = ? AND users_id = ? AND attempt_status = TRUE";
            StringBuilder result = new StringBuilder();

            try {
                Connection conn = connect(); 
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, instructorId);
                pstmt.setString(2, userId);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String testId = rs.getString("test_id");
                    String topicName = rs.getString("topic_name");
                    String totalResult = rs.getString("total_result");
                    String timestamp = rs.getString("test_attempt_timestamp");

                    // Append each test's details as a comma-separated string with timestamp
                    result.append(testId).append(",").append(topicName).append(",").append(totalResult).append(",").append(timestamp).append(";");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return result.toString();  // Return the concatenated result as a string
        }
        
        public static String getNotAttemptedTests(String instructorId, String userId) {
            String query = "SELECT test_id, topic_name FROM public.test WHERE instructor_id = ? AND users_id = ? AND attempt_status = FALSE";
            StringBuilder result = new StringBuilder();

            try {
                Connection conn = connect(); 
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, instructorId);
                pstmt.setString(2, userId);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String testId = rs.getString("test_id");
                    String topicName = rs.getString("topic_name");

                    // Append each test's details as a comma-separated string
                    result.append(testId).append(",").append(topicName).append(";");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return result.toString();  // Return the concatenated result as a string
        }
        
        public static boolean InstructorSetTest(String instructor_id, String users_id, String[] topicsSelected, Time test_duration, boolean attempt_status) {
            String sql = "INSERT INTO public.test (test_id, instructor_id, users_id, topic_name, subtopic_name, test_duration, attempt_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = connect(); 
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Retrieve the most recent test_id from the database
                String latestTestId = getLatestTestId(conn);
                int newTestNumber = 1; // Default test number if no entries are found

                if (latestTestId != null && latestTestId.startsWith("testNo")) {
                    newTestNumber = Integer.parseInt(latestTestId.substring(6)) + 1; // Increment the last test number by 1
                }

                // Insert a new row for each subtopic in topicsSelected[]
                for (String subtopic : topicsSelected) {
                    if (subtopic == null) continue; // Skip null entries

                    // Generate new test_id by incrementing the number
                    String test_id = String.format("testNo%04d", newTestNumber++);
                    String topic_name = mapSubtopicToTopic(subtopic);

                    // Set the values for the INSERT query
                    pstmt.setString(1, test_id);
                    pstmt.setString(2, instructor_id);
                    pstmt.setString(3, users_id);
                    pstmt.setString(4, topic_name);
                    pstmt.setString(5, subtopic);
                    pstmt.setTime(6, test_duration); // Assuming test_duration is in Time format
                    pstmt.setBoolean(7, attempt_status);

                    // Execute the insert query for each topic
                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows == 0) {
                        System.out.println("Data insertion failed for topic: " + subtopic);
                        return false; // Stop execution if any insert fails
                    }
                }

                System.out.println("Data inserted successfully for all selected topics.");
                return true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return false;
        }

        // Method to retrieve the most recent test_id from the database
        private static String getLatestTestId(Connection conn) {
            String query = "SELECT test_id FROM public.test ORDER BY test_id DESC LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("test_id");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }


        // Method to map subtopics to main topics
        private static String mapSubtopicToTopic(String subtopic) {
        	// Define the SQL query to fetch the question description by question_id
            String query = "SELECT topic_name FROM public.mtap_information WHERE subtopic_name = ?"; 
            // Variable to hold the question description
            String topicName = null;

            try (Connection conn = connect(); // Assuming connect() establishes the connection
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, subtopic);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    topicName = rs.getString("topic_name");
                }
                
                System.out.println("Fetched Topic Name: " + topicName);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return topicName;
        }
        
        public static String[] getStaticDetailsByTestId(String testId) {
            String sql = "SELECT test.users_id, test.instructor_id, test.result_id, " +
                         "test.total_result AS overall_result, test.test_attempt_timestamp " +
                         "FROM public.test WHERE test.test_id = ?";

            String[] staticDetails = new String[5];

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, testId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    staticDetails[0] = rs.getString("users_id");
                    staticDetails[1] = rs.getString("instructor_id");
                    staticDetails[2] = rs.getString("result_id");
                    staticDetails[3] = rs.getString("overall_result");
                    staticDetails[4] = rs.getString("test_attempt_timestamp");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return staticDetails;
        }

        public static List<String[]> getQuestionResultsByTestId(String testId) {
            String sql = "SELECT result.question_id, result.result_value " +
                         "FROM public.result " +
                         "JOIN test ON test.result_id = result.result_id " +
                         "WHERE test.test_id = ?";

            List<String[]> questionResults = new ArrayList<>();

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, testId);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String[] detail = new String[2];
                    detail[0] = rs.getString("question_id");
                    detail[1] = String.valueOf(rs.getInt("result_value"));
                    questionResults.add(detail);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return questionResults;
        }



        public static String[] getTestDetailsForUserTest(String userId) {
            String query = "SELECT test_id, topic_name, subtopic_name, test_duration, instructor_id, attempt_status FROM public.test WHERE users_id = ?";
            List<String> testDetailsList = new ArrayList<>();
            
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                
                pstmt.setString(1, userId);
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    String testDetails = String.join(",",
                        rs.getString("test_id"),
                        rs.getString("topic_name"),
                        rs.getString("subtopic_name"),
                        rs.getString("test_duration"),
                        rs.getString("instructor_id"),
                        rs.getString("attempt_status")
                    );
                    testDetailsList.add(testDetails);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return testDetailsList.toArray(new String[0]);
        }
        	
        public static String getCorrectAnswerForQuestion(String questionId) {
            String correctAnswer = "";  // Initialize the answer variable
            String query = "SELECT answer_value FROM public.answers WHERE question_id = ?";
            
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                 
                pstmt.setString(1, questionId);  // Set the question_id
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    correctAnswer = rs.getString("answer_value");  // Get the correct answer
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return correctAnswer;  // Return the answer
        }
        
        public static String getRecentResultId() {
            String recentResultId = "";
            String query = "SELECT result_id FROM public.result " +
                           "ORDER BY CAST(REGEXP_REPLACE(result_id, '[^0-9]', '', 'g') AS INTEGER) DESC " +
                           "LIMIT 1";

            try (Connection conn = connect();
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    recentResultId = rs.getString("result_id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return recentResultId;
        }

        public static String incrementResultId(String resultId) {
            String numericPart = resultId.replaceAll("\\D+", ""); // Extract numeric part
            int idNum = 0;
            try {
                idNum = Integer.parseInt(numericPart);  // Parse the numeric part
            } catch (NumberFormatException e) {
                // Handle the case where the format is unexpected
                e.printStackTrace();
                idNum = 0;  // Default to zero or handle appropriately
            }
            
            idNum += 1;
            
            // Return the new resultId with padding
            return "resultNo" + String.format("%04d", idNum);
        }




        public static void updateTestResult(String resultId, double totalResult,String test_id) {
            String query = "UPDATE public.test SET attempt_status = true, total_result = ?, result_id = ?,test_attempt_timestamp = NOW() WHERE test_id = ?";
            try (Connection conn = connect();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, totalResult);
                stmt.setString(2, resultId);
                stmt.setString(3, test_id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        public static void updateResultTable(String resultId, String questionId, int resultValue) {
            String query = "INSERT INTO public.result (result_id, question_id, result_value) VALUES (?, ?, ?)";
            
            try (Connection conn = connect(); 
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                 
                pstmt.setString(1, resultId);
                pstmt.setString(2, questionId);
                pstmt.setInt(3, resultValue);
                
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        
        public static Image getQuestionImageFromDatabase(String question_id) {
            String query = "SELECT question_image FROM public.questions WHERE question_id = ?";
            Image image = null;

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, question_id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    byte[] imageData = rs.getBytes("question_image");

                    // Convert byte array into JavaFX Image
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    image = new Image(bis);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return image;
        }
        
        public static Image getQuestionOption1ImageFromDatabase(String question_id) {
            String query = "SELECT question_option1_image FROM public.questions WHERE question_id = ?";
            Image image = null;

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, question_id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    byte[] imageData = rs.getBytes("question_option1_image");

                    // Convert byte array into JavaFX Image
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    image = new Image(bis);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return image;
        }
        
        public static Image getQuestionOption2ImageFromDatabase(String question_id) {
            String query = "SELECT question_option2_image FROM public.questions WHERE question_id = ?";
            Image image = null;

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, question_id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    byte[] imageData = rs.getBytes("question_option2_image");

                    // Convert byte array into JavaFX Image
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    image = new Image(bis);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return image;
        }
        
        public static Image getQuestionOption3ImageFromDatabase(String question_id) {
            String query = "SELECT question_option3_image FROM public.questions WHERE question_id = ?";
            Image image = null;

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, question_id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    byte[] imageData = rs.getBytes("question_option3_image");

                    // Convert byte array into JavaFX Image
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    image = new Image(bis);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return image;
        }
        
        public static Image getQuestionOption4ImageFromDatabase(String question_id) {
            String query = "SELECT question_option4_image FROM public.questions WHERE question_id = ?";
            Image image = null;

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, question_id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    byte[] imageData = rs.getBytes("question_option4_image");

                    // Convert byte array into JavaFX Image
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    image = new Image(bis);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return image;
        }
        
        public static String getQuestionDescFromDatabase(String question_id) {
            // Define the SQL query to fetch the question description by question_id
            String query = "SELECT question_desc FROM questions WHERE question_id = ?";

            // Variable to hold the question description
            String questionDesc = null;

            try (Connection conn = connect(); // Assuming connect() establishes the connection
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, question_id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    questionDesc = rs.getString("question_desc");
                }
                
                System.out.println("Fetched Question Description: " + questionDesc);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return questionDesc;
        }

        public static int getNumberOfQuestionsInSubtopic(String subtopicName) {
            String query = "SELECT COUNT(*) AS question_count FROM public.questions WHERE subtopic_name = ?";
            int questionCount = 0; // Default to 0 if no data is found

            try {
                Connection conn = connect(); // Assuming `connect()` establishes a database connection
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, subtopicName);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    questionCount = rs.getInt("question_count");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exception, maybe log it or rethrow
            }

            System.out.println("question count : " + questionCount);
            return questionCount;
            // Return the number of questions as an integer
        }
        
        public static String[] getQuestionIdsForSubtopic(String subtopicName) {
            String query = "SELECT question_id FROM public.questions WHERE subtopic_name = ?";
            List<String> questionIds = new ArrayList<>();

            try {
                Connection conn = connect(); // Assuming `connect()` establishes a database connection
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, subtopicName);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    questionIds.add(rs.getString("question_id")); // Add each question_id to the list
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Convert the ArrayList to a String array and return it
            return questionIds.toArray(new String[0]);
        }
        
        public static String getOption1DescForQuestion(String questionId) {
            String correctAnswer = "";  // Initialize the answer variable
            String query = "SELECT question_option1_desc FROM public.questions WHERE question_id = ?";
            
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                 
                pstmt.setString(1, questionId);  // Set the question_id
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    correctAnswer = rs.getString("question_option1_desc");  // Get the correct answer
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return correctAnswer;  // Return the answer
        }
        
        public static String getOption2DescForQuestion(String questionId) {
            String correctAnswer = "";  // Initialize the answer variable
            String query = "SELECT question_option2_desc FROM public.questions WHERE question_id = ?";
            
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                 
                pstmt.setString(1, questionId);  // Set the question_id
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    correctAnswer = rs.getString("question_option2_desc");  // Get the correct answer
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return correctAnswer;  // Return the answer
        }
        
        public static String getOption3DescForQuestion(String questionId) {
            String correctAnswer = "";  // Initialize the answer variable
            String query = "SELECT question_option3_desc FROM public.questions WHERE question_id = ?";
            
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                 
                pstmt.setString(1, questionId);  // Set the question_id
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    correctAnswer = rs.getString("question_option3_desc");  // Get the correct answer
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return correctAnswer;  // Return the answer
        }
        
        public static String getOption4DescForQuestion(String questionId) {
            String correctAnswer = "";  // Initialize the answer variable
            String query = "SELECT question_option4_desc FROM public.questions WHERE question_id = ?";
            
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                 
                pstmt.setString(1, questionId);  // Set the question_id
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    correctAnswer = rs.getString("question_option4_desc");  // Get the correct answer
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return correctAnswer;  // Return the answer
        }

        public static String[] getTopicNamesFromInfoTable() {
            String query = "SELECT DISTINCT topic_name FROM public.mtap_information";
            List<String> TopicNames = new ArrayList<>();

            try {
                Connection conn = connect(); // Assuming `connect()` establishes a database connection
                PreparedStatement pstmt = conn.prepareStatement(query);
                
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    TopicNames.add(rs.getString("topic_name")); // Add each question_id to the list
                }
                
                Collections.sort(TopicNames);
                
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Convert the ArrayList to a String array and return it
            return TopicNames.toArray(new String[0]);
        }
        
        public static String[] getSubTopicNamesFromInfoTable(String topic_name) {
            String query = "SELECT DISTINCT subtopic_name FROM public.mtap_information WHERE topic_name = ?";
            List<String> SubTopicNames = new ArrayList<>();

            try {
                Connection conn = connect(); // Assuming `connect()` establishes a database connection
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, topic_name);
                
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    SubTopicNames.add(rs.getString("subtopic_name")); // Add each question_id to the list
                }
                
                Collections.sort(SubTopicNames);
                
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Convert the ArrayList to a String array and return it
            return SubTopicNames.toArray(new String[0]);
        }
}
