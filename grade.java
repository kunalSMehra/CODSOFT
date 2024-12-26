// package one;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class grade {
    private static int numSubjects;
    private static JTextField[] gradeFields;
    private static JTextField[] maxMarksFields;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        JLabel numSubjectsLabel = new JLabel("Enter number of subjects:", SwingConstants.CENTER);
        numSubjectsLabel.setFont(new Font("Arial", Font.BOLD, 16)); 
        mainPanel.add(numSubjectsLabel);

        JTextField numSubjectsField = new JTextField();
        numSubjectsField.setMaximumSize(new Dimension(Integer.MAX_VALUE, numSubjectsField.getPreferredSize().height));
        numSubjectsField.setFont(new Font("Arial", Font.PLAIN, 14)); 
        mainPanel.add(numSubjectsField);

        JButton proceedButton = new JButton("Proceed");
        proceedButton.setFont(new Font("Arial", Font.BOLD, 14)); 
        mainPanel.add(proceedButton);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(480, 400)); 
        mainPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        mainPanel.add(buttonPanel);

        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16)); 
        resultLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); 
        mainPanel.add(resultLabel);

        frame.add(mainPanel);
        frame.setVisible(true);

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    numSubjects = Integer.parseInt(numSubjectsField.getText());
                    inputPanel.removeAll();
                    buttonPanel.removeAll(); 
                    gradeFields = new JTextField[numSubjects];
                    maxMarksFields = new JTextField[numSubjects];

                    for (int i = 0; i < numSubjects; i++) {
                        JPanel subjectPanel = new JPanel();
                        subjectPanel.setLayout(new GridLayout(2, 2, 10, 10));
                        
                        JLabel gradeLabel = new JLabel("Grade for subject " + (i + 1) + ":");
                        gradeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                        subjectPanel.add(gradeLabel);

                        gradeFields[i] = new JTextField();
                        gradeFields[i].setFont(new Font("Arial", Font.PLAIN, 14));
                        subjectPanel.add(gradeFields[i]);

                        JLabel maxMarksLabel = new JLabel("Max marks for subject " + (i + 1) + ":");
                        maxMarksLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                        subjectPanel.add(maxMarksLabel);

                        maxMarksFields[i] = new JTextField();
                        maxMarksFields[i].setFont(new Font("Arial", Font.PLAIN, 14));
                        subjectPanel.add(maxMarksFields[i]);

                        inputPanel.add(Box.createVerticalStrut(10)); 
                        inputPanel.add(subjectPanel);
                    }

                    JButton calculateButton = new JButton("Calculate");
                    calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
                    buttonPanel.add(calculateButton);

                    JButton resetButton = new JButton("Reset");
                    resetButton.setFont(new Font("Arial", Font.BOLD, 14));
                    buttonPanel.add(resetButton);

                    calculateButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                double[] grades = new double[numSubjects];
                                double[] maxMarks = new double[numSubjects];

                                for (int i = 0; i < numSubjects; i++) {
                                    grades[i] = Double.parseDouble(gradeFields[i].getText());
                                    maxMarks[i] = Double.parseDouble(maxMarksFields[i].getText());
                                }

                                double percentage = calculatePercentage(grades, maxMarks);
                                double cgpa = calculateCGPA(percentage);
                                double sgpa = calculateSGPA(grades, maxMarks);

                                resultLabel.setText("<html>Percentage: " + percentage + "%<br/>CGPA: " + String.format("%.2f", cgpa) + "<br/>SGPA: " + String.format("%.2f", sgpa) + "</html>");
                            } catch (NumberFormatException ex) {
                                resultLabel.setText("Please enter valid numbers for grades and max marks.");
                            }
                        }
                    });

                    resetButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            numSubjectsField.setText("");
                            inputPanel.removeAll();
                            resultLabel.setText("");
                            buttonPanel.removeAll();
                            frame.revalidate();
                            frame.repaint();
                        }
                    });

                    frame.revalidate();
                    frame.repaint();
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Please enter a valid number of subjects.");
                }
            }
        });
    }

    public static double calculatePercentage(double[] grades, double[] maxMarks) {
        double totalMarksObtained = 0;
        double totalMaxMarks = 0;
        for (int i = 0; i < grades.length; i++) {
            totalMarksObtained += grades[i];
            totalMaxMarks += maxMarks[i];
        }
        return (totalMarksObtained / totalMaxMarks) * 100;
    }

    public static double calculateCGPA(double percentage) {
        return percentage / 9.5;
    }

    public static double calculateSGPA(double[] grades, double[] maxMarks) {
        double totalPoints = 0;
        for (int i = 0; i < grades.length; i++) {
            totalPoints += (grades[i] / maxMarks[i]) * 10;
        }
        return totalPoints / grades.length;
    }
}
