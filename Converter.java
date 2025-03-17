import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Converter extends JFrame {
    private JComboBox<String> conversionOptions;
    private JButton nextButton;
    private JLabel inputLabel, outputLabel, titleLabel;
    private JTextField inputField, outputField;
    private JButton convertButton;
    private String selectedConversion;
    public Converter() {
        setTitle("Temperature Conversion");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());
        JPanel fs = new JPanel();
        fs.setLayout(new GridLayout(3, 1, 10, 10));
        fs.setBorder(new EmptyBorder(10, 10, 10, 10));
        String[] options = {
            "Celsius to Fahrenheit",
            "Celsius to Kelvin",
            "Kelvin to Celsius",
            "Kelvin to Fahrenheit",
            "Fahrenheit to Celsius",
            "Fahrenheit to Kelvin"
        };
        conversionOptions = new JComboBox<>(options);
        nextButton = new JButton("Next");
        fs.add(new JLabel("Select Conversion Option:"));
        fs.add(conversionOptions);
        fs.add(nextButton);
        JPanel ss = new JPanel();
        ss.setLayout(new GridLayout(5, 1, 10, 10));
        ss.setBorder(new EmptyBorder(10, 10, 10, 10));
        titleLabel = new JLabel("Temperature Conversion");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        inputLabel = new JLabel("Enter Temperature:");
        outputLabel = new JLabel("Converted Temperature:");
        inputField = new JTextField();
        outputField = new JTextField();
        outputField.setEditable(false);
        convertButton = new JButton("Convert");
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        JPanel outputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        outputPanel.add(outputLabel);
        outputPanel.add(outputField);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(convertButton);
        ss.add(titleLabel);
        ss.add(inputPanel);
        ss.add(outputPanel);
        ss.add(new JLabel());
        ss.add(buttonPanel);
        add(fs, "First Screen");
        add(ss, "Second Screen");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedConversion = (String) conversionOptions.getSelectedItem();
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                cardLayout.show(getContentPane(), "Second Screen");
            }
        });
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double temp = Double.parseDouble(inputField.getText());
                    double convertedTemp = convertTemperature(temp, selectedConversion);
                    outputField.setText(String.valueOf(convertedTemp));
                    saveConversionToFile(temp, convertedTemp, selectedConversion);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number");
                }
            }
        });
    }
    private double convertTemperature(double temp, String conversionType) {
        switch (conversionType) {
            case "Celsius to Fahrenheit":
                return celsiusToFahrenheit(temp);
            case "Celsius to Kelvin":
                return celsiusToKelvin(temp);
            case "Kelvin to Celsius":
                return kelvinToCelsius(temp);
            case "Kelvin to Fahrenheit":
                return kelvinToFahrenheit(temp);
            case "Fahrenheit to Celsius":
                return fahrenheitToCelsius(temp);
            case "Fahrenheit to Kelvin":
                return fahrenheitToKelvin(temp);
            default:
                throw new IllegalArgumentException("Invalid conversion type");
        }
    }

    private void saveConversionToFile(double temp, double convertedTemp, String conversionType) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("conversion_history.txt", true))) {
            writer.write(String.format("%s: %.2f -> %.2f%n", conversionType, temp, convertedTemp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    private double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    private double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    private double kelvinToFahrenheit(double kelvin) {
        return (kelvin - 273.15) * 9 / 5 + 32;
    }

    private double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    private double fahrenheitToKelvin(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9 + 273.15;
    }

    public static void main(String[] args) {
     new Converter().setVisible(true);
    }
}
