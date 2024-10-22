import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LOGIN {

    // HashMap to store user information (in a real application, you'd use a database)
    private static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        signUpPage(); // Start with the sign-up page
    }

    // Method to hash passwords for security (OWASP practice)
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Sign-up page
    public static void signUpPage() {
        JFrame frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between elements
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Arial", Font.PLAIN, 16); // Set font size to 16

        // Title for the sign-up form
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(titleLabel, gbc);

        // Reset gridwidth for the rest of the components
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Name field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(font);
        JTextField nameText = new JTextField(20); // Increase the textbox width
        nameText.setFont(font);

        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(nameLabel, gbc);
        gbc.gridx = 1;
        frame.add(nameText, gbc);

        // Reg Number field
        JLabel regLabel = new JLabel("Reg Number:");
        regLabel.setFont(font);
        JTextField regText = new JTextField(20);
        regText.setFont(font);

        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(regLabel, gbc);
        gbc.gridx = 1;
        frame.add(regText, gbc);

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(font);
        JTextField emailText = new JTextField(20);
        emailText.setFont(font);

        gbc.gridx = 0; gbc.gridy = 3;
        frame.add(emailLabel, gbc);
        gbc.gridx = 1;
        frame.add(emailText, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(font);
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setFont(font);

        gbc.gridx = 0; gbc.gridy = 4;
        frame.add(passwordLabel, gbc);
        gbc.gridx = 1;
        frame.add(passwordText, gbc);

        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(font);
        JPasswordField confirmPasswordText = new JPasswordField(20);
        confirmPasswordText.setFont(font);

        gbc.gridx = 0; gbc.gridy = 5;
        frame.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1;
        frame.add(confirmPasswordText, gbc);

        // Password generator button
        JButton generatePasswordButton = new JButton("Generate Strong Password");
        gbc.gridx = 1; gbc.gridy = 6;
        frame.add(generatePasswordButton, gbc);

        // Action listener for generating a strong password and copying it to clipboard
        generatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String generatedPassword = generateRandomPassword();
                passwordText.setText(generatedPassword);
                confirmPasswordText.setText(generatedPassword); // Set generated password in both fields

                // Offer the user to copy the generated password
                int option = JOptionPane.showConfirmDialog(frame, "Generated Password: " + generatedPassword + "\nDo you want to copy it to clipboard?", "Password Generated", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    copyToClipboard(generatedPassword); // Copy to clipboard
                    JOptionPane.showMessageDialog(frame, "Password copied to clipboard.");
                }
            }
        });

        // Sign-up button
        JButton signUpButton = new JButton("SIGN UP");
        gbc.gridx = 1; gbc.gridy = 7;
        frame.add(signUpButton, gbc);

        JLabel successLabel = new JLabel("");
        gbc.gridx = 1; gbc.gridy = 8;
        frame.add(successLabel, gbc);

        // Validate sign-up form and password
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String regNumber = regText.getText();
                String email = emailText.getText();
                String password = new String(passwordText.getPassword());
                String confirmPassword = new String(confirmPasswordText.getPassword());

                if (isValidEmail(email) && isValidPassword(password) && password.equals(confirmPassword)) {
                    // Hash the password for security
                    String hashedPassword = hashPassword(password);
                    // Save user data
                    users.put(email, new User(name, regNumber, email, hashedPassword));

                    // Notify success and redirect to login page
                    successLabel.setText("Sign-up successful! Please login.");
                    frame.dispose();
                    loginPage();
                } else {
                    successLabel.setText("Invalid input. Ensure passwords match and are strong.");
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Login page
    public static void loginPage() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridBagLayout()); // Use GridBagLayout for alignment
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding between elements
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Arial", Font.PLAIN, 16); // Font size 16

        // Title for the login form
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Create labels and text fields for Login
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(font);
        JTextField emailText = new JTextField(20);
        emailText.setFont(font);

        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(emailLabel, gbc);
        gbc.gridx = 1;
        frame.add(emailText, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(font);
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setFont(font);

        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(passwordLabel, gbc);
        gbc.gridx = 1;
        frame.add(passwordText, gbc);

        // Login button
        JButton loginButton = new JButton("LOGIN");
        gbc.gridx = 1; gbc.gridy = 3;
        frame.add(loginButton, gbc);

        JLabel loginLabel = new JLabel("");
        gbc.gridx = 1; gbc.gridy = 4;
        frame.add(loginLabel, gbc);

        // Add action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailText.getText();
                String password = new String(passwordText.getPassword());

                // Check if the email exists and validate the password
                if (users.containsKey(email)) {
                    User user = users.get(email);
                    String hashedPassword = hashPassword(password);

                    if (user.getPassword().equals(hashedPassword)) {
                        displayUserInfo(user); // Show the user's info page
                        frame.dispose();
                    } else {
                        loginLabel.setText("Invalid password.");
                    }
                } else {
                    loginLabel.setText("User not found. Please sign up.");
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to display user info after login
    public static void displayUserInfo(User user) {
        JFrame frame = new JFrame("User Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JLabel userInfoLabel = new JLabel("Welcome " + user.getName() + " | Reg Number: " + user.getRegNumber());
        frame.add(userInfoLabel);

        frame.setVisible(true);
    }

    // Email validation method
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        Matcher matcher = pat.matcher(email);
        return matcher.matches();
    }

    // Password validation method to check for strong passwords
    public static boolean isValidPassword(String password) {
        // At least 8 chars, 1 upper, 1 lower, 1 digit, 1 special char
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
        Pattern pat = Pattern.compile(passwordRegex);
        return pat.matcher(password).matches();
    }

    // Method to generate a strong random password
    public static String generateRandomPassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "@$!%*?&#";
        String allChars = upper + lower + digits + special;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Ensure at least one character from each category
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));

        // Fill the remaining with random characters from all categories
        for (int i = 4; i < 12; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }

    // Method to copy generated password to clipboard
    public static void copyToClipboard(String str) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(str);
        clipboard.setContents(transferable, null);
    }
}

// User class to store user details
class User {
    private String name;
    private String regNumber;
    private String email;
    private String password;

    public User(String name, String regNumber, String email, String password) {
        this.name = name;
        this.regNumber = regNumber;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
