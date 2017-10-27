/*
 * FileSaver.java
 *
 * Created on 06 February 2005, 03:08
 */

/**
 *
 * @author  Pierre Cauchy
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;    
import java.io.*;

public class FileSaver extends JFrame{
    
    /** Creates a new instance of FileSaver */
    public FileSaver(String s, String directory, String fileName) 
    {
        try
        {
            
        
        
            File outFile = new File(directory,fileName);
            FileWriter fw = new FileWriter(outFile);
            PrintWriter writer = new PrintWriter(fw);
            writer.print(s);
            //System.out.println(s);
            writer.close();
        }
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(this, "Error", "Could not write to file", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public FileSaver(String s, String fileName) 
    {
        try
        {
            
        
            
            File outFile = new File(fileName);
            FileWriter fw = new FileWriter(outFile);
            PrintWriter writer = new PrintWriter(fw);
            writer.print(s);
            //System.out.println(s);
            writer.close();
        }
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(this, "Error", "Could not write to file", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
