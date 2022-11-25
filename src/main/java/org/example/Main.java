package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Main {
    public static void printText(String n, String bufferSize, String outputFileName){
        System.out.println(n+" "+bufferSize+" "+outputFileName);
    }
    public static void onClick(String action, String nTextFieldValue,String bufferSizeTextFieldValue,String outputFileTextFieldValue){
        if (action.equals("Start Producer")){
            Main.printText(nTextFieldValue,bufferSizeTextFieldValue,outputFileTextFieldValue);
            //IF WE WANT TO FLUSH TEXT FIELDS AFTER BUTTON CLICK V
            // nTextField.setText("");
            // bufferSizeTextField.setText("");
            // outputFileTextField.setText("");
            try {
                if(!outputFileTextFieldValue.contains(".txt")){
                    outputFileTextFieldValue+=".txt";
                }
                File outputFile = new File(outputFileTextFieldValue);
                if (outputFile.createNewFile()) {
                    System.out.println("File created: " + outputFile.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException ioe) {
                System.out.println("An error occurred.");
                ioe.printStackTrace();
            }
        }
    }
    public static void main(String args[]) {

        JFrame frame = new JFrame("");
        //Fonts
        Font labelFont = new Font("sans serif", Font.BOLD, 13);
        Font buttonTextFieldFont = new Font("sans serif", Font.PLAIN, 11);
        Font lowerPartTextFont = new Font("sans serif", Font.BOLD, 11);

        // Components
        JTextField nTextField =  new JTextField(200);
        JTextField bufferSizeTextField =  new JTextField(200);
        JTextField outputFileTextField =  new JTextField(200);
        JLabel nLabel = new JLabel("N");
        JLabel bufferSizeLabel = new JLabel("Buffer Size");
        JLabel outputFileLabel = new JLabel("Output File");
        JButton startProducerButton = new JButton("Start Producer");
        JLabel largestPrimeNumberTitle = new JLabel("The largest prime number");
        JLabel numberOfElementsGeneratedTitle = new JLabel("No. of elements (prime number) generated");
        JLabel timeElapsedTitle = new JLabel("Time elapsed since the start of processing");
        JLabel largestPrimeNumber = new JLabel("1234567");
        JLabel numberOfElementsGenerated = new JLabel("123456");
        JLabel timeElapsed = new JLabel("12345678901");

        // Fonts setting
        startProducerButton.setFont(buttonTextFieldFont);
        nTextField.setFont(buttonTextFieldFont);
        bufferSizeTextField.setFont(buttonTextFieldFont);
        outputFileTextField.setFont(buttonTextFieldFont);
        nLabel.setFont(labelFont);
        bufferSizeLabel.setFont(labelFont);
        outputFileLabel.setFont(labelFont);
        largestPrimeNumberTitle.setFont(lowerPartTextFont);
        numberOfElementsGeneratedTitle.setFont(lowerPartTextFont);
        timeElapsedTitle.setFont(lowerPartTextFont);
        largestPrimeNumber.setFont(lowerPartTextFont);
        numberOfElementsGenerated.setFont(lowerPartTextFont);
        timeElapsed.setFont(lowerPartTextFont);

        // Setting labels color
        Color orangeBrown = new Color(205,122,15);
        largestPrimeNumberTitle.setForeground(orangeBrown);
        numberOfElementsGeneratedTitle.setForeground(orangeBrown);
        timeElapsedTitle.setForeground(orangeBrown);

        // Positioning of components
        startProducerButton.setBounds(40,180,140,20);
        nTextField.setBounds(40,30,200,30);
        bufferSizeTextField.setBounds(40,80,200,30);
        outputFileTextField.setBounds(40,130,200,30);
        nLabel.setBounds(280,30,200,30);
        bufferSizeLabel.setBounds(280,80,200,30);
        outputFileLabel.setBounds(280,130,200,30);
        largestPrimeNumberTitle.setBounds(40,270,200,30);
        numberOfElementsGeneratedTitle.setBounds(40,320,400,30);
        timeElapsedTitle.setBounds(40,370,400,30);
        largestPrimeNumber.setBounds(370,270,200,30);
        numberOfElementsGenerated.setBounds(370,320,400,30);
        timeElapsed.setBounds(370,370,400,30);

        // Adding components to the frame
        frame.add(startProducerButton);
        frame.add(nTextField);
        frame.add(bufferSizeTextField);
        frame.add(outputFileTextField);
        frame.add(nLabel);
        frame.add(bufferSizeLabel);
        frame.add(outputFileLabel);
        frame.add(largestPrimeNumberTitle);
        frame.add(numberOfElementsGeneratedTitle);
        frame.add(timeElapsedTitle);
        frame.add(largestPrimeNumber);
        frame.add(numberOfElementsGenerated);
        frame.add(timeElapsed);

        // Action listener to the button and custom actionPerformed for our case
        startProducerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                Main.onClick(action,nTextField.getText(),bufferSizeTextField.getText(),outputFileTextField.getText());

            }
        });

        frame.setSize(540,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}