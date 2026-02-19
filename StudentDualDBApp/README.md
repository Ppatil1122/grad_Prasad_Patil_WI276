# StudentDualDBApp - Spring Boot with Dual Databases (H2 and PostgreSQL)

This project demonstrates a Spring Boot web application that saves student records into two separate databases: an in-memory H2 database and a PostgreSQL database.

## Project Structure

```
StudentDualDBApp
├── src/main/java/com/example/student
│    ├── StudentDualDbAppApplication.java
│    ├── config
│    │     ├── H2Config.java
│    │     └── PostgresConfig.java
│    ├── controller
│    │     └── StudentController.java
│    ├── entity
│    │     └── Student.java
│    ├── repository
│    │     ├── h2
│    │     │     └── H2StudentRepository.java
│    │     └── pg
│    │           └── PgStudentRepository.java
│    └── service
│          └── StudentService.java
├── src/main/resources
│    ├── templates
│    │     ├── student.html
│    │     └── success.html
│    └── application.properties
└── pom.xml
```

## Prerequisites

Before running the application, ensure you have the following installed:

1.  **Java Development Kit (JDK) 17 or higher**
2.  **Apache Maven** (usually bundled with STS/Eclipse)
3.  **Spring Tool Suite (STS) or Eclipse IDE with Spring Tools 4**
4.  **PostgreSQL Database Server**: Make sure PostgreSQL is installed and running on `localhost:5432`.

## PostgreSQL Setup

1.  **Create Database**: Connect to your PostgreSQL server (e.g., using `psql` or pgAdmin) and create a database named `studentdb`.
    ```sql
    CREATE DATABASE studentdb;
    ```
2.  **User Credentials**: The application expects a user `postgres` with password `postgres`. If your PostgreSQL setup uses different credentials, update the `src/main/resources/application.properties` file accordingly:
    ```properties
    spring.datasource.pg.username=your_username
    spring.datasource.pg.password=your_password
    ```

## Running the Application in STS/Eclipse

1.  **Import as Maven Project**:
    *   Open STS/Eclipse.
    *   Go to `File -> Import...`.
    *   Select `Maven -> Existing Maven Projects` and click `Next`.
    *   Browse to the `StudentDualDBApp` directory (the root of this project) and click `Finish`.

2.  **Update Maven Project (if needed)**:
    *   Right-click on the `StudentDualDBApp` project in the Project Explorer.
    *   Go to `Maven -> Update Project...`.
    *   Ensure `Force Update of Snapshots/Releases` is checked and click `OK`.

3.  **Run the Application**:
    *   Locate the `StudentDualDbAppApplication.java` file in `src/main/java/com/example/student`.
    *   Right-click on `StudentDualDbAppApplication.java`.
    *   Select `Run As -> Spring Boot App`.

## Accessing the Application

Once the application starts successfully (you'll see messages in the console indicating it's running on port 8080):

1.  **Student Form**: Open your web browser and navigate to: `http://localhost:8080/`
    *   You will see a form to enter student details.
    *   Fill in the details and click `INSERT`.
    *   Upon successful insertion, you will be redirected to a success page.

2.  **H2 Console**: You can access the H2 database console at: `http://localhost:8080/h2-console`
    *   **JDBC URL**: `jdbc:h2:mem:h2db`
    *   **User Name**: `sa`
    *   **Password**: (leave empty)
    *   Click `Connect` to view the H2 database. You should see the `STUDENTS` table with the data you inserted.

## Verification

After inserting data through the web form:

*   **H2 Database**: Check the `STUDENTS` table in the H2 console (`http://localhost:8080/h2-console`).
*   **PostgreSQL Database**: Connect to your `studentdb` database using `psql` or pgAdmin and query the `students` table:
    ```sql
    SELECT * FROM students;
    ```
    You should see the same student records in both databases.

This setup ensures that the application is ready to run and demonstrates the dual database persistence as required. Enjoy!
