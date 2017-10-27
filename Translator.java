/*
 * Translator.java
 *
 * Created on 05 February 2005, 20:16
 */

/**
 *
 * @author  Pierre Cauchy
 */
public class Translator 
{
    
    /*3D arrays, representing genetic code, universal, and mitochondrial in Vertebrates
    * Use bases as coordinates. Much quicker to use than algorithms that search for codons? 
    * Perhaps more convenient to use for multiple codes.
    * is stop, > is start
    **/ 
    
    //canonic code
    private final String[][][] UCODE = {{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"L","S","*","*","X"},{"L","P","Q","R","X"},{"I","T","K","R","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","R","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},   {{"X","S","X","X","X"},{"L","P","X","R","X"},{"X","T","X","X","X"},{"V","A","X","G","X"},{"X","X","X","X","X"}}};
    
    //private final String[][][] UCODE = {{{"F","S","Y","C"},{"L","P","H","R"},{"I","T","N","S"},{"V","A","D","G"}},{{"F","S","Y","C"},{"L","P","H","R"},{"I","T","N","S"},{"V","A","D","G"}},{{"L","S","*","*"},{"L","P","Q","R"},{"I","T","K","R"},{"V","A","E","G"}},{{"L","S","*","W"},{"L","P","Q","R"},{"M","T","K","R"},{"V","A","E","G"}},   {{"X","S","X","X"},{"L","P","X","R"},{"X","T","X","X"},{"V","A","X","G"}}};
    //vertebrate mitochondrial code
    

    //vertebrate mitochondrial code
    
    
    private final String[][][] VMCODE = {{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","*","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","*","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},   {{"X","S","X","X","X"},{"L","P","X","R","X"},{"X","T","X","X","X"},{"V","A","X","G","X"},{"X","X","X","X","X"}}};
    //arthropod mitochondrial code
    private final String[][][] AMCODE = {{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","S","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","R","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},   {{"X","S","X","X","X"},{"L","P","X","R","X"},{"X","T","X","X","X"},{"V","A","X","G","X"},{"X","X","X","X","X"}}};
    //echinoderm mitochondrial code
    private final String[][][] EMCODE = {{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"I","T","N","S","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","S","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},   {{"X","S","X","X","X"},{"L","P","X","R","X"},{"X","T","X","X","X"},{"V","A","X","G","X"},{"X","X","X","X","X"}}};
    //molluscan mitochondrial code
    private final String[][][] MMCODE = {{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","N","S","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","S","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},   {{"X","S","X","X","X"},{"L","P","X","R","X"},{"X","T","X","X","X"},{"V","A","X","G","X"},{"X","X","X","X","X"}}};
    //ascidian mitochondrial code
    private final String[][][] SMCODE = {{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","G","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","G","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},   {{"X","S","X","X","X"},{"L","P","X","R","X"},{"X","T","X","X","X"},{"V","A","X","G","X"},{"X","X","X","X","X"}}};
    //euplote code
    private final String[][][] ECODE = {{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"L","S","*","C","X"},{"L","P","Q","R","X"},{"I","T","K","R","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"L","P","Q","R","X"},{"M","T","K","R","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},   {{"X","S","X","X","X"},{"L","P","X","R","X"},{"X","T","X","X","X"},{"V","A","X","G","X"},{"X","X","X","X","X"}}};
    //Paramecium, Tetrahymena, Oxytrichia, Stylonychia, Glaucoma, Acetabularia 
    private final String[][][] PCODE = {{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"L","S","Q","*","X"},{"L","P","Q","R","X"},{"I","T","K","R","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},{{"L","S","Q","W","X"},{"L","P","Q","R","X"},{"M","T","K","R","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},   {{"X","S","X","X","X"},{"L","P","X","R","X"},{"X","T","X","X","X"},{"V","A","X","G","X"},{"X","X","X","X","X"}}};
    //Candida cylindrica 
    private final String[][][] CCODE = {{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"F","S","Y","C","X"},{"L","P","H","R","X"},{"I","T","N","S","X"},{"V","A","D","G","X"},{"X","X","X","X","X"}},{{"L","S","*","*","X"},{"L","P","Q","R","X"},{"I","T","K","R","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},{{"L","S","*","W","X"},{"S","P","Q","R","X"},{"M","T","K","R","X"},{"V","A","E","G","X"},{"X","X","X","X","X"}},   {{"X","S","X","X","X"},{"L","P","X","R","X"},{"X","T","X","X","X"},{"V","A","X","G","X"},{"X","X","X","X","X"}}};
    
    
    //array instance variables
    private String[] codons;
    private String[] protSeq;
    int[] strtCodonsPos;
    int[] stopCodonsPos;
    int maxStart=0;
    int maxStop=0;
    
    
    //this one represents coordinates in 3D array, with T being 0, C=1, A=2, G=3
    private String[] coord;
    
    //String representation of translation product
    private String processedSeq="";
    int numLines;
    
    /** Creates a new instance of Translator */
    //constructor, DNA seq passed as string array 
    public Translator(String [] s) 
    {
        codons = s;
    }
    
    
    /**Translation obtained by converting bases to coordinates. corresponding 3D array value 
     * returned
     **/
    public void translate(int code)
    {
        
        coord = new String [codons.length];
        java.util.Arrays.fill(coord,"");
        int[] xyz = new int[3];
        int a=0;
        protSeq = new String[codons.length];
        java.util.Arrays.fill(protSeq,"");
        for(int i=0;i<codons.length;i++)
        {
            for(int j =0;j<3;j++)
            {
                if(codons[i].charAt(j)=='T')
                {
                    coord[i]+="0";
                    a=0;
                }
                
                if(codons[i].charAt(j)=='C')
                {
                    coord[i]+="1";
                    a=1;
                }
                
                if(codons[i].charAt(j)=='A')
                {
                    coord[i]+="2";
                    a=2;
                }
                
                if(codons[i].charAt(j)=='G')
                {
                    coord[i]+="3";
                    a=3;
                }
                if(codons[i].charAt(j)=='N')
                {
                    coord[i]+="4";
                    a=4;
                }
                
                xyz[j]=a;
                
                
            }
            
            
            //according to code return amino acid
            
            String[][][] gCode={{{}}};
            
            switch(code)
            {
                case 0: gCode=UCODE;break;
                case 1: gCode=VMCODE;break;
                case 2: gCode=AMCODE;break;
                case 3: gCode=EMCODE;break;
                case 4: gCode=MMCODE;break;
                case 5: gCode=SMCODE;break;
                case 6: gCode=ECODE;break;
                case 7: gCode=PCODE;break;
                case 8: gCode=CCODE;break;
            }
            
            protSeq[i]= gCode[xyz[2]][xyz[0]][xyz[1]];
            
            
            
            
            
            //System.out.print(protSeq[i]);
            
        }
        //System.out.println();
    }
    
    
    /**Processes aa sequence according to ribosome logic.
     * Logic: reads array, if no start codon yet next M is start, if
     * start found, next M is M (can't be 2 start codons in a row)
     * if stop found next M is start.
     **/
    public void processStartCodons()
    {
        
        boolean translationStarted=false;
        for(int i=0;i<protSeq.length;i++)
        {
            if(protSeq[i].equals("*"))
            {
                translationStarted=false;
            }
            
            if(protSeq[i].equals("M")&&translationStarted)
            {
                protSeq[i]="M";
            }
            
            
            if(protSeq[i].equals("M")&&!translationStarted)
            {
                protSeq[i]=">";
                translationStarted=true;
            }
            //System.out.print(protSeq[i]);
            
        }
        //System.out.println();
    }
    
    
    //counts start codons from obtained seq (above), position stored in array
    public int[] countStartCodons()
    {
        strtCodonsPos = new int [0];
        for(int i=0;i<protSeq.length;i++)
        {
            if(protSeq[i].equals(">"))
            {
               strtCodonsPos=add(strtCodonsPos,i+1);
               
            }
        }
        //for(int i=0;i<strtCodonsPos.length;i++)
        //{
            //System.out.print(strtCodonsPos[i]+" ");
        //}
        
        //System.out.println();
        return strtCodonsPos;
        
    }
    
    //same as above for stop codons
    public int[] countStopCodons()
    {
        stopCodonsPos = new int [0];
        for(int i=0;i<protSeq.length;i++)
        {
            if(protSeq[i].equals("*"))
            {
               stopCodonsPos=add(stopCodonsPos,i+1);
               
            }
        }
         //for(int i=0;i<stopCodonsPos.length;i++)
        //{
            //System.out.print(stopCodonsPos[i]+" ");
        //}
        
        //System.out.println();
        //System.out.println();
        return stopCodonsPos;
    }
    
    
    //returns total number of start codons
    public int getNumStartCodons()
    {
        return strtCodonsPos.length;
    }
    
    //returns total num of stop codons
    public int getNumStopCodons()
    {
        int s=stopCodonsPos.length;;
        return stopCodonsPos.length;
    }
    
    
    //returns maximum translated size. Achieved by calculating difference
    //between each start codons array value and the closest next one in stop
    //codons array
    public int getMaxTranslated()
    {
        int max=Integer.MIN_VALUE;
        maxStart=0;
        maxStop=0;
        int[] stpList=stopCodonsPos;
        //boolean breakLoop=false;
        stpList=add(stpList,protSeq.length);
        for(int i=0;i<strtCodonsPos.length;i++)
        {
            //System.out.println("START:"+strtCodonsPos[i]*3);
            for(int j=0;j<stpList.length;j++)
            {
                //breakLoop=false;
                if(stpList[j]*3>strtCodonsPos[i]*3)
                {
                    //System.out.println("STOP:"+stpList[j]*3);
                    if((stpList[j]*3-strtCodonsPos[i]*3)>max&&(stpList[j]>strtCodonsPos[i]))
                    {
                        max=stpList[j]*3-strtCodonsPos[i]*3;
                        maxStart=(strtCodonsPos[i]-1)*3;
                        maxStop=stpList[j]*3;
                        //System.out.println("Max Mength: "+max+" Max Start: "+maxStart+ " Max End: "+maxStop);
                        
                    }
                    break;
                }
                //if(breakLoop)
                    //break;
            }
        }
        if(strtCodonsPos.length==0)
        {
            max=0;
        }
        return max;
        
        
    }
    public int getMaxStart()
    {
        return maxStart;
    }
    
    public int getMaxStop()
    {
        return maxStop;
    }
    
    //same as above, for min, identical logic
    public int getMinTranslated()
    {
        int min=Integer.MAX_VALUE;
        int[] stpList=stopCodonsPos;
        stpList=add(stpList,protSeq.length);
        for(int i=0;i<strtCodonsPos.length;i++)
        {
            for(int j=0;j<stpList.length;j++)
            {
                if(stpList[j]*3>strtCodonsPos[i]*3)
                {
                    if(stpList[j]*3-strtCodonsPos[i]*3<min)
                    {
                        min=stpList[j]*3-strtCodonsPos[i]*3;
                        
                    }
                    break;
                }
            }
        }
        if(strtCodonsPos.length==0)
        {
            min=0;
        }
        return min;
        
        
    }
    
    //same as above, for average, takes total translated seq sizes and calculates mean
    public double getAvergageTranslatedSize()
    {
        int[] stpList=stopCodonsPos;
        stpList=add(stpList,protSeq.length);
        int[] translatedSize = new int[0];
        for(int i=0;i<strtCodonsPos.length;i++)
        {
            //System.out.println("START:"+strtCodonsPos[i]*3);
            for(int j=0;j<stpList.length;j++)
            {
                if(stpList[j]*3>strtCodonsPos[i]*3)
                {
                    //System.out.println("STOP:"+stpList[j]*3);
                    translatedSize=add(translatedSize,(stpList[j]*3-strtCodonsPos[i]*3));
                    break;
                    
                }
                
            
            }
            
        }
        int total=0;
        for(int i=0;i<translatedSize.length;i++)
        {
            total+=translatedSize[i];
        }
        if(strtCodonsPos.length==0)
        {
            total=0;
        }
        return 1.0*total/translatedSize.length;
        
    }
        
    
    
    //method to add item to list, length increases
    public int[] add(int[] c, int i)
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
    
    
    //Returns a string representation of protein array, for displaying&file saving purpose
    //counts number of lines
    public void proteinToString(int c)
    {
        
        for(int i=0;i<protSeq.length;i++)
        {
            if(i>0)
            {
            
            
                if(i%50==0)
                {
                processedSeq+="\r\n";
                numLines++;
            
                }
            }
            processedSeq+=protSeq[i];
        }
    }
    
    //returns number of lines
    public int getNumLines()
    {
        return numLines;
    }
    
    //returns String representation of translation product with start/stop codons processed
    public String getTranslatedSequence()
    {
        for(int i=0;i<protSeq.length;i++)
        {
            processedSeq+=protSeq[i];
        }
        
        return processedSeq;
    }
    
    //returns length of the latter
    public int returnTranslatedSequenceSize()
    {
        return protSeq.length;
    }
    public String getFastaProtSeq()
    {
        String tmp = processedSeq.replace('>', 'M');
        tmp = tmp.replace('*', '-');
        return tmp;
    }
}
