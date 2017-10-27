/*
 * FileLoader.java
 *
 * Created on 05 February 2005, 20:15
 */

/**
 *
 * @author  Pierre Cauchy
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;    
import java.io.*;

public class FileLoader extends JFrame{
    
    String [] fileNames;
    StringBuffer text;
    
    
    /** Creates a new instance of FileLoader */
    public FileLoader(String s)
    {
        fileNames = new String[1];
        fileNames[0] = s;
        
    }
    
    public FileLoader(File [] s)
    {
        fileNames = new String[s.length];
        for(int i=0;i<s.length;i++)
        {
           
            
            fileNames[i]=s[i].getAbsolutePath();
            
        }
        
        
    }
    
    
    //loads file, line by line, passes it to a StringBuffer checks for end of file
    public void loadFile()
    {
    
        try
        {
            
            text = new StringBuffer();
            for(int i=0;i<fileNames.length;i++)
            {
                FileReader fr = new FileReader(fileNames[i]);
                BufferedReader reader = new BufferedReader(fr);
            
                boolean endOfFile=false;
                while(!endOfFile)
                {
                    String tmp = reader.readLine();
                    if(tmp == null)
                    {
                        endOfFile=true;
                    }
                    else
                    {
                        tmp+="\r\n";
                        text.append(tmp);
                    }
                }
            }
        
            //System.out.println(text);

    
        
        }
        
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(this, "Error", "Could load file", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    //returns the latter as a string
    public String returnFile()
    {
        //System.out.println(text.toString());
        return text.toString();
    }
    
    
    
}
