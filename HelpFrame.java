/*
 * HelpFrame.java
 *
 * Created on 09 February 2005, 01:00
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

public class HelpFrame extends JFrame
{
    
    /** Creates a new instance of HelpFrame */
    public HelpFrame()
    {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Help");
        JTextArea jTextArea1 = new JTextArea();
        jTextArea1.setEditable(false);
        jTextArea1.setBackground(Color.WHITE);
        jTextArea1.setFont(new Font("Arial", 1, 12));
        jTextArea1.setText("\n    QUICK GUIDELINES FOR CORRECT USE OF TWO-HYBRID TOOLS\n\n     Two-Hybrid Tools allows one to extract cDNA sequences from Two Hybrid\n      ABI files.To Load a file, open the File menu and choose \"Open\" or press Ctrl+O\n     A set of sequences is searched for a vector upon loading. Press Load\n     Vector to use a vector other than the default one. Overlapping of\n     larger N and C terminus sequences is possible by checking the Overlap box\n     and a 5' vector can be loaded in a similar way. A reference model can\n     be applied for further alignment and mutation collection and plotting (Graphics)\n     Then simply hit the \"Translate\" button to process the desired sequence.\n     A graphical overview of the mutations plotted against a loaded model can be\n     obtained for the translated sequence by pressing the \"Graphics\" button.\n     Here are a few tips to use this program correctly and efficiently:\n\n             * Sequence code, reading frames, translation direction and translation can\n                only be set once a valid file has been loaded\n\n             * If the user chooses to modify the sequence code, reading frame, or\n                or direction once the sequence has been translated by pressing the\n                \"Translate\" button\n\n              * The sequence must be translated once again in order to obtain a\n                 graphical overview if different settings are entered.\n                 ");
        getContentPane().add(jTextArea1, java.awt.BorderLayout.CENTER);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 12));
    }
    
}
