package combinedlogingui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class combinedlogingui {
    private JFrame frame;
    private JPanel loginPanel, signupPanel;
    private CardLayout cardLayout;
    private File userFile = new File("users.txt");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(combinedlogingui::new);
    }

    public combinedlogingui() {
        frame = new JFrame("Login System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        loginPanel = createLoginPanel();
        signupPanel = createSignupPanel();

        container.add(loginPanel, "login");
        container.add(signupPanel, "signup");

        frame.setContentPane(container);
        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(60, 63, 65));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(200, 20, 100, 30);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(60, 80, 100, 30);

        JTextField userField = new JTextField();
        userField.setBounds(130, 80, 220, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(60, 130, 100, 30);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(130, 130, 220, 30);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(140, 200, 90, 35);

        JButton goToSignup = new JButton("Sign Up");
        goToSignup.setBounds(248, 200, 90, 35);

        JLabel msg = new JLabel("", SwingConstants.CENTER);
        msg.setForeground(Color.RED);
        msg.setBounds(100, 250, 300, 25);

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            if (isUserValid(user, pass)) {
                showActivityPanel(user);
            } else {
                msg.setForeground(Color.RED);
                msg.setText("Invalid username or password");
            }
        });

        goToSignup.addActionListener(e -> cardLayout.show(frame.getContentPane(), "signup"));

        panel.add(title);
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginBtn);
        panel.add(goToSignup);
        panel.add(msg);
        return panel;
    }

    private JPanel createSignupPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(60, 63, 65));

        JLabel title = new JLabel("Sign Up");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(200, 20, 120, 30);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(60, 80, 100, 30);

        JTextField userField = new JTextField();
        userField.setBounds(130, 80, 220, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(60, 130, 100, 30);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(130, 130, 220, 30);

        JButton signupBtn = new JButton("Register");
        signupBtn.setBounds(140, 200, 90, 35);
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(248, 200, 90, 35);

        JLabel msg = new JLabel("", SwingConstants.CENTER);
        msg.setForeground(Color.YELLOW);
        msg.setBounds(100, 250, 300, 25);

        signupBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                msg.setText("Fields can't be empty");
            } else if (userExists(user)) {
                msg.setText("Username already exists");
            } else {
                saveUser(user, pass);
                msg.setForeground(new Color(0, 200, 0));
                msg.setText("User registered successfully!");
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(frame.getContentPane(), "login"));

        panel.add(title);
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(signupBtn);
        panel.add(backBtn);
        panel.add(msg);
        return panel;
    }

    private void showActivityPanel(String username) {
        JPanel cipherPanel = new JPanel();
        cipherPanel.setLayout(null);
        cipherPanel.setBackground(new Color(60, 63, 65));

        JLabel title = new JLabel("Caesar Cipher Encryption");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(120, 10, 300, 30);

        JLabel inputLabel = new JLabel("Input Text:");
        inputLabel.setForeground(Color.WHITE);
        inputLabel.setBounds(50, 60, 100, 25);
        JTextField inputField = new JTextField();
        inputField.setBounds(150, 60, 250, 25);

        JLabel keyLabel = new JLabel("Key:");
        keyLabel.setForeground(Color.WHITE);
        keyLabel.setBounds(50, 100, 100, 25);
        JComboBox<Integer> keyBox = new JComboBox<>();
        for (int i = 1; i <= 10; i++) keyBox.addItem(i);
        keyBox.setBounds(150, 100, 250, 25);

        JButton encryptBtn = new JButton("Encrypt");
        encryptBtn.setBounds(80, 140, 100, 30);
        JButton decryptBtn = new JButton("Decrypt");
        decryptBtn.setBounds(220, 140, 100, 30);

        JLabel outputLabel = new JLabel("Output:");
        outputLabel.setForeground(Color.WHITE);
        outputLabel.setBounds(50, 190, 100, 25);
        JTextField outputField = new JTextField();
        outputField.setEditable(false);
        outputField.setBounds(150, 190, 250, 25);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(180, 240, 100, 30);
        logoutBtn.addActionListener(e -> cardLayout.show(frame.getContentPane(), "login"));

        encryptBtn.addActionListener(e -> {
            String text = inputField.getText();
            int key = (int) keyBox.getSelectedItem();
            outputField.setText(caesarEncrypt(text, key));
        });

        decryptBtn.addActionListener(e -> {
            String text = inputField.getText();
            int key = (int) keyBox.getSelectedItem();
            outputField.setText(caesarDecrypt(text, key));
        });

        cipherPanel.add(title);
        cipherPanel.add(inputLabel);
        cipherPanel.add(inputField);
        cipherPanel.add(keyLabel);
        cipherPanel.add(keyBox);
        cipherPanel.add(encryptBtn);
        cipherPanel.add(decryptBtn);
        cipherPanel.add(outputLabel);
        cipherPanel.add(outputField);
        cipherPanel.add(logoutBtn);

        frame.getContentPane().add(cipherPanel, "activity");
        cardLayout.show(frame.getContentPane(), "activity");
    }

    private String caesarEncrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) ((c - base + key) % 26 + base));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private String caesarDecrypt(String text, int key) {
        return caesarEncrypt(text, 26 - key);
    }

    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(",")[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {}
        return false;
    }

    private boolean isUserValid(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {}
        return false;
    }

    private void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}