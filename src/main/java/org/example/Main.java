package org.example;
/** package org.example;
// Java program to create a blank text
// field of definite number of columns.
import java.awt.event.*;
import javax.swing.*;
class text extends JFrame implements ActionListener {
    static JTextField textField;
    static JFrame frame;
    static JButton button;
    static JLabel label;// label to display text
    // default constructor
    text() {}

    MAIN
    public static void main(String[] args)
    {
        // create a new frame to store text field and button
        frame = new JFrame("Prime");

        // create a label to display text
        label = new JLabel("nothing entered");

        // create a new button
        button = new JButton("Start Producer");

        // create a object of the text class
        text te = new text();

        // addActionListener to button
        button.addActionListener(te);

        // create a object of JTextField with 16 columns
        textField = new JTextField(16);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        // add buttons and textfield to panel
        p.add(textField);
        p.add(button);
        p.add(label);

        // add panel to frame
        frame.add(p);

        // set the size of frame
        frame.setSize(1000/2, 1300/2);

        frame.show();
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("submit")) {
            // set the text of the label to the text of the field
            label.setText(textField.getText());


            // set the text of field to blank
            textField.setText(" ");
        }
    }
}
**/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void printText(String n, String bufferSize, String outputFileName){
        System.out.println(n+" "+bufferSize+" "+outputFileName);
    }
    public static void main(String args[]) {

        JFrame frame = new JFrame("");

        Font labelFont = new Font("sans serif", Font.BOLD, 13);
        Font buttonTextFieldFont = new Font("sans serif", Font.PLAIN, 11);

        JTextField nTextField =  new JTextField(200);
        JTextField bufferSizeTextField =  new JTextField(200);
        JTextField outputFileTextField =  new JTextField(200);
        JLabel nLabel = new JLabel("N");
        JLabel bufferSizeLabel = new JLabel("Buffer Size");
        JLabel outputFileLabel = new JLabel("Output File");
        JButton startProducerButton = new JButton("Start Producer");

        startProducerButton.setFont(buttonTextFieldFont);
        nTextField.setFont(buttonTextFieldFont);
        bufferSizeTextField.setFont(buttonTextFieldFont);
        outputFileTextField.setFont(buttonTextFieldFont);
        nLabel.setFont(labelFont);
        bufferSizeLabel.setFont(labelFont);
        outputFileLabel.setFont(labelFont);

        startProducerButton.setBounds(40,180,140,20);
        nTextField.setBounds(40,30,200,30);
        bufferSizeTextField.setBounds(40,80,200,30);
        outputFileTextField.setBounds(40,130,200,30);
        nLabel.setBounds(280,30,200,30);
        bufferSizeLabel.setBounds(280,80,200,30);
        outputFileLabel.setBounds(280,130,200,30);

        frame.add(startProducerButton);
        frame.add(nTextField);
        frame.add(bufferSizeTextField);
        frame.add(outputFileTextField);
        frame.add(nLabel);
        frame.add(bufferSizeLabel);
        frame.add(outputFileLabel);
        startProducerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                if (action.equals("Start Producer")){
                    Main.printText(nTextField.getText(),bufferSizeTextField.getText(),outputFileTextField.getText());
                    //IF WE WANT TO FLUSH AFTER BUTTON CLICK V
                    // nTextField.setText("");
                    // bufferSizeTextField.setText("");
                    // outputFileTextField.setText("");
                }
            }
        });

        frame.setSize(400,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}