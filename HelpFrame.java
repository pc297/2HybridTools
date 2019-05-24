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
        //this.setSize(800, 600);
        //this.getContentPane().setSize(800,600);
        JTextArea jTextArea1 = new JTextArea();
        jTextArea1.setSize(800, 600);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBackground(Color.WHITE);
        jTextArea1.setFont(new Font("Arial", 1, 12));
        jTextArea1.setText("======================================================================================================================\n" +
"2HybridTools, a handy software to facilitate clone identification and mutation mapping from yeast two-hybrid screening\n" +
"======================================================================================================================\n" +
"\n" +
"\n" +
"\n" +
"Introduction\n" +
"------------\n" +
"\n" +
"2HybridTools is a GUI-based tool dedicated to yeast two-hybrid (Y2H) and reverse two-hybrid (RY2H) analysis. It uses cDNA sequences from Y2H FASTA files as input. It allows standard Y2H and reverse two-hybrid (RY2H) analysis. It performs translation of Y2H/RY2H sequencing products, following trimming of vector and low-quality sequences, ensuring correct reading frame usage for built-in Blast! or external protein identification of clones. 2HybridTools further allows reconstruction of sequencing products from both ends by performing local pairwise Smith-Waterman alignment. Finally, for RY2H analyses, TwoHybridTools provides detailed mutation reports and heatmaps alongside the bait of interest.\n" +
"\n" +
"Installation\n" +
"------------\n" +
"\n" +
"2HybridTools requires Java 1.5. As a portable Java application, it can be installed anywhere on a hard drive, provided that BioJava 1.5 (which is distributed with 2HybridTools) is installed in the system classpath (i.e. the lib directory or in the lib/ext directory of the Java runtime environment (JRE) virtual machine folder). Alternatively, BioJava is available at https://biojava.org/. \n" +
"\n" +
"Usage\n" +
"-----\n" +
"\n" +
"2HybridTools can be started by double-clicking the file \"2HybridTools.jar\" or by entering the following command:\n" +
"\n" +
"java [-Xmx1536m] -jar 2HybridTools.jar [-Djava.library.path <path_to_biojava, default:\"lib/\" in 2HybridTools folder]\n" +
"\n" +
"To load a file, open the File menu and choose \"Open\" or press Ctrl+O.\n" +
"\n" +
"A set of sequences is searched for a vector upon loading. Press \"Load Vector\" to use a DNA or protein tag sequence other than the default ones (STHAS for 5' and DPAFL for 3'). \n" +
"\n" +
"Overlapping of larger N and C terminus sequences is possible by checking the \"Overlap\" checkbox, and a 3' vector can be loaded in a similar way.\n" +
"\n" +
"Automatic ORF mapping can optionally be performed by pressing the \"Find ORFs\" button, which will identify and set the longest ORF for each sequence.\n" +
"\n" +
"To enable RY2H mode, a reference model can loaded as a DNA or protein FASTA file, or alternatively by inputting a RefSeq DNA or protein accession number. Any loaded model will be applied for further alignment and mutation collection and plotting (\"Graph\" button)\n" +
"\n" +
"To start the analysis, simply hit the \"Go\" button to process the desired sequence. \n" +
"\n" +
"A graphical overview of the mutations plotted against a loaded model can be obtained for the translated sequence by pressing the \"Graph\" button. A detailed summary of mutations per residue can be obtained by pressing the \"Summary\" button once the analysis is complete. Detailed mutation summary reports can be saved using the \"Save\" file menu from the summary window.\n" +
"\n" +
"Prey proteins can be identified automatically via ExPASy Blast! using the \"Blast\" button, and optionally sorted alphabetically.\n" +
"\n" +
"Analysis results can be exported as text files using the \"Save DNA Sequences\" or \"Save Proteins\" menu.\n" +
"\n" +
"Here are a few tips to use this program correctly and efficiently:\n" +
"\n" +
"* Sequence code, reading frames, translation direction and translation can only be set once a valid file has been loaded\n" +
"\n" +
"* If the user chooses to modify the sequence code, reading frame, or direction once the sequence has been translated by pressing the \"Go\" button\n" +
"\n" +
"* The sequence must be translated once again in order to obtain a graphical overview if different settings are entered.\n" +
"\n" +
"Support\n" +
"-------\n" +
"\n" +
"For troubleshooting, please send an email to `cauchy@ie-freiburg.mpg.de` and I'll try to help you. Please also let me know if you notice any bugs, and also please notify any on github. \n" +
"\n" +
"Contributions\n" +
"-------------\n" +
"\n" +
"Any contributions are highly welcome and appreciated!\n" +
"\n" +
"\n" +
"License\n" +
"-------\n" +
"\n" +
"Copyright (C) 2007 Pierre Cauchy. This work is licensed under the GNU General Public License v3.0, see ``LICENCE.TXT`` for details. If you require the use of this software under a difference license, please let me know.");
        JScrollPane jScrollPane1 = new JScrollPane(jTextArea1);

        
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(jScrollPane1);
        jTextArea1.setCaretPosition(0);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 12));
    }
    
}
