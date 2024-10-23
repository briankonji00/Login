H230087A BRIAN T KONJI ISA 1.2

Secure Login and Signup System in Java
Description
This is a simple Java-based GUI application that implements a secure sign-up and login system. Users can register by entering their name, registration number, email, and a secure password. After registration, they can log in using their credentials. Once logged in, the system displays the user's name and registration number.
Features
1. Sign-up Form:
- Users must provide their name, registration number, email, and password.
- The system validates the email format.
- The system validates password strength (must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long).
- The system allows users to confirm their password.
- The system provides feedback if the password is weak or mismatched.
- A random password generator is available to help users create a strong password.
- Users can copy the generated password to their clipboard.
2. Login Form:
- Users can log in using their email and password.
- Passwords are hashed and securely stored (using SHA-256).
- The system checks the credentials against the stored user data and grants access upon a successful match.
3. Show Password:
- Users can toggle the visibility of their password during sign-up and login.
4. Security Features:
- Password Strength: Passwords must meet secure coding standards, including length and complexity.
- Password Hashing: Passwords are hashed using SHA-256 for secure storage.
- Email Validation: Email addresses are validated to ensure only valid formats are allowed.
5. Friendly UI:
- Labels and text fields are styled with a font size of 16px for readability.
- All forms are centered on the screen for a better user experience.
- The background has been set to a clean, user-friendly white color.
How to Run
1. Clone the Repository:

git clone https://github.com/briankonji00/Login.git

2. Open in intelij:
- Import the project into intelij- Ensure you have a Java Development Kit (JDK) installed.
3. Run the Program:
- Run the `LOGIN` class. The system will start with the sign-up form.
Password Security Features
Password Requirements:
- At least one uppercase letter (A-Z)
- At least one lowercase letter (a-z)
- At least one digit (0-9)
- At least one special character (@$!%*?&#)
- Minimum length of 8 characters
Password Hashing:
- Passwords are hashed using the SHA-256 algorithm before being stored in the system to ensure security.
Random Password Generator:
- The system includes a built-in random password generator to help users create strong passwords. Users can generate and copy this password.
OWASP Top 10 Implementation
The project follows the OWASP top 10 security guidelines:
1. Injection: Input validation on email and password fields to prevent potential SQL injection or other malicious input.
2. Broken Authentication: Passwords are hashed and not stored in plain text.
3. Sensitive Data Exposure: Passwords are hashed using SHA-256 to protect user credentials.
4. Security Misconfiguration: Uses Java's built-in security libraries for secure password hashing.
5. Cross-Site Scripting (XSS): Not applicable to this project as it's a desktop application, but user inputs are still validated.
Future Improvements
1. Implement session management for enhanced security.
2. Add database integration for persistent storage of user data.
3. Improve the UI for a more modern look and feel.
Unit Testing
Unit tests can be added to validate:
- Email validation
- Password strength checking
- Successful login and sign-up operations
Special Features
1. Password Strength Indicator: The application provides real-time feedback on the strength of the password being created.
2. Password Generator: A feature to generate a strong random password for the user.
3. Copy to Clipboard: The generated password can be copied to the clipboard for easy use.
Requirements
1. Java 8 or above
2. any preferred Java IDE
Author
- [BRIAN T KONJI]
