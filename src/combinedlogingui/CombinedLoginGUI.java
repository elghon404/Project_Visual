package combinedlogingui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CombinedLoginGUI extends JFrame {

    // general constants
    public static final Font FONT_GENERAL_UI = new Font("Segoe UI", Font.PLAIN, 20);
    public static final Font FONT_FORGOT_PASSWORD = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Color COLOR_OUTLINE = new Color(103, 112, 120);
    public static final Color COLOR_BACKGROUND = new Color(37, 51, 61);
    public static final Color COLOR_INTERACTIVE = new Color(108, 216, 158);
    public static final Color COLOR_INTERACTIVE_DARKER = new Color(87, 171, 127);
    public static final Color OFFWHITE = new Color(229, 229, 229);
    public static final String BUTTON_TEXT_LOGIN = "Login";
    public static final String BUTTON_TEXT_FORGOT_PASS = "Forgot your password?";
    public static final String BUTTON_TEXT_REGISTER = "Register";
    public static final String PLACEHOLDER_TEXT_USERNAME = "Username/email";
    public static final int ROUNDNESS = 8;

    private final Toaster toaster;

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CombinedLoginGUI::new);
    }

    // users info ---------------------------------
    public static final String user_1 = "ghonem";
    public static final String pass_1 = "123";
    public static final String user_2 = "shalapy";
    public static final String pass_2 = "456";
    public static final String user_3 = "omar";
    public static final String pass_3 = "789";
    //----------------------------------------------

    // main constructor
    private CombinedLoginGUI() {
        this.setTitle("Login App");
        JPanel mainPanel = getMainPanel();
        addLogo(mainPanel);
        addSeparator(mainPanel);
        addUsernameTextField(mainPanel);
        addPasswordTextField(mainPanel);
        addLoginButton(mainPanel);
        addForgotPasswordButton(mainPanel);
        addRegisterButton(mainPanel);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
        this.toFront();
        setLocationRelativeTo(null);
        toaster = new Toaster(mainPanel);
    }

    // getting the main panel
    private JPanel getMainPanel() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(800, 400));
        panel.setBackground(COLOR_BACKGROUND);

        // moving panel on drag
        MouseAdapter ma = new MouseAdapter() {
            int lastX, lastY;
            public void mousePressed(MouseEvent e) {
                lastX = e.getXOnScreen();
                lastY = e.getYOnScreen();
            }
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(getLocation().x + x - lastX, getLocation().y + y - lastY);
                lastX = x;
                lastY = y;
            }
        };
        panel.addMouseListener(ma);
        panel.addMouseMotionListener(ma);
        return panel;
    }

    // adding the line separator
    private void addSeparator(JPanel panel) {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setForeground(COLOR_OUTLINE);
        separator.setBounds(310, 80, 1, 240);
        panel.add(separator);
    }

    // adding the logo sec
    private void addLogo(JPanel panel) {
        JLabel label = new JLabel("LOGO");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(55, 146, 200, 110);
        panel.add(label);
    }

    // adding the username sec
    private void addUsernameTextField(JPanel panel) {
        JTextField field = new TextFieldUsername();
        field.setBounds(423, 109, 250, 44);
        panel.add(field);
    }

    // adding the password sec
    private void addPasswordTextField(JPanel panel) {
        JPasswordField field = new TextFieldPassword();
        field.setBounds(423, 168, 250, 44);
        panel.add(field);
    }

    // adding the login button
    private void addLoginButton(JPanel panel) {
        JLabel loginBtn = new JLabel(BUTTON_TEXT_LOGIN, SwingConstants.CENTER);
        loginBtn.setFont(FONT_GENERAL_UI);
        loginBtn.setOpaque(true);
        loginBtn.setBackground(COLOR_INTERACTIVE);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setBounds(423, 247, 250, 44);

        loginBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Component[] comps = panel.getComponents();
                String username = "", password = "";

                for (Component c : comps) {
                    if (c instanceof TextFieldUsername) {
                        username = ((TextFieldUsername) c).getText().trim();
                    } else if (c instanceof TextFieldPassword) {
                        password = new String(((TextFieldPassword) c).getPassword()).trim();
                    }
                }

                if (username.equals(PLACEHOLDER_TEXT_USERNAME) || password.equals("Password")) {
                    toaster.error("Please fill in all fields");
                    return;
                }

                if ((username.equals(user_1) && password.equals(pass_1)) ||
                    (username.equals(user_2) && password.equals(pass_2)) ||
                    (username.equals(user_3) && password.equals(pass_3))) {
                    toaster.success("Login successful!");
                    SwingUtilities.invokeLater(SuccessFrame::new);
                } else {
                    toaster.error("Wrong username or password");
                    SwingUtilities.invokeLater(ErrorFrame::new);
                }
            }
        });

        panel.add(loginBtn);
    }

    // adding the forgot password link
    private void addForgotPasswordButton(JPanel panel) {
        panel.add(new HyperlinkText(BUTTON_TEXT_FORGOT_PASS, 423, 300, () -> toaster.error("Forgot password event")));
    }

    // adding the register link
    private void addRegisterButton(JPanel panel) {
        panel.add(new HyperlinkText(BUTTON_TEXT_REGISTER, 631, 300, () -> toaster.success("Register event")));
    }

    // styling the username textfield
    static class TextFieldUsername extends JTextField {
        private final String placeholder = "Username/email";
        private boolean showingPlaceholder = true;

        public TextFieldUsername() {
            setOpaque(false);
            setBackground(COLOR_BACKGROUND);
            setForeground(Color.GRAY);
            setCaretColor(Color.white);
            setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            setFont(FONT_GENERAL_UI);
            setText(placeholder);

            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (getText().equals(placeholder)) {
                        setText("");
                        setForeground(Color.WHITE);
                        showingPlaceholder = false;
                    }
                }

                public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) {
                        setText(placeholder);
                        setForeground(Color.GRAY);
                        showingPlaceholder = true;
                    }
                }
            });
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ROUNDNESS, ROUNDNESS);
            super.paintComponent(g2);
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(COLOR_INTERACTIVE);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ROUNDNESS, ROUNDNESS);
        }
    }

    // styling the password textfield
    static class TextFieldPassword extends JPasswordField {
        private final String placeholder = "Password";
        private boolean showingPlaceholder = true;
        private boolean showPassword = false;
        private JButton toggleButton;

        public TextFieldPassword() {
            setOpaque(false);
            setBackground(COLOR_BACKGROUND);
            setForeground(Color.GRAY);
            setCaretColor(Color.white);
            setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            setFont(FONT_GENERAL_UI);
            setEchoChar((char) 0);
            setText(placeholder);

            toggleButton = new JButton("👁");
            toggleButton.setFocusable(false);
            toggleButton.setBorderPainted(false);
            toggleButton.setContentAreaFilled(false);
            toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            toggleButton.addActionListener(e -> togglePasswordVisibility());

            setLayout(null);
            add(toggleButton);

            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    toggleButton.setBounds(getWidth() - 30, 10, 30, getHeight() - 20);
                }
            });

            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (showingPlaceholder) {
                        setText("");
                        setForeground(Color.WHITE);
                        setEchoChar('•');
                        showingPlaceholder = false;
                    }
                }

                public void focusLost(FocusEvent e) {
                    if (getPassword().length == 0) {
                        setText(placeholder);
                        setForeground(Color.GRAY);
                        setEchoChar((char) 0);
                        showingPlaceholder = true;
                    }
                }
            });
        }

        private void togglePasswordVisibility() {
            if (showingPlaceholder) return;
            showPassword = !showPassword;
            setEchoChar(showPassword ? (char) 0 : '•');
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ROUNDNESS, ROUNDNESS);
            super.paintComponent(g2);
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(COLOR_OUTLINE);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ROUNDNESS, ROUNDNESS);
        }
    }

    // reusable hyperlink text label
    static class HyperlinkText extends JLabel {
        public HyperlinkText(String text, int x, int y, Runnable action) {
            super(text);
            setForeground(COLOR_OUTLINE);
            setFont(FONT_FORGOT_PASSWORD);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Dimension prefSize = getPreferredSize();
            setBounds(x, y, prefSize.width + 20, prefSize.height);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) { action.run(); }
                public void mouseEntered(MouseEvent e) { setForeground(COLOR_OUTLINE.darker()); }
                public void mouseExited(MouseEvent e) { setForeground(COLOR_OUTLINE); }
            });
        }
    }

    // toaster handler
    static class Toaster {
        private static final int START_Y = 15;
        private static final int SPACER = 15;
        private static final ArrayList<ToasterBody> toasts = new ArrayList<>();
        private final static AtomicInteger OFFSET_Y = new AtomicInteger();
        private final JPanel parent;

        public Toaster(JPanel parent) { this.parent = parent; }
        public void error(String msg) { toast(msg, new Color(181, 59, 86)); }
        public void success(String msg) { toast(msg, new Color(33, 181, 83)); }
        public void warn(String msg) { toast(msg, new Color(181, 147, 10)); }

        private void toast(String msg, Color color) {
            ToasterBody toast = new ToasterBody(parent, msg, color, OFFSET_Y.get() + START_Y);
            OFFSET_Y.addAndGet(toast.height + SPACER);
            toasts.add(toast);
            new Thread(() -> {
                parent.add(toast);
                parent.repaint();
                try { Thread.sleep(4000); } catch (InterruptedException ignored) {}
                parent.remove(toast);
                OFFSET_Y.addAndGet(-toast.height - SPACER);
                parent.repaint();
            }).start();
        }
    }

    // toaster body UI
    static class ToasterBody extends JPanel {
        final int height;
        final String message;
        final Color color;

        public ToasterBody(JPanel parent, String msg, Color color, int y) {
            this.message = msg;
            this.color = color;
            this.height = 40;
            setOpaque(false);
            setBounds((parent.getWidth() - 300) / 2, y, 300, height);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), ROUNDNESS, ROUNDNESS);
            g2.setFont(FONT_GENERAL_UI);
            g2.setColor(Color.WHITE);
            g2.drawString(message, 20, 25);
        }
    }

    // success frame
    static class SuccessFrame extends JFrame {
        public SuccessFrame() {
            setTitle("Success");
            JLabel label = new JLabel("Login Successful!", SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 22));
            label.setForeground(Color.GREEN);
            add(label);
            setSize(300, 150);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    // error frame
    static class ErrorFrame extends JFrame {
        public ErrorFrame() {
            setTitle("Error");
            JLabel label = new JLabel("Incorrect username or password", SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            label.setForeground(Color.RED);
            add(label);
            setSize(350, 150);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
}