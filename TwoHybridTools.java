/*
 * TwoHybridTools.java
 *
 * Created on 11 janvier 2007, 14:59
 */

/**
 *
 * @author  Pierre Cauchy
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;    
import java.io.*;
import javax.swing.text.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Arrays.*;
import java.net.*;
import org.biojava.bio.alignment.NeedlemanWunsch;
import org.biojava.bio.alignment.SequenceAlignment;
import org.biojava.bio.alignment.SmithWaterman;
import org.biojava.bio.alignment.SubstitutionMatrix;
import org.biojava.bio.seq.DNATools;

import org.biojava.bio.symbol.AlphabetManager;
import org.biojava.bio.symbol.FiniteAlphabet;




public class TwoHybridTools extends javax.swing.JFrame 
{
    
    /**
     * Creates new form TwoHybridTools
     */
    
    //instance variables
    
    //control booleans, alter program sequence
    public boolean seqLoaded = false;
    public boolean isTranslated = false;
    public boolean ctrlPressed = false;
    public boolean rangeSelected = false;
    boolean noSeqs=true;
    boolean translate=false;
    boolean translationDone=false;
    String[][] charListAndCount;
    String report;
    int ncutoff=2;
    int modelscore=1000;
    String giSeq;
    proxyOptions options = new proxyOptions();
    //translation settings variables
    public int readFrameNum = 1;
    public boolean reverse = false;
    public boolean complementary = false;
    public int start,tmpStart;
    public int end,tmpStop;
    public String genCode = "Universal";
    public String direction = "Forward (5'-3')";
    public final String[] STRANDS = {"Original","Reverse"};
    public SeqList seqs;
    public SeqList ADSeqs;
    public SeqList TERMSeqs;
    public SeqList overlappedSeqs;
    public SeqList overlappedDNASeqs;
    public SeqList transSeqs;
    public SeqList loadedTransSeqs;
    public SeqList originalSeqs;
    boolean ORFfounds=false;
    boolean proteinVector = false;
    boolean termProteinVector = false;
    String protVector="";
    String termProtVector="";
    int protVectorStart;
    int termProtVectorStart;
    int strand = 0;
    int code=0;
    int numSeqs=0;
    boolean overlapTerms=false;
    boolean modelLoaded=false;
    Sequence model;
    boolean scrap=false;
    String modelDef="";
    public SeqList loadedSeqs;
    boolean matchProtein = true;
    String vector="AAAAAGCA";
    String termvector="AAACCACTTT";
    boolean vectorLoaded=false;
    boolean termvectorLoaded=false;
    int vectorStart=14;
    int termvectorStart=28;
    VectorFrame vectorFrame,termvectorFrame;
    //DNA sequence string and amino acid sequence string
    public int minDNAOverlap=15;
    public int minProtOverlap=5;
    public String seq,transSeq;
    int maxTransSeq=Integer.MIN_VALUE;
    String def;
    char[][] mutationsMatrix;
    int[] mutationsList;
    String proxyHost="";
    String proxyPort="";
    String ADString="AD";
    String TERMString="TERM";
    int mismatches=1;
    //display & comments variables
    public final int COMMENTS_SIZE=11;
    public String description, accession, details;
    public boolean sortAlphabetical=false;
    
    //Instance variables for date(date might change during program!)
    public Date date;
    public SimpleDateFormat df;
    
    //translation class instanciated as contains method used by multiple methods
    public Sequence mySequence;
    public Translator myTranslator;
    
    //ALL GRAPHICS-RELATED INSTANCE VARIABLES ARE LOCATED AT THE THE FILE
    
    
    //constructor. dims buttons.
    public TwoHybridTools() {
        initComponents();
        
        
           
            jComboBox3.setEnabled(false);
            
            Go.setEnabled(false);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
            jButton5.setEnabled(false);
            jMenuItem2.setEnabled(false);
            jMenuItem20.setEnabled(false);
            jComboBox4.setEnabled(false);
            jRadioButton4.doClick();
            jRadioButton4.setEnabled(false);
            jRadioButton5.setEnabled(false);
            jRadioButton6.setEnabled(false);
            jCheckBox1.setEnabled(false);
            jCheckBox2.setEnabled(false);
            jMenuItem9.setEnabled(false);
            jComboBox1.setEnabled(false);
            jProgressBar1.setVisible(false);
            //jTextPane1.setMargin(new Insets(5,5,0,10));   
            //jScrollPane1.setHorizontalScrollBarPolicy(jScrollPane1.HORIZONTAL_SCROLLBAR_NEVER);
            //jScrollPane1.setPreferredSize(new Dimension (50,145));
            ButtonGroup group = new ButtonGroup();
            group.add(jRadioButton4);
            group.add(jRadioButton5);
            group.add(jRadioButton6);
            
            
            Go.setMnemonic(KeyEvent.VK_ENTER);
            //jTextPane1.setContentType("text/html");
            jTextPane1.setEditable(false);
            //jScrollPane1.setViewportView(jTextPane1);
            jTextPane2.setEditable(false);
            //jTextPane2.setContentType("text/html");
            
            //jTextPane1.getDocument().putProperty("i18n", true);
            //jTextPane1.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
            
            //javax.swing.text.html.HTMLEditorKit editorKit = (javax.swing.text.html.HTMLEditorKit)jTextPane1.getEditorKit();
            //javax.swing.text.html.HTMLDocument doc = (javax.swing.text.html.HTMLDocument)jTextPane1.getDocument();
            //jTextPane1.getDocument().putProperty("i18n", Boolean.FALSE);
            //javax.swing.text.html.HTMLEditorKit editorKit = (javax.swing.text.html.HTMLEditorKit)jTextPane1.getEditorKit();
            //javax.swing.text.html.HTMLDocument doc = (javax.swing.text.html.HTMLDocument)jTextPane1.getDocument();
            
            
            //jTextPane1.setText( "<html><body>AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA</body></html>");
            
            
            //jTextPane2.setText("<html><head></head><body><font size=3 face=Courier New>No model currently loaded<br></font></body></html>");
            jTextPane2.setText("No model currently loaded");
            
            
            UIManager.put("OptionPane.font", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("OptionPane.buttonFont", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("TextField.font", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("List.font", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("Label.font", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("ComboBox.font", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("Button.font", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("TableHeader.font", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("Table.font", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            UIManager.put("ToggleButton.font", new javax.swing.plaf.FontUIResource(new Font("MS Reference Sans Serif", Font.PLAIN, 12)));
            
            
            
            
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     *
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        Go = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new JTextPane ()
        {
            public void setSize(Dimension d)
            {
                if (d.width < getParent().getSize().width)
                d.width = getParent().getSize().width;

                super.setSize(d);
            }

            public boolean getScrollableTracksViewportWidth()
            {
                return false;
            }

        };
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jCheckBox2 = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jOptionPane1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("2-HybridTools");
        setBackground(new java.awt.Color(235, 235, 235));
        setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(null);

        Go.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        Go.setText("Go");
        Go.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoActionPerformed(evt);
            }
        });
        getContentPane().add(Go);
        Go.setBounds(400, 610, 100, 30);

        jComboBox3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jComboBox3.setMaximumRowCount(12);
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Universal", "Mitochondrial (Vert.)", "Mitochondrial (Arth.)", "Mitochondrial (Ech.)", "Mitochondrial (Moll.)", "Mitochondrial (Asc.)", "Euplotes", "Paramecium", "Candida" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox3);
        jComboBox3.setBounds(60, 570, 110, 23);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Reading Frame");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(530, 560, 80, 23);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Code");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(10, 570, 30, 23);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Information");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(310, 270, 280, 20);

        jButton2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jButton2.setText("Graph");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(510, 610, 100, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("DNA Sequences");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(340, 0, 120, 30);

        jComboBox4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Original", "Reverse" }));
        jComboBox4.setPreferredSize(new java.awt.Dimension(32, 25));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox4);
        jComboBox4.setBounds(60, 610, 90, 23);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Strand");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        getContentPane().add(jLabel8);
        jLabel8.setBounds(0, 610, 40, 20);

        jRadioButton4.setText("1");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton4);

        jRadioButton5.setText("2");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton5);

        jRadioButton6.setText("3");
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton6);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(510, 570, 113, 30);

        jButton3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jButton3.setText("Summary");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(290, 610, 100, 30);

        jButton4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jButton4.setText("Find ORFs");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(180, 610, 100, 30);

        jCheckBox1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jCheckBox1.setText("Overlap N and C Terms (where available):");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox1);
        jCheckBox1.setBounds(170, 570, 269, 23);

        jComboBox1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Protein", "DNA" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(440, 570, 60, 21);

        jButton5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jButton5.setText("Blast results");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(620, 610, 100, 30);
        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(570, 270, 146, 14);

        jTextPane2.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(jTextPane2);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 300, 730, 250);

        jScrollPane1.setViewportView(jTextPane1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 730, 230);

        jCheckBox2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jCheckBox2.setText("Sort Blast output");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox2);
        jCheckBox2.setBounds(620, 570, 130, 23);

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem1.setText("Open Sequence(s)");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem20.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem20.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem20.setText("Append Sequence(s)");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem20);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem10.setText("Load Model (Optional)");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuItem16.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem16.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem16.setText("Load Model from NCBI");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem13.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem13.setText("Unload Model");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem13);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem2.setText("Save Proteins");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem9.setText("Save DNA Sequences");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem7.setText("Quit");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu4.setText("Options");
        jMenu4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N

        jMenu5.setText("Vector");
        jMenu5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N

        jMenuItem11.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem11.setText("Define 5' Vector");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem11);

        jMenuItem12.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem12.setText("Define 3' Vector");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem12);

        jMenuItem23.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem23.setText("Vector mismatches");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem23);

        jMenu4.add(jMenu5);

        jMenu7.setText("Primer name tags");
        jMenu7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N

        jMenuItem21.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem21.setText("FW primer name tag");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem21);

        jMenuItem22.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem22.setText("RV primer name tag");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem22);

        jMenu4.add(jMenu7);

        jMenu6.setText("Overlap");
        jMenu6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N

        jMenuItem18.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem18.setText("Minimum DNA Overlap");
        jMenuItem18.setActionCommand("Protein Overlap");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem18);

        jMenuItem19.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem19.setText("Minimum Protein Overlap");
        jMenuItem19.setActionCommand("Protein Overlap");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem19);

        jMenu4.add(jMenu6);

        jMenuItem14.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem14.setText("N cutoff");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuItem15.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem15.setText("Model alignment score");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem15);

        jMenuItem17.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem17.setText("Network Options");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem17);

        jMenuBar1.add(jMenu4);

        jMenu2.setText("Edit");
        jMenu2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem5.setText("Cut");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem4.setText("Copy");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem6.setText("Paste");
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");
        jMenu3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem8.setText("Help");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jMenuItem3.setText("About");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
// TODO add your handling code here:
        //FileDialog myDialog = new FileDialog(this);
        
            
        
            
        
        
        JFileChooser chooser = new JFileChooser();
        
        // Enable multiple selections
        chooser.setMultiSelectionEnabled(true);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new SequenceFilter());
        chooser.setDialogTitle("Choose Sequence Files");
        // Show the dialog; wait until dialog is closed
        chooser.showOpenDialog(this);
        //Is it possible to use file filters? Looks hard (file filters are interfaces?!?)
        
        
        
        //FilenameFilter filter = new GenBankFilter();
        //myDialog.setFilenameFilter(filter);
        
        //myDialog.setTitle("Choose Fasta File");
        //myDialog.show();
        
        
        try
        {
            
        File [] file=chooser.getSelectedFiles();
        //String fileName = myDialog.getFile();
        //String directory = myDialog.getDirectory();
        
        //safeguard if no file chosen
        //if(directory==null||fileName==null)
        
        if(file.length==0)
        {
            
            throw new NullPointerException();
        }
        
       
        
        
        
        
        //Loads file with absolute path
        FileLoader myFile = new FileLoader(file);
        myFile.loadFile();
        seqLoaded=true;
                
        
        //parses file
        MetaParser myMetaParser = new MetaParser(myFile.returnFile());
        numSeqs+=myMetaParser.getNumSeqs();
        myMetaParser.initialiseSeqLists();
        
        
        
        //gets accession and description
       
        
        
        
        
        
        
        
        //gets sequence to be processed
        
       
        SeqList newseqs = myMetaParser.getSequences();
        
        
        //jLabel4.setText("Loading Sequences");
                    //jProgressBar1.setVisible(true);
                    //jProgressBar1.setMinimum(0);
                    //jProgressBar1.setMaximum(100);
                    //jProgressBar1.setStringPainted(true);
                    //this.update(this.getGraphics());
        
        for(int i=0;i<newseqs.getNumSeqs();i++)
        {
            loadedSeqs.addDef(newseqs.returnDef(i));
            loadedSeqs.addStr(newseqs.returnSeq(i));
            originalSeqs.addDef(newseqs.returnDef(i));
            originalSeqs.addStr(newseqs.returnSeq(i));
            //jProgressBar1.setValue(100*i/loadedSeqs.getNumSeqs());
                        //this.update(this.getGraphics());
        }
        
         //jProgressBar1.setValue(100);
                    //jProgressBar1.setVisible(false);
        
        
        String tmp=jTextPane1.getText();
        
        //System.out.println("\nBEFORE\n\n"+tmp+"\n\n\nAFTER\n\n");
        //System.out.println("index="+tmp.lastIndexOf("<br></font>")+11);
        
        //System.out.println(tmp);
        
        for(int i=0;i<newseqs.getNumSeqs();i++)
        {
            tmp+=newseqs.returnDef(i)+"\n"+newseqs.returnSeq(i)+"\n\n";
            
        }
        
        
        jTextPane1.setText(tmp);
        //System.out.println(tmp+"</font></body></html>");
        jTextPane1.setCaretPosition(0);
        
        seqLoaded=true;
        
        //initialises buttons and fields
        if(modelLoaded&&translationDone)
        {
            
            //jTextPane2.setText("<html><head></head><body><font size=3 face=Courier New>Model Loaded :<br>"+modelDef+"<br>"+model.getSequence()+"<br></font>");
            jTextPane2.setText("Model Loaded :\n"+modelDef+"\n"+model.getSequence()+"\n");
        
        }
        
        Go.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(true);
        jMenuItem2.setEnabled(false);
        jComboBox3.setEnabled(true);
        jComboBox4.setEnabled(true);
        jCheckBox1.setEnabled(true);
        jCheckBox2.setEnabled(true);
        jButton5.setEnabled(false);
        jRadioButton4.setEnabled(true);
        jRadioButton5.setEnabled(true);
        jRadioButton6.setEnabled(true);
        
        
        
        
        translate=true;
        translationDone=false;
        ORFfounds=false;
        
         
        
        
        
            
        jLabel4.setText("Information");
            
        }
        catch(NullPointerException npe)
        {
            
        }
        /*catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(this,"Invalid File" , "Error", JOptionPane.ERROR_MESSAGE);
        }*/
        catch(StringIndexOutOfBoundsException stioobe)
        {
           
        }
        
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
// TODO add your handling code here:
        try
        {
            minProtOverlap=Integer.parseInt(JOptionPane.showInputDialog("Enter Minimum Protein Overlap (default: 5)","5"));
            if(translationDone)
            {
                Go.setEnabled(true);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
                jButton4.setEnabled(false);
                jButton5.setEnabled(false);
                jMenuItem2.setEnabled(false);
                jMenuItem9.setEnabled(false);
            }
        }
        catch(NumberFormatException nfe)
        {
            
        }
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
// TODO add your handling code here:
        try
        {
            minDNAOverlap=Integer.parseInt(JOptionPane.showInputDialog("Enter Minimum DNA Overlap (default: 15)","15"));
            if(translationDone)
            {
                Go.setEnabled(true);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
                jButton4.setEnabled(false);
                jButton5.setEnabled(false);
                jMenuItem2.setEnabled(false);
                jMenuItem9.setEnabled(false);
            }
        }
        catch(NumberFormatException nfe)
        {
            
        }
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
//BLAST results
// TODO add your handling code here:
    
        jProgressBar1.setVisible(true);
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum(100);
        jProgressBar1.setStringPainted(true);
        
        String postQuery="";
        String blastResults="";
        jLabel4.setText("Please hold on, BLASTing sequences");
        jTextPane2.setText("");
        if(modelLoaded)
        {
            
            //jTextPane2.setText("<html><head></head><body><font size=3 face=Courier New>Model Loaded :<br>"+modelDef+"<br>"+model.getSequence()+"<br></font><br>");
            jTextPane2.setText("Model Loaded :\n"+modelDef+"\n"+model.getSequence()+"\n");
        
        }
        else
        {
            //jTextPane2.setText("<html><head></head><body><font size=3 face=Courier New>No model currently loaded<br></font></body></html>");
            jTextPane2.setText("No model currently loaded\n");
        
        }
        jTextPane2.setCaretPosition(0);
        this.update(this.getGraphics());
        for(int i=0;i<transSeqs.getNumSeqs();i++)
        {
            
            jProgressBar1.setValue(100*i/transSeqs.getNumSeqs());
            this.update(this.getGraphics());
            postQuery="sequence="+transSeqs.returnDef(i)+"\n"+transSeqs.returnSeq(i);
            System.out.println(transSeqs.returnSeq(i));
            try
            {
                    // Create query string

 

String queryString = "notice=" + transSeqs.returnSeq(i).replace("-", "")+"&optF=T" +"&optM=BLOSUM62" +"&optb=500"+"&optd=SWISSPROT" +"&opte=10.0" +"&optp=blastp" +"&optv=500" +"&title="+transSeqs.returnDef(i);
System.out.println (queryString);

 

// Make connection

 

URL url = new URL("https://npsa-prabi.ibcp.fr/cgi-bin/simsearch_blast.pl");

URLConnection urlConnection = url.openConnection();

urlConnection.setDoOutput(true);

OutputStreamWriter out = new OutputStreamWriter(

urlConnection.getOutputStream());

 

// Write query string to request body

 

out.write(queryString);

out.flush();

 

// Read the response

 

BufferedReader in = new BufferedReader(

new InputStreamReader(urlConnection.getInputStream()));





                String uniprotID="";
                String str;
                boolean startSearch=false;
                boolean firstBlastLine=true;
                while (null != ((str = in.readLine())))
                {
                    System.out.println (str);
                    if(str.indexOf("Sequences producing significant alignments:")!=-1)
                    {
                        startSearch=true;
                    }
                    if((str.indexOf("VALUE")!=-1)&&firstBlastLine&&startSearch)
                    {
                        uniprotID=str.substring(str.indexOf("http://www.uniprot.org/uniprot/")+31,str.indexOf("http://www.uniprot.org/uniprot/")+37);
                        String uniprotDesc=str.substring(str.indexOf(uniprotID+"</A>")+11,str.indexOf(uniprotID+"</A>")+60);
                        String blastScore=str.substring(str.indexOf(uniprotID+"</A>")+61,str.indexOf(uniprotID+"</A>")+69).replace(" ", "");
                        String eScoreString=str.substring(str.indexOf("displayaln"));
                        System.out.println(eScoreString);
                        String eScore=eScoreString.substring(eScoreString.indexOf(">")+1,eScoreString.indexOf("</A>"));
                        blastResults+=uniprotID+"\t"+uniprotDesc+"\n"+blastScore+"\n"+eScore+"\n";
                        firstBlastLine=false;
                        if(uniprotID=="")
                        {
                            transSeqs.setDef(transSeqs.returnDef(i)+"\tNO BLAST HIT",i);
                        }
                        else
                        {
                            transSeqs.setDef(transSeqs.returnDef(i)+"\tTOP BLAST HIT:\t"+uniprotID+" "+uniprotDesc+" SCORE: "+blastScore+" E-VALUE: "+eScore,i);
                        }
                    }
    
                }
                if(uniprotID=="")
                        {
                            transSeqs.setDef(transSeqs.returnDef(i)+"\tNO BLAST HIT",i);
                        }
                out.close();

                in.close(); 
                
                System.out.println(blastResults);
                
            }
            catch( IOException e ){
		// handle the error here
            }
        jTextPane2.setText(jTextPane2.getText()+transSeqs.returnDef(i)+"\n"+transSeqs.returnSeq(i).replace('>', 'M').replaceAll(" ", "")+"\n");
        this.update(this.getGraphics());
        
            
        }
        jProgressBar1.setValue(100);
        jTextPane2.setText("");
        
        if(modelLoaded)
        {
            
            jTextPane2.setText("Model Loaded :\n"+modelDef+"\n"+model.getSequence()+"\n");
        }
        else
        {
            jTextPane2.setText("No model currently loaded\n");
            
        }
        if(sortAlphabetical)
        {
            transSeqs.sortByDef();
        }
        this.showSequences();
        jProgressBar1.setVisible(false);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
// TODO add your handling code here:
        if(jComboBox1.getSelectedIndex()==0)
        {
            matchProtein=true;
        }
        if(jComboBox1.getSelectedIndex()==1)
        {
            matchProtein=false;
        }
        Go.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        translate=true;
        translationDone=false;
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
// TODO add your handling code here:
        options.setSize(320, 150);
        options.setTitle("Please enter HTTP Proxy Options");
        options.setVisible(true);
        this.centreFrame(options);
        
        
        options.show();
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
// TODO add your handling code here:
        
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "proxyHost", options.returnProxyHost() );
        System.getProperties().put( "proxyPort", options.returnProxyPort() );
        String GI="";  // A local variable to hold the name.
        
        GI = JOptionPane.showInputDialog(null, "Please enter Protein Accession Number");

        
        

        
        
            
        
        giSeq="";
        try
                
        {
            if(GI==null||GI.equals(""))
            {
                throw new MalformedURLException();
            }
            
            
            URL url = new URL("https://www.ncbi.nlm.nih.gov/sviewer/viewer.cgi?tool=portal&sendto=on&log$=seqview&db=protein&dopt=fasta&sort=&val="+GI+"&from=begin&to=end&maxplex=1");
            
            
	    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	    String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                giSeq+=inputLine+"\n";
                //System.out.println(inputLine);
            }

	    in.close();
            
            
                modelDef=giSeq.substring(giSeq.indexOf(">"),giSeq.indexOf("\n", giSeq.indexOf(">")));
                model = new Sequence(giSeq.substring(giSeq.indexOf("\n", giSeq.indexOf(">"))).replaceAll("\n", ""),false,1);
                model.processSequence();
                if(modelDef.indexOf("RNA")!=-1)
                {
                    myTranslator = new Translator(model.getCodonsArray());
                    myTranslator.translate(code);
                    myTranslator.processStartCodons();
            
            //myTranslator.proteinToString(10);
                transSeq=myTranslator.getTranslatedSequence();
                transSeq=myTranslator.getFastaProtSeq();
                model = new Sequence(transSeq,false,1, 0, transSeq.length()-1, code);
                }
                //System.out.println(model.getSequence());
                
                jTextPane2.setText("Model Loaded :\n"+modelDef+"\n"+model.getSequence()+"\n");
                modelLoaded=true;
                modelscore=5*model.getSequence().length()*80/100;
        
                
        if(translationDone)
        {
            this.doTranslation(false);
        }
            
        }
        catch(StringIndexOutOfBoundsException sioobe)
        {
            JOptionPane.showMessageDialog(this,"Invalid Address" , "Error", JOptionPane.ERROR_MESSAGE);
        
        }
        
        catch(MalformedURLException mue)
        {
            
        }
        
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(this,"Invalid Address" , "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(NullPointerException npe)
        {
            JOptionPane.showMessageDialog(this,"Invalid Address" , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
// TODO add your handling code here:
        try
        {
            
            
            modelscore=Integer.parseInt(JOptionPane.showInputDialog("Enter Score Threshold (default: "+modelscore+")",Integer.toString(modelscore)));
            if(translationDone&&modelLoaded)
            {
                transSeqs=loadedTransSeqs;
                
                jTextPane2.setText("Model Loaded :\n"+modelDef+"\n"+model.getSequence()+"\n");
                jTextPane2.setCaretPosition(0);
                this.update(this.getGraphics());
                this.doTranslation(false);
            }
        }
        catch(NumberFormatException nfe)
        {
            
        }
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed

// TODO add your handling code here:
        try
        {
            ncutoff=Integer.parseInt(JOptionPane.showInputDialog("Enter N Cut-off (default: 2)","2"));
            if(translationDone)
            {
                Go.setEnabled(true);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
                jButton4.setEnabled(false);
                jButton5.setEnabled(false);
                jMenuItem2.setEnabled(false);
                jMenuItem9.setEnabled(false);
            }
        }
        catch(NumberFormatException nfe)
        {
            
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed

// TODO add your handling code here:

        modelLoaded=false;
        jTextPane2.setText("No model currently loaded\n");
        if(translationDone)
        {
            translate=true;
            translationDone=false;
            Go.setEnabled(true);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jButton5.setEnabled(false);
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
// TODO add your handling code here:
        //termvector=JOptionPane.showInputDialog(null,"Please Enter C Terminus Vector DNA Sequence");
        //if(!termvector.equals("")&&!termvector.equals(null))
        //{
            //termvectorLoaded=true;
        //}
        try
        {
            termvectorFrame = new VectorFrame();
            termvectorLoaded = true;
            if(seqLoaded)
            {
                translate=true;
            translationDone=false;
            Go.setEnabled(true);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jButton5.setEnabled(false);
            }
        }
        catch(NullPointerException npe)
        {
            
        }
        
        
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
// TODO add your handling code here:
       // vector = JOptionPane.showInputDialog(null,"Please Enter Vector DNA Sequence");
        
       // if(!vector.equals("")&&!vector.equals(null))
        //{
        //    vectorLoaded=true;
        //}
        
        //String [] result = new VectorFrame().returnResult();
        //if(!result.equals(null)&&result.length==2)
        //{
           // if(!result[0].equals(null)&&!result[0].equals(""))
            //{
            //    vector = result[0];
            //}
            //if(!result[1].equals(null)&&!result[1].equals(""))
            //{
             //   try
               // {
              //      vectorStart=Integer.parseInt(result[1]);
             //       vectorLoaded=true;
               // }
               // catch(NumberFormatException nfe)
              //  {
                    
              //  }
           // }
       // }
        
        try
        {
            vectorFrame = new VectorFrame();
            vectorLoaded = true;
            if(seqLoaded)
            {
            translate=true;
            translationDone=false;
            Go.setEnabled(true);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jButton5.setEnabled(false);
            }
        }
        catch(NullPointerException npe)
        {
            
        }
        
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
// TODO add your handling code here:
        
        /** Runs an analysis with all different settings to determine which are best adapted to
         *  the current sequence. This is determined by the longest obtained translated sequence
         **/
        
        //init max values
        
        int maxSize=0;
        double maxTrans=Double.MIN_VALUE;
        int maxFrame=0;
        int maxCode=0;
        int maxStrand=0;
        
        String maxGenCode="";
        boolean maxSense=false;
        boolean maxDirection=false;
        int maxStart=0;
        int maxStop=0;
        int totalTrans=0;
        
        //back up current values
        int bakStart=start;
        int bakEnd=end;
        int bakFrame=readFrameNum;
        int bakCode=code;
        int bakStrand=strand;
        boolean bakReverse=reverse;
        
        
        
        
        //nested loops that try all different settings
                loadedSeqs=new SeqList(new String[0], new String[0]);
                seqs=new SeqList(new String[0], new String[0]);
                for(int i=0;i<originalSeqs.getNumSeqs();i++)
                {
                    loadedSeqs.addDef(originalSeqs.returnDef(i));
                    loadedSeqs.addStr(originalSeqs.returnSeq(i));
                    seqs.addDef(originalSeqs.returnDef(i));
                    seqs.addStr(originalSeqs.returnSeq(i));
                }
                numSeqs=seqs.getNumSeqs();
                this.processSequences();
                jLabel4.setText("Finding ORFs");
                jProgressBar1.setVisible(true);
                jProgressBar1.setMinimum(0);
                jProgressBar1.setMaximum(100);
                jProgressBar1.setStringPainted(true);
                this.update(this.getGraphics());

                
                for(int j=0;j<seqs.getNumSeqs();j++)
                {
                        
                        
                        seq=seqs.returnSeq(j);
                        def=seqs.returnDef(j);     
                         
            
                        for(int i=1;i<=3&&def.indexOf(TERMString)==-1;i++) 
                        {
                        readFrameNum=i;
                        //System.out.println("\n\n\n\nFRAME = "+i);
                        start=0;
                        end=seq.length();
                        
                        
                        mySequence = new Sequence(seq,reverse,readFrameNum,start,end,strand);
                        //mySequence.processSequence();
                        
                        
                        
                        
                        
                        
                        myTranslator = new Translator(mySequence.getCodonsArray());
                        myTranslator.translate(code);
                        myTranslator.processStartCodons();
            
            //myTranslator.proteinToString(10);
                        myTranslator.countStartCodons();
                        myTranslator.countStopCodons();
                        
                        //System.out.println("\ncode : "+code+"\nFrame: "+readFrameNum+"\nStrand : "+strand+"\nReverse: "+reverse);
                        
                        
                        maxSize=myTranslator.getMaxTranslated();
                        //System.out.println("MAXIMUM TRANSLATED SIZE FOR THIS ROUND: "+maxSize+"\n");
                        if(maxSize>maxTrans) {
                            
                            maxTrans=maxSize;
                            
                            maxStart=myTranslator.getMaxStart();
                            maxStop=myTranslator.getMaxStop();
                            maxFrame=readFrameNum;
                            
                            
                            
                            
                        }
                    
                }
                        
                        if(def.indexOf(TERMString)==-1)
                        {totalTrans+=maxTrans;
                        //System.out.println(def+" frame "+maxFrame+" length "+(int) maxTrans+" start "+(int) maxStart+" End "+ (int) maxStop);
                        loadedSeqs.setDef(def+" frame "+maxFrame+" length "+(int) maxTrans+" start "+ (int)maxStart+" End "+ (int) maxStop, j);
                        
                        loadedSeqs.setStr(seqs.returnSeq(j).substring(maxStart + maxFrame-1, maxStop), j);
                        maxFrame=1;
                        maxTrans=0;
                        maxStart=0;
                        maxStop=0;
                        }
                        else
                        {
                            loadedSeqs.setDef(def, j);
                            loadedSeqs.setStr(seqs.returnSeq(j), j);
                        }
            jProgressBar1.setValue(100*j/numSeqs);
            this.update(this.getGraphics());            
           }
            
        
        //returns string for best suited genetic code
        
        
        //returns string for best suited direction
        
        
        
        
        
        
        
        
        //System.out.println("MOST PROBABLE SETTINGS:\nMaximum Size :"+(int)maxTrans +"\nReading Frame Number: "+maxFrame);
        
        //instanciates and shows a report frame with obtained results
        ORFfounds=true;
        jProgressBar1.setValue(100);
        JOptionPane.showMessageDialog(this,"Open Reading Frames set" , "Report", JOptionPane.INFORMATION_MESSAGE);
        start=bakStart;
        end=bakEnd;
        readFrameNum=bakFrame;
        code=bakCode;
        strand=bakStrand;
        reverse=bakReverse;
        jComboBox3.setEnabled(false);
        jComboBox4.setEnabled(false);
        jRadioButton4.setEnabled(false);
        jRadioButton5.setEnabled(false);
        jRadioButton6.setEnabled(false);
        jButton4.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        Go.setEnabled(true);
        jMenuItem2.setEnabled(false);
        jMenuItem9.setEnabled(false);
        jProgressBar1.setVisible(false);
        jLabel4.setText("Information");
        this.update(this.getGraphics());
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
// TODO add your handling code here:
        
        /** Instanciates and shows a frame with stats about the sequence. Calls count methods,
         *  get ave, max and min sizes for translated sequences
         **/
        this.getMutationsReport();
        MutationsReport myReport = new MutationsReport("****************Peptidic Mutations on Model*****************"+"\n\n"+report);
        myReport.setTitle("Mutations Report");
        myReport.setSize(450, 400);
        this.centreFrame(myReport);
        myReport.show();
        //this.doTranslation(false);
        //myTranslator.countStartCodons();
        //myTranslator.countStopCodons();
        //Summary mySummary=new Summary(myTranslator.getNumStartCodons(),myTranslator.getNumStopCodons(),myTranslator.getMinTranslated(),myTranslator.getMaxTranslated(),myTranslator.getAvergageTranslatedSize(),end-start,mySequence.getGCContent(), mySequence.getTm(),mySequence.getHybridisationTemp());
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed

              
        
        JFileChooser chooser = new JFileChooser();
        
        // Enable multiple selections
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new SequenceFilter());
        chooser.setDialogTitle("Choose a model DNA/Protein Sequence");
        // Show the dialog; wait until dialog is closed
        chooser.showOpenDialog(this);
        //Is it possible to use file filters? Looks hard (file filters are interfaces?!?)
        
        
        
        //FilenameFilter filter = new GenBankFilter();
        //myDialog.setFilenameFilter(filter);
        
        //myDialog.setTitle("Choose Fasta File");
        //myDialog.show();
        
        
        try
        {
            
        File file=chooser.getSelectedFile();
        //String fileName = myDialog.getFile();
        //String directory = myDialog.getDirectory();
        
        //safeguard if no file chosen
        //if(directory==null||fileName==null)
        
        if(file.equals(null))
        {
            
            throw new NullPointerException();
        }
        
       
        
        
        
        
        //Loads file with absolute path
        FileLoader myFile = new FileLoader(file.getAbsolutePath());
        myFile.loadFile();
        Parser myParser = new Parser(myFile.returnFile());
        modelDef= myParser.getDef();
        
        model = new Sequence(myParser.getModelSequenceString(),false,1);
        if(!myParser.isProtein())
        {
                myTranslator = new Translator(model.getCodonsArray());
                myTranslator.translate(code);
                myTranslator.processStartCodons();
            
            //myTranslator.proteinToString(10);
                transSeq=myTranslator.getTranslatedSequence();
                transSeq=myTranslator.getFastaProtSeq();
                model = new Sequence(transSeq,false,1, 0, transSeq.length()-1, code);
        }
        model.processSequence();
        //System.out.println(model.getSequence());
        
        jTextPane2.setText("Model Loaded :\n"+modelDef+"\n"+model.getSequence()+"\n");
        modelLoaded=true;
        modelscore=5*model.getSequence().length()*80/100;
        if(translationDone)
        {
            this.doTranslation(false);
        }
        }
        
        catch(NullPointerException npe)
        {
            
        }
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        //Windows File Dialog
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(chooser.SAVE_DIALOG);
        // Enable multiple selections
        
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new SequenceFilter());
        chooser.setDialogTitle("Choose File to Save");
        // Show the dialog; wait until dialog is closed
        chooser.showSaveDialog(this);
        //Is it possible to use file filters? Looks hard (file filters are interfaces?!?)
        
        
        
        //FilenameFilter filter = new GenBankFilter();
        //myDialog.setFilenameFilter(filter);
        
        //myDialog.setTitle("Choose Fasta File");
        //myDialog.show();
        
        
        try
        {
            
        File file=chooser.getSelectedFile();
        //String fileName = myDialog.getFile();
        //String directory = myDialog.getDirectory();
        
        //safeguard if no file chosen
        //if(directory==null||fileName==null)
        
        if(file.equals(null))
        {
            
            throw new NullPointerException();
        }
                String export="";
                for(int i=0;i<seqs.getNumSeqs();i++)
                {
                    export+=seqs.returnDef(i)+"\r\n"+seqs.returnSeq(i)+"\r\n";
                }
                
                
                FileSaver newFile = new FileSaver(export, file.getAbsolutePath());
                
                
            
            }
            
            
            catch(NullPointerException npe)
            {
            
            }
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
// TODO add your handling code here:
        if(jCheckBox1.isSelected())
        {
            overlapTerms=true;
            jComboBox1.setEnabled(true);
            
            
            
        }
        
        if(!jCheckBox1.isSelected())
        {
            overlapTerms=false;
            jComboBox1.setEnabled(false);
            
        }
        Go.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        translate=true;
        translationDone=false;
        
    }//GEN-LAST:event_jCheckBox1ActionPerformed
    //when Analysis button pressed    //when Summary button presssed    //if something is typed in this field    //when something is entered in the start field    
    
    //these methods, ranging from jRadioButton1ActionPerformed to jRadioButton6ActionPerformed 
    //allows the selected reading frame num to be input. Note that if anything is changed the sequence 
    //has to be translated again
    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
        // TODO add your handling code here:
        readFrameNum=3;
        Go.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        translationDone=false;
        translate=true;
    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        // TODO add your handling code here:
        readFrameNum=2;
        Go.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        translate=true;
        translationDone=false;
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        readFrameNum=1;
        Go.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        translate=true;
        translationDone=false;
    }//GEN-LAST:event_jRadioButton4ActionPerformed
    //when strand, original or reverse, is chosen
    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        strand = jComboBox4.getSelectedIndex();
        if(strand==0)
        {
            reverse=false;
        }
        else
        {
            reverse=true;
        }
        jMenuItem2.setEnabled(false);
        Go.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        translate=true;
        //System.out.println(strand);
        translationDone=false;
        
        
        
    }//GEN-LAST:event_jComboBox4ActionPerformed
    
    //If range modified, dims graphics button, re-enables translate button, forcing user to re-translate    //If range modified, dims graphics button, re-enables translate button, forcing user to re-translate
    //Changes genetic code to be used for translation. Also dims graphics button (same as above), disables FileSave if changed
    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        
        code=jComboBox3.getSelectedIndex();
        switch(code)
        {
            case 0: genCode="Universal";break;
            case 1: genCode="Mitochondrial, Vertebrata";break;
            case 2: genCode="Mitochondrial, Arthropoda";break;
            case 3: genCode="Mitochondrial, Echinodermata";break;
            case 4: genCode="Mitochondrial, Mollusca";break;
            case 5: genCode="Mitochondrial, Ascidia";break;
            case 6: genCode="Euplotia";break;
            case 7: genCode="Paramecium, Tetrahymena, Oxytrichia, Stylonychia, Glaucoma, Acetabularia";break;
            case 8: genCode="Candida cylindrica";break;
        }
        
        
        
        
        
        
        jMenuItem2.setEnabled(false);
        Go.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton5.setEnabled(false);
        translate=true;
        //System.out.println(code);
        translationDone=false;
        
        
    }//GEN-LAST:event_jComboBox3ActionPerformed

    //calls Help
    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        HelpFrame myHelp= new HelpFrame();
        myHelp.setSize(860,920);
        myHelp.setResizable(true);
        myHelp.show();
        this.centreFrame(myHelp);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_X)
        {
            System.exit(0);
        }
       
        
    }//GEN-LAST:event_formKeyPressed

    //Concatenates output strings into a file. Saves file using separate class
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        
        //Windows File Dialog
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(chooser.SAVE_DIALOG);
        // Enable multiple selections
        
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new SequenceFilter());
        chooser.setDialogTitle("Choose File to Save");
        // Show the dialog; wait until dialog is closed
        chooser.showSaveDialog(this);
        //Is it possible to use file filters? Looks hard (file filters are interfaces?!?)
        
        
        
        //FilenameFilter filter = new GenBankFilter();
        //myDialog.setFilenameFilter(filter);
        
        //myDialog.setTitle("Choose Fasta File");
        //myDialog.show();
        
        
        try
        {
            
        File file=chooser.getSelectedFile();
        //String fileName = myDialog.getFile();
        //String directory = myDialog.getDirectory();
        
        //safeguard if no file chosen
        //if(directory==null||fileName==null)
        
        if(file.equals(null))
        {
            
            throw new NullPointerException();
        }
        
                String export="";
                for(int i=0;i<transSeqs.getNumSeqs();i++)
                {
                    export+=transSeqs.returnDef(i)+"\r\n"+transSeqs.returnSeq(i).replaceAll(" ", "")+"\r\n";
                }
                
                
                FileSaver newFile = new FileSaver(export, file.getAbsolutePath());
                
                
            
            }
            
            
            catch(NullPointerException npe)
            {
            
            }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed

        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    //creates graphics frame, which is called with args : arrays indicating start/stop pos, range, translated seq length
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        
        
        
        
        
        GraphicsFrame myGraph = new GraphicsFrame(mutationsList);
        myGraph.setSize(600,200);
        myGraph.show();
        
        //calls custom method to centre frame rather than having to move window. How annoying!
        this.centreFrame(myGraph);
    }//GEN-LAST:event_jButton2ActionPerformed

    //cut. experimental! only cuts selected text.
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        jTextPane1.cut();
        jTextPane2.cut();
        
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    //copy. experimental! only pastes selected text.
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        jTextPane1.copy();
        jTextPane2.copy();
        
        
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    
    
    private void GoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoActionPerformed
       
       if(overlapTerms)
        {
            jLabel4.setText("Overlapping Sequences");
        }
        else
        {
            jLabel4.setText("Translated Sequences");
        }
        this.update(this.getGraphics());
        this.doTranslation(true);
    }//GEN-LAST:event_GoActionPerformed

    //Changes direction used for translation,chosen with combobox
    //Also dims graphics button (same as above), disables FileSave if changed
    //gets reading frame, chosen by combo box
    //About
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        jOptionPane1.showMessageDialog(this,"         2-Hybrid Tools 1.03\n          By Pierre Cauchy" , "About", JOptionPane.INFORMATION_MESSAGE);
        
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        
        this.getSequence();
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        ADString=JOptionPane.showInputDialog("Enter FW primer name tag (default: \"AD\")","AD");
        if(ADString=="")
        {
            ADString="AD";
        }
            
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        TERMString=JOptionPane.showInputDialog("Enter RV primer name tag (default: \"TERM\")","TERM");
        if(TERMString=="")
        {
            TERMString="AD";
        }
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
        
        if(jCheckBox2.isSelected())
        {
            sortAlphabetical=true;  
            
            
            
        }
        
        if(!jCheckBox2.isSelected())
        {
            sortAlphabetical=false;  
            
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
         try
        {
            mismatches=Integer.parseInt(JOptionPane.showInputDialog("Enter vector mismatches (default: 1)","1"));
            if(translationDone)
            {
                Go.setEnabled(true);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
                jButton4.setEnabled(false);
                jButton5.setEnabled(false);
                jMenuItem2.setEnabled(false);
                jMenuItem9.setEnabled(false);
            }
        }
        catch(NumberFormatException nfe)
        {
            
        }
                       
        
    }//GEN-LAST:event_jMenuItem23ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    
    
    /* The Main method is contained in this class. Instanciates a new JFrame (this one)
     * This was needed to centre frame (static content needed)
     **/
    
    //Loads file, parses it and processes sequence. also resets window
    public void getSequence()
    {
        if(termvectorLoaded)
        {
            this.getTermVector();
            
        }
        if(vectorLoaded)
        {
            this.getVector();
        }
        //FileDialog myDialog = new FileDialog(this);
        JFileChooser chooser = new JFileChooser();
        
        // Enable multiple selections
        chooser.setMultiSelectionEnabled(true);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new SequenceFilter());
        chooser.setDialogTitle("Choose Sequence Files");
        // Show the dialog; wait until dialog is closed
        chooser.showOpenDialog(this);
        //Is it possible to use file filters? Looks hard (file filters are interfaces?!?)
        
        
        
        //FilenameFilter filter = new GenBankFilter();
        //myDialog.setFilenameFilter(filter);
        
        //myDialog.setTitle("Choose Fasta File");
        //myDialog.show();
        
        
        try
        {
            
        File [] file=chooser.getSelectedFiles();
        //String fileName = myDialog.getFile();
        //String directory = myDialog.getDirectory();
        
        //safeguard if no file chosen
        //if(directory==null||fileName==null)
        
        if(file.length==0)
        {
            
            throw new NullPointerException();
        }
        
        jTextPane1.setText("");
        if(!modelLoaded)
        {
            jTextPane2.setText("");
            
        }
        
        
        
        
        //Loads file with absolute path
        FileLoader myFile = new FileLoader(file);
        myFile.loadFile();
        seqLoaded=true;
                
        
        //parses file
        MetaParser myMetaParser = new MetaParser(myFile.returnFile());
        numSeqs=myMetaParser.getNumSeqs();
        myMetaParser.initialiseSeqLists();
        
        
        
        //gets accession and description
       
        
        
        
        
        
        
        
        //gets sequence to be processed
        
        loadedSeqs=new SeqList(new String[0],new String[0]);
        originalSeqs=new SeqList(new String[0],new String[0]);
        seqs = myMetaParser.getSequences();
        
        //jLabel4.setText("Loading Sequences");
                    //jProgressBar1.setVisible(true);
                    //jProgressBar1.setMinimum(0);
                    //jProgressBar1.setMaximum(100);
                    //jProgressBar1.setStringPainted(true);
                    //this.update(this.getGraphics());
        
        for(int i=0;i<seqs.getNumSeqs();i++)
        {
            loadedSeqs.addDef(seqs.returnDef(i));
            loadedSeqs.addStr(seqs.returnSeq(i));
            originalSeqs.addDef(seqs.returnDef(i));
            originalSeqs.addStr(seqs.returnSeq(i));
            //jProgressBar1.setValue(100*i/loadedSeqs.getNumSeqs());
                        //this.update(this.getGraphics());
        }
        
         //jProgressBar1.setValue(100);
                    //jProgressBar1.setVisible(false);
        
        
        
        //jTextPane1.getDocument().putProperty("i18n", Boolean.TRUE);
            
        //String tmp="<html><head></head><body><font size=3 face=Courier New>";
        String tmp="";
        for(int i=0;i<seqs.getNumSeqs();i++)
        {
            //tmp+=seqs.returnDef(i)+"<br>"+seqs.returnSeq(i).replaceAll("\n","<br>")+"<br>";
            //tmp+=seqs.returnDef(i)+"<br>"+seqs.returnSeq(i)+"<br>";
            tmp+=GFormat.padRight(seqs.returnDef(i),40)+"\n"+seqs.returnSeq(i)+"\n";
        }
        
        
        //jTextPane1.setText(tmp+"</font></body></html>");
        //jTextPane1.setText(tmp);
        StyledDocument doc = jTextPane1.getStyledDocument();
            addStylesToDocument(doc);
        
            try {
                
            
            doc.insertString(doc.getLength(), tmp,
                                 doc.getStyle("regular"));
            this.update(this.getGraphics());
            }
            catch (BadLocationException ble) {
            System.err.println("Couldn't insert initial text into text pane.");
        }
        jTextPane1.setCaretPosition(0);
        
        seqLoaded=true;
        
        //initialises buttons and fields
        
        Go.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(true);
        jMenuItem2.setEnabled(false);
        jMenuItem9.setEnabled(false);
        jComboBox3.setEnabled(true);
        jComboBox4.setEnabled(true);
        jCheckBox1.setEnabled(true);
        jCheckBox2.setEnabled(true);
        jMenuItem20.setEnabled(true);
        jRadioButton4.setEnabled(true);
        jRadioButton5.setEnabled(true);
        jRadioButton6.setEnabled(true);
        if(modelLoaded&&translationDone)
        {
            
            jTextPane2.setText("Model Loaded :\n"+modelDef+"\n"+model.getSequence()+"\n");
        
        }
        else
        {
            jTextPane2.setText("No model currently loaded\n");
        }
        translate=true;
        translationDone=false;
        ORFfounds=false;
        
         
        
        
        
            
        jLabel4.setText("Information");
            
        }
        catch(NullPointerException npe)
        {
            
        }
        /*catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(this,"Invalid File" , "Error", JOptionPane.ERROR_MESSAGE);
        }*/
        catch(StringIndexOutOfBoundsException stioobe)
        {
           
        }
        
        
    }
    
    /* Translation Button clicked.
     * gets range defined in start/end fields.
     * instanciates Sequence object from raw sequence obtained in Parser
     * Converts it into array of codons, according to chosen reading frame
     * and direction (5'-3' or 3'5'), and chosen genetic code
     * gets position of start and stop codons (processed logically, ie there
     * cannot be 2 start codons in a row.
     * returns sequence to string, appends it to information and sets rows 
     * accordingly
     **/
    public void doTranslation(boolean b)
    {
        translate=b;
        if(translate)
        {
            if(termvectorLoaded)
        
        {
            this.getTermVector();
            
        }
        if(vectorLoaded)
        {
            this.getVector();
        }
        
        seqs=new SeqList(new String[0],new String[0]);
        for(int i=0;i<loadedSeqs.getNumSeqs();i++)
        {
            seqs.addDef(loadedSeqs.returnDef(i));
            seqs.addStr(loadedSeqs.returnSeq(i));
        }
        numSeqs=loadedSeqs.getNumSeqs();
        maxTransSeq=Integer.MIN_VALUE;
        transSeqs = new SeqList(new String[0], new String[0]);
        if (modelLoaded)
        {
            jTextPane2.setText("Model Loaded :\n"+modelDef+"\n"+model.getSequence()+"\n");
            
        }
        else
        {
            jTextPane2.setText("");
            
        }
        
        
        if(!ORFfounds)
        {
            this.processSequences();
        }
        
        
        
        
        
        
            
            
        if(overlapTerms&&!matchProtein)
        {
            this.overlapDNA();
            loadedTransSeqs=overlappedSeqs;
            jLabel4.setText("Translated Sequences");
        
        
            
            
            seqs=overlappedSeqs;
        }
        numSeqs=seqs.getNumSeqs();    
        this.translate();
        loadedTransSeqs=transSeqs;
        
        if(overlapTerms&&matchProtein)
        {
            this.overlapProtein();
            loadedTransSeqs=overlappedSeqs;
            jLabel4.setText("Translated Sequences");
        
        
            
            
           
        }
        
        }
        
        
        if(modelLoaded)
                {
                    
                    this.alignToModel();
                    this.getMutationsMatrix();
                    this.getMutationsList();
                    
                    
                }
        
        ////////////////////////////:TRY
       
       
                    
       
        
            
            
          this.showSequences();
          translate=false;
          translationDone=true;
          
          
              
        
        
        
    }
    
    
    
    public void processSequences()
    {
        //String displaySeqs="<html><head></head><body><font size=3 face=courier new>";
        StyledDocument doc = jTextPane1.getStyledDocument();
        jTextPane1.setText("");
        //jTextPane1.getDocument().putProperty("i18n", Boolean.TRUE); 
        addStylesToDocument(doc);
        
        
        if(termvectorLoaded)
        {
            this.getTermVector();
            
        }
        if(vectorLoaded)
        {
            this.getVector();
        }
        jLabel4.setText("Processing Sequences");
                    jProgressBar1.setVisible(true);
                    jProgressBar1.setMinimum(0);
                    jProgressBar1.setMaximum(100);
                    jProgressBar1.setStringPainted(true);
                    this.update(this.getGraphics());
        for(int i=0;i<numSeqs;i++)
        
        {
            seq=loadedSeqs.returnSeq(i);
            String def=loadedSeqs.returnDef(i);
            jProgressBar1.setValue(100*i/loadedSeqs.getNumSeqs());
                        this.update(this.getGraphics());
        
            
            
           
            //safeguard to allow user not to enter a range
           
                
                start = 0;
                
            
            
           //resets field to seq default values 
           
            
           //safeguard to allow user not to enter a range
           
            
            //resets field to seq default values
            
            
            
            
                start=0;
                end=seq.length();
                
                
                
                if(loadedSeqs.returnDef(i).indexOf(TERMString)!=-1)
                    {
                        mySequence = new Sequence(seq,!reverse,readFrameNum,start,end,strand);
                        mySequence.setNumNs(ncutoff);
                        if(termvectorLoaded)
                        {
                            
                            
                            if(termProteinVector)
                            {
                                
                                termvector=termProtVector;
                                termvectorStart=termProtVectorStart;
                                int goodFrame = 0;
                                int goodVectorIndex = 0;
                                
                                
                                for(int j=1;j<4;j++)
                                {
                                    
                                    
                                    
                                    
                                    Sequence termvectorSeq = new Sequence(seq,!reverse,j,0,seq.length(),strand, mismatches);
                                    termvectorSeq.processSequence();
                                    termvectorSeq.getComplementaryStrand();
                                    myTranslator = new Translator(termvectorSeq.getCodonsArray());
                                    myTranslator.translate(code);
                                    myTranslator.processStartCodons();
                                    String transtermVectorSeq=myTranslator.getTranslatedSequence();
                                    if(transtermVectorSeq.indexOf(termvector)!=-1)
                                    {
                                        goodFrame = j;
                                        goodVectorIndex = (transtermVectorSeq.indexOf(termvector))*3-1;
                                        
                                        termvector=termvectorSeq.getSequence().substring(goodVectorIndex,goodVectorIndex+termvector.length()*3);
                                        Sequence tmptermvectorseq = new Sequence(termvector,true,1,0,termvector.length(),1);
                                        tmptermvectorseq.processSequence();
                                        tmptermvectorseq.getComplementaryStrand();
                                        termvector = tmptermvectorseq.getSequence();
                                        
                                        termvectorStart=3*termvectorStart+termvector.length()-1;
                                        //System.out.println("\n\n\nVECTOR START = "+termvectorStart);
                                        break;
                                    }
                
        
                                    
                                    
                                    
                                    
                                    
                                    
                                }
                                
                                if(goodFrame==0)
                                {
                                    termvector = "AAACCACTTT";
                                    termvectorStart=28;
                                    //System.out.println("\n\n\nPROTEIN VECTOR NOT FOUND");
                                }
                            }
                            
                            
                            
                            
                            
                            
                            
                            
                            mySequence.setTermVector(termvector, termvectorStart);
                        }
                        //displaySeqs+=loadedSeqs.returnDef(i)+"<br>"+loadedSeqs.returnFormattedSeq(i).replaceFirst(loadedSeqs.returnFormattedSeq(i).substring(0,loadedSeqs.returnFormattedSeq(i).indexOf(termvector)),"<font size=3 face=Courier new color=red>"+loadedSeqs.returnFormattedSeq(i).substring(0,loadedSeqs.returnFormattedSeq(i).indexOf(termvector))+"</font>").replaceFirst(loadedSeqs.returnFormattedSeq(i).substring(loadedSeqs.returnFormattedSeq(i).indexOf(termvector),loadedSeqs.returnFormattedSeq(i).indexOf(termvector)+termvectorStart),"<font size=3 face=Courier new color=blue>"+loadedSeqs.returnFormattedSeq(i).substring(loadedSeqs.returnFormattedSeq(i).indexOf(termvector),loadedSeqs.returnFormattedSeq(i).indexOf(termvector)+termvectorStart)+"</font>").replaceFirst(loadedSeqs.returnFormattedSeq(i).substring(loadedSeqs.returnFormattedSeq(i).indexOf(termvector)+termvectorStart),"<font size=3 face=Courier new color=green>"+loadedSeqs.returnFormattedSeq(i).substring(loadedSeqs.returnFormattedSeq(i).indexOf(termvector)+termvectorStart)+"</font>").replaceAll("\n","<br>")+"<br>";
                        //displaySeqs+=loadedSeqs.returnDef(i)+"<br>"+loadedSeqs.returnSeq(i).replaceFirst(loadedSeqs.returnSeq(i).substring(0,loadedSeqs.returnSeq(i).indexOf(termvector)),"<font size=3 face=Courier new color=red>"+loadedSeqs.returnSeq(i).substring(0,loadedSeqs.returnSeq(i).indexOf(termvector))+"</font>").replaceFirst(loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(termvector),loadedSeqs.returnSeq(i).indexOf(termvector)+termvectorStart),"<font size=3 face=Courier new color=blue>"+loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(termvector),loadedSeqs.returnSeq(i).indexOf(termvector)+termvectorStart)+"</font>").replaceFirst(loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(termvector)+termvectorStart),"<font size=3 face=Courier new color=green>"+loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(termvector)+termvectorStart)+"</font>")+"<br>";
                        
                        // System.out.println(loadedSeqs.returnFormattedSeq(i));
                        //System.out.println("\n\nHTML TRY\ntermvector index="+loadedSeqs.returnFormattedSeq(i).indexOf(termvector)+"\n");
                        mySequence.weed5PrimeEnd();
                        try
                       {
                            
                            
                           doc.insertString(doc.getLength(), loadedSeqs.returnDef(i)+"\n",
                                 doc.getStyle("regular"));
                           
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(0,loadedSeqs.returnSeq(i).indexOf(termvector)),
                                 doc.getStyle("red"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(termvector),loadedSeqs.returnSeq(i).indexOf(termvector)+termvectorStart),
                                 doc.getStyle("green"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(termvector)+termvectorStart,mySequence.getPosN()),
                                 doc.getStyle("blue"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(mySequence.getPosN())+"\n",
                                 doc.getStyle("red"));
                           //System.out.println("posN = "+mySequence.getPosN());
                       }
                       
                       catch (BadLocationException ble) {
                        System.err.println("Couldn't insert initial text into text pane.");
        }
                        catch (StringIndexOutOfBoundsException sioobe)
                
                {
                                                                    //final int mismatches = 1;
    final String text = seq;
    final String pattern = termvector;    
    int counts=0; 
    for(int iter = 0; iter < text.length() - pattern.length() + 1; iter++)
    {
        int missed = 0;
        int ator = 0;

        do
        {
            if(text.charAt(iter + ator) != pattern.charAt(ator))
            {
                missed++;
            }
        }while(++ator < pattern.length() && missed <= mismatches);

        if(missed <= mismatches)
        {
            System.out.println("Index: " + iter + " Pattern: " + text.substring(iter, iter + pattern.length()));
            counts++;
            if(counts==1)
            {
                try
                {
                    //doc.insertString(doc.getLength(),loadedSeqs.returnDef(i)+"\n",
                     //            doc.getStyle("regular"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(0,iter),
                                 doc.getStyle("red"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(iter,iter+termvectorStart),
                                 doc.getStyle("green"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(iter+termvectorStart,mySequence.getPosN()),
                                 doc.getStyle("blue"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(mySequence.getPosN())+"\n",
                                 doc.getStyle("red"));
                }
                 catch (BadLocationException ble) {
                        System.err.println("Couldn't insert initial text into text pane.");
                        
                        
        }
                catch (StringIndexOutOfBoundsException sioobe2) {
                       
                    try
                    {
                        doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(0,mySequence.getPosN()),
                                 doc.getStyle("blue"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(mySequence.getPosN())+"\n",
                                 doc.getStyle("red"));
                    }
                    catch (BadLocationException ble) {
                        System.err.println("Couldn't insert initial text into text pane.");
        }
                    
                    
                    
        }
                
                
            }
        }
    }
                    
                }
                        mySequence.getComplementaryStrand();
                        
                    }
                else
                {
                    mySequence = new Sequence(seq,reverse,readFrameNum,start,end,strand,mismatches);
                    mySequence.setNumNs(ncutoff);
                }
                
                if(loadedSeqs.returnDef(i).indexOf(TERMString)==-1)
               {
                    
                    if(vectorLoaded)
                        {
                            if(proteinVector)
                            {
                                
                                vector=protVector;
                                vectorStart=protVectorStart;
                                int goodFrame = 0;
                                int goodVectorIndex = 0;
                                
                                
                                for(int j=1;j<4;j++)
                                {
                                    
                                    
                                    
                                    
                                    Sequence vectorSeq = new Sequence(seq,reverse,j,0,seq.length(),strand, mismatches);
                                    vectorSeq.processSequence();
                                    myTranslator = new Translator(vectorSeq.getCodonsArray());
                                    myTranslator.translate(code);
                                    myTranslator.processStartCodons();
                                    String transVectorSeq=myTranslator.getTranslatedSequence();
                                    if(transVectorSeq.indexOf(vector)!=-1)
                                    {
                                        goodFrame = j;
                                        goodVectorIndex = (transVectorSeq.indexOf(vector))*3;
                                        vector=vectorSeq.getSequence().substring(goodVectorIndex,goodVectorIndex+vector.length()*3);
                                        vectorStart=3*vectorStart+vector.length();
                                        //System.out.println("\n\n\nVECTOR START = "+vectorStart);
                                        break;
                                    }
                                   
        
                                    
                                    
                                    
                                    
                                    
                                    
                                }
                                
                                if(goodFrame==0)
                                {
                                    vector = "AAAAAGCA";
                                    vectorStart=14;
                                }
                            }
                            
                            mySequence.setVector(vector, vectorStart);
                            //System.out.println(vector);
                        }
                        //System.out.println(loadedSeqs.returnFormattedSeq(i));
                        
                        //displaySeqs+=loadedSeqs.returnDef(i)+"<br>"+loadedSeqs.returnFormattedSeq(i).replaceFirst(vector,"<font size=3 face=Courier new color=blue>"+vector+"</font>").replaceAll("\n","<br>")+"<br>";
                        //System.out.println(loadedSeqs.returnFormattedSeq(i).indexOf(vector));
                        //displaySeqs+=loadedSeqs.returnDef(i)+"<br>"+loadedSeqs.returnFormattedSeq(i).replaceFirst(loadedSeqs.returnFormattedSeq(i).substring(0,loadedSeqs.returnFormattedSeq(i).indexOf(vector)),"<font size=3 face=Courier new color=red>"+loadedSeqs.returnFormattedSeq(i).substring(0,loadedSeqs.returnFormattedSeq(i).indexOf(vector))+"</font>").replaceFirst(loadedSeqs.returnFormattedSeq(i).substring(loadedSeqs.returnFormattedSeq(i).indexOf(vector),loadedSeqs.returnFormattedSeq(i).indexOf(vector)+vectorStart),"<font size=3 face=Courier new color=blue>"+loadedSeqs.returnFormattedSeq(i).substring(loadedSeqs.returnFormattedSeq(i).indexOf(vector),loadedSeqs.returnFormattedSeq(i).indexOf(vector)+vectorStart)+"</font>").replaceFirst(loadedSeqs.returnFormattedSeq(i).substring(loadedSeqs.returnFormattedSeq(i).indexOf(vector)+vectorStart),"<font size=3 face=Courier new color=green>"+loadedSeqs.returnFormattedSeq(i).substring(loadedSeqs.returnFormattedSeq(i).indexOf(vector)+vectorStart)+"</font>").replaceAll("\n","<br>")+"<br>";
                       //displaySeqs+=loadedSeqs.returnDef(i)+"</font><br>"+loadedSeqs.returnSeq(i).replaceFirst(loadedSeqs.returnSeq(i).substring(0,loadedSeqs.returnSeq(i).indexOf(vector)),"<font size=3 face=Courier new color=red>"+loadedSeqs.returnSeq(i).substring(0,loadedSeqs.returnSeq(i).indexOf(vector))+"</font>").replaceFirst(loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(vector),loadedSeqs.returnSeq(i).indexOf(vector)+vectorStart),"<font size=3 face=Courier new color=blue>"+loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(vector),loadedSeqs.returnSeq(i).indexOf(vector)+vectorStart)+"</font>").replaceFirst(loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(vector)+vectorStart),"<font size=3 face=Courier new color=green>"+loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(vector)+vectorStart)+"</font>")+"<br><font size=3 face = Courier new>";
                       
                        
                        
                        
                        //System.out.println("\n\nHTML TRY\nvector index="+loadedSeqs.returnFormattedSeq(i).indexOf(vector)+"\n"+loadedSeqs.returnFormattedSeq(i).replaceAll(vector,"VECTOR"));
                        
                    if(strand==1)
                {
                    mySequence.getComplementaryStrand();
                    
                }
                    mySequence.weed();
                    
                    try
                       {
                           doc.insertString(doc.getLength(),loadedSeqs.returnDef(i)+"\n",
                                 doc.getStyle("regular"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(0,loadedSeqs.returnSeq(i).indexOf(vector)),
                                 doc.getStyle("red"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(vector),loadedSeqs.returnSeq(i).indexOf(vector)+vectorStart),
                                 doc.getStyle("green"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(loadedSeqs.returnSeq(i).indexOf(vector)+vectorStart,mySequence.getPosN()),
                                 doc.getStyle("blue"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(mySequence.getPosN())+"\n",
                                 doc.getStyle("red"));
                           //System.out.println("posN = "+mySequence.getPosN());
                       }
                       
                       catch (BadLocationException ble) {
                        System.err.println("Couldn't insert initial text into text pane.");
        }
                    catch (StringIndexOutOfBoundsException sioobe)
                
                {
                    
                    // allows 1 mismatch in vector sequence if not found; takes first hit as vector at beginning                
                                                    //final int mismatches = 1;
    final String text = seq;
    final String pattern = vector;    
    int counts=0; 
    for(int iter = 0; iter < text.length() - pattern.length() + 1; iter++)
    {
        int missed = 0;
        int ator = 0;

        do
        {
            if(text.charAt(iter + ator) != pattern.charAt(ator))
            {
                missed++;
            }
        }while(++ator < pattern.length() && missed <= mismatches);

        if(missed <= mismatches)
        {
            System.out.println("Index: " + iter + " Pattern: " + text.substring(iter, iter + pattern.length()));
            counts++;
            if(counts==1)
            {
                try
                {
                    //doc.insertString(doc.getLength(),loadedSeqs.returnDef(i)+"\n",
                     //            doc.getStyle("regular"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(0,iter),
                                 doc.getStyle("red"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(iter,iter+vectorStart),
                                 doc.getStyle("green"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(iter+vectorStart,mySequence.getPosN()),
                                 doc.getStyle("blue"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(mySequence.getPosN())+"\n",
                                 doc.getStyle("red"));
                }
                 catch (BadLocationException ble) {
                        System.err.println("Couldn't insert initial text into text pane.");
        }
                catch (StringIndexOutOfBoundsException sioobe2) {
                       
                    try
                    {
                        doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(0,mySequence.getPosN()),
                                 doc.getStyle("blue"));
                           doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(mySequence.getPosN())+"\n",
                                 doc.getStyle("red"));
                    }
                    catch (BadLocationException ble) {
                        System.err.println("Couldn't insert initial text into text pane.");
        }
                    
                    
                    
        }
            }
            
        }
    }
                    
                    
                    
                    
                    
                    
                    
                    //try
                   // {
                  //      doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(0, mySequence.getPosN()),
                 //                doc.getStyle("blue"));
                 //       doc.insertString(doc.getLength(), loadedSeqs.returnSeq(i).substring(mySequence.getPosN())+"\n\n",
                 //                doc.getStyle("red"));
                 //   }
                //    catch (BadLocationException ble) {
                 //       System.err.println("Couldn't insert initial text into text pane.");
      //  }
                    
                }
               }
                
                //System.out.println("vector index=")
                mySequence.processSequence();
                seqs.setStr(mySequence.getSequence(), i);
                
            }       
                    
                    //displaySeqs+="</font></body></html>";
                    //jTextPane1.getDocument().putProperty("i18n", true);
            
                    //jScrollPane1.setHorizontalScrollBarPolicy(jScrollPane1.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    //jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                    
                    //System.out.println(displaySeqs);
                    jProgressBar1.setValue(100);
                    jProgressBar1.setVisible(false);
                    jLabel4.setText("Information");
                    //jTextPane1.setText(displaySeqs);
                    //jScrollPane1.remove(jTextPane1);
                    //jTextPane1.remove(jScrollPane1);
                    //jTextPane1.add(jScrollPane1);
                    //jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                    //jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    //jScrollPane1.add(jTextPane1);
                    
                    
                    jTextPane1.setCaretPosition(0);
                   // jScrollPane1.setViewportView(jPanel2);
                    
                    //this.update(this.getGraphics());
    }
    
    
    
    
    public void translate()
    {
        jLabel4.setText("Translating");
                    jProgressBar1.setVisible(true);
                    jProgressBar1.setMinimum(0);
                    jProgressBar1.setMaximum(100);
                    jProgressBar1.setStringPainted(true);
                    this.update(this.getGraphics());
        
        
        for(int i=0;i<seqs.getNumSeqs();i++)
            {
            jProgressBar1.setValue(100*i/seqs.getNumSeqs());
                        this.update(this.getGraphics());
                        
            seq=seqs.returnSeq(i);
            def=seqs.returnDef(i);
            mySequence = new Sequence(seq,reverse,readFrameNum,start,end,strand);
            
            
            try
                {
                
                myTranslator = new Translator(mySequence.getCodonsArray());
                myTranslator.translate(code);
                myTranslator.processStartCodons();
            
            //myTranslator.proteinToString(10);
                transSeq=myTranslator.getTranslatedSequence();
                if((def+"\n"+transSeq+"\n").length()>maxTransSeq)
                    {
                        maxTransSeq=(def+"\n"+transSeq+"\n").length();
                    }
                
                
                
                
                
                transSeqs.addDef(def);
                
                transSeqs.addStr(myTranslator.getFastaProtSeq());
                
                numSeqs=transSeqs.getNumSeqs();
                
                
                    
            
            
            
            
                //safeguard, if range specified too small for translation/ chosen reading frame doesn't allow it
                   
            
                //prints out details and sequence, reitinitialising JEditorPane
                    
                
                
            }
            
            catch(NumberFormatException nfe)
            {
                jOptionPane1.showMessageDialog(this,"Please enter valid numbers" , "Error", JOptionPane.ERROR_MESSAGE);
            
            //resets fields to seq default values
            
            }
             
            catch(StringIndexOutOfBoundsException sioobe)
            {
            
            jOptionPane1.showMessageDialog(this,"The range you have entered is incorrect" , "Error", JOptionPane.ERROR_MESSAGE);
            
            //resets fields to seq default values
            
            }
            
        }
        jProgressBar1.setValue(100);
                    jProgressBar1.setVisible(false);
    }
    
    
    
    public void alignToModel()
    {
                    
        seqs=overlappedDNASeqs;            
        jLabel4.setText("Aligning to model");
                    jProgressBar1.setVisible(true);
                    jProgressBar1.setMinimum(0);
                    jProgressBar1.setMaximum(100);
                    jProgressBar1.setStringPainted(true);
                    this.update(this.getGraphics());
                    SeqList tmpNewSeqs = new SeqList(new String[0],new String[0]);
                    SeqList tmpDefs = new SeqList(new String[0],new String[0]);
                    for(int i=0;i<transSeqs.getNumSeqs();i++)
                    {
                        jProgressBar1.setValue(100*i/transSeqs.getNumSeqs());
                        this.update(this.getGraphics());
                    
                    scrap=false;
                    int alignstart=0;
                    int alignlength=0;
                    double alignscore=0;
                    int modelstart=0;
                    maxTransSeq=Integer.MIN_VALUE;
                    try
                        {
                            
                        
                        org.biojava.bio.seq.Sequence query = org.biojava.bio.seq.ProteinTools.createProteinSequence(transSeqs.returnSeq(i).replace('>', 'M'), "TransSeq");
                        org.biojava.bio.seq.Sequence target = org.biojava.bio.seq.ProteinTools.createProteinSequence(model.getSequence().replace('>', 'M'), "Model");
                        FiniteAlphabet alphabet = (FiniteAlphabet) AlphabetManager.alphabetForName("PROTEIN-TERM");
                        SubstitutionMatrix matrix = new SubstitutionMatrix(alphabet, "#  Matrix made by matblas from blosum62.iij\n#  * column uses minimum score\n#  BLOSUM Clustered Scoring Matrix in 1/2 Bit Units\n#  Blocks Database = /data/blocks_5.0/blocks.dat\n#  Cluster Percentage: >= 62\n#  Entropy =   0.6979, Expected =  -0.5209\n   A  R  N  D  C  Q  E  G  H  I  L  K  M  F  P  S  T  W  Y  V  B  Z  X  *\nA  4 -1 -2 -2  0 -1 -1  0 -2 -1 -1 -1 -1 -2 -1  1  0 -3 -2  0 -2 -1  0 -4 \nR -1  5  0 -2 -3  1  0 -2  0 -3 -2  2 -1 -3 -2 -1 -1 -3 -2 -3 -1  0 -1 -4 \nN -2  0  6  1 -3  0  0  0  1 -3 -3  0 -2 -3 -2  1  0 -4 -2 -3  3  0 -1 -4 \nD -2 -2  1  6 -3  0  2 -1 -1 -3 -4 -1 -3 -3 -1  0 -1 -4 -3 -3  4  1 -1 -4 \nC  0 -3 -3 -3  9 -3 -4 -3 -3 -1 -1 -3 -1 -2 -3 -1 -1 -2 -2 -1 -3 -3 -2 -4 \nQ -1  1  0  0 -3  5  2 -2  0 -3 -2  1  0 -3 -1  0 -1 -2 -1 -2  0  3 -1 -4 \nE -1  0  0  2 -4  2  5 -2  0 -3 -3  1 -2 -3 -1  0 -1 -3 -2 -2  1  4 -1 -4 \nG  0 -2  0 -1 -3 -2 -2  6 -2 -4 -4 -2 -3 -3 -2  0 -2 -2 -3 -3 -1 -2 -1 -4 \nH -2  0  1 -1 -3  0  0 -2  8 -3 -3 -1 -2 -1 -2 -1 -2 -2  2 -3  0  0 -1 -4 \nI -1 -3 -3 -3 -1 -3 -3 -4 -3  4  2 -3  1  0 -3 -2 -1 -3 -1  3 -3 -3 -1 -4 \nL -1 -2 -3 -4 -1 -2 -3 -4 -3  2  4 -2  2  0 -3 -2 -1 -2 -1  1 -4 -3 -1 -4 \nK -1  2  0 -1 -3  1  1 -2 -1 -3 -2  5 -1 -3 -1  0 -1 -3 -2 -2  0  1 -1 -4 \nM -1 -1 -2 -3 -1  0 -2 -3 -2  1  2 -1  5  0 -2 -1 -1 -1 -1  1 -3 -1 -1 -4 \nF -2 -3 -3 -3 -2 -3 -3 -3 -1  0  0 -3  0  6 -4 -2 -2  1  3 -1 -3 -3 -1 -4 \nP -1 -2 -2 -1 -3 -1 -1 -2 -2 -3 -3 -1 -2 -4  7 -1 -1 -4 -3 -2 -2 -1 -2 -4 \nS  1 -1  1  0 -1  0  0  0 -1 -2 -2  0 -1 -2 -1  4  1 -3 -2 -2  0  0  0 -4 \nT  0 -1  0 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -2 -1  1  5 -2 -2  0 -1 -1  0 -4 \nW -3 -3 -4 -4 -2 -2 -3 -2 -2 -3 -2 -3 -1  1 -4 -3 -2 11  2 -3 -4 -3 -2 -4 \nY -2 -2 -2 -3 -2 -1 -2 -3  2 -1 -1 -2 -1  3 -3 -2 -2  2  7 -1 -3 -2 -1 -4 \nV  0 -3 -3 -3 -1 -2 -2 -3 -3  3  1 -2  1 -1 -2 -2  0 -3 -1  4 -3 -2 -1 -4 \nB -2 -1  3  4 -3  0  1 -1  0 -3 -4  0 -3 -3 -2  0 -1 -4 -3 -3  4  1 -1 -4 \nZ -1  0  0  1 -3  3  4 -2  0 -3 -3  1 -1 -3 -1  0 -1 -3 -2 -2  1  4 -1 -4 \nX  0 -1 -1 -1 -2 -1 -1 -1 -1 -1 -1 -1 -1 -1 -2  0  0 -2 -1 -1 -1 -1 -1 -4 \n* -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4  1 ", "BLOSUM62");

                        org.biojava.bio.alignment.SequenceAlignment aligner = new org.biojava.bio.alignment.SmithWaterman( 
        0, 	// match
        3,	// replace
        10000,      // insert
        10000,	// delete
        10000,      // gapExtend
        matrix 	// SubstitutionMatrix
      );
                        aligner.pairwiseAlignment(
        query, // first sequence
        target // second one
      );
                        String alignment = aligner.getAlignmentString();
                        //System.out.println(alignment);
                        StringTokenizer st = new StringTokenizer(alignment);
                        StringTokenizer st2 = new StringTokenizer(alignment);
                        StringTokenizer st3 = new StringTokenizer(alignment);
                        StringTokenizer st4 = new StringTokenizer(alignment);
                        
                        while(st4.hasMoreTokens())
                        {
                            try
                            {
                                
                                
                                if(st4.nextToken().equals("Target:")&&(modelstart=Integer.parseInt(st4.nextToken()))>=0)
                                {
                                    break;
                                }
                                
                                    
                                        
                            }
                            catch(NumberFormatException nfe)
                                {
                                    
                                }
                        }
                        
                        
                        
                        
                        while(st2.hasMoreTokens())
                        {
                            try
                            {
                                if(st.nextToken().equals("Time"))
                                {
                                    st.nextToken();
                                    st.nextToken();
                                    if(st.nextToken().equals("Length:"))
                                    {
                                        alignlength=Integer.parseInt(st.nextToken());
                                       //System.out.println("PROTEIN ALIGNMENT LENGTH="+alignlength+"\nMODEL LOADED="+modelLoaded);
                                        break;
                                    }
                                }
                            }
                            catch(NumberFormatException nfe)
                            {
                                
                            }
                        }
                        
                        while(st.hasMoreTokens())
                        {
                            try
                            {
                                
                                
                                if(st.nextToken().equals("Query:")&&(alignstart=Integer.parseInt(st.nextToken()))>=0)
                                {
                                    break;
                                }
                                
                                    
                                        
                            }
                            catch(NumberFormatException nfe)
                                {
                                    
                                }
                        }
                        while(st3.hasMoreTokens())
                        {
                            try
                            {
                                
                                
                                if(st3.nextToken().equals("Score:")&&(alignscore=Double.parseDouble(st3.nextToken()))>=0)
                                {
                                    //System.out.println("\nSCORE:"+alignscore);
                                    break;
                                }
                                
                                    
                                        
                            }
                            catch(NumberFormatException nfe)
                                {
                                    
                                }
                        }
                        
                        //if(alignlength>=model.getSequence().length())
                        if(alignscore>modelscore)
                    {
                        tmpDefs.addDef(transSeqs.returnDef(i));
                        tmpDefs.addStr(GFormat.padLeft("",modelstart-1)+transSeqs.returnSeq(i).substring(alignstart-1, alignlength+alignstart-1));
                        tmpNewSeqs.addDef(transSeqs.returnDef(i));
                        tmpNewSeqs.addStr(seqs.returnSeq(i).substring((alignstart-1)*3, (alignlength+alignstart-1)*3));
                        //System.out.println("Model Start: "+modelstart);
                        //System.out.println("PROUUUUUUUUUUUUUT\n"+model.getSequence().substring((modelstart-1)*3, alignlength*3)+"\n"+model.getSequence());
                        //System.out.println(model.getSequence());
                        
                        
                        //System.out.println(GFormat.padLeft(transSeqs.returnSeq(i).substring(alignstart-1, alignlength+alignstart-1),model.getSequence().length()));
                        scrap=false;
                        
                        
                    }
                        else
                        {
                            scrap=true;
                        }
                        
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                if(!scrap)
                    {
                    if(transSeqs.returnSeq(i).length()>maxTransSeq)
                    {
                        maxTransSeq=transSeqs.returnSeq(i).length();
                    }
                    
                    
                    }     
                    
                    
                    
                    
                    
                    
                    
                    
                    
                }
                    catch(StringIndexOutOfBoundsException sioobe)
                    {
                        
                    }
                 catch(org.biojava.bio.symbol.IllegalSymbolException ise)
                        {
                            
                        }
                        catch(Exception e)
                        {
                            
                        } 
                    }
                    jProgressBar1.setValue(100);
                    jProgressBar1.setVisible(false);
                    transSeqs=tmpDefs;
                    seqs=tmpNewSeqs;
                    overlappedDNASeqs=tmpNewSeqs;
                    
                    
                    
    }
    
    
    
    public void showSequences()
    {
        numSeqs=transSeqs.getNumSeqs();
        StyledDocument doc2 = jTextPane2.getStyledDocument();
        addStylesToDocument(doc2);
        jTextPane2.setText("");
        
        ////////////////////////////:TRY
        
        maxTransSeq=Integer.MIN_VALUE;
        for(int i=0;i<transSeqs.getNumSeqs();i++)
            {
                if(transSeqs.returnSeq(i).length()>maxTransSeq)
                {
                    maxTransSeq=transSeqs.returnSeq(i).length();
                }
            }
        
         if(transSeqs.getNumSeqs()==0)
                    {
                        maxTransSeq=model.getSequence().length();
                        //System.out.println(jTextPane2.getText());
                        jTextPane2.setText("Sorry, no sequence could align up to model length");
                        jMenuItem2.setEnabled(false);
                        jMenuItem9.setEnabled(false);
                        Go.setEnabled(true);
                        jTextPane2.setCaretPosition(0);
                    
                    }
         else
         {
            
            
    
        //jTextPane2.setEditorKit(new StyledEditorKit());
        jTextPane2.getDocument().putProperty("i18n", Boolean.TRUE);    
       //jTextPane2.getDocument().putProperty("i18n", Boolean.TRUE);  
        
        //System.out.println("BEFORE\n"+jTextPane2.getText());
        String tmp="";
        String tmp2="";
        //String tmp2="<html><head></head><body><font size=3 face=courier>";
        //System.out.println("AFTER\n"+tmp);
        jLabel4.setText("Formatting sequences");
        this.update(this.getGraphics());
        if(modelLoaded)
        {
            try
            {
                if(modelDef.length()<20)
                {
                    doc2.insertString(doc2.getLength(), GFormat.padRight(modelDef,50),
                                 doc2.getStyle("regular"));
                }
                
                else
                {
                    doc2.insertString(doc2.getLength(), GFormat.padRight(modelDef.substring(0,20),50),
                                 doc2.getStyle("regular"));
                }
                
                doc2.insertString(doc2.getLength(), "\n",
                                 doc2.getStyle("regular"));
                
                for(int j=0;j<model.getSequence().length();j++)
                {
                    if((model.getSequence().charAt(j)=='M')||(model.getSequence().charAt(j)=='N')||(model.getSequence().charAt(j)=='C')||(model.getSequence().charAt(j)=='W')||(model.getSequence().charAt(j)=='T')||(model.getSequence().charAt(j)=='S')||(model.getSequence().charAt(j)=='Y'))
                {
                    doc2.insertString(doc2.getLength(), model.getSequence().charAt(j)+"",
                                 doc2.getStyle("green"));
                }
                
                if((model.getSequence().charAt(j)=='F')||(model.getSequence().charAt(j)=='A')||(model.getSequence().charAt(j)=='I')||(model.getSequence().charAt(j)=='P')||(model.getSequence().charAt(j)=='L')||(model.getSequence().charAt(j)=='V')||(model.getSequence().charAt(j)=='G')||(model.getSequence().charAt(j)=='Q'))
                {
                    doc2.insertString(doc2.getLength(), model.getSequence().charAt(j)+"",
                                 doc2.getStyle("red"));
                }
                
                if((model.getSequence().charAt(j)=='D')||(model.getSequence().charAt(j)=='H')||(model.getSequence().charAt(j)=='R')||(model.getSequence().charAt(j)=='K')||(model.getSequence().charAt(j)=='E'))
                {
                    doc2.insertString(doc2.getLength(), model.getSequence().charAt(j)+"",
                                 doc2.getStyle("blue"));
                }
                
                if(model.getSequence().charAt(j)=='-')
                {
                    doc2.insertString(doc2.getLength(), "*",
                                 doc2.getStyle("red"));
                }
                if(model.getSequence().charAt(j)=='X')
                {
                    doc2.insertString(doc2.getLength(), model.getSequence().charAt(j)+"",
                                 doc2.getStyle("regular"));
                }
                }
                doc2.insertString(doc2.getLength(), "\n",
                                 doc2.getStyle("regular"));
                //this.update(this.getGraphics());
            }
            
            catch (BadLocationException ble) {
            System.err.println("Couldn't insert initial text into text pane.");
            }
            
        }
        for(int i=0;i<numSeqs;i++)
        {
            //if(modelLoaded&&i==0)
            //{
                //jTextPane2.setText(jTextPane2.getText().substring(0,jTextPane2.getText().lastIndexOf("<br>")+4)+"<br>");
            //}
            //tmp+="<font size=3 face=Courier New>"+transSeqs.returnDef(i)+"</font>"+"<br>"+transSeqs.returnSeq(i).replaceAll(">", "<font size=3 face=Courier New color=green>M</font>").replaceAll("M", "<font size=3 face=Courier New color=green>M</font>").replaceAll(" ", "")+"<br><br>";
            //tmp+="<font size=3 face=Courier New>"+transSeqs.returnDef(i)+"</font><br>"+transSeqs.returnSeq(i).replaceAll(">", "<font size=3 face=Courier New color=green>M</font>").replaceAll(" ", "").replaceAll("F", "<font size=3 face=Courier New color=red>F</font>").replaceAll("A", "<font size=3 face=Courier New color=red>A</font>").replaceAll("I", "<font size=3 face=Courier New color=red>I</font>").replaceAll("P", "<font size=3 face=Courier New color=red>P</font>").replaceAll("L", "<font size=3 face=Courier New color=red>L</font>").replaceAll("V", "<font size=3 face=Courier New color=red>V</font>").replaceAll("G", "<font size=3 face=Courier New color=red>G</font>").replaceAll("-", "<b><font size=3 face=Courier New color=red>*</font></b>").replaceAll("S", "<font color=green>S</font>").replaceAll("N", "<font size=3 face=Courier New color=green>N</font>").replaceAll("Q", "<font size=3 face=Courier New color=red>Q</font>").replaceAll("C", "<font size=3 face=Courier New color=green>C</font>").replaceAll("W", "<font size=3 face=Courier New color=green>W</font>").replaceAll("T", "<font color=green>T</font>").replaceAll("Y", "<font size=3 face=Courier New color=green>Y</font>").replaceAll("D", "<font color=blue>D</font>").replaceAll("H", "<font color=blue>H</font>").replaceAll("R", "<font size=3 face=Courier New color=blue>R</font>").replaceAll("K", "<font size=3 face=Courier New color=blue>K</font>").replaceAll("X", "<font size=3 face=Courier New>X</font>")+"<br><br>";
            //tmp+=transSeqs.returnDef(i)+"\n"+transSeqs.returnSeq(i).replaceAll(" ", "").replaceAll(">", "<font size=3 face=courier new color=green>M</font>").replaceAll("M", "<font size=3 face=courier new color=green>M</font>").replaceAll("F", "<font size=3 face=courier new color=red>F</font>").replaceAll("A", "<font size=3 face=courier new color=red>A</font>").replaceAll("I", "<font size=3 face=courier new color=red>I</font>").replaceAll("P", "<font size=3 face=courier new color=red>P</font>").replaceAll("L", "<font size=3 face=courier new color=red>L</font>").replaceAll("V", "<font size=3 face=courier new color=red>V</font>").replaceAll("G", "<font size=3 face=courier new color=red>G</font>").replaceAll("-", "<b><font size=3 face=courier new color=red>*</font></b>").replaceAll("S", "<font font size=3 face=courier new color=green>S</font>").replaceAll("N", "<font size=3 face=courier new color=green>N</font>").replaceAll("Q", "<font size=3 face=courier new color=red>Q</font>").replaceAll("C", "<font size=3 face=courier new color=green>C</font>").replaceAll("W", "<font size=3 face=courier new color=green>W</font>").replaceAll("T", "<font size=3 face=courier new color=green>T</font>").replaceAll("Y", "<font size=3 face=courier new color=green>Y</font>").replaceAll("D", "<font size=3 face=courier new color=blue>D</font>").replaceAll("H", "<font size=3 face=courier new color=blue>H</font>").replaceAll("R", "<font size=3 face=courier new color=blue>R</font>").replaceAll("K", "<font size=3 face=courier new color=blue>K</font>").replaceAll("E", "<font size=3 face=courier new color=blue>E</font>").replaceAll("X", "<font size=3 face=courier new>X</font>")+"<br><br>";
            //tmp+="<font size=3 face=courier new>"+transSeqs.returnDef(i)+"<br>"+transSeqs.returnSeq(i).replaceAll(" ", "").replaceAll(">", "M")+"<br><br>";
            //tmp+="<font size=3 face=courier new>"+transSeqs.returnDef(i)+"<br>";
            
            
            try
            {
            doc2.insertString(doc2.getLength(), GFormat.padRight(transSeqs.returnDef(i),50),
                                 doc2.getStyle("regular"));
            doc2.insertString(doc2.getLength(), "\n",
                                 doc2.getStyle("regular"));
            
            for(int j=0;j<transSeqs.returnSeq(i).length();j++)
            {
                
                if((transSeqs.returnSeq(i).charAt(j)=='M')||(transSeqs.returnSeq(i).charAt(j)=='N')||(transSeqs.returnSeq(i).charAt(j)=='C')||(transSeqs.returnSeq(i).charAt(j)=='W')||(transSeqs.returnSeq(i).charAt(j)=='T')||(transSeqs.returnSeq(i).charAt(j)=='S')||(transSeqs.returnSeq(i).charAt(j)=='Y'))
                {
                    doc2.insertString(doc2.getLength(), transSeqs.returnSeq(i).charAt(j)+"",
                                 doc2.getStyle("green"));
                }
                
                if((transSeqs.returnSeq(i).charAt(j)=='F')||(transSeqs.returnSeq(i).charAt(j)=='A')||(transSeqs.returnSeq(i).charAt(j)=='I')||(transSeqs.returnSeq(i).charAt(j)=='P')||(transSeqs.returnSeq(i).charAt(j)=='L')||(transSeqs.returnSeq(i).charAt(j)=='V')||(transSeqs.returnSeq(i).charAt(j)=='G')||(transSeqs.returnSeq(i).charAt(j)=='Q'))
                {
                    doc2.insertString(doc2.getLength(), transSeqs.returnSeq(i).charAt(j)+"",
                                 doc2.getStyle("red"));
                }
                
                if((transSeqs.returnSeq(i).charAt(j)=='D')||(transSeqs.returnSeq(i).charAt(j)=='H')||(transSeqs.returnSeq(i).charAt(j)=='R')||(transSeqs.returnSeq(i).charAt(j)=='K')||(transSeqs.returnSeq(i).charAt(j)=='E'))
                {
                    doc2.insertString(doc2.getLength(), transSeqs.returnSeq(i).charAt(j)+"",
                                 doc2.getStyle("blue"));
                }
                
                if(transSeqs.returnSeq(i).charAt(j)=='-')
                {
                    doc2.insertString(doc2.getLength(), "*",
                                 doc2.getStyle("red"));
                }
                if(transSeqs.returnSeq(i).charAt(j)=='X')
                {
                    doc2.insertString(doc2.getLength(), transSeqs.returnSeq(i).charAt(j)+"",
                                 doc2.getStyle("regular"));
                }
                
                
                
                
            }
            doc2.insertString(doc2.getLength(), "\n",
                                 doc2.getStyle("regular"));
            
            }
            catch (BadLocationException ble) {
            System.err.println("Couldn't insert initial text into text pane.");
        }
            
            
            
            
            //tmp+=transSeqs.returnDef(i)+"\n"+transSeqs.returnSeq(i).replaceAll(">","M").replaceAll("-","*")+"\n\n";
            


//tmp2+=transSeqs.returnSeq(i)+"<br>";
            //tmp2+=transSeqs.returnSeq(i)+"\n";
            /*
            tmp2=tmp2.replaceAll(" ", "");
            tmp2=tmp2.replaceAll(">", "<font size=3 face=courier new color=green>M</font>");
            tmp2=tmp2.replaceAll("M", "<font size=3 face=courier new color=green>M</font>");
            tmp2=tmp2.replaceAll("N", "<font size=3 face=courier new color=green>N</font>");
            tmp2=tmp2.replaceAll("C", "<font size=3 face=courier new color=green>C</font>");
            tmp2=tmp2.replaceAll("W", "<font size=3 face=courier new color=green>W</font>");
            tmp2=tmp2.replaceAll("T", "<font size=3 face=courier new color=green>T</font>");
            tmp2=tmp2.replaceAll("S", "<font font size=3 face=courier new color=green>S</font>");
            tmp2=tmp2.replaceAll("Y", "<font size=3 face=courier new color=green>Y</font>");
            
            tmp2=tmp2.replaceAll("F", "<font size=3 face=courier new color=red>F</font>");
            tmp2=tmp2.replaceAll("A", "<font size=3 face=courier new color=red>A</font>");
            tmp2=tmp2.replaceAll("I", "<font size=3 face=courier new color=red>I</font>");
            tmp2=tmp2.replaceAll("P", "<font size=3 face=courier new color=red>P</font>");
            tmp2=tmp2.replaceAll("L", "<font size=3 face=courier new color=red>L</font>");
            tmp2=tmp2.replaceAll("V", "<font size=3 face=courier new color=red>V</font>");
            tmp2=tmp2.replaceAll("G", "<font size=3 face=courier new color=red>G</font>");
            tmp2=tmp2.replaceAll("-", "<b><font size=3 face=courier new color=red>*</font></b>");
            
            tmp2=tmp2.replaceAll("Q", "<font size=3 face=courier new color=red>Q</font>");
            
            
            tmp2=tmp2.replaceAll("D", "<font size=3 face=courier new color=blue>D</font>");
            tmp2=tmp2.replaceAll("H", "<font size=3 face=courier new color=blue>H</font>");
            tmp2=tmp2.replaceAll("R", "<font size=3 face=courier new color=blue>R</font>");
            tmp2=tmp2.replaceAll("K", "<font size=3 face=courier new color=blue>K</font>");
            tmp2=tmp2.replaceAll("E", "<font size=3 face=courier new color=blue>E</font>");
            tmp2=tmp2.replaceAll("X", "<font size=3 face=courier new>X</font>");
            */
            
        }
            /*tmp2=tmp2.replaceAll(" ", "");
            tmp2=tmp2.replaceAll(">", "<font color=green>M</font>");
tmp2=tmp2.replaceAll("M", "<font color=green>M</font>");
tmp2=tmp2.replaceAll("F", "<font color=red>F</font>");
tmp2=tmp2.replaceAll("A", "<font color=red>A</font>");
tmp2=tmp2.replaceAll("I", "<font color=red>I</font>");
tmp2=tmp2.replaceAll("P", "<font color=red>P</font>");
tmp2=tmp2.replaceAll("L", "<font color=red>L</font>");
tmp2=tmp2.replaceAll("V", "<font color=red>V</font>");
tmp2=tmp2.replaceAll("G", "<font color=red>G</font>");
tmp2=tmp2.replaceAll("-", "<b><font color=red>*</font></b>");
tmp2=tmp2.replaceAll("S", "<font font color=green>S</font>");
tmp2=tmp2.replaceAll("N", "<font color=green>N</font>");
tmp2=tmp2.replaceAll("Q", "<font color=red>Q</font>");
tmp2=tmp2.replaceAll("C", "<font color=green>C</font>");
tmp2=tmp2.replaceAll("W", "<font color=green>W</font>");
tmp2=tmp2.replaceAll("T", "<font color=green>T</font>");
tmp2=tmp2.replaceAll("Y", "<font color=green>Y</font>");
tmp2=tmp2.replaceAll("D", "<font color=blue>D</font>");
tmp2=tmp2.replaceAll("H", "<font color=blue>H</font>");
tmp2=tmp2.replaceAll("R", "<font color=blue>R</font>");
tmp2=tmp2.replaceAll("K", "<font color=blue>K</font>");
tmp2=tmp2.replaceAll("E", "<font color=blue>E</font>");
tmp2=tmp2.replaceAll("X", "<font>X</font>");
tmp2+="</body></html>";;
        
            tmp+="</body></html>";
            */
            //jTextPane2.setText(tmp);
            //jTextPane2.setText(tmp2);
            //jTextPane2.select(1,50);
            //jTextPane2.setSelectedTextColor(Color.red);
            //jTextPane2.repaint();
            
            //jTextPane2.selectAll();
            //System.out.println("POMPOMPOM\n"+jTextPane2.getSelectedText());
            //System.out.println("length="+jTextPane2.getSelectedText().length());
            
            //jTextPane2.setText(tmp.replaceAll("M", "<font size=3 face=courier new color=green>M</font>").replaceAll("F", "<font size=3 face=courier new color=red>F</font>").replaceAll("A", "<font size=3 face=courier new color=red>A</font>").replaceAll("I", "<font size=3 face=courier new color=red>I</font>").replaceAll("P", "<font size=3 face=courier new color=red>P</font>").replaceAll("L", "<font size=3 face=courier new color=red>L</font>").replaceAll("V", "<font size=3 face=courier new color=red>V</font>").replaceAll("G", "<font size=3 face=courier new color=red>G</font>").replaceAll("-", "<b><font size=3 face=courier new color=red>*</font></b>").replaceAll("S", "<font font size=3 face=courier new color=green>S</font>").replaceAll("N", "<font size=3 face=courier new color=green>N</font>").replaceAll("Q", "<font size=3 face=courier new color=red>Q</font>").replaceAll("C", "<font size=3 face=courier new color=green>C</font>").replaceAll("W", "<font size=3 face=courier new color=green>W</font>").replaceAll("T", "<font size=3 face=courier new color=green>T</font>").replaceAll("Y", "<font size=3 face=courier new color=green>Y</font>").replaceAll("D", "<font size=3 face=courier new color=blue>D</font>").replaceAll("H", "<font size=3 face=courier new color=blue>H</font>").replaceAll("R", "<font size=3 face=courier new color=blue>R</font>").replaceAll("K", "<font size=3 face=courier new color=blue>K</font>").replaceAll("E", "<font size=3 face=courier new color=blue>E</font>").replaceAll("X", "<font size=3 face=courier new>X</font>")+"<br><br>");
            //System.out.println("RESULT\n"+tmp.replaceAll("M", "<font size=3 face=courier new color=green>M</font>").replaceAll("F", "<font size=3 face=courier new color=red>F</font>").replaceAll("A", "<font size=3 face=courier new color=red>A</font>").replaceAll("I", "<font size=3 face=courier new color=red>I</font>").replaceAll("P", "<font size=3 face=courier new color=red>P</font>").replaceAll("L", "<font size=3 face=courier new color=red>L</font>").replaceAll("V", "<font size=3 face=courier new color=red>V</font>").replaceAll("G", "<font size=3 face=courier new color=red>G</font>").replaceAll("-", "<b><font size=3 face=courier new color=red>*</font></b>").replaceAll("S", "<font font size=3 face=courier new color=green>S</font>").replaceAll("N", "<font size=3 face=courier new color=green>N</font>").replaceAll("Q", "<font size=3 face=courier new color=red>Q</font>").replaceAll("C", "<font size=3 face=courier new color=green>C</font>").replaceAll("W", "<font size=3 face=courier new color=green>W</font>").replaceAll("T", "<font size=3 face=courier new color=green>T</font>").replaceAll("Y", "<font size=3 face=courier new color=green>Y</font>").replaceAll("D", "<font size=3 face=courier new color=blue>D</font>").replaceAll("H", "<font size=3 face=courier new color=blue>H</font>").replaceAll("R", "<font size=3 face=courier new color=blue>R</font>").replaceAll("K", "<font size=3 face=courier new color=blue>K</font>").replaceAll("E", "<font size=3 face=courier new color=blue>E</font>").replaceAll("X", "<font size=3 face=courier new>X</font>")+"<br><br>");
            jTextPane2.setCaretPosition(0);
        if(modelLoaded)
        {
            jButton2.setEnabled(true);
            jButton3.setEnabled(true);
        }
        jMenuItem2.setEnabled(true);
        jMenuItem9.setEnabled(true);
        isTranslated=true;
        Go.setEnabled(false);
        jButton5.setEnabled(true);
        jLabel4.setText("Translated Sequences");
         }
        this.update(this.getGraphics());
    }
    
    
    
    
    
    
    public static void main(String args[])
    {
        TwoHybridTools myGUI = new TwoHybridTools();
        myGUI.setSize(755,700);
        myGUI.show();
        myGUI.centreFrame(myGUI);
        myGUI.Go.setEnabled(false);
    }
    
    //Centre frame. Frame passes as parameter, and returned centered
    public void overlapProtein()
    {
        jLabel4.setText("Overlapping Sequences");
        this.update(this.getGraphics());         
        ADSeqs = new SeqList(new String[0], new String[0]);
        TERMSeqs = new SeqList(new String[0], new String[0]);
        overlappedSeqs = new SeqList(new String[0], new String[0]);
        SeqList ADSeqsDNA = new SeqList(new String[0], new String [0]);
        SeqList TERMSeqsDNA = new SeqList(new String[0], new String [0]);
        overlappedDNASeqs = new SeqList(new String[0], new String[0]);    
        for(int i=0;i<numSeqs;i++)
        {
            String tmpDef=transSeqs.returnDef(i);
            String tmpSeq=transSeqs.returnSeq(i);
            String tmpDNASeq=seqs.returnSeq(i);
            if(tmpDef.indexOf(ADString)!=-1)
            {
                ADSeqs.addDef(tmpDef.substring(0, tmpDef.indexOf(ADString)));
                ADSeqs.addStr(tmpSeq);
                ADSeqsDNA.addDef(tmpDef.substring(0, tmpDef.indexOf(ADString)));
                ADSeqsDNA.addStr(tmpDNASeq);
                
            }
            if(tmpDef.indexOf(TERMString)!=-1)
            {
                TERMSeqs.addDef(tmpDef.substring(0, tmpDef.indexOf(TERMString)));
                TERMSeqs.addStr(tmpSeq);
                TERMSeqsDNA.addDef(tmpDef.substring(0, tmpDef.indexOf(TERMString)));
                TERMSeqsDNA.addStr(tmpDNASeq);
                
            }
            if(tmpDef.indexOf(ADString)==-1&&tmpDef.indexOf(TERMString)==-1)
            {
                overlappedSeqs.addDef(tmpDef);
                overlappedSeqs.addStr(tmpSeq);
                overlappedDNASeqs.addDef(tmpDef);
                overlappedDNASeqs.addStr(tmpDNASeq);
            }
                        
        }    
            
        jProgressBar1.setVisible(true);
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum(100);
        jProgressBar1.setStringPainted(true);
        
        for(int i=0;i<ADSeqs.getNumDefs();i++)
        {
            jProgressBar1.setValue(100*i/ADSeqs.getNumDefs());
            this.update(this.getGraphics());
            int alignstart=0;
            int alignlength=0;
            int termstart=0;
            boolean foundTERM=false;
            
            if(i==11)
                {
                    //System.out.print("BOOO");
                }
            
            
            
            for(int j=0;j<TERMSeqs.getNumDefs();j++)
            {
                
                
                
                if(ADSeqs.returnDef(i).equals(TERMSeqs.returnDef(j)))
                {
                    String overlap="";
                    
                        String ADString = ADSeqs.returnSeq(i);
                        String TERMString = TERMSeqs.returnSeq(j);
                        
                         try
                        {
                            
                        
                        org.biojava.bio.seq.Sequence ADSeq = org.biojava.bio.seq.ProteinTools.createProteinSequence(ADString, "ADSeq");
                        org.biojava.bio.seq.Sequence TERMSeq = org.biojava.bio.seq.ProteinTools.createProteinSequence(TERMString, "TERMSeq");
                        FiniteAlphabet alphabet = (FiniteAlphabet) AlphabetManager.alphabetForName("PROTEIN-TERM");
                        //SubstitutionMatrix matrix = new SubstitutionMatrix(alphabet, "#  Matrix made by matblas from blosum62.iij\n#  * column uses minimum score\n#  BLOSUM Clustered Scoring Matrix in 1/2 Bit Units\n#  Blocks Database = /data/blocks_5.0/blocks.dat\n#  Cluster Percentage: >= 62\n#  Entropy =   0.6979, Expected =  -0.5209\n   A  R  N  D  C  Q  E  G  H  I  L  K  M  F  P  S  T  W  Y  V  B  Z  X  *\nA  4 -1 -2 -2  0 -1 -1  0 -2 -1 -1 -1 -1 -2 -1  1  0 -3 -2  0 -2 -1  0 -4 \nR -1  5  0 -2 -3  1  0 -2  0 -3 -2  2 -1 -3 -2 -1 -1 -3 -2 -3 -1  0 -1 -4 \nN -2  0  6  1 -3  0  0  0  1 -3 -3  0 -2 -3 -2  1  0 -4 -2 -3  3  0 -1 -4 \nD -2 -2  1  6 -3  0  2 -1 -1 -3 -4 -1 -3 -3 -1  0 -1 -4 -3 -3  4  1 -1 -4 \nC  0 -3 -3 -3  9 -3 -4 -3 -3 -1 -1 -3 -1 -2 -3 -1 -1 -2 -2 -1 -3 -3 -2 -4 \nQ -1  1  0  0 -3  5  2 -2  0 -3 -2  1  0 -3 -1  0 -1 -2 -1 -2  0  3 -1 -4 \nE -1  0  0  2 -4  2  5 -2  0 -3 -3  1 -2 -3 -1  0 -1 -3 -2 -2  1  4 -1 -4 \nG  0 -2  0 -1 -3 -2 -2  6 -2 -4 -4 -2 -3 -3 -2  0 -2 -2 -3 -3 -1 -2 -1 -4 \nH -2  0  1 -1 -3  0  0 -2  8 -3 -3 -1 -2 -1 -2 -1 -2 -2  2 -3  0  0 -1 -4 \nI -1 -3 -3 -3 -1 -3 -3 -4 -3  4  2 -3  1  0 -3 -2 -1 -3 -1  3 -3 -3 -1 -4 \nL -1 -2 -3 -4 -1 -2 -3 -4 -3  2  4 -2  2  0 -3 -2 -1 -2 -1  1 -4 -3 -1 -4 \nK -1  2  0 -1 -3  1  1 -2 -1 -3 -2  5 -1 -3 -1  0 -1 -3 -2 -2  0  1 -1 -4 \nM -1 -1 -2 -3 -1  0 -2 -3 -2  1  2 -1  5  0 -2 -1 -1 -1 -1  1 -3 -1 -1 -4 \nF -2 -3 -3 -3 -2 -3 -3 -3 -1  0  0 -3  0  6 -4 -2 -2  1  3 -1 -3 -3 -1 -4 \nP -1 -2 -2 -1 -3 -1 -1 -2 -2 -3 -3 -1 -2 -4  7 -1 -1 -4 -3 -2 -2 -1 -2 -4 \nS  1 -1  1  0 -1  0  0  0 -1 -2 -2  0 -1 -2 -1  4  1 -3 -2 -2  0  0  0 -4 \nT  0 -1  0 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -2 -1  1  5 -2 -2  0 -1 -1  0 -4 \nW -3 -3 -4 -4 -2 -2 -3 -2 -2 -3 -2 -3 -1  1 -4 -3 -2 11  2 -3 -4 -3 -2 -4 \nY -2 -2 -2 -3 -2 -1 -2 -3  2 -1 -1 -2 -1  3 -3 -2 -2  2  7 -1 -3 -2 -1 -4 \nV  0 -3 -3 -3 -1 -2 -2 -3 -3  3  1 -2  1 -1 -2 -2  0 -3 -1  4 -3 -2 -1 -4 \nB -2 -1  3  4 -3  0  1 -1  0 -3 -4  0 -3 -3 -2  0 -1 -4 -3 -3  4  1 -1 -4 \nZ -1  0  0  1 -3  3  4 -2  0 -3 -3  1 -1 -3 -1  0 -1 -3 -2 -2  1  4 -1 -4 \nX  0 -1 -1 -1 -2 -1 -1 -1 -1 -1 -1 -1 -1 -1 -2  0  0 -2 -1 -1 -1 -1 -1 -4 \n* -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4  1 ", "BLOSUM62");
                        SubstitutionMatrix matrix = new SubstitutionMatrix(alphabet, "  A  R  N  B  D  C  Q  Z  E  G  H  I  L  K  M  F  P  S  T  W  Y  V  X  *\nA  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nR -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nN -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nB -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nD -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nC -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nQ -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nZ -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nE -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nG -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nH -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nI -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nL -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nK -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nM -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nF -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nP -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nS -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000\nT -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000\nW -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000\nY -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000\nV -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000\nX -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  0 -10000\n* -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  0\n", "IDENTITY");
                        
                        
                        org.biojava.bio.alignment.SequenceAlignment aligner = new org.biojava.bio.alignment.SmithWaterman( 
        0, 	// match
        10000,	// replace
        10000,      // insert
        10000,	// delete
        10000,      // gapExtend
        matrix 	// SubstitutionMatrix
      );
                        aligner.pairwiseAlignment(
        ADSeq, // first sequence
        TERMSeq // second one
      );
                        String alignment = aligner.getAlignmentString();
                        //System.out.println(alignment);
                        StringTokenizer st = new StringTokenizer(alignment);
                        StringTokenizer st2 = new StringTokenizer(alignment);
                        StringTokenizer st3= new StringTokenizer(alignment);
                        while(st3.hasMoreTokens())
                        {
                            try
                            {
                                
                                
                                if(st3.nextToken().equals("Target:")&&(termstart=Integer.parseInt(st3.nextToken()))>=0)
                                {
                                    break;
                                }
                                
                                    
                                        
                            }
                            catch(NumberFormatException nfe)
                                {
                                    
                                }
                        }
                        
                        
                        while(st2.hasMoreTokens())
                        {
                            try
                            {
                                if(st.nextToken().equals("Time"))
                                {
                                    st.nextToken();
                                    st.nextToken();
                                    if(st.nextToken().equals("Length:"))
                                    {
                                        alignlength=Integer.parseInt(st.nextToken());
                                        break;
                                    }
                                }
                            }
                            catch(NumberFormatException nfe)
                            {
                                
                            }
                        }
                        
                        while(st.hasMoreTokens())
                        {
                            try
                            {
                                
                                
                                if(st.nextToken().equals("Query:")&&(alignstart=Integer.parseInt(st.nextToken()))>=0)
                                {
                                    break;
                                }
                                
                                    
                                        
                            }
                            catch(NumberFormatException nfe)
                                {
                                    
                                }
                        }
                        
                        int linePos=0;
                        
                        //System.out.println("Start="+alignstart);
                        //System.out.println("Length="+alignlength);
                        
                        
                        }
                        catch(org.biojava.bio.symbol.IllegalSymbolException ise)
                        {
                            
                        }
                        catch(Exception e)
                        {
                            
                        }
                        if(alignlength<minProtOverlap)
                        {
                            foundTERM=false;
                        }
                        else
                        {
                        foundTERM=true;
                        overlappedSeqs.addDef(ADSeqs.returnDef(i)+"AD-TERM OVERLAPPED");
                        //String fullSeq = ADSeqs.returnSeq(i).substring(0,overlapADCount-1)+TERMSeqs.returnSeq(j).substring(TERMSeqs.returnSeq(j).indexOf(overlap));
                        String fullSeq=ADString.substring(0, alignstart)+TERMString.substring(termstart);
                        //System.out.println("ADSeq LENGTH="+ADString.length());
                        //fullSeq+=TERMString;
                      
                        
                        //System.out.println("ALIGNLENGTH="+alignlength);
                        
                        //System.out.println("ALIGNLENGTH/2="+align2);
                        //fullSeq+=TERMString.substring(align2, TERMString.length()-1);
                        //System.out.println(ADString);
                        //System.out.println(GFormat.padLeft(TERMString.substring(termstart), TERMString.substring(termstart).length()+alignstart));
                        //System.out.println("FULL SEQUENCE:\n"+fullSeq);
                        
                        overlappedSeqs.addStr(fullSeq);
                        overlappedDNASeqs.addDef(ADSeqs.returnDef(i)+"AD-TERM OVERLAPPED");
                        overlappedDNASeqs.addStr(ADSeqsDNA.returnSeq(i).substring(0, alignstart*3)+TERMSeqsDNA.returnSeq(j).substring(termstart*3));
                        }
                    //}
                    //else
                    //{
                    //    foundTERM=false;
                   if(foundTERM==false)
                    {
                        overlappedSeqs.addDef(ADSeqs.returnDef(i)+"AD-NON OVERLAPPED");
                        overlappedSeqs.addStr(ADSeqs.returnSeq(i));
                        overlappedDNASeqs.addDef(ADSeqsDNA.returnDef(i)+"AD-NON OVERLAPPED");
                        overlappedDNASeqs.addStr(ADSeqsDNA.returnSeq(i));
            
            
                    }
                }
                
                
            //}
                    
            
        
            
            }
        }
        for(int i=0;i<TERMSeqs.getNumDefs();i++)
        {
            
            boolean foundAD=false;
            for(int j=0;j<overlappedSeqs.getNumDefs();j++)
            {
                
                try
                {        
                if((overlappedSeqs.returnDef(j).substring(0,overlappedSeqs.returnDef(j).indexOf("AD-TERM")).equals(TERMSeqs.returnDef(i))))
                {
                    foundAD=true;
                }
                }
                catch(StringIndexOutOfBoundsException sioobe)
                {
                //System.out.println("i="+i+"j="+j);
                }
                
            }
            if(foundAD==false)
            {
                overlappedSeqs.addDef(TERMSeqs.returnDef(i)+"TERM-NON OVERLAPPED");
                overlappedSeqs.addStr(TERMSeqs.returnSeq(i));
                overlappedDNASeqs.addDef(TERMSeqsDNA.returnDef(i)+"TERM-NON OVERLAPPED");
                overlappedDNASeqs.addStr(TERMSeqsDNA.returnSeq(i));
            }
            
        }
        
         transSeqs=overlappedSeqs;
         seqs=overlappedDNASeqs;   
        
        //for(int i=0;i<overlappedSeqs.getNumSeqs();i++)
        //{
            //System.out.println(overlappedSeqs.returnDef(i)+"\n"+overlappedSeqs.returnSeq(i));
        //}
         jProgressBar1.setValue(100);
        jProgressBar1.setVisible(false);
    }    
        
    
    
    public void overlapDNA()
    {
        jLabel4.setText("Overlapping Sequences");
        this.update(this.getGraphics());        
        ADSeqs = new SeqList(new String[0], new String[0]);
        TERMSeqs = new SeqList(new String[0], new String[0]);
        overlappedSeqs = new SeqList(new String[0], new String[0]);
        for(int i=0;i<numSeqs;i++)
        {
            String tmpDef=seqs.returnDef(i);
            String tmpSeq=seqs.returnSeq(i);
            if(tmpDef.indexOf(ADString)!=-1)
            {
                ADSeqs.addDef(tmpDef.substring(0, tmpDef.indexOf(ADString)));
                ADSeqs.addStr(tmpSeq);
                
            }
            if(tmpDef.indexOf(TERMString)!=-1)
            {
                TERMSeqs.addDef(tmpDef.substring(0, tmpDef.indexOf(TERMString)));
                TERMSeqs.addStr(tmpSeq);
                
            }
            if(tmpDef.indexOf(ADString)==-1&&tmpDef.indexOf(TERMString)==-1)
            {
                overlappedSeqs.addDef(tmpDef);
                overlappedSeqs.addStr(tmpSeq);
            }
                        
        }    
            
        jProgressBar1.setVisible(true);
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum(100);
        jProgressBar1.setStringPainted(true);
        
        for(int i=0;i<ADSeqs.getNumDefs();i++)
        {
            jProgressBar1.setValue(100*i/ADSeqs.getNumDefs());
            this.update(this.getGraphics());
            int termstart=0;
            int alignstart=0;
            int alignlength=0;
            boolean foundTERM=false;
            for(int j=0;j<TERMSeqs.getNumDefs();j++)
            {
                
                if(ADSeqs.returnDef(i).equals(TERMSeqs.returnDef(j)))
                {
                    String overlap="";
                    //String overlap=ADSeqs.returnSeq(i);
                    //int limit = ADSeqs.returnSeq(i).length();
                    //int overlapADCount=0;
                            
                    //while((TERMSeqs.returnSeq(j).indexOf(overlap)<0)&&overlapADCount<limit)
                    //{
                        //overlap=ADSeqs.returnSeq(i).substring(overlapADCount);
                        //overlapADCount++;
                        
                    //}
                    //if(TERMSeqs.returnSeq(j).indexOf(overlap)!=-1)
                    //{
                        String ADString = ADSeqs.returnSeq(i);
                        String TERMString = TERMSeqs.returnSeq(j);
                        int ADGaps=0;
                        //int totalLength=ADString.length()+TERMString.substring(TERMString.indexOf(overlap)).length()-overlap.length();
                        //System.out.println("\n\n\n\n\n\n\n"+ADSeqs.returnDef(i)+"AD-TERM OVERLAPPED\n"+ADSeqs.returnSeq(i)+"\n"+GFormat.padLeft(TERMSeqs.returnSeq(j), totalLength));
                        try
                        {
                            
                        
                        org.biojava.bio.seq.Sequence ADSeq = org.biojava.bio.seq.DNATools.createDNASequence(ADString, "ADSeq");
                        org.biojava.bio.seq.Sequence TERMSeq = org.biojava.bio.seq.DNATools.createDNASequence(TERMString, "TERMSeq");
                        FiniteAlphabet alphabet = (FiniteAlphabet) AlphabetManager.alphabetForName("DNA");
                        SubstitutionMatrix matrix = new SubstitutionMatrix(alphabet, "    A   T   G   C   S   W   R   Y   K   M   B   V   H   D   N\nA  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nT -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nG -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nC -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nS -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nW -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nR -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nK -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000 -10000\nM -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000 -10000\nM -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000 -10000\nB -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000 -10000\nV -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000 -10000\nH -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000 -10000\nD -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1 -10000\nN -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000 -10000  1\n", "IDENTITY");
                        org.biojava.bio.alignment.SequenceAlignment aligner = new org.biojava.bio.alignment.SmithWaterman( 
        0, 	// match
        10000,	// replace
        10000,      // insert
        10000,	// delete
        10000,      // gapExtend
        matrix 	// SubstitutionMatrix
      );
                        aligner.pairwiseAlignment(
        ADSeq, // first sequence
        TERMSeq // second one
      );
                        String alignment = aligner.getAlignmentString();
                        //System.out.println(alignment);
                        StringTokenizer st = new StringTokenizer(alignment);
                        StringTokenizer st2 = new StringTokenizer(alignment);
                        StringTokenizer st3= new StringTokenizer(alignment);
                        while(st3.hasMoreTokens())
                        {
                            try
                            {
                                
                                
                                if(st3.nextToken().equals("Target:")&&(termstart=Integer.parseInt(st3.nextToken()))>=0)
                                {
                                    break;
                                }
                                
                                    
                                        
                            }
                            catch(NumberFormatException nfe)
                                {
                                    
                                }
                        }
                        while(st2.hasMoreTokens())
                        {
                            try
                            {
                                if(st.nextToken().equals("Time"))
                                {
                                    st.nextToken();
                                    st.nextToken();
                                    if(st.nextToken().equals("Length:"))
                                    {
                                        alignlength=Integer.parseInt(st.nextToken());
                                        break;
                                    }
                                }
                            }
                            catch(NumberFormatException nfe)
                            {
                                
                            }
                        }
                        
                        while(st.hasMoreTokens())
                        {
                            try
                            {
                                
                                
                                if(st.nextToken().equals("Query:")&&(alignstart=Integer.parseInt(st.nextToken()))>=0)
                                {
                                    break;
                                }
                                
                                    
                                        
                            }
                            catch(NumberFormatException nfe)
                                {
                                    
                                }
                        }
                        
                        int linePos=0;
                        /*while(alignment.indexOf("\n", linePos)!=-1)
                        {
                            
                            String tmp2 = alignment.substring(linePos, linePos=alignment.indexOf("\n", linePos+1));
                            if(tmp2.indexOf("Query:")!=-1)
                            {
                                StringTokenizer st3 = new StringTokenizer(tmp2);
                                int ADStart = Integer.parseInt(st.nextToken());
                                int ADMaxPos=Integer.parseInt(st.nextToken());
                                String tmp3 = st.nextToken();
                                if(ADMaxPos>(alignlength+alignstart)/2)
                                {
                                    tmp3=tmp3.substring(0, tmp3.length()-(ADMaxPos-(alignlength+alignstart)/2));
                                    for(int l=0;l<tmp3.length();l++)
                                {
                                    if(tmp3.charAt(l)=='-')
                                    {
                                        ADGaps++;
                                    }
                                    break;
                                }
                                }
                                for(int l=0;l<tmp2.length();l++)
                                {
                                    if(tmp2.charAt(l)=='-')
                                    {
                                        ADGaps++;
                                    }
                                }
                            }
                        }
                         */
                        //System.out.println("Start="+alignstart);
                        //System.out.println("Length="+alignlength);
                        //System.out.println(ADGaps);
                        
                        
                        }
                        catch(org.biojava.bio.symbol.IllegalSymbolException ise)
                        {
                            
                        }
                        catch(Exception e)
                        {
                            
                        }
                        //int overlapPad = overlapADCount+overlap.length()-1;
                        //System.out.println("\n"+GFormat.padLeft(overlap,overlapPad));
                        //System.out.println(overlapPad);
                        
                        
                        if(alignlength<minDNAOverlap)
                        {
                            foundTERM=false;
                        }
                        else
                        {
                        foundTERM=true;
                        overlappedSeqs.addDef(ADSeqs.returnDef(i)+"AD-TERM OVERLAPPED");
                        //String fullSeq = ADSeqs.returnSeq(i).substring(0,overlapADCount-1)+TERMSeqs.returnSeq(j).substring(TERMSeqs.returnSeq(j).indexOf(overlap));
                        String fullSeq=ADString.substring(0, alignstart)+TERMString.substring(termstart);//-1);
                        //System.out.println("ADSeq LENGTH="+ADString.length());
                        //System.out.println("MIDDLE="+(((ADString.length()+alignstart)/2)-1));
                        //fullSeq+=TERMString;
                        //fullSeq+=ADString.substring(alignstart, ((ADString.length()+alignstart)/2)-1);
                        //if(alignlength>TERMString.length())
                        //{
                        //    fullSeq+=ADString.substring(0, alignstart)+TERMString;
                        //}
                        //else
                        //{
                        //    int align2=alignlength/2;
                        
                        //fullSeq+=ADString.substring(alignstart, alignstart+align2);
                        //fullSeq+=TERMString.substring(1+align2-ADGaps);
                        //}
                        //System.out.println("ALIGNLENGTH="+alignlength);
                        
                        //System.out.println("ALIGNLENGTH/2="+align2);
                        //fullSeq+=TERMString.substring(align2, TERMString.length()-1);
                        //System.out.println(ADString);
                        //System.out.println(GFormat.padLeft(TERMString.substring(termstart), TERMString.substring(termstart).length()+alignstart));
                        //System.out.println("FULL SEQUENCE:\n"+fullSeq);
                        overlappedSeqs.addStr(fullSeq);
                    //}
                    //else
                    //{
                    //    foundTERM=false;
                        }
                    }
                }
                
            //}
            if(foundTERM==false)
            {
                overlappedSeqs.addDef(ADSeqs.returnDef(i)+"AD-NON OVERLAPPED");
                overlappedSeqs.addStr(ADSeqs.returnSeq(i));
            }
            
        }
        
        for(int i=0;i<TERMSeqs.getNumDefs();i++)
        {
            
            boolean foundAD=false;
            for(int j=0;j<overlappedSeqs.getNumDefs();j++)
            {
                
                try
                {        
                if((overlappedSeqs.returnDef(j).substring(0,overlappedSeqs.returnDef(j).indexOf("AD-TERM")).equals(TERMSeqs.returnDef(i))))
                {
                    foundAD=true;
                }
                }
                catch(StringIndexOutOfBoundsException sioobe)
                {
                //System.out.println("i="+i+"j="+j);
                }
                
            }
            if(foundAD==false)
            {
                overlappedSeqs.addDef(TERMSeqs.returnDef(i)+"TERM-NON OVERLAPPED");
                overlappedSeqs.addStr(TERMSeqs.returnSeq(i));
            }
            
        }
        
        //for(int i=0;i<overlappedSeqs.getNumSeqs();i++)
        //{
            //System.out.println(overlappedSeqs.returnDef(i)+"\n"+overlappedSeqs.returnSeq(i));
        //}
        jProgressBar1.setValue(100);
        jProgressBar1.setVisible(false);
    }    
    public void centreFrame(JFrame frame)
    {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = frame.getSize();
        screenSize.height = screenSize.height/2;
        screenSize.width = screenSize.width/2;
        size.height = size.height/2;
        size.width = size.width/2;
        int y = screenSize.height - size.height;
        int x = screenSize.width - size.width;
        frame.setLocation(x, y);

    }
    
    public void getTermVector()
    {
        try
        {
            if(termvectorFrame.isProtein())
            {
                this.getProteinTermVector();
                termProteinVector = true;
            }
            
            
            
            else
            {
                termvector="";
                String [] result = termvectorFrame.returnResult();
                if(!result.equals(null))
                {
                    if(!result[0].equals(null)&&!result[0].equals(""))
                    {
                        for(int i=0;i<result[0].length();i++)
                            {
                            if((result[0].toUpperCase().charAt(i)=='A')||(result[0].toUpperCase().charAt(i)=='T')||(result[0].toUpperCase().charAt(i)=='G')||(result[0].toUpperCase().charAt(i)=='C'))
                                {
                                    termvector+=result[0].toUpperCase().charAt(i);
                                }
                            }
                    }
                    if(!result[1].equals(null)&&!result[1].equals(""))
                    {
                        try
                        {
                            Sequence termvectorSeq = new Sequence(termvector,true,1);
                            termvectorSeq.processSequence();
                            termvectorSeq.getComplementaryStrand();
                            termvector = termvectorSeq.getSequence();
                            termvectorStart=Integer.parseInt(result[1]);
                            termvectorLoaded=true;
                            //System.out.println("\n\n\nTERM VECTOR = "+termvector);
                        }
                        catch(NumberFormatException nfe)
                        {
                            vector="AAACCACTTT";
                            vectorStart=28;
                            proteinVector = false;
                        }
                    }
                }
            proteinVector = false;
            }
        }
        catch(NullPointerException npe)
        {
            vector="AAACCACTTT";
            vectorStart=28;
            proteinVector = false;
        }
            
            
        //System.out.println("Term Vector: "+termvector+"\nTerm Vector Gap: "+termvectorStart);
        
    }
    
    
    public void getProteinTermVector()
    {
        termvector="";
                String [] result = termvectorFrame.returnResult();
                if(!result.equals(null))
                {
                    if(!result[0].equals(null)&&!result[0].equals(""))
                    {
                        for(int i=0;i<result[0].length();i++)
                            {
                            if((result[0].toUpperCase().charAt(i)=='A')||(result[0].toUpperCase().charAt(i)=='R')||(result[0].toUpperCase().charAt(i)=='N')||(result[0].toUpperCase().charAt(i)=='D')||(result[0].toUpperCase().charAt(i)=='C')||(result[0].toUpperCase().charAt(i)=='E')||(result[0].toUpperCase().charAt(i)=='Q')||(result[0].toUpperCase().charAt(i)=='G')||(result[0].toUpperCase().charAt(i)=='H')||(result[0].toUpperCase().charAt(i)=='I')||(result[0].toUpperCase().charAt(i)=='L')||(result[0].toUpperCase().charAt(i)=='K')||(result[0].toUpperCase().charAt(i)=='M')||(result[0].toUpperCase().charAt(i)=='F')||(result[0].toUpperCase().charAt(i)=='P')||(result[0].toUpperCase().charAt(i)=='S')||(result[0].toUpperCase().charAt(i)=='T')||(result[0].toUpperCase().charAt(i)=='W')||(result[0].toUpperCase().charAt(i)=='Y')||(result[0].toUpperCase().charAt(i)=='V'))
                                {
                                    termvector+=result[0].toUpperCase().charAt(i);
                                }
                            }
                    }
                    if(!result[1].equals(null)&&!result[1].equals(""))
                    {
                        try
                        {
                            termvectorStart=Integer.parseInt(result[1]);
                            termvectorLoaded=true;
                            termProtVectorStart=termvectorStart;
                            termProtVector=termvector;
                        }
                        catch(NumberFormatException nfe)
                        {
                            termvector="DPAFL";
                            termvectorStart=0;
                        }
                    }
                }
    }
    
    public void getVector()
    {
        try
        {
            
            if(vectorFrame.isProtein())
            {
                this.getProteinVector();
                proteinVector = true;
            }
            
            
            
            else
            {
                vector="";
                String [] result = vectorFrame.returnResult();
                if(!result.equals(null))
                {
                    if(!result[0].equals(null)&&!result[0].equals(""))
                    {
                        for(int i=0;i<result[0].length();i++)
                            {
                            if((result[0].toUpperCase().charAt(i)=='A')||(result[0].toUpperCase().charAt(i)=='T')||(result[0].toUpperCase().charAt(i)=='G')||(result[0].toUpperCase().charAt(i)=='C'))
                                {
                                    vector+=result[0].toUpperCase().charAt(i);
                                }
                            }
                    }
                    if(!result[1].equals(null)&&!result[1].equals(""))
                    {
                        try
                        {
                            vectorStart=Integer.parseInt(result[1]);
                            vectorLoaded=true;
                            
                        }
                        catch(NumberFormatException nfe)
                        {
                            vector="AAAAAGCA";
                            vectorStart=14;
                            proteinVector = false;
                        }
                    }
                }
            proteinVector = false;
            }
        }
        catch(NullPointerException npe)
        {
            vector="AAAAAGCA";
            vectorStart=14;
            proteinVector = false;
        }
        //System.out.println("Vector: "+vector+"\nVector Gap: "+vectorStart);
    }
    
    public void getProteinVector()
    {
        vector="";
                String [] result = vectorFrame.returnResult();
                if(!result.equals(null))
                {
                    if(!result[0].equals(null)&&!result[0].equals(""))
                    {
                        for(int i=0;i<result[0].length();i++)
                            {
                            if((result[0].toUpperCase().charAt(i)=='A')||(result[0].toUpperCase().charAt(i)=='R')||(result[0].toUpperCase().charAt(i)=='N')||(result[0].toUpperCase().charAt(i)=='D')||(result[0].toUpperCase().charAt(i)=='C')||(result[0].toUpperCase().charAt(i)=='E')||(result[0].toUpperCase().charAt(i)=='Q')||(result[0].toUpperCase().charAt(i)=='G')||(result[0].toUpperCase().charAt(i)=='H')||(result[0].toUpperCase().charAt(i)=='I')||(result[0].toUpperCase().charAt(i)=='L')||(result[0].toUpperCase().charAt(i)=='K')||(result[0].toUpperCase().charAt(i)=='M')||(result[0].toUpperCase().charAt(i)=='F')||(result[0].toUpperCase().charAt(i)=='P')||(result[0].toUpperCase().charAt(i)=='S')||(result[0].toUpperCase().charAt(i)=='T')||(result[0].toUpperCase().charAt(i)=='W')||(result[0].toUpperCase().charAt(i)=='Y')||(result[0].toUpperCase().charAt(i)=='V'))
                                {
                                    vector+=result[0].toUpperCase().charAt(i);
                                }
                            }
                    }
                    if(!result[1].equals(null)&&!result[1].equals(""))
                    {
                        try
                        {
                            vectorStart=Integer.parseInt(result[1]);
                            vectorLoaded=true;
                            protVectorStart=vectorStart;
                            protVector=vector;
                        }
                        catch(NumberFormatException nfe)
                        {
                            vector="STHAS";
                            vectorStart=15;
                        }
                    }
                }
    }
    
    public void getMutationsMatrix()
    {
        mutationsMatrix = new char[model.getSequence().length()][transSeqs.getNumSeqs()];
        for(int i=0 ; i<mutationsMatrix.length;i++)
        {
            java.util.Arrays.fill(mutationsMatrix[i],'0');
        }
        
        for(int i=0;i<transSeqs.getNumSeqs();i++)
        {
            for(int j=0;j<model.getSequence().length();j++)
            {
                try
                {
                    if(transSeqs.returnSeq(i).charAt(j)!=model.getSequence().charAt(j)&&transSeqs.returnSeq(i).charAt(j)!='X'&&transSeqs.returnSeq(i).charAt(j)!=' ')
                
                    {
                        mutationsMatrix[j][i]=transSeqs.returnSeq(i).charAt(j);
                    }
                }
                catch(StringIndexOutOfBoundsException sioobe)
                {
                        
                 }
                    
            }
        }
        
        
    }
    
    public void getMutationsList()
    {
        mutationsList=new int[model.getSequence().length()];
        for(int i=0;i<transSeqs.getNumSeqs();i++)
        {
            for(int j=0;j<model.getSequence().length();j++)
            {
                try
                {
                    if(transSeqs.returnSeq(i).charAt(j)!=model.getSequence().charAt(j)&&transSeqs.returnSeq(i).charAt(j)!='X'&&transSeqs.returnSeq(i).charAt(j)!=' ')
                
                    {
                        mutationsList[j]+=1;
                    }
                }
                    catch(StringIndexOutOfBoundsException sioobe)
                    {
                        
                    }
            }
        }
        //System.out.println(mutationsList);
    }
    
    public void getMutationsReport()
    {
        report="";
        charListAndCount = new String[0][0];
        char[] tmp=new char[transSeqs.getNumSeqs()];
        int [] tmpmutList= new int[0];
        String [] tmpstringmutList = new String[0];
        int numlines=0;
        for(int k=0;k<model.getSequence().length();k+=50)
        {
            int tmpnummuts=0;
            for(int j=k;j<k+50&&j<model.getSequence().length();j++)
            {
                if(mutationsList[j]>=1)
                {
                    tmpnummuts++;
                }
            }
            if(tmpnummuts>numlines)
            {
                numlines=tmpnummuts;
            }
        }
        //System.out.println("Max num lines :"+numlines);

        



        

        //get mutations list for whole seq
        
        for(int j=0;j<model.getSequence().length();j++)
            {
                 String charString = "";
                 if(mutationsList[j]>0)
                 {
                    
                    tmp=mutationsMatrix[j];
                    //get character string               
                    for(int h=0;h<tmp.length;h++)
                    {
                    
                        if(tmp[h]!='0')
                        {
                            charString+=tmp[h];
                        }
                    }
                    
                 }
                    
                 tmpmutList = this.addInt(tmpmutList, mutationsList[j]);
                 tmpstringmutList = this.addString(tmpstringmutList, this.getFormattedCharString(charString));
                
            }
        
        
        
        
        
        for(int k=0;k<model.getSequence().length();k+=50)
        {
            String line="     ";
            int nummuts=0;
            for(int h=k;h<k+50&&h<model.getSequence().length();h++)
            {
                if(tmpmutList[h]>=1)
                {
                    nummuts++;
                }
            }
            
            
                
            for(int o =0;o<=nummuts;o++)
            {
                
            
                    
                for(int i=k;i<k+50&&i<model.getSequence().length();i++)
                {
                
                        
                    if(tmpmutList[i]>=1)
                            
                    {
                                
                        line+=tmpstringmutList[i]+"\n"+"     ";
                        tmpmutList[i]=-1;
                        break;
                            
                    }
                        
                    if(tmpmutList[i]==-1)
                    {
                        line+="|";
                    }
                    
                    if(!(tmpmutList[i]>=1)&&!(tmpmutList[i]==-1))
                    {
                        line+=" ";
                    }
                    
                }
                
            }
            for(int p=nummuts;p<numlines;p++)
            {
                report+="\n";
            }
            if(k+50<model.getSequence().length())
            {
                
            
            report+=line+"\n"+GFormat.padLeft(Integer.toString(k+1),"    ".length())+" "+model.getSequence().substring(k, k+50)+" "+Integer.toString(k+50)+"\n\n";
            
            }
            else
            {
                report+=line+"\n"+GFormat.padLeft(Integer.toString(k+1),"    ".length())+" "+model.getSequence().substring(k, model.getSequence().length())+" "+Integer.toString(model.getSequence().length())+"\n\n";
            
                
            }
                
                
        }
        
        
        
        
       
                
           
            //System.out.println(report);
        
        
    }
            
        
        
        
    
    
    
    
    public String getFormattedCharString(String s)
    {
        String mutstring="";
        String[] AAs = {"A","E","T","Y","I","P","Q","R","S","D","F","G","H","K","L","M","W","C","V","N","-"};
        if(s.length()==0)
        {
            mutstring=s;
        }
        else
        {
            int count=0;
            for(int i=0;i<AAs.length;i++)
            {
                if(s.indexOf(AAs[i])!=-1)
                {
                    count=s.replaceAll("[^"+AAs[i]+"]","").length();
                    if(count==1)
                    {
                        mutstring+=AAs[i]+",";
                    }
                    else
                    {
                        mutstring+=count+AAs[i]+",";
                    }
                    count=0;
                }
            }
            mutstring=mutstring.substring(0, mutstring.length()-1);
        }
        
        
        
        return mutstring;
    }
    
    
    
    public String getFormattedCharString2(String s)
    {
        String tmp="";
        int count=0;
        for(int i=0;i<s.length();i++)
        {
            try
            {
                while(s.indexOf(s.charAt(i),i+1)!=-1)
                {
                    count++;
                }
                
                if(count==1)
                {
                    tmp+=s.charAt(i)+" ";
                }
                if(count>1)
                {
                    tmp+=count+s.charAt(i)+" ";
                }
                s.replaceAll(s.substring(i,i+1),"");
                i+=count;
                count=0;
            }
            catch(StringIndexOutOfBoundsException sioobe)
            {
                
            }
        }
        if(s.length()==1)
        {
            tmp=s;
        }
     return tmp;   
    }
    
    public String [] addString(String[] c, String i)
    {
        String[] tmpList= new String[c.length+1];
        
        for(int j=0;j<c.length;j++)
        {
            tmpList[j]=c[j];
        }
        
        tmpList[c.length]=i;
        c=tmpList;
        return c;
        
    }
    
     public int [] addInt(int[] c, int i)
    {
        int[] tmpList= new int[c.length+1];
        
        for(int j=0;j<c.length;j++)
        {
            tmpList[j]=c[j];
        }
        
        tmpList[c.length]=i;
        c=tmpList;
        return c;
        
    }
    
    
    public String[][] addCharListAndCount(String[][] s,String c,String n)
    {
        String[][] tmp = new String[s.length+1][1];
        for(int i=0;i<s.length;i++)
        {
            tmp[i]=s[i];
        }
        String[] tmplist = {c,n};
        tmp[s.length]=tmplist;
        s=tmp;
        return s;
    }
            
    protected void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "Courier New");

        Style s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);

        s = doc.addStyle("back", regular);
        StyleConstants.setForeground(s, Color.BLACK);
        
        s = doc.addStyle("blue", regular);
        StyleConstants.setForeground(s, Color.BLUE);
        
        s = doc.addStyle("green", regular);
        StyleConstants.setForeground(s, Color.GREEN);
        
        s = doc.addStyle("red", regular);
        StyleConstants.setForeground(s, Color.RED);
        
        
        
        
    }
   
    
    
    
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Go;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    // End of variables declaration//GEN-END:variables
    
}
