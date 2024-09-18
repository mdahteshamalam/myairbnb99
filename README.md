* This is a spring boot rest api based backend project.It includes user creation and login features.Postman is used as a tool also it includes spring security for authentication purpose and mysql database is used.

Spring Boot REST API Backend Project
This project is a Spring Boot REST API based backend that includes user creation and login functionalities. It utilizes Spring Security for authentication and MySQL as the database.

** Features
. User Creation: Allows new users to register by providing necessary details.
. Login: Provides an API endpoint for users to log in and authenticate themselves.
. Authentication: Uses Spring Security to handle authentication and protect the API endpoints.
. MySQL Database: Stores user information securely in a MySQL database.
** User Booking System:
. Price Calculation
. PDF Generation
**Authentication and Authorization:
. Users can create and manage bookings (for properties, events, or other services).
. Allows retrieval of booking history for registered users.
. Automatically generates PDF documents for user bookings, invoices, or receipts using a PDF generation library.
. Dynamic price calculation based on factors such as duration, selected services, and any discounts.

. Secured API endpoints using Spring Security.
. Authentication is handled via JWT tokens for logged-in user Technologies Used

* Spring Boot: The main framework used for building the application.
* Spring Security: Provides authentication and authorization mechanisms.
* MySQL: The database used for storing user data.
* Postman: Used for testing the API endpoints during development.
* Twilio SMS: Sends SMS notifications to users (e.g., for verification).
*AWS S3 File Upload: Stores files in AWS S3 cloud storage.
Setup Instructions
Clone the repository:

git clone <https://github.com/mdahteshamalam/>
* Configure MySQL Database:
 spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name spring.datasource.username=your_username spring.datasource.password=your_password spring.jpa.hibernate.ddl-auto=update

* Configure AWS S3 and Configure Twilio:
  a.aws.s3.bucket-name=your-s3-bucket-name aws.s3.access-key=your-access-key aws.s3.secret-key=your-secret-key aws.s3.region=your-region

  b.twilio.account-sid=your-account-sid twilio.auth-token=your-auth-token twilio.phone-number=your-twilio-phone-number

** Run the application:
 Navigate to the project directory and run the application

**Test with Postman:
Use Postman to test the API endpoints for user creation, login, authentication and other.

Run the application:
Navigate to the project directory and run the application

Test with Postman:
Use Postman to test the API endpoints for user creation, login, authentication and other.
