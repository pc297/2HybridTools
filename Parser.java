/*
 * Parser.java
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

public class Parser extends JFrame
{
    
    //instance wariables. text is text from file, seq is instance for raw sequence
    //displaySeq will contain numbers and formatting
    public String text = "";
    public String seq="";
    public String displaySeq ="";
    boolean isProtein=false;
    
    
    
    
    /** Creates a new instance of Parser */
    public Parser(String s) 
    {
        text =s;
    }
    
   
    //returns sequence to display in JTextarea, with numbers
    public String getDisplayableSequence()
    {            
        
        String gen="";
        displaySeq = text.substring(text.indexOf("ABI\n"+4));
        displaySeq = displaySeq.substring(0,displaySeq.indexOf("\n>")-1);
        
        displaySeq = displaySeq.toUpperCase();
        return displaySeq;
    }
    
    //gets number of rows for the latter
    public int getNumRows()
    {
        int numRows=0;
        for(int i=0;i<displaySeq.length();i++)
        {
            if(displaySeq.charAt(i)=='\n')
            {
                numRows++;
            }
        }
        return numRows;
    }
    
    //gets description
    public String getDescription()
    {
        String def = text.substring(text.indexOf(">"),text.indexOf(".")-1);
        return def;
        
        
    }
    
    
    
    
    
    //gets raw DNA sequence, purely a string of bases.
    public String getSequenceString()
    
    {
        
        //determines if there are comments between ORIGIN and sequence
        //String tmp = text.substring(text.indexOf("ABI")+5);
        String tmp = text.substring(text.indexOf("\n",text.indexOf(">")));
        
        
        for(int i=0;i<tmp.length();i++)
        {
            if(tmp.charAt(i)=='A'||tmp.charAt(i)=='T'||tmp.charAt(i)=='G'||tmp.charAt(i)=='C'||tmp.charAt(i)=='N'||tmp.charAt(i)=='n'||tmp.charAt(i)=='a'||tmp.charAt(i)=='t'||tmp.charAt(i)=='g'||tmp.charAt(i)=='c')
            {
                seq+=tmp.charAt(i);
            }
            if(tmp.substring(i,i+1).equals(null))
            {
                break;
            }
        
                
                
        }
        //System.out.println(seq);
        seq=seq.toUpperCase();
        return seq;
        
    }
    public String getDef()
    {
        return text.substring(text.indexOf(">"), text.indexOf("\n", text.indexOf(">")));
    }
    public String getModelSequenceString()
    
    {
        
        //determines if there are comments between ORIGIN and sequence
        String tmp = text.substring(text.indexOf("\n", text.indexOf(">"))+1);
        tmp = tmp.toUpperCase();
        if((tmp.indexOf('E')!=-1)||(tmp.indexOf('D')!=-1)||(tmp.indexOf('R')!=-1)||(tmp.indexOf('N')!=-1)||(tmp.indexOf('Q')!=-1)||(tmp.indexOf('H')!=-1)||(tmp.indexOf('I')!=-1)||(tmp.indexOf('L')!=-1)||(tmp.indexOf('K')!=-1)||(tmp.indexOf('M')!=-1)||(tmp.indexOf('F')!=-1)||(tmp.indexOf('P')!=-1)||(tmp.indexOf('S')!=-1)||(tmp.indexOf('W')!=-1)||(tmp.indexOf('Y')!=-1)||(tmp.indexOf('V')!=-1))
        {
            isProtein = true;
        }
        
        if(!isProtein)
        {
        for(int i=0;i<tmp.length();i++)
        {
            if(tmp.charAt(i)=='A'||tmp.charAt(i)=='T'||tmp.charAt(i)=='G'||tmp.charAt(i)=='C'||tmp.charAt(i)=='N'||tmp.charAt(i)=='n'||tmp.charAt(i)=='a'||tmp.charAt(i)=='t'||tmp.charAt(i)=='g'||tmp.charAt(i)=='c')
            {
                seq+=tmp.charAt(i);
            }
            if(tmp.substring(i,i+1).equals(null))
            {
                break;
            }
        
                
                
        }
        }
        if(isProtein)
        {
            for(int i=0;i<tmp.length();i++)
        {
            if(tmp.charAt(i)=='E'||tmp.charAt(i)=='D'||tmp.charAt(i)=='A'||tmp.charAt(i)=='R'||tmp.charAt(i)=='N'||tmp.charAt(i)=='C'||tmp.charAt(i)=='Q'||tmp.charAt(i)=='G'||tmp.charAt(i)=='H'||tmp.charAt(i)=='I'||tmp.charAt(i)=='L'||tmp.charAt(i)=='K'||tmp.charAt(i)=='M'||tmp.charAt(i)=='F'||tmp.charAt(i)=='P'||tmp.charAt(i)=='S'||tmp.charAt(i)=='T'||tmp.charAt(i)=='W'||tmp.charAt(i)=='Y'||tmp.charAt(i)=='V')
            {
                seq+=tmp.charAt(i);
            }
            if(tmp.substring(i,i+1).equals(null))
            {
                break;
            }
        
                
                
        }
        }
        //System.out.println(seq);
        
        return seq;
        
    }
    public boolean isProtein()
    {
        return isProtein;
    }
    
    
    //gets the length for the latter. Hopefully matches the one in comments!
    public int getSequenceLength()
    {
        return seq.length();
    }
    
}
