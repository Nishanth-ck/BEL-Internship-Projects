# Internship at Bharal Electronics Limited (BEL)

I had an oppurtunity of working as a software developer intern at the prestigious BEL company. During my internship period, I developed two projects : <br>

CTAP (Cognitive Test Application for Pilots) and MTAP (Motor's Test Application for Pilots)

The tech stack used in this project are :

JavaFX,ScreenBuilder,PostreSQL and Eclipse IDE.

How to setup the projects :

1) Install Eclipse IDE in the laptop : follow the steps as specified in the youtube video -> https://youtu.be/wMTdB7ElrIQ?si=ASQPxbcHrJYTEz5h <br>
2) Install JavaFX in the laptop : follow the steps as specified in the youtube video -> https://youtu.be/_7OM-cMYWbQ?si=luzDZEF7Du-2-0RK <br>
3) Install ScreenBuilder in the laptop : follow the steps as specified in the youtube video -> https://youtu.be/-Obxf6NjnbQ?si=daGSthdp6L0X95rP <br>
4) Install PostgreSQL and pgAdmin4 in the laptop : follow the steps as specified in the youtube video -> https://youtu.be/4qH-7w5LZsA?si=rx68ckj5kXmhZOB3 <br>
5) After completing the installations, clone this repository : "https://github.com/Nishanth-ck/BEL-Internship-Projects.git" and add it to "eclipse-workspace: folder in C drive. Both CTAP and MTAP folders can be used individually to run using eclipse. <br>
6) After the folders are displayed on the eclipse screen, install the jar file from : https://www.postgresql.org/download/ and add it to build path configuration. <br>
7) Set up the database (especially questions and answers) in the local server postgreSQL : the tables used are -> (mtap_information,users,instructors,questions,answers,result,test) . Make sure it's running on the server at : URL = "jdbc:postgresql://localhost:5432/database_name" <br>
8) Run the project : a)Open pdAdmin4 and check if the localhost server is connected <br> b)Open the project folder to run (CTAP or MTAP) -> go to Main.java and right click on it -> Run as Java Application <br>
9) The program application starts. Contains two important modules throughout the working model -> a)Instructor module who logins and schedules and tracks the user's or student's test details. <br> b) User Module who takes the test based on the scheduling information from the instructor and gets detailed result for every attempted test. <br>
10) The CTAP contains questions on aptitude (spatial,verbal,analytical and verbal reasoning) and MTAP contains questions on various types of motor's used by pilots such as Altimeter, Compass and other 10 such topics. <br>
