package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

class semaphore {
    protected int value ;
    protected semaphore() { value = 1 ; }
    public synchronized void acquireLock() {
        if(value==1){//If available
            value=0; //mark as not available, proceed to execute the critical section
            return;
        }
        if(value == 0){//If not available
            try {
                wait();//Wait
                value=1;//After the wait, set as available
            } catch( InterruptedException e ) {
                System.out.println(e);
            }
        }
    }
    public synchronized void releaseLock() {
        if (value == 0){
            value=1;
            notify();
        }
    }
}
class Globals {
    public static semaphore sm;
    public static Queue<Integer> buffer;

    public static Integer bufferSize;

    public static Integer n;

    public static String outputFileName;

    public static FileWriter outputFileWriter;

    public static Boolean lastPrimeProduction;
}
class Producer extends Thread{
    public boolean checkIfPrime(int input) {
        boolean isPrime = true;
        if(input <= 1) {isPrime = false; return isPrime;}
        else {
            for (int i = 2; i<= input/2; i++) {
                if ((input % i) == 0) { isPrime = false; break;}
            }
            return isPrime;
        }
    }
    protected Integer bufferCount;
    public Producer(){
        Globals.sm.acquireLock();
        bufferCount=0;
        for(int i=0;i<=Globals.n;i++){
            if(bufferCount==Globals.bufferSize){
                Globals.sm.releaseLock();
                Globals.sm.acquireLock();
                bufferCount=0;
            }
            if (checkIfPrime(i)){
                Globals.buffer.add(i);
                bufferCount++;

            }
        }
        Globals.sm.releaseLock();
    }
    // semaphore.acquireLock()
    // bufferCount=0
    // for i= 0 -> N(Max number)
            //if bufferCount == BufferSize
            //   semaphore.releaseLock()    ---> available, consumer acquire the lock
            //   semaphore.acquireLock()
            //   BufferCount = 0
    //      if i is prime then add to buffer, bufferCount++
}
class Consumer extends Thread{

    public Consumer() throws IOException {
        Globals.sm.acquireLock();
        for(int k=0;k<Globals.n/Globals.bufferSize;k++){
            for (int i = 0; i < Globals.bufferSize; i++) {
                if (Globals.buffer.isEmpty()) { //If buffer elements are less than buffer size, break because it is unnecessary
                    break;
                }
                // Append to file
                Globals.outputFileWriter.write("\"" + Integer.toString(Globals.buffer.remove()) + "\", ");//appends the string to the file

            }
            if (Globals.buffer.isEmpty()) { //Making sure to release only if buffer is empty
                Globals.sm.releaseLock();
                Globals.sm.acquireLock();
            }
        }
    }
    // acquire lock
    // for i -> bufferSize:
    // numbers didn't fill the buffer to its max [1,2,3]
    //      if(buffer is empty){ // if buffer elements are less than buffer size, break because it is unnecessary
    //          break;
    //      }
    //
    // if(buffer is empty): release lock //Making sure to release only if buffer is empty
}
    // semaphore;
    // buffer [buffer_size]
    // Producer.start(semaphore,&buffer);
    // Consumer.start(semaphore,&buffer);
    //
    //

public class Main {

    public static void printText(String n, String bufferSize, String outputFileName){
        System.out.println(n+" "+bufferSize+" "+outputFileName);
    }
    public static void createOutputFile(String outputFileTextFieldValue){
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
            Globals.outputFileName=outputFileTextFieldValue;
        } catch (IOException ioe) {
            System.out.println("An error occurred.");
            ioe.printStackTrace();
        }
    }


    public static void onClick(String action, String nTextFieldValue,String bufferSizeTextFieldValue,String outputFileTextFieldValue) throws IOException, InterruptedException {
        if (action.equals("Start Producer")){
            Main.printText(nTextFieldValue,bufferSizeTextFieldValue,outputFileTextFieldValue);
            createOutputFile(outputFileTextFieldValue);
            semaphore sm = new semaphore();
            Queue<Integer> buffer = new LinkedList<Integer>();
            //if(buffer.isEmpty())

            Globals.sm = sm;
            Globals.buffer = buffer;
            Globals.bufferSize= Integer.parseInt(bufferSizeTextFieldValue);
            Globals.n = Integer.parseInt(nTextFieldValue);
            Globals.outputFileWriter = new FileWriter(Globals.outputFileName,true);
            Globals.lastPrimeProduction=false;

            Thread producer = new Producer();
            Thread consumer = new Consumer();
            producer.start();
            consumer.start();
            producer.join();
            consumer.join();
            Globals.outputFileWriter.close();
        }
    }
    public static void main(String args[]) throws InterruptedException {

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
                try {
                    Main.onClick(action,nTextField.getText(),bufferSizeTextField.getText(),outputFileTextField.getText());
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        frame.setSize(540,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}