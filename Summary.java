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
public class Summary  extends JFrame
{
    
    /** Creates a new instance of Summary */
    public Summary(int nstrt, int nstp, int min, int max,double ave,int l, double gc, double tm, double th)
    {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Sequence Summary");
        JTextArea jTextArea1 = new JTextArea();
        jTextArea1.setEditable(false);
        this.setBackground(Color.white);
        jTextArea1.setFont(new Font("Arial", 1, 12));
        jTextArea1.setText("SEQUENCE LENGTH: " +l +"\nNUMBER OF START CODONS : "+nstrt+"\nNUMBER OF STOP CODONS: "+nstp+"\nMINIMUM TRANSLATED SEQUENCE SIZE:"+min+"\nMAXIMUM TRANSLATED SEQUENCE SIZE:"+max+"\nAVERAGE TRANSLATED SEQUENCE SIZE:"+GFormat.fixDecimalPlaces(ave,1)+"\nGC CONTENT:"+GFormat.fixDecimalPlaces(gc,1)+"%"+"\nMELTING TEMPERATURE(Tm):"+GFormat.fixDecimalPlaces(tm,1)+"�C"+"\nHYBRIDISATION TEMPERATURE:"+GFormat.fixDecimalPlaces(th,1)+"�C");
        getContentPane().add(jTextArea1, java.awt.BorderLayout.CENTER);
        jTextArea1.setFont(new java.awt.Font("Courier New", 0, 12));
    }
    
}
