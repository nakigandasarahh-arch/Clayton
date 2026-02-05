
package app.studentregistrationapp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.regex.Pattern;

public class StudentRegistrationApp extends JFrame {
    
    // GUI Components
    private JTextField txtFirst, txtLast, txtEmail, txtConfirmEmail;
    private JPasswordField txtPass, txtConfirmPass;
    private JComboBox<Integer> cbYear, cbDay;
    private JComboBox<String> cbMonth;
    private JRadioButton rbMale, rbFemale;
    private JRadioButton rbCivil, rbCSE, rbElec, rbEC, rbMech;
    private JTextArea outputArea;
    private static int studentCounter = 1;

    public StudentRegistrationApp() {
        setTitle("New Student Registration Form");
        setSize(850, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Header ---
        JLabel lblHeader = new JLabel("New Student Registration Form", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblHeader, BorderLayout.NORTH);

        // --- Main Form Panel ---
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
       

        // Form Fields Initialization
        txtFirst = new JTextField(15);
        txtLast = new JTextField(15);
        txtEmail = new JTextField(15);
        txtConfirmEmail = new JTextField(15);
        txtPass = new JPasswordField(15);
        txtConfirmPass = new JPasswordField(15);

        // DOB Components
        Integer[] years = new Integer[101];
        for (int i = 0; i <= 100; i++) years[i] = 1950 + i;
        cbYear = new JComboBox<>(years);
        cbMonth = new JComboBox<>(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        cbDay = new JComboBox<>();
        updateDays(); // Initialize days

        // Radio Buttons
        rbMale = new JRadioButton("Male"); rbFemale = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbMale); genderGroup.add(rbFemale);

        rbCivil = new JRadioButton("Civil"); rbCSE = new JRadioButton("Computer Science and Engineering");
        rbElec = new JRadioButton("Electrical"); rbEC = new JRadioButton("Electronics and Communication");
        rbMech = new JRadioButton("Mechanical");
        ButtonGroup deptGroup = new ButtonGroup();
        deptGroup.add(rbCivil); deptGroup.add(rbCSE); deptGroup.add(rbElec); deptGroup.add(rbEC); deptGroup.add(rbMech);

        // Adding to Layout (Left Side)
        addLabelAndComp(leftPanel, "Student First Name:", txtFirst, gbc, 0);
        addLabelAndComp(leftPanel, "Student Last Name:", txtLast, gbc, 1);
        addLabelAndComp(leftPanel, "Email Address:", txtEmail, gbc, 2);
        addLabelAndComp(leftPanel, "Confirm Email:", txtConfirmEmail, gbc, 3);
        addLabelAndComp(leftPanel, "Password:", txtPass, gbc, 4);
        addLabelAndComp(leftPanel, "Confirm Password:", txtConfirmPass, gbc, 5);

        // DOB Row
        JPanel dobPanel = new JPanel();
        dobPanel.add(cbYear); dobPanel.add(cbMonth); dobPanel.add(cbDay);
        addLabelAndComp(leftPanel, "Date of Birth:", dobPanel, gbc, 6);

        // Gender & Dept
        JPanel genderPanel = new JPanel(); genderPanel.add(rbMale); genderPanel.add(rbFemale);
        addLabelAndComp(leftPanel, "Gender:", genderPanel, gbc, 7);

        JPanel deptPanel = new JPanel(new GridLayout(5, 1));
        deptPanel.add(rbCivil); deptPanel.add(rbCSE); deptPanel.add(rbElec); deptPanel.add(rbEC); deptPanel.add(rbMech);
        addLabelAndComp(leftPanel, "Department:", deptPanel, gbc, 8);

        // --- Right Side (Output) ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Your Data is Below:"), BorderLayout.NORTH);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        rightPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        add(mainPanel, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel btnPanel = new JPanel();
        JButton btnSubmit = new JButton("Submit");
        JButton btnCancel = new JButton("Cancel");
        btnPanel.add(btnSubmit); btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);

        // --- Listeners ---
        cbYear.addActionListener(e -> updateDays());
        cbMonth.addActionListener(e -> updateDays());
        
        btnSubmit.addActionListener(e -> validateAndSubmit());
        btnCancel.addActionListener(e -> clearForm());

        setVisible(true);
    }

    private void addLabelAndComp(JPanel p, String lbl, JComponent c, GridBagConstraints gbc, int y) {
        gbc.gridx = 0; gbc.gridy = y; p.add(new JLabel(lbl), gbc);
        gbc.gridx = 1; p.add(c, gbc);
    }

    private void updateDays() {
        int year = (int) cbYear.getSelectedItem();
        int month = cbMonth.getSelectedIndex();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        cbDay.removeAllItems();
        for (int i = 1; i <= maxDays; i++) cbDay.addItem(i);
    }

    private void validateAndSubmit() {
        StringBuilder errors = new StringBuilder();
        String fName = txtFirst.getText().trim();
        String lName = txtLast.getText().trim();
        String email = txtEmail.getText().trim();
        String pass = new String(txtPass.getPassword());

        // Basic Validations
        if (fName.isEmpty() || lName.isEmpty()) errors.append("- Names are required\n");
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) errors.append("- Invalid Email\n");
        if (!email.equals(txtConfirmEmail.getText().trim())) errors.append("- Emails do not match\n");
        
        if (pass.length() < 8 || pass.length() > 20 || !pass.matches(".*[a-zA-Z].*") || !pass.matches(".*\\d.*")) {
            errors.append("- Password must be 8-20 chars with letters and digits\n");
        }
        if (!pass.equals(new String(txtConfirmPass.getPassword()))) errors.append("- Passwords do not match\n");

        // Age Check
        LocalDate birthDate = LocalDate.of((int)cbYear.getSelectedItem(), cbMonth.getSelectedIndex()+1, (int)cbDay.getSelectedItem());
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 16 || age > 60) errors.append("- Age must be 16-60\n");

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, errors.toString(), "Validation Errors", JOptionPane.ERROR_MESSAGE);
        } else {
            processSuccess(fName, lName, email, birthDate);
        }
    }

    private void processSuccess(String fName, String lName, String email, LocalDate dob) {
        String gender = rbMale.isSelected() ? "M" : "F";
        String dept = "";
        if (rbCivil.isSelected()) dept = "Civil";
        else if (rbCSE.isSelected()) dept = "CSE";
        else if (rbElec.isSelected()) dept = "Electrical";
        else if (rbEC.isSelected()) dept = "E&C";
        else if (rbMech.isSelected()) dept = "Mechanical";

        String id = String.format("%d-%05d", LocalDate.now().getYear(), studentCounter++);
        String record = String.format("ID: %s | %s %s | %s | %s | %s | %s", id, lName, fName, gender, dept, dob, email);
        
        outputArea.setText(record);

        try (FileWriter fw = new FileWriter("students.csv", true)) {
            fw.write(record + "\n");
            JOptionPane.showMessageDialog(this, "Saved to students.csv");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file");
        }
    }

    private void clearForm() {
        txtFirst.setText(""); txtLast.setText(""); txtEmail.setText("");
        txtConfirmEmail.setText(""); txtPass.setText(""); txtConfirmPass.setText("");
        outputArea.setText("");
    }

    public static void main(String[] args) {
        new StudentRegistrationApp();
    }
}

