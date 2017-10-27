/*
 * Summary.java
 *
 * Created on 11 February 2005, 04:19
 */

/**
 *
 * @author  Pierre Cauchy
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;    
import java.io.*;
import java.awt.Graphics.*;
public class AnalysisFrame  extends JFrame
{
    
    /** Creates a new instance of Summary */
    public AnalysisFrame(String s)
    {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Analysis Report");
        JTextArea jTextArea1 = new JTextArea();
        jTextArea1.setEditable(false);
        this.setBackground(Color.white);
        jTextArea1.setFont(new Font("Arial", 1, 12));
        jTextArea1.setText(s);
        getContentPane().add(jTextArea1, java.awt.BorderLayout.CENTER);
        jTextArea1.setFont(new java.awt.Font("Courier New", 0, 12));
    }
    
}
