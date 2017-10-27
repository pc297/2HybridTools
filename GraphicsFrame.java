/*
 * GraphicsFrame.java
 *
 * Created on 05 February 2005, 20:16
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


public class GraphicsFrame extends JFrame
{
    private int[] mut;
    public int size;
    
    
    /** 
     * Creates a new instance of GraphicsFrame
     * start&stop codons, range, translation product size passed as params 
     **/
    public GraphicsFrame(int[] a) 
    {
        mut=a;
        size=mut.length;
        setResizable(false);
        this.setTitle("Mutation Distribution");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBackground(Color.WHITE);
        
        
        
       
    }
    
    
    //paint method overrides built in one, allowing graphics
    public void paint(Graphics g)
    {	
        //draws lines and text
        g.drawString("Graphical Overview",250,50);
        
        g.drawLine(150,100,550,100);
        g.drawLine(150,98,150,102);
        g.drawLine(550,98,550,102);
        
        g.drawString("Mutations",50,100);
        
        
        g.drawString("1",145,120);
        g.drawString(Integer.toString(mut.length),550,120);
        g.setColor(Color.green);
        g.drawLine(50,120,50,130);
        g.setColor(Color.blue);
        g.drawLine(50,140,50,150);
        g.setColor(Color.magenta);
        g.drawLine(50,160,50,170);
        g.setColor(Color.red);
        g.drawLine(50,180,50,190);
        g.setColor(Color.black);
        g.drawString("1-5",60,130);
        g.drawString("6-10",60,150);
        g.drawString("11-15",60,170);
        g.drawString(">15",60,190);
        
        //draws vert line according to relative position of start/stop in DNA seq
        boolean up=true;
        for(int i=0;i<mut.length;i++)
        {
            
            if(mut[i]>=1)
            {
                g.setColor(Color.green);
            }
            if(mut[i]>=6)
            {
                g.setColor(Color.blue);
            }
            if(mut[i]>=11)
            {
                g.setColor(Color.magenta);
            }
            if(mut[i]>=16)
            {
                g.setColor(Color.red);
            }
            
            if(mut[i]>=1&&up==false)
            {
                
            
            
            g.drawLine((int)(150 + 400.0*i/size), 101,(int)(150 + 400.0*i/size),  110);
                        
            }
            
            if(mut[i]>=1&&up==true)
            {
                
            
            
            g.drawLine((int)(150 + 400.0*i/size), 90,(int)(150 + 400.0*i/size),  99);
            }
            if(mut[i]>=1)
            {
                up=!up;
            }
            
        }
        
        
        
        
    }
    
    
    
}
